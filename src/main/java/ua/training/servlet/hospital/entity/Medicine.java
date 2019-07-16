package ua.training.servlet.hospital.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Medicine extends Therapy {
    private int count;
    private LocalDateTime refill;

    public Medicine() {
    }

    public Medicine(String name, String description, LocalDateTime assigned, User assignedBy, int count, LocalDateTime refill) {
        super(name, description, assigned, assignedBy);
        this.count = count;
        this.refill = refill;
    }

    public Medicine(long idTherapy, String name, String description, LocalDateTime assigned, User assignedBy, int count, LocalDateTime refill) {
        super(idTherapy, name, description, assigned, assignedBy);
        this.count = count;
        this.refill = refill;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDateTime getRefill() {
        return refill;
    }

    public void setRefill(LocalDateTime refill) {
        this.refill = refill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Medicine medicine = (Medicine) o;
        return count == medicine.count &&
                Objects.equals(refill, medicine.refill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), count, refill);
    }
}
