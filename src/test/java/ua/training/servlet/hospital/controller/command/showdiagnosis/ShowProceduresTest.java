package ua.training.servlet.hospital.controller.command.showdiagnosis;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.controller.utilities.PaginationUtility;
import ua.training.servlet.hospital.entity.Procedure;
import ua.training.servlet.hospital.entity.dto.Page;
import ua.training.servlet.hospital.service.procedure.ProcedureService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ShowProceduresTest {
    Gson gson = new Gson();

    @Mock
    private HttpServletRequest request;

    @Mock
    private PaginationUtility pagination;

    @Mock
    private ProcedureService procedureService;

    @Mock
    private List<Procedure> procedures;

    private Page<Procedure> createdPage = new Page<>();

    @InjectMocks
    private ShowProcedures showProcedures = new ShowProcedures();

    private String JSON = "jsonObj";

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(pagination.getRecordsPerPage()).willReturn(5);
        given(pagination.getPage()).willReturn(2);
        given(pagination.createPage(procedures)).willReturn(createdPage);


        given(request.getRequestURI()).willReturn("/patient/2/diagnosis/5/getMedicine/");
        given(procedureService.findProceduresByDiagnosisId(anyInt(), anyInt(), eq(5L))).willReturn(procedures);
        given(procedureService.getNumberOfProceduresByDiagnosisId(5L)).willReturn(10L);

        createdPage.setTotalPages(2);
        createdPage.setTotalElements(10);
        createdPage.setFirst(false);
    }


    @Test
    public void testGetMedicine () {
        assertEquals(gson.toJson(createdPage), showProcedures.execute(request));
        verify(procedureService,times(1)).findProceduresByDiagnosisId(2,5,5L);
        verify(pagination,times(1)).createPage(procedures);
    }
}