package ua.training.servlet.hospital.service.user;

import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.servlet.hospital.entity.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String email);
    Optional<User> getUser(long id);
    boolean registerUser(UserDTO userDto);
    long getNumberOfRecords();
    List<ShowUserToDoctorDTO> findPatientsToShow(int pageNumber, int UsersPerPage);
    long getNumberOfPatients();
}
