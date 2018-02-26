package main.java.redbeard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import main.java.heap.HeapEmptyException;
import main.java.heap.HeapFullException;
import main.java.world.Grid;
import main.java.world.Node;

/**
 * The main treasure hunt game
 * 
 * @author Manpreet Nanreh
 */
public class TreasureHunt {

	private final int DEFAULT_SONARS = 3;
	private final int DEFAULT_RANGE = 200;
	protected Grid islands;
	protected int height, width, landPercent;
	protected int sonars, range;
	protected String state;
	protected ArrayList<Node> path;

	/**
	 * Create the treasure hunt game.
	 */
	public TreasureHunt() {
		islands = new Grid();
		sonars = DEFAULT_SONARS;
		range = DEFAULT_RANGE;
		height = islands.getHeight();
		width = islands.getWidth();
		landPercent = islands.getPercent();
		state = "STARTED";
	}

	/**
	 * Creates the treasure hunt game with the given height,
	 * width, land percentage, number of sonars and the range of sonar.
	 * 
	 * @param height
	 * @param width
	 * @param landPercent
	 * @param sonars
	 * @param range
	 */
	public TreasureHunt(int height, int width, int landPercent, int sonars,
			int range) {
		islands = new Grid(width, height, landPercent);
		this.width = width;
		this.height = height;
		this.landPercent = landPercent;
		this.sonars = sonars;
		this.range = range;
		state = "STARTED";
	}

	/**
	 * Processes the user command and either move the boat or 
	 * drop the sonar to find the treasure.
	 * 
	 * @param command
	 * @throws HeapFullException
	 * @throws HeapEmptyException
	 */
	public void processCommand(String command) throws HeapFullException,
			HeapEmptyException {
		String[] cmdList = command.split(" ");
		String cmd = cmdList[0];
		String prm = "";
		if (cmdList.length > 1)
			prm = cmdList[1];
		switch (cmd) {
		case "SONAR":
			if (sonars > 0) {
				sonars--;
				Node treasure = islands.getTreasure(range);
				if (treasure != null) {
					Node boat = islands.getBoat();
					//Find the shortest path between boat and treasure.
					islands.findPath(boat, treasure);

					//Retrace the shortest path.
					path = islands.retracePath(boat, treasure);
				}
				//If path is found then player wins.
				//Otherwise, if the player runs out of sonar then they lose.
				if(path != null) {
					state = "WIN";
				}else if(sonars == 0) {
					state = "LOSE";
				}
			}
			break;
		case "GO":
			//Move the boat.
			islands.move(prm);
			
			//If the player walks onto the treasure, then they win.
			if(islands.getBoat().equals(islands.getTreasure())) {
				state= "WIN";
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Gets the length of the shortest path from boat to treasure.
	 * 
	 * @return int Shortest path length.
	 */
	public int pathLength() {
		if (path == null)
			return 0;
		else return path.size();
	}
	
	/**
	 * Sets the islands grid to the provided grid.
	 * Primarily used for junit testing.
	 * 
	 * @param gameGrid
	 */
	public void setIslands(Grid gameGrid) {
		islands = gameGrid;
	}
	
	//***************************
	//These methods are only used in GameTest.
	//***************************
	
	/**
	 * Reads a game command test file and processes the 
	 * commands.
	 * 
	 * @param pathName
	 * @throws FileNotFoundException
	 * @throws HeapFullException
	 * @throws HeapEmptyException
	 */
	public void playFromFile(String pathName) throws FileNotFoundException,
			HeapFullException, HeapEmptyException {
		Scanner getData = new Scanner(new File(pathName));
		System.out.println(islands.drawMap());
		while (getData.hasNextLine() && !state.equals("OVER")) {
			System.out.println("Sonars: " + sonars);
			System.out.println("Sonar range: " + range);
			System.out.println("Enter command: ");
			String line = getData.nextLine();
			System.out.println("Command given: " + line);
			processCommand(line);
			System.out.println(islands.drawMap());
		}
		System.out.println("Game state: " + state);
		System.out.println("Shortest path length: " + pathLength());
		getData.close();
	}
	
	/**
	 * Allows to play the game in the java shell.
	 * 
	 * @throws HeapFullException
	 * @throws HeapEmptyException
	 */
	public void playFromShell() throws HeapFullException, HeapEmptyException{
		Scanner data = new Scanner(System.in);
		String n;
		System.out.println(islands.drawMap());
		while(!state.equals("LOSE") && !state.equals("WIN")){
			System.out.println("Sonars: " + sonars);
			System.out.println("Sonar range: " + range);
			System.out.println("Enter command: ");
			n = data.nextLine();
			System.out.println("Command given: " + n);
			processCommand(n);
			System.out.println(islands.drawMap());
		}
		System.out.println("Game state: " + state);
		System.out.println("Shortest path length: " + pathLength());
		data.close();
	}
}