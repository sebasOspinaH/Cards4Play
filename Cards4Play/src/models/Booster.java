package models;

import java.util.ArrayList;

public class Booster extends Product{
    ArrayList<Card> cardList;
    boolean isOpen;

    public Booster(String identification, String name, double price, boolean isOpen) {
        super(identification, name, price);
        this.cardList = new ArrayList<>();
        this.isOpen = isOpen;
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public void setCardList(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

}
