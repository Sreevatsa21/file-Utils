// imports go here
import java.util.Scanner;
import java.io.PrintWriter;

/**
 *	MVCipher - Add your description here
 *	Requires Prompt and FileUtils classes.
 *	
 *	@author	Sreevatsa Pervela
 *	@since	9/21/2023
 */
public class MVCipher {
	
	// fields go here
	private String key;
	private Scanner input;	// This for the scanner to read the input file
	private PrintWriter output;
	/** Constructor */
	public MVCipher() {
		key = "";
		input = null;
		output = null;
	}
	
	public static void main(String[] args) {
		MVCipher mvc = new MVCipher();
		mvc.run();
	}
	
	/**
	 *	Method header goes here
	 * this is the main method which runs all the other mehtods. It also 
	 * prompts the user for the key, wether they want to encrypt or decrypt,
	 * the inputfile name, and for the output file name.
	 */
	public void run() {
		System.out.println("\n Welcome to the MV Cipher machine!\n");
		
		/* Prompt for a key and change to uppercase
		   Do not let the key contain anything but alpha
		   Use the Prompt class to get user input */
		
		do {
			key = Prompt.getString("Please input a word to use as key (letters only) ->");
			
			key = key.toUpperCase();
		}while (key.length() > 2 );
		
		/* Prompt for encrypt or decrypt */
		int get = 0;
		get = Prompt.getInt("Encrypt or decrypt? (1, 2) -> ", 0, 3);
		boolean isEncrypt = false;
		if (get == 1) {
			isEncrypt = true;
		}
		else {
			isEncrypt = false;
		}
			
			
		/* Prompt for an input file name */
		Stirng inputFile = "";
		inputFile = Prompt.getString("Name of file to encrypt ->");
		input = FileUtils.openToRead(input);
		/* Prompt for an output file name */
		Stirng outputFile = "";
		outputFile = Prompt.getString("Name of output file ->");
		output = FileUtils.openToWrite(outputFile);
		/* Read input file, encrypt or decrypt, and print to output file */
		read(isEncrypt, inputFile);
		
		/* Don't forget to close your output file */
	}
	
	// other methods go here
	/**
	 * This method reads the input file and decrypts or encrypts based of of the 
	 * boolean passed in to decide wether the file needs to be encrypted or
	 * decrypted
	 * 
	 * pre Conditions : boolean  and input file passed in
	 * @param isTrue	booleam which decides wether encryption or decryption is needed
	 * @param inFile	Input file
	 */
	public void read(boolean isTrue, String inFile) {
		if (isTrue == true) {
			
		}
	}
}
