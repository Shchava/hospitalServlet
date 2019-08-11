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
import ua.training.servlet.hospital.entity.dto.ProcedureDTO;
import ua.training.servlet.hospital.service.procedure.ProcedureService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AddProcedureTest {
    private Gson gson = GsonFactory.create();

    @Mock
    private HttpServletRequest request;

    @Mock
    private ProcedureService procedureService;

    @Mock
    private HttpSession session;

    private User doctor = new User(20L);

    @InjectMocks
    private AddProcedure addProcedure = new AddProcedure();

    private String JSON = "jsonObj";

    private BufferedReader reader;

    @Captor
    private ArgumentCaptor<ProcedureDTO> dtoCaptor;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        given(request.getLocale()).willReturn(Locale.forLanguageTag("en-EN"));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("LoggedUser")).thenReturn(doctor);

        given(request.getRequestURI()).willReturn("/patient/2/diagnosis/5/addProcedure/");
        given(procedureService.createProcedure(any(),anyLong(),anyLong())).willReturn(true);
        given(procedureService.getNumberOfProceduresByDiagnosisId(5L)).willReturn(10L);
    }

    @Test
    public void testAddProcedure () throws IOException {

        ProcedureDTO procedure = new ProcedureDTO();
        procedure.setName("name");
        procedure.setDescription("description");
        procedure.setRoom(43);
        procedure.setAppointmentDates(new ArrayList<>());
        procedure.getAppointmentDates().add(LocalDateTime.now());

        String dtoJSON = gson.toJson(procedure);
        Reader strReader = new StringReader(dtoJSON);
        reader = new BufferedReader(strReader);
        when(request.getReader()).thenReturn(reader);

        CreationResponse expected = new CreationResponse("created",new ArrayList<>());

        CommandResponse response = addProcedure.execute(request);

        assertEquals(gson.toJson(expected), response.getResponse());
        assertEquals(200, response.getStatus());

        verify(procedureService,times(1)).createProcedure(dtoCaptor.capture(),eq(5L),eq(20L));
        assertEquals(procedure.getName(),dtoCaptor.getValue().getName());
        assertEquals(procedure.getDescription(),dtoCaptor.getValue().getDescription());
        assertEquals(procedure.getRoom(),dtoCaptor.getValue().getRoom());
        assertEquals(procedure.getAppointmentDates(),dtoCaptor.getValue().getAppointmentDates());
    }

    @Test
    public void testAddProcedureEmptyName () throws IOException {

        ProcedureDTO procedure = new ProcedureDTO();
        procedure.setName(null);
        procedure.setDescription("description");
        procedure.setRoom(43);
        procedure.setAppointmentDates(new ArrayList<>());
        procedure.getAppointmentDates().add(LocalDateTime.now());

        String dtoJSON = gson.toJson(procedure);
        Reader strReader = new StringReader(dtoJSON);
        reader = new BufferedReader(strReader);
        when(request.getReader()).thenReturn(reader);

        CreationResponse expected = new CreationResponse("creationFailed",new ArrayList<>());

        CommandResponse response = addProcedure.execute(request);
        CreationResponse creationResponse = gson.fromJson(response.getResponse(), CreationResponse.class);

        assertEquals(400, response.getStatus());
        assertEquals("creationFailed",creationResponse.getMessage());
        assertEquals("name",creationResponse.getErrors().get(0).getCause());

        verify(procedureService,times(0)).createProcedure(dtoCaptor.capture(),eq(5L),eq(20L));
    }

    @Test
    public void testAddProcedureEmptyCount () throws IOException {

        ProcedureDTO procedure = new ProcedureDTO();
        procedure.setName("name");
        procedure.setDescription("description");
        procedure.setRoom(null);
        procedure.setAppointmentDates(new ArrayList<>());
        procedure.getAppointmentDates().add(LocalDateTime.now());

        String dtoJSON = gson.toJson(procedure);
        Reader strReader = new StringReader(dtoJSON);
        reader = new BufferedReader(strReader);
        when(request.getReader()).thenReturn(reader);

        CreationResponse expected = new CreationResponse("creationFailed",new ArrayList<>());

        CommandResponse response = addProcedure.execute(request);
        CreationResponse creationResponse = gson.fromJson(response.getResponse(), CreationResponse.class);

        assertEquals(400, response.getStatus());
        assertEquals("creationFailed",creationResponse.getMessage());
        assertEquals("room",creationResponse.getErrors().get(0).getCause());

        verify(procedureService,times(0)).createProcedure(dtoCaptor.capture(),eq(5L),eq(20L));
    }

    @Test
    public void testAddProcedureMinusCount () throws IOException {

        ProcedureDTO procedure = new ProcedureDTO();
        procedure.setName("name");
        procedure.setDescription("description");
        procedure.setRoom(-1);
        procedure.setAppointmentDates(new ArrayList<>());
        procedure.getAppointmentDates().add(LocalDateTime.now());

        String dtoJSON = gson.toJson(procedure);
        Reader strReader = new StringReader(dtoJSON);
        reader = new BufferedReader(strReader);
        when(request.getReader()).thenReturn(reader);

        CreationResponse expected = new CreationResponse("creationFailed",new ArrayList<>());

        CommandResponse response = addProcedure.execute(request);
        CreationResponse creationResponse = gson.fromJson(response.getResponse(), CreationResponse.class);

        assertEquals(400, response.getStatus());
        assertEquals("creationFailed",creationResponse.getMessage());
        assertEquals("room",creationResponse.getErrors().get(0).getCause());

        verify(procedureService,times(0)).createProcedure(dtoCaptor.capture(),eq(5L),eq(20L));
    }

}