package com.lunarku.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.lunarku.item.pojo.Item;
import com.lunarku.shop.pojo.TbItem;
import com.lunarku.shop.pojo.TbItemDesc;
import com.lunarku.shop.service.ItemDescService;
import com.lunarku.shop.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ItemAddMessageListener implements MessageListener{

	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;
	
	/**
	 * 当manager后台系统添加商品时，通过activemq触发onMessage事件，为添加商品自动生成静态html页面
	 * @param message
	 */
	@Override
	public void onMessage(Message message) {
		System.out.println("huji");
		try {
			TextMessage textMessage = (TextMessage)message;
			String strId = textMessage.getText();
			
			long itemId = Long.parseLong(strId);
			System.out.println(itemId);
			// 等待事务提交
			Thread.sleep(1000);
			TbItem tbItem = itemService.getItemById(itemId);
			Item item = new Item(tbItem);			
			TbItemDesc itemDesc = itemDescService.getItemDescById(itemId);
			// 生成静态页面
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("item", item);
			data.put("itemDesc", itemDesc);
			
			FileWriter out = new FileWriter(new File(HTML_OUT_PATH,itemId+".html"));
			template.process(data, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
