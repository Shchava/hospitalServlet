package ua.training.servlet.hospital.service.procedure;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.ProcedureDao;
import ua.training.servlet.hospital.entity.Procedure;
import ua.training.servlet.hospital.entity.dto.ProcedureDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static ua.training.servlet.hospital.TestUtils.assertTimeIsBetween;

public class ProcedureServiceImplTest {
    @Mock
    DaoFactory factory;

    @Mock
    ProcedureDao dao;

    @InjectMocks
    ProcedureServiceImpl service = new ProcedureServiceImpl(factory);

    @Mock
    List<Procedure> foundList;

    @Mock
    Procedure foundEntry;

    ProcedureDTO dto;

    @Captor
    ArgumentCaptor<Procedure> procedureCaptor;

    @Before
    public void setUp() throws Exception {
        dto = new ProcedureDTO();
        dto.setName("Test name");
        dto.setDescription("Test description");
        dto.setRoom(802);
        dto.setAppointmentDates(Arrays.asList(
                LocalDateTime.of(2019, 9, 3, 12, 30),
                LocalDateTime.of(2019, 9, 5, 12, 0),
                LocalDateTime.of(2019, 9, 7, 12, 30)
        ));

        initMocks(this);

        given(factory.createProcedureDao()).willReturn(dao);

        given(dao.findProceduresWithDoctorByDiagnosisId(anyInt(), anyInt(), anyLong())).willReturn(foundList);
        given(dao.create(any())).willReturn(true);
    }

    @Test
    public void testFindProceduresByDiagnosisId() {
        assertEquals(foundList, service.findProceduresByDiagnosisId(2, 5, 10));
        verify(dao, times(1)).findProceduresWithDoctorByDiagnosisId(10, 5, 10L);
    }

    @Test
    public void addProcedure() {
        LocalDateTime before = LocalDateTime.now();
        assertTrue(service.createProcedure(dto, 7, 5L));
        LocalDateTime after = LocalDateTime.now();

        verify(dao, times(1)).create(procedureCaptor.capture());

        assertEquals(dto.getName(), procedureCaptor.getValue().getName());
        assertTimeIsBetween(procedureCaptor.getValue().getAssigned(), before, after);
    }

    @Test
    public void testGetNumberOfProceduresByDiagnosisId(){
        given(dao.countProceduresOfDiagnosis(5L)).willReturn(612L);
        assertEquals(612,service.getNumberOfProceduresByDiagnosisId(5L));
    }
}