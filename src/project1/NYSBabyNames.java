package project1;
import java.io.*;
import java.util.*;
import java.math.*;
import project1.*;
/**
 * @author Zhenghan Zhang
 */
public class NYSBabyNames{
	
	/** 
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may contain commas)
	 * @author Joanna Klukowska
	 * @param textLine	a line of text to be passed
	 * @return an Arraylist object containing all individual entries found on that line
	 */
    private static ArrayList<String> splitCSVLine(String textLine) {
	
		ArrayList<String> entries = new ArrayList<String>(); 
		int lineLength = textLine.length(); 
		StringBuffer nextWord = new StringBuffer(); 
		char nextChar; 
		boolean insideQuotes = false; 
		boolean insideEntry= false;
		
		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			
			nextChar = textLine.charAt(i);
			
			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') {
					
				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					
					insideQuotes = false; 
					insideEntry = false;
					
				} else {
					
					insideQuotes = true; 
					insideEntry = true;
					
				}
				
			} else if (Character.isWhitespace(nextChar)) {
				
				if ( insideQuotes || insideEntry ) {
					
				    // add it to the current entry 
					nextWord.append( nextChar );
					
				} else { 
					
					// skip all spaces between entries
					continue; 
					
				}
				
			} else if ( nextChar == ',') {
				
				if (insideQuotes){ // comma inside an entry
					nextWord.append(nextChar);
					
				} else { 
					
					// end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
					
				}
				
			} else {
				
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
				
			} 
			
		}
		
		// add the last word ( assuming not empty ) 
		// trim the white space before adding to the list 
		if (!nextWord.toString().equals("")) {
			
			entries.add(nextWord.toString().trim());
			
		}
	
		return entries;
	}

	/**
	 * The main() method of this program. 
	 * @param args array of Strings provided on the command line when the program is started; 
	 * the first string should be the name of the input file containing the csv file of names. 
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//verify that the command line argument exists 
		if (args.length == 0 ) {
			System.err.println("Usage Error: the program expects file name as an argument.\n");
			System.exit(1);
		}
		//verify that command line argument contains a name of an existing file 
		File babyNamesFile = new File(args[0]); 
		if (!babyNamesFile.exists()){
			System.err.println("Error: the file "+babyNamesFile.getAbsolutePath()+" does not exist.\n");
			System.exit(1);
		}
		if (!babyNamesFile.canRead()){
			System.err.println("Error: the file "+babyNamesFile.getAbsolutePath()+
											" cannot be opened.\n");
			System.exit(1);
		}	
		//open the file for reading 
		Scanner inNames = null; 		
		try {
			inNames = new Scanner (babyNamesFile) ;
		} catch (FileNotFoundException e) {
			System.err.println("Error: the file "+babyNamesFile.getAbsolutePath()+
											" cannot be opened.\n");
			System.exit(1);
		}
		
		
		
		//read the content of the file and save the data in a list of names

		ArrayList<YearNames> years = new ArrayList<YearNames>();
		for (int i=0; i<=118; i++) {
			years.add(new YearNames(1900+i));
		}
		int min_year = 99999;
		int max_year = 0;
		while (inNames.hasNextLine()) {
			ArrayList<String> temp = null;
			try {
				String tokens = inNames.nextLine();
//				System.out.println(tokens);
				temp = splitCSVLine(tokens);
			}
			catch (NoSuchElementException ex ) {
				//caused by an incomplete or miss-formatted line in the input file
				continue; 	
			}
			try {
				Name temp_name = new Name(temp.get(1),temp.get(3),(int)Integer.parseInt(temp.get(4)));
				//System.out.println(temp_name);
				int temp_year = Integer.parseInt(temp.get(0)) - 1900;
				min_year = Math.min(min_year, temp_year);
				max_year = Math.max(max_year, temp_year);
				years.get(temp_year).add(temp_name);
			}
			catch (IllegalArgumentException ex ) {
				//ignore this exception and skip to the next line 
			}
			catch (IndexOutOfBoundsException ex1) {
				//Ignore
			}
		}
		
		

		//interactive mode: 
		
		Scanner userInput  = new Scanner (System.in ,"utf-8"); 
		String userValue = "";

		do {
			System.out.println("Enter a name or \"q\" to stop:" );
			
			//get value of from the user 
			userValue = userInput.nextLine();
			if (!userValue.equalsIgnoreCase("q")) {
				int m = 0;
				for (int i= min_year; i <= max_year; i++) {
					if (years.get(i).getCountByName(userValue) != 0) {
						m += 1;
					}
				}
				if (m == 0) {
					System.out.print("No such name in dataset.\n");
				}else {	
					for (int i= min_year; i <= max_year; i++) {	
					
					//print the histogram
						System.out.print(i+1900);
						System.out.print(" (");
						System.out.printf("%5.4f", (years.get(i).getFractionByName(userValue)) * 100);
						System.out.print("): ");
						for (int j = 0; j < Math.ceil((years.get(i).getFractionByName(userValue)) * 10000); j++ ) {
							System.out.print("|");
						}
					System.out.print("\n");
					}
				}			
		} 
		}
		while (!userValue.equalsIgnoreCase("q"));     		
		userInput.close();		
	}
}

