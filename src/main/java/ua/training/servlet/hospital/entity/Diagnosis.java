package ua.training.servlet.hospital.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Diagnosis {
    private long idDiagnosis;
    private String name;
    private String description;
    private LocalDateTime assigned;
    private LocalDateTime cured;
    private User patient;
    private User doctor;
    private List<Medicine> assignedMedicine;
    private List<Procedure> assignedProcedures;
    private List<Operation> assignedOperations;

    public Diagnosis() {
    }

    public Diagnosis(String name, String description, LocalDateTime assigned, LocalDateTime cured, User patient, User doctor) {
        this.name = name;
        this.description = description;
        this.assigned = assigned;
        this.cured = cured;
        this.patient = patient;
        this.doctor = doctor;
    }

    public Diagnosis(long idDiagnosis, String name, String description, LocalDateTime assigned, LocalDateTime cured, User patient, User doctor, List<Medicine> assignedMedicine, List<Procedure> assignedProcedures, List<Operation> assignedOperations) {
        this(name, description, assigned, cured, patient, doctor);
        this.idDiagnosis = idDiagnosis;
        this.doctor = doctor;
        this.assignedMedicine = assignedMedicine;
        this.assignedProcedures = assignedProcedures;
        this.assignedOperations = assignedOperations;
    }

    public long getIdDiagnosis() {
        return idDiagnosis;
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

    public LocalDateTime getCured() {
        return cured;
    }

    public User getPatient() {
        return patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public List<Medicine> getAssignedMedicine() {
        return assignedMedicine;
    }

    public List<Procedure> getAssignedProcedures() {
        return assignedProcedures;
    }

    public List<Operation> getAssignedOperations() {
        return assignedOperations;
    }

    public void setIdDiagnosis(long idDiagnosis) {
        this.idDiagnosis = idDiagnosis;
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

    public void setCured(LocalDateTime cured) {
        this.cured = cured;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public void setAssignedMedicine(List<Medicine> assignedMedicine) {
        this.assignedMedicine = assignedMedicine;
    }

    public void setAssignedProcedures(List<Procedure> assignedProcedures) {
        this.assignedProcedures = assignedProcedures;
    }

    public void setAssignedOperations(List<Operation> assignedOperations) {
        this.assignedOperations = assignedOperations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diagnosis diagnosis = (Diagnosis) o;
        return idDiagnosis == diagnosis.idDiagnosis &&
                Objects.equals(name, diagnosis.name) &&
                Objects.equals(description, diagnosis.description) &&
                Objects.equals(assigned, diagnosis.assigned) &&
                Objects.equals(cured, diagnosis.cured) &&
                Objects.equals(patient, diagnosis.patient) &&
                Objects.equals(doctor, diagnosis.doctor) &&
                Objects.equals(assignedMedicine, diagnosis.assignedMedicine) &&
                Objects.equals(assignedProcedures, diagnosis.assignedProcedures) &&
                Objects.equals(assignedOperations, diagnosis.assignedOperations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDiagnosis, name, description, assigned, cured, patient, doctor, assignedMedicine, assignedProcedures, assignedOperations);
    }
}
