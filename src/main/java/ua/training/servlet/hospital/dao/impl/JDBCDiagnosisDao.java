package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.DiagnosisDao;
import ua.training.servlet.hospital.dao.mapper.DiagnosisMapper;
import ua.training.servlet.hospital.entity.Diagnosis;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class JDBCDiagnosisDao extends JDBCGenericDao<Diagnosis> implements DiagnosisDao  {
    private final String findDiagnosesByPatientIdQuery = "SELECT * FROM diagnosis " +
            "LEFT JOIN user ON diagnosis.doctor_id_user = user.id_user " +
            "WHERE diagnosis.patient_id_user = ?  LIMIT ?,?";
    private final String countPatientsQuery = "SELECT COUNT(*)FROM diagnosis WHERE diagnosis.patient_id_user = ";
    private final String patientsCountLabel = "COUNT(*)";
    private final String closeDiagnosisQuery = "UPDATE diagnosis SET cured = ?" +
            "WHERE id_diagnosis = ?";

    public JDBCDiagnosisDao(Connection connection) {
        super(
                connection,
                "INSERT INTO diagnosis(name, description, assigned, cured, doctor_id_user, patient_id_user) VALUES(?,?,?,?,?,?)",
                "SELECT * FROM diagnosis " +
                        "LEFT JOIN user ON diagnosis.doctor_id_user = user.id_user " +
                        "WHERE id_diagnosis = ?",
                "SELECT SQL_CALC_FOUND_ROWS * FROM diagnosis LIMIT ?,?",
                "SELECT * FROM diagnosis " +
                        "LEFT JOIN user ON diagnosis.doctor_id_user = user.id_user ",
                "SELECT COUNT(*)FROM diagnosis ",
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
        statement.setObject(3, entity.getAssigned());
        statement.setObject(4, entity.getCured());
        statement.setLong(5,entity.getDoctor().getId());
        statement.setLong(6,entity.getPatient().getId());
    }

    @Override
    public List<Diagnosis> findDiagnosesByPatientId(int start, int count, long patientId) {
        List<Diagnosis> found = null;
        try (PreparedStatement statement = connection.prepareStatement(findDiagnosesByPatientIdQuery)){
            statement.setLong(1,patientId);
            statement.setInt(2,start);
            statement.setInt(3,count);
            found = getAllFromStatement(statement);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return found;
    }

    @Override
    public long countDiagnosesOfPatient(long patientId) {
        return count(countPatientsQuery + patientId,patientsCountLabel);
    }

    @Override
    public boolean closeDiagnosis(long idDiagnosis, LocalDateTime timeOfClose) {
        boolean changed = false;
        try (PreparedStatement statement = connection.prepareStatement(closeDiagnosisQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setObject(1,timeOfClose);
            statement.setObject(2,idDiagnosis);
                    changed = transaction(statement,(s)->{
                        return s.executeUpdate() == 1;
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return changed;
    }
}
