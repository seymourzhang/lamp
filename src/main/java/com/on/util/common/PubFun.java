/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.on.util.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
* @ClassName: PubFun
* @Description: 
* @Date 2015年9月10日 上午10:48:41
* @Author Yin Guo Xiang
* 
*/ 
public class PubFun {
	private static Logger logger = Logger.getLogger(PubFun.class);

	/**
	 * 得到当前系统日期 author: GX
	 * 
	 * @return 当前日期的格式字符串,日期格式为"yyyy-MM-dd"
	 */
	public static String getCurrentDate() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到当前系统时间 author: GX
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	 */
	public static String getCurrentTime() {
		String pattern = "HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}
	
	/**
	 * 得到当前系统日期 author: GX
	 * 
	 * @return 当前日期的格式字符串,日期格式为"yyyyMMdd"
	 */
	public static String getCurrentDate2() {
		String pattern = "yyyyMMdd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到当前系统时间 author: GX
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HHmmss"
	 */
	public static String getCurrentTime2() {
		String pattern = "HHmmss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}
	
	/**
	 * 得到指定日期的Date author: GX
	 * 
	 * @return 指定日期的Date ,参数格式：YYYY-MM-DD
	 * @throws Exception 
	 */
	public static Date getDate(String tDate) throws Exception {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date returnDate = df.parse(tDate);
		return returnDate;
	}
	
	/**
	 * 得到WEB-INF的路径
	 * 
	 * @return 指定日期的Date ,参数格式：YYYY-MM-DD
	 * @throws Exception 
	 */
	public static String getWEBINFpath() throws Exception {
		String path = Thread.currentThread().getContextClassLoader()
						.getResource("").toString();  
        path=path.replace("file:/", ""); //去掉file:  
        path=path.replace("classes/", ""); //去掉class\  
        return path;  
	}

	/**
	 * 
	 * @return 指定日期的Date ,参数格式：YYYY-MM-DD
	 * @throws ParseException 
	 * @throws ParseException
	 * @throws ParseException
	 *   			类型的日期差
	 */
	
	public static int daysBetween(Date smdate,Date bdate) throws ParseException   
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
	
	/*
	 * 日期加天数得到的日期
	 */
	 public static Date addDate(Date date,int day){
		 Date retDate = null;
	     try {
			Calendar   calendar   =   new   GregorianCalendar(); 
			 calendar.setTime(date); 
			 calendar.add(Calendar.DATE, day);//把日期往后增加一天.整数往后推,负数往前移动 
			 retDate=calendar.getTime();   //这个时间就是日期往后推一天的结果 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return retDate;
	} 
	
	public static String addDate(String date, int day) throws Exception {
		Date tDate = PubFun.getDate(date);
		Date resDate = PubFun.addDate(tDate, day);
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(resDate) ;
	}
	
	public static Object [][] convertResult2Array(
				List<LinkedHashMap<String,Object>> sourceRes){
		Object [][] targetRes = null;
		if(sourceRes != null && sourceRes.size()>0){
			targetRes = new Object[sourceRes.size()][] ;
			int itemIndex = 0;
			for(HashMap<String, Object> item:sourceRes){
				Object [] array2 = new Object[item.keySet().size()] ;
				int keyIndex = 0;
				for(String key:item.keySet()){
					array2[keyIndex] = item.get(key);
					keyIndex ++;
				}
				targetRes[itemIndex] = array2;
				itemIndex++;
			}
		}
		return targetRes;
	}
	
	public static String getRequestPara(HttpServletRequest request){
		StringBuilder sb = new StringBuilder ();
        try {
			InputStream is = request.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			 byte [] buffer = new byte[1024];
			 int read = 0;
			 while ((read=bis.read(buffer)) != -1){
			      sb.append( new String(buffer, 0, read, "UTF-8" ));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return sb.toString();
	}
	
	/*public static String getRequestPara(MAPIHttpServletRequestWrapper request){
		StringBuilder sb = new StringBuilder ();
        try {
			InputStream is = request.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			 byte [] buffer = new byte[1024];
			 int read = 0;
			 while ((read=bis.read(buffer)) != -1){
			      sb.append( new String(buffer, 0, read, "UTF-8" ));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return sb.toString();
	}*/
	
	public static boolean isNull(Object o){
		if(o == null){
			return true;
		}else if("".equals(o)){
			return true;
		}else{
			return false;
		}
	}
	
	public static BigDecimal getBigDecimalData(String val){
		BigDecimal tVal = null ;
		try {
			if(val == null || val.equals("")){
				return null;
			}else{
				tVal = BigDecimal.valueOf(Double.valueOf(val));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return tVal;
	}
	public static Double getDoubleData(String val){
		Double tVal = null ;
		try {
			tVal = Double.valueOf(val);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return tVal;
	}
	public static Integer getIntegerData(String val){
		Integer tVal = null ;
		try {
			tVal = Integer.valueOf(val);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return tVal;
	}
	
	/**
	 * double 保留小数位数
	 * @param num
	 * @param length
	 * @return
	 */
	public static String formatDoubleNum(double num,int length){
        String numStr = Double.toString(num);
        if(numStr.equals("NaN")){
        	return "-";
        }
        if((numStr.length()-numStr.indexOf("."))<length)
            return numStr;
        StringBuffer strAfterDot = new StringBuffer();
        int i = 0;
        while(i<length){
            strAfterDot.append("0");
            i++;
        }
        String formatStr = "0."+strAfterDot.toString();
        if(length==0){
        	formatStr = "0";
        }
        DecimalFormat df = new DecimalFormat( formatStr);
        String res = df.format(num);
        if(res.indexOf("∞")>=0){
        	res = "-";
        }
        return res;
    }
	
	/**
	 * 补0方法
	 *
	 * @return
	 */
	public static String fillLeftChar(int tInt,char tChar,int tLength){
		String tempStr = tInt+"";
		int needLength = tLength - tempStr.length();
		if(needLength > 0){
			for(int i = 1; i<= needLength; i++){
				tempStr = "" + tChar + tInt ;
			}
		}
		return tempStr;
    }
	
	/**
	 * 根据生长日龄计算周龄
	 * 
	 * @param calType     01-生长日龄/7的算法     02-参考自然周算法
	 * @param dayOfAge    生长日龄
	 * @param dayOfDate   该日龄对应的日期
	 * @return
	 */
	public static int getWeekAge(String calType,int dayOfAge,Date dayOfDate){
		int weekAge = 0;
		try {
			if("01".equals(calType)){
				weekAge = (dayOfAge+6) / 7 ;
			}else if("02".equals(calType)){
				Calendar cal = Calendar.getInstance();  
				Date placeDate = PubFun.addDate(dayOfDate,-dayOfAge+1);
				cal.setTime(placeDate);  
				int placeDateOfweek = cal.get(Calendar.DAY_OF_WEEK);
				if(placeDateOfweek <= 4){
					dayOfAge = dayOfAge + (placeDateOfweek-1);
				}else{
					dayOfAge = dayOfAge - (8- placeDateOfweek);
				}
				weekAge = (dayOfAge + 6) / 7; 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weekAge;
	}

	public static String getPropertyValue(String keyName){
		String value = null;
		try{
			ResourceBundle conf= ResourceBundle.getBundle("pro/Constants");
			System.out.println("constants get IP:" + new String(conf.getString(keyName).getBytes("ISO8859-1"),"UTF-8"));
			value = new String(conf.getString(keyName).getBytes("ISO8859-1"),"UTF-8");
		} catch (Exception e){
			logger.error(e.getMessage());
		} finally {
		}
		return value;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(PubFun.GetGUID());
	}
	/**
	 * Excel column index begin 1
	 * @param colStr
	 * @param length
	 * @return
	 */
	public static int excelColStrToNum(String colStr, int length) {
		int num = 0;
		int result = 0;
		for(int i = 0; i < length; i++) {
			char ch = colStr.charAt(length - i - 1);
			num = (int)(ch - 'A' + 1) ;
			num *= Math.pow(26, i);
			result += num;
		}
		return result;
	}

	/**
	 * Excel column index begin 1
	 * @param columnIndex
	 * @return
	 */
	public static String excelColIndexToStr(int columnIndex) {
		if (columnIndex <= 0) {
			return null;
		}
		String columnStr = "";
		columnIndex--;
		do {
			if (columnStr.length() > 0) {
				columnIndex--;
			}
			columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
			columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
		} while (columnIndex > 0);
		return columnStr;
	}

	public static String GetGUID()
	{
		return UUID.randomUUID().toString().replace("-", "");
	}


	public static HashMap<String,PageData> getConstantMap(String tabCodeMapString) {
		HashMap<String, PageData> resMap = new HashMap<String, PageData>();
		List<String> constantList = getConstantList(tabCodeMapString);
		for (int i = 0; i < constantList.size(); i++) {
			String[] array = constantList.get(i).split(":");
			PageData tab = new PageData();
			tab.put("name", array[0]);
			tab.put("code", array[1]);
			resMap.put(array[1], tab);
		}
		return resMap;
	}

	public static List<String> getConstantList(String productionReportShowTabString) {
		List<String> resList = new ArrayList<String>();
		String[] array = productionReportShowTabString.replace(" ", "").split(",");
		for (int i = 0; i < array.length; i++) {
			resList.add(array[i]);
		}
		return resList;
	}

	public static JSONObject post(String URLStr, JSONObject params) {
		HttpURLConnection conn = null;
		// 数据输出流，输出参数信息
		DataOutputStream dataOut = null;
		PrintWriter out = null;
		// 数据输入流，处理服务器响应数据
		BufferedReader dataIn = null;

		try {
			URL url = new URL(URLStr);

			// 将url以open方法返回的urlConnection连接强转为HttpURLConnection连接(标识一个url所引用的远程对象连接)
			// 此时cnnection只是为一个连接对象,待连接中
			conn = (HttpURLConnection) url.openConnection();

			// 设置连接输出流为true,默认false (post请求是以流的方式隐式的传递参数)
			conn.setDoOutput(true);
			// 设置连接输入流为true
			conn.setDoInput(true);
			// 设置请求方式为post
			conn.setRequestMethod("POST");
			// post请求缓存设为false
			conn.setUseCaches(false);
			// 设置该HttpURLConnection实例是否自动执行重定向
			conn.setInstanceFollowRedirects(true);

			// 设置内容的类型,设置为经过urlEncoded编码过的from参数
			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			conn.setRequestProperty("accept", "*/*");
			// conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;
			// MSIE 6.0; Windows NT 5.1;SV1)");

			// 建立连接
			// (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
			conn.connect();

			/*// 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
			dataOut = new DataOutputStream(conn.getOutputStream());
			// 将参数输出到连接
			dataOut.writeBytes(params != null ? JSON.toJSONString(params) : "");
			// 输出完成后刷新并关闭流
			dataOut.flush();*/

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			System.out.println("post out print" + JSON.toJSONString(params));
			// 发送请求参数
			out.print(JSON.toJSONString(params));
			// flush输出流的缓冲
			out.flush();

			//输出连接信息，实际使用时去掉
//			outConnInfo(conn, url);

			// 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
			dataIn = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			// 用来存储响应数据
			StringBuilder sb = new StringBuilder();
			// 循环读取流
			while ((line = dataIn.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			}
			System.out.println("login result service: " + sb.toString());
			JSONObject sbObj = JSONObject.parseObject(sb.toString());
			return sbObj;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 重要且易忽略步骤 (关闭流,切记!)
				if (out != null) {
					out.close();
				}
				if (dataOut != null) {
					dataOut.close();
				}
				if (dataIn != null) {
					dataIn.close();
				}
				// 销毁连接
				if (conn != null) {
					conn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static JSONObject get(String httpurl) {
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedReader br = null;
		String result = null;// 返回结果字符串
		JSONObject sbObj = new JSONObject();
		try {
			// 创建远程url连接对象
			URL url = new URL(httpurl);
			// 通过远程url连接对象打开一个连接，强转成httpURLConnection类
			connection = (HttpURLConnection) url.openConnection();
			// 设置连接方式：get
			connection.setRequestMethod("GET");
			// 设置连接主机服务器的超时时间：15000毫秒
			connection.setConnectTimeout(15000);
			// 设置读取远程返回的数据时间：60000毫秒
			connection.setReadTimeout(60000);
			// 发送请求
			connection.connect();
			// 通过connection连接，获取输入流
			if (connection.getResponseCode() == 200) {
				is = connection.getInputStream();
				// 封装输入流is，并指定字符集
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				// 存放数据
				StringBuffer sbf = new StringBuffer();
				String temp = null;
				while ((temp = br.readLine()) != null) {
					sbf.append(temp);
					sbf.append("\r\n");
				}

				result = sbf.toString();
				if (result.startsWith("QZOutputJson=")) {
					result = result.substring(13, result.length() - 3);
				}
				sbObj = JSONObject.parseObject(result);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sbObj;
	}

	private static void outConnInfo(HttpURLConnection conn, URL url) throws IOException {
		// url与url = conn.getURL()是等价的
		System.out.println("conn.getResponseCode():" + conn.getResponseCode());
		System.out.println("conn.getURL():" + conn.getURL());
		System.out.println("conn.getRequestMethod():" + conn.getRequestMethod());
		System.out.println("conn.getContentType():" + conn.getContentType());
		System.out.println("conn.getReadTimeout():" + conn.getReadTimeout());
		System.out.println("conn.getResponseMessage():" + conn.getResponseMessage());
		System.out.println("url.getDefaultPort():" + url.getDefaultPort());
		System.out.println("url.getFile():" + url.getFile());
		System.out.println("url.getHost():" + url.getHost());
		System.out.println("url.getPath():" + url.getPath());
		System.out.println("url.getPort():" + url.getPort());
		System.out.println("url.getProtocol():" + url.getProtocol());
		System.out.println("url.getQuery():" + url.getQuery());
		System.out.println("url.getRef():" + url.getRef());
		System.out.println("url.getUserInfo():" + url.getUserInfo());
	}


}
