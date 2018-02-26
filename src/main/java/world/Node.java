package main.java.world;

import main.java.heap.HeapItem;

/**
 * Represents the Node object to be used to represent
 * the tiles in the game grid.
 * 
 * @author Manpreet Nanreh
 */
public class Node implements HeapItem {

	protected boolean walkable;
	protected int gridX, gridY;
	protected int gCost, hCost;
	protected int heapIndex;
	protected Node parent;
	protected boolean inPath;

	/**
	 * Create a new Node object with the given x-coordinate, 
	 * y-coordinate and whether it is walkable or not.
	 * 
	 * @param walkable
	 * @param gridX
	 * @param gridY
	 */
	public Node(boolean walkable, int gridX, int gridY) {
		this.walkable = walkable;
		this.gridX = gridX;
		this.gridY = gridY;
		this.inPath = false;
	}

	/**
	 * Gets the F cost of the Node which will be used 
	 * in A* algorithm.
	 * 
	 * @return int The F cost to be used in A* algorithm.
	 */
	public int getFCost() {
		return gCost + hCost;
	}

	/**
	 * Gets the H cost of the Node which will be used
	 * in A* algorithm.
	 * 
	 * @return int The H cost to be used in A* algorithm.
	 */
	public int getHCost() {
		return hCost;
	}
	
	/**
	 * Return true if the Node is walkable.
	 * 
	 * @return true if the Node is walkable;
	 *		   false otherwise.
	 */
	public boolean getWalkable() {
		return walkable;
	}
	
	/**
	 * Gets the x-coordinate of the Node.
	 * 
	 * @return int X-coordinate of the Node.
	 */
	public int getGridX() {
		return gridX;
	}
	
	/**
	 * Gets the y-coordinate of the Node.
	 * 
	 * @return int Y-coordinate of the Node.
	 */
	public int getGridY() {
		return gridY;
	}
	
	/**
	 * Remove the Node from the path generated by 
	 * A* algorithm.
	 */
	public void removeInPath() {
		inPath = false;
	}
	
	/**
	 * Return true if the Node is in the path generated
	 * by A* algorithm.
	 * 
	 * @return true if the Node is in path;
	 * 		   false otherwise.
	 */
	public boolean getInPath() {
		return inPath;
	}

	/**
	 * This is an overridden method to compare Nodes created 
	 * by following the comparable requirements of the A* algorithm. 
	 */
	@Override
	public int compareTo(HeapItem other) {
		int compare;
		if (this.getFCost() < ((Node) other).getFCost())
			compare = -1;
		else if (this.getFCost() > ((Node) other).getFCost())
			compare = 1;
		else {
			if (this.hCost < ((Node) other).hCost)
				compare = -1;
			else if (this.hCost > ((Node) other).hCost)
				compare = 1;
			else
				compare = 0;
		}
		return -compare;
	}

	@Override
	public void setHeapIndex(int index) {
		heapIndex = index;
	}

	@Override
	public int getHeapIndex() {
		return heapIndex;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (!(other.getClass() == this.getClass()))
			return false;
		Node otherNode = (Node) other;
		return this.gridX == otherNode.gridX && this.gridY == otherNode.gridY;
	}
}
