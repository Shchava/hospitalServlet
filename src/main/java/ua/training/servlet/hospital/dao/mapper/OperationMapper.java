package ua.training.servlet.hospital.dao.mapper;

import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.Surgery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationMapper implements ObjectMapper<Surgery> {
    @Override
    public Surgery extractFromResultSet(ResultSet rs) throws SQLException {
        UserMapper userMapper = new UserMapper();

        Surgery surgery = new Surgery();
        surgery.setDiagnosis(new Diagnosis(rs.getLong("surgery.diagnosis")));
        surgery.setId(rs.getLong("surgery.id_therapy"));
        surgery.setName(rs.getString("surgery.name"));
        surgery.setDescription(rs.getString("surgery.description"));
        surgery.setAssigned(rs.getTimestamp("surgery.assigned").toLocalDateTime());
        surgery.setAssignedBy(userMapper.extractFromResultSet(rs));
        surgery.setDate(rs.getTimestamp("surgery.date").toLocalDateTime());

        return surgery;
    }
}
