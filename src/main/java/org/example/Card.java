package org.example;

public class Card {
    private String suit;
    private String value;

    // Constructor to initialize the card with a suit and value
    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    // Getter method to retrieve the suit of the card
    public String getSuit() {
        return suit;
    }

    // Getter method to retrieve the value of the card
    public String getValue() {
        return value;
    }

    // Override the toString method to display the card as a string (e.g., "Ace of Spades")
    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
