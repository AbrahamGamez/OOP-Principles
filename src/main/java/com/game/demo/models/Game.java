package com.game.demo.models;

import org.springframework.stereotype.Component;

@Component
public class Game {
	
	private String gameID;
	
	public Game() {
		super();
	}

	public String getGameID() {
		return gameID;
	}

	public Game(String gameID) {
		super();
		this.gameID = gameID;
	}
	
	
		
}

