package ua.training.servlet.hospital.controller.command.showdiagnosis;

import com.google.gson.Gson;
import ua.training.servlet.hospital.controller.command.RestCommand;
import ua.training.servlet.hospital.controller.utilities.GsonFactory;
import ua.training.servlet.hospital.controller.utilities.PaginationUtility;
import ua.training.servlet.hospital.entity.Procedure;
import ua.training.servlet.hospital.entity.dto.CommandResponse;
import ua.training.servlet.hospital.entity.dto.Page;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.procedure.ProcedureService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getDiagnosisId;
import static ua.training.servlet.hospital.controller.command.utilities.GetPathAttribute.getUserId;

public class ShowProcedures implements RestCommand {
    private Gson gson = GsonFactory.create();
    private ServiceFactory factory = ServiceFactory.getInstance();
    private ProcedureService procedureService = factory.getProcedureService();
    private PaginationUtility utility = new PaginationUtility();


    @Override
    public CommandResponse execute(HttpServletRequest request) {
        long idUser;
        long idDiagnosis;

        try {
            idUser = getUserId(request);
            idDiagnosis = getDiagnosisId(request);
        } catch (NumberFormatException ex) {
            return new CommandResponse(400,ex.getLocalizedMessage());
        }

        long procedureCount = procedureService.getNumberOfProceduresByDiagnosisId(idDiagnosis);

        utility.init(request, procedureCount);
        List<Procedure> requestedMedicines = procedureService.findProceduresByDiagnosisId(utility.getPage(), utility.getRecordsPerPage(), idDiagnosis);

        Page page = utility.createPage(requestedMedicines);

        return new CommandResponse(200, gson.toJson(page));
    }

}