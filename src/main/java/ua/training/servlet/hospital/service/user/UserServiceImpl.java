package ua.training.servlet.hospital.service.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.servlet.hospital.entity.dto.UserDTO;
import ua.training.servlet.hospital.entity.exceptions.EmailExistsException;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private PasswordEncoder encoder;
    private DaoFactory daoFactory;

    public UserServiceImpl(DaoFactory daoFactory) {

        this.daoFactory = daoFactory;
        encoder = PasswordEncoder.getInstance();
    }

    @Override
    public Optional<User> getUser(String email) {
        logger.debug("searching for user with email: " + email);
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findByEmail(email);
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        logger.debug("searching for user with id: " + id);
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findById(id);
        }
    }

    @Override
    public boolean registerUser(UserDTO userDto) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            if (emailExists(userDto.getEmail(),userDao)) {
                logger.info("tried to register user with existing email, throwing EmailExistsException");
                throw new EmailExistsException("There is an account with that email address:" + userDto.getEmail());
            }
            logger.info("trying to create user with email" + userDto.getEmail());
            User userToCreate = new User();
            userToCreate.setName(userDto.getName());
            userToCreate.setSurname(userDto.getSurname());
            userToCreate.setPatronymic(userDto.getPatronymic());
            userToCreate.setEmail(userDto.getEmail());
            userToCreate.setPasswordHash(encodePassword(userDto.getPassword()));
            userToCreate.setRole(userDto.getRole());

            return userDao.create(userToCreate);
        }
    }

    @Override
    public long getNumberOfRecords() {
        logger.debug("searching for number of users");
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.count();
        }
    }

    @Override
    public long getNumberOfPatients() {
        logger.debug("searching for number of patients");
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.countPatients();
        }
    }

    @Override
    public List<ShowUserToDoctorDTO> findPatientsToShow(int pageNumber, int UsersPerPage) {
        logger.debug("searching for patients and showing them as ShowUserToDoctorDTO from page " + pageNumber + " with "
                + UsersPerPage + "entries on page");
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findPatientsForDoctorPage(pageNumber * UsersPerPage, UsersPerPage);
        }
    }

    private String encodePassword(String password) {
        return encoder.encode(password);
    }

    private boolean emailExists(String email,UserDao userDao) {
        return userDao.findByEmail(email).isPresent();
    }
}
