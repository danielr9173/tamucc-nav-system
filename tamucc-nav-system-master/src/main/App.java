/**
 * @author Rafay Shaikh
 * @author 
 * 
 * @since 10/30/2019
 */

package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.Border;

/***
 * Class App controls everything
 *
 * @author Rafay Shaikh, Daniel Ramirez, Charles Quigley
 * @date 04/30/2020
 */
public class App {

	/// ///////////////////
	// PRIVATE VARIABLES
	/// ///////////////////
	
	private JFrame frame;
	private String mapPath = "./src/assets/map.png";
	private ArrayList<Building> buildings = new ArrayList<Building>(); 		//holds all buildings objects

	String buildingName1 = "Nothing", buildingName2 = "Nothing"; //Used to determine distance between starting and ending buildings.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Start GUI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					//timer to delay the the map until welcome screen completes the animation
					new Timer().schedule(new TimerTask() {
						public void run() {
							window.frame.setVisible(true);
						}

					}, 12100); //delay = 12100
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		createBuildings();				//this will generate buildings objects with the data from text file.
		welcomeFrame();					//this will generate a welcome scenario before the actual program begins.
		initialize();					//this will initialize the map program
	}
	
	/// ///////////////////
	// PRIVATE METHODS
	/// ///////////////////
	
	/**
	 * Create buildings to show on the map.
	 */
	private void createBuildings() {
		// Create buildings from data
		try {
			// Open data file
			File mapDataFile = new File("./src/assets/data.txt");
			Scanner mapData = new Scanner(mapDataFile);
			// Parse data
			int index = 0;
			while (mapData.hasNext()) {
				this.buildings.add(new Building(
						mapData.nextLine(), // Name
						mapData.nextLine(), // Description
						mapData.nextLine(),	// Number of rooms
						"./src/assets/" + (index + 1) + ".png" //Map pictures
				));
				index++;
			}

			//We only set the buildingPaths array once. It's a static array. So all
			//Building objects share it. Hence why we don't have to put data in multiple buildingPath arrays
			if(!Building.pathsPopulated)
			{
				Building.setBuildingPaths();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create string array of building names.
	 * @return buildingNames that will fill-up the ComboBox
	 */
	private String[] getBuildingOptions() {
		String[] buildingNames = new String[this.buildings.size()];
		for (int i = 0; i < buildingNames.length; i++) {
			buildingNames[i] = this.buildings.get(i).getName();
		}
		return buildingNames;
	}

	/***
	 * Welcome Frame is a implementation of proxy design pattern
	 *
	 */
	void welcomeFrame()  {
		JFrame welcomeFrame = new JFrame();	//new frame
		welcomeFrame.setUndecorated(true); // Remove title bar
		ImageIcon imgThisImg = new ImageIcon("./src/assets/welcome.gif");//to display picture
		JLabel welcomeData = new JLabel();
		welcomeData.setIcon(imgThisImg);	//label filled with gif image
		welcomeFrame.add(welcomeData);
		welcomeFrame.setSize(482, 311);
		welcomeFrame.setLocationRelativeTo(null);
		welcomeFrame.setVisible(true);
		/*
		* Timer will wait for animation to display for specific amount of time
		*then the map program will begin execution
		*/
		new Timer().schedule(new TimerTask() {
			public void run() {
				// closes the welcome display frame when the actual program begins
				welcomeFrame.dispose();
			}
		}, 13000);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// Create JFrame
		frame = new JFrame("Texas A&M University Corpus Christi Map");
		frame.setResizable(false);
		frame.setBounds(0, 0, 1370, 720); // old w = 1500, old x = 100, y = 100
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//Border object is used to create black lines (in the shape of rectangles) in the window.
		//These rectangular-shaped black lines help differentiate between starting location menus,
		//ending location menus, the map, and the displayed distance and time information.
		Border line = BorderFactory.createLineBorder(Color.BLACK);

		//BLACK LINE BORDERS
		//------------------------------------------
		//Border for the starting Location menus and description. It is The leftmost black Rectangle.
		JLabel startingLocationBorder = new JLabel("");
		startingLocationBorder.setBounds(0, 0, 330, 720);
		startingLocationBorder.setBorder(line);
		frame.getContentPane().add(startingLocationBorder);

		//Border for the TAMUCC map. It is the center most black Rectangle
		JLabel mapRectangleBorder = new JLabel("");
		mapRectangleBorder.setBounds(330, 0, 700, 466);
		mapRectangleBorder.setBorder(line);
		frame.getContentPane().add(mapRectangleBorder);

		//Border for the ending location menus and description. It is the rightmost black rectangle
		JLabel endingLocationBorder = new JLabel("");
		endingLocationBorder.setBounds(1030, 0, 324, 720);
		endingLocationBorder.setBorder(line);
		frame.getContentPane().add(endingLocationBorder);

		//Border for the TAMUCC logo
		JLabel logoBorder = new JLabel("");
		logoBorder.setBounds(330, 466, 240, 215);
		logoBorder.setBorder(line);
		frame.getContentPane().add(logoBorder);

		//Border for the distance and time portion of the window. It is the 2nd rectangle (from the left)
		//that is directly under the TAMUCC map.
		JLabel distanceAndTimeBorder = new JLabel("");
		distanceAndTimeBorder.setBounds(570, 466, 460, 215);
		distanceAndTimeBorder.setBorder(line);
		frame.getContentPane().add(distanceAndTimeBorder);
		//----------------------------------------------------


		// Combo box label
		JLabel lblComboBox = new JLabel("Select a starting location:"); //Old text: "Select a building"
		lblComboBox.setBounds(20, 10, 394, 14); //old x = 1090, y = 10 // 720, 10
		frame.getContentPane().add(lblComboBox);

		// Combo box label
		JLabel lblComboBox2 = new JLabel("Select an ending location:");
		lblComboBox2.setBounds(1040, 10, 394, 14);
		frame.getContentPane().add(lblComboBox2);

		// Combo box - starting location
		JComboBox comboBox = new JComboBox(this.getBuildingOptions());
		comboBox.setBounds(20, 30, 300, 30); //old x = 1090, y = 30, width = 394, height = 30
		frame.getContentPane().add(comboBox);

		// Combo box - ending location
		JComboBox comboBox2 = new JComboBox(this.getBuildingOptions());
		comboBox2.setBounds(1040, 30, 300, 30); //old x = 1090, y = 30, width = 394, height = 30
		frame.getContentPane().add(comboBox2);

		// Overlay icon on map for Starting Location
		JLabel buildingOverlay = new JLabel();
		buildingOverlay.setBounds(330, 0, 700, 466); // old w = 1080, h = 720
		frame.getContentPane().add(buildingOverlay);

		// Overlay icon on map for Ending Location
		JLabel buildingOverlay2 = new JLabel();
		buildingOverlay2.setBounds(330, 0, 700, 466); // old w = 1080, h = 720
		frame.getContentPane().add(buildingOverlay2);

		// Display TAMUCC Map
		JLabel map = new JLabel();
		map.setBounds(330, 0, 700, 466); // old w = 1080, h = 720
		map.setIcon(new ImageIcon(mapPath));
		frame.getContentPane().add(map);


		//Written 'Building Description' to display for Starting Location
		JLabel lblDescription = new JLabel("Building Description:");
		lblDescription.setBounds(20, 71, 394, 14);
		frame.getContentPane().add(lblDescription);

		JLabel buildingDescrption = new JLabel("");
		buildingDescrption.setVerticalAlignment(SwingConstants.TOP);
		buildingDescrption.setBounds(20, 96, 280, 720); // old h = 584
		frame.getContentPane().add(buildingDescrption);


		//Written 'Building Description' to display for Ending Location
		JLabel lblDescription2 = new JLabel("Building Description:");
		lblDescription2.setBounds(1040, 71, 394, 14); //old x = 1090, y = 71, w = 394, h = 14
		frame.getContentPane().add(lblDescription2);

		JLabel buildingDescrption2 = new JLabel("");
		buildingDescrption2.setVerticalAlignment(SwingConstants.TOP);
		buildingDescrption2.setBounds(1040, 96, 280, 720); //old x = 1090, y = 96, w = 280, h = 584
		frame.getContentPane().add(buildingDescrption2);


		//Written to give the header for the distance and time information.
		JLabel distanceAndTime = new JLabel("Distance between location and estimated time of arrival:");
		distanceAndTime.setBounds(575, 471, 400, 14);
		frame.getContentPane().add(distanceAndTime);

		JLabel distanceAndTimeDescription = new JLabel("");
		distanceAndTimeDescription.setBounds(20, 20, 200, 20);
		distanceAndTime.add(distanceAndTimeDescription);


		//Written prompt to display
		JLabel lblUrlClick = new JLabel("Click On The Logo For More Information");
		lblUrlClick.setBounds(335, 471, 394, 14); //old x = 1090, y = 420
		frame.getContentPane().add(lblUrlClick);

		//TAMUCC logo where user will click to open university's website
		ImageIcon logoPic = new ImageIcon("./src/assets/logo.jpg");//to display url logo
		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(logoPic);
		lblLogo.setBounds(335, 491, logoPic.getIconWidth(), logoPic.getIconHeight()); //old x = 1250, y = 450
		//Mouselistner activates when user click on the logo image.
		lblLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
				try {
					Desktop desktop = java.awt.Desktop.getDesktop(); //this will open the browser
					URI oURL = new URI("http://www.tamucc.edu");// with this URL
					desktop.browse(oURL);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(lblLogo);

		// Create combo box listener for Starting Location Menu
		comboBox.addActionListener(new ActionListener() {
			// On item selected
			@Override
			public void actionPerformed(ActionEvent ae) {
				// Get selected building
				Building building = buildings.get(comboBox.getSelectedIndex());
				buildingOverlay.setIcon(new ImageIcon(building.getImagePath()));
				buildingDescrption.setText(building.toString());

				buildingName1 = building.getName();

				if (buildingName1.equals(buildingName2)) {
					distanceAndTimeDescription.setForeground(Color.RED);
					distanceAndTimeDescription.setText("The Starting and Ending Locations are Identical");
					//distanceAndTimeDescription.updateUI(); //Update the JLabel to show this new text.
					System.out.println(distanceAndTimeDescription.getText());
				}

				if (!buildingName2.equals("Nothing")) //If the ending destination has been picked already.
				{                                    //It wouldn't be picked during the start of the program.

					for (int nameIndex = 0; nameIndex < Building.maxFirstBracketIndex; nameIndex++) {


					}
				}
			}
		});

		//Create Combo box Listener for Ending location menu
		comboBox2.addActionListener(new ActionListener() {
			// On item selected
			@Override
			public void actionPerformed(ActionEvent ae) {
				// Get selected building
				Building building2 = buildings.get(comboBox2.getSelectedIndex());
				buildingOverlay2.setIcon(new ImageIcon(building2.getImagePath()));
				buildingDescrption2.setText(building2.toString());

				buildingName2 = building2.getName();

				if (buildingName1.equals(buildingName2)) {
					distanceAndTimeDescription.setForeground(Color.RED);
					distanceAndTimeDescription.setText("The Starting and Ending Locations are Identical");
				}

				if (!buildingName1.equals("Nothing")) //If the starting destination has been picked already.
				{                                    //It wouldn't be picked during the start of the program.


					for (int i = 0; i < Building.maxFirstBracketIndex; i++) {



						//First bracket represents starting or ending building names.
						//second bracket represents ending or starting building names.
							if( (buildingName1.toUpperCase().equals(Building.buildingPaths[i][0][0].toUpperCase()) && buildingName2.toUpperCase().equals(Building.buildingPaths[i][1][0].toUpperCase()))
								||(buildingName2.toUpperCase().equals(Building.buildingPaths[i][0][0].toUpperCase()) && buildingName1.toUpperCase().equals(Building.buildingPaths[i][1][0].toUpperCase()) ) )
							{

								//Third bracket represents the distance in feet.
								distanceAndTimeDescription.setText("Estimated Distance: " + Building.buildingPaths[i][0][1] + " ft");

								System.out.println(Building.buildingPaths[i][1][1]);

							}

						}

					}
				}
		});
	}
}

