package ua.training.servlet.hospital.service.surgery;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.SurgeryDao;
import ua.training.servlet.hospital.entity.Surgery;
import ua.training.servlet.hospital.entity.dto.SurgeryDTO;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static ua.training.servlet.hospital.TestUtils.assertTimeIsBetween;

public class SurgeryServiceImplTest {
    @Mock
    DaoFactory factory;

    @Mock
    SurgeryDao dao;

    @InjectMocks
    SurgeryServiceImpl service = new SurgeryServiceImpl(factory);

    @Mock
    List<Surgery> foundList;

    @Mock
    Surgery foundEntry;

    SurgeryDTO dto;

    @Captor
    ArgumentCaptor<Surgery> surgeryCaptor;

    @Before
    public void setUp() throws Exception {
        dto = new SurgeryDTO();
        dto.setName("Test name");
        dto.setDescription("Test description");
        dto.setSurgeryDate(LocalDateTime.of(2019, 9, 5, 12, 0));

        initMocks(this);

        given(factory.createSurgeryDao()).willReturn(dao);

        given(dao.findSurgeriesWithDoctorByDiagnosisId(anyInt(), anyInt(), anyLong())).willReturn(foundList);

        given(dao.create(any())).willReturn(true);
    }

    @Test
    public void testFindProceduresByDiagnosisId() {
        assertEquals(foundList, service.findSurgeriesByDiagnosisId(2, 5, 10));
        verify(dao, times(1)).findSurgeriesWithDoctorByDiagnosisId(10, 5, 10L);
    }

    @Test
    public void addProcedure() {
        LocalDateTime before = LocalDateTime.now();
        assertTrue(service.createSurgery(dto, 7, 6L));
        LocalDateTime after = LocalDateTime.now();

        verify(dao, times(1)).create(surgeryCaptor.capture());

        assertEquals(dto.getName(), surgeryCaptor.getValue().getName());
        assertTimeIsBetween(surgeryCaptor.getValue().getAssigned(), before, after);
    }

    @Test
    public void testGetNumberOfSurgeriesByDiagnosisId(){
        given(dao.countSurgeriesOfDiagnosis(5L)).willReturn(612L);
        assertEquals(612,service.getNumberOfSurgeriesByDiagnosisId(5L));
    }
}