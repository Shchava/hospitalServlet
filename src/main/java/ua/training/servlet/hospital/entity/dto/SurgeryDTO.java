package ua.training.servlet.hospital.entity.dto;

import java.time.LocalDateTime;

public class SurgeryDTO {
    String name;
    String description;
    LocalDateTime surgeryDate;

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

    public LocalDateTime getSurgeryDate() {
        return surgeryDate;
    }

    public void setSurgeryDate(LocalDateTime surgeryDate) {
        this.surgeryDate = surgeryDate;
    }
}
