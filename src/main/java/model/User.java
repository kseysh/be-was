package model;

import java.util.UUID;

public class User {

    private final String userId;
    private String password;
    private String name;
    private final String email;
    private final String imageId;

    public User(String userId, String password, String name, String email, String imageId) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.imageId = imageId;
    }

    public static User newUser(String password, String name, String email, String imageId) {
        return new User(UUID.randomUUID().toString(), password, name, email, imageId);
    }

    public String getUserId() {
        return userId;
    }

    public void changeUserName(String name){
        this.name = name;
    }

    public void changePassword(String password){
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageId() {
        return imageId;
    }

    public String getPassword(){
        return password;
    }

    public boolean matchesPassword(String password) {
        return this.password.equals(password);
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }

}
