package ua.training.servlet.hospital.controller.command.showdiagnosis;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisService;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ShowDiagnosisTest {
    @Mock
    HttpServletRequest request;

    @Mock
    Diagnosis foundDiagnosis;

    @Mock
    DiagnosisService diagnosisService;

    @InjectMocks
    ShowDiagnosis showDiagnosis;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        given(request.getRequestURI()).willReturn("/patient/2/diagnosis/2/");

        given(diagnosisService.getDiagnosis(2L)).willReturn(Optional.of(foundDiagnosis));
    }

    @Test
    public void showPatient(){
        assertEquals("/showDiagnosis.jsp",showDiagnosis.execute(request));
        verify(request,times(1)).setAttribute("diagnosis",foundDiagnosis);
    }

    @Test
    public void showNotExistingPatient(){
        given(diagnosisService.getDiagnosis(2L)).willReturn(Optional.empty());

        assertEquals("/notFoundPage.jsp",showDiagnosis.execute(request));

        verify(request,times(0)).setAttribute(eq("diagnosis"),any());
    }
}

