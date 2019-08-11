package ua.training.servlet.hospital.controller.command;

import ua.training.servlet.hospital.entity.dto.CommandResponse;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    CommandResponse execute(HttpServletRequest request);
}
