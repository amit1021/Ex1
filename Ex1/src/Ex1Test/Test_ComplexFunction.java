package Ex1Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Polynom;
import Ex1.function;

class Test_ComplexFunction {

	@Test
	void test_initToString() {
		String[] ComplexFunc = { "plus(min(x,plus(4x+5,-2x)),x^4+3)", "mul(2x,min(3x^3,5x-9))",
				"max(x^2,plus(4x+5,div(x,3)))", "Comp(x^3-2+5x,5.20x+7.6x^3)" };
		String[] expected = { "plus(min(1.0x,plus(4.0x+5.0,-2.0x)),1.0x^4+3.0)", "times(2.0x,min(3.0x^3,5.0x-9.0))",
				"max(1.0x^2,plus(4.0x+5.0,divid(1.0x,3.0)))", "Comp(1.0x^3+5.0x-2.0,7.6x^3+5.2x)" };
		for (int i = 0; i < ComplexFunc.length; i++) {
			ComplexFunction cf = new ComplexFunction();
			function f = cf.initFromString(ComplexFunc[i]);
			assertEquals(f.toString().toLowerCase(), expected[i].toLowerCase());
		}

	}

	@Test
	void test_Fx() {
		String[] ComplexFunc = { "plus(min(x,plus(4x+5,-2x)),x^4+3)", "mul(2x,min(3x^3,5x-9))",
				"max(x^2,plus(4x+5,div(x,2)))", "Comp(x^3-2+5x,5.20x+7.6x^3)" };
		double ans2[] = { 21, 4.0, 14, 361298.128 };// , -2,
		double ans1[] = { 3, 28, 1, -2163.1520000000005 };
		double ans0[] = { 9.5625, -4.5, 11.75, 37592.53862500001 };

		ComplexFunction cf = new ComplexFunction();
		for (int i = 0; i < ComplexFunc.length; i++) {// x=2
			function f = cf.initFromString(ComplexFunc[i]);
			assertEquals(f.f(2.0), ans2[i]);
		}
		for (int i = 0; i < ComplexFunc.length; i++) { // x=-1
			function f = cf.initFromString(ComplexFunc[i]);
			assertEquals(f.f(-1), ans1[i]);
		}
		for (int i = 0; i < ComplexFunc.length; i++) { // x=1.5
			function f = cf.initFromString(ComplexFunc[i]);
			assertEquals(f.f(1.5), ans0[i]);
		}
	}

	@Test
	void test_toString() {
		String ComplexFunction[] = { "Plus(max(3x,7+2x^4),min(3x,6))", "div(6x-1,mul(4x,8))", "error(3,5)" };// "None(3x-2)"
		String ans[] = { "Plus(Max(3.0x,2.0x^4+7.0),Min(3.0x,6.0))", "Divid(6.0x-1.0,Times(4.0x,8.0))",
				"Error(3.0,5.0)" };// None(3x-2,)
		ComplexFunction cf = new ComplexFunction();
		for (int j = 0; j < ComplexFunction.length; j++) {
			function f = cf.initFromString(ComplexFunction[j]);
			assertEquals(f.toString(), ans[j]);
		}
	}

	@Test
	void test_copy() {
		String ComplexFunction[] = { "Plus(max(3x,7+2x^4),min(3x,6))", "div(6x-1,mul(4x,8))", "error(3x,5+3x-6x^2)" };
		ComplexFunction cf = new ComplexFunction();
		function clone = new ComplexFunction();
		for (int j = 0; j < ComplexFunction.length; j++) {
			function f = cf.initFromString(ComplexFunction[j]);
			clone = f.copy();
			assertEquals(clone.toString(), f.toString());
		}
	}

	@Test
	void test_equals() {
		String ComplexFunction[] = { "plus(min(x,plus(4x+5,-2x)),x^4+3)", "mul(2x,min(3x^3,5x-9))",
				"max(x^2,plus(4x+5,div(x,2)))", "Comp(x^3-2+5x,5.20x+7.6x^3)" };
		String ComplexEqual[] = { "plus(min(1x,plus(4x^1+5-0,-2x)),x^4+3)", "mul(2x-0,min(3x^3+0,5x-10+1))",
				"max(1x^2,plus(4x+5-1+1,div(x^1,2x^0)))", "Comp(x^3-2+5x,5.20x+7.6x^3)" };
		ComplexFunction cf = new ComplexFunction();
		ComplexFunction Equal = new ComplexFunction();
		for (int j = 0; j < ComplexFunction.length; j++) {
			function f = cf.initFromString(ComplexFunction[j]);
			function f1 = Equal.initFromString(ComplexEqual[j]);
			assertTrue(f.equals(f1));
		}
	}

	@Test
	void test_plus() {
		String ComplexFunction[] = { "plus(min(x,plus(4x+5,-2x)),x^4+3)", "mul(2x,min(3x^3,5x-9))",
				"max(x^2,plus(4x+5,div(x,2)))", "Comp(x^3-2+5x,5.20x+7.6x^3)" };
		String ComplexEqual[] = { "plus(times(3x+5,2x^4+5x^2),plus(min(x,plus(4x+5,-2x)),x^4+3))",
				"Plus(times(3x+5,2x^4+5x^2),mul(2x-0,min(3x^3+0,5x-10+1)))",
				"plus(times(3x+5,2x^4+5x^2),max(x^2,plus(4x+5,div(x,2))))",
				"plus(times(3x+5,2x^4+5x^2),Comp(x^3-2+5x,5.20x+7.6x^3))" };
		ComplexFunction cf = new ComplexFunction();
		ComplexFunction ans = new ComplexFunction();
		function p = new Polynom("3x+5");
		function p1 = new Polynom("2x^4+5x^2");
		for (int j = 0; j < ComplexFunction.length; j++) {
			function f1 = cf.initFromString(ComplexFunction[j]);
			ComplexFunction f = new ComplexFunction("Mul", p, p1);
			function f2 = ans.initFromString(ComplexEqual[j]);
			f.plus(f1);
			assertTrue(f.equals(f2));
		}

	}

	@Test
	void test_mul() {
		String ComplexFunction[] = { "plus(min(x,plus(4x+5,-2x)),x^4+3)", "mul(2x,min(3x^3,5x-9))",
				"max(x^2,plus(4x+5,div(x,2)))", "Comp(x^3-2+5x,5.20x+7.6x^3)" };
		String ComplexEqual[] = { "times(times(3x+5,2x^4+5x^2),plus(min(x,plus(4x+5,-2x)),x^4+3))",
				"times(times(3x+5,2x^4+5x^2),mul(2x-0,min(3x^3+0,5x-10+1)))",
				"times(times(3x+5,2x^4+5x^2),max(x^2,plus(4x+5,div(x,2))))",
				"times(times(3x+5,2x^4+5x^2),Comp(x^3-2+5x,5.20x+7.6x^3))" };
		ComplexFunction cf = new ComplexFunction();
		ComplexFunction ans = new ComplexFunction();
		function p = new Polynom("3x+5");
		function p1 = new Polynom("2x^4+5x^2");
		for (int j = 0; j < ComplexFunction.length; j++) {
			function f1 = cf.initFromString(ComplexFunction[j]);
			ComplexFunction f = new ComplexFunction("Mul", p, p1);
			function f2 = ans.initFromString(ComplexEqual[j]);
			f.mul(f1);
			assertTrue(f.equals(f2));
		}

	}

	@Test
	void test_div() {
		String ComplexFunction[] = { "plus(min(x,plus(4x+5,-2x)),x^4+3)", "mul(2x,min(3x^3,5x-9))",
				"max(x^2,plus(4x+5,div(x,2)))", "Comp(x^3-2+5x,5.20x+7.6x^3)" };
		String ComplexEqual[] = { "divid(times(3x+5,2x^4+5x^2),plus(min(x,plus(4x+5,-2x)),x^4+3))",
				"divid(times(3x+5,2x^4+5x^2),mul(2x-0,min(3x^3+0,5x-10+1)))",
				"divid(times(3x+5,2x^4+5x^2),max(x^2,plus(4x+5,div(x,2))))",
				"divid(times(3x+5,2x^4+5x^2),Comp(x^3-2+5x,5.20x+7.6x^3))" };
		ComplexFunction cf = new ComplexFunction();
		ComplexFunction ans = new ComplexFunction();
		function p = new Polynom("3x+5");
		function p1 = new Polynom("2x^4+5x^2");
		for (int j = 0; j < ComplexFunction.length; j++) {
			function f1 = cf.initFromString(ComplexFunction[j]);
			ComplexFunction f = new ComplexFunction("Mul", p, p1);
			function f2 = ans.initFromString(ComplexEqual[j]);
			f.div(f1);
			assertEquals(f.toString(), f2.toString());
		}
	}

	@Test
	void test_max() {
		String ComplexFunction[] = { "plus(min(x,plus(4x+5,-2x)),x^4+3)", "mul(2x,min(3x^3,5x-9))",
				"max(x^2,plus(4x+5,div(x,2)))", "Comp(x^3-2+5x,5.20x+7.6x^3)" };
		String ComplexEqual[] = { "max(times(3x+5,2x^4+5x^2),plus(min(x,plus(4x+5,-2x)),x^4+3))",
				"max(times(3x+5,2x^4+5x^2),mul(2x-0,min(3x^3+0,5x-10+1)))",
				"max(times(3x+5,2x^4+5x^2),max(x^2,plus(4x+5,div(x,2))))",
				"max(times(3x+5,2x^4+5x^2),Comp(x^3-2+5x,5.20x+7.6x^3))" };
		ComplexFunction cf = new ComplexFunction();
		ComplexFunction ans = new ComplexFunction();
		function p = new Polynom("3x+5");
		function p1 = new Polynom("2x^4+5x^2");
		for (int j = 0; j < ComplexFunction.length; j++) {
			function f1 = cf.initFromString(ComplexFunction[j]);
			ComplexFunction f = new ComplexFunction("Mul", p, p1);
			function f2 = ans.initFromString(ComplexEqual[j]);
			f.max(f1);
			assertEquals(f.toString(), f2.toString());
		}
	}

	@Test
	void test_min() {
		String ComplexFunction[] = { "plus(min(x,plus(4x+5,-2x)),x^4+3)", "mul(2x,min(3x^3,5x-9))",
				"max(x^2,plus(4x+5,div(x,2)))", "Comp(x^3-2+5x,5.20x+7.6x^3)" };
		String ComplexEqual[] = { "min(times(3x+5,2x^4+5x^2),plus(min(x,plus(4x+5,-2x)),x^4+3))",
				"min(times(3x+5,2x^4+5x^2),mul(2x-0,min(3x^3+0,5x-10+1)))",
				"min(times(3x+5,2x^4+5x^2),max(x^2,plus(4x+5,div(x,2))))",
				"min(times(3x+5,2x^4+5x^2),Comp(x^3-2+5x,5.20x+7.6x^3))" };
		ComplexFunction cf = new ComplexFunction();
		ComplexFunction ans = new ComplexFunction();
		function p = new Polynom("3x+5");
		function p1 = new Polynom("2x^4+5x^2");
		for (int j = 0; j < ComplexFunction.length; j++) {
			function f1 = cf.initFromString(ComplexFunction[j]);
			ComplexFunction f = new ComplexFunction("Mul", p, p1);
			function f2 = ans.initFromString(ComplexEqual[j]);
			f.min(f1);
			assertEquals(f.toString(), f2.toString());
		}
	}

	@Test
	void test_comp() {
		String ComplexFunction[] = { "plus(min(x,plus(4x+5,-2x)),x^4+3)", "mul(2x,min(3x^3,5x-9))",
				"max(x^2,plus(4x+5,div(x,2)))", "Comp(x^3-2+5x,5.20x+7.6x^3)" };
		String ComplexEqual[] = { "comp(times(3x+5,2x^4+5x^2),plus(min(x,plus(4x+5,-2x)),x^4+3))",
				"comp(times(3x+5,2x^4+5x^2),mul(2x-0,min(3x^3+0,5x-10+1)))",
				"comp(times(3x+5,2x^4+5x^2),max(x^2,plus(4x+5,div(x,2))))",
				"comp(times(3x+5,2x^4+5x^2),Comp(x^3-2+5x,5.20x+7.6x^3))" };
		ComplexFunction cf = new ComplexFunction();
		ComplexFunction ans = new ComplexFunction();
		function p = new Polynom("3x+5");
		function p1 = new Polynom("2x^4+5x^2");
		for (int j = 0; j < ComplexFunction.length; j++) {
			function f1 = cf.initFromString(ComplexFunction[j]);
			ComplexFunction f = new ComplexFunction("Mul", p, p1);
			function f2 = ans.initFromString(ComplexEqual[j]);
			f.comp(f1);
			assertEquals(f.toString(), f2.toString());
		}
	}

}
