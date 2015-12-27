package com.enjoy.love.service;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.enjoy.love.common.log.CommonLogger;
import com.enjoy.love.common.log.CommonLoggerFactory;
import com.enjoy.love.common.runtime.CommonRuntimeException;
import com.enjoy.love.common.runtime.ServiceConstants;

public class ServiceTemplateImpl implements ServiceTemplate {
	private static final CommonLogger logger = CommonLoggerFactory
			.getLogger(ServiceTemplateImpl.class);
	
	/**
	 * Spring事务模板
	 */
	protected TransactionTemplate transactionTemplate;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CallbackResult execute(final ServiceCallback action, Object domain) {
		if (logger.isDebugEnabled()) {
			logger.debug("进入模板方法开始处理");
		}
		CallbackResult result = CallbackResult.success();
		try {
			// 1. 处理环境参数
			// FIXME：性能统计点 P1
			result = action.executeCheck();
			// --- END P1
			
			if (result.isSuccess()) {
				// 2. 处理事务
				// FIXME:性能统计点 P2
				this.transactionTemplate
				.execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus status) {
						// 3. 回调业务逻辑
						// 3.1 通过annotation来实现某些option类型的扩展
						CallbackResult iNresult = action
								.executeAction();
						if (null == iNresult) {
							throw new ServiceException(
									ServiceConstants.SERVICE_NO_RESULT);
						}
						// 4. 扩展点
						templateExtensionInTransaction(iNresult);
						if (iNresult.isFailure()) {
							status.setRollbackOnly();
							return iNresult;
						}
						// 在事务内发送统一事件由业务自己来决定，因为补偿接口必须由
						// 业务自己来设定，在模板中是无法设定的
						// 5. 发送事件
						/*
						 * sendUniformEvent(iNresult, uniformEvent,
						 * domain); if (iNresult.isFailure()) {
						 * status.setRollbackOnly(); return iNresult; }
						 */
						return iNresult;
					}
				});
				// 6. 扩展点
				if (result.isSuccess()) {
					templateExtensionAfterTransaction(result);
				}
				// 不在关心result是否可能被回滚，因为已经在事务之外了
			}
			if (logger.isDebugEnabled()) {
				logger.debug("正常退出模板方法");
			}
		} catch (ServiceException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(formatInvocationArgs("异常退出模板方法A点"), e);
			}
			// FIXME:logError(productLog, e.getErrorCode(), e.getMessage());
			result = CallbackResult.failure(e.getErrorCode(), e,
					e.getParameters());

		} catch (CommonRuntimeException e) {
			if (logger.isErrorEnabled()) {
				logger.error(formatInvocationArgs("异常退出模板方法B点"), e);
			}
			// FIXME:logError(productLog, e.getErrorCode(), e.getMessage());
			result = CallbackResult.failure(e.getErrorCode(), e,
					e.getParameters());

		} catch (Throwable e) {
			if (logger.isErrorEnabled()) {
				logger.error(formatInvocationArgs("异常退出模板方法C点"), e);
			}
			// FIXME:logError(productLog, e);
			result = CallbackResult.failure(
					ServiceConstants.SERVICE_SYSTEM_FALIURE, e);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("模板方法处理结束");
		}
		
		return result;
	}

	@Override
	public CallbackResult executeWithoutTransaction(ServiceCallback action,
			Object domain) {
		if (logger.isDebugEnabled()) {
			logger.debug("进入模板方法开始处理");
		}
		
		CallbackResult result = CallbackResult.success();
		try {
			// 1. 处理环境参数
			// FIXME：性能统计点 P1
			result = action.executeCheck();
			// --- END P1
			
			if (result.isSuccess()) {
				// FIXME:性能统计点 P2
				// 3. 回调业务逻辑
				// NOTICE: 通过annotation来实现某些option类型的扩展
				result = action.executeAction();
				if (null == result) {
					throw new ServiceException(
							ServiceConstants.SERVICE_NO_RESULT);
				}
				
				// 4. 扩展点
				templateExtensionAfterExecute(result);
				if (result.isFailure()) {
					return result;
				}
				
			}
		} catch (ServiceException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(formatInvocationArgs("异常退出模板方法D点"), e);
			}
			// FIXME:logError(productLog, e.getErrorCode(), e.getMessage());
			result = CallbackResult.failure(e.getErrorCode(), e,
					e.getParameters());

		} catch (CommonRuntimeException e) {
			if (logger.isErrorEnabled()) {
				logger.error(formatInvocationArgs("异常退出模板方法E点"), e);
			}
			// FIXME:logError(productLog, e.getErrorCode(), e.getMessage());
			result = CallbackResult.failure(e.getErrorCode(), e,
					e.getParameters());

		} catch (Throwable e) {
			if (logger.isErrorEnabled()) {
				logger.error(formatInvocationArgs("异常退出模板方法F点"), e);
			}
			// FIXME:logError(productLog, e);
			result = CallbackResult.failure(
					ServiceConstants.SERVICE_SYSTEM_FALIURE, e);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("模板方法处理结束");
		}
		return result;
	}
	
	/**
	 * 扩展点：模板提供的允许不同类型业务在<b>事务内</b>进行扩展的一个点
	 * 
	 * @param serviceContext
	 * @param domain
	 */
	protected void templateExtensionInTransaction(CallbackResult result) {
		// DUMY
	}
	
	/**
	 * 扩展点：模板提供的允许不同类型业务在<b>事务后</b>进行扩展的一个点
	 * 
	 * @param serviceContext
	 * @param domain
	 */
	protected void templateExtensionAfterTransaction(CallbackResult result) {
		// DUMY
	}
	
	/**
	 * 扩展点：模板提供的允许不同类型业务在<b>非事务类场景下，回调函数结束之后</b>进行扩展的一个点
	 * 
	 * @param serviceContext
	 * @param domain
	 */
	protected void templateExtensionAfterExecute(CallbackResult result) {
		// DUMY
	}
	
	// 用来打印某些系统异常时的完整环境数据，以方便找出问题
	private String formatInvocationArgs(String prefix) {
		// to do 需要从分布式环境上下文中取数据
		return prefix;
	}
	
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

}
