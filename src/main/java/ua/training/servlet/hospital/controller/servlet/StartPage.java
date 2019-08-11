package ua.training.servlet.hospital.controller.servlet;

import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class StartPage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        User user = (User) request.getSession().getAttribute("LoggedUser");

        if(user.getRole() == Roles.PATIENT){
            response.sendRedirect("/patient/" + user.getId() + "/");
        }else{
            response.sendRedirect("/patientsList");
        }
    }
}
