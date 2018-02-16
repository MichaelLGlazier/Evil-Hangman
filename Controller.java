package gui;

import hangMan.HangMan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This class serves as a gate keeper between the AppletUI and the actual
 * HangMan game
 * 
 * @author Bryan Franklin
 * @author Michael Glazier
 * 
 */
public class Controller implements ActionListener, MouseListener, KeyListener {
	private JLabel playerScoreLabel;
	private JLabel playersLabel;
	private JPanel gamePanel;
	private JLabel letterDisplay;
	private JLabel wordProgress;

	private JButton playerUp;
	private JButton playerDown;
	private JButton pass;
	
	private JButton wordLengthUp;
	private JButton wordLengthDown;
	private JButton giveUp;
	private JLabel wordLength;
	
	private HangMan game;

	public Controller(String filename) {
		game = new HangMan(filename);
	}

	public void setWordButtons(JButton down, JButton up) {
		wordLengthDown = down;
		wordLengthUp = up;
		wordLengthDown.setFocusable(false);
		wordLengthUp.setFocusable(false);
		wordLengthDown.addActionListener(this);
		wordLengthUp.addActionListener(this);
	}
	
	/**creates a reference to the wordProgress label
	 * 
	 * @param wordProgress reference
	 */
	public void setWordProgress(JLabel labelRef)
	{
		wordProgress = labelRef;
	}
	public void setWordLabel(JLabel labelRef) {
		wordLength = labelRef;
	}
	
	public void setGiveUpButton(JButton buttonRef) {
		giveUp = buttonRef;
		giveUp.setFocusable(false);
		giveUp.addActionListener(this);
	}
	
	/**creates a reference to the payerScoreLabel JLabel 
	 *  
	 * @param references playerScoreLabel
	 */
	public void setPlayerScoreLabel(JLabel labelRef) {
		playerScoreLabel = labelRef;
	}
	
	public void setPlayersLabel(JLabel labelRef) {
		playersLabel = labelRef;
	}

	public void setPlayerButtons(JButton down, JButton up) {
		playerDown = down;
		playerUp = up;
		playerDown.setFocusable(false);
		playerUp.setFocusable(false);
		playerDown.addActionListener(this);
		playerUp.addActionListener(this);
	}

	public void setGamePanel(JPanel panelRef) {
		gamePanel = panelRef;
		gamePanel.addMouseListener(this);
	}

	public void setPassButton(JButton buttonRef) {
		pass = buttonRef;
		pass.setFocusable(false);
		pass.addActionListener(this);
	}

	public HangMan getGame() {
		return game;
	}

	public void updateAll() {
		if (playersLabel != null)
			playersLabel.setText("" + game.getNumPlayers());
		if (gamePanel != null)
			gamePanel.getParent().repaint();
		if (wordLength != null)
			wordLength.setText("" + game.getWordLength());
		//formats the playerScoreLabel to display one player and score per line
		if(playerScoreLabel != null)
		{
			String player = "<html>";

			for(int x = 1; x <= game.getNumPlayers(); x++)
			{
				player = player + "Player " + x + ": " + game.getPlayerScore(x - 1) + "<br>";	
			}
			player = player + "</html>";
			
			playerScoreLabel.setText(player);
			
		}
		//makes the letterDisplay JLabel display the output of characterBank
		if(letterDisplay != null)
		{
			 letterDisplay.setText(characterBank());
		}
		//makes the wordProgress JLabel output wordProgress
		if(wordProgress != null)
		{
			
			wordProgress.setText(getWordProgress());
		}
		
	}
	/** Takes the string of the correctly guessed letters and blank spots and
	 *  intersparses a blank space between them.
	 * 
	 * @return string of the wordProgress JLabel
	 */
	public String getWordProgress()
	{
		String currentWordProgress = game.getExposedLetters();
		StringBuilder spacedWordProgress = new StringBuilder();
		
		//adds spaces between the elements of the string
		for (int x = 0; x < currentWordProgress.length(); x++) {
		   spacedWordProgress.append(" ");
		   spacedWordProgress.append(currentWordProgress.charAt(x));
		}

		//sets the stringbuilder equal to the currentWordProgress string so that it can be returned.
		currentWordProgress = spacedWordProgress.toString();
		
		return currentWordProgress; 
	}
	/**Loops through the characters of the alphabet and makes them
	 * part of a string if they haven't been guessed or makes them an underscore
	 * if they had already been guessed.
	 * 
	 * @return List of letters that are guessed and not guessed.
	 */
	public String characterBank()
	{
		String bank = "  ";
		
		//loops through characters
		for (char ch = 'A'; ch <= 'Z'; ++ch) {
			//adds character to bank if not guessed
			if (game.letterAvailable(ch))
				bank = bank + ch + "  ";
			//adds underscore to bank if character is guessed
			else
				bank = bank + "_  ";
		}
		return bank;
	}

	/**creates reference to JLabel
	 * 
	 * @param setLetterDisplay reference
	 */
	public void setLetterDisplay(JLabel panelRef)
	{
		letterDisplay = panelRef;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playerDown) {
			game.setNumPlayers(game.getNumPlayers() - 1);
		}
		if (e.getSource() == playerUp) {
			game.setNumPlayers(game.getNumPlayers() + 1);
		}
		if (e.getSource() == pass) {
			game.nextPlayer();
		}
		if(e.getSource() == wordLengthUp){
			game.setWordLength(game.getWordLength() + 1);
			game.changeWord();
		}
		
		if(e.getSource() == wordLengthDown){
			game.setWordLength(game.getWordLength() - 1);
			game.changeWord();
		}
		
		if(e.getSource() == giveUp){
			game.giveUp();
		}

		
		updateAll();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// check each letter
		int x = e.getX();
		int y = e.getY();
		System.out.println("Click at position " + x + "," + y);
		
		//Allows the player to select a new word after a game is won or lost.
		if(!game.gameActive()){

			if(e.getX() >= 159 && e.getX() <= 326 && e.getY() <= 37 && e.getY() >= 21)
			{
				game.changeWord();
			}
		}
		//below ifs map certain parts of the panel to certain characters
		//maps A
		if(e.getX() >= 35 && e.getX() <= 50 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('A');
		}
		//maps B
		if(e.getX() >= 55 && e.getX() <= 70 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('B');
		}
		//maps C
		if(e.getX() >= 73 && e.getX() <= 88 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('C');
		}
		//maps D
		if(e.getX() >= 90 && e.getX() <= 105 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('D');
		}
		//maps E
		if(e.getX() >= 110 && e.getX() <= 125 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('E');
		}
		//maps F
		if(e.getX() >= 130 && e.getX() <= 140 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('F');
		}
		//maps G
		if(e.getX() >= 145 && e.getX() <= 160 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('G');
		}
		//maps H
		if(e.getX() >= 165 && e.getX() <= 175 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('H');
		}
		//maps I
		if(e.getX() >= 180 && e.getX() <= 190 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('I');
		}
		//maps J
		if(e.getX() >= 200 && e.getX() <= 210 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('J');
		}
		//maps K
		if(e.getX() >= 215 && e.getX() <= 230 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('K');
		}
		//maps L
		if(e.getX() >= 235 && e.getX() <= 250 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('L');
		}
		//maps M
		if(e.getX() >= 251 && e.getX() <= 265 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('M');
		}
		//maps N
		if(e.getX() >= 270 && e.getX() <= 285 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('N');
		}
		//maps  O
		if(e.getX() >= 290 && e.getX() <= 305 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('O');
		}
		//maps P
		if(e.getX() >= 306 && e.getX() <= 320 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('P');
		}
		//maps Q
		if(e.getX() >= 325 && e.getX() <= 340 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('Q');
		}
		//maps R
		if(e.getX() >= 341 && e.getX() <= 355 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('R');
		}
		//maps S
		if(e.getX() >= 360 && e.getX() <= 375 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('S');
		}
		//maps T
		if(e.getX() >= 376 && e.getX() <= 390 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('T');
		}
		//maps U
		if(e.getX() >= 397 && e.getX() <= 410 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('U');
		}
		//maps V
		if(e.getX() >= 415 && e.getX() <= 430 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('V');
		}
		//maps W
		if(e.getX() >= 435 && e.getX() <= 450 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('W');
		}
		//maps X
		if(e.getX() >= 451 && e.getX() <= 465 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('X');
		}
		//maps Y
		if(e.getX() >= 467 && e.getX() <= 480 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('Y');
		}
		//maps Z
		if(e.getX() >= 485 && e.getX() <= 500 && e.getY() <= 415 && e.getY() >= 400)
		{
			game.makeGuess('Z');
		}

		// cause an immediate redraw
		updateAll();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println(e.getKeyChar() + " typed on keyboard");

		//allows keyboard input
		game.makeGuess(e.getKeyChar());
		// cause an immediate redraw
		updateAll();
	}

}
