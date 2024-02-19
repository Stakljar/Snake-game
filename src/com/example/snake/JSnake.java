package com.example.snake;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class JSnake extends JFrame {
	
	private Direction direction = Direction.NORTH;
	private final CardLayout mainLayout = new CardLayout();
	private JButton startButton;
	private JButton startAgainButton;
	private JLabel titleLabel;
	private JLabel endLabel;
	private final JPanel startPanel = new JPanel(new GridLayout(3, 3, 0, 0));
	private final JPanel gamePanel = new JPanel(new GridLayout(1, 1, 0, 0));
	private final JPanel endPanel = new JPanel(new GridLayout(3, 3, 0, 0));
	private final PanelAdjuster adjuster = new PanelAdjuster();
	private JSnakeGraphics graphics;

	public JSnake() {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				startButton = new JButton("START");
				startAgainButton = new JButton("PLAY AGAIN");
				titleLabel = new JLabel("Snake game");
				endLabel = new JLabel("Game over");
				graphics = new JSnakeGraphics(mainLayout, getContentPane(), JSnake.this, adjuster);
				startButton.requestFocus();
				createFrame();
				performAutoMovement();
			}
		});
	}
	
	private void createFrame() {
		
		setSize(1116, 799);
		setTitle("Snake game");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(mainLayout);
		configurePanels();
	}
	
	private void configurePanels() {
		
		adjuster.adjustPanel(startPanel, startButton, titleLabel);
		adjuster.adjustPanel(endPanel, startAgainButton, endLabel);
		addNextPanelListener(startButton);
		addNextPanelListener(startAgainButton);
		startButton.setName("start");
		startAgainButton.setName("end");
		add(startPanel);
		gamePanel.add(graphics);
		gamePanel.setFocusable(true);
		add(gamePanel);
		add(endPanel);
		gamePanel.setBackground(Color.YELLOW);
	    gamePanel.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_UP) {
					if(direction != Direction.SOUTH)
					direction = Direction.NORTH;	
				}
				else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					if(direction != Direction.NORTH)
					direction = Direction.SOUTH;
				}
				else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if(direction != Direction.WEST)
					direction = Direction.EAST;
				}
				else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					if(direction != Direction.EAST)
					direction = Direction.WEST;
				}
			}
		});	
	    
	    endPanel.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				startAgainButton.requestFocus();
			}
		});
	}

	private void performAutoMovement() {
				
		new SwingWorker<Object, Object>() {

			@Override
			protected Object doInBackground() throws Exception {
				
				while(true) {
					switch(direction) {
						case NORTH:
							graphics.adjustFirstElement(0, -20);
							break;
						case EAST:
							graphics.adjustFirstElement(20, 0);
							break;
						case SOUTH:
							graphics.adjustFirstElement(0, 20);
							break;
						case WEST:
							graphics.adjustFirstElement(-20, 0);
							break;
						default:
							break;
						}
					EventQueue.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							
							gamePanel.repaint();
						}
					});
					try {
						Thread.sleep(40);
					} 
					catch (InterruptedException e) {
						
						 dispose();
						 JOptionPane.showMessageDialog(gamePanel, "Application has encountered error.", "ERROR",
					     JOptionPane.ERROR_MESSAGE);
						 System.exit(1);
						 
					}
				}
			}
			
		}.execute();	
	}
	
	private void performButtonAction(JButton button) {
		
		direction = direction.getRandomDirection();
		graphics.reset();
		mainLayout.next(getContentPane());
		if(button.getName().equals("end")) {
			mainLayout.next(getContentPane());
		}
		while(!gamePanel.requestFocusInWindow()) {}
	}

	private void addNextPanelListener(JButton button) {

		button.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					performButtonAction(button);
				}
			}
		});
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				performButtonAction(button);
			}
		});	
	}
}
