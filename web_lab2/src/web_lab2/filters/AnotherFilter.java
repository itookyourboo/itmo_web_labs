package web_lab2.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnotherFilter implements Filter {

    public AnotherFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AnotherFilter init!!");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletResponse res = (HttpServletResponse) servletResponse;
        System.out.printf("ANO RES %s\n", res.getHeaderNames());
        res.addHeader("NewHeader", "SomeValue");
        System.out.println("ANO RES Added NewHeader to the response");
    }

    public void destroy() {
        System.out.println("SimpleFilter destroy!");
    }
}
