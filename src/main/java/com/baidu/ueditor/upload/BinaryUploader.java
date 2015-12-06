package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class BinaryUploader {

	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		if(request instanceof MultipartHttpServletRequest) {
			return handleBySpringMultipartRequest(request, conf);
		}
		else {
			return handleByFileItemRequest(request, conf);
		}
		
//		FileItemStream fileStream = null;
//		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;
//
//		if (!ServletFileUpload.isMultipartContent(request)) {
//			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
//		}
//
//		ServletFileUpload upload = new ServletFileUpload(
//				new DiskFileItemFactory());
//
//        if ( isAjaxUpload ) {
//            upload.setHeaderEncoding( "UTF-8" );
//        }
//
//		try {
//			FileItemIterator iterator = upload.getItemIterator(request);
//
//			while (iterator.hasNext()) {
//				fileStream = iterator.next();
//
//				if (!fileStream.isFormField())
//					break;
//				fileStream = null;
//			}
//
//			if (fileStream == null) {
//				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
//			}
//
//			String savePath = (String) conf.get("savePath");
//			String originFileName = fileStream.getName();
//			String suffix = FileType.getSuffixByFilename(originFileName);
//
//			originFileName = originFileName.substring(0,
//					originFileName.length() - suffix.length());
//			savePath = savePath + suffix;
//
//			long maxSize = ((Long) conf.get("maxSize")).longValue();
//
//			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
//				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
//			}
//
//			savePath = PathFormat.parse(savePath, originFileName);
//
//			String physicalPath = (String) conf.get("rootPath") + savePath;
//
//			InputStream is = fileStream.openStream();
//			State storageState = StorageManager.saveFileByInputStream(is,
//					physicalPath, maxSize);
//			is.close();
//
//			if (storageState.isSuccess()) {
//				storageState.putInfo("url", PathFormat.format(savePath));
//				storageState.putInfo("type", suffix);
//				storageState.putInfo("original", originFileName + suffix);
//			}
//
//			return storageState;
//		} catch (FileUploadException e) {
//			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
//		} catch (IOException e) {
//		}
//		return new BaseState(false, AppInfo.IO_ERROR);
	}
	
	private static State handleByFileItemRequest(HttpServletRequest request, Map<String, Object> conf) {
		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;
		if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }
		FileItemStream fileStream = null;
		try {
			FileItemIterator iterator = upload.getItemIterator(request);
			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}
			
			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}
			
			String fileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(fileName);
			State checkResult = checkSuffix(suffix, conf);
			if(checkResult != null) {
				return checkResult;
			}
			
			String savePath = getSavePath(fileName, conf);
			String physicalPath = getPhysicalPath(savePath, conf);
			InputStream is = fileStream.openStream();
			State storageState = StorageManager.saveFileByInputStream(is, physicalPath, getMaxSize(conf));
			is.close();
			
			if (storageState.isSuccess()) {
				storageState.putInfo("url", PathFormat.format(savePath));
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", fileName);
			}
			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}
	
	private static State handleBySpringMultipartRequest(HttpServletRequest request, Map<String, Object> conf) {
		Map<String,MultipartFile> fileMap = ((MultipartHttpServletRequest)request).getFileMap();
		if(fileMap == null || fileMap.isEmpty()) {
			return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
		}
		MultipartFile file = null;
		for(String name: fileMap.keySet()) {
			file = fileMap.get(name);
			System.out.println("fileName-->" + file.getName());
			if(file != null) {
				break;
			}
		}
		if(file == null) {
			return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
		}
		try {
			String fileName = file.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(fileName);
			State checkResult = checkSuffix(suffix, conf);
			if(checkResult != null) {
				return checkResult;
			}
			
			String savePath = getSavePath(fileName, conf);
			String physicalPath = getPhysicalPath(savePath, conf);
			State storageState = StorageManager.saveFileByInputStream(file.getInputStream(), physicalPath, getMaxSize(conf));
			
			if (storageState.isSuccess()) {
				storageState.putInfo("url", PathFormat.format(savePath));
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", fileName);
			}
			return storageState;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}
	
	private static long getMaxSize(Map<String, Object> conf) {
		return ((Long) conf.get("maxSize")).longValue();
	}
	
	private static State checkSuffix(String suffix, Map<String, Object> conf) {
		if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
			return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
		}
		return null;
	}
	
	private static String getSavePath(String fileName,Map<String, Object> conf) {
		String suffix = FileType.getSuffixByFilename(fileName);
		String originFileName = fileName.substring(0, fileName.length() - suffix.length());
		String savePath = (String) conf.get("savePath");
		savePath = savePath + suffix;
		savePath = PathFormat.parse(savePath, originFileName);
		return savePath;
	}
	
	private static String getPhysicalPath(String savePath, Map<String, Object> conf) {
		String physicalPath = (String) conf.get("rootPath") + savePath;
		
		return physicalPath;
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
