package ua.training.servlet.hospital.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Medicine extends Therapy {
    private int count;
    private LocalDate refill;

    public Medicine() {
    }

    public Medicine(Diagnosis diagnosis, String name, String description, LocalDateTime assigned, User assignedBy, int count, LocalDate refill) {
        super(diagnosis, name, description, assigned, assignedBy);
        this.count = count;
        this.refill = refill;
    }

    public Medicine(long idTherapy, Diagnosis diagnosis, String name, String description, LocalDateTime assigned, User assignedBy, int count, LocalDate refill) {
        this(diagnosis,name,description,assigned,assignedBy,count,refill);
        setId(idTherapy);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDate getRefill() {
        return refill;
    }

    public void setRefill(LocalDate refill) {
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
