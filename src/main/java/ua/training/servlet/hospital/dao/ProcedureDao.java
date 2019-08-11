package ua.training.servlet.hospital.dao;

import ua.training.servlet.hospital.entity.Procedure;

import java.util.List;

public interface ProcedureDao extends GenericDao<Procedure> {
    List<Procedure> findProceduresWithDoctorByDiagnosisId(int start, int count, long diagnosisId);
    long countProceduresOfDiagnosis(long diagnosisId);
}
