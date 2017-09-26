package com.murun.fict.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationCorsFilter implements Filter {

    private static final String PROPERTY_NAME_ALLOWED_ORIGINS = "fict.allowed_origins";

    private List<String>  allowedOrigins;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    private Environment env;

    public ApplicationCorsFilter() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        if ( allowedOrigins == null || !allowedOrigins.contains(request.getHeader("origin"))) {
            chain.doFilter(req, res);
            return;
        }
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST,PUT,GET,DELETE,HEAD,OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Charset, Cache-Control, Authorization, Accept");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        if ( env.getRequiredProperty(PROPERTY_NAME_ALLOWED_ORIGINS) == null ) {
            return;
        }
        allowedOrigins = Arrays.asList(env.getRequiredProperty(PROPERTY_NAME_ALLOWED_ORIGINS).split( " "));
    }

    @Override
    public void destroy() {
    }
}