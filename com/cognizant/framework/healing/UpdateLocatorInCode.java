package com.cognizant.framework.healing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.cognizant.framework.Util;

public class UpdateLocatorInCode {

	public static void findPageNUpdate(int lineNumber, String className, By arg0, String frameworkPath,
			String packageName) throws IOException {

		String code = Files.readAllLines(Paths.get(Util.getJavaPathOfFramework() + Util.getFileSeparator() + packageName
				+ Util.getFileSeparator() + className + ".java")).get(lineNumber - 1);
		String pageClassName = getPageClass(code);

		findWhichPage(pageClassName, arg0, frameworkPath, className, code, packageName);

	}

	private static void findWhichPage(String lineCode, By arg0, String frameworkPath, String bcClassName, String code,
			String packageName) {

		if (packageName.contains("pages")) {

			if (!lineCode.contains("By.")) {

				updatePageCodeForPage(bcClassName, lineCode.split("\\.")[0], arg0, frameworkPath);

			} else {

				if (lineCode.contains("By.")) {
					updatePageCodeForPageInsideMethod(code, arg0, frameworkPath, bcClassName);
				} else if (lineCode.length() == 0) {
					updatePageCodeForPage(lineCode.split("\\.")[0], lineCode.split("\\.")[1], arg0, frameworkPath);
				}
			}

		} else if (packageName.contains("businesscomponents")) {
			if (checkIfItsPage(frameworkPath, lineCode)) {
				updatePageCodeForPage(lineCode.split("\\.")[0], lineCode.split("\\.")[1], arg0, frameworkPath);
			} else {
				updatePageCodeForBusinessComponent(code, arg0, frameworkPath, bcClassName);
			}
		}

	}

	private static boolean checkIfItsPage(String frameworkPath, String lineCode) {
		boolean isPage = false;
		final String CLASS_FILE_EXTENSION = ".java";

		String encryptedBCPath = Util.getJavaPathOfFramework() + Util.getFileSeparator() + "pages";

		File[] packageDirectories = { new File(encryptedBCPath) };

		for (File packageDirectory : packageDirectories) {
			File[] packageFiles = packageDirectory.listFiles();
			for (int i = 0; i < packageFiles.length; i++) {
				File packageFile = packageFiles[i];
				String fileName = packageFile.getName();
				String className = fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION.length());
				if (className.equals(lineCode.split("\\.")[0])) {
					return isPage = true;
				}

			}
		}
		return isPage;

	}

	private static void updatePageCodeForBusinessComponent(String lineCode, By arg0, String frameworkPath,
			String bcClassName) {
		final String CLASS_FILE_EXTENSION = ".java";

		String encryptedBCPath = Util.getJavaPathOfFramework() + Util.getFileSeparator() + "businesscomponents";

		File[] packageDirectories = { new File(encryptedBCPath) };

		for (File packageDirectory : packageDirectories) {
			File[] packageFiles = packageDirectory.listFiles();
			for (int i = 0; i < packageFiles.length; i++) {
				File packageFile = packageFiles[i];
				String fileName = packageFile.getName();
				String path = packageFile.getAbsolutePath();

				if (fileName.endsWith(CLASS_FILE_EXTENSION)) {

					String className = fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION.length());
					if (className.equals(bcClassName)) {
						writeBusinessComponentFile(lineCode, arg0, path, bcClassName);
					}
				}
			}
		}

	}

	private static void writeBusinessComponentFile(String lineCode, By arg0, String path, String classNameNew) {
		Map<String, String> details = getLineNumberForMethod(lineCode, path);
		String trimmedLocator;
		String properLocator;
		if (lineCode.split("=", 2)[0].contains("WebElement")) {
			trimmedLocator = getTrimmerLocatorBC(arg0.toString());
			properLocator = lineCode.split("=", 2)[0] + "=" + "driver.findElement(" + trimmedLocator + ")" + ";".trim();
		} else {
			trimmedLocator = getTrimmerLocatorBC(arg0.toString());
			properLocator = "driver.findElement(" + trimmedLocator + ")" + "."
					+ lineCode.substring(lineCode.lastIndexOf(".") + 1).trim();
		}

		ChangeLineInFile changeFile = new ChangeLineInFile();
		changeFile.changeALineInATextFile(path, properLocator, Integer.valueOf(details.get("LineNumber")));

	}

	private static String getTrimmerLocatorBC(String locator) {
		return locator.split(":")[0] + "(" + "\"" + locator.split(":")[1].trim() + "\"" + ")";
	}

	private static void updatePageCodeForPage(String classNameNew, String methodName, By arg0, String frameworkPath) {
		final String CLASS_FILE_EXTENSION = ".java";

		String encryptedBCPath = Util.getJavaPathOfFramework() + Util.getFileSeparator() + "pages";

		File[] packageDirectories = { new File(encryptedBCPath) };

		for (File packageDirectory : packageDirectories) {
			File[] packageFiles = packageDirectory.listFiles();
			for (int i = 0; i < packageFiles.length; i++) {
				File packageFile = packageFiles[i];
				String fileName = packageFile.getName();
				String path = packageFile.getAbsolutePath();

				if (fileName.endsWith(CLASS_FILE_EXTENSION)) {

					String className = fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION.length());
					if (className.equals(classNameNew)) {
						writePageFile(methodName, arg0, path, classNameNew);
					}
				}
			}
		}

	}

	private static void writePageFile(String methodName, By arg0, String path, String classNameNew) {

		Map<String, String> details = getLineNumberForMethod(methodName, path);

		String oldCode = details.get("LineText");
		String oldObject = oldCode.split("=", 2)[0];

		String trimmedLocator = getTrimmerLocator(arg0.toString());

		String properLocator = createValidLocator(oldObject, trimmedLocator);

		ChangeLineInFile changeFile = new ChangeLineInFile();
		changeFile.changeALineInATextFile(path, properLocator, Integer.valueOf(details.get("LineNumber")));

	}

	private static void updatePageCodeForPageInsideMethod(String lineCode, By arg0, String frameworkPath,
			String bcClassName) {
		final String CLASS_FILE_EXTENSION = ".java";

		String encryptedBCPath = Util.getJavaPathOfFramework() + Util.getFileSeparator() + "pages";

		File[] packageDirectories = { new File(encryptedBCPath) };

		for (File packageDirectory : packageDirectories) {
			File[] packageFiles = packageDirectory.listFiles();
			for (int i = 0; i < packageFiles.length; i++) {
				File packageFile = packageFiles[i];
				String fileName = packageFile.getName();
				String path = packageFile.getAbsolutePath();

				if (fileName.endsWith(CLASS_FILE_EXTENSION)) {

					String className = fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION.length());
					if (className.equals(bcClassName)) {
						writeBusinessComponentFile(lineCode, arg0, path, bcClassName);
					}
				}
			}
		}

	}

	private static String getTrimmerLocator(String locator) {
		return locator.split(":")[0] + ":" + locator.split(":")[1].trim();
	}

	private static String createValidLocator(String oldObject, String locator) {
		String newLoc;

		String newloc = "\"" + locator.split(":", 2)[1] + "\"";
		String newloc1 = "(" + newloc + ")";
		String newLoc12 = locator.split(":", 2)[0] + newloc1 + ";";

		newLoc = oldObject + "=" + newLoc12;
		return newLoc;
	}

	private static Map<String, String> getLineNumberForMethod(String methodName, String path) {
		Map<String, String> details = new LinkedHashMap<>();
		int lineNum = 0;
		try {

			BufferedReader lineReader = new BufferedReader(new FileReader(path));
			String lineText = null;

			while ((lineText = lineReader.readLine()) != null) {
				lineNum++;
				if (lineText.contains(methodName)) {

					details.put("LineNumber", Integer.toString(lineNum));
					details.put("LineText", lineText);
					break;
				}
			}

			lineReader.close();
		} catch (IOException ex) {
			System.err.println(ex);
		}

		return details;
	}

	private static String getPageClass(String code) {
		String className = null;
		Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(code);
		while (m.find()) {
			className = m.group(1);
			break;
		}
		return className;
	}

}
