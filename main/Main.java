
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import containers.*;
import ports.Port;
import ships.Ship;

import java.util.ArrayList;

/**
 * Runnable Main Class that contains the main method that runs the program.
 * 
 * @author Ramazan Burak Sarýtaþ
 * 
 */
public class Main {
	/**
	 * Main method that runs the program.
	 * Reads the input file. Creates ports, ships and containers in the input order and stores them in the ArrayLists. <br>
	 * Executes load, unLoad, reFuel and sail actions.
	 * 
	 * @param args the arguments 
	 * @throws FileNotFoundException if the input file is not found
	 */
	public static void main(String[] args) throws FileNotFoundException {

		/**
		 * Keeps track of the containers that get created.
		 */
		ArrayList<Container> allContainers = new ArrayList<Container>();
		/**
		 * Keeps track of the ships that get created.
		 */
		ArrayList<Ship> ships = new ArrayList<Ship>();
		/**
		 * Keeps track of the ports that get created.
		 */
		ArrayList<Port> ports = new ArrayList<Port>();
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		int N = in.nextInt();

		// Moves Cursor to the Next Line:
		in.nextLine();

		// Fields for the program to store input in an array.
		
		/**
		 * Counter field to use in storing the input line to create a new port. <br>
		 * Also works as the ID of that port. Gets incremented by one in every use.
		 * 
		 */
		int currentPortID = 0;
		/**
		 * Counter field to use in storing the input line to create a new ship. <br>
		 * Also works as the ID of that ship. Gets incremented by one in every use.
		 * 
		 */
		int currentShipID = 0;
		/**
		 * Counter field to use in storing the input line to create a new container. <br>
		 * Also works as the ID of that container. Gets incremented by one in every use.
		 * 
		 */
		int currentContainerID = 0;
		/**
		 * Counter field to use in storing the input line for the actions. <br>
		 * Gets incremented by one in every use.
		 */
		int currentActionID = 0;
		/**
		 * Stores the input line to create a new port.
		 */
		ArrayList<String> portData = new ArrayList<String>();
		/**
		 * Stores the input line to create a new ship.
		 */
		ArrayList<String> shipData = new ArrayList<String>();
		/**
		 * Stores the input line to create a new container.
		 */
		ArrayList<String> containerData = new ArrayList<String>();
		/**
		 * Stores the input line for the actions.
		 */
		ArrayList<String> actionData = new ArrayList<String>();
		
		// Stores input lines in an array
		for (int i = 0; i < N; i++) {

			String inputLine = in.nextLine();
			String[] tempArray = inputLine.split(" ");

			if (Integer.parseInt(tempArray[0])==1) {		// inputs with number 1
				containerData.add(currentContainerID, inputLine);
				currentContainerID += 1;
			}

			else if (Integer.parseInt(tempArray[0])==2) {	// inputs with number 2
				shipData.add(currentShipID, inputLine);
				currentShipID += 1;
			}

			else if (Integer.parseInt(tempArray[0])==3) {	// inputs with number 2
				portData.add(currentPortID, inputLine);
				currentPortID += 1;
			}

			else {											// inputs with number 3, 4, 5, 6, 7, 8
				actionData.add(currentActionID, inputLine);
				currentActionID += 1;
			}	    
		}

		// Creates PORT objects
		for (int i = 0; i < portData.size(); i++) {

			ports.add(i, new Port(i, 										// ID
					Double.parseDouble(portData.get(i).split(" ")[1]),		// X
					Double.parseDouble(portData.get(i).split(" ")[2])));	// Y
		}

		// Creates SHIP objects
		for (int i = 0; i < shipData.size(); i++) {

			ships.add(i, new Ship(i, 											// ID
					ports.get(Integer.parseInt(shipData.get(i).split(" ")[1])),	// Port
					Integer.parseInt(shipData.get(i).split(" ")[2]),			// MaxWeight
					Integer.parseInt(shipData.get(i).split(" ")[3]),			// Max # of Container
					Integer.parseInt(shipData.get(i).split(" ")[4]),			// Max # of Heavy Container
					Integer.parseInt(shipData.get(i).split(" ")[5]),			// Max # of Refrigerated Container
					Integer.parseInt(shipData.get(i).split(" ")[6]),			// Max # of Liquid Container
					Double.parseDouble(shipData.get(i).split(" ")[7])));		// Fuel Consumption Per KM
		}

		// Creates CONTAINER objects
		for (int i = 0; i < containerData.size(); i++) {

			// BasicContainer or HeavyContainer
			if (containerData.get(i).split(" ").length == 3) {

				if (Integer.parseInt(containerData.get(i).split(" ")[2]) <= 3000) {
					allContainers.add(i, new BasicContainer(i,						// ID							// ID
							Integer.parseInt(containerData.get(i).split(" ")[2])));	// Weight
				}
				else {
					allContainers.add(i, new HeavyContainer(i,						// ID								// ID
							Integer.parseInt(containerData.get(i).split(" ")[2])));	// Weight
				}
			}

			// RefrigeratedContainer or LiquidContainer
			else {
				if (containerData.get(i).split(" ")[3].equals("R") == true) {
					allContainers.add(i, new RefrigeratedContainer(i,				 // ID							 // ID
							Integer.parseInt(containerData.get(i).split(" ")[2])));	 // Weight
				}
				else {
					allContainers.add(i, new LiquidContainer(i,	            		// ID							// Port ID
							Integer.parseInt(containerData.get(i).split(" ")[2])));	// Weight
				}
			}

			ports.get(Integer.parseInt(containerData.get(i).split(" ")[1]))
			.containers.add(allContainers.get(i));

		}

		// Adds ships to their current ports' current array list.
		for (Ship s : ships) {
			s.getCurrentPort().incomingShip(s);
		}

		// Executes Actions
		for (int i = 0; i < actionData.size(); i++) {

			// Loads Container to Ship
			if (Integer.parseInt(actionData.get(i).split(" ")[0]) == 4) {

				ships.get(Integer.parseInt(actionData.get(i).split(" ")[1])).
				load(allContainers.get(Integer.parseInt(actionData.get(i).split(" ")[2])));
			}

			// Unloads Container from Ship to Current Port
			else if (Integer.parseInt(actionData.get(i).split(" ")[0]) == 5) {

				ships.get(Integer.parseInt(actionData.get(i).split(" ")[1])).
				unLoad(allContainers.get(Integer.parseInt(actionData.get(i).split(" ")[2]))); 
			}

			// Sails Ship to the given Port
			else if (Integer.parseInt(actionData.get(i).split(" ")[0]) == 6) {

				ships.get(Integer.parseInt(actionData.get(i).split(" ")[1])).
				sailTo(ports.get(Integer.parseInt(actionData.get(i).split(" ")[2]))); 
			}

			// Adds Fuel to the Ship
			else if (Integer.parseInt(actionData.get(i).split(" ")[0]) == 7) {

				ships.get(Integer.parseInt(actionData.get(i).split(" ")[1])).
				reFuel(Double.parseDouble(actionData.get(i).split(" ")[2])); 
			}
		}


		// OUTPUT
		// Port
		for (Port p : ports) {
			out.printf("Port %d: (%.2f, %.2f)\n", p.getID(), p.getX(), p.getY());

			ArrayList<RefrigeratedContainer> refrigerated = new ArrayList<RefrigeratedContainer>();
			ArrayList<LiquidContainer> liquid = new ArrayList<LiquidContainer>();
			ArrayList<HeavyContainer> heavy = new ArrayList<HeavyContainer>();
			ArrayList<BasicContainer> basic = new ArrayList<BasicContainer>();

			// Categorization of Containers
			for (Container c : p.getCurrentContainersInPort(p.containers)) {	

				if (c instanceof RefrigeratedContainer) {
					refrigerated.add((RefrigeratedContainer) c);
				}

				else if (c instanceof LiquidContainer) {
					liquid.add((LiquidContainer) c);
				}

				else if (c instanceof HeavyContainer) {
					heavy.add((HeavyContainer) c);
				}

				else {
					basic.add((BasicContainer) c);
				}
			}

			// Printing Containers
			if (basic.size()>0) {
				out.print("  BasicContainer:");
				for (BasicContainer bc : basic ) {
					out.print(" " + bc.getID());
				}
				out.println();
			}

			if (heavy.size()>0) {
				out.print("  HeavyContainer:");
				for (HeavyContainer hc : heavy ) {
					out.print(" " + hc.getID());
				}
				out.println();
			}

			if (refrigerated.size()>0) {
				out.print("  RefrigeratedContainer:");
				for (RefrigeratedContainer rc : refrigerated ) {
					out.print(" " + rc.getID());
				}
				out.println();
			}

			if (liquid.size()>0) {
				out.print("  LiquidContainer:");
				for (LiquidContainer lc : liquid ) {
					out.print(" " + lc.getID());
				}
				out.println();
			}

			for (Ship s : p.getCurrentShipsInPort(p.current)) {

				out.printf("  Ship %d: %.2f\n", s.getID(), s.getFuel());

				ArrayList<RefrigeratedContainer> refrigeratedInShip = new ArrayList<RefrigeratedContainer>();
				ArrayList<LiquidContainer> liquidInShip = new ArrayList<LiquidContainer>();
				ArrayList<HeavyContainer> heavyInShip = new ArrayList<HeavyContainer>();
				ArrayList<BasicContainer> basicInShip = new ArrayList<BasicContainer>();

				// Categorization of Containers
				for (Container cInShip : s.getCurrentContainers()) {	

					if (cInShip instanceof RefrigeratedContainer) {
						refrigeratedInShip.add((RefrigeratedContainer) cInShip);
					}

					else if (cInShip instanceof LiquidContainer) {
						liquidInShip.add((LiquidContainer) cInShip);
					}

					else if (cInShip instanceof HeavyContainer) {
						heavyInShip.add((HeavyContainer) cInShip);
					}

					else {
						basicInShip.add((BasicContainer) cInShip);
					}
				}

				// Printing Containers
				if (basicInShip.size()>0) {
					out.print("    BasicContainer:");
					for (BasicContainer bc : basicInShip ) {
						out.print(" " + bc.getID());
					}
					out.println();
				}

				if (heavyInShip.size()>0) {
					out.print("    HeavyContainer:");
					for (HeavyContainer hc : heavyInShip ) {
						out.print(" " + hc.getID());
					}
					out.println();
				}

				if (refrigeratedInShip.size()>0) {
					out.print("    RefrigeratedContainer:");
					for (RefrigeratedContainer rc : refrigeratedInShip ) {
						out.print(" " + rc.getID());
					}
					out.println();
				}

				if (liquidInShip.size()>0) {
					out.print("    LiquidContainer:");
					for (LiquidContainer lc : liquidInShip ) {
						out.print(" " + lc.getID());
					}
					out.println();
				}
			}
		}

		in.close();
		out.close();
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

