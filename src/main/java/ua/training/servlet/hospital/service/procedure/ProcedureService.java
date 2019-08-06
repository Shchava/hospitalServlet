package ua.training.servlet.hospital.service.procedure;

import ua.training.servlet.hospital.entity.Procedure;
import ua.training.servlet.hospital.entity.dto.ProcedureDTO;

import java.util.List;
import java.util.Optional;

public interface ProcedureService {
    List<Procedure> findProceduresByDiagnosisId(int pageNumber, int ProcedurePerPage, long diagnosisId);
    Optional<Procedure> createProcedure(ProcedureDTO dto, long diagnosisId, String doctorEmail);
}
