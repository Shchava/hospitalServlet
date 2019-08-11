package ua.training.servlet.hospital.controller.command.showdiagnosis;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.controller.utilities.PaginationUtility;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.dto.CommandResponse;
import ua.training.servlet.hospital.entity.dto.Page;
import ua.training.servlet.hospital.service.medicine.MedicineService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ShowMedicineTest {
    Gson gson = new Gson();

    @Mock
    private HttpServletRequest request;

    @Mock
    private PaginationUtility pagination;

    @Mock
    private MedicineService medicineService;

    @Mock
    private List<Medicine> medicines;

    private Page<Medicine> createdPage = new Page<>();

    @InjectMocks
    private ShowMedicine showMedicine = new ShowMedicine();

    private String JSON = "jsonObj";

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(pagination.getRecordsPerPage()).willReturn(5);
        given(pagination.getPage()).willReturn(2);
        given(pagination.createPage(medicines)).willReturn(createdPage);


        given(request.getRequestURI()).willReturn("/patient/2/diagnosis/5/getMedicine/");
        given(medicineService.findMedicineByDiagnosisId(anyInt(), anyInt(), eq(5L))).willReturn(medicines);
        given(medicineService.getNumberOfMedicineByDiagnosisId(5L)).willReturn(10L);

        createdPage.setTotalPages(2);
        createdPage.setTotalElements(10);
        createdPage.setFirst(false);
    }


        @Test
        public void testGetMedicine () {
            CommandResponse response = showMedicine.execute(request);
            assertEquals(gson.toJson(createdPage), response.getResponse());
            assertEquals(200, response.getStatus());
            verify(medicineService,times(1)).findMedicineByDiagnosisId(2,5,5L);
            verify(pagination,times(1)).createPage(medicines);
        }
    }