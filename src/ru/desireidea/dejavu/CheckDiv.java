package ru.desireidea.dejavu;

public class CheckDiv implements BooleanOperation {

	public Boolean op(Object arg1, Object arg2)
			throws IllegalOperationException {
		try {
			return ((double) arg1 % (double) arg2) == 0;
		} catch (ClassCastException e) {
			Object[] operands = { arg1, arg2 };
			throw new IllegalOperationException(
					"Класс CheckDiv поддерживает только аргументы, приводящиеся к типу double",
					e, "checking division", operands);
		}
	}

}
