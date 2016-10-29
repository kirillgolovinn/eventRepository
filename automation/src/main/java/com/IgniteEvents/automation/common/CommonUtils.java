package com.IgniteEvents.automation.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.logging.log4j.Logger;
import org.junit.ComparisonFailure;
import org.testng.Assert;

public class CommonUtils {

	public static Map<String, String> strMap = new HashMap<String, String>();
	public static Map<String, String> stanParam = new HashMap<String, String>();
	static Properties props = new Properties();
	static Properties issuesList = new Properties();
	static String propFileName = "src/test/resources/properties.prop";
	private static final Logger logger = SeleneseTestCase.logger;

	// Check, if one of checks is fail, then fail test
	public static void checkAndFail(String TestName) {
		if (CommonUtils.getParam("testResult", false).equals("fail")) {
			throw new AssertionError(TestName + " test is FAIL. Please see log for details");
		}
	}

	public static void setStandtartParam(String key, String aValue) {

		stanParam.put(key, aValue);
	}

	public static String getStandtartParam(String aKey) {

		if (stanParam.get(aKey) == null) {
			System.err.println("Not correct data. Param " + aKey + " not found.");
		}
		return stanParam.get(aKey);

	}

	public static void clearStandtartParam() {

		stanParam.clear();
	}

	public static void setParam(String key, String aValue) {

		strMap.put(key, aValue);
	}

	public static String getParam(String aKey, boolean printError) {

		if (strMap.get(aKey) == null) {
			return "false";
		}
		return strMap.get(aKey);
	}
			
	
	public static String getParam(String aKey) {
		if (strMap.get(aKey) == null) {
			logger.info("Not correct data. Param " + aKey + " not found.");
			return "false";
		}
		return strMap.get(aKey);
	}

	public static void clear() {

		strMap.clear();
	}

	public static boolean assertValue(String message, Object expected, Object actual, boolean ignoreException)
			throws ComparisonFailure {
		try {
			Assert.assertEquals(expected, actual);
		} catch (ComparisonFailure e) {
			System.err.println(e.getMessage());

			if (!ignoreException) {
				throw new ComparisonFailure(message, expected.toString(), actual.toString());
			}

		}
		return false;
	}

	public static String booleanTo1or0(boolean aValue) {
		if (aValue) {
			return "1";
		} else {
			return "0";
		}
	}

	public static boolean booleanTo1or0Reverce(String aValue) {
		if (aValue.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean reverceBooleanValue(boolean aValue) {
		if (aValue) {
			return false;
		} else {
			return true;
		}
	}

	public static String replaceSimbol(String aString, String subStr, String subStrNew) throws Exception {
		StringBuffer s = new StringBuffer(aString);
		if (aString.indexOf(subStr) > 0) {
			s.replace(aString.indexOf(subStr), aString.indexOf(subStr) + subStr.length(), subStrNew);
		}
		return s.toString();
	}

	public static String createDateTimeString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd '_' HH-mm-ss");
		return dateFormat.format(new Date());
	}

	public static String addDaysToCurrentDate(Integer days) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, days);
		return dateFormat.format(c.getTime());
	}

	public static String getMonthNumber(String aMonth) {
		Integer monthNumber = 0;

		String[] profileData = { "January", "February", "March", "April", "May", "June", "July", "August", "Septembar",
				"October", "November", "December" };
		for (int i = 0; i < profileData.length; i++) {
			if (aMonth.equals(profileData[i])) {
				monthNumber = i + 1;

			}
		}
		return monthNumber.toString();
	}

	public static String convertDateFormat(String aDate) throws ParseException {
		aDate = aDate.replaceAll("/", ".");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
		return dateFormat.format(dateFormat.parse(aDate));
	}

	public static Integer getAge(String bYear) {
		Date birthdate = new Date();
		Calendar calendar = GregorianCalendar.getInstance();

		calendar.setTime(new Date());
		int currentYear = calendar.get(Calendar.YEAR);

		calendar.setTime(birthdate);
		calendar.set(Calendar.YEAR, Integer.parseInt(bYear));
		int birthYear = calendar.get(Calendar.YEAR);

		return currentYear - birthYear;
	}

	public static String getDatesSeparator(String aBrowser) {
		if (aBrowser.equalsIgnoreCase("IE")) {
			return ".";
		} else
			return "/";
	}

	public static String assertEquals(String assertName, String expected, String actual) {
		String errrorMessage = "true";
		if (!actual.equals(expected)) {
			errrorMessage = "Wrong " + assertName + ". Expexted " + expected + " but was " + actual;
		}
		return errrorMessage;
	}

	public static String getTodayDateDependsOnBrowser(String browser) {
		return getTodayDateDependsOnBrowser(browser, 0);
	}

	public static String getTodayDateDependsOnBrowser(String browser, Integer addDays) {
		String separator = getDatesSeparator(browser);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd" + separator + "MM" + separator + "yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, addDays);
		return dateFormat.format(c.getTime());
	}

	public static String getTodayDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format(new Date());
	}

	public static String getUnicName() {
		String uniqueName = CommonUtils.createDateTimeString();
		uniqueName = uniqueName.replace(" ", "");
		uniqueName = uniqueName.replace("-", "");
		uniqueName = uniqueName.replace("_", "");
		return uniqueName;
	}

	public static boolean writeDataToFile(String aData, boolean reWrite) throws IOException {
		Map<Integer, String> oldData = null;
		if (!reWrite) {
			oldData = readData();
		}
		String filename = new File("TestData").getAbsolutePath();

		if (!new File(filename + File.separator + "data.txt").exists()) {
			new File(filename + File.separator + "data.txt").createNewFile();
		}

		File data = new File(filename + File.separator + "data.txt");
		PrintWriter p = new PrintWriter(new FileWriter(data));
		p.println(aData);
		if (!reWrite) {
			for (int i = 0; i < oldData.size(); i++) {
				p.println(oldData.get(i));
			}
		}

		p.close();
		return true;
	}

	public static Map<Integer, String> readData() {
		String filename = new File("TestData").getAbsolutePath();
		Map<Integer, String> data = new HashMap<Integer, String>();
		int i = 0;
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename + File.separator + "data.txt"));
			String line;
			while ((line = in.readLine()) != null) {
				data.put(i, line);

				i++;
			}
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static Integer getTextAfter(String aText, String subString) {
		return Integer.valueOf(aText.substring(aText.lastIndexOf(subString) + subString.length(), aText.length()));

	}

	public static String createLongString(Integer aLong) {
		String aText = "It's test string. Length is " + aLong.toString();
		while (aText.length() < aLong) {
			aText = aText + aText;
		}
		return aText.substring(0, aLong);

	}

	public static String getRandomValue(Integer intMaxValue, Integer doubleValueLength) {
		Random randomGenerator = new Random();
		String result = Double.toString(randomGenerator.nextInt(intMaxValue) + randomGenerator.nextDouble());
		result = result.substring(0, result.lastIndexOf(".") + doubleValueLength + 1);
		if (doubleValueLength == 0)
			result = result.replace(".", "");
		return result;
	}

	public static String getRandomValueFromTo(Integer intMinValue, Integer intMaxValue, Integer doubleValueLength) {
		Random randomGenerator = new Random();
		Integer val = 0;
		while (val <= intMinValue) {
			val = randomGenerator.nextInt(intMaxValue);
		}
		if (doubleValueLength > 0) {
			String result = Double.toString(val + randomGenerator.nextDouble());
			return result.substring(0, result.lastIndexOf(".") + doubleValueLength + 1);
		}
		return Integer.toString(val);
	}

	public static Integer getRandomValueNumericFromTo(Integer intMinValue, Integer intMaxValue) {
		Random randomGenerator = new Random();
		Integer val = 0;
		while (val <= intMinValue) {
			val = randomGenerator.nextInt(intMaxValue);
		}
		return val;
	}

	public static String getRandomNumericValueFixedLength(Integer len) {
		String s = "";
		while (s.length() < len) {
			s = s + getRandomValueFromTo(0, 9, 0);
		}
		return s;
	}

	public static Boolean getRandomBoolean() {
		Random rnd = new Random();
		return rnd.nextBoolean();
	}

	// returns array from string. Use : as symbol
	public static String[] getArrayFromStringBySymbol(String aText, String aSymbol) {
		return aText.split(aSymbol);
	}

	public static FileInputStream getFileInputStream() {
		return getFileInputStream(propFileName);
	}

	private static FileInputStream getFileInputStream(String fileName) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(fileName).getAbsolutePath());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return fis;
	}

	private static void loadProperties(String fileName) {
		try {
			props.load(getFileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void saveProperties() {
		saveProperties(propFileName);
	}

	@SuppressWarnings("deprecation")
	private static void saveProperties(String fileName) {
		try {
			props.save(new FileOutputStream(new File(fileName).getAbsolutePath()), "AUTOMATICALLY GENERATED");
		} catch (FileNotFoundException e) {
			logger.error("", e);
		}
	}

	public static void setProperty(String key, String value) {
		if (new File(propFileName).exists()) {
			loadProperties(propFileName);
		}
		props.setProperty(key, value);
		saveProperties();
	}

	public static String getProperty(String key) {
		return getProperty(key, "0");
	}

	public static String getProperty(String key, String defValue) {
		if (new File(propFileName).exists()) {
			loadProperties(propFileName);
		}
		if (props.getProperty(key) == null) {
			props.setProperty(key, defValue);
		}
		return props.getProperty(key);

	}

	public static void saveIssueKey(String key, String value) {
		if (new File("issues.prop").exists()) {
			loadProperties("issues.prop");
		}
		issuesList.setProperty(key, value);
		saveIssues("issues.prop");
	}

	public static String getIssueKey(String key) {
		if (new File("issues.prop").exists()) {
			loadIssues("issues.prop");
		}
		if (issuesList.getProperty(key) == null) {
			return "";
		}
		return issuesList.getProperty(key);
	}

	private static void loadIssues(String fileName) {
		try {
			issuesList.load(getFileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private static void saveIssues(String fileName) {
		try {
			issuesList.save(new FileOutputStream(new File(fileName).getAbsolutePath()), "AUTOMATICALLY GENERATED");
		} catch (FileNotFoundException e) {
			logger.error("", e);
		}
	}

	public static String anonimizeCreditCardNumber(String cardNumber) {

		return "**************" + cardNumber.substring(12);
	}

	public static void saveDataToCSV(String fileName, String data, boolean isAdd) {
		try {
			FileWriter writer = new FileWriter(fileName, isAdd);
			String[] s = data.split(":");
			for (int i = 0; i < s.length; i++) {
				writer.append(s[i]);
				writer.append(',');
			}
			writer.append('\n');
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

	public static void addDataToCSV(String fileName, String data, String whereAdd) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line;
		String whereChange = "";
		String cvsSplitBy = ",";
		List<String> oldData = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			if (line.contains(whereAdd)) {
				whereChange = line.replace(cvsSplitBy, ":");
			} else {
				oldData.add(line);
			}
		}

		try {
			FileWriter writer = new FileWriter(fileName, false);
			data = whereChange + data;
			String[] s = data.split(":");
			for (int i = 0; i < s.length; i++) {
				writer.append(s[i]);
				writer.append(',');
			}
			writer.append('\n');
			for (int i = 0; i < oldData.size(); i++) {
				writer.append(oldData.get(i));
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void removeDataFromCSV(String string) {
		// TODO Auto-generated method stub

	}

}
