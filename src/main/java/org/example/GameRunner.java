package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class GameRunner {
    private Deck deck;                   // The deck of cards used in the game
    private Player player;               // The player in the game
    private ArrayList<Card> dealerHand;  // The dealer's hand of cards
    private PlayMusic musicPlayer;       // The music player for background music

    // Constructor to initialize the game
    public GameRunner() {
        deck = new Deck();
        player = new Player(100); // Starting balance for the player
        dealerHand = new ArrayList<>();
        musicPlayer = new PlayMusic();
    }

    // Method to start the game
    public void start() {
        musicPlayer.playMusic("/CasinoJazz.wav"); // Start background music
        System.out.println("Welcome to Blackjack!");

        Scanner scanner = new Scanner(System.in);

        // Main game loop
        while (true) {
            System.out.println("Your balance: $" + player.getBalance());
            System.out.println("Place your bet (increments of 5): ");
            int bet = scanner.nextInt(); // Get the player's bet
            player.placeBet(bet);

            player.clearHand();    // Clear the player's hand for a new round
            dealerHand.clear();    // Clear the dealer's hand for a new round
            deck.shuffle();        // Shuffle the deck

            // Deal two cards to the player and dealer
            player.addCard(deck.dealNextCard());
            player.addCard(deck.dealNextCard());
            dealerHand.add(deck.dealNextCard());
            dealerHand.add(deck.dealNextCard());

            // Player's turn to play
            while (true) {
                System.out.println("Your hand: " + player.getHand());
                System.out.println("Dealer's hand: " + dealerHand.get(0) + " and [hidden]");

                System.out.println("Do you want to hit, stand, or double down?");
                String choice = scanner.next(); // Get player's choice

                if (choice.equalsIgnoreCase("hit")) {
                    // Deal a new card to the player
                    Card newCard = deck.dealNextCard();
                    if (newCard == null) {
                        // Reshuffle the deck if it's empty
                        System.out.println("The deck is empty. Reshuffling the deck.");
                        deck = new Deck();
                        newCard = deck.dealNextCard();
                    }
                    player.addCard(newCard);

                    // Check if the player busts (hand value exceeds 21)
                    if (calculateHandValue(player.getHand()) > 21) {
                        System.out.println("Bust! You lose.");
                        player.loseBet();
                        break;
                    }
                } else if (choice.equalsIgnoreCase("stand")) {
                    // Dealer's turn to play
                    while (calculateHandValue(dealerHand) < 17) {
                        Card newCard = deck.dealNextCard();
                        if (newCard == null) {
                            // Reshuffle the deck if it's empty
                            System.out.println("The deck is empty. Reshuffling the deck.");
                            deck = new Deck();
                            newCard = deck.dealNextCard();
                        }
                        dealerHand.add(newCard);
                    }
                    determineWinner(); // Determine the winner of the round
                    break;
                } else if (choice.equalsIgnoreCase("double down")) {
                    // Double the player's bet and deal one more card
                    player.placeBet(player.getBet());
                    Card newCard = deck.dealNextCard();
                    if (newCard == null) {
                        // Reshuffle the deck if it's empty
                        System.out.println("The deck is empty. Reshuffling the deck.");
                        deck = new Deck();
                        newCard = deck.dealNextCard();
                    }
                    player.addCard(newCard);

                    // Check if the player busts after doubling down
                    if (calculateHandValue(player.getHand()) > 21) {
                        System.out.println("Bust! You lose.");
                        player.loseBet();
                        break;
                    }

                    // Dealer's turn to play after player doubles down
                    while (calculateHandValue(dealerHand) < 17) {
                        newCard = deck.dealNextCard();
                        if (newCard == null) {
                            System.out.println("The deck is empty. Reshuffling the deck.");
                            deck = new Deck();
                            newCard = deck.dealNextCard();
                        }
                        dealerHand.add(newCard);
                    }
                    determineWinner(); // Determine the winner of the round
                    break;
                }
            }

            // Ask the player if they want to play another round
            System.out.println("Play another round? (yes/no)");
            if (!scanner.next().equalsIgnoreCase("yes")) {
                break;
            }
        }

        scanner.close(); // Close the scanner
    }

    // Method to determine the winner of the round
    private void determineWinner() {
        int playerValue = calculateHandValue(player.getHand());
        int dealerValue = calculateHandValue(dealerHand);

        System.out.println("Dealer's hand: " + dealerHand);

        if (dealerValue > 21 || playerValue > dealerValue) {
            System.out.println("You win!");
            player.winBet(); // Player wins and earns the bet
        } else if (playerValue < dealerValue) {
            System.out.println("You lose!");
            player.loseBet(); // Player loses the bet
        } else {
            System.out.println("It's a tie!");
            player.pushBet(); // Bet is returned to the player in case of a tie
        }
    }

    // Method to calculate the value of a hand of cards
    private int calculateHandValue(ArrayList<Card> hand) {
        int value = 0;
        int aces = 0;

        // Iterate through each card in the hand
        for (Card card : hand) {
            String cardValue = card.getValue();
            if (cardValue.equals("Ace")) {
                aces++;
                value += 11; // Aces are initially worth 11 points
            } else if (cardValue.equals("King") || cardValue.equals("Queen") || cardValue.equals("Jack")) {
                value += 10; // Face cards are worth 10 points
            } else {
                value += Integer.parseInt(cardValue); // Number cards are worth their face value
            }
        }

        // Adjust for Aces if the hand's value exceeds 21
        while (value > 21 && aces > 0) {
            value -= 10; // Convert an Ace from 11 points to 1 point
            aces--;
        }

        return value;
    }

    public static void main(String[] args) {
        GameRunner game = new GameRunner(); // Create a new game instance
        game.start(); // Start the game
    }
}
