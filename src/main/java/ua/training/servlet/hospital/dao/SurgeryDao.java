package ua.training.servlet.hospital.dao;

import ua.training.servlet.hospital.entity.Surgery;

import java.util.List;

public interface SurgeryDao extends GenericDao<Surgery> {
    List<Surgery> findSurgeriesWithDoctorByDiagnosisId(int start, int count, long diagnosisId);
    long countSurgeriesOfDiagnosis(long diagnosisId);
}
