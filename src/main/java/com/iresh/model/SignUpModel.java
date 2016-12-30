package com.iresh.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by iresh on 12/23/2016.
 */
public class SignUpModel {

    @NotNull
    @Size(min = 5,max = 18,message = "User Name should be greater than 5 character and less than 10 character")
    private String userName;

    @NotNull
    @Pattern(regexp = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b",message = "Not a valid Email")
    private String email;

    @NotNull
    @Size(min = 8,max = 15,message = "Password should be greater than 8 character and less than 15 character")
    private String password;

    @NotNull(message = "Please renter your password")
    private String confirmPassword;

    public SignUpModel() {
    }

    public SignUpModel(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    @Override
    public String toString() {
        return "SignUpModel{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
