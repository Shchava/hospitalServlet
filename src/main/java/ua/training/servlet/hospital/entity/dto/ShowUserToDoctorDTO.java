package ua.training.servlet.hospital.entity.dto;


public class ShowUserToDoctorDTO {
    long id;
    String name;
    String surname;
    String patronymic;
    String email;
    String lastDiagnosisName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastDiagnosisName() {
        return lastDiagnosisName;
    }

    public void setLastDiagnosisName(String lastDiagnosisName) {
        this.lastDiagnosisName = lastDiagnosisName;
    }
}
