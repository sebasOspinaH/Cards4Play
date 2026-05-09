package models;

public class Product {
    private String identification;
    private String name;
    private double price;

    public Product(String identification, String name, double price) {
        this.identification = identification;
        this.name = name;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
