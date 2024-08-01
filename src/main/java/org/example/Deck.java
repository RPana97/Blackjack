package org.example;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Deck implements DeckActions {

    private ArrayList<Card> myCards;
    private int numCards;

    public Deck() {
        myCards = new ArrayList<>();
        for (String suit : Suits.values()) {
            for (String value : Values.values()) {
                myCards.add(new Card(suit, value));
            }
        }
        numCards = myCards.size();
        shuffle();
    }

    @Override
    public void shuffle() {
        Random random = new Random();
        for (int i = myCards.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = myCards.get(i);
            myCards.set(i, myCards.get(j));
            myCards.set(j, temp);
        }
    }

    @Override
    public Card dealNextCard() {
        if (numCards == 0) {
            return null;
        }
        return myCards.remove(--numCards);
    }

    @Override
    public void printDeck(int numToPrint) {
        for (int i = 0; i < numToPrint && i < numCards; i++) {
            System.out.println(myCards.get(i));
        }
    }
}
