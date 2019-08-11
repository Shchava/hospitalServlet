package ua.training.servlet.hospital.service.procedure;

import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.Procedure;
import ua.training.servlet.hospital.entity.dto.MedicineDTO;
import ua.training.servlet.hospital.entity.dto.ProcedureDTO;

import java.util.List;
import java.util.Optional;

public interface ProcedureService {
    long getNumberOfProceduresByDiagnosisId(long diagnosisId);
    List<Procedure> findProceduresByDiagnosisId(int pageNumber, int ProcedurePerPage, long diagnosisId);
    boolean createProcedure(ProcedureDTO dto, long diagnosisId, long doctorId);
}
