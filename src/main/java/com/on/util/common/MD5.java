package com.on.util.common;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String md5(String str) {
		try {
			String mdKey = PubFun.getPropertyValue("System.Password.MessageDigest").toUpperCase();
			MessageDigest md = MessageDigest.getInstance(mdKey);
//			if("MD5".equals(mdKey)){
//				md.update(str.getBytes());
//				byte b[] = md.digest();
//
//				int i;
//
//				StringBuffer buf = new StringBuffer("");
//				for (int offset = 0; offset < b.length; offset++) {
//					i = b[offset];
//					if (i < 0)
//						i += 256;
//					if (i < 16)
//						buf.append("0");
//					buf.append(Integer.toHexString(i));
//				}
//				str = buf.toString();
//			} else{
				str = encoderByMd5(md, str);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**利用MD5进行加密
	 * @param str  待加密的字符串
	 * @return  加密后的字符串
	 * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
	 * @throws UnsupportedEncodingException
	 */
	public static String encoderByMd5(MessageDigest md, String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//确定计算方法
		BASE64Encoder base64en = new BASE64Encoder();
		//加密后的字符串
		String newstr=base64en.encode(md.digest(str.getBytes("utf-8")));
		return newstr;
	}


//	public static void main(String[] args) {
////		System.out.println(md5("31119@qq.com"+"123456"));
////		System.out.println(md5("mj1"));
//		System.out.println(md5("Admin_123"));
//	}
}
