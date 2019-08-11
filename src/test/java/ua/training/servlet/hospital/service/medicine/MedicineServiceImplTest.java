package ua.training.servlet.hospital.service.medicine;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.MedicineDao;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.dto.MedicineDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static ua.training.servlet.hospital.TestUtils.assertTimeIsBetween;

public class MedicineServiceImplTest {
    @Mock
    DaoFactory factory;

    @Mock
    MedicineDao dao;

    @InjectMocks
    MedicineServiceImpl service = new MedicineServiceImpl(factory);

    @Mock
    List<Medicine> foundList;

    @Mock
    Medicine foundEntry;

    MedicineDTO dto;

    @Captor
    ArgumentCaptor<Medicine> medicineCaptor;

    @Before
    public void setUp() throws Exception {
        dto = new MedicineDTO();
        dto.setName("Test name");
        dto.setDescription("Test description");
        dto.setCount(12);
        dto.setRefill(LocalDate.of(2019,9,12));

        initMocks(this);

        given(factory.createMedicineDao()).willReturn(dao);

        given(dao.findMedicineWithDoctorByDiagnosisId(anyInt(), anyInt(), anyLong())).willReturn(foundList);
        given(dao.create(any())).willReturn(true);
    }

    @Test
    public void testFindMedicineByDiagnosisId() {
        assertEquals(foundList, service.findMedicineByDiagnosisId(2, 5, 10));
        verify(dao, times(1)).findMedicineWithDoctorByDiagnosisId(10, 5, 10L);
    }

    @Test
    public void addMedicine() {
        LocalDateTime before = LocalDateTime.now();
        assertTrue(service.createMedicine(dto, 7, 9L));
        LocalDateTime after = LocalDateTime.now();

        verify(dao, times(1)).create(medicineCaptor.capture());

        assertEquals(dto.getName(),medicineCaptor.getValue().getName());
        assertTimeIsBetween(medicineCaptor.getValue().getAssigned(), before, after);
    }

    @Test
    public void testGetNumberOfMedicineByDiagnosisId(){
        given(dao.countMedicinesOfDiagnosis(5L)).willReturn(612L);
        assertEquals(612,service.getNumberOfMedicineByDiagnosisId(5L));
    }
}