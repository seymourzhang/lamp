package com.on.util.common;

/**
 * @ClassName: StringHexUtil
 * @Date 2016-6-23
 * @author loyeWen
 *
 */
public class StringHexUtil {
	
	/**
	 * 把字节数组转换成16进制字符串
	 */
	public static final String bytes2HexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		for (int i = 0; i < bArray.length; i++) {
			sb.append(b2HS(bArray[i]));
		}
		return sb.toString();
	}

	public static final String bytes2String(byte[] bArray) {
		return new String(bArray);
	}
	
	/**
	 * 把字节转换成16进制字符串
	 */
	public static final String b2HS(byte b) {
		String result ="";
		result = Integer.toHexString(0xFF & b);
		if (result.length() < 2){
			result="0"+result;
		}
		return result.toUpperCase();
	}
    
	/**
	 * 把16进制字符串转换成字节数组
	 */
	public static byte[] hexString2Bytes(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
	
	/**
     * 把字节数组转化成2进制字符串 
     */  
    public static String bytes2BinaryString(byte[] b)
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i)
        {
            buffer.append(b2BS(b[i]));
        }
        return buffer.toString();
    }

	/**
	 * 把16进制转字符串
	 */
	public static String convertHexToString(String hex){

		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		//49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for( int i=0; i<hex.length()-1; i+=2 ){

			//grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			//convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			//convert the decimal to character
			sb.append((char)decimal);

			temp.append(decimal);
		}

		return sb.toString();
	}

	/**
     * 把字节转化成2进制字符串 
     */  
    public static String b2BS(byte b){
        String result ="";  
        byte a = b;  
        for (int i = 0; i < 8; i++){
            byte c=a;  
            a=(byte)(a>>1);//每移一位如同将10进制数除以2并去掉余数。  
            a=(byte)(a<<1);
            if(a==c){  
                result="0"+result;  
            }else{  
                result="1"+result;  
            }  
            a=(byte)(a>>1);  
        }  
        return result;  
    }
	
    public static void main(String[] args)
    {
//    	byte[] byteArray = hexString2Bytes("4198CCCD");
//    	int resInt = ByteNumUtil.bytesToInt(byteArray);
//    	Float resFloat = Float.intBitsToFloat(resInt);
		String resString = convertHexToString("383636383733303230373236343936260A17011067DC0021CE64".substring(0, 32));

		System.out.println("length: " + "38363638373330323037323634393626".length());
    	System.out.println("resString  :" + resString);

    }
}