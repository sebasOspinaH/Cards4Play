package com.cards4play.models;

import java.util.ArrayList;
import java.util.List;

public class Booster extends Product {
    private List<Card> cardList;
    private boolean isOpen;

    public Booster(String identification, String name, double priceUSD) {
        super(identification, name, priceUSD, "BOOSTER");
        this.cardList = new ArrayList<>();
        this.isOpen = false;
    }

    public List<Card> getCardList() { return cardList; }
    public void setCardList(List<Card> cardList) { this.cardList = cardList; }

    public boolean isOpen() { return isOpen; }
    public void setOpen(boolean open) { isOpen = open; }
}
