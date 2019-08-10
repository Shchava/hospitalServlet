package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.SurgeryDao;
import ua.training.servlet.hospital.dao.mapper.OperationMapper;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.Surgery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSurgeryDao extends JDBCGenericDao<Surgery> implements SurgeryDao {
    private final String findSurgeriesByDiagnosisIdQuery =
            "SELECT * FROM surgery " +
                    "LEFT JOIN user ON user.id_user = surgery.assigned_by_id_user " +
                    "WHERE surgery.diagnosis = ?  LIMIT ?,?";
    private final String countSurgeriesByDiagnosisQuery = "SELECT COUNT(*)FROM surgery WHERE diagnosis = ";
    private final String surgeriesCountLabel = "COUNT(*)";

    public JDBCSurgeryDao(Connection connection) {
        super(
                connection,
                "INSERT INTO surgery(diagnosis, name, description, assigned, assigned_by_id_user, date) VALUES(?,?,?,?,?,?)",
                "SELECT * FROM surgery LEFT JOIN user ON(surgery.assigned_by_id_user = user.id_user) WHERE id_therapy = ?",
                "SELECT SQL_CALC_FOUND_ROWS * FROM surgery LEFT JOIN user ON(surgery.assigned_by_id_user = user.id_user) LIMIT ?,?",
                "SELECT * FROM surgery LEFT JOIN user ON(surgery.assigned_by_id_user = user.id_user)",
                "SELECT COUNT(*)FROM surgery",
                "COUNT(*)",
                "UPDATE surgery SET diagnosis = ?, name = ?, description = ?, assigned = ?, assigned_by_id_user = ?, date = ? WHERE id_therapy = ?",
                7,
                "DELETE FROM surgery WHERE id_therapy = ?",
                new OperationMapper());
    }

    @Override
    long getId(Surgery entity) {
        return entity.getId();
    }

    @Override
    void setId(Surgery entity, long Id) throws SQLException {
        entity.setId(Id);
    }


    @Override
    void setEntityValues(PreparedStatement statement, Surgery entity) throws SQLException {
        statement.setLong(1,entity.getDiagnosis().getIdDiagnosis());
        statement.setString(2,entity.getName());
        statement.setString(3,entity.getDescription());
        statement.setTimestamp(4,Timestamp.valueOf(entity.getAssigned()));
        statement.setLong(5,entity.getAssignedBy().getId());
        statement.setObject(6,entity.getDate());
    }

    @Override
    public List<Surgery> findSurgeriesWithDoctorByDiagnosisId(int start, int count, long diagnosisId) {
        List<Surgery> found = null;
        try (PreparedStatement statement = connection.prepareStatement(findSurgeriesByDiagnosisIdQuery)){
            statement.setLong(1,diagnosisId);
            statement.setInt(2,start);
            statement.setInt(3,count);
            found = getAllFromStatement(statement);
        }catch (Exception ex){
            ex.printStackTrace();
            found = new ArrayList<>();
        }
        return found;
    }

    @Override
    public long countSurgeriesOfDiagnosis(long diagnosisId) {
        return count(countSurgeriesByDiagnosisQuery + diagnosisId,surgeriesCountLabel);
    }
}
