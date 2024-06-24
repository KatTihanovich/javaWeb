package com.esde.web.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;


public class GreetUserTag extends TagSupport {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public int doStartTag() {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String username = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                    break;
                }
            }
        }

        JspWriter out = pageContext.getOut();
        try {
            out.print(Objects.requireNonNullElse(username, "Guest"));
        } catch (IOException e) {
            logger.error(e);
        }
        return SKIP_BODY;
    }
}