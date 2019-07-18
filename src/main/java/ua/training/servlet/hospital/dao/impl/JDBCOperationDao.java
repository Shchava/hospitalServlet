package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.OperationDao;
import ua.training.servlet.hospital.dao.mapper.OperationMapper;
import ua.training.servlet.hospital.entity.Operation;

import java.sql.*;

public class JDBCOperationDao extends JDBCGenericDao<Operation> implements OperationDao {

    public JDBCOperationDao(Connection connection) {
        super(
                connection,
                "INSERT INTO operation(name, description, assigned, assigned_by_id_user, date) VALUES(?,?,?,?,?)",
                "SELECT * FROM operation LEFT JOIN user ON(operation.assigned_by_id_user = user.id_user) WHERE id_therapy = ?",
                "SELECT SQL_CALC_FOUND_ROWS * FROM operation LEFT JOIN user ON(operation.assigned_by_id_user = user.id_user) LIMIT ?,?",
                "SELECT * FROM operation LEFT JOIN user ON(operation.assigned_by_id_user = user.id_user)",
                "SELECT COUNT(*)FROM operation",
                "COUNT(*)",
                "UPDATE operation SET name = ?, description = ?, assigned = ?, assigned_by_id_user = ?, date = ? WHERE id_therapy = ?",
                6,
                "DELETE FROM operation WHERE id_therapy = ?",
                new OperationMapper());
    }

    @Override
    long getId(Operation entity) {
        return entity.getId();
    }

    @Override
    void setId(Operation entity, long Id) throws SQLException {
        entity.setId(Id);
    }


    @Override
    void setEntityValues(PreparedStatement statement, Operation entity) throws SQLException {
        statement.setString(1,entity.getName());
        statement.setString(2,entity.getDescription());
        statement.setTimestamp(3,Timestamp.valueOf(entity.getAssigned()));
        statement.setLong(4,entity.getAssignedBy().getId());
        statement.setObject(5,entity.getDate());
    }
}
