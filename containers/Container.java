
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * 
 * @author Ramazan Burak Sarýtaþ
 *
 */
public abstract class Container {
	
	/**
	 * ID of the container.
	 */
	protected final int ID;
	/**
	 * Weight of the container.
	 */
	protected final int weight;
	
	/**
	 * Constructor for the abstract class Container. 
	 * <br>
	 * Basic properties that all containers must have.
	 * 
	 * @param ID the ID of container
	 * @param weight the weight of the container to set
	 */
	public Container(int ID, int weight) {
		
		this.ID = ID;
		this.weight = weight;
	}
	
	/**
	 * Calculates the fuel consumption of the container per KM.
	 * @return fuel consumption of the container per KM
	 */
	public abstract double consumption();
	
	/**
	 * Checks if the two containers are the same container.
	 * <br>
	 * Checks their type, ID, weight.
	 * 
	 * @param other the container to compare
	 * @return true or false depending on the result
	 */
	public abstract boolean equals(Container other);

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

