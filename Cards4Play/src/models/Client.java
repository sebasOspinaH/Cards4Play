package models;

public class Client extends User{
    Inventory inventory;

    public Client(String identification, String name, String email, String password, Inventory inventory) {
        super(identification, name, email, password);
        this.inventory = inventory;
    }
}
