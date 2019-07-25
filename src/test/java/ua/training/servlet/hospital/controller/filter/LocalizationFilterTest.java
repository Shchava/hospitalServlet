package ua.training.servlet.hospital.controller.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

public class LocalizationFilterTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    FilterChain chain;
    @Captor
    ArgumentCaptor<Cookie> cookieCaptor;

    @InjectMocks
    LocalizationFilter localization = new LocalizationFilter();

    private Cookie[] cookies = {new Cookie("lang","uk-UA")};

    @Before
    public void setUp(){
        initMocks(this);
        when(request.getSession()).thenReturn(session);
        when(request.getCookies()).thenReturn(new Cookie[]{});
    }

    @Test
    public void testCookieLocaleSetting() throws IOException, ServletException {
        when(request.getCookies()).thenReturn(cookies);
        localization.doFilter(request,response,chain);

        verify(response,times(1)).setLocale(Locale.forLanguageTag("uk-UA"));
        verify(chain,times(1)).doFilter(request, response);
    }

    @Test
    public void testParameterLocaleSetting() throws IOException, ServletException {
        when(request.getParameter("lang")).thenReturn("UA");

        localization.doFilter(request,response,chain);

        verify(response).addCookie(cookieCaptor.capture());
        assertEquals("lang",cookieCaptor.getValue().getName());
        assertEquals("uk-UA",cookieCaptor.getValue().getValue());
        verify(response,times(1)).setLocale(Locale.forLanguageTag("uk-UA"));
        verify(chain,times(1)).doFilter(request, response);
    }

    @Test
    public void testNoProvidedLanguageSetting() throws IOException, ServletException {
        localization.doFilter(request,response,chain);

        verify(response,times(0)).addCookie(any());
        verify(response,times(1)).setLocale(Locale.forLanguageTag("en-US"));
        verify(chain,times(1)).doFilter(request, response);
    }
}