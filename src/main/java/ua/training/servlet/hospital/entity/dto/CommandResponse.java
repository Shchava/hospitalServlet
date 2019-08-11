package ua.training.servlet.hospital.entity.dto;

public class CommandResponse {
    int status;
    String response;

    public CommandResponse() {
    }

    public CommandResponse(int status, String response) {
        this.status = status;
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
