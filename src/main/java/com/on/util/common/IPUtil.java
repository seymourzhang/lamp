/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.on.util.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Set;

/**
* @ClassName: IPUtil
* @Description: 
* @Date 2015年9月10日 上午10:48:33
* @Author Yin Guo Xiang
* 
*/ 
public class IPUtil {
	private static Logger mLogger = LoggerFactory.getLogger(IPUtil.class);

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
    
    /**
     * 获取本机Ip
     * @return
     */
    public static String getCurIp(){
    	String curIp = null;
    	InetAddress addr;
    	try {
	    	Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
	    	InetAddress ip = null;
	    	while (allNetInterfaces.hasMoreElements())
	    	{
		    	NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
//		    	mLogger.debug("netInterface.Name==" + netInterface.getName());
		    	Enumeration<InetAddress> address = netInterface.getInetAddresses();
		    	while (address.hasMoreElements())
		    	{
		    		ip = address.nextElement(); 
//		    		mLogger.debug("ip.isSiteLocalAddress==" + ip.isSiteLocalAddress());
//		    		mLogger.debug("ip.isLoopbackAddress==" + ip.isLoopbackAddress());
//		    		mLogger.debug("ip.isAnyLocalAddress==" + ip.isAnyLocalAddress());
			    	if (ip != null 
			    			&& ip instanceof Inet4Address
			    			&& !ip.isLoopbackAddress()
			    			&& ip.isSiteLocalAddress()
			    			&& ip.getHostAddress().indexOf(":") == -1
			    			)
			    	{
//			    		mLogger.debug("InetAddress.HostName==" + ip.getHostName());
			    		curIp =  ip.getHostAddress() ;
			    	} 
		    	}
	    	}
			mLogger.info("LocalIp==" + curIp);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return curIp;
    }

	/**
	 * 获取当前应用端口
	 * @return
	 */
	public static String getTomcatPort() throws MalformedObjectNameException, NullPointerException {
		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
				Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
		String port = objectNames.iterator().next().getKeyProperty("port");

		return port;
	}
    
    /**
     * 判断是否需要运行批处理任务
     * @return
     */
    public static boolean needRunTask(){
    	String proIp = "", proPort = "";
    	try {
			proIp = PubFun.getPropertyValue("Pub.RunTaskIp");
			proPort = PubFun.getPropertyValue("Pub.RunTashPort");
			mLogger.info("proIp==" + proIp + ":" + proPort);
			mLogger.info("curIp==" + IPUtil.getCurIp());
			mLogger.info("curPort==" + IPUtil.getTomcatPort());
			if (proIp.equals(IPUtil.getCurIp()) && proPort.equals(IPUtil.getTomcatPort())) {
				return false;
			} else {
				mLogger.info("该服务器不需要跑批处理任务。。。。");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
    }
    
//    public static void main(String[] args) {
//		IPUtil.getCurIp();
//	}
}
