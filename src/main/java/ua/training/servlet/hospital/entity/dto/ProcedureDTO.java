package ua.training.servlet.hospital.entity.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProcedureDTO {
    String name;

    String description;

    int room;

    List<LocalDateTime> appointmentDates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public List<LocalDateTime> getAppointmentDates() {
        return appointmentDates;
    }

    public void setAppointmentDates(List<LocalDateTime> appointmentDates) {
        this.appointmentDates = appointmentDates;
    }
}
