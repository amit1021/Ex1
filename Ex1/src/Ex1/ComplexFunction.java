package Ex1;

public class ComplexFunction implements complex_function {
	private Operation op;
	private function left, right;

	public ComplexFunction(Operation op, function left, function right) {
		if (left == null) {
			throw new InvalidInputException("undefind function");
		} // function left cannot be null
		this.left = left.copy();
		if (op == Operation.None || right == null) {// if operation is None <---> function right is null
			if (right != null) {
				throw new InvalidInputException("undefind function");
			}
			if (op != Operation.None) {
				throw new InvalidInputException("undefind function");
			}
		}
		this.op = op;
		if (right != null) {
			this.right = right.copy();
		} else {
			this.right = null;
		}
	}

	public ComplexFunction(String op, function left, function right) {

		if (left == null) {
			throw new InvalidInputException("Undefind function");
		} // function left cannot be null
		this.left = left.copy();
		if (op.toLowerCase().equals("none") || right == null) {// if operation is None <---> function right is null
			if (right != null) {
				throw new InvalidInputException("undefind function");
			}
			if (!op.toLowerCase().equals("none")) {
				throw new InvalidInputException("undefind function");
			}
		}
		if (right != null) {
			this.right = right.copy();
		} else {
			this.right = null;
		}
		String opString = op.toLowerCase();
		switch (opString) {
		case "plus":
			this.op = Operation.Plus;
			break;
		case "mul":
			this.op = Operation.Times;
			break;
		case "times":
			this.op = Operation.Times;
			break;
		case "div":
			this.op = Operation.Divid;
			break;
		case "divid":
			this.op = Operation.Divid;
			break;
		case "min":
			this.op = Operation.Min;
			break;
		case "max":
			this.op = Operation.Max;
			break;
		case "comp":
			this.op = Operation.Comp;
			break;
		case "none":
			this.op = Operation.None;
			break;
		case "error":
			this.op = Operation.Error;
			break;
		default:
			System.out.println("Invalid input");
			break;
		}
	}

	public ComplexFunction(function left) {
		this.op = Operation.None;
		this.left = left.copy();
		this.right = null;
	}

	public ComplexFunction() {// empty constructor
		this.left = new Polynom();
		this.right = null;
		this.op = Operation.None;
	}

	public function initFromString(String s) {
		try {
			if (!s.contains(",")) {
				if (s.equals("null")) {
					return null;
				} else {
					return new Polynom(s);
				}
			}
			int index = 1;
			int i = s.indexOf("(") + 1;
			for (; i < s.length(); i++) {
				if (s.charAt(i) == '(') {
					index++;
				}
				if (s.charAt(i) == ',') {
					index--;
				}
				if (index == 0) {
					break;
				}
			}
			function left = initFromString(s.substring(s.indexOf("(") + 1, i));// take the left side of the complex
																				// function
			function right = initFromString(s.substring(i + 1, s.lastIndexOf(")"))); // take the right side of the
																						// complex function
			function cf = new ComplexFunction(s.substring(0, s.indexOf("(")), left, right);
			return cf;
		} catch (Exception e) {
			throw new InvalidInputException("Invalid input");
		}
	}

	public double f(double x) {
		double sum = 0;
		if (this.op == Operation.Plus) {
			sum += this.left.f(x) + this.right.f(x);
		}
		if (this.op == Operation.Min) {
			sum += Math.min(this.left.f(x), this.right.f(x));
		}
		if (this.op == Operation.Max) {
			sum += Math.max(this.left.f(x), this.right.f(x));
		}
		if (this.op == Operation.Times) {
			sum += this.left.f(x) * this.right.f(x);
		}
		if (this.op == Operation.Divid) {
			if (this.right.f(x) == 0) {
				throw new InvalidInputException("can't divid in 0");
			}
			sum += this.left.f(x) / this.right.f(x);
		}
		if (this.op == Operation.Comp) {
			sum += this.left.f(this.right.f(x));
		}
		if (this.op == Operation.None) {
			sum += this.left.f(x);
		}
		if (this.op == Operation.Error) {
			throw new InvalidInputException("undefind function");
		}
		return sum;
	}

	public String toString() {
		String ans = "";
		if (this.op == Operation.None) {
			if (this.right == null) {
				ans += this.left;
			}
		} else {
			ans += this.op + "(" + this.left() + "," + this.right() + ")";
		}
		return ans;
	}

	public function copy() {
		if (this.right == null) {
			function copy = new ComplexFunction(this.getOp(), this.left.copy(), null);
			return copy;
		}
		function copy = new ComplexFunction(this.getOp(), this.left.copy(), this.right.copy());
		return copy;
	}

	public boolean equals(Object obj) {
		double eps = 0.0001;
		double arr[] = { -3, -1, 0.2, 1, 2, 4.2, 3.2, 7, 4, 10, 1.4 };
		function f = initFromString(obj.toString());
		for (int i = 0; i < arr.length; i++) {
			double x = arr[i];
			if (this.f(x) - f.f(x) > eps) {
				return false;
			}
		}
		return true;
	}

	public void plus(function f1) {
		function copy;
		copy = this.copy();
		this.left = copy;
		this.right = f1;
		this.op = Operation.Plus;
	}

	public void mul(function f1) {
		function copy;
		copy = this.copy();
		this.left = copy;
		this.right = f1;
		this.op = Operation.Times;
	}

	public void div(function f1) {
		function copy;
		copy = this.copy();
		this.left = copy;
		this.right = f1;
		this.op = Operation.Divid;
	}

	public void max(function f1) {
		function copy;
		copy = this.copy();
		this.left = copy;
		this.right = f1;
		this.op = Operation.Max;
	}

	public void min(function f1) {
		function copy;
		copy = this.copy();
		this.left = copy;
		this.right = f1;
		this.op = Operation.Min;
	}

	public void comp(function f1) {
		function copy;
		copy = this.copy();
		this.left = copy;
		this.right = f1;
		this.op = Operation.Comp;
	}

	public function left() {
		return this.left;
	}

	public function right() {
		return this.right;
	}

	public Operation getOp() {
		return this.op;
	}

}
