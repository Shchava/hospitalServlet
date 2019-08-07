package ua.training.servlet.hospital.controller.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.controller.utilities.PaginationUtility;
import ua.training.servlet.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.servlet.hospital.service.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ShowAllPatientsTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    UserService userService;
    @Mock
    PaginationUtility utility;
    @InjectMocks
    ShowAllPatients showAllPatients = new ShowAllPatients();

    @Mock
    List<ShowUserToDoctorDTO> found;


    @Before
    public void setUp() throws Exception {
        showAllPatients.init();
        initMocks(this);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(userService.getNumberOfPatients()).thenReturn(43);
    }

    @Test
    public void testGetPage() throws IOException, ServletException {
        given(userService.findPatientsToShow(anyInt(),anyInt())).willReturn(found);

        showAllPatients.doGet(request,response);

        verify(request,times(1)).getRequestDispatcher("/patientsList.jsp");
        verify(requestDispatcher,times(1)).forward(any(),any());
        verify(request,times(1)).setAttribute("patients",found);
        verify(utility,times(1)).setAttributes(request,43);
    }

}