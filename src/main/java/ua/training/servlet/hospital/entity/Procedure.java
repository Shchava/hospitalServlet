package ua.training.servlet.hospital.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Procedure extends Therapy{
    private int room;
    private List<LocalDateTime> appointmentDates;

    public Procedure() {
    }

    public Procedure(Diagnosis diagnosis,String name, String description, LocalDateTime assigned, User assignedBy, int room, List<LocalDateTime> appointmentDates) {
        super(diagnosis,name, description, assigned, assignedBy);
        this.room = room;
        this.appointmentDates = appointmentDates;
    }

    public Procedure(Diagnosis diagnosis,long idTherapy, String name, String description, LocalDateTime assigned, User assignedBy, int room, List<LocalDateTime> appointmentDates) {
        super(idTherapy, diagnosis ,name, description, assigned, assignedBy);
        this.room = room;
        this.appointmentDates = appointmentDates;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Procedure procedure = (Procedure) o;
        return room == procedure.room &&
                Objects.equals(appointmentDates, procedure.appointmentDates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), room, appointmentDates);
    }
}
