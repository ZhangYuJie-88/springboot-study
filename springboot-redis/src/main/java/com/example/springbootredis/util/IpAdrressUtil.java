package com.example.springbootredis.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <h3>springboot-study</h3>
 * <p>获取IP地址的工具类</p>
 *
 * @author : ZhangYuJie
 * @date : 2022-05-29 17:42
 **/

public class IpAdrressUtil {
    /**
     * 获取IP地址
     */
    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                }
                if (inet.getHostAddress() != null) {
                    ipAddress= inet.getHostAddress();
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

}
