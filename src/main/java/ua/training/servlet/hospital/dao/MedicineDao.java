package ua.training.servlet.hospital.dao;

import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.Medicine;

import java.util.List;

public interface MedicineDao extends GenericDao<Medicine> {
    List<Medicine> findMedicineWithDoctorByDiagnosisId(int start, int count, long diagnosisId);
    long countMedicinesOfDiagnosis(long diagnosisId);
}
