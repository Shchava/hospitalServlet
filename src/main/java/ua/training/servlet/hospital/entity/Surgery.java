package ua.training.servlet.hospital.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Surgery extends Therapy {
    private LocalDateTime date;

    public Surgery() {
    }

    public Surgery(Diagnosis diagnosis,String name, String description, LocalDateTime assigned, User assignedBy, LocalDateTime date) {
        super(diagnosis, name, description, assigned, assignedBy);
        this.date = date;
    }

    public Surgery(long idTherapy, Diagnosis diagnosis, String name, String description, LocalDateTime assigned, User assignedBy, LocalDateTime date) {
        super(idTherapy, diagnosis, name, description, assigned, assignedBy);
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Surgery surgery = (Surgery) o;
        return Objects.equals(date, surgery.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date);
    }
}
