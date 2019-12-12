package Ex1;

import java.util.Comparator;

//import javax.sql.rowset.spi.TransactionalWriter;
//import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real
 * number and a is an integer (summed a none negative), see:
 * https://en.wikipedia.org/wiki/Monomial The class implements function and
 * support simple operations as: construction, value at x, derivative, add and
 * multiply.
 * 
 * @author Boaz
 * 
 *         *-----this class represent:
 * 
 *         1)Monom - use the extract_coefficient and extract_power functio
 *         2)extract_coefficient - Checks whether the string is empty, whether
 *         it starts with X, whether it starts with X, and whether it appears in
 *         X. 3)extract_power - Checks whether the string is empty or contains
 *         no X, does it contain the character ^, and check if X is not the last
 *         char in the string. 6)add - if the power of the monoms are equals,
 *         add their coefficient. 8)substract - if the power of the monoms are
 *         equals, substract their coefficient. 10)multiply - multiply their
 *         coefficient and add their power. 11)equals - return if their power
 *         and their coefficient are equals. 12)toString - take a Monom and
 *         covert it to String.
 */
public class Monom implements function {
	public static final Monom ZERO = new Monom(0, 0);
	public static final Monom MINUS1 = new Monom(-1, 0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();

	public function initFromString(String s) {
		function f = new Monom(s);
		return f;
	}

	public function copy() {
		function copy = new Monom(this._coefficient, this._power);
		return copy;

	} // clone

	public boolean equals(Object obj) {
		Monom m = new Monom((Monom) obj);
		return this.equals(m);
	}

	public static Comparator<Monom> getComp() {
		return _Comp;
	}

	public Monom(double a, int b) {
		this.set_coefficient(a);
		this.set_power(b);
	}

	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}

	public int get_power() {
		return this._power;
	}

	/**
	 * this method returns the derivative monom of this.
	 * 
	 * @return
	 */
	public Monom derivative() {
		if (this.get_power() == 0) {
			this.set_coefficient(0);
			return this;
		}
		this.set_coefficient(this.get_coefficient() * this.get_power());
		this.set_power(this.get_power() - 1);
		return this;
	}

	public double f(double x) {
		double ans = 0;
		double p = this.get_power();
		ans = this.get_coefficient() * Math.pow(x, p);
		return ans;
	}

	public boolean isZero() {
		return Math.abs(this.get_coefficient()) <= EPSILON;
	}

	public Monom(String s) {
		s = s.replace(" ", "");
		double extractedCoefficient = this.extract_coefficient(s);
		int extractedPower = this.extract_power(s);
		if (extractedCoefficient != 0) {
			this.set_coefficient(extractedCoefficient);
			this.set_power(extractedPower);
		}

	}

	private double extract_coefficient(String s) {
		try {

			if (s.isEmpty()) {
				return 0;
			} else {
				if (s.startsWith("+")) {
					s = s.substring(1);
				}

				if (s.contains("x")) {

					if (s.startsWith("x")) {
						return 1;
					} else if (s.startsWith("-x")) {
						return -1;
					} else {
						return Double.parseDouble(s.substring(0, s.indexOf("x")));
					}

				}
			}

			return Double.parseDouble(s);

		} catch (Exception e) {
			throw new InvalidInputException(s);
		}
	}

	private int extract_power(String s) {

		try {

			int result = 0;

			if (s.isEmpty() || !s.contains("x")) {
				result = 0;
			} else {

				if (s.contains("^")) {
					result = Integer.parseInt(s.substring(s.indexOf("^") + 1, s.length()));
				} else {
					if (s.indexOf("x") != s.length() - 1)
						throw new InvalidInputException(s);
					result = 1;
				}
			}

			if (result < 0) {
				throw new Exception();
			}

			return result;

		} catch (Exception e) {
			throw new InvalidInputException(s);
		}
	}

	public void add(Monom m) {
		if (this.get_power() == m.get_power()) {
			this.set_coefficient(m.get_coefficient() + this.get_coefficient());
		} else {
			throw new InvalidInputException(m.toString());
		}
	}

	public void substract(Monom m) {
		if (this.get_power() == m.get_power()) {
			this.set_coefficient(this.get_coefficient() - m.get_coefficient());
		}
	}

	public void multipy(Monom d) {
		this.set_power(this.get_power() + d.get_power());
		this.set_coefficient(this.get_coefficient() * d.get_coefficient());
	}

	public boolean equals(Monom m) {
		return this.get_coefficient() == m.get_coefficient() && this.get_power() == m.get_power();
	}

	public String toString() {
		String ans = "";

		if (this.get_coefficient() == 0) {
			ans += "0";
		} else if (this.get_power() == 0) {
			ans += this.get_coefficient();
		} else if (this.get_power() == 1) {
			ans += this.get_coefficient() + "x";
		} else {
			ans += this.get_coefficient() + "x^" + this.get_power();
			return ans;
		}

		return ans;
	}
	// ****************** Private Methods and Data *****************

	private void set_coefficient(double a) {
		this._coefficient = a;
	}

	private void set_power(int p) {
		if (p < 0) {
			throw new RuntimeException("ERR the power of Monom should not be negative, got: " + p);
		}
		this._power = p;
	}

	private static Monom getNewZeroMonom() {
		return new Monom(ZERO);
	}

	private double _coefficient;
	private int _power;

}
