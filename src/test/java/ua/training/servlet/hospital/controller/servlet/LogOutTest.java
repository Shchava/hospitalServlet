package ua.training.servlet.hospital.controller.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class LogOutTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;

    @InjectMocks
    LogOut login = new LogOut();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testLogOut() throws Exception {
        login.doGet(request, response);
        verify(session,times(1)).invalidate();
        verify(response,times(1)).sendRedirect("/login.jsp");
    }
}