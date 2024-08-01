package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class GameRunner {
    private Deck deck;
    private Player player;
    private ArrayList<Card> dealerHand;
    private PlayMusic musicPlayer;

    public GameRunner() {
        deck = new Deck();
        player = new Player(100); // Starting balance for the player
        dealerHand = new ArrayList<>();
        musicPlayer = new PlayMusic();
    }

    public void start() {
        musicPlayer.playMusic("/CasinoJazz.wav");
        System.out.println("Welcome to Blackjack!");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Your balance: $" + player.getBalance());
            System.out.println("Place your bet (increments of 5): ");
            int bet = scanner.nextInt();
            player.placeBet(bet);

            player.clearHand();
            dealerHand.clear();
            deck.shuffle();

            player.addCard(deck.dealNextCard());
            player.addCard(deck.dealNextCard());
            dealerHand.add(deck.dealNextCard());
            dealerHand.add(deck.dealNextCard());

            while (true) {
                System.out.println("Your hand: " + player.getHand());
                System.out.println("Dealer's hand: " + dealerHand.get(0) + " and [hidden]");

                System.out.println("Do you want to hit, stand, or double down?");
                String choice = scanner.next();

                if (choice.equalsIgnoreCase("hit")) {
                    player.addCard(deck.dealNextCard());
                    if (calculateHandValue(player.getHand()) > 21) {
                        System.out.println("Bust! You lose.");
                        player.loseBet();
                        break;
                    }
                } else if (choice.equalsIgnoreCase("stand")) {
                    while (calculateHandValue(dealerHand) < 17) {
                        dealerHand.add(deck.dealNextCard());
                    }
                    determineWinner();
                    break;
                } else if (choice.equalsIgnoreCase("double down")) {
                    player.placeBet(player.getBet()); // Double the bet
                    player.addCard(deck.dealNextCard());
                    if (calculateHandValue(player.getHand()) > 21) {
                        System.out.println("Bust! You lose.");
                        player.loseBet();
                        break;
                    }
                    while (calculateHandValue(dealerHand) < 17) {
                        dealerHand.add(deck.dealNextCard());
                    }
                    determineWinner();
                    break;
                }
            }

            System.out.println("Play another round? (yes/no)");
            if (!scanner.next().equalsIgnoreCase("yes")) {
                break;
            }
        }

        scanner.close();
    }

    private void determineWinner() {
        int playerValue = calculateHandValue(player.getHand());
        int dealerValue = calculateHandValue(dealerHand);

        System.out.println("Dealer's hand: " + dealerHand);
        if (dealerValue > 21 || playerValue > dealerValue) {
            System.out.println("You win!");
            player.winBet();
        } else if (playerValue < dealerValue) {
            System.out.println("You lose!");
            player.loseBet();
        } else {
            System.out.println("It's a tie!");
            player.pushBet();
        }
    }

    private int calculateHandValue(ArrayList<Card> hand) {
        int value = 0;
        int aces = 0;

        for (Card card : hand) {
            String cardValue = card.getValue();
            if (cardValue.equals("Ace")) {
                aces++;
                value += 11;
            } else if (cardValue.equals("King") || cardValue.equals("Queen") || cardValue.equals("Jack")) {
                value += 10;
            } else {
                value += Integer.parseInt(cardValue);
            }
        }

        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }

        return value;
    }

    public static void main(String[] args) {
        GameRunner game = new GameRunner();
        game.start();
    }
}
