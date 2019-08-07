package ua.training.servlet.hospital.dao.mapper;

import ua.training.servlet.hospital.entity.dto.ShowUserToDoctorDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ShowUserToDoctorDTOMapper implements ObjectMapper<ShowUserToDoctorDTO> {
    @Override
    public ShowUserToDoctorDTO extractFromResultSet(ResultSet rs) throws SQLException {
        ShowUserToDoctorDTO dto = new ShowUserToDoctorDTO();
        dto.setId(rs.getLong("user.id_user"));
        dto.setName(rs.getString("user.name"));
        dto.setSurname(rs.getString("user.surname"));
        dto.setPatronymic(rs.getString("user.patronymic"));
        dto.setEmail(rs.getString("user.email"));
        dto.setLastDiagnosisName(rs.getString("diagnosis.name"));
        return dto;
    }
}
