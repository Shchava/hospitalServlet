package ua.training.servlet.hospital.entity.dto;


import java.time.LocalDate;

public class MedicineDTO {
    String name;

    String description;

    Integer count;

    LocalDate refill;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public LocalDate getRefill() {
        return refill;
    }

    public void setRefill(LocalDate refill) {
        this.refill = refill;
    }
}
