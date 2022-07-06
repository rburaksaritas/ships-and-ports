
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * 
 * @author Ramazan Burak Sarýtaþ
 *
 */
public class HeavyContainer extends Container {
	
	/**
	 * Constructor for the objects of HeavyContainer class.
	 * 
	 * @param ID the ID of the heavy container
	 * @param weight the weight of the container to set
	 */
	public HeavyContainer(int ID, int weight) {
		super(ID, weight);
	}

	
	@Override
	public double consumption() {
		return 3.00 * this.weight;
	}

	@Override
	public boolean equals(Container other) {
		if (this.getClass() == other.getClass() && this.ID == other.ID && this.weight == other.weight) {
			return true;
		}
		
		else {
			return false;
		}

	}
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

