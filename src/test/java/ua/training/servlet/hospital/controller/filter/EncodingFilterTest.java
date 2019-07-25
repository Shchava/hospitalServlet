package ua.training.servlet.hospital.controller.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class EncodingFilterTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain chain;

    @InjectMocks
    EncodingFilter encodingFilter = new EncodingFilter();

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void testSettingEncoding() throws IOException, ServletException {
        encodingFilter.doFilter(request,response,chain);

        verify(request,times(1)).setCharacterEncoding("UTF-8");
        verify(response,times(1)).setCharacterEncoding("UTF-8");
        verify(chain,times(1)).doFilter(request, response);
    }
}