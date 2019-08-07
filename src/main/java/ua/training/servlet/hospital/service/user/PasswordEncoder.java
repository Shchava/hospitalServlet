package ua.training.servlet.hospital.service.user;

public interface PasswordEncoder {
    String encode(String password);
    boolean checkPassword(String password, String hash);

    static PasswordEncoder getInstance(){
        return new PasswordEncoderImpl();
    }
}
