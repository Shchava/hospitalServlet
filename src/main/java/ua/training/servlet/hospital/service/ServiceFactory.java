package ua.training.servlet.hospital.service;

import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.service.user.AuthService;
import ua.training.servlet.hospital.service.user.UserService;

public interface ServiceFactory {
    UserService getUserService(UserDao dao);
    AuthService getAuthService(UserService userService);

    static ServiceFactory getInstance(){
        return new DefaultServiceFactory();
    }
}
