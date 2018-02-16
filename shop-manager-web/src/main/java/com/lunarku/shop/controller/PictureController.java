package com.lunarku.shop.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lunarku.shop.common.util.JsonUtils;
import com.lunarku.shop.util.FastDFSClient;

@Controller
public class PictureController {
	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SAVER_URL;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	@SuppressWarnings("all")
	public String fileUpload(MultipartFile uploadFile) {
		Map map = new HashMap();
		try {
			// 获取文件扩展名
			String originalFileName = uploadFile.getOriginalFilename();
			String extName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
			
			// 创建一个FastDFS的客户端
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
			String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			String url = IMAGE_SAVER_URL + path;
			
			map.put("error", 0);
			map.put("url", url);
			return JsonUtils.objectToJson(map);
			//return map; // 直接返回map， 火狐浏览器不兼容， 返回string可以解决就这个问题
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("error", 1);
			map.put("message", "上传失败");
			return JsonUtils.objectToJson(map);
			//return map;
		}
	}
	
}
