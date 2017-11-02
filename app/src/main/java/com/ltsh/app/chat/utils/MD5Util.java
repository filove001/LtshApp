package com.ltsh.app.chat.utils;


import java.security.MessageDigest;

/**
 * MD5加密封装
 * @author zhongJc
 * @version 2017年6月6日 下午4:17:22
 */
public class MD5Util {

	/**
	 * @description 指定参数对MD5进行加密
	 * @param data
	 * @param charset
	 * @return
	 */
	public static String encoder(String data,String charset){
		// 初始化MessageDigest
		String t = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			// 执行消息摘要
			t = Base64.encode(md.digest(data.getBytes(charset)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t.replaceAll("\\n", "");
	}

	/**
	 * @description 执行MD5加密
	 * @param data
	 * @return
	 */
	public static String encoder(String data){
		return encoder(data, "utf-8");
	}


	public static void main(String [] args){
		System.out.print(MD5Util.encoder("11212"));
	}
}
