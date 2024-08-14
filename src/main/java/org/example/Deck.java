package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Deck implements DeckActions {

    private ArrayList<Card> myCards;  // The deck of cards, represented as a list of Card objects
    private int numCards;             // The current number of cards in the deck

    // Constructor to initialize the deck of cards
    public Deck() {
        myCards = new ArrayList<>();  // Initialize the list to hold the deck's cards
        // Loop through each suit and value to create the full deck of 52 cards
        for (String suit : Suits.values()) {
            for (String value : Values.values()) {
                myCards.add(new Card(suit, value));
            }
        }
        numCards = myCards.size();  // Set the initial number of cards
        shuffle();                  // Shuffle the deck once it's created
    }

    // Method to shuffle the deck
    @Override
    public void shuffle() {
        Random random = new Random();
        // Implement Fisher-Yates shuffle algorithm
        for (int i = myCards.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1); // Generate a random index between 0 and i
            // Swap cards at positions i and j
            Card temp = myCards.get(i);
            myCards.set(i, myCards.get(j));
            myCards.set(j, temp);
        }
    }

    // Method to deal the next card from the deck
    @Override
    public Card dealNextCard() {
        if (numCards == 0) {
            return null;  // Return null if there are no cards left in the deck
        }
        // Remove and return the last card from the deck
        return myCards.remove(--numCards);
    }

    // Method to print a specified number of cards from the deck (for debugging or display purposes)
    @Override
    public void printDeck(int numToPrint) {
        for (int i = 0; i < numToPrint && i < numCards; i++) {
            System.out.println(myCards.get(i));  // Print each card
        }
    }
}
