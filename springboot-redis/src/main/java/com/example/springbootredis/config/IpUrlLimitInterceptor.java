package com.example.springbootredis.config;

import com.alibaba.fastjson.JSON;
import com.example.springbootredis.util.IpAdrressUtil;
import com.example.springbootredis.util.RedisUtil;
import com.example.springbootredis.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <h3>springboot-study</h3>
 * <p>ip+url重复请求现在拦截器</p>
 *
 * @author : ZhangYuJie
 * @date : 2022-05-29 17:29
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class IpUrlLimitInterceptor implements HandlerInterceptor {
    private final RedisUtil redisUtil;

    private static final String LOCK_IP_URL_KEY = "lock_ip_";

    private static final String IP_URL_REQ_TIME = "ip_url_times_";

    private static final long LIMIT_TIMES = 5;

    private static final String IP_LOCK_TIME = "60";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("request请求地址uri={},ip={}", httpServletRequest.getRequestURI(), IpAdrressUtil.getIpAddr(httpServletRequest));
        if (ipIsLock(IpAdrressUtil.getIpAddr(httpServletRequest))) {
            log.info("ip访问被禁止={}", IpAdrressUtil.getIpAddr(httpServletRequest));
            returnJson(httpServletResponse, JSON.toJSONString(Result.success("ip访问被禁止")));
            return false;
        }
        if (!addRequestTime(IpAdrressUtil.getIpAddr(httpServletRequest), httpServletRequest.getRequestURI())) {
            returnJson(httpServletResponse, JSON.toJSONString(Result.success("ip访问被禁止")));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 判断ip是否被禁用
     *
     * @param ip
     * @return
     */
    private Boolean ipIsLock(String ip) {
        if (redisUtil.hasKey(LOCK_IP_URL_KEY + ip)) {
            return true;
        }
        return false;
    }

    /**
     * 记录请求次数
     *
     * @param ip
     * @param uri
     * @return
     */
    private Boolean addRequestTime(String ip, String uri) {
        String key = IP_URL_REQ_TIME + ip + uri;
        if (redisUtil.hasKey(key)) {
            // 如果key存在，次数+1
            long time = redisUtil.incr(key, (long) 1);
            log.info("time:{}", time);
            if (time >= LIMIT_TIMES) {
                // 如果超过限制次数，则设置ip被禁用 60秒
                redisUtil.getLock(LOCK_IP_URL_KEY + ip, ip, IP_LOCK_TIME);
                return false;
            }
        } else {
            // ip+uri请求次数为1，1秒后过期
            redisUtil.getLock(key, "1", "1");
            log.info("记录请求次数1");
        }
        return true;
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            log.error("LoginInterceptor response error ---> {}", e.getMessage(), e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
