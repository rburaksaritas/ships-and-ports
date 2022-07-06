
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ships;

import java.util.ArrayList;

import containers.*;
import interfaces.IShip;
import ports.Port;

/**
 * 
 * @author Ramazan Burak Sarýtaþ
 *
 */
public class Ship implements IShip {
	
	/**
	 * The ID of the ship.
	 */
	private final int ID;
	/**
	 * The fuel of the ship.<br>
	 * It will be set to 0 at the beginning as default and will be increased by reFuel method.
	 */
	private double fuel;
	/**
	 * The current port that the Ship is at.
	 */
	private Port currentPort;
	/**
	 * The total weight capacity of the ship.
	 */
	private int totalWeightCapacity;
	/**
	 * The maximum number of containers ship can carry.
	 */
	private int maxNumberOfAllContainers;
	/**
	 * The maximum number of heavy containers ship can carry.
	 */
	private int maxNumberOfHeavyContainers;
	/**
	 * The maximum number of refrigerated containers ship can carry.
	 */
	private int maxNumberOfRefrigeratedContainers;
	/**
	 * The maximum number of liquid containers ship can carry.
	 */
	private int maxNumberOfLiquidContainers;
	/**
	 * The fuel consumption of the ship per KM. 
	 * <br>
	 * Only the fuel consumption of the ship itself. Total consumption will be calculated later, <br>
	 * by including the fuel consumption of the containers that the ship is carrying.  
	 */
	private double fuelConsumptionPerKM;
	
	/**
	 * Keeps track of the containers in the ship.
	 */
	ArrayList<Container> currentContainers = new ArrayList<Container>();

	/**
	 * Constructor for the objects of Ship class.
	 * 
	 * @param ID the ID of the ship to set
	 * @param p the port of the ship to set
	 * @param totalWeightCapacity the total weight capacity of the ship to set
	 * @param maxNumberOfAllContainers the maximum number of containers ship can carry to set
	 * @param maxNumberOfHeavyContainers the maximum number of heavy containers ship can carry to set
	 * @param maxNumberOfRefrigeratedContainers	the maximum number of refrigerated containers ship can carry to set
	 * @param maxNumberOfLiquidContainers the maximum number of liquid containers ship can carry to set
	 * @param fuelConsumptionPerKM the fuel consumption of the ship per KM
	 */
	public Ship(int ID, Port p, int totalWeightCapacity, int
			maxNumberOfAllContainers, int maxNumberOfHeavyContainers,
			int maxNumberOfRefrigeratedContainers, int
			maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {
		
		this.fuel = 0;
		this.ID = ID;
		this.setCurrentPort(p);
		this.totalWeightCapacity = totalWeightCapacity;
		this.maxNumberOfAllContainers = maxNumberOfAllContainers;
		this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers;
		this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
		this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
		this.fuelConsumptionPerKM = fuelConsumptionPerKM;
		
	}
	/**
	 * Number of containers that the ship is currently carrying. <br>
	 * It will be compared to the max number of containers that the ship can carry in the load method.
	 */
	private int numberOfContainers = 0;
	/**
	 * Number of heavy containers that the ship is currently carrying. <br>
	 * It will be compared to the max number of heavy containers that the ship can carry in the load method.
	 */
	private int numberOfHeavyContainers = 0;
	/**
	 * Number of liquid containers that the ship is currently carrying. <br>
	 * It will be compared to the max number of liquid containers that the ship can carry in the load method.
	 */
	private int numberOfLiquidContainers = 0;
	/**
	 * Number of refrigerated containers that the ship is currently carrying. <br>
	 * It will be compared to the max number of refrigerated containers that the ship can carry in the load method.
	 */
	private int numberOfRefrigeratedContainers = 0;
	/**
	 * Total weight of containers that the ship is currently carrying. <br>
	 * It will be compared to the max weight capacity of the ship in the load method.
	 */
	private int weightOfContainers = 0;
	
	/**
	 * 
	 * Sorts Containers in the Ship by their ID. 
	 * <br>
	 * (Uses a bubble sort kind of sorting algorithm.)
	 * 
	 *  @return the sorted ArrayList of Containers
	 *  
	 */
	public ArrayList<Container> getCurrentContainers() {
		
		ArrayList<Container> sortedContainers = new ArrayList<Container>();
		sortedContainers.addAll(currentContainers);
	   
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
	 *	Sails the ship to the given port.
	 *	<br>
	 *	Gets executed if the remaining fuel of the ship is greater than or equal to the calculated required fuel.
	 *	
	 *	@param p the destination port
	 *	@return true if the sailing is successfully completed
	 *
	 */
	@Override
	public boolean sailTo(Port p) {
		
		double containerFuelConsumptionPerKM = 0;
		for (Container c : currentContainers) {
			containerFuelConsumptionPerKM += c.consumption();
		}
		
		double totalFuelConsumption = containerFuelConsumptionPerKM + this.fuelConsumptionPerKM;
		double requiredFuel = this.getCurrentPort().getDistance(p) * totalFuelConsumption;
		
		if (requiredFuel <= this.fuel) {		
			this.fuel -= requiredFuel;
			this.getCurrentPort().outgoingShip(this);
			this.setCurrentPort(p);
			this.getCurrentPort().incomingShip(this);
			return true;
		}
		
		else {
			return false;
		}
	}

	/**
	 * Refuels the ship.
	 * 
	 * @param newFuel the fuel amount that will be added to the remaining fuel of ship
	 */
	@Override
	public void reFuel(double newFuel) {
		
		this.fuel += newFuel;
		
	}
	
	/**
	 * Loads the container to the ship if the requirements are fulfilled.
	 * <br>
	 * The final weight and numbers of containers must not exceed 
	 * max capacity of weight and numbers of containers.
	 * 
	 * @param cont the container to load
	 * @return true if the container is loaded successfully
	 * <br>
	 * false otherwise
	 *
	 */
	@Override
	public boolean load(Container cont) {
		
		if (this.getCurrentPort().containers.contains(cont) == true) {
			
				if (cont instanceof RefrigeratedContainer == true) {
					if (this.weightOfContainers + cont.getWeight() <= this.totalWeightCapacity
							&& this.numberOfRefrigeratedContainers + 1 <= this.maxNumberOfRefrigeratedContainers
							&& this.numberOfHeavyContainers + 1 <= this.maxNumberOfHeavyContainers
							&& this.numberOfContainers + 1 <= this.maxNumberOfAllContainers) {
						
						this.getCurrentPort().containers.remove(cont);
						this.currentContainers.add(cont);
						this.weightOfContainers += cont.getWeight();
						this.numberOfRefrigeratedContainers += 1;
						this.numberOfHeavyContainers += 1;
						this.numberOfContainers += 1;	
						return true;
					}
					
					else {
						return false;
					}
				}
				
				else if (cont instanceof LiquidContainer == true) {
					if (this.weightOfContainers + cont.getWeight() <= this.totalWeightCapacity
							&& this.numberOfLiquidContainers + 1 <= this.maxNumberOfLiquidContainers
							&& this.numberOfHeavyContainers + 1 <= this.maxNumberOfHeavyContainers
							&& this.numberOfContainers + 1 <= this.maxNumberOfAllContainers) {
						
						this.getCurrentPort().containers.remove(cont);
						this.currentContainers.add(cont);
						this.weightOfContainers += cont.getWeight();
						this.numberOfLiquidContainers += 1;
						this.numberOfHeavyContainers += 1;
						this.numberOfContainers += 1;
						return true;
					}
					else {
						return false;
					}
				}
				
				else if (cont instanceof HeavyContainer == true) {
					if (this.weightOfContainers + cont.getWeight() <= this.totalWeightCapacity
							&& this.numberOfHeavyContainers + 1 <= this.maxNumberOfHeavyContainers
							&& this.numberOfContainers + 1 <= this.maxNumberOfAllContainers) {
						
						this.getCurrentPort().containers.remove(cont);
						this.currentContainers.add(cont);
						this.weightOfContainers += cont.getWeight();
						this.numberOfHeavyContainers += 1;
						this.numberOfContainers += 1;
						return true;
					}
					else {
						return false;
					}
				}
				
				else if (cont instanceof BasicContainer) {		// container instance of BasicContainer
					if (this.weightOfContainers + cont.getWeight() <= this.totalWeightCapacity
							&& this.numberOfContainers + 1 <= this.maxNumberOfAllContainers) {
						
						this.getCurrentPort().containers.remove(cont);
						this.currentContainers.add(cont);
						this.weightOfContainers += cont.getWeight();
						this.numberOfContainers += 1;
						return true;
					}
					else {
						return false;
					}
				}
				
				else {
					return false;
				}
			}
		
		else {
			return false;
		}
	}
	
	/**
	 * Unloads the container to the port if the given container is in the ship.
	 * 
	 * @param cont the container to unload
	 * @return true if the container is unloaded successfully
	 * <br>
	 * false otherwise
	 *
	 */
	@Override
	public boolean unLoad(Container cont) {
	
		if (this.currentContainers.contains(cont)) {
			
			this.getCurrentPort().containers.add(cont);
			this.currentContainers.remove(cont);
			this.weightOfContainers -= cont.getWeight();
			this.numberOfContainers -= 1;
			
			if (cont instanceof HeavyContainer) {
				this.numberOfHeavyContainers -= 1;
				
				if (cont instanceof RefrigeratedContainer) {
					this.numberOfRefrigeratedContainers -= 1;
				}
				
				if (cont instanceof LiquidContainer) {
					this.numberOfLiquidContainers -= 1;
				}
			}			
			return true;
		}
		
		else {
			return false;
		}
		
	}


	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	
	/**
	 * @return the fuel
	 */
	public double getFuel() {
		return fuel;
	}


	/**
	 * @param fuel the fuel to set
	 */
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}


	/**
	 * @return the currentPort
	 */
	public Port getCurrentPort() {
		return currentPort;
	}


	/**
	 * @param currentPort the currentPort to set
	 */
	public void setCurrentPort(Port currentPort) {
		this.currentPort = currentPort;
	}


	
	
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

