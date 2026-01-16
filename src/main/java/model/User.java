package model;

import model.policy.NameChangePolicy;
import model.policy.PasswordChangePolicy;

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

    public String getUserId() {
        return userId;
    }

    public void changeUserName(String name, NameChangePolicy nameChangePolicy){
        nameChangePolicy.validate(name);
        this.name = name;
    }

    public void changePassword(String password, PasswordChangePolicy passwordChangePolicy){
        this.password = password;
        passwordChangePolicy.validate(password);
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
