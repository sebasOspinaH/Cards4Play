package com.cards4play.models;

public abstract class User {
    private String identification;
    private String name;
    private String email;
    private String password;
    private String role; // "ADMIN" o "CLIENT"

    public User(String identification, String name, String email, String password, String role) {
        this.identification = identification;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getIdentification() { return identification; }
    public void setIdentification(String identification) { this.identification = identification; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
