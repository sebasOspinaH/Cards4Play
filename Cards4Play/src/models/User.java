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
}
