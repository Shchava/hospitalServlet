package ua.training.servlet.hospital.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Operation extends Therapy {
    private LocalDateTime date;

    public Operation() {
    }

    public Operation(String name, String description, LocalDateTime assigned, User assignedBy, LocalDateTime date) {
        super(name, description, assigned, assignedBy);
        this.date = date;
    }

    public Operation(long idTherapy, String name, String description, LocalDateTime assigned, User assignedBy, LocalDateTime date) {
        super(idTherapy, name, description, assigned, assignedBy);
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
        Operation operation = (Operation) o;
        return Objects.equals(date, operation.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date);
    }
}
