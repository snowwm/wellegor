package ru.desireidea.wellegor;

import java.awt.Point;

public class BGMData implements Cloneable {

	public long value = -6;
	public byte[] pointsH, pointsV;
	public Point[][] lines;
	public int[] intSum;
	public static final long WRONG_PRODUCT = -1;
	public static final long ILLEGAL_FACTOR = -2;
	public static final long INDEX_OUT_OF_BOUNDS_EXCEPTION = -3;
	public static final long NUMBER_FORMAT_EXCEPTION = -4;
	public static final long UNEXPECTED_EXCEPTION = -5;
	public static final long UNKNOWN_ERROR = -6;
	public static final long MAX_PRODUCT_VALUE = 999999998000000001l;
	public static final int MAX_FACTOR_VALUE = 999999999;

	public BGMData(long value, byte[] pointsH, byte[] pointsV, Point[][] lines,
			int[] intSum) {
		this.value = value;
		this.pointsH = pointsH;
		this.pointsV = pointsV;
		this.lines = lines;
		this.intSum = intSum;
	}

	public BGMData() {
	}

	public BGMData clone() {
		return new BGMData(value, pointsH, pointsV, lines, intSum);
	}

}
