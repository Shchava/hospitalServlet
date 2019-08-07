package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.MedicineDao;
import ua.training.servlet.hospital.dao.mapper.MedicineMapper;
import ua.training.servlet.hospital.entity.Medicine;

import java.sql.*;

public class JDBCMedicineDao extends JDBCGenericDao<Medicine> implements MedicineDao {
    public JDBCMedicineDao(Connection connection) {
        super(
                connection,
                "INSERT INTO medicine(diagnosis, name, description, assigned, assigned_by_id_user, count, refill) VALUES(?,?,?,?,?,?,?)",
                "SELECT * FROM medicine LEFT JOIN user ON(medicine.assigned_by_id_user = user.id_user) WHERE id_therapy = ?",
                "SELECT SQL_CALC_FOUND_ROWS * FROM medicine LEFT JOIN user ON(medicine.assigned_by_id_user = user.id_user) LIMIT ?,?",
                "SELECT * FROM medicine LEFT JOIN user ON(medicine.assigned_by_id_user = user.id_user)",
                "SELECT COUNT(*)FROM medicine",
                "COUNT(*)",
                "UPDATE medicine SET diagnosis = ?, name = ?, description = ?, assigned = ?, assigned_by_id_user = ?, count = ?, refill = ? WHERE id_therapy = ?",
                8,
                "DELETE FROM medicine WHERE id_therapy = ?",
                new MedicineMapper());
    }

    @Override
    long getId(Medicine entity) {
        return entity.getId();
    }

    @Override
    void setId(Medicine entity, long Id) throws SQLException {
        entity.setId(Id);
    }

    @Override
    void setEntityValues(PreparedStatement statement, Medicine entity) throws SQLException{
        statement.setLong(1,entity.getDiagnosis());
        statement.setString(2,entity.getName());
        statement.setString(3,entity.getDescription());
        statement.setTimestamp(4,Timestamp.valueOf(entity.getAssigned()));
        statement.setLong(5,entity.getAssignedBy().getId());
        statement.setInt(6,entity.getCount());
        statement.setObject(7,entity.getRefill());
    }
}
