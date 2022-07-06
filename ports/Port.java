
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ports;

import java.util.ArrayList;

import containers.Container;
import interfaces.IPort;
import ships.Ship;

/**
 * 
 * @author Ramazan Burak Sarýtaþ
 *
 */
public class Port implements IPort {
	
	/**
	 * The ID of the port.
	 */
	private final int ID;
	/**
	 * The X coordinate of the port.
	 */
	private final double X;
	/**
	 * The Y coordinate of the port.
	 */
	private final double Y;
	/**
	 * Keeps track of the containers currently in the port.
	 */
	public ArrayList<Container> containers = new ArrayList<Container>();
	/**
	 * Keeps track of every ship that has visited.
	 */
	public ArrayList<Ship> history = new ArrayList<Ship>();
	/**
	 * Keeps track of the ships currently in the port.
	 */
	public ArrayList<Ship> current = new ArrayList<Ship>();

	/**
	 * Constructor for the objects of Port class.
	 * 
	 * @param ID the ID of the port to set
	 * @param X the X coordinate of the port to set
	 * @param Y	the Y coordinate of the port to set
	 */
	public Port(int ID, double X, double Y) {
		
		this.ID = ID;
		this.X = X;
		this.Y = Y;
	}
	
	/**
	 * Calculates the distance between ports.
	 * @param other the destination port
	 * @return the distance between ports
	 */
	
	public double getDistance(Port other) {
		return Math.sqrt(Math.pow(other.X - this.X, 2) + Math.pow(other.Y - this.Y, 2));
	
	}
	/**
	 * Adds the ship to the current ArrayList of the port.
	 * @param s the incoming ship
	 */
	@Override
	public void incomingShip(Ship s) {
		if (current.contains(s) == false) { 	// NO DUPLICATES
			current.add(s);
		}
	}
	
	
	
	/**
	 * Adds the ship to the history ArrayList of the port.
	 * @param s the outgoing ship
	 */
	@Override
	public void outgoingShip(Ship s) {
		if (history.contains(s) == false) {		// NO DUPLICATES
			history.add(s);
		}
		current.remove(s);	
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * 
	 * @return the X coordinate of the port
	 */
	public double getX() {
		return X;
	}
	
	/**
	 * 
	 * @return the Y coordinate of the port
	 */
	public double getY() {
		return Y;
	}
	
	/**
	 * 
	 * Sorts Containers in Port. 
	 * <br>
	 * (Uses a bubble sort kind of sorting algorithm.)
	 * 
	 *  @param c the ArrayList of Containers
	 *  @return the sorted ArrayList of Containers
	 *  
	 */
	public ArrayList<Container> getCurrentContainersInPort(ArrayList<Container> c) {
		
		ArrayList<Container> sortedContainers = new ArrayList<Container>();
		sortedContainers.addAll(c);
	   
		int length = sortedContainers.size();
		
		for (int i = 0; i < length; i++) {
	        for (int j = i + 1; j < length; j++) {
	            Container temp;
	            if (sortedContainers.get(i).getID() > sortedContainers.get(j).getID()) {
	                temp = sortedContainers.get(i);
	                sortedContainers.remove(i);
	                sortedContainers.add(i, sortedContainers.get(j-1));
	                sortedContainers.remove(j);
	                sortedContainers.add(j, temp);
	            }
	        }
	    }
	return sortedContainers;		
	}
	
	
	/**
	 * 
	 * Sorts Ships in Port. 
	 * <br>
	 * (Uses a bubble sort kind of sorting algorithm.)
	 * 
	 * @param s the ArrayList of Ships
	 * @return the sorted ArrayList of Ships
	 * 
	 */
	public ArrayList<Ship> getCurrentShipsInPort(ArrayList<Ship> s) {
		
		ArrayList<Ship> sortedShips = new ArrayList<Ship>();
		sortedShips.addAll(s);
	   
		int length = sortedShips.size();
		
		for (int i = 0; i < length; i++) {
	        for (int j = i + 1; j < length; j++) {
	            Ship temp;
	            if (sortedShips.get(i).getID() > sortedShips.get(j).getID()) {
	                temp = sortedShips.get(i);
	                sortedShips.remove(i);
	                sortedShips.add(i, sortedShips.get(j-1));
	                sortedShips.remove(j);
	                sortedShips.add(j, temp);
	            }
	        }
	    }
	return sortedShips;		
	}
	
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

