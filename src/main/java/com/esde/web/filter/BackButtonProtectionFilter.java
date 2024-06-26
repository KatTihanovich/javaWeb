package com.esde.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class BackButtonProtectionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.addHeader("Cache-Control", "post-check=0, pre-check=0");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setDateHeader("Expires", 0);

        chain.doFilter(request, response);
    }
}