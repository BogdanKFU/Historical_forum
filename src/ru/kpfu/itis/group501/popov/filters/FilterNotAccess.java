package ru.kpfu.itis.group501.popov.filters;

import ru.kpfu.itis.group501.popov.models.CustomCookie;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.Repository;
import ru.kpfu.itis.group501.popov.repository.custom.CustomRepository;
import ru.kpfu.itis.group501.popov.repository.custom.CustomStatement;
import ru.kpfu.itis.group501.popov.singletons.RepositorySingleton;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebFilter(filterName = "FilterCookieLogin")
public class FilterNotAccess implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        Cookie[] cookies = ((HttpServletRequest)req).getCookies();
        Repository repository = RepositorySingleton.getRepository();
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
        CustomStatement cs = new CustomStatement();
        Map map = repository.do_select(cs.selectBy(CustomCookie.class, "cookie", value));
        List list1 = (List) map.get("CustomCookie");
        int user_id = -1;
        if (list1.size() != 0) {
            CustomCookie cookie = (CustomCookie) list1.get(0);
            user_id = (int) cookie.get("id_user");
        }
        if ((((HttpServletRequest)req).getSession().getAttribute("current_user") == null)) {
            if (flag && user_id != -1) {
                CustomStatement cs1 = new CustomStatement();
                Map map1 = repository.do_select(cs.selectBy(User.class, "id", user_id));
                List list = (List) map1.get("User");
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
