package Ex1Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.Polynom;
import Ex1.function;

class Test_Polynom {

	@Test
	void test_create_Polynom_initFromString_substract_isZero() {
		String[] Polynom = { "4x^4-x^0+0", "-2x-3x^1+2", "5x^7+x^0", "8x^0+7x+3x" };
		String[] expected = { "-1+4x^4", "-5x+2", "5x^7+1", "10x+8" };
		for (int i = 0; i < Polynom.length; i++) {
			Polynom p = new Polynom(Polynom[i]);
			Polynom expect = new Polynom(expected[i]);
			Polynom p1 = new Polynom();
			function f = p1.initFromString(Polynom[i]);
			assertTrue(p.equals(f));
			p.substract(expect);
			assertTrue(p.isZero());

		}
	}

	@Test
	void test_area() {
		String[] Polynom = { "4x^2-x^4+3", "-2x-3x^6+1", "x^7+1", "1+7x+3x^9" };
		double[] expect = { 4.133, 0.428, 1.125, 4.8 };
		for (int i = 0; i < Polynom.length; i++) {
			Polynom p = new Polynom(Polynom[i]);
			double res = (int) (p.area(0, 1, 0.01) * 1000);
			res /= 1000;
			assertEquals(res, expect[i]);
		}
	}

	@Test
	void test_root() {
		String[] Polynom = { "4x^2-x^4+3", "-2x-3x^3+1", "x^7+1", "1+7x+3x^9" };
		double[] expected = { -2.154, 0.402, -1, -0.143 };
		for (int i = 0; i < Polynom.length; i++) {
			Polynom p = new Polynom(Polynom[i]);
			double res = (int) (p.root(1, -4, 0.001) * 1000);
			res /= 1000;
			assertEquals(res, expected[i]);
		}
	}

	@Test
	void test_fx() {
		String[] Polynom = { "-x^3+4x+2", "2x^6-4x", "x^3+x^4-x^1" };
		double[] expected = { -13, 1446, 105 };
		for (int i = 0; i < Polynom.length; i++) {
			double point = 3;
			Polynom p = new Polynom(Polynom[i]);
			assertEquals(p.f(point), expected[i]);
		}
	}

	@Test
	void test_multiply() {
		String[] Polynom = { "-x^2+4x+1", "x^4+2x", "x^3-x^4-15x" };
		String[] expected = { "-3x^6+12x^5+3x^4-12x^3+48x^2+12x", "3x^8+18x^5+24x^2", "-3x^8+3x^7-57x^5+12x^4-180x^2" };
		Polynom mul = new Polynom("3x^4+12x");
		for (int i = 0; i < Polynom.length; i++) {
			Polynom res = new Polynom(Polynom[i]);
			Polynom expect = new Polynom(expected[i]);
			res.multiply(mul);
			res.substract(expect);
			assertTrue(res.isZero());
		}

	}

	@Test
	void test_derivative() {
		String[] Polynom = { "-x^5+2x+1", "12x^5+14x^9-x^11+2x+3x^0", "x^3-x^4-15x" };
		String[] expected = { "-5x^4+2", "-11x^10+126x^8+60x^4+2", "3x^2-4x^3-15" };
		for (int i = 0; i < Polynom.length; i++) {
			Polynom p = new Polynom(Polynom[i]);
			Polynom expect = new Polynom(expected[i]);
			p.derivative();
			p.substract(expect);
			assertTrue(p.isZero());
		}

	}

	@Test
	void test_equals_copy() {
		String[] Polynom = { "4x+5", "2x^0+4", "1+2+0.5x+0.5x" };
		String[] expected = { "2x+2x+5", "6", "x+3" };
		function p1 = new Polynom();
		for (int i = 0; i < Polynom.length; i++) {
			Polynom p = new Polynom(Polynom[i]);
			Polynom expect = new Polynom(expected[i]);
			p1 = p.copy();
			assertTrue(p.equals(expect));
			assertTrue(p.equals(p1));
		}
	}

	@Test
	void test_add() {
		String[] Polynom = { "4x+5x^1+3", "2x^0-2x+1", "x+4x^5-x^2" };
		String[] add = { "2x+14-x^3", "x^8-3", "x+3" };
		String[] expected = { "-x^3+11x+17", "x^8-2x", "4x^5-x^2+2x+3" };
		for (int i = 0; i < Polynom.length; i++) {
			Polynom p = new Polynom(Polynom[i]);
			Polynom a = new Polynom(add[i]);
			Polynom expect = new Polynom(expected[i]);
			p.add(a);
			assertTrue(p.equals(expect));
		}

	}
}