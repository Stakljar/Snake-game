package com.example.snake;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.awt.CardLayout;
import java.awt.Container;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class JSnakeGraphics extends JComponent{

	private final Random random = new Random();
	private CardLayout layout;
	private Container container;
	private JFrame frame;
	private BufferedImage apple = null;
	private BufferedImage snakeHead = null;
	private BufferedImage snakeBody = null;
	private PanelAdjuster adjuster = null;
	private final ArrayList<SnakeTile> snakeTiles = new ArrayList<SnakeTile>();
	public final int WIDTH = 20;
	public final int HEIGHT = 20;
	public int appleX = 400;
	public int appleY = 240;
	private int score = 0;
	private final int maxValueX = 46;
	private final int maxValueY = 30;
	private final int minValueX = 5;
	private final int minValueY = 5;
	private final int minValueAppleX = 0;
	private final int minValueAppleY = 0;
	private final int maxValueAppleX = 54;
	private final int maxValueAppleY = 37;
	private final int multiplier = 20;
	
	public JSnakeGraphics(CardLayout layout, Container container, JFrame frame, PanelAdjuster adjuster) {
		
		snakeTiles.add(new SnakeTile(460, 360));
		this.layout = layout;
		this.container = container;
		this.frame = frame;
		this.adjuster = adjuster;
		try {
		    apple = ImageIO.read(new File("resources/apple.jpg"));
		    snakeHead = ImageIO.read(new File("resources/snake-head.jpg"));
		    snakeBody = ImageIO.read(new File("resources/snake-body.jpg"));
		} 
		catch (IOException e) {
			 frame.dispose();
			 JOptionPane.showMessageDialog(frame, "Error reading image.", "ERROR",
		     JOptionPane.ERROR_MESSAGE);
			 System.exit(1);
		}
	}

	private void terminate(CardLayout layout, Container container) {
		
		invokeAdjuster();
		layout.next(container);
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.setColor(Color.GREEN);
		g.drawImage(apple, appleX, appleY, null);
		g.setColor(Color.RED);
		g.drawImage(snakeHead, snakeTiles.get(0).getX(), snakeTiles.get(0).getY(), null);
		
		for (int i = 1; i < snakeTiles.size(); i++) {
			g.drawImage(snakeBody, snakeTiles.get(i).getX(), snakeTiles.get(i).getY(), null);
		}
		
		if(snakeTiles.get(0).getX() == appleX && snakeTiles.get(0).getY() == appleY) {
			score++;
			resetApple();
			snakeTiles.add(new SnakeTile(snakeTiles.get(snakeTiles.size() - 1).getX(), snakeTiles.get(snakeTiles.size() - 1).getY()));
		}
		
		for(int i = 3; i < snakeTiles.size(); i++) {
			if(snakeTiles.get(0).getX() == snakeTiles.get(i).getX() && snakeTiles.get(0).getY() == snakeTiles.get(i).getY()) {
				terminate(layout, container);
			}
		}
		
		if(snakeTiles.get(0).getX() < 0 || snakeTiles.get(0).getX() >= frame.getWidth() - 16 || snakeTiles.get(0).getY() < -1 || snakeTiles.get(0).getY() >= frame.getHeight() - 39) {
			terminate(layout, container);
		}
	}
	
	public void reset() {
		
		snakeTiles.get(0).setX((minValueX + random.nextInt(maxValueX - minValueX + 1)) * multiplier);
		snakeTiles.get(0).setY((minValueY + random.nextInt(maxValueY - minValueY + 1)) * multiplier);
		resetApple();
		SnakeTile tile = snakeTiles.get(0);
		snakeTiles.clear();
		snakeTiles.add(tile);
		score = 0;
	}
	
	private void resetApple() {

		appleX = (minValueAppleX + random.nextInt(maxValueAppleX - minValueAppleX + 1)) * multiplier;
		appleY = (minValueAppleY + random.nextInt(maxValueAppleY - minValueAppleY + 1)) * multiplier;
		for(int i = 0; i < snakeTiles.size(); i++) {
			if(snakeTiles.get(i).getX() == appleX && snakeTiles.get(i).getY() == appleY)
				resetApple();
		}
	}
	
	public void adjustFirstElement(int adjustmentX, int adjustmentY) {
		
	    	for (int i = snakeTiles.size() - 1; i > 0; i--) {
			snakeTiles.get(i).setX(snakeTiles.get(i - 1).getX());
			snakeTiles.get(i).setY(snakeTiles.get(i - 1).getY());
		}
		
		snakeTiles.get(0).setX(snakeTiles.get(0).getX() + adjustmentX);
		snakeTiles.get(0).setY(snakeTiles.get(0).getY() + adjustmentY);
	}
	
	private void invokeAdjuster() {
		
		adjuster.setScore(score);
	}
	
	public void setScore(int score) {
		
		this.score = score;
	}
	
	public int getScore() {
		
		return score;
	}
	

}