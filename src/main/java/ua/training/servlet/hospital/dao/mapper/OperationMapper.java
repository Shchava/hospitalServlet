package ua.training.servlet.hospital.dao.mapper;

import ua.training.servlet.hospital.entity.Operation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationMapper implements ObjectMapper<Operation> {
    @Override
    public Operation extractFromResultSet(ResultSet rs) throws SQLException {
        UserMapper userMapper = new UserMapper();

        Operation operation = new Operation();
        operation.setId(rs.getLong("operation.id_therapy"));
        operation.setName(rs.getString("operation.name"));
        operation.setDescription(rs.getString("operation.description"));
        operation.setAssigned(rs.getTimestamp("operation.assigned").toLocalDateTime());
        operation.setAssignedBy(userMapper.extractFromResultSet(rs));
        operation.setDate(rs.getTimestamp("operation.date").toLocalDateTime());

        return operation;
    }
}
