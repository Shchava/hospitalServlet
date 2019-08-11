package ua.training.servlet.hospital.controller.command.showdiagnosis;

import com.google.gson.Gson;
import ua.training.servlet.hospital.controller.command.RestCommand;
import ua.training.servlet.hospital.controller.utilities.gson.GsonFactory;
import ua.training.servlet.hospital.entity.dto.*;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.procedure.ProcedureService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getDiagnosisIdOrAddError;
import static ua.training.servlet.hospital.controller.command.utilities.GetSessionAttribute.getUserId;

public class AddProcedure implements RestCommand {
    private Gson gson = GsonFactory.create();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private ProcedureService procedureService = serviceFactory.getProcedureService();

    @Override
    public CommandResponse execute(HttpServletRequest request) {
        ResourceBundle errors = ResourceBundle.getBundle("bundles/ValidationMessages", request.getLocale());
        CreationResponse response = new CreationResponse();

        long diagnosisId = getDiagnosisIdOrAddError(request, response, errors);
        long userId = getUserId(request);
        ProcedureDTO dto = getDto(request, response, errors);

        validateIfNeeded(dto, response, errors);

        createIfNoErrors(dto, diagnosisId, userId, response, errors);

        return createCommandResponse(response);
    }


    private ProcedureDTO getDto(HttpServletRequest request, CreationResponse response, ResourceBundle errors) {
        ProcedureDTO dto = null;
        try {
            dto = gson.fromJson(request.getReader(), ProcedureDTO.class);
        } catch (IOException | NumberFormatException| DateTimeParseException e) {
            e.printStackTrace();
            response.addError(new CreationError("name", errors.getString("exceptionHandler.json.message")));
        }
        return dto;
    }

    private void validateIfNeeded(ProcedureDTO dto, CreationResponse response, ResourceBundle errors) {
        if (response.getErrors().isEmpty()) {
            validateProcedureDTO(dto, response, errors);
        }
    }

    private void validateProcedureDTO(ProcedureDTO dto, CreationResponse response, ResourceBundle errors) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            response.addError(new CreationError("name", errors.getString("therapy.name.empty")));
        }
        if (dto.getRoom() == null) {
            response.addError(new CreationError("room", errors.getString("procedure.room.null")));
        } else {
            if (dto.getRoom() < 0) {
                response.addError(new CreationError("room", errors.getString("procedure.room.negative")));
            }
        }
    }

    private void createIfNoErrors(ProcedureDTO dto, long diagnosisId, long userId, CreationResponse response, ResourceBundle errors) {
        if (response.getErrors().isEmpty()) {
            createProcedure(dto, diagnosisId, userId, response, errors);
        }
    }

    private void createProcedure(ProcedureDTO dto, long diagnosisId, long userId, CreationResponse response, ResourceBundle errors) {
        if (!procedureService.createProcedure(dto, diagnosisId, userId)) {
            response.addError(new CreationError("object", errors.getString("procedure.cannotCreate")));
        }
    }

    private CommandResponse createCommandResponse(CreationResponse response){
        if(response.getErrors().isEmpty()){
            response.setMessage("created");
            return new CommandResponse(200,gson.toJson(response));
        }else{
            response.setMessage("creationFailed");
            return new CommandResponse(400,gson.toJson(response));
        }
    }
}
