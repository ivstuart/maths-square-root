package university.warwick;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

public class SquareRootAlgorithmTest {

	@Test
	public void testOdd() {
		SquareRootAlgorithm sqrt = new SquareRootAlgorithm();

		assertTrue(sqrt.isOdd(1));
	}

	@Test
	public void testEven() {
		SquareRootAlgorithm sqrt = new SquareRootAlgorithm();

		assertFalse(sqrt.isOdd(10));
	}

	@Test
	public void testOddPair() {
		SquareRootAlgorithm sqrt = new SquareRootAlgorithm();

		List<Integer> list = sqrt.splitValueIntoPairs("123");

		System.out.println(list);

		assertEquals(Integer.valueOf(1), list.get(0));
		assertEquals(Integer.valueOf(23), list.get(1));
	}

	@Test
	public void testEvenPair() {
		SquareRootAlgorithm sqrt = new SquareRootAlgorithm();

		List<Integer> list = sqrt.splitValueIntoPairs("12");

		System.out.println(list);

		assertEquals(Integer.valueOf(12), list.get(0));

	}

	@Test
	public void testDecimalEvenPair() {
		SquareRootAlgorithm sqrt = new SquareRootAlgorithm();

		List<Integer> list = sqrt.splitValueIntoPairs("0.12");

		System.out.println(list);

		assertEquals(Integer.valueOf(0), list.get(0));
		assertEquals(Integer.valueOf(12), list.get(1));

	}

	@Test
	public void testRootOfFour() {
		SquareRootAlgorithm sqrt = new SquareRootAlgorithm();

		BigDecimal result = sqrt.squareRoot(BigDecimal.valueOf(4), 10);

		assertEquals("2.0000000000", result.toPlainString());

	}

	@Test
	public void testRootOfFive() {
		testRootOfValue(5, 14);
	}

	public void testRootOfValue(double value, int places) {
		SquareRootAlgorithm sqrt = new SquareRootAlgorithm();

		BigDecimal result = sqrt.squareRoot(BigDecimal.valueOf(value), places);

		String expectedResult = String.valueOf(Math.sqrt(value)).substring(0,
				places);

		assertEquals(expectedResult, result.toPlainString()
				.substring(0, places));

	}

	@Test
	public void testRootOfEvenDecimal() {
		testRootOfValue(0.12, 17);
	}

	@Test
	public void testRootOfOddDecimal() {

		testRootOfValue(0.0003, 18);
	}

	@Test
	public void testRootOfOddDecimalTo100Places() {
		SquareRootAlgorithm sqrt = new SquareRootAlgorithm();

		int value = 3;
		int places = 100;

		BigDecimal result = sqrt.squareRoot(BigDecimal.valueOf(value), places);

		BigDecimal expected = result.multiply(result);

		String resultSquared = expected.toPlainString()
				.substring(0, places / 2);

		assertEquals("2.999999999999999999999999999999999999999999999999",
				resultSquared);
	}

	@Test
	public void testRootOf1000To1Place() {
		SquareRootAlgorithm sqrt = new SquareRootAlgorithm();

		BigDecimal result = sqrt.squareRoot(BigDecimal.valueOf(10000), 1);

		assertEquals("100", result.toPlainString());
	}

}