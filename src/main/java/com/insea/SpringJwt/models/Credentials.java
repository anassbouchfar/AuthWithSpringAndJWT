package com.insea.SpringJwt.models;

public class Credentials {
    private String email;
    private String password;

    public Credentials() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
