package ua.training.servlet.hospital.controller.servlet;

import com.google.gson.Gson;
import jdk.nashorn.internal.ir.RuntimeNode;
import ua.training.servlet.hospital.controller.command.Command;
import ua.training.servlet.hospital.controller.command.RestCommand;
import ua.training.servlet.hospital.controller.command.showdiagnosis.ShowDiagnosis;
import ua.training.servlet.hospital.controller.command.showdiagnosis.ShowMedicine;
import ua.training.servlet.hospital.controller.command.showdiagnosis.ShowProcedures;
import ua.training.servlet.hospital.controller.command.showdiagnosis.ShowSurgeries;
import ua.training.servlet.hospital.controller.command.showpatient.ShowPatientDiagnoses;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.dto.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/patient/*")
public class ShowPatient extends HttpServlet {

    private Map<String, Command> commands = new HashMap<>();
    private Map<String, RestCommand> restCommands = new HashMap<>();

    @Override
    public void init(){
        restCommands.put("diagnosis/getMedicine/",new ShowMedicine());
        restCommands.put("diagnosis/getProcedures/",new ShowProcedures());
        restCommands.put("diagnosis/getSurgeries/",new ShowSurgeries());

        commands.put("",new ShowPatientDiagnoses());
        commands.put("diagnosis/",new ShowDiagnosis());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/patient/\\d*/?" , "");
        path = path.replaceAll("\\d+/?","");
        if(restCommands.containsKey(path)) {
            processRestRequest(request,response,path);
        }else{
            processPageRequest(request,response,path);
        }
    }

    private void processRestRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RestCommand command = restCommands.get(path);
        String responseJson = command.execute(request);
        PrintWriter out = response.getWriter();
        out.print(responseJson);
        out.flush();
    }

    private void processPageRequest(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        Command command = commands.getOrDefault(path, (r) -> "/notFoundPage.jsp");
        String page = command.execute(request);
        request.getRequestDispatcher(page).forward(request,response);
    };


}
