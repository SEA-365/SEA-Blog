package com.sea.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 获取ip地址的工具类
 * @author: sea
 * @date: 2023/12/18 20:32
 */
public class IpUtil {
    //日志打印
    public static final Logger log = LoggerFactory.getLogger(IpUtil.class);

    public static final String TAG = "IpUtil ====> ";

    /**
     * 获取ip地址
     * @param httpServletRequest http请求
     * @return ip地址
     */
    public static String getIpAddr(HttpServletRequest httpServletRequest){
        if(httpServletRequest == null)
            return "";
        Subject subject = SecurityUtils.getSubject();
        String ip = subject.getSession().getHost();
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip) || ip.length() == 0) {
            ip = httpServletRequest.getHeader("X-Forwarded-For");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip) || ip.length() == 0) {
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip) || ip.length() == 0) {
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip) || ip.length() == 0) {
            ip = httpServletRequest.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip) || ip.length() == 0) {
            ip = httpServletRequest.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip) || ip.length() == 0) {
            ip = httpServletRequest.getRemoteAddr();
        }

        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip) || ip.length() == 0) {
            ip = httpServletRequest.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                //根据网卡获取本机配置的ip
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error(TAG + " getIpAddress exception: ", e);
                }
                assert inet != null;
                ip = inet.getHostAddress();
                log.info(TAG + "getIp() => ip: " + ip);
            }
        }

        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }

        String xRealIp = httpServletRequest.getHeader("Origin");
        if (StrUtil.isNotBlank(xRealIp) && !"unknown".equalsIgnoreCase(xRealIp)) {
            ip = xRealIp.split(":")[1].substring(2);
        }
        if("localhost".equals(ip)){
            ip = "127.0.0.1";
        }
        log.info(TAG + "getIp() => return ip: " + ip);
        return ip;
    }

    /**
     * 通过IP获取地址
     *
     * @param ip
     * @return ip地址信息
     */
    public static String getIpInfo(String ip) {
        log.info(TAG + " ip: " + ip);
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        String info = null;
        try {
            URL url = new URL("http://opendata.baidu.com/api.php?query=" + ip + "&co=&resource_id=6006&oe=utf8");
            log.info(TAG + " url: " + url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            while ((info = reader.readLine()) != null) {
                result.append(info);
            }
            reader.close();
            Map map = JSON.parseObject(result.toString(), Map.class);
            List<Map<String, String>> data = (List) map.get("data");
            return data.get(0).get("location");
        } catch (Exception e) {
            log.error(TAG + " getIpInfo exception: ", e);
            return "";
        }
    }
}


