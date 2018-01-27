package com.shop.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.lunarku.shop.util.FastDFSClient;

public class FastDFSTest {
	
	@Test
	public void testFileUpload() throws Exception{
		//1.加载配置文件全路径
		ClientGlobal.init("F:\\eclipse_wsp\\taotao\\shop-manager-web\\src\\main\\resources\\resource\\client.conf");
		//2. 创建一个TrackerClient对象.
		TrackerClient  trackerClient= new TrackerClient();
		//3. 使用TrackerClient对象创建连接，获得一个TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		//4. 创建一个StorageServer的引用，值为null
		StorageServer storageServer = null;
		//5. 创建一个StorageClient对象，需要两个参数，TrackerServer、StorageServer的引用
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//6. 使用storageClient对象上传图片, 扩展名不带"."
		String[] strings = storageClient.upload_file("C:\\Users\\18361\\Pictures\\桌面\\57a2b02b0e95614bb5045a407e905299_hd.jpg", "jpg", null);
		//7. 返回数组，包含组名和图片的路径
		for(String string: strings) {
			System.out.println(string);
		}		
	}
	
	@Test
	public void testFastDFSClient() throws Exception {
		String conf = "F:\\eclipse_wsp\\taotao\\shop-manager-web\\src\\main\\resources\\resource\\client.conf";
		FastDFSClient util = new FastDFSClient(conf);
		String result = util.uploadFile("C:\\Users\\18361\\Pictures\\桌面\\57a2b02b0e95614bb5045a407e905299_hd.jpg", "jpg");
		System.out.println(result);
	}
}
