package ua.training.servlet.hospital.controller.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;
import ua.training.servlet.hospital.service.user.AuthService;
import ua.training.servlet.hospital.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoginTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;

    @InjectMocks
    Login login = new Login();
    @Mock
    AuthService authService;
    @Mock
    UserService userService;

    String email = "email@test.com";
    String password = "password";

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testRightAuth() throws Exception {
        given(request.getParameter("email")).willReturn(email);
        given(request.getParameter("password")).willReturn(password);

        User foundUser = new User(
                "TestName",
                "TestName",
                "TestPatronymic",
                email,
                "SomeGeneratedPasswordHash",
                Roles.DOCTOR);

        given(userService.getUser(email)).willReturn(Optional.of(foundUser));
        given(authService.checkAuthority(email,password)).willReturn(true);

        login.doPost(request, response);

        verify(session,times(1)).setAttribute("LoggedUser", foundUser);
        verify(response,times(1)).sendRedirect("/index.jsp");
    }


    @Test
    public void testWrongAuth() throws Exception {
        given(request.getParameter("email")).willReturn(email);
        given(request.getParameter("password")).willReturn(password);

        given(userService.getUser(email)).willReturn(Optional.empty());
        given(authService.checkAuthority(email,password)).willReturn(false);

        login.doPost(request, response);

        verify(session,times(0)).setAttribute(any(), any());
        verify(response,times(1)).sendRedirect("/login");
    }
}