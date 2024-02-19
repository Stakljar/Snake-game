package com.example.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelAdjuster {
	
	private ComponentAdjuster adjuster = new ComponentAdjuster(); 
	private int score = 0;
	private JLabel scoreLabel;
	
	public class ComponentAdjuster {
		
		private ComponentAdjuster() {
			
			scoreLabel = new JLabel("Final score: " + score);
		}
		
		private void adjustLabel(JLabel label) {
			
			label.setForeground(Color.RED);
			label.setFont(new Font("Times New Roman", Font.BOLD, 55));
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		private void adjustButton(JButton button) {
			
			button.setBackground(Color.RED);
			button.setForeground(Color.BLACK);
			button.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		}
	}
	
	public void adjustPanel(JPanel panel, JButton button, JLabel label) {
			
		JPanel topSubPanel = new JPanel(new GridLayout(3, 3, 0, 0));
		JPanel middleSubPanel = new JPanel(new GridLayout(3, 3, 0, 0));
		topSubPanel.setBackground(Color.YELLOW);
		middleSubPanel.setBackground(Color.YELLOW);
		panel.add(getHelperPanel());
		panel.add(topSubPanel);

		topSubPanel.add(getHelperPanel());
		topSubPanel.add(label);
		topSubPanel.add(scoreLabel); //it transfers to the last panel adjusted

		panel.add(getHelperPanel());
		panel.add(getHelperPanel());
		panel.add(middleSubPanel);

		middleSubPanel.add(getHelperPanel());
		middleSubPanel.add(button);
		middleSubPanel.add(getHelperPanel());

		panel.add(getHelperPanel());
		panel.add(getHelperPanel());
		panel.add(getHelperPanel());
		panel.add(getHelperPanel());

	    	adjuster.adjustButton(button);
		adjuster.adjustLabel(label);
		adjuster.adjustLabel(scoreLabel);
	}
	
	private JPanel getHelperPanel() {
	
		JPanel panel = new JPanel();
		panel.setBackground(Color.YELLOW);
		return panel;
	}
	
	public void setScore(int score) {
		
		this.score = score;
		scoreLabel.setText("Final score: " + score);
	}
}