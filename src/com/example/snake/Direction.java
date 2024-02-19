package com.example.snake;

import java.util.Random;

public enum Direction {
	
	NORTH, WEST, SOUTH, EAST;
	
	public Direction getRandomDirection() {
		
		int randomDirection = new Random().nextInt(Direction.values().length);
	    return Direction.values()[randomDirection];
	}
}
