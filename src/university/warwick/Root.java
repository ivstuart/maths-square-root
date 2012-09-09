package university.warwick;

import java.util.* ; // required for SringTokenizer

/**
 * Program that calculates the square root of a number
 * @author Ivan Stuart 05/10/2000
 * @version 1.0
 */
final public class Root {

/**
 * Main method
 * @param args takes two command line inputs
 */
public static void main(String args[]){

	if (2 != args.length ) {
		System.out.println("Two command line arguements only!");
		System.exit(0);
		}
		
	if (args[0].charAt(0) == '-' ) {
		System.out.println("Negative values not allowed!");
		System.exit(0);
		}
		
	int places = Integer.parseInt(args[1]);

	// Create a tokenizer to format command line input
	StringTokenizer numberToken = new StringTokenizer(args[0],".");
	String intNumber[] = new String[numberToken.countTokens()];

	int charLength = 0;

	// Store integer digits and decimal seperately
	for (int i=0;i<= numberToken.countTokens();i++) {
		intNumber[i] = numberToken.nextToken();
		charLength = charLength + intNumber[i].length() ;
		}
		
	// if there is a decimal point then a valid input 
	// will have one more character only
	charLength = charLength + intNumber.length - 1 ;
	
	// Check number of decimal points in first arg
	if (2 < intNumber.length || args[0].length() != charLength) {
		System.out.println("Too many decimal points!");
		System.exit(0);
		}	
		
	// Create array to store pairs
	int pairs[] = new int[args[0].length()];
	int count = 0;
	
	// If even number of pairs to right of decimal point 
	if (0 == intNumber[0].length() % 2) {
		for (int i=0;i<intNumber[0].length();)
			pairs[count++] = Integer.parseInt(intNumber[0].substring(i++,++i));		
		}
		
	// If odd number of pairs to right of decimal point 
	else {
		pairs[count++] = Integer.parseInt(intNumber[0].substring(0,1));
		for (int i=1;i<intNumber[0].length();) 
			pairs[count++] = Integer.parseInt(intNumber[0].substring(i++,++i));
		}
	
	// Used to locate the decimal point
	int numberOfIntegerPairs = count ;	
	
	// Only execture this part if there are some pairs right of decimal point
	if (2 == intNumber.length ) {
		// Add a zero digit to odd length of decimal digits to prevent overflow
		if (1 == intNumber[1].length() % 2)
			intNumber[1] = new String(intNumber[1]+"0");

		// Pair off the decimal point digits
		for (int i=0;i<intNumber[1].length();) 
			pairs[count++] = Integer.parseInt(intNumber[1].substring(i++,++i));
		} 
		
	int numberOfPairs = count ;
	int decimalPoint = (intNumber[0].length()-1)/2;

	///// Recursive Algorithm /////
		
	BigNumber u = new BigNumber();
	BigNumber v = new BigNumber();
	BigNumber s = new BigNumber();
	BigNumber temp = new BigNumber();
	
	int digit = 0 ; 
	int countChar = 0;
	
	// Type int defined earlier
	count = 0;
	
	while (count<places) {
	
		// u = u + nextPair
		if (count < numberOfPairs) 
			u.add(pairs[count]);
	
		// s = 10 * (s + digit)
		s.add(digit);
		s.shift(1);
	
		// Find digit such that digit*(digit+s) <= u 
		digit = 0 ;			
		while (digit < 9){
			digit++;
			
			// temp = digit+s
			temp = new BigNumber(s);//
			temp.add(digit);
			
			// calculate digit * temp
			temp.multiply(digit);
			
			// check to see if digit*(digit+s) > u
			if (temp.greaterThan(u)) {
				--digit;
				break;
				}
			}
			 
		System.out.print(digit);
		countChar++;

		// Display decimal point
		if (numberOfIntegerPairs-1 == count) {
			countChar++;
			System.out.print(".");
			}
			
		// New line every 60 characters displayed
		if (0 == countChar % 60) {
			System.out.flush();
			System.out.println("");
			}

		// s = s + digit
		s.add(digit);

		// v = d * (d + s)
		v = new BigNumber(s) ;
		v.multiply(digit);
				
		// u = 100*(u-v)
		u.subtract(v);
		u.shift(2);
		
		// Increase count
		count++;
			
		} // loop until all digits found
	
	// if decimal point not displayed add zeros to show place value
	if (numberOfIntegerPairs > count) {
		for (int i=count;i<numberOfIntegerPairs;i++) 
			System.out.print("0");
		}
	System.out.println("");
	System.out.flush();
	}		
}