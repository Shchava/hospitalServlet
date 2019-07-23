package ua.training.servlet.hospital.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String path = (request).getRequestURI();
        if (path.startsWith("/login")) {
            filterChain.doFilter(request, response);
        }else {
            HttpSession session = request.getSession();
            if (nonNull(session) && nonNull(session.getAttribute("LoggedUser"))) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                servletRequest.setAttribute("requestedUrl",path);
                servletRequest.getRequestDispatcher("/login.jsp").forward(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy(){

    }
}
