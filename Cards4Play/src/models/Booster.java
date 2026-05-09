package models;

import java.util.ArrayList;

public class Booster extends Product{
    ArrayList<Card> cardList;
    boolean isOpen;

    public Booster(String identification, String name, double price, ArrayList<Card> cardList, boolean isOpen) {
        super(identification, name, price);
        this.cardList = new ArrayList<>();
        this.isOpen = isOpen;
    }
}
