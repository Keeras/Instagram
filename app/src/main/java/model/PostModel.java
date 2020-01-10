package model;

public class PostModel {
    private String caption, postImage;

    public PostModel(String caption, String postImage) {
        this.caption = caption;
        this.postImage = postImage;
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
}
