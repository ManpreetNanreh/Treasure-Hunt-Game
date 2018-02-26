package test.java;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Test;
import main.java.heap.*;
import main.java.world.Grid;
import main.java.world.Node;

public class GridTest {
	//
	//
	//Functionality test
	@Test(timeout=100)
	public void testGrid() {
		Grid g = new Grid();
		assertEquals(g.getHeight(), 35);
		assertEquals(g.getWidth(), 40);
		assertEquals(g.getPercent(), 20);
	}

	@Test(timeout=100)
	public void testGridIntIntInt() {
		//appropriate sizes
		Grid g = new Grid(100, 100, 30);
		assertEquals(g.getHeight(), 100);
		assertEquals(g.getWidth(), 100);
		assertEquals(g.getPercent(), 30);
		
		//percent < 0
		Grid g1 = new Grid(100, 100, -10);
		assertEquals(g1.getHeight(), 100);
		assertEquals(g1.getWidth(), 100);
		assertEquals(g1.getPercent(), 20);
		
		//percent > 100
		Grid g11 = new Grid(100, 100, 200);
		assertEquals(g11.getHeight(), 100);
		assertEquals(g11.getWidth(), 100);
		assertEquals(g11.getPercent(), 20);
	}

	@Test(timeout=100)
	public void testGetWidth() {
		Grid g = new Grid(100, 100, 30);
		assertEquals(g.getWidth(), 100);
	}

	@Test(timeout=100)
	public void testGetHeight() {
		Grid g = new Grid(100, 100, 30);
		assertEquals(g.getHeight(), 100);
	}

	@Test(timeout=100)
	public void testGetPercent() {
		Grid g = new Grid(100, 100, 30);
		assertEquals(g.getPercent(), 30);
	}

	@Test(timeout=100)
	public void testGetBoat() {
		Grid g = new Grid(100, 100, 30);
		Node n = new Node(false, 100, 0);
		g.boat = n;
		assertEquals(g.boat, n);
	}

	@Test(timeout=100)
	public void testMove() {
		Grid g = new Grid(100, 100, 30);
		Node boat = new Node(false, 2,3);
		int expectedX = 1;
		int expectedY = 2;
		
		g.setBoat(boat);
		g.move("NW");
		
		if(!g.getMap()[2][1].getWalkable()) {
			expectedX = 2;
			expectedY = 3;
		}
		assertEquals(g.getBoat().getGridX(), expectedX);
		assertEquals(g.getBoat().getGridY(), expectedY);
	}

	@Test(timeout=100)
	public void testGetTreasure() {
		Grid g = new Grid(100, 100, 30);
		Node boat = new Node(false, 2,3);
		g.boat = boat;
		Node treasure = new Node(false, 2, 1);
		g.treasure = treasure;
		//in distance
		assertEquals(g.getTreasure(30), treasure);
		//not in distance
		assertEquals(g.getTreasure(10), null);
	}
	
	@Test(timeout=1000)
	public void test1() throws HeapFullException, HeapEmptyException {
		Grid g = loadGrid("src/test/resources/test1.txt");
		g.findPath(g.boat, g.treasure);
		ArrayList<Node> path = g.retracePath(g.boat, g.treasure);
		assertEquals(1, path.size());
	}
	
	@Test(timeout=1000)
	public void test2() throws HeapFullException, HeapEmptyException {
		Grid g = loadGrid("src/test/resources/test2.txt");
		g.findPath(g.boat, g.treasure);
		ArrayList<Node> path = g.retracePath(g.boat, g.treasure);
		assertEquals(2, path.size());
	}
	
	@Test(timeout=1000)
	public void test3() throws HeapFullException, HeapEmptyException {
		Grid g = loadGrid("src/test/resources/test3.txt");
		g.findPath(g.boat, g.treasure);
		ArrayList<Node> path = g.retracePath(g.boat, g.treasure);
		assertEquals(4, path.size());
	}
	
	@Test(timeout=1000)
	public void test4() throws HeapFullException, HeapEmptyException {
		Grid g = loadGrid("src/test/resources/test4.txt");
		g.findPath(g.boat, g.treasure);
		ArrayList<Node> path = g.retracePath(g.boat, g.treasure);
		assertEquals(6, path.size());
	}

	
	@Test(timeout=1000)
	public void test5() throws HeapFullException, HeapEmptyException {
		Grid g = loadGrid("src/test/resources/test5.txt");
		g.findPath(g.boat, g.treasure);
		ArrayList<Node> path = g.retracePath(g.boat, g.treasure);
		assertEquals(8, path.size());
	}
	
	/**
	 * Loads the game grid from a given test file.
	 * 
	 * @param pathName
	 * @return Grid The game grid.
	 */
	private Grid loadGrid(String pathName) {
		Grid g = new Grid(60, 15, 20);
		File file = new File(pathName);
		Node[][] map = g.getMap();
		String textLine = "";
		char ch;
		Node node;
		int i = 0;
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()){
				textLine = scan.nextLine();
				//System.out.println(textLine);
				for(int j = 0; j < 60; j++) {
				   ch = textLine.charAt(j);
				   if(ch == '+') 
					   node = new Node(false, j, i);
				   else
					   node = new Node(true, j, i);
				   if(ch == 'B')
					   g.boat = node;
				   if(ch == 'T')
					   g.treasure = node;
				   map[i][j] = node;
				}
				i++;
			}
			scan.close();
			return g;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}