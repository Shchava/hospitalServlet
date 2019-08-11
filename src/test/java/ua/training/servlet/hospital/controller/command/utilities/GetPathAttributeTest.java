package ua.training.servlet.hospital.controller.command.utilities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetPathAttributeTest {
    @Mock
    HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

    }

    @Test
    public void testGetUserId() {
        when(request.getRequestURI()).thenReturn("/patient/5/");
        assertEquals(5, GetPathAttribute.getUserId(request));

        when(request.getRequestURI()).thenReturn("/patient/5");
        assertEquals(5, GetPathAttribute.getUserId(request));
    }

    @Test(expected = NumberFormatException.class)
    public void testEmptyNumber(){
        when(request.getRequestURI()).thenReturn("/patient/");
        GetPathAttribute.getUserId(request);
    }

    @Test(expected = NumberFormatException.class)
    public void testEmptyNumberWithSlash(){
        when(request.getRequestURI()).thenReturn("/patient//");
        GetPathAttribute.getUserId(request);
    }

    @Test(expected = NumberFormatException.class)
    public void testWrongPath(){
        when(request.getRequestURI()).thenReturn("/patint/5/");
        GetPathAttribute.getUserId(request);
    }

    @Test
    public void testGetDiagnosisId() {
        when(request.getRequestURI()).thenReturn("/diagnosis/15/");
        assertEquals(15, GetPathAttribute.getDiagnosisId(request));

        when(request.getRequestURI()).thenReturn("/diagnosis/55/");
        assertEquals(55, GetPathAttribute.getDiagnosisId(request));
    }

    @Test(expected = NumberFormatException.class)
    public void testDiagnosisEmptyNumber(){
        when(request.getRequestURI()).thenReturn("/diagnosis/");
        GetPathAttribute.getDiagnosisId(request);
    }

    @Test(expected = NumberFormatException.class)
    public void testDiagnosisEmptyNumberWithSlash(){
        when(request.getRequestURI()).thenReturn("/diagnosis//");
        GetPathAttribute.getDiagnosisId(request);
    }
}