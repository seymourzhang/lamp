package com.on.util.common;

import org.slf4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
	private static Logger mLogger = LoggerFactory.getLogger(Tools.class);
	
	/**
	 * 随机生成六位数验证码 
	 * @return
	 */
	public static int getRandomNum(){
		int rt = 100000;
		Long l = System.currentTimeMillis();
		char[] c = l.toString().toCharArray();
		String s = "";
		for(int i=c.length ; (i-1)>=0 ;i--){
			s += c[i-1];
		}
		s = s.substring(0,6);
		rt = Integer.valueOf(s);
//		 Random r = new Random();
//		 return r.nextInt(900000)+100000;//(Math.random()*(999999-100000)+100000)
		return rt;
	}
	
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	
	/**
	 * 字符串转换为字符串数组
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}
	
	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * @param str	字符串
	 * @return
	 */
	public static String[] str2StrArray(String str){
		return str2StrArray(str,",\\s*");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date){
		return date2Str(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date){
		if(notEmpty(date)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		}else{
			return null;
		}
	}
	
	/**
	 * 按照参数format的格式，日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date,String format){
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 把时间根据时、分、秒转换为时间段
	 * @param StrDate
	 */
	public static String getTimes(String StrDate){
		String resultTimes = "";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date now;

	    try {
	    	now = new Date();
	    	Date date=df.parse(StrDate);
	    	long times = now.getTime()-date.getTime();
	    	long day  =  times/(24*60*60*1000);
	    	long hour = (times/(60*60*1000)-day*24);
	    	long min  = ((times/(60*1000))-day*24*60-hour*60);
	    	long sec  = (times/1000-day*24*60*60-hour*60*60-min*60);

	    	StringBuffer sb = new StringBuffer();
	    	//sb.append("发表于：");
	    	if(hour>0 ){
	    		sb.append(hour+"小时前");
	    	} else if(min>0){
	    		sb.append(min+"分钟前");
	    	} else{
	    		sb.append(sec+"秒前");
	    	}

	    	resultTimes = sb.toString();
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }

	    return resultTimes;
	}

	/**
	 * 写txt里的单行内容
	 * @param content  写入的内容
	 */
	public static void writeFile(String fileP,String content){
		String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
		filePath = (filePath.trim() + fileP.trim()).substring(6).trim();
		if(filePath.indexOf(":") != 1){
			filePath = File.separator + filePath;
		}
		try {
	        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath),"utf-8");
	        BufferedWriter writer=new BufferedWriter(write);
	        writer.write(content);
	        writer.close();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	  * 验证邮箱
	  * @param email
	  * @return
	  */
	 public static boolean checkEmail(String email){
	  boolean flag = false;
	  try{
	    String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    Pattern regex = Pattern.compile(check);
	    Matcher matcher = regex.matcher(email);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }

	 /**
	  * 验证手机号码
	  * @return
	  */
	 public static boolean checkMobileNumber(String mobileNumber){
	  boolean flag = false;
	  try{
	    Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
	    Matcher matcher = regex.matcher(mobileNumber);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }

	/**
	 * 检测KEY是否正确
	 * @param paraname  传入参数
	 * @param FKEY		接收的 KEY
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean checkKey(String paraname, String FKEY){
		paraname = (null == paraname)? "":paraname;
		return MD5.md5(paraname+DateUtil.getDays()+",fh,").equals(FKEY);
	}

	/**
	 * 读取txt里的单行内容
	 */
	public static String readTxtFile(String fileP) {
		try {

			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();
			if(filePath.indexOf(":") != 1){
				filePath = File.separator + filePath;
			}
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 		// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), encoding);	// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					return lineTxt;
				}
				read.close();
			}else{
//				System.out.println("找不到指定的文件,查看此路径是否正确:"+filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return "";
	}

	/**
	 * 删除Html标签
	 *
	 * @param inputString
	 * @return
	 */
	public static String htmlRemoveTag(String inputString) {
	    if (inputString == null)
	        return null;
	    String htmlStr = inputString; // 含html标签的字符串
	    String textStr = "";
	    Pattern p_script;
	    Matcher m_script;
	    Pattern p_style;
	    Matcher m_style;
	    Pattern p_html;
	    Matcher m_html;
	    try {
	        //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
	        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; 
	        //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
	        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; 
	        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	        p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
	        m_script = p_script.matcher(htmlStr);
	        htmlStr = m_script.replaceAll(""); // 过滤script标签
	        p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
	        m_style = p_style.matcher(htmlStr);
	        htmlStr = m_style.replaceAll(""); // 过滤style标签
	        p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
	        m_html = p_html.matcher(htmlStr);
	        htmlStr = m_html.replaceAll(""); // 过滤html标签
	        textStr = htmlStr;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return textStr;// 返回文本字符串
	}

	/**
	 * 复制文件
	 * @param in
	 * @param out
	 * @return
	 * @throws Exception
	 */
	public static long copyFile(FileInputStream in , FileOutputStream out) throws Exception{
		long time=new Date().getTime();
		int length=2097152;
		FileChannel inC=in.getChannel();
		FileChannel outC=out.getChannel();
		ByteBuffer b=null;
		while(true){
			if(inC.position()==inC.size()){
				inC.close();
				outC.close();
				return new Date().getTime()-time;
			}
			if((inC.size()-inC.position())<length){
				length=(int)(inC.size()-inC.position());
			}else
				length=2097152;
			b=ByteBuffer.allocateDirect(length);
			inC.read(b);
			b.flip();
			outC.write(b);
			outC.force(false);
		}
	}

	public static String toSafeString(Object obj){
		String rt = (String)obj;
		StringUtils.replaceEach(rt, new String[] { "\n", "\r","%0d", "%0D", "%0a", "%0A" }, new String[] { "", "", "", "", "", "" });
		return rt;
	}

	public static String checkFilePath(String path){
		Boolean flag = false;
		String tmpPath = path;
		if(tmpPath.indexOf(":")<0){
			tmpPath = "c:/" + tmpPath;
		}
		tmpPath = tmpPath.replace("/","\\").trim();
//		String regex = "[a-zA-Z]:(?:[/\\\\][^/\\\\:*?\"<>|]{1,255})+";
		String regex = "^[a-zA-Z]:\\\\[^/:*?\"<>|]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(tmpPath);
		flag = matcher.matches();
		return (flag.booleanValue())?path:null;
	}

	public static String getOrderIdByUUId() {
		int machineId = 1;//最大支持1-9个集群机器部署
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if(hashCodeV < 0) {//有可能是负数
			hashCodeV = - hashCodeV;
		}
		// 0 代表前面补充0
		// 4 代表长度为4
		// d 代表参数为正数型
		return machineId + String.format("%015d", hashCodeV);
	}

//	public static void main(String[] args) {
//		System.out.println(getRandomNum());
//	}

	/**
	 *
	 * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
	 * 实现步骤: <br>
	 *
	 * @param paraMap   要排序的Map对象
	 * @param urlEncode   是否需要URLENCODE
	 * @param keyToLower    是否需要将Key转换为全小写
	 *            true:key转化成小写，false:不转化
	 * @return
	 */
     public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
		 String buff = "";
		 Map<String, String> tmpMap = paraMap;
		 try {
			 List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
			 // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			 Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
				 @Override
				 public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
					 return (o1.getKey()).toString().compareTo(o2.getKey());
				 }
			 });
			 // 构造URL 键值对的格式
			 StringBuilder buf = new StringBuilder();
			 for (Map.Entry<String, String> item : infoIds) {
				 if (StringUtils.isNotBlank(item.getKey())) {
					 String key = item.getKey();
					 String val = item.getValue();
					 if (urlEncode) {
						 val = URLEncoder.encode(val, "utf-8");
					 }
					 if (keyToLower) {
						 buf.append(key.toLowerCase() + "=" + val);
					 } else {
						 buf.append(key + "=" + val);
					 }
					 buf.append("&");
				 }
			 }
			 buff = buf.toString();
			 if (buff.isEmpty() == false) {
				 buff = buff.substring(0, buff.length() - 1);
			 }
		 } catch (Exception e) {
			 return null;
		 }
		 return buff;
	 }

	/**
	 * 方法名: getRemotePortData
	 * 描述: 发送远程请求 获得代码示例
	 * 参数：  @param urls 访问路径
	 * 参数：  @param param 访问参数-字符串拼接格式, 例：port_d=10002&port_g=10007&country_a=
	 * 创建人: Xia ZhengWei
	 * 创建时间: 2017年3月6日 下午3:20:32
	 * 版本号: v1.0
	 * 返回类型: String
	 */
     public static String getRemotePortData(String urls, String param) {
		 mLogger.info("港距查询抓取数据----开始抓取外网港距数据");
		 try {
			 URL url = new URL(urls);
			 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			 // 设置连接超时时间
			 conn.setConnectTimeout(30000);
			 // 设置读取超时时间
			 conn.setReadTimeout(30000);
			 conn.setRequestMethod("POST");
			 if (param != null && !"".equals(param)) {
//				 conn.setRequestProperty("Origin", "https://sirius.searates.com");// 主要参数
//				 conn.setRequestProperty("Referer", "https://sirius.searates.com/cn/port?A=ChIJP1j2OhRahjURNsllbOuKc3Y&D=567&G=16959&shipment=1&container=20st&weight=1&product=0&request=&weightcargo=1&");
				 conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");// 主要参数
			 }
			 // 需要输出
			 conn.setDoInput(true);
			 // 需要输入
			 conn.setDoOutput(true);
			 // 设置是否使用缓存
			 conn.setUseCaches(false);
			 // 设置请求属性
			 conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			 conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			 conn.setRequestProperty("Charset", "UTF-8");

			 if (param != null && !"".equals(param)) {
				 // 建立输入流，向指向的URL传入参数
				 DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
				 dos.writeBytes(param);
				 dos.flush();
				 dos.close();
			 }
			 // 输出返回结果
			 InputStream input = conn.getInputStream();
			 int resLen = 0;
			 byte[] res = new byte[1024];
			 StringBuilder sb = new StringBuilder();
			 while ((resLen = input.read(res)) != -1) {
				 sb.append(new String(res, 0, resLen));
			 }
			 System.out.println("sb:" + sb);
			 return sb.toString();
		 } catch (MalformedURLException e) {
			 e.printStackTrace();
			 mLogger.info("港距查询抓取数据----抓取外网港距数据发生异常：" + e.getMessage());
		 } catch (IOException e) {
			 e.printStackTrace();
			 mLogger.info("港距查询抓取数据----抓取外网港距数据发生异常：" + e.getMessage());
		 }
		 mLogger.info("港距查询抓取数据----抓取外网港距数据失败, 返回空字符串");
		 return "";
	 }

     /**
      * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
      * @param strxml
      * @return
      * @throws
      * @throws IOException
      */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> doXMLParse(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		Map<String, String> m = new HashMap<String, String>();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			m.put(k, v);
		}
		//关闭流
		in.close();
		return m;
	}

	private static InputStream String2Inputstream(String str) {
	   return new ByteArrayInputStream(str.getBytes());
	}

	/**
	* 获取子结点的xml
	* @param children
	* @return String
	*/
	@SuppressWarnings("rawtypes")
	private static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
		   Iterator it = children.iterator();
		   while(it.hasNext()) {
				Element e = (Element) it.next();
			    String name = e.getName();
			    String value = e.getTextNormalize();
			    List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					 sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}
}