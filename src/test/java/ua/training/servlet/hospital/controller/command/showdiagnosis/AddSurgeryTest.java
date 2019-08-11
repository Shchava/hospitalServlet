package ua.training.servlet.hospital.controller.command.showdiagnosis;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.controller.utilities.GsonFactory;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.CommandResponse;
import ua.training.servlet.hospital.entity.dto.CreationResponse;
import ua.training.servlet.hospital.entity.dto.SurgeryDTO;
import ua.training.servlet.hospital.service.surgery.SurgeryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AddSurgeryTest {
    private Gson gson = GsonFactory.create();

    @Mock
    private HttpServletRequest request;

    @Mock
    private SurgeryService surgeryService;

    @Mock
    private HttpSession session;

    private User doctor = new User(20L);

    @InjectMocks
    private AddSurgery addSurgery = new AddSurgery();

    private String JSON = "jsonObj";

    private BufferedReader reader;

    @Captor
    private ArgumentCaptor<SurgeryDTO> dtoCaptor;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(request.getLocale()).willReturn(Locale.forLanguageTag("en-EN"));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("LoggedUser")).thenReturn(doctor);

        given(request.getRequestURI()).willReturn("/patient/2/diagnosis/5/addSurgery/");
        given(surgeryService.createSurgery(any(),anyLong(),anyLong())).willReturn(true);
        given(surgeryService.getNumberOfSurgeriesByDiagnosisId(5L)).willReturn(10L);
    }

    @Test
    public void testAddSurgery () throws IOException {

        SurgeryDTO surgery = new SurgeryDTO();
        surgery.setName("name");
        surgery.setDescription("description");
        surgery.setSurgeryDate(LocalDateTime.now());

        String dtoJSON = gson.toJson(surgery);
        Reader strReader = new StringReader(dtoJSON);
        reader = new BufferedReader(strReader);
        when(request.getReader()).thenReturn(reader);

        CreationResponse expected = new CreationResponse("created",new ArrayList<>());

        CommandResponse response = addSurgery.execute(request);

        assertEquals(gson.toJson(expected), response.getResponse());
        assertEquals(200, response.getStatus());

        verify(surgeryService,times(1)).createSurgery(dtoCaptor.capture(),eq(5L),eq(20L));
        assertEquals(surgery.getName(),dtoCaptor.getValue().getName());
        assertEquals(surgery.getDescription(),dtoCaptor.getValue().getDescription());
        assertEquals(surgery.getSurgeryDate(),dtoCaptor.getValue().getSurgeryDate());
    }

    @Test
    public void testAddSurgeryEmptyName () throws IOException {

        SurgeryDTO surgery = new SurgeryDTO();
        surgery.setName(null);
        surgery.setDescription("description");
        surgery.setSurgeryDate(LocalDateTime.now());

        String dtoJSON = gson.toJson(surgery);
        Reader strReader = new StringReader(dtoJSON);
        reader = new BufferedReader(strReader);
        when(request.getReader()).thenReturn(reader);

        CreationResponse expected = new CreationResponse("creationFailed",new ArrayList<>());

        CommandResponse response = addSurgery.execute(request);
        CreationResponse creationResponse = gson.fromJson(response.getResponse(), CreationResponse.class);

        assertEquals(400, response.getStatus());
        assertEquals("creationFailed",creationResponse.getMessage());
        assertEquals("name",creationResponse.getErrors().get(0).getCause());

        verify(surgeryService,times(0)).createSurgery(dtoCaptor.capture(),eq(5L),eq(20L));
    }

    @Test
    public void testAddSurgeryEmptySurgeryDate () throws IOException {

        SurgeryDTO surgery = new SurgeryDTO();
        surgery.setName("name");
        surgery.setDescription("description");
        surgery.setSurgeryDate(null);

        String dtoJSON = gson.toJson(surgery);
        Reader strReader = new StringReader(dtoJSON);
        reader = new BufferedReader(strReader);
        when(request.getReader()).thenReturn(reader);

        CreationResponse expected = new CreationResponse("creationFailed",new ArrayList<>());

        CommandResponse response = addSurgery.execute(request);
        CreationResponse creationResponse = gson.fromJson(response.getResponse(), CreationResponse.class);

        assertEquals(400, response.getStatus());
        assertEquals("creationFailed",creationResponse.getMessage());
        assertEquals("surgeryDate",creationResponse.getErrors().get(0).getCause());

        verify(surgeryService,times(0)).createSurgery(dtoCaptor.capture(),eq(5L),eq(20L));
    }
}