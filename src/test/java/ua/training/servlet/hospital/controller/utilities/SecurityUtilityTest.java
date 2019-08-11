package ua.training.servlet.hospital.controller.utilities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SecurityUtilityTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    User user = new User(4L);
    @Before
    public void init(){
        initMocks(this);
        user.setRole(Roles.DOCTOR);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("LoggedUser")).thenReturn(user);

        when(request.getRequestURI()).thenReturn("/patient/4/");
    }

    @Test
    public void testRemoveVariableParts() {
        assertEquals("path/path/",SecurityUtility.removeVariableParts("path/path/"));
        assertEquals("path/path/",SecurityUtility.removeVariableParts("path/412/path/34/"));
        assertEquals("path/path/",SecurityUtility.removeVariableParts("path/412/path/34/?param1='ggg'&param2='gg2'"));
    }

    @Test
    public void testHasAccess() {
        assertTrue(SecurityUtility.hasAccess(request));

        when(request.getRequestURI()).thenReturn("/patient/7/");
        assertTrue(SecurityUtility.hasAccess(request));

        when(request.getRequestURI()).thenReturn("/patientsList");
        assertTrue(SecurityUtility.hasAccess(request));

        when(request.getRequestURI()).thenReturn("/patient/diagnosis/wrongURL");
        assertFalse(SecurityUtility.hasAccess(request));

        user.setRole(Roles.PATIENT);
        when(request.getRequestURI()).thenReturn("/patient/7/");
        assertFalse(SecurityUtility.hasAccess(request));

        when(request.getRequestURI()).thenReturn("/patient/4/");
        assertTrue(SecurityUtility.hasAccess(request));

        when(request.getRequestURI()).thenReturn("/login");
        assertTrue(SecurityUtility.hasAccess(request));
    }
}