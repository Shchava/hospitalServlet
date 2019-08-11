package ua.training.servlet.hospital.service.procedure;


import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.ProcedureDao;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.Procedure;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.ProcedureDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ProcedureServiceImpl implements ProcedureService {
    private DaoFactory factory;

    public ProcedureServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public long getNumberOfProceduresByDiagnosisId(long diagnosisId) {
        try (ProcedureDao procedureDao = factory.createProcedureDao()) {
            return procedureDao.countProceduresOfDiagnosis(diagnosisId);
        }
    }

    @Override
    public List<Procedure> findProceduresByDiagnosisId(int pageNumber, int procedurePerPage, long diagnosisId) {
        try (ProcedureDao procedureDao = factory.createProcedureDao()) {
            return procedureDao.findProceduresWithDoctorByDiagnosisId(pageNumber * procedurePerPage, procedurePerPage, diagnosisId);
        }
    }

    @Override
    public boolean createProcedure(ProcedureDTO dto, long diagnosisId, long doctorId) {
        Procedure toCreate = new Procedure();
        toCreate.setName(dto.getName());
        toCreate.setDescription(dto.getDescription());
        toCreate.setRoom(dto.getRoom());
        toCreate.setAppointmentDates(dto.getAppointmentDates());
        toCreate.setAssigned(getAssignedTime());
        toCreate.setDiagnosis(new Diagnosis(diagnosisId));
        toCreate.setAssignedBy(new User(doctorId));

        try (ProcedureDao procedureDao = factory.createProcedureDao()) {
            return procedureDao.create(toCreate);
        }
    }


    private LocalDateTime getAssignedTime() {
        return LocalDateTime.now();
    }
}
