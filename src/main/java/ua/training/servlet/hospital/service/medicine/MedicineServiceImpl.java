package ua.training.servlet.hospital.service.medicine;

import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.MedicineDao;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.MedicineDTO;

import java.time.LocalDateTime;
import java.util.List;

public class MedicineServiceImpl implements MedicineService {
    private DaoFactory factory;

    public MedicineServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public long getNumberOfMedicineByDiagnosisId(long diagnosisId) {
        try (MedicineDao medicineDao = factory.createMedicineDao()) {
            return medicineDao.countMedicinesOfDiagnosis(diagnosisId);
        }
    }

    @Override
    public List<Medicine> findMedicineByDiagnosisId(int pageNumber, int MedicinePerPage, long diagnosisId) {
        try (MedicineDao medicineDao = factory.createMedicineDao()) {
            return medicineDao.findMedicineWithDoctorByDiagnosisId(pageNumber * MedicinePerPage, MedicinePerPage, diagnosisId);
        }
    }

    @Override
    public boolean createMedicine(MedicineDTO dto, long diagnosisId, long doctorId) {
        User assignedBy = new User(doctorId);

        Medicine toCreate = new Medicine();
        toCreate.setName(dto.getName());
        toCreate.setDescription(dto.getDescription());
        toCreate.setCount(dto.getCount());
        toCreate.setRefill(dto.getRefill());
        toCreate.setAssigned(getAssignedTime());
        toCreate.setDiagnosis(new Diagnosis(diagnosisId));
        toCreate.setAssignedBy(assignedBy);

        try (MedicineDao medicineDao = factory.createMedicineDao()) {
            return medicineDao.create(toCreate);
        }
    }

    private LocalDateTime getAssignedTime() {
        return LocalDateTime.now();
    }
}
