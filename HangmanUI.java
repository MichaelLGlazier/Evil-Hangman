package gui;

import hangMan.HangMan;
import textUI.TextUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * <Description of this class goes here>
 * 
 * @author Bryan Franklin
 * @author Michael Glazier
 * 
 */
public class HangmanUI extends JFrame {
	private static final long serialVersionUID = -6215774992938009947L;
	HangMan game;
	Controller ctrl;
	TextUI textUI;

	public HangmanUI() {
		String dictionaryFile = "up-goer-5.txt";
		ctrl = new Controller(dictionaryFile);
		game = ctrl.getGame();
	}

	public void init() {
		//creates fonts that are monospaced
		Font mono = new Font(Font.MONOSPACED, Font.PLAIN, 10);
		Font monoTwo = new Font(Font.MONOSPACED, Font.PLAIN, 15);

		setSize(640, 480);
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.BOTH;

		// players config stuff
		JLabel players = new JLabel("Players");
		players.setHorizontalAlignment(SwingConstants.CENTER);
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(players, c);

		// start row of single width buttons
		c.gridwidth = 1;
		c.gridy = 1;

		JButton playersDown = new JButton("-");
		c.gridx = 0;
		pane.add(playersDown, c);

		JLabel numPlayers = new JLabel("" + game.getNumPlayers());
		c.gridx = 1;
		pane.add(numPlayers, c);
		ctrl.setPlayersLabel(numPlayers);

		JButton playersUp = new JButton("+");
		c.gridx = 2;
		pane.add(playersUp, c);
		ctrl.setPlayerButtons(playersDown, playersUp);

		// pass
		JButton pass = new JButton("Pass");

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		pane.add(pass, c);
		ctrl.setPassButton(pass);
		
		//creates a JLabel that displays the players and value of their scores
		JLabel playerScores = new JLabel();
		playerScores.setHorizontalAlignment(SwingConstants.LEFT);
		playerScores.setVerticalAlignment(SwingConstants.TOP);
		c.gridwidth = 3;
		c.gridheight = 1;
		c.gridy = 3;
		c.gridx = 0;
		c.weighty = 1; // make it fill extra space
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		pane.add(playerScores, c);
		ctrl.setPlayerScoreLabel(playerScores);
		c.weighty = 0;
		
		//Creates a label that shows the guessed characters of the word being attempted
		JLabel wordProgress = new  JLabel();
		wordProgress.setFont(monoTwo);
		wordProgress.setHorizontalAlignment(SwingConstants.CENTER);
		wordProgress.setVerticalAlignment(SwingConstants.BOTTOM);
		c.gridwidth = 3;
		c.gridx = 4;
		c.gridy = 3;
		pane.add(wordProgress, c);
		ctrl.setWordProgress(wordProgress);			

		//Creates a label that holds the list of characters that are guessed
		JLabel letterDisplay = new JLabel(ctrl.characterBank()); 
		//characters are monospaced so that they don't move when they change to an underscore
		letterDisplay.setFont(mono);
		letterDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		c.gridwidth = 3;
		c.gridx = 4;
		c.gridy = 5;
		pane.add(letterDisplay, c);
		ctrl.setLetterDisplay(letterDisplay);
		
		JButton giveUp = new JButton("Give Up");
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 3;
		pane.add(giveUp, c);
		ctrl.setGiveUpButton(giveUp);
		
		JLabel wordLength = new JLabel("Word Length");
		wordLength.setHorizontalAlignment(SwingConstants.CENTER);
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 5;
		pane.add(wordLength, c);
		
		c.gridy = 6;
		c.gridwidth = 1;
		JButton wordLengthDown = new JButton("-");
		c.gridx = 0;
		pane.add(wordLengthDown, c);
		
		JLabel numWord = new JLabel("" + game.getWordLength());
		c.gridx = 1;
		pane.add(numWord, c);
		ctrl.setWordLabel(numWord);
		
		JButton wordLengthUp = new JButton("+");
		c.gridx = 2;
		pane.add(wordLengthUp, c);
		ctrl.setWordButtons(wordLengthDown, wordLengthUp);

		// create place to draw game state
		JPanel drawPanel = new HangmanPanel(game);
		drawPanel.setBackground(Color.blue);
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 1;
		c.gridheight = 7;
		c.fill = GridBagConstraints.BOTH;
		this.addKeyListener(ctrl);
		pane.add(drawPanel, c);
		ctrl.setGamePanel(drawPanel);
		
		

		// start game
		game.changeWord();
		
		// make sure UI is updated
		ctrl.updateAll();
	}
	

	public static void main(String[] args) {
		HangmanUI app = new HangmanUI();
		app.init();
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		app.setPreferredSize(new Dimension(640, 480));
		app.pack();
		app.setVisible(true);
	}

}
