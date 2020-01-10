/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 * 文件名：UploadUtil.java
 * 创建人：isheng92 
 * 创建时间：2019年6月19日 下午11:10:50
 * 文件描述：
 */
package com.isheng.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：isheng92
 * 时间：2019年6月19日 下午11:10:50
 * 描述：文件上传工具类
 */
public class UploadUtil {

	private static final Logger logger = LoggerFactory.getLogger(UploadUtil.class);

	/**
	 * 表单字段常量
	 */
	public static final String FORM_FIELDS = "form_fields";
	/**
	 * 文件域常量
	 */
	public static final String FILE_FIELDS = "file_fields";

	// 最大文件大小
	private long maxSize = 1000000;
	// 定义允许上传的文件扩展名
	private Map<String, String> extMap = new HashMap<String, String>();
	// 文件保存相对路径
	private String basePath = "upload";
	// 文件的目录名
	private String dirName = "images";
	// 上传临时路径
	private static final String TEMP_PATH = "/temp";
	// 临时路径
	private String tempPath = basePath + TEMP_PATH;
	// 若不指定则文件名默认为 yyyyMMddHHmmss_xyz
	private String fileName;
	// 文件保存目录路径
	private String savePath;
	// 文件保存目录url
	private String saveUrl;
	// 文件最终的url包括文件名
	private String fileUrl;

	public UploadUtil() {
		// 其中images,flashs,medias,files,对应文件夹名称,对应dirName
		// key文件夹名称
		// value该文件夹内可以上传文件的后缀名
		extMap.put("images", "gif,jpg,jpeg,png,bmp");
		extMap.put("flashs", "swf,flv");
		extMap.put("medias", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("files", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
	}

	/**
	 * 文件上传
	 * @param request
	 * @return messages: messages[0]验证文件域返回错误信息 messages[1] 上传文件错误信息 messages[2] savePath messages[3]
	 */
	@SuppressWarnings("unchecked")
	public String[] uploadFile(HttpServletRequest request) {
		String[] messages = new String[5];
		// 验证
		messages[0] = this.validFields(request);
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		if ("true".equals(messages[0])) {
			fieldsMap = this.initFields(request);
		}

		// 上传
		List<FileItem> fileItemList = (List<FileItem>) fieldsMap.get(UploadUtil.FILE_FIELDS);
		if (null != fileItemList && fileItemList.size() > 0) {
			for (FileItem item : fileItemList) {
				messages[1] = this.saveFile(item);
			}
			messages[2] = savePath;
			messages[3] = saveUrl;
			messages[4] = fileUrl;
		}
		return messages;
	}

	/**
	 * 上传验证，并初始化文件目录
	 * @param request
	 * @return
	 */
	private String validFields(HttpServletRequest request) {
		String message = "true";
		String contentType = request.getContentType();
		int contentLength = request.getContentLength();
		// 文件保存目录路径
		savePath = contextRealPath(request) + basePath + File.separator;
		// 文件保存目录url
		saveUrl = request.getContextPath() + File.separator + basePath + File.separator;

		File uploadDir = new File(savePath);
		if (contentType == null || !contentType.startsWith("multipart")) {
			message = "请求不包含multipart/form-data流";

		} else if (maxSize < contentLength) {
			message = "上传文件大小超出最大文件大小[" + maxSize + "]";

		} else if (!ServletFileUpload.isMultipartContent(request)) {
			message = "请选择文件";

		} else if (!uploadDir.isDirectory()) {
			message = "上传目录[" + savePath + "]不存在";

		} else if (!uploadDir.canWrite()) {
			message = "上传目录[" + savePath + "]没有写权限";

		} else if (!extMap.containsKey(dirName)) {
			message = "目录名不正确";

		} else {
			savePath += dirName + File.separator;
			saveUrl += dirName + File.separator;

			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}

			String dateTime = dateTime("yyyyMMdd");
			savePath += dateTime + File.separator;
			saveUrl += dateTime + File.separator;

			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}

			tempPath = contextRealPath(request) + tempPath + File.separator;
			File tempFile = new File(tempPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
		}
		return message;
	}

	/**
	 * 处理上传的内容, 普通form字段，文件字段
	 * @return
	 */
	private Map<String, Object> initFields(HttpServletRequest request) {
		// 存储表单字段和非表单字段
		Map<String, Object> map = new HashMap<String, Object>();
		// 1. 判断request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// 2. 解析request
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 阀值,超过这个值才会写到临时目录,否则在内存中
			factory.setSizeThreshold(1024 * 1024 * 10);
			factory.setRepository(new File(tempPath));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload();
			upload.setHeaderEncoding("UTF-8");
			// 最大上传限制
			upload.setSizeMax(maxSize);

			List<FileItem> items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				logger.error("Upload File ParseRequest FileUploadException：", e);
			}

			// 3.处理upload items
			if (null != items && items.size() > 0) {
				Iterator<FileItem> iter = items.iterator();
				// 文件域对象
				List<FileItem> itemList = new ArrayList<>();
				// 表单字段
				Map<String, String> fieldMap = new HashMap<>();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					if (item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString();
						fieldMap.put(name, value);
					} else {
						itemList.add(item);
					}
				}
				map.put(FORM_FIELDS, fieldMap); // 普通form字段
				map.put(FILE_FIELDS, itemList); // 文件字段
			}
		}
		return map;
	}

	/**
	 * 保存上传文件
	 * @param item 要上传的文件域
	 * @return
	 */
	private String saveFile(FileItem item) {
		String message = "true";
		fileName = item.getFieldName();
		String fileExt = fileName.substring((fileName.lastIndexOf(".") + 1)).toLowerCase();
		if (item.getSize() > maxSize) {
			message = "上传文件大小超过限制";
		} else {
			String newFileName;
			if ("".equals(fileName.trim())) {
				newFileName = dateTime("yyyyMMddHHmmss") + "_" + new Random().nextInt(1000) + "." + fileExt;
			} else {
				newFileName = fileName + "." + fileExt;
			}

			fileUrl = saveUrl + newFileName;
			try {
				File uploadFile = new File(savePath, newFileName);
				item.write(uploadFile);
			} catch (IOException e) {
				logger.error("Upload File IOException：", e);
			} catch (Exception e) {
				logger.error("Upload File Exception：", e);
			}
		}
		return message;
	}

	private String contextRealPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}

	private String dateTime(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	
	//*************************************** get/set ************************************/

	public long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	public Map<String, String> getExtMap() {
		return extMap;
	}

	public void setExtMap(Map<String, String> extMap) {
		this.extMap = extMap;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		tempPath = basePath + TEMP_PATH;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getSaveUrl() {
		return saveUrl;
	}

	public void setSaveUrl(String saveUrl) {
		this.saveUrl = saveUrl;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

}
