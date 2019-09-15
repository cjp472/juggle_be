package com.juggle.juggle.framework.common.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.util.Random;

public class PasswordUtils {
	public static String encript(String plainText) {
		try{//采用MD5处理
			MessageDigest md =
					MessageDigest.getInstance("MD5");
			byte[] output = md.digest(
					plainText.getBytes());
			//将加密结果output利用Base64转成字符串输出
			String ret =
					Base64.encodeBase64String(output);
			return ret;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String createRandomNumber(int len) {
		//35是因为数组是从0开始的，26个字母+10个数字
		final int  maxNum = 36;
		int i;  //生成的随机数
		int count = 0; //生成的密码的长度
		char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while(count < len){
			//生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count ++;
			}
		}
		return pwd.toString();
	}

	public static String createRandom(int len) {
		//35是因为数组是从0开始的，26个字母+10个数字
		final int  maxNum = 36;
		int i;  //生成的随机数
		int count = 0; //生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while(count < len){
			//生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count ++;
			}
		}
		return pwd.toString();
	}

}
