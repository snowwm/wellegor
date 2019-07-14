package ru.desireidea.dejavu;

public class IllegalOperationException extends Exception {

	private String operation = "[non-known]";
	private String operands = "[non-known]";

	public IllegalOperationException() {
	}

	public IllegalOperationException(String message) {
		super(message);
	}

	public IllegalOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalOperationException(Throwable cause) {
		super(cause);
	}

	public IllegalOperationException(String message, Throwable cause,
			String operation, Object[] operands) {
		super(message, cause);
		this.operation = operation;
		this.operands = getOperandsTypes(operands);
	}

	public IllegalOperationException(String message, String operation,
			Object[] operands) {
		super(message);
		this.operation = operation;
		this.operands = getOperandsTypes(operands);
	}

	public IllegalOperationException(Throwable cause, String operation,
			Object[] operands) {
		super(cause);
		this.operation = operation;
		this.operands = getOperandsTypes(operands);
	}

	public IllegalOperationException(String oeration, Object[] operands) {
		this.operation = oeration;
		this.operands = getOperandsTypes(operands);
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String action) {
		this.operation = action;
	}

	public String getOperands() {
		return operands;
	}

	public void setOperands(String operands) {
		this.operands = operands;
	}

	private String getOperandsTypes(Object[] operands) {
		String outcome = "";
		for (int i = 0; i < operands.length; i++) {
			if (i != 0)
				outcome += ", ";
			if (operands[i] != null)
				outcome += operands[i].getClass().getName();
			else
				outcome += "null";
		}
		return outcome;
	}

	public String getLocalizedMessage() {
		return "The operation \"" + operation
				+ "\" is unsupported for the operands' types: " + operands
				+ "." + (getMessage() != null ? " " + getMessage() : "");
	}

	public String toString() {
		return getClass().getCanonicalName()
				+ (getMessage() != null ? ": " + getMessage() : "");
	}

}