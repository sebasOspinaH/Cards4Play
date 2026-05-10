package models;

public class User {
    private String identification;
    private String name;
    private String password;
    private String email;

    public User(String identification, String name, String email, String password) {
        this.identification = identification;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
