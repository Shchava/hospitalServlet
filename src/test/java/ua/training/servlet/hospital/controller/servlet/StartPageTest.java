package ua.training.servlet.hospital.controller.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class StartPageTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;

    User user = new User(5L);

    @InjectMocks
    StartPage startPage = new StartPage();

    @Before
    public void setUp(){
        user.setRole(Roles.DOCTOR);

        initMocks(this);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("LoggedUser")).thenReturn(user);
    }

    @Test
    public void testDoctorRedirect() throws IOException, ServletException {
        startPage.doGet(request,response);
        verify(response,times(1)).sendRedirect("/patientsList");
    }

    @Test
    public void testPatientRedirect() throws IOException, ServletException {
        user.setRole(Roles.PATIENT);
        startPage.doGet(request,response);
        verify(response,times(1)).sendRedirect("/patient/5/");
    }
}