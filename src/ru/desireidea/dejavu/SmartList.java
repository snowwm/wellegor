package ru.desireidea.dejavu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class SmartList extends ArrayList {

	public static final String EXMES1 = "Тип сравниваемого результата не соответствует типу реального значения.";
	public static final String EXMES2 = "Типы операндов не соответствуют выбранной операции.";
	public static final String EXMES3 = "Выбранная операция не реализована в данном методе.";

	public SmartList(int arg) {
		super(arg);
	}

	public SmartList() {
		super();
	}

	public SmartList(Collection arg) {
		super(arg);
	}

	public SmartList collect(BooleanOperation collector, Object arg2)
			throws IllegalOperationException {
		SmartList result = new SmartList();
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			result.add(collector.op(i.next(), arg2));
		}
		return result;
	}

	public SmartList collect(Object arg1, String operation)
			throws IllegalOperationException {
		SmartList result = new SmartList();
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			result.add(op(arg1, operation, i.next()));
		}
		return result;
	}

	public SmartList collect(String operation, Object arg2)
			throws IllegalOperationException {
		SmartList result = new SmartList();
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			result.add(op(i.next(), operation, arg2));
		}
		return result;
	}

	public void select(BooleanOperation selector, Object arg2)
			throws IllegalOperationException {
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			if (!selector.op(i.next(), arg2))
				i.remove();
		}
	}

	public void select(Object arg1, String operation, Object result)
			throws IllegalOperationException {
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			if (!op(arg1, operation, i.next(), result))
				i.remove();
		}
	}

	public void select(String operation, Object arg2, Object result)
			throws IllegalOperationException {
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			if (!op(i.next(), operation, arg2, result))
				i.remove();
		}
	}

	public void reject(BooleanOperation rejector, Object arg2)
			throws IllegalOperationException {
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			if (rejector.op(i.next(), arg2))
				i.remove();
		}
	}

	public void reject(Object arg1, String operation, Object result)
			throws IllegalOperationException {
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			if (op(arg1, operation, i.next(), result))
				i.remove();
		}
	}

	public void reject(String operation, Object arg2, Object result)
			throws IllegalOperationException {
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			if (op(i.next(), operation, arg2, result))
				i.remove();
		}
	}

	public Object detect(BooleanOperation detector, Object arg2)
			throws IllegalOperationException {
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			Object arg1 = i.next();
			if (detector.op(arg1, arg2))
				return arg1;
		}
		return null;
	}

	public Object detect(Object arg1, String operation, Object result)
			throws IllegalOperationException {
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			Object arg2 = i.next();
			if (op(arg1, operation, arg2, result))
				return arg2;
		}
		return null;
	}

	public Object detect(String operation, Object arg2, Object result)
			throws IllegalOperationException {
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			Object arg1 = i.next();
			if (op(arg1, operation, arg2, result))
				return arg1;
		}
		return null;
	}

	public Object inject(Operation injector, Object result)
			throws IllegalOperationException {
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			result = injector.op(i.next(), result);
		}
		return result;
	}

	public Object inject(Object result, String operation)
			throws IllegalOperationException {
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			result = op(result, operation, i.next());
		}
		return result;
	}

	public Object inject(String operation, Object result)
			throws IllegalOperationException {
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			result = (op(i.next(), operation, result));
		}
		return result;
	}

	public int count(BooleanOperation oper, Object arg2)
			throws IllegalOperationException {
		int counter = 0;
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			if (oper.op(i.next(), arg2))
				counter++;
		}
		return counter;
	}

	public int count(Object arg1, String operation, Object result)
			throws IllegalOperationException {
		int counter = 0;
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			if (op(arg1, operation, i.next(), result))
				counter++;
		}
		return counter;
	}

	public Object count(String operation, Object arg2, Object result)
			throws IllegalOperationException {
		int counter = 0;
		for (ListIterator i = this.listIterator(); i.hasNext();) {
			if (op(i.next(), operation, arg2, result))
				counter++;
		}
		return counter;
	}

	private boolean op(Object arg1, String operation, Object arg2, Object result)
			throws IllegalOperationException {
		Object s = op(arg1, operation, arg2);
		if (s instanceof Boolean)
			return (boolean) s;
		else if (result instanceof Double)
			return (double) s == (double) result;
		Object[] operands = { arg1, arg2, result };
		throw new IllegalOperationException(EXMES1, operation, operands);
	}

	private Object op(Object arg1, String operation, Object arg2)
			throws IllegalOperationException {
		if (operation == "=") {
			return arg1.equals(arg2);
		} else {
			if (arg1 instanceof Boolean & arg2 instanceof Boolean) {
				return op((boolean) arg1, operation, (boolean) arg2);
			} else if (arg1 instanceof Double & arg2 instanceof Double) {
				if (operation == "<=")
					return (double) arg1 <= (double) arg2;
				else if (operation == ">=")
					return (double) arg1 >= (double) arg2;
				else
					return op((double) arg1, operation, (double) arg2);
			}
		}
		Object[] operands = { arg1, arg2 };
		throw new IllegalOperationException(EXMES2, operation, operands);
	}

	private boolean op(boolean arg1, String operation, boolean arg2)
			throws IllegalOperationException {
		switch (operation) {
		case "|":
			return arg1 | arg2;
		case "&":
			return arg1 & arg2;
		case "^":
			return arg1 ^ arg2;
		case "&&":
			return arg1 && arg2;
		case "||":
			return arg1 || arg2;
		}
		Object[] operands = { arg1, arg2 };
		throw new IllegalOperationException(EXMES3, operation, operands);
	}

	private double op(double arg1, String operation, double arg2)
			throws IllegalOperationException {
		switch (operation) {
		case "+":
			return arg1 + arg2;
		case "-":
			return arg1 - arg2;
		case "*":
			return arg1 * arg2;
		case "/":
			return arg1 / arg2;
		case "%":
			return arg1 % arg2;
		}
		Object[] operands = { arg1, arg2 };
		throw new IllegalOperationException(EXMES3, operation, operands);
	}

	public static List doubleToInt(List list) {
		for (int i = 0; i < list.size(); i++) {
			list.set(
					i,
					(list.get(i) instanceof Double ? ((Double) list.get(i))
							.intValue() : list.get(i)));
		}
		return list;
	}

	public static List intToDouble(List list) {
		for (int i = 0; i < list.size(); i++) {
			list.set(
					i,
					(list.get(i) instanceof Integer ? ((Integer) list.get(i))
							.doubleValue() : list.get(i)));
		}
		return list;
	}

	public List doubleToInt() {
		return doubleToInt(this);
	}

	public List intToDouble() {
		return intToDouble(this);
	}

	public static ArrayList toArrayList(SmartList list) {
		ArrayList result = new ArrayList();
		for (ListIterator i = list.listIterator(); i.hasNext();) {
			result.add(i.next());
		}
		return result;
	}

	public ArrayList toArrayList() {
		return toArrayList(this);
	}

}
