package crypto;

public class InvalidPlayFairBoxDimensions extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPlayFairBoxDimensions() {
		super("Invalid dimensions, the product of the dimensions "
				+ "must be lesser than or equal to the size of the symbol table");
	}
}