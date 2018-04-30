package com.on.util.common;
/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */

/**
 * @ClassName: ByteIntUtil
 * @Date 2016-6-23
 * @author loyeWen
 *
 */
public class ByteNumUtil {
	/**
	 * short 转换为 2字节的byte[] 高位在前，低位在后
	 */
	public static byte[] shortToBytes(short value) {
		byte[] src = new byte[2];
		src[0] = (byte) ((value >> 8) & 0xFF);
		src[1] = (byte) (value & 0xFF);
		return src;
	}
	/**
	 * int 转换为 4字节的byte[] 高位在前，低位在后
	 */
	public static byte[] intToBytes(int value) {
		byte[] src = new byte[4];
		src[0] = (byte) ((value >> 24) & 0xFF);
		src[1] = (byte) ((value >> 16) & 0xFF);
		src[2] = (byte) ((value >> 8) & 0xFF);
		src[3] = (byte) (value & 0xFF);
		return src;
	}

	/**
	 *   long 转换为 8字节的byte[] 高位在前，低位在后
	 */
	public static byte[] longToBytes(long value) {
		byte[] src = new byte[8];
		src[0] = (byte) ((value >> 56) & 0xFF);
		src[1] = (byte) ((value >> 48) & 0xFF);
		src[2] = (byte) ((value >> 40) & 0xFF);
		src[3] = (byte) ((value >> 32) & 0xFF);
		src[4] = (byte) ((value >> 24) & 0xFF);
		src[5] = (byte) ((value >> 16) & 0xFF);
		src[6] = (byte) ((value >> 8) & 0xFF);
		src[7] = (byte) (value & 0xFF);
		return src;
	}

	/**
	 *  4字节的byte[] 转换为 int
	 */
	public static int bytesToInt(byte[] src) {
		int value;
		value = (int) (((src[0] & 0xFF) << 24)
				| ((src[1] & 0xFF) << 16)
				| ((src[2] & 0xFF) << 8) | (src[3] & 0xFF));
		return value;
	}
	
	/**
	 *  8字节的byte[] 转换为 long
	 */
    public static long bytes2Long(byte[] b) { 
        long s = 0; 
        
        long s7 = b[0] & 0xff;// 最低位 
        long s6 = b[1] & 0xff; 
        long s5 = b[2] & 0xff; 
        long s4 = b[3] & 0xff; 
        long s3 = b[4] & 0xff;// 最低位 
        long s2 = b[5] & 0xff; 
        long s1 = b[6] & 0xff; 
        long s0 = b[7] & 0xff; 
 
        // s0不变 
        s1 <<= 8; 
        s2 <<= 16; 
        s3 <<= 24; 
        s4 <<= 8 * 4; 
        s5 <<= 8 * 5; 
        s6 <<= 8 * 6; 
        s7 <<= 8 * 7; 
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7; 
        return s; 
    }

	/**
	 *  2字节的byte[] 转换为 short
	 */
	public static short bytesToShort(byte[] src) {
		short value;
		value = (short) (((src[0] & 0xFF) << 8)
				| src[1] & 0xFF);
		return value;
	}

	public static void main(String[] args) {
		byte[] temp = StringHexUtil.hexString2Bytes("FFE2");
		byte[] temp22 = {(byte)0,(byte)0,temp[0],temp[1]} ;

		System.out.println("result11:" + ByteNumUtil.bytesToShort(StringHexUtil.hexString2Bytes("FFE2")));

		System.out.println("result22:" + ByteNumUtil.bytesToInt(temp22));
	}
}
