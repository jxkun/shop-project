package com.lunarku.shop.util;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSClient {
	
	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageClient storageClient = null;
	private StorageServer storageServer = null;
	
	public FastDFSClient(String conf_filename) throws Exception{
		if(conf_filename.contains("classpath:")) {
			conf_filename = conf_filename.replace("classpath:", this.getClass().getResource("/").getPath());
		}
		ClientGlobal.init(conf_filename);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient(trackerServer, storageServer);
	}
	
	/**
	 * 上传文件方法
	 * @param fileName 文件全路径
	 * @param extName 文件扩展名， 不含 .
	 * @param metas 文件扩展信息
	 * @return 示例： group1/M00/00/00/wKgZr1pskrOAcnhtAACeQ-fhhYc047.jpg
	 * @throws Exception
	 */
	public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
		String[] list = storageClient.upload_file(fileName, extName, metas);
		String result = list[0] + "/" + list[1];
		return result;
	}
	
	public String uploadFile(String fileName, String extName) throws Exception {
		return uploadFile(fileName, extName, null);
	}
	
	public String uploadFile(String fileName) throws Exception {
		return uploadFile(fileName, null, null);
	}
	
	/**
	 * 上传文件方法
	 * @param fileContent 文件内容字节数组
	 * @param extName 文件扩展名
	 * @param metas 文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {
		String[] list = storageClient.upload_file(fileContent, extName, metas);
		String result = list[0] + "/" + list[1];
		return result;
	}
	
	public String uploadFile(byte[] fileContent, String extName) throws Exception{
		return uploadFile(fileContent, extName, null);
	}
	
	public String uploadFile(byte[] fileContent) throws Exception{
		return uploadFile(fileContent, null, null);
	}
}
