package ua.training.servlet.hospital.dao;

import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.ShowUserToDoctorDTO;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User>{
    Optional<User> findByEmail(String email);
    List<ShowUserToDoctorDTO> findPatientsForDoctorPage(int start, int count);
}
