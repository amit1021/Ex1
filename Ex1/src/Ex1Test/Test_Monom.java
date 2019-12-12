package Ex1Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.InvalidInputException;
import Ex1.Monom;
import Ex1.function;

class Test_Monom {

	@Test
	void test_fx() {
		String[] Monom = { "4x^7", "-x^3", "2x^3", "5x^0", "4" };
		double[] expected = { 8748, -27, 54, 5, 4 };
		for (int i = 0; i < Monom.length; i++) {
			Monom m = new Monom(Monom[i]);
			double result = m.f(3);
			assertEquals(expected[i], result);
		}
	}

	@Test
	void test_derivative() {
		String[] Monom = { "12x^7", "-2x^1", "2x^3", "-5x^2", "0" };
		String[] expected = { "84x^6", "-2x^0", "6x^2", "-10x^1", "0" };
		for (int i = 0; i < Monom.length; i++) {
			Monom m = new Monom(Monom[i]);
			m.derivative();
			Monom expect = new Monom(expected[i]);
			assertEquals(expect.toString(), m.toString());
		}
	}

	@Test
	void test_Create_Monom_initFromString() {
		String[] Monom = { "3x^2", "-1.5x^3", "-5x^2", "0" };
		String[] expected = { "3.0x^2", "-1.5x^3", "-5.0x^2", "0" };
		for (int i = 0; i < Monom.length; i++) {
			Monom m = new Monom(Monom[i]);
			Monom expect = new Monom(expected[i]);
			Monom m2 = new Monom("x");
			function f = m2.initFromString(Monom[i]);
			assertTrue(m.equals(expect));
			assertTrue(m.equals(f));
		}
	}

	@Test
	void test_substract() {
		String[] Monom = { "3x^2", "-1.5x^3", "-5x^2", "0" };
		String[] expected = { "x^2", "0", "x^2", "0" };
		String[] substract = { "2x^2", "-1.5x^3", "-6x^2", "-x" };
		for (int i = 0; i < Monom.length; i++) {
			Monom m = new Monom(Monom[i]);
			Monom sub = new Monom(substract[i]);
			Monom expect = new Monom(expected[i]);
			m.substract(sub);
			assertEquals(m.toString(), expect.toString());
		}
	}

	@Test
	void test_multiply() {
		String[] Monom = { "3x^2", "-1.5x^3", "-5x^2", "0" };
		String[] expected = { "6x^8", "-3x^9", "-10x^8", "0" };
		for (int i = 0; i < Monom.length; i++) {
			Monom m = new Monom(Monom[i]);
			Monom expect = new Monom(expected[i]);
			Monom mul = new Monom(2, 6);
			m.multipy(mul);
			assertEquals(m.toString(), expect.toString());
		}
	}

	@Test
	void test_add() {
		String[] Monom = { "3x^2", "-1.5x^3", "-5x^2", "0" };
		String[] expected = { "4x^2", "-1.5x^3", "-4x^2", "0" };
		for (int i = 0; i < Monom.length; i++) {
			Monom m = new Monom(Monom[i]);
			Monom expect = new Monom(expected[i]);
			Monom add = new Monom(1, 2);
			if (Monom[i] == "-1.5x^3" || Monom[i] == "0") {
				assertThrows(InvalidInputException.class, () -> m.add(add));
			} else {
				m.add(add);
				assertTrue(m.equals(expect));
			}
		}
	}

	@Test
	void test_equals_copy() {
		String[] monoms1 = { "2", "-x", "-3.2x^2", "4", "-1.5x^2" };
		String[] monoms2 = { "5", "1.7x", "3.2x^2", "-3", "1.5x^2" };
		String[] monoms3 = { "2x^0", "-1x", "-3.2x^2", "4", "-1.5x^2" };
		function [] function = new Monom[5];
		for (int i = 0; i < monoms1.length; i++) {
			Monom m1 = new Monom(monoms1[i]);
			Monom m2 = new Monom(monoms2[i]);
			Monom m3 = new Monom(monoms3[i]);
			function[i] = m1.copy();

			assertFalse(m1.equals(m2));
			assertTrue(m1.equals(m3));
			assertTrue(m1.equals(function[i]));
		}
	}
}
