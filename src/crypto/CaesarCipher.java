package crypto;


/*
 * Author: K Sreram
 * 
 * copyright (c) 2018 K Sreram. 
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;



public class CaesarCipher extends CryptoBase{
	

	public CaesarCipher(ArrayList<String> pChipperTable) {
		super (pChipperTable);
	}
	
	public CaesarCipher( ) {
		super(new ArrayList<String>(Arrays.asList(
				"a", "b", "c", "d", "e", "f", "g", "h", "i",
				"j", "k", "l", "m", "n", "o", "p", "q", "r", 
				"s", "t", "u", "v", "w", "x", "y", "z", "_", 
				"0", "1", "2", "3", "4", "5", "6", "7", "8", 
				"9", "!", "@", "#", "$", "%", "^", "&", "*", 
				"(", ")", "-", "+", "=")));
	}

	
	public String encrypt (String str, Integer key) {
		ArrayList<Integer> values  = stringToInteger(str);
		values = encrypt(values, key);
		return this.integerToString(values);
	}
	
	public String decrypt (String str, Integer key) {
		ArrayList<Integer> values = stringToInteger(str);
		values = decrypt (values, key);
		return this.integerToString(values);
	}
	
	public ArrayList<Integer> encrypt(ArrayList<Integer> values, Integer key) {
		ArrayList<Integer> result = new ArrayList<Integer>();

		for (int i = 0; i < values.size(); i++) {

			result.add((values.get(i) + key) % chipperTable.size());

		}

		return result;
	}

	
	public ArrayList<Integer> decrypt(ArrayList<Integer> values, Integer key) {
		ArrayList<Integer> result = new ArrayList<Integer>();

		for (int i = 0; i < values.size(); i++) {
			Integer valueDiff = values.get(i) - key;
			if (valueDiff < 0) {
				valueDiff = -1 * valueDiff;
				valueDiff = valueDiff % chipperTable.size();
				valueDiff = chipperTable.size() - valueDiff;
			}
			result.add(valueDiff % chipperTable.size());
		}

		return result;
	}


}