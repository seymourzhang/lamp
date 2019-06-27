package com.on.wechat.filter;

import com.on.util.common.PageData;
import com.on.util.common.PubFun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/cart/*", filterName = "loginFilter")
public class LoginFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void doFilterInternal (HttpServletRequest var1, HttpServletResponse var2, FilterChain var3) throws IOException, ServletException {
        logger.info("filter doFilter here ==================");
//        String jsonString = PubFun.getRequestPara(var1);
        var3.doFilter(var1, var2);
    }

}
