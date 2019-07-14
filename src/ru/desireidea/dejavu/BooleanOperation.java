package ru.desireidea.dejavu;

public interface BooleanOperation extends Operation {

	public Boolean op(Object arg1, Object arg2)
			throws IllegalOperationException;

}
