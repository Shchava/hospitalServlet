package ua.training.servlet.hospital.service.procedure;


import ua.training.servlet.hospital.dao.ProcedureDao;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.Procedure;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.ProcedureDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ProcedureServiceImpl implements ProcedureService {
    private ProcedureDao procedureDao;

    public ProcedureServiceImpl(ProcedureDao procedureDao) {
        this.procedureDao = procedureDao;
    }

    @Override
    public long getNumberOfProceduresByDiagnosisId(long diagnosisId) {
        return procedureDao.countProceduresOfDiagnosis(diagnosisId);
    }

    @Override
    public List<Procedure> findProceduresByDiagnosisId(int pageNumber, int procedurePerPage, long diagnosisId) {
        return procedureDao.findProceduresWithDoctorByDiagnosisId(pageNumber*procedurePerPage, procedurePerPage,diagnosisId);
    }

    @Override
    public boolean createProcedure(ProcedureDTO dto, long diagnosisId, long doctorId) {
        Procedure toCreate = new Procedure();
        toCreate.setName(dto.getName());
        toCreate.setDescription(dto.getDescription());
        toCreate.setRoom(dto.getRoom());
        toCreate.setAppointmentDates(dto.getAppointmentDates());
        toCreate.setAssigned(getAssignedTime());
        toCreate.setDiagnosis(new Diagnosis(doctorId));
        toCreate.setAssignedBy(new User(doctorId));


        return procedureDao.create(toCreate);
    }


    private LocalDateTime getAssignedTime() {
        return LocalDateTime.now();
    }
}
