package ua.training.servlet.hospital.controller.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.entity.enums.Roles;
import ua.training.servlet.hospital.entity.exceptions.EmailExistsException;
import ua.training.servlet.hospital.service.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RegistrationTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    ServletConfig config;
    @Mock
    ServletContext context;
    @InjectMocks
    Registration registration = new Registration();


    @Mock
    UserService userService;



    @Before
    public void setUp() throws Exception {
        registration.init(config);
        initMocks(this);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(config.getServletContext()).thenReturn(context);
        when(context.getResourceAsStream(any()))
                .thenReturn(new FileInputStream("src/main/webapp/resources/regexp.properties"));

        given(request.getParameter("name")).willReturn("Name");
        given(request.getParameter("surname")).willReturn("Surname");
        given(request.getParameter("patronymic")).willReturn("Patronymic");
        given(request.getParameter("email")).willReturn("email@example.com");
        given(request.getParameter("password")).willReturn("password1");
        given(request.getParameter("confirmPassword")).willReturn("password1");
        given(request.getParameter("role")).willReturn(Roles.DOCTOR.name());
    }

    @Test
    public void testRegisterWithNullNamingData() throws IOException, ServletException {
        given(request.getParameter("name")).willReturn(null);
        given(request.getParameter("surname")).willReturn(null);
        given(request.getParameter("patronymic")).willReturn(null);

        registration.doPost(request,response);

        verify(request,times(1)).setAttribute("nameEmpty",true);
        verify(request,times(1)).setAttribute("surnameEmpty",true);
        verify(request,times(1)).setAttribute("patronymicEmpty",true);
        verify(userService,times(0)).registerUser(any());
        verify(request,times(1)).getRequestDispatcher("/register.jsp");
        verify(requestDispatcher,times(1)).forward(any(),any());
    }

    @Test
    public void testRegisterWithInvalidNamingData() throws IOException, ServletException {
        given(request.getParameter("name")).willReturn("__Name");
        given(request.getParameter("surname")).willReturn("Ыurname");
        given(request.getParameter("patronymic")).willReturn("Patrnymicпобатькові");

        registration.doPost(request,response);

        verify(request,times(1)).setAttribute("nameWrong",true);
        verify(request,times(1)).setAttribute("surnameWrong",true);
        verify(request,times(1)).setAttribute("patronymicWrong",true);
        verify(userService,times(0)).registerUser(any());
        verify(request,times(1)).getRequestDispatcher("/register.jsp");
        verify(requestDispatcher,times(1)).forward(any(),any());
    }

    @Test
    public void testRegisterWithInvalidEmail() throws IOException, ServletException {
        given(request.getParameter("email")).willReturn("email");


        registration.doPost(request,response);

        verify(request,times(0)).setAttribute("nameWrong",true);
        verify(request,times(0)).setAttribute("surnameWrong",true);
        verify(request,times(0)).setAttribute("patronymicWrong",true);
        verify(request,times(1)).setAttribute("emailWrong",true);
        verify(userService,times(0)).registerUser(any());
        verify(request,times(1)).getRequestDispatcher("/register.jsp");
        verify(requestDispatcher,times(1)).forward(any(),any());
    }

    @Test
    public void testRegisterWithInvalidRole() throws IOException, ServletException {
        given(request.getParameter("role")).willReturn("somerole");


        registration.doPost(request,response);

        verify(request,times(0)).setAttribute("nameWrong",true);
        verify(request,times(0)).setAttribute("surnameWrong",true);
        verify(request,times(0)).setAttribute("patronymicWrong",true);
        verify(request,times(0)).setAttribute("emailWrong",true);
        verify(request,times(1)).setAttribute("roleNull",true);
        verify(userService,times(0)).registerUser(any());
        verify(request,times(1)).getRequestDispatcher("/register.jsp");
        verify(requestDispatcher,times(1)).forward(any(),any());
    }

    @Test
    public void testRegisterWithInvalidNotMatchingPasswords() throws IOException, ServletException {
        given(request.getParameter("confirmPassword")).willReturn("newPassword");


        registration.doPost(request,response);

        verify(request,times(0)).setAttribute("nameWrong",true);
        verify(request,times(0)).setAttribute("surnameWrong",true);
        verify(request,times(0)).setAttribute("patronymicWrong",true);
        verify(request,times(0)).setAttribute("emailWrong",true);
        verify(request,times(0)).setAttribute("roleNull",true);
        verify(request,times(1)).setAttribute("passwordsNotEqual",true);
        verify(userService,times(0)).registerUser(any());
        verify(request,times(1)).getRequestDispatcher("/register.jsp");
        verify(requestDispatcher,times(1)).forward(any(),any());
    }

    @Test
    public void testRegisterWithInvalidPassword() throws IOException, ServletException {
        given(request.getParameter("password")).willReturn("ps1");
        given(request.getParameter("confirmPassword")).willReturn("ps1");


        registration.doPost(request,response);

        verify(request,times(0)).setAttribute("nameWrong",true);
        verify(request,times(0)).setAttribute("surnameWrong",true);
        verify(request,times(0)).setAttribute("patronymicWrong",true);
        verify(request,times(0)).setAttribute("emailWrong",true);
        verify(request,times(0)).setAttribute("roleNull",true);
        verify(request,times(0)).setAttribute("passwordsNotEqual",true);
        verify(request,times(1)).setAttribute("passwordWrong",true);
        verify(userService,times(0)).registerUser(any());
        verify(request,times(1)).getRequestDispatcher("/register.jsp");
        verify(requestDispatcher,times(1)).forward(any(),any());
    }

    @Test
    public void testRegisterWithExistingEmiail() throws IOException, ServletException {
        given(userService.registerUser(any())).willThrow(new EmailExistsException());

        registration.doPost(request,response);

        verify(request,times(0)).setAttribute("nameWrong",true);
        verify(request,times(0)).setAttribute("surnameWrong",true);
        verify(request,times(0)).setAttribute("patronymicWrong",true);
        verify(request,times(0)).setAttribute("emailWrong",true);
        verify(request,times(0)).setAttribute("roleNull",true);
        verify(request,times(0)).setAttribute("passwordsNotEqual",true);
        verify(request,times(0)).setAttribute("passwordWrong",true);
        verify(request,times(1)).setAttribute("emailExists",true);
        verify(userService,times(1)).registerUser(any());
        verify(request,times(1)).getRequestDispatcher("/register.jsp");
        verify(requestDispatcher,times(1)).forward(any(),any());
    }

    @Test
    public void testRegistrationErrorRegister() throws IOException, ServletException {

        registration.doPost(request,response);

        verify(request,times(0)).setAttribute("nameWrong",true);
        verify(request,times(0)).setAttribute("surnameWrong",true);
        verify(request,times(0)).setAttribute("patronymicWrong",true);
        verify(request,times(0)).setAttribute("emailWrong",true);
        verify(request,times(0)).setAttribute("roleNull",true);
        verify(request,times(0)).setAttribute("passwordsNotEqual",true);
        verify(request,times(0)).setAttribute("passwordWrong",true);
        verify(request,times(0)).setAttribute("emailExists",true);
        verify(request,times(1)).setAttribute("registrationError",true);
        verify(userService,times(1)).registerUser(any());
        verify(request,times(1)).getRequestDispatcher("/register.jsp");
        verify(requestDispatcher,times(1)).forward(any(),any());
    }

    @Test
    public void testValidRegistration() throws IOException, ServletException {
        given(userService.registerUser(any())).willReturn(true);

        registration.doPost(request,response);

        verify(request,times(0)).setAttribute("nameWrong",true);
        verify(request,times(0)).setAttribute("surnameWrong",true);
        verify(request,times(0)).setAttribute("patronymicWrong",true);
        verify(request,times(0)).setAttribute("emailWrong",true);
        verify(request,times(0)).setAttribute("roleNull",true);
        verify(request,times(0)).setAttribute("passwordsNotEqual",true);
        verify(request,times(0)).setAttribute("passwordWrong",true);
        verify(request,times(0)).setAttribute("emailExists",true);
        verify(request,times(0)).setAttribute("registrationError",true);
        verify(request,times(1)).setAttribute("registered",true);
        verify(userService,times(1)).registerUser(any());
        verify(request,times(1)).getRequestDispatcher("/login.jsp");
        verify(requestDispatcher,times(1)).forward(any(),any());
    }
}