package main.java.redbeard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import main.java.heap.HeapEmptyException;
import main.java.heap.HeapFullException;

/**
 * The Game GUI for the treasure hunt game.
 * 
 * @author Manpreet Nanreh
 */
public class GameGui {
	private JFrame mainFrame;
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	private TreasureHunt tHuntGame;
	
	/**
	 * Create a game GUI object.
	 */
	public GameGui() {
		mainFrame = new JFrame();
		menuPanel = new MenuPanel();
		menuPanel.setPreferredSize(new Dimension(40*20 + 200, 35*20));
		addMenuPanelButtonListener();
		mainFrame.add(menuPanel);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
	
	/**
	 * Adds an action listener to the buttons in the menu panel.
	 */
	private void addMenuPanelButtonListener() {
		menuPanel.getPlayButton().addActionListener(new ButtonActionListener());
		menuPanel.getQuitButton().addActionListener(new ButtonActionListener());
	}
	
	/**
	 * Adds an action listener to the buttons in the game panel.
	 */
	private void addGamePanelButtonListener() {
		gamePanel.getNorthButton().addActionListener(new ButtonActionListener());
		gamePanel.getSouthButton().addActionListener(new ButtonActionListener());
		gamePanel.getEastButton().addActionListener(new ButtonActionListener());
		gamePanel.getWestButton().addActionListener(new ButtonActionListener());
		gamePanel.getNorthEastButton().addActionListener(new ButtonActionListener());
		gamePanel.getNorthWestButton().addActionListener(new ButtonActionListener());
		gamePanel.getSouthEastButton().addActionListener(new ButtonActionListener());
		gamePanel.getSouthWestButton().addActionListener(new ButtonActionListener());
		gamePanel.getQuitToMenuButton().addActionListener(new ButtonActionListener());
		gamePanel.getSonarButton().addActionListener(new ButtonActionListener());
	}
	
	/**
	 * Detects which button is clicked and does the specified task.
	 * 
	 * @author Manpreet Nanreh
	 */
	private class ButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Quit")) {
				//Quit the game.
				System.exit(0);
			}else if(e.getActionCommand().equals("Play")) {
				//Create a new treasure hunt game with each play click.
				tHuntGame = new TreasureHunt();
				//Create the game panel and show it to the user.
				gamePanel = new GamePanel(tHuntGame);
				addGamePanelButtonListener();
				mainFrame.remove(menuPanel);
				mainFrame.add(gamePanel);
				mainFrame.repaint();
				mainFrame.revalidate();
			}else if(e.getActionCommand().equals("Quit to Menu")) {
				//Remove the game panel and display the mene panel.
				mainFrame.remove(gamePanel);
				mainFrame.add(menuPanel);
				mainFrame.repaint();
				mainFrame.revalidate();
			}else if(e.getActionCommand().equals("N")) {
				//Move the player to the specified direction.
				try {
					tHuntGame.processCommand("GO N");
				} catch (HeapFullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HeapEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mainFrame.repaint();
			}else if(e.getActionCommand().equals("S")) {
				//Move the player to the specified direction.
				try {
					tHuntGame.processCommand("GO S");
				} catch (HeapFullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HeapEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mainFrame.repaint();
			}else if(e.getActionCommand().equals("E")) {
				//Move the player to the specified direction.
				try {
					tHuntGame.processCommand("GO E");
				} catch (HeapFullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HeapEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mainFrame.repaint();
			}else if(e.getActionCommand().equals("W")) {
				//Move the player to the specified direction.
				try {
					tHuntGame.processCommand("GO W");
				} catch (HeapFullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HeapEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mainFrame.repaint();
			}else if(e.getActionCommand().equals("NE")) {
				//Move the player to the specified direction.
				try {
					tHuntGame.processCommand("GO NE");
				} catch (HeapFullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HeapEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mainFrame.repaint();
			}else if(e.getActionCommand().equals("NW")) {
				//Move the player to the specified direction.
				try {
					tHuntGame.processCommand("GO NW");
				} catch (HeapFullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HeapEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mainFrame.repaint();
			}else if(e.getActionCommand().equals("SW")) {
				//Move the player to the specified direction.
				try {
					tHuntGame.processCommand("GO SW");
				} catch (HeapFullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HeapEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mainFrame.repaint();
			}else if(e.getActionCommand().equals("SE")) {
				//Move the player to the specified direction.
				try {
					tHuntGame.processCommand("GO SE");
				} catch (HeapFullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HeapEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mainFrame.repaint();
			}else if(e.getActionCommand().equals("SONAR")) {
				//Drop the sonar in order to find the treasure.
				try {
					tHuntGame.processCommand("SONAR");
				} catch (HeapFullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HeapEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Update the number of sonars remaining.
				gamePanel.setSonarLabel();
				mainFrame.repaint();
			}
			
			//If the player wins or loses the game then disable all the movement
			//related buttons and show the game over text.
			if(tHuntGame.state == "WIN" || tHuntGame.state == "LOSE") {
				gamePanel.setTreasureFound();
				gamePanel.getNorthButton().setEnabled(false);
				gamePanel.getSouthButton().setEnabled(false);
				gamePanel.getEastButton().setEnabled(false);
				gamePanel.getWestButton().setEnabled(false);
				gamePanel.getNorthEastButton().setEnabled(false);
				gamePanel.getNorthWestButton().setEnabled(false);
				gamePanel.getSouthEastButton().setEnabled(false);
				gamePanel.getSouthWestButton().setEnabled(false);
				gamePanel.getSonarButton().setEnabled(false);
				gamePanel.setGameOverLabelText("YOU " + tHuntGame.state);
			}
		}
	}
	
	public static void main(String[] args) {
		//Run the game.
		new GameGui();
	}
}
