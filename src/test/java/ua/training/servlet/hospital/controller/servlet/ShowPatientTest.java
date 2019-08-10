package ua.training.servlet.hospital.controller.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.controller.command.Command;
import ua.training.servlet.hospital.controller.command.RestCommand;
import ua.training.servlet.hospital.controller.command.showdiagnosis.ShowDiagnosis;
import ua.training.servlet.hospital.controller.command.showdiagnosis.ShowMedicine;
import ua.training.servlet.hospital.controller.command.showpatient.ShowPatientDiagnoses;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
    PrintWriter mockWriter;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    ShowPatientDiagnoses showPatientDiagnoses = new ShowPatientDiagnoses();

    @Mock
    ShowDiagnosis showDiagnosis = new ShowDiagnosis();

    @InjectMocks
    ShowPatient showPatient = new ShowPatient();

    @Mock
    private ShowMedicine showMedicine;

    @Mock
    private Map<String, Command> commands;

    @Mock
    private Map<String, RestCommand> restCommands;

    String JSON = "created JSON object";

    @Before
    public void setUp() throws Exception {
        showPatient.init();
        initMocks(this);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);

        when(restCommands.containsKey("diagnosis/getMedicine/")).thenReturn(true);
        when(restCommands.get("diagnosis/getMedicine/")).thenReturn(showMedicine);

        when(commands.getOrDefault(eq(""), any())).thenReturn(showPatientDiagnoses);
        when(commands.getOrDefault(eq("diagnosis/"), any())).thenReturn(showDiagnosis);

        when(response.getWriter()).thenReturn(mockWriter);

        when(showPatientDiagnoses.execute(request)).thenReturn("/showPatient.jsp");
        when(showDiagnosis.execute(request)).thenReturn("/showDiagnosis.jsp");

        when(showMedicine.execute(request)).thenReturn(JSON);
    }

    @Test
    public void testGetShowPatient() throws IOException, ServletException {
        given(request.getRequestURI()).willReturn("/patient/1/");

        showPatient.doGet(request, response);

        verify(showPatientDiagnoses, times(1)).execute(request);
        verify(request, times(1)).getRequestDispatcher("/showPatient.jsp");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testGetShowDiagnosis() throws IOException, ServletException {
        given(request.getRequestURI()).willReturn("/patient/1/diagnosis/3/");

        showPatient.doGet(request, response);

        verify(showDiagnosis, times(1)).execute(request);
        verify(request, times(1)).getRequestDispatcher("/showDiagnosis.jsp");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testGetShowMedicine() throws IOException, ServletException {
        given(request.getRequestURI()).willReturn("/patient/1/diagnosis/3/getMedicine/");

        showPatient.doGet(request, response);

        verify(showMedicine, times(1)).execute(request);
        verify(response, times(1)).getWriter();
        verify(mockWriter, times(1)).print(JSON);
        verify(mockWriter, times(1)).flush();
    }

}