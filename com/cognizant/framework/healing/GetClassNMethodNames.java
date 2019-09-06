package com.cognizant.framework.healing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cognizant.framework.Util;

public class GetClassNMethodNames {

	public static List<String> getVaribleName(int lineNumber, String className, String methodName, String frameworkPath,
			String packageName) {

		List<String> variableName = new ArrayList<>();
		try {

			String code = Files.readAllLines(Paths.get(Util.getJavaPathOfFramework() + Util.getFileSeparator()
					+ packageName + Util.getFileSeparator() + className + ".java")).get(lineNumber - 1);

			String pageClassName = getPageClass(code);

			if (pageClassName.startsWith("By")) {

				variableName.add(className);
				variableName.add(methodName + lineNumber);

			} else {
				variableName = getSimpleVariableName(pageClassName, className);
			}

		} catch (IOException e) {
			e.getMessage();
		}
		return variableName;
	}

	private static List<String> getSimpleVariableName(String pageClassName, String className) {

		List<String> simpleVariableName = new ArrayList<>();

		try {

			simpleVariableName.add(pageClassName.split("\\.")[0]);
			simpleVariableName.add(pageClassName.split("\\.")[1]);

		} catch (Exception e) {

			simpleVariableName.clear();
			simpleVariableName.add(className);
			simpleVariableName.add(pageClassName.split("\\.")[0]);
		}

		return simpleVariableName;
	}

	public static String getPageClass(String code) {
		String className = null;
		Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(code);
		while (m.find()) {
			className = m.group(1);
			break;
		}
		return className;
	}

	public static String getCodeLine(int lineNumber, String className, String methodName, String frameworkPath,
			String packageName) {
		String code = null;
		packageName = packageName.replace(".", "\\");

		try {
			code = Files.readAllLines(Paths.get(Util.getJavaPathOfFramework() + Util.getFileSeparator()
					+ packageName + Util.getFileSeparator() + className + ".java")).get(lineNumber - 1);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;

	}

}
