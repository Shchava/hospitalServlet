package ua.training.servlet.hospital.controller.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AuthFilterTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    FilterChain chain;
    @Mock
    RequestDispatcher requestDispatcher;

    @InjectMocks
    AuthFilter login = new AuthFilter();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
    }

    @Test
    public void testFilterAuthenticatedUser() throws IOException, ServletException {
        User loggedUser = new User(
                "TestName",
                "TestName",
                "TestPatronymic",
                "testEmail@example.com",
                "SomeGeneratedPasswordHash",
                Roles.DOCTOR);

        given(session.getAttribute("LoggedUser")).willReturn(loggedUser);
        given(request.getRequestURI()).willReturn("/index");

        login.doFilter(request,response,chain);

        verify(chain,times(1)).doFilter(any(), any());
        verify(request,times(0)).getRequestDispatcher("/login.jsp");
    }

    @Test
    public void testFilterNotAuthenticatedUser() throws IOException, ServletException {

        given(session.getAttribute("LoggedUser")).willReturn(null);
        given(request.getRequestURI()).willReturn("/index");

        login.doFilter(request,response,chain);

        verify(chain,times(0)).doFilter(any(), any());
        verify(request,times(1)).getRequestDispatcher("/login.jsp");
    }

    @Test
    public void testLoginPageAcces() throws IOException, ServletException {

        given(session.getAttribute("LoggedUser")).willReturn(null);
        given(request.getRequestURI()).willReturn("/login.jsp");

        login.doFilter(request,response,chain);

        verify(chain,times(1)).doFilter(any(), any());
        verify(request,times(0)).getRequestDispatcher("/login.jsp");
    }

    @Test
    public void testRegistrationPageAcces() throws IOException, ServletException {

        given(session.getAttribute("LoggedUser")).willReturn(null);
        given(request.getRequestURI()).willReturn("/registration.jsp");

        login.doFilter(request,response,chain);

        verify(chain,times(1)).doFilter(any(), any());
        verify(request,times(0)).getRequestDispatcher("/login.jsp");
    }
}