package ua.training.servlet.hospital.dao.mapper;

import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MedicineMapper implements ObjectMapper<Medicine> {
    @Override
    public Medicine extractFromResultSet(ResultSet rs) throws SQLException {
        UserMapper userMapper = new UserMapper();

        Medicine medicine = new Medicine();
        medicine.setId(rs.getLong("medicine.id_therapy"));
        medicine.setName(rs.getString("medicine.name"));
        medicine.setDescription(rs.getString("medicine.description"));
        medicine.setAssigned(rs.getTimestamp("medicine.assigned").toLocalDateTime());
        medicine.setAssignedBy(userMapper.extractFromResultSet(rs));
        medicine.setCount(rs.getInt("medicine.count"));
        medicine.setRefill((LocalDate) rs.getDate("medicine.refill").toLocalDate());

        return medicine;
    }
}
