package ua.training.servlet.hospital.controller.command.utilities;

import ua.training.servlet.hospital.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetSessionAttribute {
    public static long getUserId(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("LoggedUser");
        return user.getId();
    }
}
