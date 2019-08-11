package ua.training.servlet.hospital.controller.command.showpatient;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.CommandResponse;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AddDiagnosisTest {
    @Mock
    HttpServletRequest request;

    @Mock
    private HttpSession session;

    private User doctor = new User(20L);

    @Mock
    ServiceFactory serviceFactory;

    @Mock
    DiagnosisService diagnosisService;

    @Mock
    ShowPatientDiagnoses showPatientDiagnoses;

    @InjectMocks
    AddDiagnosis addDiagnosis;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("LoggedUser")).thenReturn(doctor);

        given(showPatientDiagnoses.execute(request)).willReturn(new CommandResponse(200,"/showPatient.jsp"));

        given(request.getRequestURI()).willReturn("/patient/2/addDiagnosis/");
        given(diagnosisService.addDiagnosis(any(),anyLong(),anyLong())).willReturn(true);

        given(request.getParameter("name")).willReturn("name");
        given(request.getParameter("description")).willReturn("description");
    }

    @Test
    public void testAddDiagnosis(){
        CommandResponse response = addDiagnosis.execute(request);
        assertEquals("/showPatient.jsp",response.getResponse());
        assertEquals(200,response.getStatus());

        verify(showPatientDiagnoses,times(1)).execute(request);
        verify(diagnosisService,times(1)).addDiagnosis(any(),eq(2L),eq(20L));
        verify(request,times(1)).setAttribute("created",true);
    }

    @Test
    public void testAddDiagnosisWithNullName(){
        given(request.getParameter("name")).willReturn(null);
        CommandResponse response = addDiagnosis.execute(request);
        assertEquals("/showPatient.jsp",response.getResponse());
        assertEquals(400,response.getStatus());

        verify(showPatientDiagnoses,times(1)).execute(request);
        verify(diagnosisService,times(0)).addDiagnosis(any(),eq(2L),eq(20L));
        verify(request,times(1)).setAttribute("nameEmpty",true);
        verify(request,times(0)).setAttribute("created",true);
    }

    @Test
    public void testAddDiagnosisWithEmptyName(){
        given(request.getParameter("name")).willReturn("");
        CommandResponse response = addDiagnosis.execute(request);
        assertEquals("/showPatient.jsp",response.getResponse());
        assertEquals(400,response.getStatus());

        verify(showPatientDiagnoses,times(1)).execute(request);
        verify(diagnosisService,times(0)).addDiagnosis(any(),eq(2L),eq(20L));
        verify(request,times(1)).setAttribute("nameEmpty",true);
        verify(request,times(0)).setAttribute("created",true);
    }

    @Test
    public void testAddDiagnosisServiceCreationFailed(){
        given(diagnosisService.addDiagnosis(any(),anyLong(),anyLong())).willReturn(false);
        CommandResponse response = addDiagnosis.execute(request);
        assertEquals("/showPatient.jsp",response.getResponse());
        assertEquals(400,response.getStatus());

        verify(showPatientDiagnoses,times(1)).execute(request);
        verify(diagnosisService,times(1)).addDiagnosis(any(),eq(2L),eq(20L));
        verify(request,times(0)).setAttribute("nameEmpty",true);
        verify(request,times(0)).setAttribute("created",true);
        verify(request,times(1)).setAttribute("creationError",true);
    }
}