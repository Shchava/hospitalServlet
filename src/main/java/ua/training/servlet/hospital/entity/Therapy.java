package ua.training.servlet.hospital.entity;

import java.time.LocalDateTime;
import java.util.Objects;


public abstract class Therapy {
    private long idTherapy;
    private String name;
    private String description;
    private LocalDateTime assigned;
    private User assignedBy;

    public Therapy() {
    }

    public Therapy(String name, String description, LocalDateTime assigned, User assignedBy) {
        this.name = name;
        this.description = description;
        this.assigned = assigned;
        this.assignedBy = assignedBy;
    }

    public Therapy(long idTherapy, String name, String description, LocalDateTime assigned, User assignedBy) {
        this(name, description, assigned, assignedBy);
        this.idTherapy = idTherapy;
    }

    public long getId() {
        return idTherapy;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getAssigned() {
        return assigned;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setId(long idTherapy) {
        this.idTherapy = idTherapy;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAssigned(LocalDateTime assigned) {
        this.assigned = assigned;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Therapy therapy = (Therapy) o;
        return idTherapy == therapy.idTherapy &&
                Objects.equals(name, therapy.name) &&
                Objects.equals(description, therapy.description) &&
                Objects.equals(assigned, therapy.assigned) &&
                Objects.equals(assignedBy, therapy.assignedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTherapy, name, description, assigned, assignedBy);
    }
}
