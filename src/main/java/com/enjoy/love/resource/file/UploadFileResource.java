package com.enjoy.love.resource.file;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeLimitExceededException;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/file/upload")
public class UploadFileResource {
	
	private static List<String> imageSuffixTypeList = Arrays.asList(new String[]{"jpg","png","bmp"});
	
	@RequestMapping(value="/servletFileUpload",produces = "application/json")
	@ResponseBody
	public Object uploadFileByServletFileUpload(HttpServletRequest request, HttpServletResponse response) {
		try {
			return this.handleRequestFileList(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.getEmpty();
	}
	
	@RequestMapping(value="/springMultipartRequest")
	@ResponseBody
	public Object uploadFileBySpringMultipartRequest(HttpServletRequest request) {
		try {
			Map<String,MultipartFile> fileMap = ((MultipartHttpServletRequest)request).getFileMap();
			for(String name: fileMap.keySet()) {
				System.out.println("name--->" + name);
				MultipartFile file = fileMap.get(name);
				this.printMultipartFile(file);
			}
			return this.getImageUrlJson("http://img30.360buyimg.com/cf/jfs/t1930/175/815670745/738809/65cd6120/562db06fN6a0a96ec.jpg");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return this.getEmpty();
	}
	
	@RequestMapping(value="/springMultipart")
	@ResponseBody
	public Object uploadFileBySpringMultipart(@RequestParam("upload2") MultipartFile file) throws IOException {
		if(!file.isEmpty()) {
			this.printMultipartFile(file);
			return this.getImageUrlJson("http://img30.360buyimg.com/cf/jfs/t1930/175/815670745/738809/65cd6120/562db06fN6a0a96ec.jpg");
		}
		return this.getEmpty();
	}
	
	private void printMultipartFile(MultipartFile file) throws IOException {
		System.out.println("size-->" + file.getSize());
		System.out.println("length-->" + file.getBytes().length);
		System.out.println("name--->" + file.getName());
	}
	
	private String getEmpty() {
		return this.getImageUrlJson("empty");
	}
	
	private String getImageUrlJson(String imageUrl) {
		return "{\"imageUrl\":\""+imageUrl+"\"}";
	}
	
	private Map<String,Object> handleRequestFileList(HttpServletRequest request) throws FileUploadException {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		List<FileItem> fileList = fileUpload.parseRequest(request);
		for(FileItem fileItem: fileList) {
			String result = handleOneRequestFile(fileItem, imageSuffixTypeList);
			if(StringUtils.isNotBlank(result)) {
				resultMap.put("imageUrl", result);
			}
		}
		if(resultMap.isEmpty()) {
			resultMap.put("imageUrl", "not found image");
		}
		return resultMap;
	}
	
	private String handleOneRequestFile(FileItem fileItem, List<String> fileTypeList) {
		if(fileItem == null || fileItem.isFormField()) {
			return null;
		}
		String fileName = fileItem.getName();
		if(StringUtils.isBlank(fileName)) {
			return null;
		}
		System.out.println("fileName--->" + fileName);
		if(!this.isContianFileSuffix(fileTypeList, this.getFileSuffix(fileName))) {
			return null;
		}
		return storeUploadFileAndReturnAccessUrl(fileItem);
	}
	
	private String storeUploadFileAndReturnAccessUrl(FileItem fileItem) {
		//存储上传的文件
		return "http://localhost:8080/image/lib/upload/sl226_170.png";
	}
	
	private String getFileSuffix(String fileName) {
		String suffix = "";
		if (fileName.lastIndexOf(".") >= 0) {
			suffix = fileName.substring(fileName.lastIndexOf("."));
		}
		return suffix;
	}
	
	private boolean isContianFileSuffix(List<String> fileTypeList, String fileSuffix) {
		if(StringUtils.isBlank(fileSuffix)) {
			return false;
		}
		if(fileTypeList.contains(fileSuffix)) {
			return true;
		}
		return false;
	}
	
//	@Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize("1KB");
//        factory.setMaxRequestSize("1KB");
//        return factory.createMultipartConfig();
//    }
}
