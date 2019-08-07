package ua.training.servlet.hospital.controller.servlet;

import ua.training.servlet.hospital.controller.utilities.PaginationUtility;
import ua.training.servlet.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.servlet.hospital.service.ServiceFactory;
import ua.training.servlet.hospital.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/patientsList")
public class ShowAllPatients extends HttpServlet {
    PaginationUtility utility;
    UserService userService;
    @Override
    public void init(){
        userService = ServiceFactory.getInstance().getUserService();
        utility = new PaginationUtility();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int rows = userService.getNumberOfPatients();
        utility.setAttributes(req,rows);
        List<ShowUserToDoctorDTO> patients = userService.findPatientsToShow(utility.getOffset(),utility.getRecordsPerPage());
        req.setAttribute("patients",patients);

        req.getRequestDispatcher("/patientsList.jsp").forward(req,resp);
    }

}
