package web_lab2.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class LogFilter implements Filter {

    public LogFilter() {}

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LogFilter init!!");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        System.out.printf("LOG REQ %s - %s, URL: %s\n",
                new Date(), req.getServletPath(), req.getRequestURL());
//        System.out.printf("LOG REQ %s\n", Collections.list(req.getHeaderNames()));

        filterChain.doFilter(servletRequest, servletResponse);

        System.out.printf("LOG RES %s\n", res.getHeaderNames());
    }

    public void destroy() {
        System.out.println("SimpleFilter destroy!");
    }
}
