package ua.training.servlet.hospital.controller.command.utilities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetUserIdTest {
    @Mock
    HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

    }

    @Test
    public void testGetUserId() {
        when(request.getRequestURI()).thenReturn("/patient/5/");
        assertEquals(5,GetUserId.getUserId(request));

        when(request.getRequestURI()).thenReturn("/patient/5");
        assertEquals(5,GetUserId.getUserId(request));
    }

    @Test(expected = NumberFormatException.class)
    public void testEmptyNumber(){
        when(request.getRequestURI()).thenReturn("/patient/");
        GetUserId.getUserId(request);
    }

    @Test(expected = NumberFormatException.class)
    public void testEmptyNumberWithSlash(){
        when(request.getRequestURI()).thenReturn("/patient//");
        GetUserId.getUserId(request);
    }

    @Test(expected = NumberFormatException.class)
    public void testWrongPath(){
        when(request.getRequestURI()).thenReturn("/patint/5/");
        GetUserId.getUserId(request);
    }

}