package com.example.onlineartgallery;

public class Post {

    private String postId;
    private String postImage;
    private String title;
    private String publisher;
    private String medium;
    private String price;
    private String dimensions;


    public Post(String postId, String postImage, String title, String publisher, String medium, String price, String dimensions)
    {
        this.postId = postId;
        this.postImage=postImage;
        this.medium=medium;
        this.price=price;
        this.title=title;
        this.publisher=publisher;
        this.dimensions= dimensions;
    }


    public  Post(){

    }


    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
}
