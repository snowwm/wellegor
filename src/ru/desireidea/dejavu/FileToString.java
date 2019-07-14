package ru.desireidea.dejavu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileToString {

	public static String readFile(InputStream input) throws Exception {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		while ((line = reader.readLine()) != null)
			builder.append(line).append("\n");
		reader.close();
		return builder.toString();
	}

}
