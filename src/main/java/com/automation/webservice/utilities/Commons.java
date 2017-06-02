package com.automation.webservice.utilities;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Commons {

	private static final Logger LOGGER = LoggerFactory.getLogger(Commons.class);
	public  static String getCurrentDateTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}


	public static void getime(){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		f.setTimeZone(TimeZone.getTimeZone("CST6CDT"));
		Date date=new Date();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date);
		cal2.add(Calendar.HOUR_OF_DAY, -8);
		cal2.add(Calendar.MINUTE, 00);
		System.out.println(f.format(cal2.getTime()));
	}

	public static Map<String, Integer> countStringInFile(String stringToLookFor, String fileName){
		Map<String,Integer> map = new HashMap<String, Integer>();
		try{
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;
			br.readLine();
			br.readLine();
			while ((strLine = br.readLine()) != null) {
				if(!map.containsKey(strLine)){
					map.put(strLine, 1);
				}
				else{
					map.put(strLine, map.get(strLine)+1);
				}
			}
			for(Map.Entry<String,Integer> entry: map.entrySet()){
				System.out.println(entry.getKey() + "::" + entry.getValue());
			}

			in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return map;
	}


	/**
	 * Check if passed object is null
	 * @param obj
	 * 				Object to be verified
	 * @return True if null/false
	 */
	public static boolean isNull(Object obj){
		return obj == null;
	}

	/**
	 * Check if passed input is null or empty
	 * @param obj
	 * 				String to be verified
	 * @return true or false
	 */
	public static boolean isNullOrEmpty(String text){
		return text == null || text.isEmpty();
	}

	/**
	 * Check if passed input is NOT null or NOT empty
	 * @param obj
	 * 				String to be verified
	 * @return True/false
	 */
	public static boolean isNotNullAndEmpty(String text){
		return text != null && !text.isEmpty();
	}

	public static String getChicagoTimeStamp(String timeformat)
	{
		String Formattedtime = null;
		try
		{
			DateTime zoned = new DateTime(DateTimeZone.forID("America/Chicago"));
			System.out.println(zoned.toString());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
			SimpleDateFormat output = new SimpleDateFormat(timeformat);
			Date d = sdf.parse(zoned.toString());
			Formattedtime = output.format(d);
			System.out.println(Formattedtime);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Formattedtime;
	}


	public static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
	}

	public static String getTimeDiff(String d1, String d2){
		long diffMinutes = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(Commons.isNotNullAndEmpty(d1) && Commons.isNotNullAndEmpty(d2)){
				long diff = sdf.parse(d1).getTime() - sdf.parse(d2).getTime();
				long diffDays = diff / (24 * 60 * 60 * 1000);
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffMins = diff / (60 * 1000) % 60;
				diffMinutes = diffDays*24*60 + diffHours*60 + diffMins;
			}
			else{
				diffMinutes = 15;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Long.toString(diffMinutes+5);

	}

	public static String getTimeDiffInSecs(String d1, String d2){
		long diffSecs = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(Commons.isNotNullAndEmpty(d1) && Commons.isNotNullAndEmpty(d2)){
				long diff = sdf.parse(d1).getTime() - sdf.parse(d2).getTime();
				long diffDays = diff / (24 * 60 * 60 * 1000);
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffMins = diff / (60 * 1000) % 60;
				long diffSeconds = diff / 1000 % 60;
				diffSecs = diffDays*24*60*60 + diffHours*60*60 + diffMins*60 + diffSeconds;
			}
			else{
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Long.toString(diffSecs);

	}

	public String getJsonfromFile(String path){
		StringBuilder result = new StringBuilder("");
		try{
			InputStream file = getClass().getResourceAsStream(path);
			try (Scanner scanner = new Scanner(file)) {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					result.append(line).append("\n");
				}
				scanner.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result.toString();
	}

	public static void printMap(HashMap<String, String> map){
		for (String name: map.keySet()){
			String key =name.toString();
			String value = map.get(name).toString();  
			LOGGER.info(key + " " + value);  
		}
	}

	public static String getDiv(String partnum){
		String div = "";
		try{
			div = partnum.substring(0,3);
			LOGGER.info("Divison of the Item is " +div);  
		}catch(Exception e){
			e.printStackTrace();
		}
		return div;
	}

	public static Double CeilDoubleValue(Double CeilValue){
		return Math.ceil(CeilValue);
	}
}
