package model;

public class Response {
    private String message, status;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }
}
