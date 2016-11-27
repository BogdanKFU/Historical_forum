package ru.kpfu.itis.group501.popov.filters;

import ru.kpfu.itis.group501.popov.models.Role;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.models.UsersRoles;
import ru.kpfu.itis.group501.popov.repository.Repository;
import ru.kpfu.itis.group501.popov.repository.custom.CustomStatement;
import ru.kpfu.itis.group501.popov.singletons.RepositorySingleton;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@WebFilter(filterName = "FilterAdmin")
public class FilterAdmin implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        Role role = (Role) ((HttpServletRequest) req).getSession().getAttribute("role");
        if (role.get("name").equals("admin")) {
            chain.doFilter(req, resp);
        }
        else {
            ((HttpServletResponse)resp).sendRedirect("/profile");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
