package ru.desireidea.dejavu;

public class ExtendedInteger {

	private Integer value = 0;

	public ExtendedInteger(Integer value) {
		this.value = value;
	}

	public ExtendedInteger(int value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}

	public int intValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static SmartList primeInts(int limit) {
		SmartList list = new SmartList();
		SmartList result = new SmartList();
		for (double i = 2; i < limit; i++)
			list.add(i);
		while (!list.isEmpty()) {
			double x = (double) list.get(0);
			result.add(x);
			try {
				list.reject("%", x, (double) 0);
			} catch (IllegalOperationException e) {
				return null;
			}
		}
		result.doubleToInt();
		return result;
	}

	public SmartList primeInts() {
		return primeInts(value);
	}

	public static SmartList primeDivisors(int arg) {
		SmartList list = new SmartList(primeInts(arg));
		list.intToDouble();
		try {
			list.select((double) arg, "%", (double) 0);
		} catch (IllegalOperationException e) {
			list = null;
		}
		list.doubleToInt();
		return list;
	}

	public SmartList primeDivisors() {
		return primeDivisors(value);
	}

}
