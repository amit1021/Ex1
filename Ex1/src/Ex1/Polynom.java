package Ex1;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

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
}
