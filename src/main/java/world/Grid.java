package main.java.world;

import main.java.heap.Heap;
import main.java.heap.HeapEmptyException;
import main.java.heap.HeapFullException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This is the game grid object consisting of the world map 
 * with default or custom settings.
 * 
 * @author Manpreet Nanreh
 */
public class Grid {

	private final int DEFAULT_WIDTH = 40;
	private final int DEFAULT_HEIGHT = 35;
	private final int DEFAULT_PERCENT = 20;
	protected int width, height;
	protected int percent;
	public Node treasure;
	public Node boat;

	private Node[][] map;

	/**
	 * Create the Grid object using the default options.
	 */
	public Grid() {
		width = DEFAULT_WIDTH;
		height = DEFAULT_HEIGHT;
		percent = DEFAULT_PERCENT;
		buildMap();
	}

	/**
	 * Create the Grid object with the given width,
	 * height and percentage of land.
	 * 
	 * @param width
	 * @param height
	 * @param percent
	 */
	public Grid(int width, int height, int percent) {
		this.width = width;
		this.height = height;
		if (percent <= 0 || percent >= 100)
			this.percent = DEFAULT_PERCENT;
		else
			this.percent = percent;
		buildMap();
	}

	/**
	 * Build the world map for the game consisting of
	 * land, water, boat and treasure where each tile
	 * is represented by a Node.
	 */
	private void buildMap() {
		map = new Node[height][width];
		boolean walkable;
		Random r = new Random();
		int boatX = r.nextInt(width);
		int boatY = r.nextInt(height);
		int chestX = r.nextInt(width);
		int chestY = r.nextInt(height);
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				if (i == boatY && j == boatX) {
					map[i][j] = new Node(true, j, i);
					boat = map[i][j];
				} else if (i == chestY && j == chestX) {
					map[i][j] = new Node(true, j, i);
					treasure = map[i][j];
				} else {
					if (r.nextInt(100) < percent)
						walkable = false;
					else
						walkable = true;
					map[i][j] = new Node(walkable, j, i);
				}
			}
	}

	/**
	 * Create a visual representation for the game map.
	 * 
	 * @return
	 */
	public String drawMap() {
		System.out.println("width: " + width + ", " + "height: " + height);
		String result = "";
		String hline = "       ";
		String extraSpace;
		for (int i = 0; i < width / 10; i++)
			hline += "         " + (i + 1);
		result += hline + "\n";
		hline = "       ";
		for (int i = 0; i < width; i++)
			hline += (i % 10);
		result += hline + "\n";

		for (int i = 0; i < height; i++) {
			if (i < 10)
				extraSpace = "      ";
			else
				extraSpace = "     ";
			hline = extraSpace + i;
			for (int j = 0; j < width; j++) {
				if (i == boat.gridY && j == boat.gridX)
					hline += "B";
				else if (i == treasure.gridY && j == treasure.gridX)
					hline += "T";
				else if (map[i][j].inPath)
					hline += "*";
				else if (map[i][j].walkable)
					hline += ".";
				else
					hline += "+";
			}
			result += hline + i + "\n";
		}
		hline = "       ";
		for (int i = 0; i < width; i++)
			hline += (i % 10);
		result += hline + "\n";
		return result;
	}
	
	/**
	 * Gets the width of the game map.
	 * 
	 * @return int Width of the game map.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets the height of the game map.
	 * 
	 * @return int Height of the game map.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Gets the land percentage in the game map.
	 * 
	 * @return int The land percentage.
	 */
	public int getPercent() {
		return percent;
	}
	
	/**
	 * Gets the node boat object.
	 * 
	 * @return Node The boat object in the map.
	 */
	public Node getBoat() {
		return boat;
	}
	
	/**
	 * Sets the boat to a new boat object.
	 * It is only used in junit test cases.
	 * 
	 * @param newBoat
	 */
	public void setBoat(Node newBoat) {
		boat = newBoat;
	}
	
	/**
	 * Gets the node treasure object.
	 * 
	 * @return Node The treasure object in the map.
	 */
	public Node getTreasure() {
		return treasure;
	}

	/**
	 * Gets the game map.
	 * 
	 * @return Node[][] The game map.
	 */
	public Node[][] getMap() {
		return map;
	}
	
	/**
	 * Sets the map of the game.
	 * It is only used in junit test cases.
	 * 
	 * @param newMap
	 */
	public void setMap(Node[][] newMap) {
		map = newMap;
	}

	/**
	 * Gets the neighbours of the node at distance 1
	 * in all directions.
	 * 
	 * @param node
	 * @return ArrayList<Node> The neighbours of the node.
	 */
	private ArrayList<Node> getNeighbours(Node node) {
		ArrayList<Node> neighbours = new ArrayList<Node>();
		int checkX, checkY;
		for (int x = -1; x <= 1; x++)
			for (int y = -1; y <= 1; y++) {
				if (x == 0 && y == 0)
					continue;
				checkX = node.gridX + x;
				checkY = node.gridY + y;
				if (checkX >= 0 && checkX < width && checkY >= 0
						&& checkY < height)
					neighbours.add(map[checkY][checkX]);
			}
		return neighbours;
	}

	/**
	 * Gets the distance between two nodes following the 
	 * distance formula from A* algorithm.
	 * 
	 * @param nodeA
	 * @param nodeB
	 * @return int The distance between two nodes.
	 */
	private int getDistance(Node nodeA, Node nodeB) {
		int dstX = Math.abs(nodeA.gridX - nodeB.gridX);
		int dstY = Math.abs(nodeA.gridY - nodeB.gridY);
		if (dstX > dstY)
			return 14 * dstY + 10 * (dstX - dstY);
		return 14 * dstX + 10 * (dstY - dstX);
	}

	/**
	 * Finds the shortest path between the start node and 
	 * the target node using the A* algorithm. 
	 * This implementation uses the Heap to keep track
	 * of Nodes with the shortest distance from the start node
	 * and adds them to the path.
	 * 
	 * @param startNode
	 * @param targetNode
	 * @throws HeapFullException
	 * @throws HeapEmptyException
	 */
	public void findPath(Node startNode, Node targetNode)
			throws HeapFullException, HeapEmptyException {
		int newMoveCost;
		Heap<Node> openSet = new Heap<>(width * height);
		Set<Node> closedSet = new HashSet<>();
		Node currentNode;
		openSet.add(startNode);
		while (openSet.count() > 0) {
			currentNode = openSet.removeFirst();
			currentNode.setHeapIndex(0);
			closedSet.add(currentNode);
			if (currentNode.equals(targetNode)) {
				// path found
				//Empty out the Heap for next possible SONAR use.
				while(openSet.count() > 0) {
					currentNode = openSet.removeFirst();
					currentNode.setHeapIndex(0);
				}
				return;
			}
			for (Node neighbour : getNeighbours(currentNode)) {
				if (!neighbour.walkable || closedSet.contains(neighbour))
					continue;
				newMoveCost = currentNode.gCost
						+ getDistance(currentNode, neighbour);
				if (newMoveCost < neighbour.gCost
						|| !openSet.contains(neighbour)) {
					neighbour.gCost = newMoveCost;
					neighbour.hCost = getDistance(neighbour, targetNode);
					neighbour.parent = currentNode;
					if (!openSet.contains(neighbour))
						openSet.add(neighbour);
				}
			}
		}
	}

	/**
	 * This function uses the path generated in the findPath method
	 * in order to retrace the path from the end node to the start
	 * node. This is done by following the node's parent in the path
	 * and the node to be in the path.
	 * 
	 * @param startNode
	 * @param endNode
	 * @return ArrayList<Node> The shortest path from the start node to
	 * 		   the end node.
	 */
	public ArrayList<Node> retracePath(Node startNode, Node endNode) {
		Node currentNode = endNode;
	    ArrayList<Node> path = new ArrayList<Node>();
		while (currentNode != startNode && currentNode != null) {
			currentNode.inPath = true;
			path.add(currentNode);
			currentNode = currentNode.parent;
		}
		return path;
	}

	/**
	 * This function is responsible for moving the boat
	 * towards the given direction without moving onto
	 * a non-walkable tile.
	 * 
	 * @param direction
	 */
	public void move(String direction) {
		int x, y;
		switch (direction) {
		case "NW":
			x = -1;
			y = -1;
			break;
		case "N":
			x = 0;
			y = -1;
			break;
		case "NE":
			x = 1;
			y = -1;
			break;
		case "W":
			x = -1;
			y = 0;
			break;
		case "E":
			x = 1;
			y = 0;
			break;
		case "SW":
			x = -1;
			y = 1;
			break;
		case "S":
			x = 0;
			y = 1;
			break;
		case "SE":
			x = 1;
			y = 1;
			break;
		default:
			x = 0;
			y = 0;
			break;
		}
		int checkX = boat.gridX + x;
		int checkY = boat.gridY + y;

		if (checkX >= 0 && checkX < width && checkY >= 0 && checkY < height 
				&& map[checkY][checkX].walkable)
			boat = map[checkY][checkX];
	}
	
	/**
	 * Gets the treasure within a certain range.
	 * 
	 * @param range
	 * @return Node The treasure.
	 */
	public Node getTreasure(int range) {
		int distance = getDistance(boat, treasure);
		if(distance <= range)
			return treasure;
		else return null;
	}
}