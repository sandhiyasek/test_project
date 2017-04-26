package gov.shdrc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorHelper {
	
	public static boolean isValidSplCharacter(String fieldValue){
		 String pattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=*]).{8,15})";
	     // Create a Pattern object
	     Pattern r = Pattern.compile(pattern);
	     // Now create matcher object.
	     Matcher m = r.matcher(fieldValue);
	     return m.matches();
	}
	
	public static boolean isValidNumericChar(String fieldValue){
		 String pattern = "^[A-Za-z0-9@!.' \\+]+(\\[_A-Za-z0-9]+)*";
	     // Create a Pattern object
	     Pattern r = Pattern.compile(pattern);
	     // Now create matcher object.
	     Matcher m = r.matcher(fieldValue);
	     return m.matches();
	}
	public static boolean isNumeric(String fieldValue){
		 String pattern = "^[-]?\\d*\\.?\\d*$";
	     // Create a Pattern object
	     Pattern r = Pattern.compile(pattern);
	     // Now create matcher object.
	     Matcher m = r.matcher(fieldValue);
	     return m.matches();
	}
	
	public static boolean isValidEmailPattern(String fieldValue){
		 String pattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
	     // Create a Pattern object
	     Pattern r = Pattern.compile(pattern);
	     // Now create matcher object.
	     Matcher m = r.matcher(fieldValue);
	     return m.matches();
	}
	public static void main(String[] main){
		//System.out.println(isNumeric("9840777135"));
		//boolean msg=isValidNotificationMessage("Respected DPH Department , SHDRC Helpdesk has initiated DPH Load as per new mapping. You may experience inconsistencies during this process. ETA for this activity will remain as - 22/Aug/2016.");
		//System.out.println(msg);
	}
	
	public static boolean isValidNotificationMessage(String fieldValue){
		 String pattern = "^[A-Za-z0-9-@!.',/:;=&? \\+]+(\\[_A-Za-z0-9]+)*";
	     // Create a Pattern object
	     Pattern r = Pattern.compile(pattern);
	     // Now create matcher object.
	     Matcher m = r.matcher(fieldValue);
	     return m.matches();
	}

}
