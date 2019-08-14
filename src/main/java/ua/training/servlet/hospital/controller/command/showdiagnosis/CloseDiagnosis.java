package ua.training.servlet.hospital.controller.command.showdiagnosis;

import com.google.gson.Gson;
import ua.training.servlet.hospital.controller.command.RestCommand;
import ua.training.servlet.hospital.controller.utilities.gson.GsonFactory;
import ua.training.servlet.hospital.entity.dto.ClosingResponse;
import ua.training.servlet.hospital.entity.dto.CommandResponse;
import ua.training.servlet.hospital.entity.dto.CreationError;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisService;
import ua.training.servlet.hospital.service.surgery.SurgeryService;

import javax.servlet.http.HttpServletRequest;

import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getDiagnosisId;

public class CloseDiagnosis implements RestCommand {
    private Gson gson = GsonFactory.create();
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private DiagnosisService diagnosisService = serviceFactory.getDiagnosisService();


    @Override
    public CommandResponse execute(HttpServletRequest request) {
        long idDiagnosis;
        try{
            idDiagnosis =  getDiagnosisId(request);
        }catch (NumberFormatException ex){
            ex.printStackTrace();
            return new CommandResponse(400,gson.toJson(new ClosingResponse("cant parse diagnosis id")));
        }

        if(diagnosisService.closeDiagnosis(idDiagnosis)){
//            logger.info("diagnosis with id: " + idDiagnosis + " closed");
            return new CommandResponse(200,gson.toJson(new ClosingResponse("closed")));
        }else{
//            logger.info("cant close diagnosis with id: "+ idDiagnosis);
            return new CommandResponse(400,gson.toJson(new ClosingResponse("cantClose")));
        }
    }
}
