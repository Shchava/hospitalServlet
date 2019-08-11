package ua.training.servlet.hospital.dao;

import ua.training.servlet.hospital.entity.Diagnosis;

import java.util.List;

public interface DiagnosisDao extends GenericDao<Diagnosis> {
    List<Diagnosis> findDiagnosesByPatientId(int start, int count, long patientId);
    long countDiagnosesOfPatient(long patientId);
}
