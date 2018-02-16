package gui;

import hangMan.HangMan;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This class is used to draw the graphical portion of the user interface (i.e.
 * the gallows, letters, current word, etc.)
 * 
 * @author Bryan Franklin
 * @author Michael Glazier
 * 
 */
public class HangmanPanel extends JPanel {
	private static final long serialVersionUID = 7734877696044080629L;
	HangMan game;

	public HangmanPanel(HangMan gameRef) {
		game = gameRef;
	}

	/**
	 * paintComponent method is part of the JPanel class and is called to draw
	 * things to the JPanel
	 * 
	 * @param g
	 *            - graphics object use for drawing to the JPanel
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.RED);
		
		g.fillRect(120, 40, 250, 30);
		g.fillRect(140, 60, 20, 275);
		g.fillRect(130, 335, 300, 50);
		g.fillRect(280, 70, 5, 45);
	
		g.setColor(Color.GREEN);
		g.fillRect(0, 385, 600, 95);
		
		Font font = new Font("Times", Font.PLAIN, 20);
		Font deadEyes = new Font("Times", Font.BOLD, 15);
		g.setFont(font);
		
		if(!game.gameLost() && !game.gameWon()){
			g.drawString(
				"Player " + (game.currentPlayer() + 1) + " of "
						+ game.getNumPlayers(), 165, 20);
		}
		if(game.gameWon()){
			
			g.drawString("Player " + (game.currentPlayer() + 1) + " Won!", 165, 20);
			g.drawString("Click for new word", 160, 38);
		}
		if(game.gameLost()){
			g.drawString("Player " + (game.currentPlayer() + 1) + " Lost!", 165, 20);
			g.drawString("Click for new word", 160, 38);
		}
		g.setColor(Color.YELLOW);
		
		//draws head
		if(game.getFailures() == 1){
			
			g.fillOval(260, 90, 45, 45);
		}
		//draws head and body
		if(game.getFailures() == 2){
			g.fillOval(260, 90, 45, 45);
			g.fillRect(282, 135, 4, 80);
		}
		//draws head, body, and arm
		if(game.getFailures() == 3){
			g.fillOval(260, 90, 45, 45);
			g.fillRect(282, 135, 4, 80);
			g.drawLine(230, 130, 282, 150);
		}
		//draws head, body, and arms
		if(game.getFailures() == 4){
			g.fillOval(260, 90, 45, 45);
			g.fillRect(282, 135, 4, 80);
			g.drawLine(230, 130, 282, 150);
			g.drawLine(282, 150, 340, 130);
		}
		//draws head, body, arms, and a leg
		if(game.getFailures() == 5){
			g.fillOval(260, 90, 45, 45);
			g.fillRect(282, 135, 4, 80);
			g.drawLine(230, 130, 282, 150);
			g.drawLine(282, 150, 340, 130);
			g.drawLine(331, 255, 283, 215);
		}
		//draws head, body, arms, and legs
		if(game.getFailures() == 6){
			g.fillOval(260, 90, 45, 45);
			g.fillRect(282, 135, 4, 80);
			g.drawLine(230, 130, 282, 150);
			g.drawLine(282, 150, 340, 130);
			g.drawLine(331, 255, 283, 215);
			g.drawLine(231, 255, 283, 215);
			
			g.setColor(Color.black);
			g.fillArc( 265, 120, 30, 15, -180, -180);
			
			//draws frowny face
			g.setFont(deadEyes);
			g.drawString("X", 265, 115);
			g.drawString("X", 280, 115);
			
			g.setFont(font);

		}
		

	} // end of paintComponent method

}
