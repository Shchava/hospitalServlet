package ua.training.servlet.hospital.service.diagnosis;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.dao.DiagnosisDao;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.dto.DiagnosisDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static ua.training.servlet.hospital.TestUtils.assertTimeIsBetween;

public class DiagnosisServiceImplTest {
    @Mock
    DiagnosisDao diagnosisDao;

    @InjectMocks
    DiagnosisServiceImpl service = new DiagnosisServiceImpl(diagnosisDao);

    @Mock
    List<Diagnosis> diagnosesList;

    @Mock
    Diagnosis diagnosis;

    DiagnosisDTO diagnosisDTO = new DiagnosisDTO(
            "test name",
            "some description");

    @Captor
    ArgumentCaptor<Diagnosis> diagnosisCaptor;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(diagnosisDao.create(any())).willReturn(true);
        given(diagnosisDao.findDiagnosesByPatientId(anyInt(), anyInt(),anyLong())).willReturn(diagnosesList);
        given(diagnosisDao.create(any())).willReturn(true);
    }

    @Test
    public void testFindDiagnosesByPatientId() {
        assertEquals(diagnosesList, service.findDiagnosesByPatientId(2, 5, 10));
        verify(diagnosisDao, times(1)).findDiagnosesByPatientId(10,5, 10L);
    }

    @Test
    public void testFindDiagnosis(){
        given(diagnosisDao.findById(4L)).willReturn(Optional.of(diagnosis));
        assertEquals(diagnosis,service.getDiagnosis(4).get());

    }

    @Test
    public void addDiagnosis() {
        LocalDateTime before = LocalDateTime.now();
        assertTrue(service.addDiagnosis(diagnosisDTO, 7, 3));
        LocalDateTime after = LocalDateTime.now();

        verify(diagnosisDao, times(1)).create(diagnosisCaptor.capture());

        assertEquals(diagnosisDTO.getName(),diagnosisCaptor.getValue().getName());
        assertEquals(diagnosisDTO.getDescription(),diagnosisCaptor.getValue().getDescription());
        assertEquals(7L,diagnosisCaptor.getValue().getPatient().getId());
        assertEquals(3L,diagnosisCaptor.getValue().getDoctor().getId());
        assertTimeIsBetween(diagnosisCaptor.getValue().getAssigned(), before, after);
    }

    @Test
    public void testGetNumberOfDiagnosesByPatientId(){
        given(diagnosisDao.countDiagnosesOfPatient(5L)).willReturn(612L);
        assertEquals(612,service.getNumberOfDiagnosesByPatientId(5L));
    }
}