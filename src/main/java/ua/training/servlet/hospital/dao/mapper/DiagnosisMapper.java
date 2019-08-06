package ua.training.servlet.hospital.dao.mapper;

import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class DiagnosisMapper implements ObjectMapper<Diagnosis>{
    @Override
    public Diagnosis extractFromResultSet(ResultSet rs) throws SQLException {

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setIdDiagnosis(rs.getLong("diagnosis.id_diagnosis"));
        diagnosis.setName(rs.getString("diagnosis.name"));
        diagnosis.setDescription(rs.getString("diagnosis.description"));
        diagnosis.setAssigned(rs.getTimestamp("diagnosis.assigned").toLocalDateTime());
        Timestamp cured = rs.getTimestamp("diagnosis.cured");
        if(cured != null){
            diagnosis.setCured(cured.toLocalDateTime());
        }else{
            diagnosis.setCured(null);
        }
        User doctor = new User();
        doctor.setId(rs.getLong("diagnosis.doctor_id_user"));
        diagnosis.setDoctor(doctor);
        User patient = new User();
        patient.setId(rs.getLong("diagnosis.patient_id_user"));
        diagnosis.setPatient(patient);
        return diagnosis;
    }
}
