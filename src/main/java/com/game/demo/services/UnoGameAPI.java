package com.game.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class UnoGameAPI {

	private int players = 4;
	private ArrayList<Hand> hands = new ArrayList<Hand>();
	private Deck deck = initializeGame(players, hands);
	private boolean isWinner = false;
	private boolean isUno = false;
	private boolean reverse = false;
	private boolean winner = false;
	int i = 0;

	private static HashMap<String, UnoGameAPI> games = new HashMap<String, UnoGameAPI>();

	private UnoGameAPI() {
	}

	// Factory method
	public static UnoGameAPI getGame(String gameName) {
		// use 'gameName' as a key in the games HashMap
		// if 'gameName' is not found, create a new game and add it to 'games' HashMap.  
		// return the new game.
		// if 'gameName' is found, return that game.
		UnoGameAPI game = games.get(gameName);
		if(game == null) {
			game = new UnoGameAPI();
			games.put(gameName, game);
		}
		return game;

	}
	
	public static String[] getGameNames() {
		Set<String> keys = games.keySet();
		return keys.toArray(new String[0]);
	}
	
	public boolean nextTurn() {
		// returns true as long as a winner has not been declared
		// returns false when the game has a winner
		// once winner has been determined, this method should return false each time.
		if(winner == false) {
			return true;
		}
		return false;
	}

	public Turn getTurn() {

			deck.replenish(); // checks if deck needs to be replenished
			Cards topDiscard = deck.topDiscard(); // getting the top card on the discard deck
			int currentPlayer = i;
			//			System.out.printf("Turn: %d\n", turns);
			//			System.out.printf("Top Card: %s\n", topDiscard);
			//			System.out.printf("Player: %d %s \n", currentPlayer, hands.get(currentPlayer)); 			
			Cards card = hands.get(currentPlayer).hasMatch(topDiscard); // checking if player has a match

			if (card != null) { // if the player can play a card
				//				System.out.println("Card Played:" + card); // what card they placed down
				if (isWinner == true) { // checks if player won
					winner = true;
					getTurn();
					//					System.out.printf("\nPlayer %d Won\n", currentPlayer);

					//					System.out.println("Player " + currentPlayer + " score is: "+ scoreCard(hands));
					
				}
				i = processSpecialCard(hands, players, deck, i, currentPlayer, card);

				callUno(hands, deck, currentPlayer, card);

			} else { // if the player can't match a card they pick one up
				hands.get(currentPlayer).drawCard(deck.dealCard());
				card = topDiscard;
				// player draws one card
				//				System.out.printf("Draw Card: %d \n", i); //display player number
				//				System.out.println(hands.get(currentPlayer)); //displayers current players hand
			}
			i = getNextPlayer(i, players); // getting the next player
			//			System.out.println();
		
		System.out.println();
		return new Turn(currentPlayer, card, isUno, isWinner, hands);
//		int i = 0;
//		if (reverse == false) {
//			i ++;
//		} else if (reverse == true) {
//			i--;
//		}
//		if (i > players -1) { // making sure that i does not break out of array
//			i = 0;
//		}
//		if (i < 0) { // making sure that i does not break out of array
//			i =  players -1;
//		}
//		if (isWinner == true)
//			i = 0;
//
//		return new Turn(players, cards, isUno, isWinner);
		// returns current turn
		// once the winner is declared, should return the last turn of the game
	}

	public void Game() {
		
		while (winner == false) { // loops through players until there is a winner
			deck.replenish(); // checks if deck needs to be replenished
			Cards topDiscard = deck.topDiscard(); // getting the top card on the discard deck
			int currentPlayer = i;
			//			System.out.printf("Turn: %d\n", turns);
			//			System.out.printf("Top Card: %s\n", topDiscard);
			//			System.out.printf("Player: %d %s \n", currentPlayer, hands.get(currentPlayer)); 			
			Cards card = hands.get(currentPlayer).hasMatch(topDiscard); // checking if player has a match

			if (card != null) { // if the player can play a card
				//				System.out.println("Card Played:" + card); // what card they placed down
				if (isWinner == true) { // checks if player won
					winner = true;
					getTurn();
					//					System.out.printf("\nPlayer %d Won\n", currentPlayer);

					//					System.out.println("Player " + currentPlayer + " score is: "+ scoreCard(hands));
					break;
				}
				i = processSpecialCard(hands, players, deck, i, currentPlayer, card);

				callUno(hands, deck, currentPlayer, card);

			} else { // if the player can't match a card they pick one up
				hands.get(currentPlayer).drawCard(deck.dealCard()); // player draws one card
				//				System.out.printf("Draw Card: %d \n", i); //display player number
				//				System.out.println(hands.get(currentPlayer)); //displayers current players hand
			}
			i = getNextPlayer(i, players); // getting the next player
			//			System.out.println();
		}
	}

	private void callUno(ArrayList<Hand> hands, Deck deck, int currentPlayer, Cards card) {
		deck.addToDiscard(card); // takes the card the player played and puts it at the top of the discard deck
		if (isUno == true) { // checks if player can call Uno
			//		System.out.printf("\nPlayer %d calls UNO!\n", currentPlayer);
		}
	}

	private int processSpecialCard(ArrayList<Hand> hands, int players, Deck deck, int i, int currentPlayer,
			Cards card) {
		if (card.isSpecial(card) == true) { // checks if card is special
			switch (card.getValue()) {
			case SKIP:
				i = getNextPlayer(i, players);
				break;
			case REVERSE:
				reverse = !reverse;
				break;
			case DRAWTWO:
				int nextPlayer = getNextPlayer(i, players);
				for (int x = 0; x < 2; x++) {
					hands.get(nextPlayer).drawCard(deck.dealCard());
					deck.replenish();
				}
				break;
			case WILD:
				hands.get(currentPlayer).colorCount(card);
				//			System.out.println("Player has called the color: " + card.getColor());
				break;
			case WILD_DRAWFOUR:
				hands.get(currentPlayer).colorCount(card);
				//			System.out.println("Player has called the color: " + card.getColor());
				int wildNextPlayer = getNextPlayer(i, players);
				for (int x = 0; x < 4; x++) {
					hands.get(wildNextPlayer).drawCard(deck.dealCard());
					deck.replenish();
				}
				break;
			default:
				//			System.out.println("Hmm something went wrong with a special card");
				break;

			}
			//		System.out.println("A special card has been played");
		}
		return i;
	}

	private Deck initializeGame(int players, ArrayList<Hand> hands) {
		Deck deck = new Deck();
		deck.populate();
		//	System.out.println(deck);
		deck.shuffle();
		//	System.out.println(deck);

		for (int i = 0; i < players; i++) {
			hands.add(new Hand());
		}

		// adding cards into the hands of the players
		for (int i = 0; i < 7; i++) {
			for (Hand hand : hands) {
				hand.drawCard(deck.dealCard());
			}
		}
		// add a card to the discard to start it, draw until there is a normal card
		deck.discardPile();
		//		System.out.println(hands);
		return deck;
	}

	private int scoreCard(ArrayList<Hand> hands) {
		int score = 0;
		for (int i = 0; i< hands.size(); i++) {
			for (int h = 0; h < hands.get(i).getHand().size(); h++) {
				Cards scard = hands.get(i).getHand().get(h);
				score = score + scard.cardScore(scard);
			}
		}
		return score;
	}

	private int getNextPlayer(int i, int players) {

		if (reverse == false) {
			i++;
		} else if (reverse == true) {
			i--;
		}
		if (i > players -1) { // making sure that i does not break out of array
			i = 0;
		}
		if (i < 0) { // making sure that i does not break out of array
			i =  players -1;
		}
		return i;
	}
}		


