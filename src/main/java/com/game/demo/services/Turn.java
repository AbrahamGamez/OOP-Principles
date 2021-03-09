package com.game.demo.services;

import java.util.ArrayList;

public class Turn {
	// Contains all the information needed for the U/I to show turn results
	private int currentPlayer;
	private Cards card = new Cards(null, null);
	private boolean isUno;
	private boolean isWinner;
	private ArrayList<Hand> hands;
	
	public Turn(int currentPlayer, Cards card, boolean isUno, boolean isWinner, ArrayList<Hand> hands) {
		super();
		this.currentPlayer = currentPlayer;
		this.card = card;
		this.isUno = isUno;
		this.isWinner = isWinner;
		this.hands = hands;
	}
	
	public ArrayList<Hand> getHands() {
		return hands;
	}

	public void setHands(ArrayList<Hand> hands) {
		this.hands = hands;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public Cards getCard() {
		return card;
	}

	public boolean isUno() {
		return isUno;
	}
	
	public boolean isWinner() {
		return isWinner;
	}
	
}

