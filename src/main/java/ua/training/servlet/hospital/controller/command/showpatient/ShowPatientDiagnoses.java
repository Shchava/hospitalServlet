package ua.training.servlet.hospital.controller.command.showpatient;

import ua.training.servlet.hospital.controller.command.Command;
import ua.training.servlet.hospital.controller.utilities.PaginationUtility;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.CommandResponse;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisService;
import ua.training.servlet.hospital.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;

import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getUserId;

public class ShowPatientDiagnoses implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private UserService userService = factory.getUserService();
    private DiagnosisService diagnosisService = factory.getDiagnosisService();
    private PaginationUtility pagination = new PaginationUtility();

    @Override
    public CommandResponse execute(HttpServletRequest request) {
        long idUser;
        User patient;

        try {
            idUser = getUserId(request);
            patient = userService.getUser(idUser).orElseThrow(NoSuchElementException::new);
        }catch (NumberFormatException|NoSuchElementException ex){
            return new CommandResponse(404,"/notFoundPage.jsp");
        }

        long diagnosesCount = diagnosisService.getNumberOfDiagnosesByPatientId(idUser);

        pagination.setAttributes(request,diagnosesCount);

        List<Diagnosis> diagnoses = diagnosisService.findDiagnosesByPatientId(pagination.getPage(),pagination.getRecordsPerPage(),idUser);

        request.setAttribute("patient",patient);
        request.setAttribute("diagnoses",diagnoses);

        return  new CommandResponse(200,"/showPatient.jsp");
    }


}
