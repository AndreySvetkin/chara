package com.example.chara.model;

public class Profile {
    private String login;
    private String password;

    private String firstName;

    private String lastName;

    public Profile(String login, String password, String lastName, String firstName) {
        this.login = login;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
