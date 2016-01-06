/**
 * 
 */
package com.enjoy.love.common.encrypt.ucf;

import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * @author lenovo
 *
 */
public class UcfForOnline {
	public static Boolean verify(String signKey, String signName, String sign,
			Map<String, String> params, String type)
			throws GeneralSecurityException, CoderException {
		boolean isVerifySuccess = false;
		if ((null == sign) || ("".equals(sign)) || (null == signKey)
				|| ("".equals(signKey))) {
			return Boolean.valueOf(isVerifySuccess);
		}
		String signOut = UcfDigestUtils.encode(signKey,
				UcfDigestUtils.getSignData(signName, params), type);
		if (sign.equals(signOut)) {
			isVerifySuccess = true;
		}
		return Boolean.valueOf(isVerifySuccess);
	}

	public static String createSign(String signKey, String signName,
			Map<String, String> params, String type)
			throws GeneralSecurityException, CoderException {
		return UcfDigestUtils.encode(signKey,
				UcfDigestUtils.getSignData(signName, params), type);
	}

	public static String encode(String data, String key, String codeType)
			throws GeneralSecurityException, CoderException {
		return UcfDigestUtils.encode(key, data, codeType);
	}

	public static String decode(String data, String key, String codeType)
			throws Exception {
		return UcfDigestUtils.decode(key, data, codeType);
	}
}
