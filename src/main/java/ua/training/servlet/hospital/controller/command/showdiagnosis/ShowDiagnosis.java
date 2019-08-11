package ua.training.servlet.hospital.controller.command.showdiagnosis;

import ua.training.servlet.hospital.controller.command.Command;
import ua.training.servlet.hospital.controller.utilities.PaginationUtility;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.dto.CommandResponse;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisService;
import ua.training.servlet.hospital.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

import java.util.NoSuchElementException;

import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getDiagnosisId;
import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getUserId;

public class ShowDiagnosis implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private DiagnosisService diagnosisService = factory.getDiagnosisService();

    @Override
    public CommandResponse execute(HttpServletRequest request) {
        long idUser;
        long idDiagnosis;
        Diagnosis diagnosis;

        try{
            idUser = getUserId(request);
            idDiagnosis = getDiagnosisId(request);
            diagnosis = diagnosisService.getDiagnosis(idDiagnosis).orElseThrow(NoSuchElementException::new);
        }catch (NumberFormatException| NoSuchElementException ex){
            return new CommandResponse(404,"/notFoundPage.jsp");
        }

        request.setAttribute("diagnosis",diagnosis);

        return new CommandResponse(200,"/showDiagnosis.jsp");
    }
}
