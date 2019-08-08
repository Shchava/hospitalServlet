package ua.training.servlet.hospital.controller.command.ShowPatient;

import ua.training.servlet.hospital.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowPatientDiagnoses implements Command {
    @Override
    public String execute(HttpServletRequest request) {


        return  "/showPatient.jsp";
    }
}
