/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 * 文件名：IOUtil.java
 * 创建人：isheng92 
 * 创建时间：2019年6月14日 下午10:57:37
 * 文件描述：File工具类
 */
package com.isheng.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：isheng92
 * 时间：2019年6月14日 下午10:57:37
 * 描述：File工具类
 */
public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * 读取指定文件中除beginLineChar（包含）与endLineChar（包含）之间的所有内容
	 * @param filePath
	 * @param beginLineChar
	 * @param endLineChar
	 * @return
	 */
	public static String readTextLineNotAmong(String filePath, String beginLineChar, String endLineChar) {
		String result = StringUtil.empty();
		File file = new File(filePath);
		if (!file.isFile() || !file.canRead()) {
			return result;
		}
		
		BufferedReader buffReader = null;
		FileReader fileReader = null;
		StringBuilder content = new StringBuilder();
		try {
			fileReader = new FileReader(file);
			buffReader = new BufferedReader(fileReader);
			String line = "";
			while((line = buffReader.readLine()) != null) {
				if (line.contains(beginLineChar)) {
					while((line = buffReader.readLine()) != null) { //跳过中间部分
						if (line.contains(endLineChar)) {
							break;
						}
					}
					continue;
				}
				content.append(line).append("\n");
			}
			
			result = content.toString();
			
		}  catch (Exception e) {
			logger.error("读取文件异常,", e);
			
		} finally {
			try {
				fileReader.close();
				buffReader.close();
			} catch (Exception e) {
				logger.error("读取文件--关闭流异常,", e);
			}
		}
		
		return result;
	}
	
	/**
	 * 读取指定文件中包含指定字符chars的行的内容
	 * @param filePath
	 * @param chars
	 * @return
	 */
	public static String readIncludeTextLine(String filePath, String chars) {
		String result = StringUtil.empty();
		File file = new File(filePath);
		if (!file.isFile() || !file.canRead()) {
			return result;
		}
		
		BufferedReader buffReader = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
			buffReader = new BufferedReader(fileReader);
			String line = "";
			while((line = buffReader.readLine()) != null) {
				if (line.contains(chars)) {
					result = line;
					break;
				}
			}
			
		}  catch (Exception e) {
			logger.error("读取文件异常,", e);
			
		} finally {
			try {
				fileReader.close();
				buffReader.close();
			} catch (Exception e) {
				logger.error("读取文件--关闭流异常,", e);
			}
		}
		
		return result;
	}
	
	/**
	 * 按行读取指定文件内容到指定的字符串前截至
	 * @param filePath 要读取的文件路径
	 * @param exceptChars 截至字符串
	 * @return
	 */
	public static String readTextLineExceptChars(String filePath, String exceptChars) {
		String result = StringUtil.empty();
		File file = new File(filePath);
		if (!file.isFile() || !file.canRead()) {
			return result;
		}
		
		BufferedReader buffReader = null;
		FileReader fileReader = null;
		StringBuilder content = new StringBuilder();
		try {
			fileReader = new FileReader(file);
			buffReader = new BufferedReader(fileReader);
			String line = "";
			while((line = buffReader.readLine()) != null) {
				if (exceptChars.trim().equals(line.trim())) {
					break;
				}
				line = String.format("%s\n", line);
				content.append(new String(line.getBytes(), "UTF-8"));
			}
			result = content.toString();
			
		}  catch (Exception e) {
			logger.error("读取文件异常,", e);
			
		} finally {
			try {
				fileReader.close();
				buffReader.close();
			} catch (Exception e) {
				logger.error("读取文件--关闭流异常,", e);
			}
		}
		
		return result;
	}
	
	/**
	 * 获取指定文件的内容的总行数，包括回车换行也占一行
	 * @param filePath
	 * @return
	 */
	public static int getFileLineNumber(String filePath) {
		LineNumberReader lnr = null;
		try {
			FileReader fileReader = new FileReader(filePath);
			lnr = new LineNumberReader(fileReader);
			lnr.skip(Long.MAX_VALUE);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("获取文件行数异常，文件未找到，filePath={}", filePath);
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("获取文件行数异常,", e);
			
		} finally {
			if (lnr != null) {
				try {
					lnr.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("获取文件行数， 关闭LineNumberReader异常，", e);
				}
			}
		}
		return lnr.getLineNumber();
	}
	
	public static String readTextByLine(String filePath) {
		String result = StringUtil.empty();
		File file = new File(filePath);
		if (!file.isFile() || !file.canRead()) {
			StringUtil.empty();
		}
		
		BufferedReader br = null;
		FileReader fr = null;
		StringBuilder content = new StringBuilder();
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = br.readLine();
			if (null != line && line.length() > 0) {
				line = String.format("%s\n", line);
				content.append(new String(line.getBytes("UTF-8")));
			}
			
			while(null != line) {
				line = br.readLine();
				if (null != line && !line.isEmpty()) {
					line = String.format("%s\n", line);
					content.append(new String(line.getBytes(), "UTF-8"));
				}
			}
			
			result = content.toString();
			
		}  catch (Exception e) {
			logger.error("读取文件异常,", e);
			
		} finally {
			try {
				fr.close();
				br.close();
			} catch (Exception e) {
				logger.error("读取文件--关闭流异常,", e);
			}
		}
		
		return result;
	}
	
	/**
	 * 写文件到指定目录
	 * @param filePath 文件全路径名
	 * @param content 文件内容
	 * @param append 是否拼接，true-接着写，false-覆盖
	 */
	public static void writeText(String filePath, String content, boolean append) {
		File file = new File(filePath);
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			fw = new FileWriter(file, append);
			bw = new BufferedWriter(fw);
			bw.write(new String(content.getBytes(), "utf-8"));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("写入文件异常，", e);
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (Exception e) {
				logger.error("写入文件--关闭流异常，", e);
			}
		}
	}
	
	/**
	 * 文件拷贝
	 * @param oriFilePath 源文件路径
	 * @param targetPath 目标文件路径
	 * @param deleteOriFile 拷贝完成是否删除源文件
	 */
	public static void copyFile(String oriFilePath, String targetPath, boolean deleteOriFile) {
		
		try {
			Path target = Paths.get(targetPath);
			Path ori = Paths.get(oriFilePath);
			Files.copy(ori, target, StandardCopyOption.REPLACE_EXISTING);
			if (deleteOriFile) {
				Files.deleteIfExists(ori);
			}
		} catch (IOException e) {
			logger.error("文件拷贝异常，", e);
		}
	}
	
	/**
	 * 创建文件夹
	 * @param dirPath 文件夹路径
	 */
	public static void makDir(String dirPath) {
		try {
			Path path = Paths.get(dirPath);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
		} catch (Exception e) {
			logger.error("创建文件夹异常，", e);
		}
	}
	
	public static void deleteFile(String filePath) {
		try {
			Path path = Paths.get(filePath);
			Files.deleteIfExists(path);
		} catch (Exception e) {
			logger.error("删除指定文件异常，", e);
		}
	}
	
	/**
	 * 递归获取指定目录下的所有文件路径，包括子目录中的文件
	 * @param folderPath 文件夹目录
	 * @return 
	 */
	public static List<String> getFilePaths(String folderPath) {
		List<String> list = new ArrayList<>();
		File dir = new File(folderPath);
		File[] files = dir.listFiles();
		if (null == files || files.length == 0) {
			return list;
		}
		
		for (File f : files) {
			if (f.isDirectory()) {
				getFilePaths(f.getAbsolutePath());
			} else {
				list.add(f.getAbsolutePath());
			}
		}
		
		return list;
	}
	
	/**
	 * 获取指定目录下的所有文件夹信息
	 * @param folderPath 文件目录
	 * @return
	 */
	public static List<String> getDirs(String folderPath) {
		List<String> list = new ArrayList<>();
		File dir = new File(folderPath);
		File[] files = dir.listFiles();
		if (null != files && files.length > 0) {
			for (File f : files) {
				if (f.isDirectory()) {
					list.add(f.getAbsolutePath());
				}
			}
		}
		return list;
	}
	
}
