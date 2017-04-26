package gov.shdrc.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Util {
	private static final String DDMMYYYY = "dd-MM-yyyy";
	private static final String YYYYMMDD = "yyyy-MM-dd";
	private static DecimalFormat df2 = new DecimalFormat(".##");
	
	public static ApplicationContext ctx = null;
	
	public static int YEARRANGE=41;
	public static int STARTYEAR=2010;
	
	public static List<Integer> yearList =null;
	public static List<Integer> dashboardYearList =null;
	public static List<CommonStringList> monthsList =null;
	public static List<CommonStringList> freuencyList =null;
	public static List<CommonStringList> quarterList =null;
	public static List<CommonStringList> weeksList =null;
	public static Map<String, Integer> monthsMap=null;
	public static Map<String, Integer> monthsShortNamesMap=null;
	
	static{
		yearList  = new ArrayList<Integer>();
		Calendar cal=Calendar.getInstance();
		int startYear=2010;
		int currentYear=cal.get(Calendar.YEAR);
		int yearRange=currentYear-startYear+1;
		for(int i=0;i<yearRange;i++){
			yearList.add(startYear);
			startYear++;
		}
		
		dashboardYearList  = new ArrayList<Integer>();
		int dashboardStartYear=2016;
		int dashboardYearRange=currentYear-dashboardStartYear+1;
		for(int i=0;i<dashboardYearRange;i++){
			dashboardYearList.add(dashboardStartYear);
			dashboardStartYear++;
		}
		
		freuencyList = new ArrayList<CommonStringList>();
		freuencyList.add(new CommonStringList(1,"Daily"));
		freuencyList.add(new CommonStringList(2,"Weekly"));
		freuencyList.add(new CommonStringList(3,"Monthly"));
		freuencyList.add(new CommonStringList(4,"Quarterly"));
		
		monthsList = new ArrayList<CommonStringList>();
	    String[] months = new DateFormatSymbols().getMonths();
	    for (int i = 0; i < months.length-1; i++) {
	      monthsList .add(new CommonStringList(i, months[i]));
	    }
	    
	    quarterList = new ArrayList<CommonStringList>();
	    quarterList.add(new CommonStringList(1,"1"));
	    quarterList.add(new CommonStringList(2,"2"));
	    quarterList.add(new CommonStringList(3,"3"));
	    quarterList.add(new CommonStringList(4,"4"));
	    
	    weeksList = new ArrayList<CommonStringList>();
	    weeksList.add(new CommonStringList(1,"1"));
	    weeksList.add(new CommonStringList(2,"2"));
	    weeksList.add(new CommonStringList(3,"3"));
	    weeksList.add(new CommonStringList(4,"4"));
	    weeksList.add(new CommonStringList(5,"5"));
	    
	    monthsMap = new HashMap<String, Integer>();
		monthsMap.put("January",0); 
		monthsMap.put("February",1);
		monthsMap.put("March",2);
		monthsMap.put("April",3);
		monthsMap.put("May",4);
		monthsMap.put("June",5);
		monthsMap.put("July",6);
		monthsMap.put("August",7);
		monthsMap.put("September",8);
		monthsMap.put("October",9);
		monthsMap.put("November",10);
		monthsMap.put("December",11);
		
		monthsShortNamesMap=new HashMap<String, Integer>();
		monthsShortNamesMap.put("Jan",0); 
		monthsShortNamesMap.put("Feb",1);
		monthsShortNamesMap.put("Mar",2);
		monthsShortNamesMap.put("Apr",3);
		monthsShortNamesMap.put("May",4);
		monthsShortNamesMap.put("Jun",5);
		monthsShortNamesMap.put("Jul",6);
		monthsShortNamesMap.put("Aug",7);
		monthsShortNamesMap.put("Sep",8);
		monthsShortNamesMap.put("Oct",9);
		monthsShortNamesMap.put("Nov",10);
		monthsShortNamesMap.put("Dec",11);
	}

	public static Object getBean(String name) {
		if (ctx == null) {
			String[] confLoc = { "config/application-context.xml"};
			ctx = new ClassPathXmlApplicationContext(confLoc);
		}

		return ctx.getBean(name);
	}
	
	public static Date getUtilDate(String StrDate) {
		Date  date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DDMMYYYY);
			date = formatter.parse(StrDate);
	 
		} catch (ParseException e) {
		}
		return date;
	}
	public static Date convertToUtilDate(String StrDate) {
		Date  date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDD);
			date = formatter.parse(StrDate);
	 
		} catch (ParseException e) {
		}
		return date;
	}
	public static String getStrDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DDMMYYYY);
		return formatter.format(date);
	}
	public static int getCurrentHours() {
		int hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		return hour;
	}
	
	public static String convertSqlDateToStrDate(String StrDate) {
		Date  date =convertToUtilDate(StrDate);
		String date1 = getStrDate(date);
		return date1;
	}
	
	public static boolean isNullOrBlank(Object obj) {
		return (obj==null || obj.toString().trim().length()==0);
	}
	public static boolean isNotNull(Object obj) {
		return (obj!=null && obj.toString().trim().length()>0);
	}
	public static void main(String arg[]){
		/*Date date =getUtilDate("11-09-2014");
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		System.out.println(date);
		System.out.println(sqlDate);
		System.out.println(getStrDate(sqlDate));*/
		
		/*String ip="2015-06-03";
		Date date=convertToUtilDate(ip);
		System.out.println(getStrDate(date));*/
	}
	public static JSONArray getColumnHearders(){
		JSONArray jsonArray =null;
		try {
			jsonArray =new JSONArray();
			JSONObject row1= new JSONObject();
			row1.put("indicatorname","Indicator Name");
			row1.put("generaltype","General Type");
			row1.put("generalname","General Name");
			row1.put("indicatorvalue","Indicator Value");
			jsonArray.put(row1);
			
		}catch(JSONException json){
			
		}
		return jsonArray;
	}
	
	public static JSONArray getDemographicColumnHearders(){
		JSONArray jsonArray =null;
		try {
			jsonArray =new JSONArray();
			JSONObject row1= new JSONObject();
			row1.put("gender","Gender");
			row1.put("agegroup","Age Group");
			row1.put("social","Social");
			row1.put("indicatorvalue","Indicator Value");
			jsonArray.put(row1);
			
		}catch(JSONException json){
			
		}
		return jsonArray;
	}
	
	public static JSONArray getMonthlyDataStatusHearders(){
		JSONArray jsonArray =null;
		try {
			jsonArray =new JSONArray();
			JSONObject row1= new JSONObject();
			row1.put("directorateList","Directorate Name");
			row1.put("month1","Jan");
			row1.put("month2","Feb");
			row1.put("month3","Mar");
			row1.put("month4","Apr");
			row1.put("month5","May");
			row1.put("month6","Jun");
			row1.put("month7","Jul");
			row1.put("month8","Aug");
			row1.put("month9","Sep");
			row1.put("month10","Oct");
			row1.put("month11","Nov");
			row1.put("month12","Dec");
			jsonArray.put(row1);
			
		}catch(JSONException json){
			
		}
		return jsonArray;
	}
	
	public static JSONArray getDailyDataStatusHearders(){
		JSONArray jsonArray =null;
		try {
			jsonArray =new JSONArray();
			JSONObject row1= new JSONObject();
			row1.put("directorateList","Directorate Name");
			row1.put("day1","1");
			row1.put("day2","2");
			row1.put("day3","3");
			row1.put("day4","4");
			row1.put("day5","5");
			row1.put("day6","6");
			row1.put("day7","7");
			row1.put("day8","8");
			row1.put("day9","9");
			row1.put("day10","10");
			row1.put("day11","11");
			row1.put("day12","12");
			row1.put("day13","13");
			row1.put("day14","14");
			row1.put("day15","15");
			row1.put("day16","16");
			row1.put("day17","17");
			row1.put("day18","18");
			row1.put("day19","19");
			row1.put("day20","20");
			row1.put("day21","21");
			row1.put("day22","22");
			row1.put("day23","23");
			row1.put("day24","24");
			row1.put("day25","25");
			row1.put("day26","26");
			row1.put("day27","27");
			row1.put("day28","28");
			row1.put("day29","29");
			row1.put("day30","30");
			row1.put("day31","31");
			jsonArray.put(row1);
			
		}catch(JSONException json){
			
		}
		return jsonArray;
	}
	
	public static JSONArray getWeeklyDataStatusHearders(){
		JSONArray jsonArray =null;
		try {
			jsonArray =new JSONArray();
			JSONObject row1= new JSONObject();
			row1.put("directorateList","Directorate Name");
			row1.put("week1","Week1");
			row1.put("week2","Week2");
			row1.put("week3","Week3");
			row1.put("week4","Week4");
			jsonArray.put(row1);
			
		}catch(JSONException json){
			
		}
		return jsonArray;
	}
	
	public static JSONArray getQuarterlyDataStatusHearders(){
		JSONArray jsonArray =null;
		try {
			jsonArray =new JSONArray();
			JSONObject row1= new JSONObject();
			row1.put("directorateList","Directorate Name");
			row1.put("quarter1","Quarter1");
			row1.put("quarter2","Quarter2");
			row1.put("quarter3","Quarter3");
			row1.put("quarter4","Quarter4");
			jsonArray.put(row1);
			
		}catch(JSONException json){
			
		}
		return jsonArray;
	}
	
	public static String getFullRole(String abbrRole){
        String role=null;
        switch (abbrRole) {
            case ShdrcConstants.Role.DMS		:	role = "Directorate of Medical and Rural Health Services";
                     	 							break;
            case ShdrcConstants.Role.DPH		:	role = "Directorate of Public Health and Preventive Medicine";
            			 							break;
            case ShdrcConstants.Role.TNMSC		:	role = "Tamil Nadu Medical Services Corporation";
            			  							break;
            case ShdrcConstants.Role.TANSACS	: 	role = "Tamil Nadu State AIDS Control society";
            			 							break;
            case ShdrcConstants.Role.NRHM		:	role = "National Rural Health Mission";
                     	 							break;
            case ShdrcConstants.Role.RNTCP		: 	role = "Revised National Tuberculosis Control Programme";
                     	 							break;
            case ShdrcConstants.Role.MA			:  	role = "Municipal Administration - Health Wing";
                     								break;
            case ShdrcConstants.Role.DME		:  	role = "Directorate of Medical Education";
													break;
            case ShdrcConstants.Role.DRMGR		:  	role = "TNDRMGR Medical University, Chennai";
													break;
            case ShdrcConstants.Role.DFW		:  	role = "Directorate of Family welfare";
													break;
            case ShdrcConstants.Role.SHTO		:  	role = "State Health Transport Department";
													break;
            case ShdrcConstants.Role.COC		:  	role = "Corporation of Chennai (Health Wing)";
													break;
            case ShdrcConstants.Role.SBCS		:  	role = "State Blindness Control Society";
													break;
            case ShdrcConstants.Role.DCA		:  	role = "Drug Control Authority";
													break;
            case ShdrcConstants.Role.MRB		:  	role = "Medical Recruitment Board";
													break;
            case ShdrcConstants.Role.DFS		:  	role = "Directorate of Food Safety";
													break;
            case ShdrcConstants.Role.CMCHIS		:  	role = "Chief Ministers Comprehensive Health Insurance Scheme";
													break;
            case ShdrcConstants.Role.DIM		:  	role = "Department of Indian Medicine";
													break;
            case ShdrcConstants.Role.NLEP		:  	role = "National Leprosy Eradication Programme";
													break;
            case ShdrcConstants.Role.SBHI		:  	role = "State Bureau of Health Intelligence";
													break;
            case ShdrcConstants.Role.ESI		:  	role = "Employee State Insurance";
													break;
            case ShdrcConstants.Role.PVTSEC		:  	role = "Private Sector";
													break;										
            case ShdrcConstants.Role.AMB		:  	role = "108 Ambulance";
													break;		
        }
		return role;
	}
	
	public static String getDirectorateShortName(Integer directorateId){
        String directorateShortName=null;
        switch (directorateId) {
        case ShdrcConstants.DirectorateId.DMS		:	directorateShortName="DMS";
                 	 									break;
        case ShdrcConstants.DirectorateId.DPH		:	directorateShortName="DPH";
														break;
        case ShdrcConstants.DirectorateId.TNMSC		:	directorateShortName="TNMSC";
														break;
		case ShdrcConstants.DirectorateId.TANSACS		: 	directorateShortName="TANSACS";
														break;
		case ShdrcConstants.DirectorateId.NRHM		:	directorateShortName="NRHM";
														break;
		case ShdrcConstants.DirectorateId.RNTCP		: 	directorateShortName="RNTCP";
														break;
		case ShdrcConstants.DirectorateId.MA		:  	directorateShortName="MA";
														break;
		case ShdrcConstants.DirectorateId.DME		:  	directorateShortName="DME";
														break;
		case ShdrcConstants.DirectorateId.DRMGR		:  	directorateShortName="DRMGR";
														break;
		case ShdrcConstants.DirectorateId.DFW		:  	directorateShortName="DFW";
														break;
		case ShdrcConstants.DirectorateId.SHTO		:  	directorateShortName="SHTO";
														break;
		case ShdrcConstants.DirectorateId.COC		:  	directorateShortName="COC";
														break;
		case ShdrcConstants.DirectorateId.SBCS		:  	directorateShortName="SBCS";
														break;
		case ShdrcConstants.DirectorateId.DCA		:  	directorateShortName="DCA";
														break;
		case ShdrcConstants.DirectorateId.MRB		:  	directorateShortName="MRB";
														break;
		case ShdrcConstants.DirectorateId.DFS		:  	directorateShortName="DFS";
														break;
		case ShdrcConstants.DirectorateId.CMCHIS	:  	directorateShortName="CMCHIS";
														break;
		case ShdrcConstants.DirectorateId.DIM		:  	directorateShortName="DIM";
														break;
		case ShdrcConstants.DirectorateId.NLEP		:  	directorateShortName="NLEP";
														break;
		case ShdrcConstants.DirectorateId.SBHI		:  	directorateShortName="SBHI";
														break;
		case ShdrcConstants.DirectorateId.ESI		:  	directorateShortName="ESI";
														break;
		case ShdrcConstants.DirectorateId.PVTSEC	:  	directorateShortName="PVTSEC";
														break;	
		case ShdrcConstants.DirectorateId.AMB		:  	directorateShortName="AMB";
														break;	
        }  
		return directorateShortName;
	}
	
	public static String getMonthByIndex(int index){
		  String months[] = {"January", "February", "March", "April",
                  "May", "June", "July", "August", "September",
                  "October", "November", "December"};
		  return months[index];
	}
	public static int getMonthIndexByName(String monthName){
		  return monthsMap.get(monthName);
	}
	public static int getMonthIndexByShortName(String monthName){
		  return monthsShortNamesMap.get(monthName);
	}
	public static String encodeJSONArray(String jsonArray){
		 String encodedJsonArray=null;
		try {
			encodedJsonArray = URLEncoder.encode(jsonArray, "UTF-8")   
					   .replaceAll("\\%28", "(")                          
					   .replaceAll("\\%29", ")")   		
					   .replaceAll("\\+", "%20")                          
					   .replaceAll("\\%27", "'")   			   
					   .replaceAll("\\%21", "!")
					   .replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return encodedJsonArray;
		
	}
	public static String getFrequencyShortName(String frequncy){
		String frequencyShortName=null;
		  switch (frequncy) {
	          case ShdrcConstants.DAILY		:	frequencyShortName = "D";
	                   	 						break;
	          case ShdrcConstants.WEEKLY	:	frequencyShortName = "W";
	          			 						break;
	          case ShdrcConstants.MONTHLY	:	frequencyShortName = "M";
												break;
	          case ShdrcConstants.QUARTERLY	:	frequencyShortName = "Q";
												break;
	          case ShdrcConstants.YEARLY	:	frequencyShortName = "Y";
												break;									
		  }
		return frequencyShortName;
	}
	
	public static String getRoleShortName(String role){
		int indexT1 = role.lastIndexOf("T1");
		int indexT2 = role.lastIndexOf("T2");
		int indexT3 = role.lastIndexOf("T3");
		int indexT4 = role.lastIndexOf("T4");
		String roleShortName = null;
		if(indexT1!=-1){
			roleShortName=role.substring(0, indexT1).trim();
		}else if(indexT2!=-1){
			roleShortName=role.substring(0, indexT2).trim();
		}else if(indexT3!=-1){
			roleShortName=role.substring(0, indexT3).trim();
		}else if(indexT4!=-1){
			roleShortName=role.substring(0, indexT4).trim();
		}
		return roleShortName;
	}
	
	public static String getDataEntryLevel(Integer districtId,String institutionType,Integer institutionId){
		String indicatorHierarchy=null;
		if(districtId!=null
				&& districtId==ShdrcConstants.SELECTALLID)
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELSTATE;
		else if(districtId!=null 
				&& districtId!=ShdrcConstants.SELECTALLID
				&& institutionType!=null 
				&& ShdrcConstants.SELECTALLVALUE.equals(institutionType)
				&& institutionId!=null
				&& institutionId==ShdrcConstants.SELECTALLID)
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELDISTRICT;
		else if(institutionType!=null 
				&& !ShdrcConstants.SELECTALLVALUE.equals(institutionType)
				&& institutionId!=null
				&& institutionId==ShdrcConstants.SELECTALLID)
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELINSTITUTIONTYPE;
		else
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELINSTITUTION;
		
		return indicatorHierarchy;
	}
	
	public static String getFrequencyFullName(String frequncy){
		String frequencyFullName=null;
		  switch (frequncy) {
	          case ShdrcConstants.DAILYSHORTNAME		:	frequencyFullName = ShdrcConstants.DAILY;
	                   	 									break;
	          case ShdrcConstants.WEEKLYSHORTNAME		:	frequencyFullName = ShdrcConstants.WEEKLY;
	          			 									break;
	          case ShdrcConstants.MONTHLYSHORTNAME		:	frequencyFullName = ShdrcConstants.MONTHLY;
															break;
	          case ShdrcConstants.QUARTERLYSHORTNAME	:	frequencyFullName = ShdrcConstants.QUARTERLY;
															break;
	          case ShdrcConstants.YEARLYSHORTNAME		:	frequencyFullName = ShdrcConstants.YEARLY;
															break;									
		  }
		return frequencyFullName;
	}
	
	public static String getIndicatorFactCalc(String fact){
		String IndicatorFactCalc=null;
		  switch (fact) {
	          case ShdrcConstants.NORMALFACT		:	IndicatorFactCalc = "Normal";
	                   	 								break;
	          case ShdrcConstants.DASHBOARDFACT		:	IndicatorFactCalc = "Dashboard";
	          			 								break;
	          case ShdrcConstants.CALCULATION		:	IndicatorFactCalc = "Calculation";
														break;				
		  }
		return IndicatorFactCalc;
	}
	
	public static String getIndicatorDataEntry(String dataEntry){
		String IndicatorDataEntry=null;
		  switch (dataEntry) {
	          case ShdrcConstants.DATAENTRY		:	IndicatorDataEntry = "Yes";
	                   	 							break;
	          case ShdrcConstants.NOTDATAENTRY	:	IndicatorDataEntry = "No";
	          			 							break;			
		  }
		return IndicatorDataEntry;
	}
	
	public static String getIndicatorHierarchy(String hierarchy){
		String IndicatorHierarchy=null;
		  switch (hierarchy) {
	          case ShdrcConstants.DATAENTRYLEVELSTATE			:	IndicatorHierarchy = ShdrcConstants.STATE;
	                   	 											break;
	          case ShdrcConstants.DATAENTRYLEVELDISTRICT		:	IndicatorHierarchy = ShdrcConstants.DISTRICT;
	          			 											break;			
	          case ShdrcConstants.DATAENTRYLEVELINSTITUTION		:	IndicatorHierarchy = ShdrcConstants.INSTITUTION;
																	break;
	          case ShdrcConstants.DATAENTRYLEVELINSTITUTIONTYPE	:	IndicatorHierarchy = ShdrcConstants.INSTITUTIONTYPE;
																	break;			
	          case ShdrcConstants.DATAENTRYLEVELBLOCK			:	IndicatorHierarchy = ShdrcConstants.BLOCK;
	          														break;
	          case ShdrcConstants.DATAENTRYLEVELLAB				:	IndicatorHierarchy = ShdrcConstants.LAB;
	          														break;		
	          case ShdrcConstants.DATAENTRYLEVELZONE			:	IndicatorHierarchy = ShdrcConstants.ZONE;
																	break;
	          case ShdrcConstants.DATAENTRYLEVELRANGE			:	IndicatorHierarchy = ShdrcConstants.RANGE;
																	break;		
	          case ShdrcConstants.DATAENTRYLEVELDISTRICTWORKSHOP:	IndicatorHierarchy = ShdrcConstants.DISTRICTWORKSHOP;
																	break;
	          case ShdrcConstants.DATAENTRYLEVELREGIONALWORKSHOP:	IndicatorHierarchy = ShdrcConstants.REGIONALWORKSHOP;
																	break;			
	          case ShdrcConstants.DATAENTRYLEVELMOBILEWORKSHOP	:	IndicatorHierarchy = ShdrcConstants.MOBILEWORKSHOP;
																	break;
	          case ShdrcConstants.DATAENTRYLEVELWAREHOUSE		:	IndicatorHierarchy = ShdrcConstants.WAREHOUSE;
																	break;			
	          case ShdrcConstants.DATAENTRYLEVELPROGRAM			:	IndicatorHierarchy = ShdrcConstants.PROGRAM;
																	break;
	          case ShdrcConstants.DATAENTRYLEVELREGIONCORPORATION:	IndicatorHierarchy = ShdrcConstants.REGIONCORPORATION;
																	break;		
	          case ShdrcConstants.DATAENTRYLEVELHOSPITAL		:	IndicatorHierarchy = ShdrcConstants.HOSPITAL;
																	break;
	          case ShdrcConstants.DATAENTRYLEVELREGION			:	IndicatorHierarchy = ShdrcConstants.REGION;
																	break;	
	          case ShdrcConstants.DATAENTRYLEVELMUNICIPALITY	:	IndicatorHierarchy = ShdrcConstants.MUNICIPALITY;
																	break;	
		  }
		return IndicatorHierarchy;
	}
	
	public static String getIndHierarchyShortName(String hierarchy){
		String IndHierarchyShortName=null;
		  switch (hierarchy) {
	          case ShdrcConstants.STATE				:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELSTATE;
	                   	 								break;
	          case ShdrcConstants.DISTRICT			:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELDISTRICT;
	          			 								break;			
	          case ShdrcConstants.INSTITUTION		:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELINSTITUTION;
														break;
	          case ShdrcConstants.INSTITUTIONTYPE	:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELINSTITUTIONTYPE;
														break;			
	          case ShdrcConstants.BLOCK				:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELBLOCK;
	          											break;
	          case ShdrcConstants.LAB				:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELLAB;
	          											break;		
	          case ShdrcConstants.ZONE				:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELZONE;
														break;
	          case ShdrcConstants.RANGE				:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELRANGE;
														break;	
	          case ShdrcConstants.DISTRICTWORKSHOP	:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELDISTRICTWORKSHOP;
														break;
	          case ShdrcConstants.WAREHOUSE			:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELWAREHOUSE;
														break;			
	          case ShdrcConstants.PROGRAM			:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELPROGRAM;
														break;
	          case ShdrcConstants.REGIONCORPORATION	:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELREGIONCORPORATION;
														break;			
	          case ShdrcConstants.HOSPITAL			:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELHOSPITAL;
														break;
	          case ShdrcConstants.MOBILEWORKSHOP	:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELMOBILEWORKSHOP;
														break;		
	          case ShdrcConstants.REGION			:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELREGION;
														break;
	          case ShdrcConstants.REGIONALWORKSHOP	:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELREGIONALWORKSHOP;
														break;			
	          case ShdrcConstants.MUNICIPALITY		:	IndHierarchyShortName = ShdrcConstants.DATAENTRYLEVELMUNICIPALITY;
				break;											
		  }
		return IndHierarchyShortName;
	}
	public static Long roundIndValue(Double indValue){
		return (indValue==null?null:Math.round(indValue));
	}
	public static boolean isPercentageIndicator(String indName){
		if("Percentage of Deaths among Accident and Emergency Cases".equalsIgnoreCase(indName)
				|| "Percentage of Assisted Deliveries".equalsIgnoreCase(indName)
				|| "Excess of Average Length of Stay".equalsIgnoreCase(indName)
				|| "Percentage of Cesarian Deliveries".equalsIgnoreCase(indName)
				|| "Percentage of High Risk Pregnancies among ANCs registered".equalsIgnoreCase(indName)
				|| "Hospital Death Rate".equalsIgnoreCase(indName)
				|| "Percentage of Neonatal Deaths among Live Births".equalsIgnoreCase(indName)
				|| "Percentage of Referral among inpatients treated".equalsIgnoreCase(indName)
				|| "Staff Deficiency".equalsIgnoreCase(indName)
				|| "Percentage of Beds Vacant / Underutilized Bed Occupancy".equalsIgnoreCase(indName)
				|| "Infant Mortality Rate".equalsIgnoreCase(indName)
				|| "Maternal Mortality Rate".equalsIgnoreCase(indName)){
			return true;
		}
		
		return false;
	}
	
	public static String formatTwoDecimal(Double indValue){
		return (indValue==null?null:df2.format(indValue)); 
	}
	
	public static int generateSerialNo(int serial,String previousParam,String currentParam){
		if(previousParam==null){
			previousParam = currentParam;						
		}
		if(!(currentParam.equalsIgnoreCase(previousParam))){
			serial++;
			previousParam = currentParam;		
		}
	return serial;
	}
}
