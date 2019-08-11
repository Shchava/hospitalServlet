package ua.training.servlet.hospital.controller.command.showdiagnosis;

import com.google.gson.Gson;
import ua.training.servlet.hospital.controller.command.RestCommand;
import ua.training.servlet.hospital.controller.utilities.GsonFactory;
import ua.training.servlet.hospital.entity.dto.CommandResponse;
import ua.training.servlet.hospital.entity.dto.CreationError;
import ua.training.servlet.hospital.entity.dto.CreationResponse;
import ua.training.servlet.hospital.entity.dto.MedicineDTO;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.medicine.MedicineService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getDiagnosisIdOrAddError;
import static ua.training.servlet.hospital.controller.command.utilities.GetSessionAttribute.getUserId;

public class AddMedicine implements RestCommand {
    private Gson gson = GsonFactory.create();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private MedicineService medicineService = serviceFactory.getMedicineService();

    @Override
    public CommandResponse execute(HttpServletRequest request) {
        ResourceBundle errors = ResourceBundle.getBundle("bundles/ValidationMessages", request.getLocale());
        CreationResponse response = new CreationResponse();

        long diagnosisId = getDiagnosisIdOrAddError(request, response, errors);
        long userId = getUserId(request);
        MedicineDTO dto = getDto(request, response, errors);

        validateIfNeeded(dto, response, errors);

        createIfNoErrors(dto, diagnosisId, userId, response, errors);

        return createCommandResponse(response);
    }


    private MedicineDTO getDto(HttpServletRequest request, CreationResponse response, ResourceBundle errors) {
        MedicineDTO dto = null;
        try {
            dto = gson.fromJson(request.getReader(), MedicineDTO.class);
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
            response.addError(new CreationError("name", errors.getString("exceptionHandler.json.message")));
        }
        return dto;
    }

    private void validateIfNeeded(MedicineDTO dto, CreationResponse response, ResourceBundle errors) {
        if (response.getErrors().isEmpty()) {
            validateMedicineDTO(dto, response, errors);
        }
    }

    private void validateMedicineDTO(MedicineDTO dto, CreationResponse response, ResourceBundle errors) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            response.addError(new CreationError("name", errors.getString("therapy.name.empty")));
        }
        if (dto.getCount() == null) {
            response.addError(new CreationError("count", errors.getString("medicine.count.null")));
        } else {
            if (dto.getCount() < 0) {
                response.addError(new CreationError("count", errors.getString("medicine.count.negative")));
            }
        }
    }

    private void createIfNoErrors(MedicineDTO dto, long diagnosisId, long userId, CreationResponse response, ResourceBundle errors) {
        if (response.getErrors().isEmpty()) {
            createMedicine(dto, diagnosisId, userId, response, errors);
        }
    }

    private void createMedicine(MedicineDTO dto, long diagnosisId, long userId, CreationResponse response, ResourceBundle errors) {
        if (!medicineService.createMedicine(dto, diagnosisId, userId)) {
            response.addError(new CreationError("object", errors.getString("medicine.cannotCreate")));
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
