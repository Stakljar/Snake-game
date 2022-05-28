package hr.ferit.snake;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class JSnake extends JFrame {
	
	private Direction direction = Direction.NORTH;
	private final CardLayout mainLayout = new CardLayout();
	private final JButton startButton = new JButton("START");
	private final JButton endButton = new JButton("Main menu");
	private final JLabel titleLabel = new JLabel("Snake game");
	private final JLabel endLabel = new JLabel("Game over.");
	private final JPanel startPanel = new JPanel(new GridLayout(3, 3, 0, 0));
	private final JPanel gamePanel = new JPanel(new GridLayout(1, 1, 0, 0));
	private final JPanel endPanel = new JPanel(new GridLayout(3, 3, 0, 0));
	private final PanelAdjuster adjuster = new PanelAdjuster();
	private final JSnakeGraphics graphics = new JSnakeGraphics(mainLayout, getContentPane(), this, adjuster);

	public JSnake() {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
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
		adjuster.adjustPanel(endPanel, endButton, endLabel);
		addNextPanelListener(startButton);
		addNextPanelListener(endButton);
		startButton.setName("start");
		endButton.setName("end");
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
	
	private void addNextPanelListener(JButton button) {
		
		button.setMnemonic(KeyEvent.VK_ENTER);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(button.getName() == "start") {
					direction = direction.getRandomDirection();
					graphics.reset();
				}
				mainLayout.next(getContentPane());
				if(button.getName() == "start") {
					while(!gamePanel.requestFocusInWindow()) {}
				}
			}
			
		});	
	}
}
