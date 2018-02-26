package test.java;

import main.java.heap.HeapEmptyException;
import main.java.heap.HeapFullException;
import main.java.redbeard.TreasureHunt;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import main.java.world.Grid;
import main.java.world.Node;

public class GameTest {

	/**
	 * Load the game grid from a given file. 
	 * 
	 * @param args
	 * @throws IOException 
	 * @throws HeapEmptyException 
	 * @throws HeapFullException 
	 */
	private static Grid loadGrid(String pathName) throws IOException {
		//Set the grid size to 15x60 because all test cases are of that size.
        Grid g = new Grid(60, 15, 20);
        FileInputStream fstream = new FileInputStream(pathName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String textLine = "";
		char ch;
		Node node;
		Node[][] processedMap = new Node[15][60];
		int i = 0;

		while ((textLine = br.readLine()) != null)   {
			// Print the content on the console
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
			   processedMap[i][j] = node;
			}
			i++;
		}
		br.close();
		g.setMap(processedMap);
		return g;
	}
	
	public static void main(String[] args) throws HeapFullException, HeapEmptyException, IOException {
		//********************
		//This part of the code creates a treasure hunt game and allows you to play the game.
		//Either let the code generate its own game grid or supply one through a file.
		//Primary purpose here is to test the game yourself.
		//Either run the game with custom settings or run it with default settings.
		//********************
		
		TreasureHunt tGame = null;
		int loadOption;
		int gameOption = 1;
		int playOption;
		int width;
		int height;
		int landPercent;
		int sonar;
		int sonarRange;
		String gridPathName;
		String commandPathName;
		Scanner scn = new Scanner(System.in);

		System.out.println("Press (1) to let the code make the game grid.");
		System.out.println("Press (2) to load the game grid from a file.");
		loadOption = scn.nextInt();
		System.out.println("**************************************************");
		scn.nextLine();
		
		if(loadOption == 2) {
			System.out.println("Enter the file path for game grid:");
			gridPathName = scn.nextLine();
			System.out.println("**************************************************");
			tGame = new TreasureHunt();
			tGame.setIslands(loadGrid(gridPathName));
		}else if(loadOption == 1) {
			System.out.println("Press (1) to play game with Default settings.");
			System.out.println("Press (2) to play game with Custom settings.");
			gameOption = scn.nextInt();
			System.out.println("**************************************************");
			scn.nextLine();
		}
		
		if(gameOption == 1) {
			tGame = new TreasureHunt();
		}else if(gameOption == 2) {
			System.out.print("Enter Game Width: ");
			width = scn.nextInt();
			scn.nextLine();
			System.out.print("Enter Game Height: ");
			height = scn.nextInt();
			scn.nextLine();
			System.out.print("Enter Game Land Percentage: ");
			landPercent = scn.nextInt();
			scn.nextLine();
			System.out.print("Enter number of Sonars: ");
			sonar = scn.nextInt();
			scn.nextLine();
			System.out.print("Enter Sonar range: ");
			sonarRange = scn.nextInt();
			scn.nextLine();
			tGame = new TreasureHunt(height, width, landPercent, sonar, sonarRange);
			System.out.println("**************************************************");
		}
		
		System.out.println("Press (1) to enter the play commands on java shell.");
		System.out.println("Press (2) to load the play commands from a file.");
		playOption = scn.nextInt();
		scn.nextLine();
		System.out.println("**************************************************");
		
		if(playOption == 1) {
			tGame.playFromShell();
		}else if(playOption == 2) {
			System.out.println("Enter the path name for play command file: ");
			commandPathName = scn.nextLine();
			System.out.println("**************************************************");
			tGame.playFromFile(commandPathName);
		}
		scn.close();
	}
}