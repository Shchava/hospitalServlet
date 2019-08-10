package ua.training.servlet.hospital.dao.mapper;

import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class MedicineMapper implements ObjectMapper<Medicine> {
    @Override
    public Medicine extractFromResultSet(ResultSet rs) throws SQLException {
        UserMapper userMapper = new UserMapper();

        Medicine medicine = new Medicine();
        medicine.setDiagnosis(new Diagnosis(rs.getLong("medicine.diagnosis")));
        medicine.setId(rs.getLong("medicine.id_therapy"));
        medicine.setName(rs.getString("medicine.name"));
        medicine.setDescription(rs.getString("medicine.description"));
        medicine.setAssigned(rs.getTimestamp("medicine.assigned").toLocalDateTime());
        medicine.setAssignedBy(userMapper.extractFromResultSet(rs));
        medicine.setCount(rs.getInt("medicine.count"));
        Date refill = rs.getDate("medicine.refill");
        if(Objects.nonNull(refill)){
            medicine.setRefill(refill.toLocalDate());
        }else{
            medicine.setRefill(null);
        }


        return medicine;
    }
}
