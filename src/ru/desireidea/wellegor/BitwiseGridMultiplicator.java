package ru.desireidea.wellegor;

import java.awt.Point;

public abstract class BitwiseGridMultiplicator {

	public static final long WRONG_PRODUCT = -1;
	public static final long ILLEGAL_FACTOR = -2;
	public static final long INDEX_OUT_OF_BOUNDS_EXCEPTION = -3;
	public static final long NUMBER_FORMAT_EXCEPTION = -4;
	public static final long UNEXPECTED_EXCEPTION = -5;
	public static final long UNKNOWN_ERROR = -6;
	public static final long MAX_PRODUCT_VALUE = 999999998000000001l;
	public static final int MAX_FACTOR_VALUE = 999999999;

	public static long multiply(long arg1, long arg2, boolean state) {
		return specialMultiply(arg1, arg2, state).value;
	}

	public static BGMData specialMultiply(long lArg1, long lArg2,
			boolean checkResult) {
		BGMData data = new BGMData();
		if (lArg1 < 0 || lArg1 > MAX_FACTOR_VALUE || lArg2 < 0
				|| lArg2 > MAX_FACTOR_VALUE) {
			data.value = -2;
			return data;
		}
		try {
			String arg1 = String.valueOf(lArg1);
			String arg2 = String.valueOf(lArg2);
			byte[][] points = new byte[arg2.length()][arg1.length()];
			data.intSum = new int[arg1.length() + arg2.length() - 1];
			byte counter = (byte) (data.intSum.length - 1);
			byte[] digits = new byte[20];
			String result = "";
			data.pointsV = new byte[arg1.length()];
			data.pointsH = new byte[arg2.length()];
			data.lines = new Point[data.intSum.length][];
			for (byte i = 0; i < arg1.length(); i++) {
				for (byte j = 0; j < arg2.length(); j++) {
					data.pointsV[i] = Byte.parseByte(String.valueOf(arg1
							.charAt(i)));
					data.pointsH[j] = Byte.parseByte(String.valueOf(arg2
							.charAt(j)));
					points[j][i] = (byte) (data.pointsV[i] * data.pointsH[j]);
				}
			}
			for (byte i = 0; i < points.length; i++, counter--) {
				data.lines[counter] = new Point[Math.min(i + 1,
						points[i].length)];
				for (byte j = i, k = 0; j >= 0 && j < points.length && k >= 0
						&& k < points[i].length; j--, k++) {
					data.intSum[counter] += points[j][k];
					data.lines[counter][k] = new Point(j, k);
				}
			}
			for (byte i = 1; i < points[0].length; i++, counter--) {
				data.lines[counter] = new Point[Math.min(points.length,
						points[0].length - i)];
				for (byte j = i, k = (byte) (points.length - 1), n = 0; j >= 0
						&& j < points[0].length && k >= 0 && k < points.length; j++, k--, n++) {
					data.intSum[counter] += points[k][j];
					data.lines[counter][n] = new Point(k, j);
				}
			}
			counter = 19;
			for (byte i = 0; i < data.intSum.length; i++, counter--) {
				String[] sum = new String[data.intSum.length];
				sum[i] = String.valueOf(data.intSum[i]);
				for (byte j = (byte) (sum[i].length() - 1), c = counter; j >= 0; j--, c--) {
					digits[c] += Byte.valueOf(String.valueOf(sum[i].charAt(j)));
					if (digits[c] > 9)
						for (byte k = c;; k--) {
							digits[k] -= 10;
							digits[k - 1] += 1;
							if (digits[k] < 10)
								break;
						}
				}
			}
			for (byte i = 0; i < digits.length; i++) {
				result += digits[i];
			}
			data.value = Long.valueOf(result);
		} catch (IndexOutOfBoundsException e) {
			data.value = -3;
			return data;
		} catch (NumberFormatException e) {
			data.value = -4;
			return data;
		} catch (Exception e) {
			data.value = -5;
			return data;
		}
		if (checkResult && data.value != lArg1 * lArg2)
			data.value = -1;
		return data;
	}

}
