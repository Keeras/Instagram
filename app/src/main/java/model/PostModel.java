package model;

public class PostModel {
    private String caption, postImage, username, userImage;

    public PostModel(String caption, String postImage, String username, String userImage) {
        this.caption = caption;
        this.postImage = postImage;
        this.username = username;
        this.userImage = userImage;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

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
}
