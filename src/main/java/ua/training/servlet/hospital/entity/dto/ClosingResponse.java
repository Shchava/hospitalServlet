package ua.training.servlet.hospital.entity.dto;

public class ClosingResponse {
    private String response;

    public ClosingResponse() {
    }

    public ClosingResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
