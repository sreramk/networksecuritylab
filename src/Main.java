/*
 * Author: K Sreram
 * 
 * copyright (c) 2018 K Sreram. 
 * 
 */


import java.util.ArrayList;

import java.util.Scanner;

import crypto.CaesarCipher;
import crypto.InvalidPlayFairBoxDimensions;
import crypto.InvalidReferenceIndex;
import crypto.PlayFair;

import java.util.Arrays;

public class Main {
	
	
	public static void caesarCipherTest () {
		
		Scanner scan = new Scanner(System.in).useDelimiter("\\n");
	
		
		CaesarCipher cipherInstance = new CaesarCipher();
		System.out.println(" (e)ncrypt or (d)ecrypt");

		String choice = scan.next();

		if (choice.equals("e")) {

			System.out.println("Enter the plain text: ");

			String plainText = scan.next();

			// System.out.println(plainText);

			System.out.println("Enter key");

			int key = scan.nextInt();

			String chipperText = cipherInstance.encrypt(plainText, key);

			System.out.println("Chipper :");

			System.out.println(chipperText);

		} else if (choice.equals("d")) {

			System.out.println("Enter chipper text:");
			String chipperText = scan.next();

			System.out.println("Enter key :");

			int key = scan.nextInt();

			String message = cipherInstance.decrypt(chipperText, key);
			System.out.println("Original message : ");

			System.out.println(message);

		}
	}
	
	public static void playfair () throws InvalidPlayFairBoxDimensions, InvalidReferenceIndex {
		
		Scanner scan = new Scanner(System.in).useDelimiter("\\n");
		
		ArrayList<String> chiperTable = new ArrayList<String>(Arrays.asList(
				"a", "b", "c", "d", "e", "f", "g", "h", "i",
				"j", "k", "l", "m", "n", "o", "p", "q", "r", 
				"s", "t", "u", "v", "w", "x", "y", "z", "_", "!", "."));
		
		System.out.println(" (e)ncrypt or (d)ecrypt");
		String choice = scan.next();
		
		if (choice.equals("e")) {
			System.out.println("Enter the plain text: ");

			String plainText = scan.next();

			System.out.println("Enter key");
			
			String key = scan.next();
			
			PlayFair playfairInst = new PlayFair(chiperTable,6,4, key, "x" );
			
			playfairInst.addShrinkList(new ArrayList<String>(Arrays.asList(
				"i","j", "z")), 0);
			
			playfairInst.addShrinkList(new ArrayList<String>(Arrays.asList(
					"v","_","!", ".")), 0);
			
			playfairInst.ConstuctPayFair();
			
			playfairInst.debugDisplayPlayfairBox();
			
			String chiperText = playfairInst.encrypt(plainText);
			
			System.out.println("Chiper text: ");
			System.out.println(chiperText);
			
		} else if (choice.equals("d")){
			
			System.out.println("Enter the chiper text:");
			
			String chiperText = scan.next();
			
			System.out.println("Enter the key:");
			
			String key = scan.next();
			
			PlayFair playfairInst = new PlayFair(chiperTable,6,4, key, "x" );
			
			playfairInst.addShrinkList(new ArrayList<String>(Arrays.asList(
					"i","j", "z")), 0);
			playfairInst.addShrinkList(new ArrayList<String>(Arrays.asList(
					"v","_","!", ".")), 0);
				
			playfairInst.ConstuctPayFair();
			
			
			playfairInst.debugDisplayPlayfairBox();
			
			String message = playfairInst.decrypt(chiperText);
			System.out.println("Message : " );
			System.out.println(message);
		}
		
		//PlayFair playfairInst = new PlayFair()
	}
	

	public static void main(String[] args) throws InvalidPlayFairBoxDimensions, InvalidReferenceIndex {
		Scanner scan = new Scanner(System.in).useDelimiter("\\n");
		String option;
		System.out.println("(c)aesar or (p)lay fair ?");
		option = scan.next();
		
		if (option.equals("c")) {
			caesarCipherTest();
		} else if (option.equals("p")) {
			playfair();
		}
	}

}
