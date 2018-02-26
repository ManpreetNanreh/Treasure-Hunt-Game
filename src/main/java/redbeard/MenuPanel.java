package main.java.redbeard;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The menu panel object which is responsible for creating
 * the menu for the game GUI.
 * 
 * @author Manpreet Nanreh
 */
@SuppressWarnings("serial")
public class MenuPanel extends JPanel{
	private JPanel buttonPanel;
	private JButton playButton;
	private JButton quitButton;
	private JLabel backgroundLabel;
	private ImageIcon bImg;

	/**
	 * Create a menu panel object.
	 */
	public MenuPanel() {
		super();
		buttonPanel = new JPanel();
		playButton = new JButton("Play");
		quitButton = new JButton("Quit");
		
		try {
			bImg = new ImageIcon(ImageIO.read(new File("src/main/resources/background-3045402_1920.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		backgroundLabel = new JLabel(bImg);
		setUpPanel();
	}
	
	/**
	 * Sets up the menu panel with the included units.
	 */
	private void setUpPanel() {
		//Setup the buttons into button panel.
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = new Insets(2, 0, 2, 0);
		c.gridx = 0;
		c.gridy = 0;
		buttonPanel.add(playButton, c);
		c.gridy ++;
		buttonPanel.add(quitButton, c);
		
		//Setup the background label and add the button panel to label.
		backgroundLabel.setLayout(new GridBagLayout());
		backgroundLabel.add(buttonPanel);
		
		//Add the label with buttons.
		setLayout(new BorderLayout());
		add(backgroundLabel, BorderLayout.CENTER);
	}
	
	/**
	 * Gets the play button.
	 * 
	 * @return JButton The play button.
	 */
	public JButton getPlayButton() {
		return playButton;
	}
	
	/**
	 * Gets the quit button.
	 * 
	 * @return JButton The quit button.
	 */
	public JButton getQuitButton() {
		return quitButton;
	}
}
