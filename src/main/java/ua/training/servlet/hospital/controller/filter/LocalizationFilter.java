package ua.training.servlet.hospital.controller.filter;


import sun.util.locale.LocaleUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import static java.util.Objects.nonNull;

@WebFilter(filterName = "LocaleFilter")
public class LocalizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =  (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Locale locale;

        if(nonNull(request.getParameter("lang"))){
            locale = Locale.forLanguageTag(request.getParameter("lang"));
            Cookie cookie = new Cookie("lang",locale.toLanguageTag());
            response.addCookie(cookie);
        } else{
            String localeCode = readCookie("lang",request).orElse("en-US");
            locale = Locale.forLanguageTag(localeCode);
        }
        response.setLocale(locale);

        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }

    private Optional<String> readCookie(String key, HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(c -> key.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }
}
