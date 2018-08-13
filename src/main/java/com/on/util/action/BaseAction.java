package com.on.util.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.api.Session;
import com.on.util.common.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@SpringBootApplication
@RequestMapping("baseController")
public class BaseAction {


	@Autowired
	private ResourceLoader resourceLoader;

	protected Logger logger = Logger.getLogger(this.getClass());

	static final String KEY_SIGN = "__sign__";
	static final String KEY_PARAM = "param";
	static final String KEY_SIGN_SPLIT = "#&!:!&#";

	/**
	 * 得到PageData
	 */
	public PageData getPageData() throws Exception{
		PageData pd = new PageData();
		String methodMode = this.getRequest().getMethod();
		if ("POST".equals(methodMode)) {

			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) this.getRequest().getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			String params = sb.toString();
			JSONObject jParams = JSONObject.parseObject(params);
			pd.putAll(jParams);
		} else if ("GET".equals(methodMode)) {
			pd = new PageData(this.getRequest());
		}
		return pd;
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		return request;
	}
	

	/**
	 * 得到分页列表的信息 
	 */
	public Page getPage(){
		
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	

	public static void writeJson(Object object,HttpServletResponse res, HttpServletRequest req) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			json = StringEscapeUtils.unescapeJava(json);
			json = json.replaceAll("\":\"\\{\"","\":\\{\"");
			json = json.replaceAll("\"\\}\"\\,\"","\"\\}\\,\"");

			res.setHeader("Access-Control-Allow-Origin", "*");
			res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			res.setHeader("Access-Control-Allow-Headers", "x-requested-with");

			res.setContentType("text/html;charset=utf-8");
			res.getWriter().write(json);
			res.getWriter().flush();
			res.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeJson(Object object,HttpServletResponse res) {
		writeJson(object, res, null);
	}
	//获取IP地址
	public String getIpAddr(HttpServletRequest request) {
	      String ip = request.getHeader("x-forwarded-for");  
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	         ip = request.getHeader("Proxy-Client-IP");  
	     }  
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	         ip = request.getHeader("WL-Proxy-Client-IP");  
	      }  
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	          ip = request.getRemoteAddr();  
	     }  
	     return ip;  
	}

	/**
	 * 全局下载功能
	 *
	 * @param response
	 * @param request
	 * @param session
	 * @throws Exception
     */
	@RequestMapping("/download")
	public void download(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {

		String fileName = String.valueOf(session.getAttribute("fileName"));
		String dir = request.getSession().getServletContext().getRealPath("")+"modules/file/download/" + fileName;
		dir = org.apache.commons.lang.StringUtils.replace(org.apache.commons.lang.StringUtils.replace(dir, "\\\\", "\\"), "\\]", "]");
		InputStream fis = new FileInputStream(new File(dir));

		response.reset();
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1"));

		FileCopyUtils.copy(fis, response.getOutputStream());//spring工具类直接流拷贝
		fis.close();
	}
	/**
	 * 上传文件
	 */
	public String uploadFile(HttpServletRequest request, MultipartFile myfile) {
		if (null != myfile && !myfile.isEmpty()) {
			Date now = new Date();
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy_MM");   
			String dateUrl=dateformat.format(now);
			// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
			String realPath = request.getSession().getServletContext()
					.getRealPath("/upload/fileUpload/"+dateUrl);
			File file=new File(realPath);
			if(!file.exists()){
				file.mkdir();
			}
			// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
			try {
				FileUtils.copyInputStreamToFile(myfile.getInputStream(),
						new File(realPath,now.getTime()+ myfile.getOriginalFilename()));
				
				return "/upload/fileUpload/"+dateUrl+"/"+now.getTime()+ myfile.getOriginalFilename();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 获取用户ID
	 * @return
	 *
	protected int getUserId(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		SDUser user=(SDUser)session.getAttribute(Const.SESSION_USER);
		return user.getId();
	}
	*/

	/**
	 * 获取用户权限
	 *
	protected PageData getUserRights(PageData pd, HttpSession session,HttpServletResponse response,HttpServletRequest request){
		Subject currentUser = SecurityUtils.getSubject();
		Session s = currentUser.getSession();
		SDUser user=(SDUser)s.getAttribute(Const.SESSION_USER);

		String spi = pd.getString("write_read");
		if(!StringUtils.isBlank(spi)){
			if(spi.matches("[0-9]+") && null != user.getId()) {
				session.setAttribute("write_read", ESAPI.encoder().encodeForHTML(spi));
			}
		}else{
			pd.put("write_read", session.getAttribute("write_read"));
		}
		return pd;
	}*/




	@Autowired
	CookieLocaleResolver resolver;

	//@Autowired SessionLocaleResolver resolver;

	/**
	 * 语言切换
	 */
	@RequestMapping("language")
	public ModelAndView language(HttpServletRequest request, HttpServletResponse response, String language){

		language=language.toLowerCase();
		String language2="";
		if(language==null||language.equals("")){
			return new ModelAndView("redirect:/baseController/toLogin.action?cl=1&language=en");
		}else{
			if(language.equals("zh_cn")){
				resolver.setLocale(request, response, Locale.CHINA );
				language2="en";
			}else if(language.equals("en")){
				resolver.setLocale(request, response, Locale.ENGLISH );
				language2="zh_cn";
			}else{
				resolver.setLocale(request, response, Locale.CHINA );
				language2="en";
			}
		}

		return new ModelAndView("redirect:/baseController/toLogin.action?cl=1&language="+language2);
	}

	@ResponseBody
	@RequestMapping(value = "/message/**.properties")
	public void messageFile(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
		String result = "param:" + request.getQueryString();
		String path = request.getRequestURL().toString();
		String fileName = path.substring(path.lastIndexOf("/"));
//		String dir = request.getSession().getServletContext().getRealPath("")+"WEB-INF/classes/spring/message" + fileName;
//		String dir = session.getServletContext().getRealPath("")+"WEB-INF/classes/spring/message" + fileName;
		File file = resourceLoader.getResource(Tools.checkFilePath("WEB-INF/classes/spring/message" + fileName)).getFile();
//		dir = org.apache.commons.lang.StringUtils.replace(org.apache.commons.lang.StringUtils.replace(dir, "\\\\", "\\"), "\\]", "]");
//		InputStream fis = new FileInputStream(new File(dir));
		InputStream fis = new FileInputStream(file);


		response.reset();
		response.setContentType("text/plain;charset=UTF-8");
	//		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1"));

		FileCopyUtils.copy(fis, response.getOutputStream());//spring工具类直接流拷贝
		fis.close();
	}
	@RequestMapping("/toLogin")
	public ModelAndView toLogin(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
		String cl = request.getParameter("cl");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		if(cl != null && cl.equals("1")){
			pd.put("language2",request.getParameter("language"));
		}else{
			String language = PubFun.getPropertyValue("default.language");
			language=language.toLowerCase();
			if(language==null||language.equals("")){
				return new ModelAndView("redirect:/");
			}else{
				if(language.equals("zh_cn")){
					resolver.setLocale(request, response, Locale.CHINA );
				}else if(language.equals("en")){
					resolver.setLocale(request, response, Locale.ENGLISH );
				}else{
					resolver.setLocale(request, response, Locale.CHINA );
				}
			}
		}

		mv.setViewName("modules/user/login");
		mv.addObject("pd",pd);
		return mv;
	}
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		String cl = request.getParameter("cl");
		if(cl != null && cl.equals("1")){

		}else{
			String language = PubFun.getPropertyValue("default.language");
			language=language.toLowerCase();
			if(language==null||language.equals("")){
				return new ModelAndView("redirect:/");
			}else{
				if(language.equals("zh_cn")){
					resolver.setLocale(request, response, Locale.CHINA );
				}else if(language.equals("en")){
					resolver.setLocale(request, response, Locale.ENGLISH );
				}else{
					resolver.setLocale(request, response, Locale.CHINA );
				}
			}
		}

		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("modules/user/login");
		mv.addObject("pd",pd);
		return mv;
	}


	/**
	 * <p>Desc: 获取Param对象</p>
	 * <p>Date: 27/03/2018</p>
	 * <p>Author: Raymon</p>
	 * @param
	 * @return org.json.JSONObject
	 */
	public static PageData getParam(String paramStr) {
		com.alibaba.fastjson.JSONObject jo = com.alibaba.fastjson.JSONObject.parseObject(paramStr);
		PageData pd = new PageData();
		if ( null != jo ) {
			for( String key : jo.keySet() ) {
				pd.put(key, jo.getString(key));
			}
		} else {
			pd = null;
		}
		return pd;
	}

	/**
	 * <p>Desc: 获取Param对象</p>
	 * <p>Date: 27/03/2018</p>
	 * <p>Author: Raymon</p>
	 * @param
	 * @return com.nh.ifarm.util.common.PageData
	 */
	public static PageData getParam(HttpServletRequest request) {
		return getParam(getParamStr(request));
	}

	/**
	 * <p>Desc: 获取Param对象字符串</p>
	 * <p>Date: 27/03/2018</p>
	 * <p>Author: Raymon</p>
	 * @param
	 * @return java.lang.String
	 */
	public static String getParamStr(HttpServletRequest request) {
		PageData pd = new PageData(request);
		String rt =  pd.getString(KEY_PARAM);
		return rt;
	}

	/**
	 * <p>Desc: </p>
	 * <p>Date: 27/03/2018</p>
	 * <p>Author: Raymon</p>
	 * @param request
	 * @return java.util.List<java.lang.String>
	 */
	public static List<String> getHeader(HttpServletRequest request) {
		List<String> rtList = null;
		String header = request.getHeader(KEY_SIGN);
		if ( null != header) {
			String[] rtArrays = request.getHeader(KEY_SIGN).split(KEY_SIGN_SPLIT);
			rtList = Arrays.asList(rtArrays);
		}
		return rtList;
	}
	
	/** 
	* <p>Desc: 设置头</p>
	* <p>Date: 27/03/2018</p>         
	* <p>Author: Raymon</p>
	* @param response, sign
	* @return void
	*/
	public static void setHeader(HttpServletResponse response, String sign) {
		response.addHeader(KEY_SIGN, sign);
	}
	

}
