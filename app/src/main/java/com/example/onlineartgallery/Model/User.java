package com.example.onlineartgallery.Model;

public class User {

    private String id;
    private  String username;
    private String fullname;
    private  String imageUrl;

    private String bio;
    private String skills;
    private String email;
    private String phone;

    public User(String id, String username, String fullname, String imageUrl, String bio, String skills,String email,String phone) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.imageUrl = imageUrl;
        this.bio = bio;
        this.skills=skills;
        this.email=email;
        this.phone = phone;

    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
