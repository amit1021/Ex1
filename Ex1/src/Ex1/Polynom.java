package Ex1;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

/**
 * This class represents a Polynom with add, multiply functionality, it also
 * should support the following: 1. Riemann's Integral:
 * https://en.wikipedia.org/wiki/Riemann_integral 2. Finding a numerical value
 * between two values (currently support root only f(x)=0). 3. Derivative
 * 
 * @author Boaz
 * 
 *         -----this class represent: 1)Polynom- use getNextMonom function and
 *         add the monom to the LinkedList and cut the string.
 *         2)organizePolynom- Initialize the array to false, run in the first
 *         loop on the array and check if arr[i] = false . If it's true, arr[i]
 *         = true. then creates a new monom equals to this.polynomList[i], enter
 *         the second loop and run from arr[j] (j = i+1), if the arr(j)= false.
 *         then creates a new monom equals to this.polynomList[j], if their
 *         power are equals, then add two monoms. continue to run in the inner
 *         loop until the end of the array ' and then add the monom to the new
 *         newPolynomList. 3)getNextMonom - Help function to create a new
 *         polynom. get a string and runs it in the loop and stops when the
 *         string[i] = +/- , returning the string until the +/-. 4) f(x) -Runing
 *         in the loop on the Polynom list, and use the function from the Monom
 *         class f(x),to calculate monom by monom in the Polynom. 5)add(Polynom
 *         able) - Create a new polynom that is equal to the polynom able,
 *         running on the polynom and each monom add to this.polynom. 6)add -
 *         Adds the monom to the polynom and call the organize function.
 *         7)substract - Multiply the monom by monom -1 and call the add
 *         function. 8)substract(Polynom able) - Create a new polynom that is
 *         equal to the polynom able, running on the polynom and each monom
 *         substract from this.polynom. 9)multiply - Running on the polynom and
 *         use the function multiply from Monom class to multiply
 *         this.polynom[i] with Monom. 10)multiply -Copy this.polynom to a new
 *         polynom. Run in the loop and create another polynom equal to the new
 *         polynom. With it multiply the monom from polynom able in the new
 *         polynom and at the end of the loop add to this.polynom all the
 *         result. 11)equals - Copy this.polynom to a new polynom. Call the
 *         subtract function and subtracts the polynom able from the new
 *         polynom. If the answer equals 0, by the range of Epsilon returns true
 *         (isZero function). 12)isZero- running on the polynom and check each
 *         monom if equals to zero(use the isZero function from Monom class).
 *         13)root - running on the polynom and check each monom if equals to
 *         zero(use the isZero function from Monom class) 13) Check whether fx0
 *         * fx1 > 0. (Because otherwise there is no point that fx equals 0).
 *         Creating a result variable that takes the average of x0 and x1 Run in
 *         the loop while the absolute value of x0-x1 is greater than Epsilon.
 *         The function returns the root after reducing the section at the range
 *         of epsilon. 14)copy - create a new polynom and running in for on
 *         this.polynom and add the monom to the new polynom. 15)derivative -
 *         Run in the loop and call the derivative function from the monom
 *         class, and run on each monom 16) area - Runners in loop from x0 to x1
 *         and each time add to i epsilon, and according to Riemann integral.
 *         17)toString - take a Polynom and covert it to String
 */
public class Polynom implements Polynom_able {
	public static final Polynom Zero = new Polynom();
	private LinkedList<Monom> polynomList = new LinkedList<Monom>();
	public static final Comparator<Monom> C = new Monom_Comperator();
	private Monom Min1 = new Monom(-1, 0);

	/**
	 * Zero (empty polynom)
	 */
	public function copy() {
		Polynom copy = new Polynom();
		for (int i = 0; i < this.polynomList.size(); i++) {
			Monom m = new Monom(this.polynomList.get(i).toString());
			copy.add(m);
		}
		function f = copy;
		return f;
	}

	public function initFromString(String s) {
		function f = new Polynom(s);
		return f;
	}

	public Polynom() {
		;
	}

	public Polynom(Polynom other) {// copy constructor
		for (int i = 0; i < other.polynomList.size(); i++) {
			this.polynomList.add(new Monom(other.polynomList.get(i)));
		}

		this.organizePolynom();
	}

	/**
	 * init a Polynom from a String such as: (3x+4^2), (8-4x^4+6x^7) for example.
	 * only +/- operators
	 * 
	 * @param s: is a string represents a Polynom
	 */

	public Polynom(String s) {
		s = s.replace(" ", "");
		while (!s.isEmpty()) {

			// get the next monom
			String nextMonom = this.getNextMonom(s);

			// add the monom to the polynom list
			polynomList.add(new Monom(nextMonom));

			// subtract this monom from the string
			s = s.substring(nextMonom.length());
		}

		this.organizePolynom();
	}

	private String getNextMonom(String s) {

		String nextMonom = "";

		for (int i = 0; i < s.length(); i++) {
			if (i >= 1 && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
				break;
			}
			nextMonom += s.charAt(i);
		}

		return nextMonom;
	}

	private void organizePolynom() {

		LinkedList<Monom> newPolynomList = new LinkedList<Monom>();

		int polynomListSize = this.polynomList.size();

		boolean[] arr = new boolean[polynomListSize];

		for (int i = 0; i < polynomListSize; i++) {

			if (!arr[i]) {

				Monom m1 = this.polynomList.get(i);
				arr[i] = true;

				for (int j = i + 1; j < polynomListSize; j++) {

					if (!arr[j]) {
						Monom m2 = this.polynomList.get(j);

						if (m1.get_power() == m2.get_power()) {
							m1.add(m2);
							arr[j] = true;
						}
					}
				}

				newPolynomList.add(m1);

			}
		}

		newPolynomList.sort(C);

		this.polynomList = newPolynomList;
	}

	@Override
	public double f(double x) {
		double result = 0;
		for (int i = 0; i < polynomList.size(); i++) {
			result += polynomList.get(i).f(x);
		}
		return result;
	}

	@Override
	public void add(Polynom_able p1) {

		Polynom cloned = new Polynom((Polynom) p1);
		Iterator<Monom> iterator = cloned.iteretor();

		while (iterator.hasNext()) {
			this.add(iterator.next());
		}
		this.organizePolynom();
	}

	@Override
	public void add(Monom m1) {
		this.polynomList.add(m1);
		this.organizePolynom();
	}

	public void substract(Monom m) {
		m.multipy(Min1);
		this.add(m);
	}

	@Override
	public void substract(Polynom_able p1) {

		Polynom cloned = new Polynom((Polynom) p1);

		Iterator<Monom> iterator = cloned.iteretor();

		while (iterator.hasNext()) {
			Monom m = new Monom(iterator.next());
			this.substract(m);
		}
	}

	@Override
	public void multiply(Monom m1) {
		for (int i = 0; i < polynomList.size(); i++) {
			polynomList.get(i).multipy(m1);
		}
	}

	@Override
	public void multiply(Polynom_able p1) {

		Polynom originalPolynom = new Polynom(this);
		// clear the current polynom list
		this.polynomList.clear();

		Iterator<Monom> iterator = p1.iteretor();

		while (iterator.hasNext()) {
			Polynom originalPolynomCloned = new Polynom(originalPolynom);
			originalPolynomCloned.multiply(iterator.next());
			this.add(originalPolynomCloned);
		}
	}

	public boolean equals(Object p1) {
		Polynom clone = new Polynom(this);
		clone.substract((Polynom) p1);
		return clone.isZero();
	}

	@Override
	public boolean isZero() {
		for (int i = 0; i < polynomList.size(); i++) {
			if (!polynomList.get(i).isZero()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public double root(double x0, double x1, double eps) {
		if (this.f(x0) * this.f(x1) > 0) {
			throw new RuntimeException("no root");
		}
		if (this.f(x0) == 0) {
			return x0;
		}
		if (this.f(x1) == 0) {
			return x1;
		}
		double result = (x0 + x1) / 2;
		while (Math.abs(x1 - x0) > eps) {
			result = (x0 + x1) / 2;
			if (Math.abs(this.f(result)) <= eps) {
				return result;
			} else if (this.f(result) * this.f(x1) < 0) {
				x0 = result;
			} else if (this.f(result) * this.f(x0) < 0) {
				x1 = result;
			}
		}
		return result;
	}

	@Override
	public Polynom_able derivative() {
		for (int i = 0; i < polynomList.size(); i++) {
			this.polynomList.get(i).derivative();
			if (polynomList.get(i).get_coefficient() == 0) {
				this.polynomList.remove(i);
			}
		}
		return this;
	}

	@Override

	public double area(double x0, double x1, double eps) {
		double sum = 0;
		for (double i = x0; i < x1; i += eps) {
			sum += ((this.f(x0) + this.f(x0 + eps)) / 2) * eps;
			x0 += eps;
		}
		return Math.abs(sum);
	}

	@Override
	public Iterator<Monom> iteretor() {
		// TODO Auto-generated method stub
		return polynomList.iterator();
	}

	public String toString() {
		String ans = "";
		for (int i = 0; i < polynomList.size(); i++) {
			if (polynomList.get(i).get_coefficient() > 0 && i >= 1) {
				ans += "+" + polynomList.get(i);
			} else if (polynomList.get(i).get_coefficient() != 0) {
				ans += polynomList.get(i);
			} else if (polynomList.get(i).get_coefficient() == 0 && i == 0) {
				ans += polynomList.get(i);
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		Polynom p = new Polynom("2x+5");
		Polynom p1 = new Polynom(p);
		Monom m = new Monom("100x^5");
		p.add(m);
		System.out.println("p: " + p);
		System.out.println("p1: " + p1);

	}
}
