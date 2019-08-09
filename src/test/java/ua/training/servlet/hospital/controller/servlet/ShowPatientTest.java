package ua.training.servlet.hospital.controller.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.controller.command.Command;
import ua.training.servlet.hospital.controller.command.showpatient.ShowPatientDiagnoses;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ShowPatientTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    ShowPatientDiagnoses showPatientDiagnoses = new ShowPatientDiagnoses();

    @Mock
    private Map<String, Command> commands; //= new HashMap<>();

    @InjectMocks
    ShowPatient showPatient = new ShowPatient();

    @Before
    public void setUp() throws Exception {
        showPatient.init();
        initMocks(this);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);

        when(commands.getOrDefault(eq(""),any())).thenReturn(showPatientDiagnoses);

        when(showPatientDiagnoses.execute(request)).thenReturn("/showPatient.jsp");
    }

    @Test
    public void testGetShowPatient() throws IOException, ServletException {
        given(request.getRequestURI()).willReturn("/patient/1/");

        showPatient.doGet(request,response);

        verify(showPatientDiagnoses,times(1)).execute(request);
        verify(request,times(1)).getRequestDispatcher("/showPatient.jsp");
        verify(requestDispatcher,times(1)).forward(request,response);
    }

}