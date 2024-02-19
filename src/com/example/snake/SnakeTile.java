package com.example.snake;

public class SnakeTile {

	private int x, y;
	
	public SnakeTile(int x, int y) {
		
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
	
		this.x = x;
	}
	
	public void setY(int y) {
			
		this.y = y;
	}
	
	public int getX() {
		
		return x;
	}
	
	public int getY() {
		
		return y;
	}
}
