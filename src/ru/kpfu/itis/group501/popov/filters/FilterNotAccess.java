package ru.kpfu.itis.group501.popov.filters;

import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.CustomRepository;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "FilterCookieLogin")
public class FilterNotAccess implements Filter {
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
        int user_id = CustomRepository.get_user_cookie(value);
        if ((((HttpServletRequest)req).getSession().getAttribute("current_user") == null)) {
            if (flag && user_id != -1) {
                List list = CustomRepository.getBy(User.class, "id", user_id);
                if (list.size() != 0) {
                    User user = (User) list.get(0);
                    ((HttpServletRequest) req).getSession().setAttribute("current_user", user);
                    ((HttpServletResponse) resp).sendRedirect("/profile");
                }
                else {
                    chain.doFilter(req, resp);
                }
            }
            else {
                chain.doFilter(req, resp);
            }
        }
        else {
            ((HttpServletResponse)resp).sendRedirect("/profile");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
