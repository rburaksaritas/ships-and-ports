
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * 
 * @author Ramazan Burak Sarýtaþ
 *
 */
public class LiquidContainer extends HeavyContainer {

	/**
	 * Constructor for the objects of LiquidContainer class.
	 * 
	 * @param ID the ID of the liquid container
	 * @param weight the weight of the container to set
	 */
	public LiquidContainer(int ID, int weight) {
		super(ID, weight);
	
	}

	@Override
	public double consumption() {
		return 4.00 * this.weight;
	}
	

}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

