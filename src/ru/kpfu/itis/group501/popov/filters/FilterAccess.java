package ru.kpfu.itis.group501.popov.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "FilterCookie")
public class FilterAccess implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        Cookie[] cookies = ((HttpServletRequest)req).getCookies();
        boolean flag = false;
        String value = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                flag = cookie.getName().equals("current_user");
                if (flag) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        Object current_user = (((HttpServletRequest)req).getSession().getAttribute("current_user"));
        if (flag || current_user != null) {
                chain.doFilter(req, resp);
        }
        else {
            ((HttpServletResponse)resp).sendRedirect("/auth/login");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}