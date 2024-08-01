package org.example;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private int balance;
    private int bet;

    public Player(int initialBalance) {
        hand = new ArrayList<>();
        balance = initialBalance;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getBalance() {
        return balance;
    }

    public void placeBet(int amount) {
        if (amount % 5 == 0 && amount <= balance) {
            bet = amount;
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Bet must be in increments of 5 and within balance");
        }
    }

    public void winBet() {
        balance += bet * 2;
    }

    public void loseBet() {
        // Bet is already deducted
    }

    public void pushBet() {
        balance += bet;
    }

    public int getBet() {
        return bet;
    }

    public void clearHand() {
        hand.clear();
    }
}
