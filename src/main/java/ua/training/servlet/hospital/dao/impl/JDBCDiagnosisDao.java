package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.DiagnosisDao;
import ua.training.servlet.hospital.dao.mapper.DiagnosisMapper;
import ua.training.servlet.hospital.entity.Diagnosis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class JDBCDiagnosisDao extends JDBCGenericDao<Diagnosis> implements DiagnosisDao  {

    public JDBCDiagnosisDao(Connection connection) {
        super(
                connection,
                "INSERT INTO diagnosis(name, description, assigned, cured, doctor_id_user, patient_id_user) VALUES(?,?,?,?,?,?)",
                "SELECT * FROM diagnosis WHERE id_diagnosis = ?",
                "SELECT SQL_CALC_FOUND_ROWS * FROM diagnosis LIMIT ?,?",
                "SELECT * FROM diagnosis",
                "SELECT COUNT(*)FROM diagnosis",
                "COUNT(*)",
                "UPDATE diagnosis SET name = ?, description = ?, assigned = ?, cured = ?, doctor_id_user = ?, patient_id_user = ?  WHERE id_diagnosis = ?",
                7,
                "DELETE FROM diagnosis WHERE id_diagnosis = ?",
                new DiagnosisMapper());
    }

    @Override
    long getId(Diagnosis entity) {
        return entity.getIdDiagnosis();
    }

    @Override
    void setId(Diagnosis entity, long Id) throws SQLException {
        entity.setIdDiagnosis(Id);
    }

    @Override
    void setEntityValues(PreparedStatement statement, Diagnosis entity) throws SQLException{
        statement.setString(1,entity.getName());
        statement.setString(2,entity.getDescription());
        statement.setTimestamp(3, Timestamp.valueOf(entity.getAssigned()));
        statement.setTimestamp(4, Timestamp.valueOf(entity.getCured()));
        statement.setLong(5,entity.getDoctor().getId());
        statement.setLong(6,entity.getPatient().getId());
    }
}
