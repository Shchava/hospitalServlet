package ua.training.servlet.hospital.controller.utilities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class PaginationUtilityTest {
    @Mock
    HttpServletRequest request;

    PaginationUtility utility = new PaginationUtility();

    @Before
    public void setUp(){
        initMocks(this);
        given(request.getParameter("page")).willReturn("4");
        given(request.getParameter("recordsPerPage")).willReturn("5");
    }

    @Test
    public void setAttributes() {
        utility.setAttributes(request,43);

        verify(request,times(1)).setAttribute("numberOfPages",9);
        verify(request,times(1)).setAttribute("page",4);
        verify(request,times(1)).setAttribute("recordsPerPage",5);
        verify(request,times(1)).setAttribute("records",43L);
    }

    @Test
    public void getOffset() {
        utility.setAttributes(request,43);
        assertEquals(15,utility.getOffset());
    }

    @Test
    public void getRecordsPerPage() {
        utility.setAttributes(request,43);
        assertEquals(5,utility.getRecordsPerPage());
    }
}