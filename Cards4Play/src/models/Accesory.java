package models;

public class Accesory extends Product{
    private String type;

    public Accesory(String identification, String name, double price, String type) {
        super(identification, name, price);
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
