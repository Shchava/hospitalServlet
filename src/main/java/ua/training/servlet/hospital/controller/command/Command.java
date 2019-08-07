package ua.training.servlet.hospital.controller.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request);
}
