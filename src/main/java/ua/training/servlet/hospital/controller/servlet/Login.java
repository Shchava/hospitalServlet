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
    private UserService userService;
    AuthService auth;

    @Override
    public void init(){
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService();
        auth = serviceFactory.getAuthService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String requestedPage = getRequestedPage(request);

        if (auth.checkAuthority(email, password)) {
            userService.getUser(email).ifPresent(user -> {
                request.getSession().setAttribute("LoggedUser",user);
            });
            response.sendRedirect(requestedPage);
        } else {
            request.setAttribute("requestedUrl",requestedPage);
            request.setAttribute("error",true);
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private String getRequestedPage(HttpServletRequest request){
        String url = request.getParameter("requestedUrl");
        if(url == null || url.startsWith("/login")){
            url = "/index.jsp";
        }
        return url;
    }
}