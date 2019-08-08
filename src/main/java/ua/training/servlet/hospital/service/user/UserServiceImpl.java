package ua.training.servlet.hospital.service.user;

import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.servlet.hospital.entity.dto.UserDTO;
import ua.training.servlet.hospital.entity.exceptions.EmailExistsException;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private PasswordEncoder encoder;
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
        encoder = PasswordEncoder.getInstance();
    }

    @Override
    public Optional<User> getUser(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> getUser(long id) {
        return userDao.findById(id);
    }

    @Override
    public boolean registerUser(UserDTO userDto) {
        if (emailExists(userDto.getEmail())) {
            throw new EmailExistsException("There is an account with that email address:" + userDto.getEmail());
        }
        User userToCreate = new User();
        userToCreate.setName(userDto.getName());
        userToCreate.setSurname(userDto.getSurname());
        userToCreate.setPatronymic(userDto.getPatronymic());
        userToCreate.setEmail(userDto.getEmail());
        userToCreate.setPasswordHash(encodePassword(userDto.getPassword()));
        userToCreate.setRole(userDto.getRole());

        return userDao.create(userToCreate);
    }

    @Override
    public long getNumberOfRecords() {
        return userDao.count();
    }

    @Override
    public long getNumberOfPatients(){
        return userDao.countPatients();
    }

    @Override
    public List<ShowUserToDoctorDTO> findPatientsToShow(int pageNumber, int UsersPerPage) {
        return userDao.findPatientsForDoctorPage(pageNumber*UsersPerPage,UsersPerPage);
    }

    private String encodePassword(String password){
        return encoder.encode(password);
    }

    private boolean emailExists(String email){
        return userDao.findByEmail(email).isPresent();
    }
}
