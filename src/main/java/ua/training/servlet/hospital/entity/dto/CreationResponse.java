package ua.training.servlet.hospital.entity.dto;

import java.util.ArrayList;
import java.util.List;

public class CreationResponse {
    String message;
    List<CreationError> errors;

    public CreationResponse() {
        this.errors = new ArrayList<>();
    }

    public CreationResponse(String message, List<CreationError> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CreationError> getErrors() {
        return errors;
    }

    public void setErrors(List<CreationError> errors) {
        this.errors = errors;
    }

    public void addError(CreationError error){
        errors.add(error);
    }
}
