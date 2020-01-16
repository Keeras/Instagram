package model;

public class Response {
    private String username, message, status, userImage;
    private boolean success;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

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
