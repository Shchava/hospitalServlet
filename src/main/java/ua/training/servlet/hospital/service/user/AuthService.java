package ua.training.servlet.hospital.service.user;

public interface AuthService {
    boolean checkAuthority(String username,String password);
}
