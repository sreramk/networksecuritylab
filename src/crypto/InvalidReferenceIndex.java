package crypto;

public class InvalidReferenceIndex extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidReferenceIndex() {
		super("Invalid Reference index. The size of the Reference index must be less"
				+ " than the size of the symbol table. Because, only a symbol "
				+ "included in the playfair box can be used as a Reference");
	}
}