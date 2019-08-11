package ua.training.servlet.hospital.controller.command.showpatient;

import ua.training.servlet.hospital.controller.command.Command;
import ua.training.servlet.hospital.entity.dto.CommandResponse;
import ua.training.servlet.hospital.entity.dto.DiagnosisDTO;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisService;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

import static java.util.Objects.isNull;
import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getUserId;

public class AddDiagnosis implements Command {
    private boolean allMatches;
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private DiagnosisService diagnosisService = serviceFactory.getDiagnosisService();
    private ShowPatientDiagnoses loadDiagnoses = new ShowPatientDiagnoses();

    @Override
    public CommandResponse execute(HttpServletRequest request) {
        allMatches = true;

        long idUser;
        long doctorId;

        try {
            idUser = getUserId(request);
            doctorId = ua.training.servlet.hospital.controller.command.utilities.GetSessionAttribute.getUserId(request);
        } catch (NumberFormatException | NoSuchElementException ex) {
            return new CommandResponse(404, "/notFoundPage.jsp");
        }

        DiagnosisDTO dto = getValidatedDto(request);

        boolean registered = createAndAddErrorAttributes(request, dto, idUser, doctorId);

        //load diagnoses
        CommandResponse load = loadDiagnoses.execute(request);

        if (registered) {
            return createSuccessResponse(request,load.getResponse());
        }
        return createErrorResponse(request, dto,load.getResponse());
    }

    private DiagnosisDTO getValidatedDto(HttpServletRequest request) {
        DiagnosisDTO diagnosisDTO = null;
        diagnosisDTO = new DiagnosisDTO(
                request.getParameter("name"),
                request.getParameter("description"));

        validateDiagnosis(request, diagnosisDTO);
        return diagnosisDTO;
    }

    private void validateDiagnosis(HttpServletRequest request, DiagnosisDTO dto) {
        if (isNull(dto.getName()) || dto.getName().isEmpty()) {
            request.setAttribute("nameEmpty", true);
            allMatches = false;
        }
    }

    private boolean createAndAddErrorAttributes(HttpServletRequest request, DiagnosisDTO diagnosisDTO, long idUser, long doctorId) {
        if (allMatches) {
            if (diagnosisService.addDiagnosis(diagnosisDTO, idUser, doctorId)) {
                return true;
            }
            request.setAttribute("creationError", true);
        }
        return false;
    }

    private CommandResponse createSuccessResponse(HttpServletRequest request, String path) {
        request.setAttribute("created", true);
        return new CommandResponse(200, path);
    }

    private CommandResponse createErrorResponse(HttpServletRequest request, DiagnosisDTO dto, String path) {
        request.setAttribute("values", dto);
        return new CommandResponse(400, path);
    }
}
