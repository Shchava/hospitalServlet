package ua.training.servlet.hospital.dao.mapper;

import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.Procedure;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcedureMapper implements ObjectMapper<Procedure> {
    @Override
    public Procedure extractFromResultSet(ResultSet rs) throws SQLException {
        UserMapper userMapper = new UserMapper();

        Procedure procedure = new Procedure();
        procedure.setDiagnosis(new Diagnosis(rs.getLong("treatment.diagnosis")));
        procedure.setId(rs.getLong("treatment.id_therapy"));
        procedure.setName(rs.getString("treatment.name"));
        procedure.setDescription(rs.getString("treatment.description"));
        procedure.setAssigned(rs.getTimestamp("treatment.assigned").toLocalDateTime());
        procedure.setAssignedBy(userMapper.extractFromResultSet(rs));
        procedure.setRoom(rs.getInt("treatment.room"));
        return procedure;
    }
}
