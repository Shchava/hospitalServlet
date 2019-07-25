package ua.training.servlet.hospital.controller.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogOut extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logout(request);
        request.setAttribute("logout",true);
        request.getRequestDispatcher("/login.jsp").forward(request,response);
    }

    private void logout(HttpServletRequest request){
        HttpSession ses = request.getSession();
        ses.invalidate();
    }
}
