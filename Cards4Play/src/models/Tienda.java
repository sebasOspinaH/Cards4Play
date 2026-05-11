package models;

public class Tienda {
    private final Admin admin;
    private String name;

    public Tienda(Admin admin, String name) {
        this.admin = admin;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Admin getAdmin() {
        return admin;
    }
}
