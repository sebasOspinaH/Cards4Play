package com.cards4play.models;

public class Admin extends User {

    public Admin(String identification, String name, String email, String password) {
        super(identification, name, email, password, "ADMIN");
    }
}
