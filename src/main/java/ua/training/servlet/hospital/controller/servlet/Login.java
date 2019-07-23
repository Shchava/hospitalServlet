package ua.training.servlet.hospital.controller.servlet;

import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.user.AuthService;
import ua.training.servlet.hospital.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    private ServiceFactory serviceFactory;
    private UserService userService;
    AuthService auth;

    public Login(){
        super();
        serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService(DaoFactory.getInstance().createUserDao());
        auth = serviceFactory.getAuthService(userService);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");


        if (auth.checkAuthority(email, password)) {
            userService.getUser(email).ifPresent(user -> {
                request.getSession().setAttribute("LoggedUser",user);
            });
            response.sendRedirect("/index.jsp");
        } else {
            request.setAttribute("error",true);
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}