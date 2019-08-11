package ua.training.servlet.hospital.controller.filter;

import ua.training.servlet.hospital.controller.utilities.SecurityUtility;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter(filterName = "authFilter")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        if (SecurityUtility.hasAccess(request)) {
            giveAccess(filterChain,request,response);
        }else{
            denyAccess(request,response);
        }
    }

    private void giveAccess(FilterChain filterChain, HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void denyAccess(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        servletRequest.setAttribute("requestedUrl",servletRequest.getRequestURI());
        servletRequest.getRequestDispatcher("/login.jsp").forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy(){

    }
}
