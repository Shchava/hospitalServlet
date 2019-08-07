package ua.training.servlet.hospital.controller.servlet;

import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.UserDTO;
import ua.training.servlet.hospital.entity.enums.Roles;
import ua.training.servlet.hospital.entity.exceptions.EmailExistsException;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;

import static java.util.Objects.isNull;

@WebServlet("/registration")
public class Registration extends HttpServlet {
    private ServiceFactory serviceFactory;
    private UserService userService;
    private boolean allMatches;

    public Registration() {
        super();
        serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService(DaoFactory.getInstance().createUserDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        allMatches = true;

        UserDTO user = getValidatedDto(request);
        boolean registered = registerAndAddErrorAttributes(user,request);

        if(registered){
            forwardSuccess(request,response);
        }else{
            forwardError(user,request,response);
        }
    }

    private boolean registerAndAddErrorAttributes(UserDTO user, HttpServletRequest request){
        boolean registered = false;
        if(allMatches){
            try{
                registered = tryRegisterOrAddRegistrationErrorAttr(user,request);
            }catch (EmailExistsException ex){
                request.setAttribute("emailExists",true);
            }
        }
        return registered;
    }

    private boolean tryRegisterOrAddRegistrationErrorAttr(UserDTO user, HttpServletRequest request){
        if(userService.registerUser(user)){
            return true;
        }else{
            request.setAttribute("registrationError",true);
            return false;
        }
    }

    private UserDTO getValidatedDto(HttpServletRequest request) throws IOException {
        UserDTO user = null;
        try {
            user = new UserDTO(
                    request.getParameter("name"),
                    request.getParameter("surname"),
                    request.getParameter("patronymic"),
                    request.getParameter("email"),
                    request.getParameter("password"),
                    request.getParameter("confirmPassword"),
                    Roles.valueOf(request.getParameter("role")));

            validateUser(request, user);

        }catch (IllegalArgumentException ex){
            allMatches = false;
            request.setAttribute("roleNull",true);
        }
        return user;
    }

    private void validateUser(HttpServletRequest request, UserDTO dto) throws IOException {
        Properties regex = new Properties();
        regex.load(getServletContext().getResourceAsStream("/resources/regexp.properties"));

        //check fields filled
        checkNotEmpty(dto.getName(),"nameEmpty",request);
        checkNotEmpty(dto.getSurname(),"surnameEmpty",request);
        checkNotEmpty(dto.getPatronymic(),"patronymicEmpty",request);
        checkNotEmpty(dto.getEmail(),"emailEmpty",request);
        checkNotEmpty(dto.getPassword(),"passwordEmpty",request);

        //check fields corresponds to regex
        if(allMatches) {
            matchesRegex(dto.getName(),regex.getProperty("pattern.name"),"nameWrong",request);
            matchesRegex(dto.getSurname(),regex.getProperty("pattern.name"),"surnameWrong",request);
            matchesRegex(dto.getPatronymic(),regex.getProperty("pattern.name"),"patronymicWrong",request);
            matchesRegex(dto.getEmail(),regex.getProperty("pattern.email"),"emailWrong",request);
            matchesRegex(dto.getPassword(),regex.getProperty("pattern.password"),"passwordWrong",request);

            passwordMatching(dto.getPassword(),dto.getConfirmPassword(),"passwordsNotEqual",request);
        }
    }

    private void checkNotEmpty(String param, String emptyAttribute, ServletRequest request){
        if(isNull(param) || param.isEmpty()){
            request.setAttribute(emptyAttribute,true);
            allMatches = false;
        }
    }

    private void matchesRegex(String param, String regex ,String wrongRegexAttribute, ServletRequest request){
        if (!param.matches(regex)) {
            request.setAttribute(wrongRegexAttribute,true);
            allMatches = false;
        }
    }

    private void passwordMatching(String password, String confirm, String attribute, ServletRequest request){
        if(!password.equals(confirm)){
            request.setAttribute(attribute,true);
            allMatches = false;
        }
    }

    private void forwardError(UserDTO dto, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("values",dto);
        response.setStatus(400);
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    private void forwardSuccess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("registered",true);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
