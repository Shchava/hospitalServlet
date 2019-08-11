package ua.training.servlet.hospital.controller.command.showdiagnosis;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.controller.utilities.gson.GsonFactory;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.CommandResponse;
import ua.training.servlet.hospital.entity.dto.CreationResponse;
import ua.training.servlet.hospital.entity.dto.MedicineDTO;
import ua.training.servlet.hospital.service.medicine.MedicineService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AddMedicineTest {
    private Gson gson = GsonFactory.create();

    @Mock
    private HttpServletRequest request;

    @Mock
    private MedicineService medicineService;

    @Mock
    private HttpSession session;

    private User doctor = new User(20L);

    @InjectMocks
    private AddMedicine addMedicine = new AddMedicine();

    private String JSON = "jsonObj";

    private BufferedReader reader;

    @Captor
    private ArgumentCaptor<MedicineDTO> dtoCaptor;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(request.getLocale()).willReturn(Locale.forLanguageTag("en-EN"));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("LoggedUser")).thenReturn(doctor);

        given(request.getRequestURI()).willReturn("/patient/2/diagnosis/5/addMedicine/");
        given(medicineService.createMedicine(any(),anyLong(),anyLong())).willReturn(true);
        given(medicineService.getNumberOfMedicineByDiagnosisId(5L)).willReturn(10L);
    }

    @Test
    public void testAddMedicine () throws IOException {

        MedicineDTO medicine = new MedicineDTO();
        medicine.setName("name");
        medicine.setDescription("description");
        medicine.setCount(43);
        medicine.setRefill(LocalDate.now());

        String dtoJSON = gson.toJson(medicine);
        Reader strReader = new StringReader(dtoJSON);
        reader = new BufferedReader(strReader);
        when(request.getReader()).thenReturn(reader);

        CreationResponse expected = new CreationResponse("created",new ArrayList<>());

        CommandResponse response = addMedicine.execute(request);

        assertEquals(gson.toJson(expected), response.getResponse());
        assertEquals(200, response.getStatus());

        verify(medicineService,times(1)).createMedicine(dtoCaptor.capture(),eq(5L),eq(20L));
        assertEquals(medicine.getName(),dtoCaptor.getValue().getName());
        assertEquals(medicine.getDescription(),dtoCaptor.getValue().getDescription());
        assertEquals(medicine.getRefill(),dtoCaptor.getValue().getRefill());
        assertEquals(medicine.getCount(),dtoCaptor.getValue().getCount());
    }

    @Test
    public void testAddMedicineEmptyName () throws IOException {

        MedicineDTO medicine = new MedicineDTO();
        medicine.setName(null);
        medicine.setDescription("description");
        medicine.setCount(43);
        medicine.setRefill(LocalDate.now());

        String dtoJSON = gson.toJson(medicine);
        Reader strReader = new StringReader(dtoJSON);
        reader = new BufferedReader(strReader);
        when(request.getReader()).thenReturn(reader);

        CreationResponse expected = new CreationResponse("creationFailed",new ArrayList<>());

        CommandResponse response = addMedicine.execute(request);
        CreationResponse creationResponse = gson.fromJson(response.getResponse(), CreationResponse.class);

        assertEquals(400, response.getStatus());
        assertEquals("creationFailed",creationResponse.getMessage());
        assertEquals("name",creationResponse.getErrors().get(0).getCause());

        verify(medicineService,times(0)).createMedicine(dtoCaptor.capture(),eq(5L),eq(20L));
    }

    @Test
    public void testAddMedicineEmptyCount () throws IOException {

        MedicineDTO medicine = new MedicineDTO();
        medicine.setName("name");
        medicine.setDescription("description");
        medicine.setCount(null);
        medicine.setRefill(LocalDate.now());

        String dtoJSON = gson.toJson(medicine);
        Reader strReader = new StringReader(dtoJSON);
        reader = new BufferedReader(strReader);
        when(request.getReader()).thenReturn(reader);

        CreationResponse expected = new CreationResponse("creationFailed",new ArrayList<>());

        CommandResponse response = addMedicine.execute(request);
        CreationResponse creationResponse = gson.fromJson(response.getResponse(), CreationResponse.class);

        assertEquals(400, response.getStatus());
        assertEquals("creationFailed",creationResponse.getMessage());
        assertEquals("count",creationResponse.getErrors().get(0).getCause());

        verify(medicineService,times(0)).createMedicine(dtoCaptor.capture(),eq(5L),eq(20L));
    }

    @Test
    public void testAddMedicineMinusCount () throws IOException {

        MedicineDTO medicine = new MedicineDTO();
        medicine.setName("name");
        medicine.setDescription("description");
        medicine.setCount(-1);
        medicine.setRefill(LocalDate.now());

        String dtoJSON = gson.toJson(medicine);
        Reader strReader = new StringReader(dtoJSON);
        reader = new BufferedReader(strReader);
        when(request.getReader()).thenReturn(reader);

        CreationResponse expected = new CreationResponse("creationFailed",new ArrayList<>());

        CommandResponse response = addMedicine.execute(request);
        CreationResponse creationResponse = gson.fromJson(response.getResponse(), CreationResponse.class);

        assertEquals(400, response.getStatus());
        assertEquals("creationFailed",creationResponse.getMessage());
        assertEquals("count",creationResponse.getErrors().get(0).getCause());

        verify(medicineService,times(0)).createMedicine(dtoCaptor.capture(),eq(5L),eq(20L));
    }

}