package crypto;
/*
 * Author: K Sreram
 * 
 * copyright (c) 2018 K Sreram. 
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CryptoBase {

	protected ArrayList<String> chipperTable;

	protected HashMap<String, Integer> stringToIndexMap;

	public CryptoBase(ArrayList<String> pChipperTable) {
		reset (pChipperTable);
	}
	
	public void reset (ArrayList<String> pChipperTable) {
		
		chipperTable = pChipperTable;
		stringToIndexMap = new HashMap<>();
		for (int i = 0; i < chipperTable.size(); i++) {
			stringToIndexMap.put("" + chipperTable.get(i), i);
		}

	}
	
	public static ArrayList<Integer> stringToIntegerStatic (String str, 
			HashMap<String, Integer> pstringToIndexMap) {
		ArrayList<Integer> result = new ArrayList<Integer>();

		for (int i = 0; i < str.length(); i++) {
			result.add(pstringToIndexMap.get(new String("" + str.charAt(i))));
			// result.add( (str.charAt(i) - 'a') );

		}

		return result;
	}
	
	
	public static String integerToStringStatic (ArrayList<Integer> values, 
			ArrayList<String> pchipperTable) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < values.size(); i++) {
		
			sb.append(pchipperTable.get(values.get(i)));

		}

		sb.append('\0');

		return sb.toString();
	}
	

	public ArrayList<Integer> stringToInteger(String str) {
		return stringToIntegerStatic(str, stringToIndexMap);
	}
	
	public Integer stringToUnitInteger (String str) {
		return stringToIndexMap.get(str);
	}

	public String integerToString(ArrayList<Integer> values) {
		return integerToStringStatic(values, chipperTable);
	}
	
	public String intergerToUnitString(Integer val) {
		return chipperTable.get(val);
	}

}
