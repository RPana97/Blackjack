package org.example;

public interface DeckActions {
    // Method to shuffle the deck
    public void shuffle();

    // Method to deal the next card from the deck
    public Card dealNextCard();

    // Method to print a specified number of cards from the deck
    public void printDeck(int numToPrint);
}
