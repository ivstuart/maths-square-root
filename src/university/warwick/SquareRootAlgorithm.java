package university.warwick;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This algorithm was provided as part of programming I at Warwick University in
 * 1995. The assignment for the course was to implement in Pascal the follow
 * algorithm. My original 1995 code was functional and monolithic in places. I
 * recoded the design and algorithm for fun in 05/10/2000. However BigDecimal
 * was not available in core language, since it's introduction it make it much
 * easier to focus on abstracting the problem into small methods and avoiding
 * repeating code. This version was creating in under 3 hours for fun and as an
 * example of how far to take cleaning up code. There is a temptation to
 * optimize this code further, but often it is developers/humans that read code
 * hence code clarity in the first optimization goal.
 * 
 * I am releasing this code as open source, however if you use any of this code,
 * please email me at Ivan.Stuart@gmail.com
 * 
 * @author (c) Ivan Stuart 08/09/2012
 * @version 1.0
 */
public final class SquareRootAlgorithm {

	public static final BigInteger HUNDRED = BigInteger.valueOf(100);

	private int numberOfIntegerPairs = 0;

	/**
	 * Calculate the square root of a number to up to a large number of decimal
	 * places.
	 * 
	 * @param value
	 * @param numberOfPlaces
	 * @return root
	 */
	public BigDecimal squareRoot(final BigDecimal value,
			final int numberOfPlaces) {

		if (numberOfPlaces < 1) {
			throw new IllegalArgumentException(
					"Number of places must be greater than zero");
		}

		if (value.signum() < 1) {
			throw new IllegalArgumentException(
					"Value must be greater than zero");
		}

		List<Integer> pairs = splitValueIntoPairs(value.toPlainString());

		BigInteger u = BigInteger.ZERO;
		BigInteger v = BigInteger.ZERO;
		BigInteger s = BigInteger.ZERO;

		int digit = 0;

		StringBuilder result = new StringBuilder();

		Iterator<Integer> pairIter = pairs.iterator();

		for (int count = 0; count <= numberOfPlaces; count++) {

			// u = u + nextPair
			if (pairIter.hasNext()) {
				u = u.add(BigInteger.valueOf(pairIter.next()));
			}

			// s = 10 * (s + digit)
			s = s.add(BigInteger.valueOf(digit)).multiply(BigInteger.TEN);

			// Condition: digit*(digit+s) <= u
			digit = findDigit(s, u);

			result.append(digit);

			if (lastIntegerPair(count)) {
				result.append(".");
			}

			// s = s + digit
			s = s.add(BigInteger.valueOf(digit));

			// v = d * (d + s)
			v = s.multiply(BigInteger.valueOf(digit)); // Note that findDigit
														// calculates v, however
														// decided for clarity
														// not to optimize.

			// u = 100*(u-v)
			u = u.subtract(v).multiply(HUNDRED);

		} // loop until all digits found

		// if decimal point not displayed add zeros to show place value
		appendPlaceValueZeros(result, numberOfPlaces);

		return new BigDecimal(result.toString());
	}

	/**
	 * if decimal point not displayed add zeros to show place value
	 * 
	 * @param result
	 * @param numberOfPlaces
	 */
	public void appendPlaceValueZeros(final StringBuilder result,
			int numberOfPlaces) {

		for (; numberOfPlaces < numberOfIntegerPairs; numberOfPlaces++) {
			result.append("0");
		}

	}

	/**
	 * Used to check when to append the decimal point
	 * 
	 * @return true when decimal point required.
	 */
	public boolean lastIntegerPair(int count) {

		return (count == numberOfIntegerPairs);
	}

	/**
	 * Split the digits into pairs of digits
	 * 
	 * <pre>
	 * 
	 * 100 => 1 00 
	 * 1234 => 12 34 
	 * 5.6 => 5 60
	 * 0.0005=> 0 00 05
	 * </pre>
	 * 
	 * @param value
	 * @return list of pairs of digits
	 */
	public List<Integer> splitValueIntoPairs(final String valueString) {

		StringBuilder value = new StringBuilder(valueString);

		List<Integer> pairs = new ArrayList<Integer>(2 + value.length() / 2);

		int indexOfDecimalPoint = value.indexOf(".");

		if (indexOfDecimalPoint == -1) {

			indexOfDecimalPoint = value.length(); // 100 => 100.
		} else {

			value = value.deleteCharAt(indexOfDecimalPoint);

		}

		// Decided on object variable, because more work in returning this as
		// well as the pairs from method.
		numberOfIntegerPairs = (indexOfDecimalPoint - 1) / 2;

		// 100 => 0100
		if (isOdd(indexOfDecimalPoint)) {
			value = new StringBuilder("0").append(value);
		}

		// 1234 => 12 34
		for (int beginIndex = 0; beginIndex + 2 <= value.length(); beginIndex = beginIndex + 2) {

			String pair = value.substring(beginIndex, beginIndex + 2);

			pairs.add(Integer.parseInt(pair));
		}

		return pairs;

	}

	/**
	 * Check if value is odd
	 * 
	 * @param indexOfDecimalPoint
	 * @return true for odd and false for even
	 */
	public boolean isOdd(int indexOfDecimalPoint) {
		return (indexOfDecimalPoint % 2 == 1);
	}

	/**
	 * Find a digit from 0 to 9 which satisfies the condition digit*(digit+s) <=
	 * u
	 * 
	 * @param s
	 * @param u
	 * @return digit such that digit*(digit+s) <= u
	 */
	public int findDigit(final BigInteger s, final BigInteger u) {

		// 0 always is less than, hence skip it.
		// 10 will always exceed and provide a result of 9, hence skip it.
		for (int digit = 1; digit <= 9; digit++) {

			BigInteger lhs = s.add(BigInteger.valueOf(digit)).multiply(
					BigInteger.valueOf(digit));

			if (lhs.compareTo(u) == 1) {
				return --digit;
			}

		}
		return 9;
	}

	/**
	 * Main method
	 * 
	 * @param args
	 *            takes two command line inputs
	 */
	public static void main(String args[]) {

		if (2 != args.length) {
			displayUsage();

			System.exit(0);
		}

		SquareRootAlgorithm sqrt = new SquareRootAlgorithm();

		BigDecimal value = new BigDecimal(args[0]);

		Integer numberOfPlaces = new Integer(args[1]);

		BigDecimal result = sqrt.squareRoot(value, numberOfPlaces);

		// Assignment require formatting, a carriage return every 60 digits.
		sqrt.displayResult(result);

	}

	/**
	 * Display command line usage
	 */
	public static void displayUsage() {
		System.out.println("Two command line arguements only!");

		System.out.println("Usage:");

		System.out
				.println(" [value to square root] [number of significant figures]");
	}

	/**
	 * Format result such that there is a carriage return every 60 digits.
	 * 
	 * @param result
	 */
	public void displayResult(final BigDecimal result) {
		StringBuilder sb = new StringBuilder(result.toPlainString());

		for (int index = 0; index < sb.length(); index++) {
			System.out.print(sb.charAt(index));

			// Magic numbers are bad as they offer no description of their
			// context.
			// Make life easier for the code reviewer by using a descriptive
			// variable name
			if (index % 60 == 59) {
				System.out.println("");
			}
		}

		System.out.println("");
	}
}
