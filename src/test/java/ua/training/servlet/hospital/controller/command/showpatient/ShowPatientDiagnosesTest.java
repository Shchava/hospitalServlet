package ua.training.servlet.hospital.controller.command.showpatient;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.controller.utilities.PaginationUtility;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisService;
import ua.training.servlet.hospital.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ShowPatientDiagnosesTest {
    @Mock
    HttpServletRequest request;

    @Mock
    List<Diagnosis> foundDiagnoses;

    @Mock
    ServiceFactory serviceFactory;

    @Mock
    UserService userService;

    @Mock
    DiagnosisService diagnosisService;

    @Mock
    PaginationUtility pagination;

    @InjectMocks
    ShowPatientDiagnoses showPatientDiagnoses = new ShowPatientDiagnoses();

    @Mock
    User patient;

    @Mock
    List<Diagnosis> diagnoses;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(pagination.getRecordsPerPage()).willReturn(5);
        given(pagination.getPage()).willReturn(2);

        given(request.getRequestURI()).willReturn("/patient/2/");
        given(userService.getUser(2L)).willReturn(Optional.of(patient));
        given(diagnosisService.getNumberOfDiagnosesByPatientId(2L)).willReturn(10L);
        given(diagnosisService.findDiagnosesByPatientId(2,5,2L)).willReturn(diagnoses);
    }

    @Test
    public void showPatient(){
        assertEquals("/showPatient.jsp",showPatientDiagnoses.execute(request));

        verify(request,times(1)).setAttribute("patient",patient);
        verify(request,times(1)).setAttribute("diagnoses",diagnoses);
    }

    @Test
    public void showNotExistingPatient(){
        given(userService.getUser(2L)).willReturn(Optional.empty());

        assertEquals("/notFoundPage.jsp",showPatientDiagnoses.execute(request));

        verify(request,times(0)).setAttribute("patient",patient);
        verify(request,times(0)).setAttribute("diagnoses",diagnoses);
    }
}