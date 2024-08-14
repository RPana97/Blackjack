package org.example;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;   // The player's hand, which is a collection of cards
    private int balance;            // The player's balance or available money
    private int bet;                // The amount the player bets in a round

    // Constructor to initialize the player with an initial balance
    public Player(int initialBalance) {
        hand = new ArrayList<>();   // Initialize the hand as an empty list of cards
        balance = initialBalance;   // Set the player's starting balance
    }

    // Method to add a card to the player's hand
    public void addCard(Card card) {
        hand.add(card);
    }

    // Method to retrieve the player's hand
    public ArrayList<Card> getHand() {
        return hand;
    }

    // Method to retrieve the player's current balance
    public int getBalance() {
        return balance;
    }

    // Method to place a bet; it deducts the bet amount from the balance
    public void placeBet(int amount) {
        // Bet must be in increments of 5 and should not exceed the player's balance
        if (amount % 5 == 0 && amount <= balance) {
            bet = amount;
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Bet must be in increments of 5 and within balance");
        }
    }

    // Method to handle the scenario where the player wins the bet
    public void winBet() {
        balance += bet * 2;  // Player wins twice the amount of their bet
    }

    // Method to handle the scenario where the player loses the bet
    public void loseBet() {
        // No need to deduct further as the bet amount has already been subtracted when placed
    }

    // Method to handle a push (tie) situation; the bet is returned to the player
    public void pushBet() {
        balance += bet;
    }

    // Method to retrieve the current bet amount
    public int getBet() {
        return bet;
    }

    // Method to clear the player's hand, typically at the end of a round
    public void clearHand() {
        hand.clear();
    }
}
