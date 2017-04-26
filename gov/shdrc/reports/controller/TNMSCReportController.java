package gov.shdrc.reports.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.reports.service.ICommonReportManager;
import gov.shdrc.reports.service.ITNMSCReportManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;



@Controller
public class TNMSCReportController {
	@Autowired(required=true)
	ITNMSCReportManager tnmscReportManager;
	@Autowired(required=true)
	ICommonReportManager commonReportManager;
	
	@RequestMapping(value="/tnmscDashboardZone", method=RequestMethod.GET)
	public ModelAndView onLoadDashboardZone(HttpServletRequest request) throws JSONException, SHDRCException{	
		ModelAndView modelAndView=new ModelAndView();
		JSONArray jsonArrayFirstTable=null;
		JSONArray jsonArraySecondTable=null;
		String numberofscreens=null;
		JSONObject jsonfirst=null;
		JSONObject jsonsecond=null;
		try{
			 UserInfo.hasValidReportAccess(ShdrcConstants.Role.TNMSC);
			 int directorateId=Integer.parseInt(request.getParameter("directorateId"));				
			 modelAndView.addObject("directorateId",directorateId);
			 String dashBoardName="PD Account Balance";
			 //CommonStringList commonsList=tnmscReportManager.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
			 int year=2014;/*commonsList.getId();*/
			 String month="Sep";/*commonsList.getName();*/
			 int noOfScreens=2;
			 String loggedUser=UserInfo.getLoggedInUserName();
			 jsonfirst=tnmscReportManager.getPdAccountBalance1(directorateId, dashBoardName, year, month, loggedUser);
			 jsonsecond=tnmscReportManager.getPdAccountBalance2(directorateId, dashBoardName, year, month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 JSONArray headerSecondlist=(JSONArray) jsonsecond.get("headers");
			 jsonArraySecondTable=(JSONArray) jsonsecond.get("jsonArraySecondTable");
			 modelAndView.addObject("headerFirstlist", headerFirstlist);
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("headerSecondlist", headerSecondlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArraySecondTable", jsonArraySecondTable);
			 modelAndView.addObject("headerSecondlist", headerSecondlist);
			 modelAndView.addObject("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("jsonArraySecondTable", jsonArraySecondTable);
			 modelAndView.addObject("yearList", Util.yearList);
			 modelAndView.addObject("monthsList", Util.monthsList);			 
			 modelAndView.addObject("loggedUser",loggedUser);		          
	         modelAndView.addObject("Year", year);
	         modelAndView.addObject("noOfScreens", noOfScreens);
	         modelAndView.addObject("dashBoardName", dashBoardName);
	         modelAndView.addObject("Month",Util.getMonthIndexByShortName(month));
			 modelAndView.setViewName("tnmscDashboardZone");
		}finally{
		}
		 return modelAndView;
	}
	
	@RequestMapping(value="/tnmscReportZone", method=RequestMethod.GET)
	public ModelAndView onLoadReportZone(HttpServletRequest request) throws SHDRCException
	{	
		ModelAndView modelAndView=null;
		String month=null;
		Integer year;
		Integer fromYear;
		String fromMonth=null;
		Integer toYear;
		String toMonth=null;
		String reportName=null;
		Integer directorateId;
		String userName=null;
		List<CommonStringList> monthsList=null;
		List<Integer> yearList=null;
		try{
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.TNMSC);
			modelAndView = new ModelAndView();
			modelAndView.setViewName("tnmscReportZone");
			
			year = Calendar.getInstance().get(Calendar.YEAR);			
			Calendar cal =  Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			fromYear = toYear = year;
			fromMonth = toMonth = month;
			reportName="Passbook Utilization";
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			userName=UserInfo.getLoggedInUserName();
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("monthsList", monthsList);
			modelAndView.addObject("directorateId", directorateId);
			modelAndView.addObject("month", monthName);
			modelAndView.addObject("year", year);
			modelAndView.addObject("reportName", reportName);
			modelAndView.addObject("reportZoneData", tnmscReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName));
		}finally{
			monthsList=null;	
			yearList=null;
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/tnmscReportZoneSearchChange", method=RequestMethod.POST)
	public @ResponseBody void getcorrespondingMonthData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		String reportName=request.getParameter("reportName");
		Integer fromYear=(Util.isNotNull(request.getParameter("fromYear"))?Integer.parseInt(request.getParameter("fromYear")):null);
		String fromMonth=request.getParameter("fromMonth");
		Integer toYear=(Util.isNotNull(request.getParameter("toYear"))?Integer.parseInt(request.getParameter("toYear")):null);
		String toMonth=request.getParameter("toMonth");
		if(fromMonth!=null && fromMonth.length()>3){
			fromMonth=fromMonth.substring(0,3);
		}	
		if(toMonth!=null && toMonth.length()>3){
			toMonth=toMonth.substring(0,3);
		}		
		Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
		String userName=UserInfo.getLoggedInUserName();
		JSONArray monthReportData=null;
		monthReportData =  tnmscReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName);
		out.println(monthReportData);
	}
	
	@RequestMapping(value="/tnmscAnalysisZone", method=RequestMethod.GET)
	public ModelAndView onLoadAnalysisZone(HttpServletRequest request) throws SHDRCException{
		ModelAndView modelAndView=null;
		String analysisReport1=null;
		String analysisReport2=null;
		String analysisReport3=null;
		Calendar cal=null;
		String month=null;
		Integer year;
		Integer directorateId;
		String userName=null;
		List<CommonStringList> monthsList=null;
		List<Integer> yearList=null;
		try{
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.TNMSC);
			modelAndView=new ModelAndView();
			modelAndView.setViewName("tnmscAnalysisZone");
			
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("monthsList", monthsList);
		
			analysisReport1 = "Stock Deficit";
			analysisReport2 = "Blacklisted Drugs";
			analysisReport3 = "Supplier Wise Purchase Details";
			year = Calendar.getInstance().get(Calendar.YEAR);	
			cal=Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			userName=UserInfo.getLoggedInUserName();	
			
			JSONArray analysisZoneReport1Data= tnmscReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName);
			JSONArray analysisZoneReport2Data= tnmscReportManager.getAnalysisZoneData(directorateId,analysisReport2,year,month,userName);
			JSONArray analysisZoneReport3Data= tnmscReportManager.getAnalysisZoneData(directorateId,analysisReport3,year,month,userName);
			
			modelAndView.addObject("directorateId", request.getParameter("directorateId"));
			modelAndView.addObject("analysisZoneReport1Data", analysisZoneReport1Data);
			modelAndView.addObject("analysisZoneReport2Data", analysisZoneReport2Data);
			modelAndView.addObject("analysisZoneReport3Data", analysisZoneReport3Data);
			modelAndView.addObject("year", year);
			modelAndView.addObject("month", monthName);
		}finally{
			cal=null;
			analysisReport1=null;
			analysisReport2=null;
			analysisReport3=null;
			month=null;
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/tnmscAnalysisZoneSearchChange", method=RequestMethod.POST)
	public @ResponseBody void getAnalysisZoneData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONObject data=null;
		Integer year=(Util.isNotNull(request.getParameter("selectedYear"))?Integer.parseInt(request.getParameter("selectedYear")):null);
		String month=request.getParameter("monthName");
		if(month!=null && month.length()>3){
			month=month.substring(0,3);
		}		
		Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
		String userName=UserInfo.getLoggedInUserName();
		data=new JSONObject();
		String analysisReport1=null;
		String analysisReport2=null;
		String analysisReport3=null;
		analysisReport1 = "Stock Deficit";
		analysisReport2 = "Blacklisted Drugs";
		analysisReport3 = "Supplier Wise Purchase Details";
		
		JSONArray analysisZoneReport1Data= tnmscReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName);
		JSONArray analysisZoneReport2Data= tnmscReportManager.getAnalysisZoneData(directorateId,analysisReport2,year,month,userName);
		JSONArray analysisZoneReport3Data= tnmscReportManager.getAnalysisZoneData(directorateId,analysisReport3,year,month,userName);
		data.put("analysisZoneReport1Data", analysisZoneReport1Data);
        data.put("analysisZoneReport2Data", analysisZoneReport2Data);
        data.put("analysisZoneReport3Data", analysisZoneReport3Data);
		out.println(data);
	}
	
	@RequestMapping(value="/tnmscIndicatorZone", method=RequestMethod.GET)
	public ModelAndView tnmscOnLoadIndicatorZone(HttpServletRequest request) throws SHDRCException
	{	
		ModelAndView modelAndView=null;
		int year=0;
		String month=null;
        String userName=null;
        int directorateId=0;
        String reportName= null;
		try{
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.TNMSC);
			modelAndView = new ModelAndView();
			modelAndView.setViewName("tnmscIndicatorZone");
			directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			modelAndView.addObject("directorateId", directorateId);
			userName=UserInfo.getLoggedInUserName();
			reportName="Human Resource";
			CommonStringList commonsList=commonReportManager.getIndicatorMaxMonthAndYear(directorateId,reportName);
			year=commonsList.getId();
			month=commonsList.getName().substring(0, 3);
			modelAndView.addObject("month", commonsList.getName());
			modelAndView.addObject("year", year);
			modelAndView.addObject("yearList", Util.yearList);
			modelAndView.addObject("monthsList", Util.monthsList);
			JSONArray tnmscPvtData=tnmscReportManager.getTNMSCPvtData(directorateId,reportName,year,month,userName);
			modelAndView.addObject("reportName", reportName);
			modelAndView.addObject("tnmscPvtData", tnmscPvtData);
		}finally{
			userName=null;
			reportName= null;
	        month=null;
		}
		return modelAndView;
	}   
	
	@RequestMapping(value="/tnmscOnSearch", method=RequestMethod.POST)
	public @ResponseBody void tnmscGetIndicatorData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
        int directorateId=0;
        String userName=null;
        PrintWriter out=null;
        JSONObject data=null;
        String reportName=null;
        String month=null;
        int year=0;
        try{
	        data=new JSONObject();
	        out=response.getWriter();
	        directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			userName=UserInfo.getLoggedInUserName();
	        reportName = request.getParameter("reportName");
	        month = request.getParameter("month").substring(0, 3);
	        year = Integer.parseInt(request.getParameter("year"));
	        JSONArray tnmscPvtData=tnmscReportManager.getTNMSCPvtData(directorateId,reportName,year,month,userName);
	        data.put("tnmscPvtData", tnmscPvtData);
	        data.put("reportName", reportName);
	        out.println(data);
        }finally{
			userName=null;
			reportName= null;
	        month=null;
		}
	}
	
	/*@RequestMapping(value="/tnmscReportForDistrict", method=RequestMethod.POST)
	public  @ResponseBody String ShowDistrictReport(HttpServletRequest request){		
		  int directorateID=Integer.parseInt(request.getParameter("DirectorateID"));
		  String indicatorCategory=request.getParameter("IndFrequency");
		  int year=Integer.parseInt(request.getParameter("Year"));
		  int month=Integer.parseInt(request.getParameter("Month"));
		  String indMonth=Util.getMonthByIndex(month);
		  indMonth=new StringBuilder(indMonth).substring(0,3);
		  String loggedUser=UserInfo.getLoggedInUserName();
		  
		  String indicatorName=request.getParameter("SelectedIndicatorName");		  
	      JSONObject object=new JSONObject();
	      try {
	    	  JSONArray jsnArray=tnmscReportManager.getDistrictwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName, year, indMonth, loggedUser);
	    	  request.setAttribute("indicatorDetailsforDistrict", jsnArray);
		      object.put("indicatorDetailsforDistrict",jsnArray);
	      }finally{}
	      return object.toString();
	}
	
	@RequestMapping(value="/tnmscReportForInstitution", method=RequestMethod.POST)
	public  @ResponseBody String ShowInstitutionReport(HttpServletRequest request){	
		  int directorateID=Integer.parseInt(request.getParameter("DirectorateID"));
		  String indicatorCategory=request.getParameter("IndFrequency");
		  int year=Integer.parseInt(request.getParameter("Year"));
		  int month=Integer.parseInt(request.getParameter("Month"));
		  String indMonth=Util.getMonthByIndex(month);
		  indMonth=new StringBuilder(indMonth).substring(0,3);
		  String loggedUser=UserInfo.getLoggedInUserName();
		  String districtName=request.getParameter("Disrtict");	
		  String indicatorName=request.getParameter("Indicator");	
	      JSONObject object=new JSONObject();
	      try {
	    	  JSONArray jsnArray=tnmscReportManager.getInstitutionwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName,districtName, year, indMonth, loggedUser);
	    	  object.put("indicatorDetailsforDistrict",jsnArray);
	      }finally{}
	      return object.toString();
	}    */
	
	@RequestMapping(value="/tnmscSearchIndicatorList", method=RequestMethod.POST)
	public ModelAndView searchIndicatorListByFilter(HttpServletRequest request,HttpServletResponse response) throws JSONException{	    		
		ModelAndView modelAndView=new ModelAndView();
		JSONArray jsonArrayFirstTable=null;
		JSONArray jsonArraySecondTable=null;
		JSONArray jsonArrayThirdTable=null;
		String numberofscreens=null;
		JSONObject jsonfirst=null;
		JSONObject jsonsecond=null;
		JSONObject jsonthird=null;
		String dashBoardName=request.getParameter("dashBoardName");		
		int noOfScreens=getNumberofScreens(dashBoardName);		
		int Year=Integer.parseInt(request.getParameter("year"));
		String Month=request.getParameter("month");
		Month=Util.getMonthByIndex(Integer.parseInt(Month));
		int directorateId=3;
		Month=new StringBuilder(Month).substring(0, 3);
		String loggedUser=UserInfo.getLoggedInUserName();		
		modelAndView.addObject("noOfScreens", noOfScreens);
		modelAndView.addObject("dashBoardName", dashBoardName);
		modelAndView.addObject("Year", Year);
		modelAndView.addObject("Month", request.getParameter("month"));	
		modelAndView.addObject("yearList", Util.yearList);
		modelAndView.addObject("monthsList", Util.monthsList);
		modelAndView.addObject("directorateId",directorateId);	
		modelAndView.addObject("loggedUser",loggedUser);
		if(dashBoardName.equalsIgnoreCase("PD Account Balance")){			
			 jsonfirst=tnmscReportManager.getPdAccountBalance1(directorateId, dashBoardName, Year, Month, loggedUser);
			 jsonsecond=tnmscReportManager.getPdAccountBalance2(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 JSONArray headerSecondlist=(JSONArray) jsonsecond.get("headers");
			 jsonArraySecondTable=(JSONArray) jsonsecond.get("jsonArraySecondTable");
			 modelAndView.addObject("headerFirstlist", headerFirstlist);
			 modelAndView.addObject("headerSecondlist", headerSecondlist);
			 modelAndView.addObject("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("jsonArraySecondTable", jsonArraySecondTable);
		}else if(dashBoardName.equalsIgnoreCase("No. of drug items not finalized in current year tender")){
			 jsonfirst=tnmscReportManager.getNoofdrugsnotfinalizedcurrentyeartrend1(directorateId, dashBoardName, Year, Month, loggedUser);
			 jsonsecond=tnmscReportManager.getNoofdrugsnotfinalizedcurrentyeartrend2(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 JSONArray headerSecondlist=(JSONArray) jsonsecond.get("headers");
			 jsonArraySecondTable=(JSONArray) jsonsecond.get("jsonArraySecondTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("headerSecondlist", headerSecondlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArraySecondTable", jsonArraySecondTable);
			 modelAndView.addObject("headerFirstlist", headerFirstlist);
			 modelAndView.addObject("headerSecondlist", headerSecondlist);
			 modelAndView.addObject("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("jsonArraySecondTable", jsonArraySecondTable);	
		}else if(dashBoardName.equalsIgnoreCase("No. of suppliers of a Drug Items")){
			 jsonfirst=tnmscReportManager.getSupplier_drug_item(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 modelAndView.addObject("headerFirstlist", headerFirstlist);
			 modelAndView.addObject("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);	
		}else if(dashBoardName.equalsIgnoreCase("Blacklisted Products or Firm in current tender(by Performance)")){
			 jsonfirst=tnmscReportManager.getBlacklisted_Products_Firm(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);	
		}else if(dashBoardName.equalsIgnoreCase("QC Result pending (Tablets or Capsules)")){
			jsonfirst=tnmscReportManager.getQC_Result_Tablets(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);	
		}else if(dashBoardName.equalsIgnoreCase("QC Result pending (Fluids)")){
			jsonfirst=tnmscReportManager.getQC_Result_Fluids(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);	
		}else if(dashBoardName.equalsIgnoreCase("Blacklisted Products or Firm in current tender(by Quality)")){
			jsonfirst=tnmscReportManager.getBlacklisted_Products_Quality(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);	
		}else if(dashBoardName.equalsIgnoreCase("ASV,ARV,TT,TCV Stock")){
			jsonfirst=tnmscReportManager.getASV_ARV_TT_TCV_Stock(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);				
		}else if(dashBoardName.equalsIgnoreCase("Hypertensive, Diabeties, IV-Fluid, Cancer, Narcotic Stock")){			
			jsonfirst=tnmscReportManager.getHypertensiveDiabetiesIVFluidCancer(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);	
		}else if(dashBoardName.equalsIgnoreCase("Un-utilized Passbooks")){
			 jsonfirst=tnmscReportManager.getUnutilizedPassbook1(directorateId, dashBoardName, Year, Month, loggedUser);
			 jsonsecond=tnmscReportManager.getUnutilizedPassbook2(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 JSONArray headerSecondlist=(JSONArray) jsonsecond.get("headers");
			 jsonArraySecondTable=(JSONArray) jsonsecond.get("jsonArraySecondTable");
			 modelAndView.addObject("headerFirstlist", headerFirstlist);
			 modelAndView.addObject("headerSecondlist", headerSecondlist);
			 modelAndView.addObject("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("jsonArraySecondTable", jsonArraySecondTable);
			
		}else if(dashBoardName.equalsIgnoreCase("Hospital Stock")){
			jsonfirst=tnmscReportManager.getHospitalStock(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);	
		}else if(dashBoardName.equalsIgnoreCase("Local Purchase")){
			jsonfirst=tnmscReportManager.getLocalPurchase(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);	
		}else if(dashBoardName.equalsIgnoreCase("Online Indent non-performance")){
			 jsonfirst=tnmscReportManager.getOnlineIndentNonperformance(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);	
		}else if(dashBoardName.equalsIgnoreCase("Warranty or AMC Expiry Date for Category A and B 1 Equipments")){
			 jsonfirst=tnmscReportManager.getTnmsc_amc_expiry_categorya_b1_1(directorateId, dashBoardName, Year, Month, loggedUser);
			 jsonsecond=tnmscReportManager.getTnmsc_amc_expiry_categorya_b1_2(directorateId, dashBoardName, Year, Month, loggedUser);
			 jsonthird=tnmscReportManager.getTnmsc_amc_expiry_categorya_b1_3(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 JSONArray headerSecondlist=(JSONArray) jsonsecond.get("headers");
			 jsonArraySecondTable=(JSONArray) jsonsecond.get("jsonArraySecondTable");
			 JSONArray headerThirdlist=(JSONArray) jsonthird.get("headers");
			 jsonArrayThirdTable=(JSONArray) jsonthird.get("jsonArrayThirdTable");
			 modelAndView.addObject("headerFirstlist", headerFirstlist);
			 modelAndView.addObject("headerSecondlist", headerSecondlist);
			 modelAndView.addObject("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("jsonArraySecondTable", jsonArraySecondTable);
			 modelAndView.addObject("headerThirdlist", headerThirdlist);
			 modelAndView.addObject("jsonArrayThirdTable", jsonArrayThirdTable);
		}else if(dashBoardName.equalsIgnoreCase("Empanelled Laboratories for Sample Testing")){
			jsonfirst=tnmscReportManager.getEmpanelledLaboratoriesSampleTesting(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);	
		}else if(dashBoardName.equalsIgnoreCase("Indent for which tenders yet to be floated for Equipment")){
			
		}else if(dashBoardName.equalsIgnoreCase("Indent for which tenders floated not finalized for Equipment")){
			
		}else if(dashBoardName.equalsIgnoreCase("CT or MRI or Lithotripsy Scan machine down-time")){
			
		}else if(dashBoardName.equalsIgnoreCase("Warranty or AMC Expiry Date for other than Category A and B 1 Equipments")){
			
		}else if(dashBoardName.equalsIgnoreCase("Nil ground Stock")){
			 jsonfirst=tnmscReportManager.getNilGroundStock(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);	
			
		}else if(dashBoardName.equalsIgnoreCase("Nil QC-passed Stock")){
			 jsonfirst=tnmscReportManager.getNilQC_passedStock(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);	
			
		}else if(dashBoardName.equalsIgnoreCase("Less Stock")){
			 jsonfirst=tnmscReportManager.getLessStock(directorateId, dashBoardName, Year, Month, loggedUser);
			 JSONArray headerFirstlist=(JSONArray) jsonfirst.get("headers");
			 jsonArrayFirstTable=(JSONArray) jsonfirst.get("jsonArrayFirstTable");
			 request.setAttribute("headerFirstlist", headerFirstlist);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 request.setAttribute("jsonArrayFirstTable", jsonArrayFirstTable);
			 modelAndView.addObject("directorateId",directorateId);				
		}		
		modelAndView.setViewName("tnmscDashboardZone");
    	return modelAndView;
	} 
	
	private int getNumberofScreens(String string){
		int value=0;
		switch (string) {
		case "PD Account Balance": value= 2;
		break;
		case "No. of drug items not finalized in current year tender": value= 2;
		break;
		case "No. of suppliers of a Drug Items": value= 1;
		break;
		case "Blacklisted Products or Firm in current tender(by Performance)": value= 1;
		break;
		case "QC Result pending (Tablets or Capsules)": value= 1;
		break;
		case "QC Result pending (Fluids)": value= 1;
		break;
		case "Blacklisted Products or Firm in current tender(by Quality)": value= 1;
		break;
		case "ASV,ARV,TT,TCV Stock": value= 1;
		break;
		case "Hypertensive, Diabeties, IV-Fluid, Cancer, Narcotic Stock": value= 1;
		break;
		case "Un-utilized Passbooks": value= 2;
		break;
		case "Hospital Stock": value= 1;
		break;
		case "Local Purchase": value= 1;
		break;
		case "Online Indent non-performance": value= 1;
		break;
		case "Warranty or AMC Expiry Date for Category A and B 1 Equipments": value= 3;
		break;
		case "Empanelled Laboratories for Sample Testing": value= 1;
		break;
		case "Indent for which tenders yet to be floated for Equipment": value= 0;
		break;
		case "Indent for which tenders floated not finalized for Equipment": value= 0;
		break;
		case "CT or MRI or Lithotripsy Scan machine down-time": value= 0;
		break;
		case "Warranty or AMC Expiry Date for other than Category A and B 1 Equipments": value= 0;
		break;
		case "Nil ground Stock": value= 1;
		break;
		case "Nil QC-passed Stock": value= 1;
		break;
		case "Less Stock": value= 1;
		break;

		default:
			value=0;
			break;
		}
		return value;
	}
	@RequestMapping(value="/tnmscFreeHandZone", method=RequestMethod.GET)
	public ModelAndView FreeHandZone(HttpServletRequest request) throws SHDRCException{
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
		 ModelAndView modelAndView=new ModelAndView();
		 List indCategory=tnmscReportManager.getFreeHandZoneIndCategory(directorateId);
		 List<CommonStringList> indNamesByCategory=tnmscReportManager.getFreeHandZoneIndNamesByCategory(directorateId,String.valueOf(indCategory.get(0)));
		 List indYearByNameandCate=tnmscReportManager.getIndYearByNameandCategory(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()));
		 JSONArray indJsonArray=tnmscReportManager.getFreeHandZoneData(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()),Integer.parseInt(String.valueOf(indYearByNameandCate.get(0))));
		 modelAndView.addObject("indJsonArray", indJsonArray);
		 modelAndView.addObject("indCategory", indCategory);
		 modelAndView.addObject("indNamesByCategory", indNamesByCategory);
		 modelAndView.addObject("yearList", indYearByNameandCate);
		 modelAndView.addObject("selectedIndicator", String.valueOf(indNamesByCategory.get(0).getId()));
		 request.setAttribute("selectedIndicator", String.valueOf(indNamesByCategory.get(0).getId()));
		 modelAndView.addObject("currentYear", Integer.parseInt(String.valueOf(indYearByNameandCate.get(0))));
		 modelAndView.addObject("directorateId",directorateId);           
		modelAndView.setViewName("esiFreeHandZone");
		
		 return modelAndView;		 
	}
	
	@RequestMapping(value="/tnmscOnCategoryChangeSearch", method=RequestMethod.POST)
	public  @ResponseBody void onCategoryChangeSearch(HttpServletRequest request,HttpServletResponse response){
		 String indicatorCategory=request.getParameter("IndicatorCategory");		
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));	
		 PrintWriter out=null;
	     try {
			out = response.getWriter();
		 } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
	     List<CommonStringList> indNamesByCategory=tnmscReportManager.getFreeHandZoneIndNamesByCategory(directorateId,indicatorCategory);
	     String json=new Gson().toJson(indNamesByCategory);  
	     out.println(json);
	}
	@RequestMapping(value="/tnmscOnIndicatorNameChangeSearch", method=RequestMethod.POST)
	public  @ResponseBody void onIndicatorNameChangeSearch(HttpServletRequest request,HttpServletResponse response){
		 String indicatorCategory=request.getParameter("IndicatorCategory");
		 String indicatorName=request.getParameter("IndicatorName");
		 int directorateId=Integer.parseInt(request.getParameter("DirectorateId"));
		 PrintWriter out=null;
	     try {
			out = response.getWriter();
		 } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
		 List indYearByNameandCate=tnmscReportManager.getIndYearByNameandCategory(directorateId,indicatorCategory,indicatorName);
		 String json=new Gson().toJson(indYearByNameandCate);  
	     out.println(json);
	}
	@RequestMapping(value="/tnmscFreeHandZoneBySearch", method=RequestMethod.POST)
	public  @ResponseBody String ShowFreeHandZoneBySearcht(HttpServletRequest request,HttpServletResponse response){
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));	
		  String indicatorCategory=request.getParameter("IndicatorCategory");
		  String indCategoryName=request.getParameter("IndicatorName");
		  String year=request.getParameter("Year");
		  JSONObject object=new JSONObject();      	
	 	  JSONArray indJsonArray=tnmscReportManager.getFreeHandZoneData(directorateId,indicatorCategory,indCategoryName,Integer.parseInt(year));
	 	  try {
			object.put("freeHandZoneData", indJsonArray);
			/*object.put("specifiedCategory", indicatorCategory);
			object.put("specifiedIndicators", indCategoryName);
		 	object.put("specifiedYear", year);*/
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 	  
	     return object.toString();
	}
}
