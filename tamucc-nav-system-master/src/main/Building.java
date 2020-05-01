/**
 * @author Rafay Shaikh
 * @author Henry Lee Barton III
 * 
 * @since 10/30/2019
 */

package main;


import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/***
 * Building class holds the data about buildings in TAMUCC
 */
public class Building {

	//static means same string array is shared by all objects of this class.
	//first [] holds the current Building. Second [] holds the ending Building.
	//third [] holds distance between the two.
	public static String[][][] buildingPaths;

	//To keep track of how many elements each index of the String[][][] can hold.
	public static Integer maxFirstBracketIndex;
	public static int maxSecondBracketIndex = 2, maxThirdBracketIndex = 2;

	//false until the first Building object is created.
	//true after that. So, we only popoulate the buildingPaths array one time,
	//(when the first building object is created), and then all Building objects will have the data.
	public static boolean pathsPopulated = false;


	/// ///////////////////
	// PRIVATE VARIABLES
	/// ///////////////////
	
	private String name; // Name of building
	private String desc; // Description of the building
	private String numRooms; // Number of rooms in the building
	private String path; // Path to the image of the building
	
	/// ///////////////////
	// CONSTRUCTOR
	/// ///////////////////
	
	/**
	 * Default constructor
	 */
	public Building() {
		this.name = "";
		this.desc = "";
		this.numRooms = "";
		this.path = "";
	}
	
	/**
	 * Constructor function set name, desc, rooms
	 * @param name
	 * @param desc
	 * @param numRooms
	 * @param path
	 */
	public Building(String name, String desc, String numRooms, String path) {
		this.name = name;
		this.desc = desc;
		this.numRooms = numRooms;
		this.path = path;
	}


	//Array holding one particular building's path to every other building.
	//--------------------------------------------------------------------------
	// 0 = Bay Hall, 1 = Center for Instruction, 2 = Center for Art, 3 = Center for Sci
	// 4 = Class room east/west, 5 = corpus christi hall, 6 = dr robert, 7 = Early childhood,
	//8 = Education Center Math/Sci, 9 = Engineer lab, 10 = Faculty Center
	//11 = Glass Student, 12 = Harte Research, 13 = Island Hall, 14 = Mary and Jeff Bell,
	//15 = Michael and Karen, 16 = Tidal Hall, 17 = Carlos Truan, 18 = Student Services,
	//19 = University center, 20 = University police, 21 = Bayside

	
	/// ///////////////////
	// GET METHODS
	/// ///////////////////
	
	/**
	 * Get building name
	 * @return String
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get building description
	 * @return String
	 */
	public String getDesc() {
		return this.desc;
	}
	
	/**
	 * Get number of rooms
	 * @return String
	 */
	public String getNumRooms() {
		return this.numRooms;
	}
	
	/**
	 * Get image path of building
	 * @return
	 */
	public String getImagePath() {
		return this.path;
	}
	
	/// ///////////////////
	// SET METHODS
	/// ///////////////////
	
	/**
	 * Set name of building
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Set description of building
	 * @param desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * Set number of rooms in building
	 * @param numRooms
	 */
	public void setNumRooms(String numRooms) {
		this.numRooms = numRooms;
	}
	
	/**
	 * Set the path of the image of the building
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	public static void setBuildingPaths()
	{

		pathsPopulated = true;

		String parseLine;

		//InputStream distanceStream = Building.class.getResourceAsStream("bin/assets/distances.txt");
		//BufferedReader distanceReader = new BufferedReader(new InputStreamReader(distanceStream));

		try {
			Scanner distanceReader = new Scanner(new File("bin/assets/distances.txt"));


			//Discard the first line of the text file. That line is just instructions.
			distanceReader.nextLine();

			//Integer.parseInt() takes a string value and converts it into an integer.
			maxFirstBracketIndex = Integer.parseInt(distanceReader.nextLine());

			buildingPaths = new String[maxFirstBracketIndex][maxSecondBracketIndex][maxThirdBracketIndex];



			for(int i = 0; i < maxFirstBracketIndex; i++) {
				parseLine = distanceReader.nextLine();
				String[] tokens = parseLine.split(":"); //delimiter between tokens is ":".

				buildingPaths[i][0][0] = tokens[0];
				buildingPaths[i][1][0] = tokens[1];
				buildingPaths[i][1][1] = tokens[2];


				System.out.printf("%s  %s  %s\n", buildingPaths[i][0][0], buildingPaths[i][1][0], buildingPaths[i][1][1]);

			}

		}catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	
	/// ///////////////////
	// FUNCTIONS
	/// ///////////////////
	
	@Override
	public String toString() {
		return "<html>" + this.name + "<br>"
				+ "<br>"
				+ this.desc
				+ "<br><br>"
				+ "Total Rooms: " + this.numRooms
				+ "<br></html>";
	}
}
