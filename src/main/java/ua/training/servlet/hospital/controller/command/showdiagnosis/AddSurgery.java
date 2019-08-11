package ua.training.servlet.hospital.controller.command.showdiagnosis;

import com.google.gson.Gson;
import ua.training.servlet.hospital.controller.command.RestCommand;
import ua.training.servlet.hospital.controller.utilities.GsonFactory;
import ua.training.servlet.hospital.entity.dto.*;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.surgery.SurgeryService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getDiagnosisIdOrAddError;
import static ua.training.servlet.hospital.controller.command.utilities.GetSessionAttribute.getUserId;

public class AddSurgery implements RestCommand {
    private Gson gson = GsonFactory.create();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private SurgeryService surgeryService = serviceFactory.getSurgeryService();

    @Override
    public CommandResponse execute(HttpServletRequest request) {
        ResourceBundle errors = ResourceBundle.getBundle("bundles/ValidationMessages", request.getLocale());
        CreationResponse response = new CreationResponse();

        long diagnosisId = getDiagnosisIdOrAddError(request, response, errors);
        long userId = getUserId(request);
        SurgeryDTO dto = getDto(request, response, errors);

        validateIfNeeded(dto, response, errors);

        createIfNoErrors(dto, diagnosisId, userId, response, errors);

        return createCommandResponse(response);
    }


    private SurgeryDTO getDto(HttpServletRequest request, CreationResponse response, ResourceBundle errors) {
        SurgeryDTO dto = null;
        try {
            dto = gson.fromJson(request.getReader(), SurgeryDTO.class);
        } catch (IOException | NumberFormatException| DateTimeParseException e) {
            e.printStackTrace();
            response.addError(new CreationError("name", errors.getString("exceptionHandler.json.message")));
        }
        return dto;
    }

    private void validateIfNeeded(SurgeryDTO dto, CreationResponse response, ResourceBundle errors) {
        if (response.getErrors().isEmpty()) {
            validateSurgeryDTO(dto, response, errors);
        }
    }

    private void validateSurgeryDTO(SurgeryDTO dto, CreationResponse response, ResourceBundle errors) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            response.addError(new CreationError("name", errors.getString("therapy.name.empty")));
        }
        if (dto.getSurgeryDate() == null) {
            response.addError(new CreationError("surgeryDate", errors.getString("surgery.date.null")));
        }
    }

    private void createIfNoErrors(SurgeryDTO dto, long diagnosisId, long userId, CreationResponse response, ResourceBundle errors) {
        if (response.getErrors().isEmpty()) {
            createSurgery(dto, diagnosisId, userId, response, errors);
        }
    }

    private void createSurgery(SurgeryDTO dto, long diagnosisId, long userId, CreationResponse response, ResourceBundle errors) {
        if (!surgeryService.createSurgery(dto, diagnosisId, userId)) {
            response.addError(new CreationError("object", errors.getString("surgery.cannotCreate")));
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