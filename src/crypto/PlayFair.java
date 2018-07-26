package crypto;
/*
 * Author: K Sreram
 * 
 * copyright (c) 2018 K Sreram. 
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class PlayFair extends CryptoBase {

	
	private static final Integer openBracket  = -1;
	private static final Integer closeBracket = -2;
	private static final Integer terminalSymbol = -3;
	
	private class MatrixPos {
		
		private Integer x, y;
		
		public MatrixPos (MatrixPos matrixPos) {
			x= matrixPos.getX();
			y= matrixPos.getY();
		}
		
		public MatrixPos (int px, int py) {
			x = px; y = py;
		}
		
		public void setX (int px) {
			x = px;
		}
		
		public void setY (int py) {
			y = py;
		}
		
		public Integer getX () {
			return x;
		}
		public Integer getY () {
			return y;
		}
		
	}
	
	private Set<Integer> keyValues;
	private ArrayList<Integer> key;

	private HashMap<Integer, ArrayList<Integer>> shrunkenElementList;
	private HashMap<Integer, ArrayList<Integer>> shrunkenElementReferenceed;

	private Integer dimX, dimY, numberOfShrunkenEleToAdd;
	
	private ArrayList<ArrayList<Integer>> playFairBox;
	private HashMap <Integer, MatrixPos> elementToPositionPlayFair;
	private Integer equalizerElement;
	
	
	private void resetPlayFair() {
		
		keyValues = new HashSet<Integer>();
		key       = new ArrayList<Integer>();
		
		shrunkenElementList = new HashMap<Integer, ArrayList<Integer>>();
		shrunkenElementReferenceed = new HashMap<Integer, ArrayList<Integer>>();
		elementToPositionPlayFair = new HashMap <Integer, MatrixPos>();
		
		dimX = dimY = numberOfShrunkenEleToAdd = new Integer(0);
		playFairBox = new ArrayList<ArrayList<Integer>>();
	}

	private void initialConfigure(String pKey, Integer pDimX, Integer pDimY, 
			Integer pEqualizerElement)
			throws InvalidPlayFairBoxDimensions {
		resetPlayFair();
		equalizerElement = pEqualizerElement;
		dimX = pDimX;
		dimY = pDimY;
		numberOfShrunkenEleToAdd = super.chipperTable.size() - pDimX * pDimY + 1;
		if (numberOfShrunkenEleToAdd < 0) {
			throw new InvalidPlayFairBoxDimensions();
		}
		
		ArrayList<Integer> tempKey = this.stringToInteger(pKey);
		for (int i = 0; i < tempKey.size(); i++) {
			if (!keyValues.contains(tempKey.get(i))) {
				key.add(tempKey.get(i));
			}
			keyValues.add(tempKey.get(i));
		}
	}

	public PlayFair(ArrayList<String> pChipperTable, int pDimX, int pDimY, String pKey, 
			String pEqualizerElement)
			throws InvalidPlayFairBoxDimensions {
		super(pChipperTable);
		initialConfigure(pKey, pDimX, pDimY, stringToUnitInteger(pEqualizerElement));
	}
	
	public void reconfigure (ArrayList<String> pChipperTable, 
							 int pDimX, int pDimY, 
							 String pKey, Integer pEqualizerElement) 
									 throws InvalidPlayFairBoxDimensions {
		super.reset(pChipperTable);
		initialConfigure(pKey, pDimX, pDimY, pEqualizerElement);
	}

	public Integer addShrinkList(ArrayList<String> shrinkList, int ReferenceIndex) throws InvalidReferenceIndex {

		if (ReferenceIndex > this.dimX*this.dimY) {
			throw new InvalidReferenceIndex();
		}
		
		ArrayList<Integer> listToAdd = new ArrayList<Integer>();
		
		for (int i = 0; i < shrinkList.size(); i++) {
			listToAdd.add(stringToUnitInteger(shrinkList.get(i)));
		}

		/*for (int i = 0; i < listToAdd.size(); i++) {
			if (keyValues.contains(listToAdd.get(i))) {
				listToAdd.remove(i);
			}
		}*/

		shrunkenElementReferenceed.put(listToAdd.get(ReferenceIndex), listToAdd);

		for (int i = 0; i < listToAdd.size() && numberOfShrunkenEleToAdd > 0; 
				i++, numberOfShrunkenEleToAdd--) {
			shrunkenElementList.put(listToAdd.get(i), listToAdd);
		}

		return numberOfShrunkenEleToAdd;

	}

	public boolean ConstuctPayFair() {
		if (numberOfShrunkenEleToAdd > 0) {
			return false;
		}
		Set<Integer> tempContainer = new HashSet<Integer>();
		for (int i = 0, k=0; i < dimY; i++) {
			ArrayList<Integer> temp = new ArrayList<>();
			for (int j = 0; j < dimX; j++, k++) {

				Integer tempIndex;
				
				if (k < key.size()) {
					tempIndex = key.get(k); 
				} else  {
					/// note that 'k - key.size()' represents the character as a
					/// numeral. the character-set's numbering as defined by the
					/// chiperTable.
					tempIndex = k - key.size();
				}
				
				/*if (k < key.size()) {
					temp.add(key.get(k));
					elementToPositionPlayFair.put(key.get(k), new MatrixPos (j, i));
					continue;
				} else {*/
					
					//Integer tempIndex = k - key.size();

					if (!tempContainer.contains(tempIndex) /* !keyValues.contains(tempIndex)*/) {
						if (shrunkenElementReferenceed.containsKey(tempIndex)) {
							temp.add(tempIndex);
							tempContainer.add(tempIndex);
							ArrayList<Integer> tempListOfAllShrinkable = shrunkenElementReferenceed.get(tempIndex);
							for (int z = 0; z < tempListOfAllShrinkable.size(); z++) {
								elementToPositionPlayFair.put(tempListOfAllShrinkable.get(z), new MatrixPos (j, i));
							}
							
							continue;
						} else if (!shrunkenElementList.containsKey(tempIndex)) {
							temp.add(tempIndex);
							tempContainer.add(tempIndex);
							elementToPositionPlayFair.put(tempIndex, new MatrixPos (j, i));
							continue;
						}
					}
					/// if no element is added, then the block cannot be left
					/// empty. Thus, k progresses while j doesn't 
					j--;
			}

			playFairBox.add(temp);

		}
		
		return true;
	}
	
	private ArrayList<ArrayList<Integer>> splitPair (ArrayList<Integer> values) {
		ArrayList <Integer> tempValues = new ArrayList<Integer> (values);
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		
		if (tempValues.size() %2 != 0) {
			tempValues.add(equalizerElement);
		}
		
		for (int i = 0; i < tempValues.size(); i+= 2) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(tempValues.get(i));
			temp.add(tempValues.get(i + 1));
			result.add(temp);
		}
		
		return result;
	}
	
    private static void xaxisSwap(MatrixPos v1, MatrixPos v2) {
        int temp;
        temp = v1.getX();
        v1.setX(v2.getX());
        v2.setX(temp);
    }
	
    public enum Direction {
    	kForward, kBackward
    }
    
    
    public ArrayList<Integer>  transformRepititionWithEqualizer(ArrayList<Integer> values) {
    	
    	ArrayList<Integer> result = new ArrayList<Integer>();
    	boolean lock = false;
    	int temp = 0;
    	for (int i = 0; i < values.size(); i++) {
    		if (!lock) {
    			temp = values.get(i);
    			result.add( temp );
    			lock = true;
    		} else  {
    			if ( values.get(i).equals( temp )) {
    				result.add(equalizerElement);
    			} else {
    				//result.add( values.get(i));
    				lock = false;
    				i--;
    			}
    		}
    	}
    	
    	return result;
    }
    
    
    public ArrayList<Integer>  inverseTransformWithEqualizer(ArrayList<Integer> values) {
    	
    	ArrayList<Integer> result = new ArrayList<Integer>();
    	boolean lock = false;
    	int temp = 0;
    	
    	for (int i = 0; i < values.size(); i++) {
    		if (!lock) {
    			temp = values.get(i);
    			result.add( temp );
    			lock = true;
    		} else if (lock) {
    			if ( values.get(i).equals( equalizerElement )) {
    				
    				//if (i == values.size() -1 ) {
    					result.add(PlayFair.openBracket); // signifies open bracket: {
    					result.add(temp);
    					result.add(equalizerElement);
    					if (i == values.size() -1) {
    						result.add(PlayFair.terminalSymbol);
    					}
    					result.add(PlayFair.closeBracket); // signifies closed bracket: }
    				//} else {
    					//result.add( temp );
    				//}
    			} else {
    				//result.add( values.get(i));
    				lock = false;
    				i--;
    			}
    		}
    	}
    	
    	result.add(PlayFair.terminalSymbol);
    	
    	return result;
    }
    
    
    private ArrayList<Integer> transform (ArrayList<Integer> values, Direction direction) {
    	
    	ArrayList<ArrayList<Integer>> pairs = splitPair (values);
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for (int i = 0; i < pairs.size(); i++) {
			MatrixPos pos0 = new MatrixPos(elementToPositionPlayFair.get(pairs.get(i).get(0)));
			MatrixPos pos1 = new MatrixPos(elementToPositionPlayFair.get(pairs.get(i).get(1)));
			
			if (pos0.getY() == pos1.getY()) {
				
				if (direction.equals(Direction.kForward)) {
					pos0.setX( (pos0.getX() + 1) % this.dimX );
					pos1.setX( (pos1.getX() + 1) % this.dimX);
				} else {
					pos0.setX( (pos0.getX() > 0 ) ? (pos0.getX() - 1) : this.dimX-1 );
					pos1.setX( (pos1.getX() > 0 ) ? (pos1.getX() - 1) : this.dimX-1 );
				}
				

			} else if (pos0.getX() == pos1.getX()) {
				
				
				if (direction.equals(Direction.kForward)) {
					pos0.setY( (pos0.getY() + 1) % this.dimY );
					pos1.setY( (pos1.getY() + 1) % this.dimY);
				} else {
					pos0.setY( (pos0.getY() > 0 ) ? (pos0.getY() - 1) : this.dimY-1 );
					pos1.setY( (pos1.getY() > 0 ) ? (pos1.getY() - 1) : this.dimY-1 );
				}
				
			} else {
				xaxisSwap(pos0, pos1);
			}
			result.add(playFairBox.get(pos0.getY()).get(pos0.getX()));
			result.add(playFairBox.get(pos1.getY()).get(pos1.getX()));
		}
		return result;
    }
   
    
    public String encrypt (String str) {
    	ArrayList<Integer> values = stringToInteger(str);
    	values = encrypt(values);
    	return integerToString(values);
    }
    
    public String decrypt (String str) {
    	ArrayList<Integer> values = stringToInteger(str);
    	values = decrypt(values);
    	return integerToStringDecrypt(values);
    }
    
	public ArrayList<Integer> encrypt(ArrayList<Integer> values) {
    	ArrayList<Integer> valuesAfterRemovingDuplicates = 
    			transformRepititionWithEqualizer (values);
		return transform(valuesAfterRemovingDuplicates, Direction.kForward);
	}

	public ArrayList<Integer> decrypt(ArrayList<Integer> values) {
		ArrayList<Integer> valuesAfterAddingDuplicates = 
				transform(values, Direction.kBackward);
		return inverseTransformWithEqualizer (valuesAfterAddingDuplicates);
	}
	
	public String integerToStringDecrypt(ArrayList<Integer> values) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < values.size(); i++) {
			
			ArrayList<Integer> shrinkableList = shrunkenElementList.get(values.get(i));
			
			if (values.get(i).equals(PlayFair.openBracket)) {
				sb.append('{');
				continue;
			} else if (values.get(i).equals(PlayFair.closeBracket)) {
				sb.append('}');
				continue;
			} else if (values.get(i).equals(PlayFair.terminalSymbol)) {
				sb.append("<end>");
				continue;
			}
			
			if (shrinkableList!=null) {
				sb.append('{');
				for (int j = 0; j < shrinkableList.size(); j++) {
					sb.append(chipperTable.get(shrinkableList.get(j)));
				}
				sb.append('}');
			} else {
				sb.append(chipperTable.get(values.get(i)));
			}

		}

		sb.append('\0');

		return sb.toString();
		
	}
	
	
	public void debugDisplayPlayfairBox (){
		for (int i = 0; i < this.dimY; i++) {
			System.out.println(super.integerToString(playFairBox.get(i)));
		}
	}
	

}
