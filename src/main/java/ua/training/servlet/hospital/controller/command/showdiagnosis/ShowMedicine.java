package ua.training.servlet.hospital.controller.command.showdiagnosis;

import com.google.gson.Gson;
import ua.training.servlet.hospital.controller.command.RestCommand;
import ua.training.servlet.hospital.controller.utilities.PaginationUtility;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.dto.Page;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.medicine.MedicineService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.List;
import java.util.NoSuchElementException;

import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getDiagnosisId;
import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getUserId;

public class ShowMedicine implements RestCommand {
    private Gson gson = new Gson();
    private ServiceFactory factory = ServiceFactory.getInstance();
    private MedicineService medicineService = factory.getMedicineService();
    private PaginationUtility utility = new PaginationUtility();


    @Override
    public String execute(HttpServletRequest request) {
        long idUser;
        long idDiagnosis;

        try{
            idUser = getUserId(request);
            idDiagnosis = getDiagnosisId(request);
        }catch (NumberFormatException ex){
            return "/notFoundPage.jsp";
        }

        long medicineCount = medicineService.getNumberOfMedicineByDiagnosisId(idDiagnosis);

        utility.init(request,medicineCount);
        List<Medicine> requestedMedicines = medicineService.findMedicineByDiagnosisId(utility.getPage(),utility.getRecordsPerPage(),idDiagnosis);

        Page page = utility.createPage(requestedMedicines);

        return gson.toJson(page);
    }

}