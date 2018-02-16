package com.lunarku.shop.common.util;

import java.util.Random;

/**
 * id 生成工具类
 */
public class IDUtil {

	/**
	 * 生成图片名， 当前毫秒值 + 3位随机数
	 * @return
	 */
	public static String getImageName() {
		long millis = System.currentTimeMillis();
		Random random = new Random();
		int end3 = random.nextInt(999);
		// 不足3位前面补零
		String res = millis + String.format("%03d", end3);
		return res;
	}
	
	/**
	 * 生成商品id， 当前毫秒值 + 2位随机数
	 * @return
	 */
	public static long getItemId() {
		 long millis = System.currentTimeMillis();
		 Random random = new Random();
		 int end2 = random.nextInt(99);
		 String str = millis + String.format("%02d", end2);
		 
		 return new Long(str);
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 200; i++) {
			System.out.println(getItemId());
		}
	}
	
}
