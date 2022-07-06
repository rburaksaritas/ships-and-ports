
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * 
 * @author Ramazan Burak Sarýtaþ
 *
 */
public class RefrigeratedContainer extends HeavyContainer {
	
	/**
	 * Constructor for the objects of RefrigeratedContainer class.
	 * 
	 * @param ID the ID of the refrigerated container
	 * @param weight the weight of the container to set
	 */
	public RefrigeratedContainer(int ID, int weight) {
		super(ID, weight);
	
	}

	@Override
	public double consumption() {
		return 5.00 * this.weight;
	}
	
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

