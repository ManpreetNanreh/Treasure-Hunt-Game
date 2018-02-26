package main.java.redbeard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.world.Grid;
import main.java.world.Node;

/**
 * A JPanel class responsible for creating the game panel.
 * 
 * @author Manpreet Nanreh
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	private GridPanel gridPanel;
	private JPanel controlPanel;
	private JButton northButton;
	private JButton southButton;
	private JButton eastButton;
	private JButton westButton;
	private JButton northEastButton;
	private JButton northWestButton;
	private JButton southEastButton;
	private JButton southWestButton;
	private JButton sonarButton;
	private JButton quitToMenuButton;
	private ImageIcon bImg;
	private TreasureHunt tGame;
	private JLabel sonarCountLabel;
	private JLabel gameOverLabel;
	private boolean treasureFound;
	
	/**
	 * Create the game panel given the treasure hunt game.
	 * 
	 * @param game
	 */
	public GamePanel(TreasureHunt game) {
		super();
		tGame = game;
		gridPanel = new GridPanel();
		controlPanel = new JPanel();
		northButton = new JButton("N");
		southButton = new JButton("S");
		eastButton = new JButton("E");
		westButton = new JButton("W");
		northEastButton = new JButton("NE");
		northWestButton = new JButton("NW");
		southEastButton = new JButton("SE");
		southWestButton = new JButton("SW");
		sonarButton = new JButton("SONAR");
		quitToMenuButton = new JButton("Quit to Menu");
		sonarCountLabel = new JLabel();
		gameOverLabel = new JLabel();
		treasureFound = false;
		
		try {
			bImg = new ImageIcon(ImageIO.read(new File("src/main/resources/background-3045402_1920.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setUpPanel();
	}
	
	/**
	 * Sets up the game panel by putting the objects at their
	 * appropriate places.
	 */
	private void setUpPanel() {
		//Add game over label to the center of gridPanel.
		//This is used to display the message once the game is finished.
		gridPanel.setLayout(new GridBagLayout());
		gridPanel.add(gameOverLabel);
		
		//Create the control panel. Also, add the grid and control panel
		//to the main game panel.
		createControlPanel();
		setLayout(new BorderLayout());
		add(gridPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.EAST);
	}
	
	/**
	 * Creates the control panel which is used to display the 
	 * user controls and in game information.
	 */
	private void createControlPanel() {
		JLabel backgroundLabel = new JLabel(bImg);
		JLabel controlLabel = new JLabel("Controls", SwingConstants.CENTER);
		controlLabel.setFont(new Font("Dialog", Font.BOLD, 45));
		
		JLabel sonarRangeLabel = new JLabel();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new GridBagLayout());
		
		//Set preferred size for all the buttons.
		northButton.setPreferredSize(new Dimension(75, 40));
		southButton.setPreferredSize(new Dimension(75, 40));
		eastButton.setPreferredSize(new Dimension(55, 40));
		westButton.setPreferredSize(new Dimension(55, 40));
		northEastButton.setPreferredSize(new Dimension(55, 40));
		northWestButton.setPreferredSize(new Dimension(55, 40));
		southEastButton.setPreferredSize(new Dimension(55, 40));
		southWestButton.setPreferredSize(new Dimension(55, 40));
		sonarButton.setPreferredSize(new Dimension(75, 40));
		
		//Add the buttons to the button panel.
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.VERTICAL;
		c.insets = new Insets(2, 2, 2, 2);
		c.gridx = 0;
		c.gridy = 0;
		buttonPanel.add(northWestButton, c);
		c.gridy ++;
		buttonPanel.add(westButton, c);
		c.gridy ++;
		buttonPanel.add(southWestButton, c);
		c.gridx = 1;
		c.gridy = 0;
		buttonPanel.add(northButton, c);
		c.gridy ++;
		buttonPanel.add(sonarButton, c);
		c.gridy ++;
		buttonPanel.add(southButton, c);
		c.gridx = 2;
		c.gridy = 0;
		buttonPanel.add(northEastButton, c);
		c.gridy ++;
		buttonPanel.add(eastButton, c);
		c.gridy ++;
		buttonPanel.add(southEastButton, c);
		
		//Set up both labels responsible for displaying game information.
		setSonarLabel();
		sonarRangeLabel.setText("Sonar Range: " + tGame.range);
		sonarRangeLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 3;
		buttonPanel.add(sonarCountLabel, c);
		c.gridy ++;
		buttonPanel.add(sonarRangeLabel, c);
		
		//Add button panel to background label.
		backgroundLabel.setLayout(new GridBagLayout());
		backgroundLabel.add(buttonPanel);
		
		//Add all components to the control panel.
		controlPanel.setLayout(new BorderLayout());
		controlPanel.add(controlLabel, BorderLayout.NORTH);
		controlPanel.add(backgroundLabel, BorderLayout.CENTER);
		controlPanel.add(quitToMenuButton, BorderLayout.SOUTH);
		controlPanel.setPreferredSize(new Dimension(200, 35*20));
	}
	
	/**
	 * Sets the label text to be shown when the user finishes the game.
	 * 
	 * @param overString
	 */
	public void setGameOverLabelText(String overString) {
		gameOverLabel.setFont(new Font("Serif", Font.BOLD, 50));
		gameOverLabel.setForeground(Color.RED);
		gameOverLabel.setText(overString);
	}
	
	/**
	 * Sets the sonar label which is responsible for displaying
	 * the number of sonars.
	 */
	public void setSonarLabel() {
		sonarCountLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		sonarCountLabel.setText("Sonar: " + tGame.sonars);
	}
	
	/**
	 * Sets true if the treasure is found.
	 */
	public void setTreasureFound() {
		treasureFound = true;
	}
	
	/**
	 * Gets the player movement to north button.
	 * 
	 * @return JButton The North button.
	 */
	public JButton getNorthButton() {
		return northButton;
	}

	/**
	 * Gets the player movement to south button.
	 * 
	 * @return JButton The South button.
	 */
	public JButton getSouthButton() {
		return southButton;
	}

	/**
	 * Gets the player movement to east button.
	 * 
	 * @return JButton The East button.
	 */
	public JButton getEastButton() {
		return eastButton;
	}

	/**
	 * Gets the player movement to west button.
	 * 
	 * @return JButton The West button.
	 */
	public JButton getWestButton() {
		return westButton;
	}

	/**
	 * Gets the player movement to northeast button.
	 * 
	 * @return JButton The NorthEast button.
	 */
	public JButton getNorthEastButton() {
		return northEastButton;
	}

	/**
	 * Gets the player movement to northwest button.
	 * 
	 * @return JButton The NorthWest button.
	 */
	public JButton getNorthWestButton() {
		return northWestButton;
	}

	/**
	 * Gets the player movement to southeast button.
	 * 
	 * @return JButton The SouthEast button.
	 */
	public JButton getSouthEastButton() {
		return southEastButton;
	}

	/**
	 * Gets the player movement to southwest button.
	 * 
	 * @return JButton The SouthWest button.
	 */
	public JButton getSouthWestButton() {
		return southWestButton;
	}

	/**
	 * Gets the button responsible for throwing sonars..
	 * 
	 * @return JButton The Sonar button.
	 */
	public JButton getSonarButton() {
		return sonarButton;
	}

	/**
	 * Gets the button responsible for quitting to main menu.
	 * 
	 * @return JButton The Quit Menu Button.
	 */
	public JButton getQuitToMenuButton() {
		return quitToMenuButton;
	}

	/**
	 * The grid panel used to display the game map to the user.
	 * 
	 * @author Manpreet Nanreh
	 */
	private class GridPanel extends JPanel{
		private Grid gameGrid;
		private int height;
		private int width;
		private Node[][] gMap;
		private int sizeMultiplier;
		private BufferedImage imgWater;
		private BufferedImage imgLand;
		private BufferedImage imgBoat;
		private BufferedImage imgTreasure;
		private Node boat;
		private Node treasure;
		
		/**
		 * Create the grid panel object.
		 */
		public GridPanel() {
			super();
			gameGrid = tGame.islands;
			gMap = gameGrid.getMap();
			height = gameGrid.getHeight();
			width = gameGrid.getWidth();
			treasure = gameGrid.getTreasure();
			
			try {
				//Image source: https://www.deviantart.com/art/RPG-Maker-VX-RTP-Tileset-159218223
				imgWater = ImageIO.read(new File("src/main/resources/water.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				//Image source: https://www.deviantart.com/art/RPG-Maker-VX-RTP-Tileset-159218223
				imgLand = ImageIO.read(new File("src/main/resources/grass.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				//Image source: https://icons8.com/icon/set/pirate-wheel/ios
				imgBoat = ImageIO.read(new File("src/main/resources/ship2.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				//Image source: https://www.deviantart.com/art/RPG-Maker-VX-RTP-Tileset-159218223
				imgTreasure = ImageIO.read(new File("src/main/resources/treasure.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sizeMultiplier = 20;
		}
		
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(width * sizeMultiplier, height * sizeMultiplier);
		}
		
		/**
		 * Draw the game map with specified images for each tile.
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			boat = gameGrid.getBoat();
			
			for(int x = 0; x < width; x++) {
				for (int y = 0; y < height; y ++) {
					if(x == boat.getGridX() && y == boat.getGridY()) {
						g.drawImage(imgBoat, x * sizeMultiplier, y * sizeMultiplier, sizeMultiplier, sizeMultiplier, null);
					}else if(x == treasure.getGridX() && y == treasure.getGridY()) {
						if(treasureFound) {
							g.drawImage(imgTreasure, x * sizeMultiplier, y * sizeMultiplier, sizeMultiplier, sizeMultiplier, null);
						}else {
							g.drawImage(imgWater, x * sizeMultiplier, y * sizeMultiplier, sizeMultiplier, sizeMultiplier, null);
						}
					}else if(gMap[y][x].getInPath()) {
						g.drawImage(imgBoat, x * sizeMultiplier, y * sizeMultiplier, sizeMultiplier, sizeMultiplier, null);
					}else if(gMap[y][x].getWalkable()) {
						g.drawImage(imgWater, x * sizeMultiplier, y * sizeMultiplier, sizeMultiplier, sizeMultiplier, null);
					}else {
						g.drawImage(imgLand, x * sizeMultiplier, y * sizeMultiplier, sizeMultiplier, sizeMultiplier, null);
					}
				}
			}
		}
	}
}