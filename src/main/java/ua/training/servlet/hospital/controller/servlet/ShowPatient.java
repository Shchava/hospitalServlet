package ua.training.servlet.hospital.controller.servlet;

import ua.training.servlet.hospital.controller.command.Command;
import ua.training.servlet.hospital.controller.command.ShowPatient.ShowPatientDiagnoses;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/patient/*")
public class ShowPatient extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(){
        commands.put("",new ShowPatientDiagnoses());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/patient/\\d*/" , "");

        Command command = commands.getOrDefault(path , (r)->"/index.jsp");
        String page = command.execute(request);
        request.getRequestDispatcher(page).forward(request,response);
    }
}
