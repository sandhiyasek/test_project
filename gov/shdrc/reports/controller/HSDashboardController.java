
package gov.shdrc.reports.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.reports.service.IHSDashboardManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HSDashboardController {
	@Autowired(required=true)
	IHSDashboardManager hsDashboardManager;
	
	@RequestMapping(value="/hsHomePage", method=RequestMethod.GET)
	public ModelAndView displayHsHomePage(HttpServletRequest request){		
	   ModelAndView modelView=new ModelAndView();
	   modelView.setViewName("hsHomePage");
	 return modelView;  
	}   

	
	@RequestMapping(value="/hsDashboard", method=RequestMethod.GET)
	public ModelAndView showTestPages(HttpServletRequest request){		
	   ModelAndView modelView=new ModelAndView();
	   modelView.setViewName("hsDashboard");
		try{
			 List<String> yearList=hsDashboardManager.getYearList();
		     List<String> districtList=hsDashboardManager.getDistrictList();
		     List<String> monthList=getMonthsList();   
		     HttpSession httpSession=request.getSession();
		    // List<String> tempSessionDist=(List<String>) httpSession.getAttribute("sessionDistrictList");
		     @SuppressWarnings("unchecked")
			List<String> tempSessionYear=(List<String>) httpSession.getAttribute("sessionYearList");
	        if(tempSessionYear == null){
	        	httpSession.setAttribute("sessionDistrictList", districtList);
	        	httpSession.setAttribute("sessionYearList", yearList);
	        }
			
			String dashIndName=request.getParameter("indicatorName");			
				JSONArray searchOptionsList= hsDashboardManager.getSearchOptionsList(dashIndName);        
		        List<String> childIndList = hsDashboardManager.getChildIndList(dashIndName);
				int countChildInd=childIndList.size();
				
				String year = "Current Year";
				String month = "Current Month";
				String district = "All Districts";
				String institution = "All Institutions";
				
				if("Maternal Mortality Rate".equals(dashIndName)
						|| "Infant Mortality Rate".equals(dashIndName)){
				
					if( year == null || "Current Year".equalsIgnoreCase(year)){
						int curyear = Calendar.getInstance().get(Calendar.YEAR);	
						year=Integer.toString(curyear);
					}
					if("Current Month".equalsIgnoreCase(month)){
						Calendar cal =  Calendar.getInstance();
						cal.add(Calendar.MONTH ,-2);
						month = new SimpleDateFormat("MMMMM").format(cal.getTime());
						month=month.substring(0,3);
					}
				}else{
					CommonStringList maxMonthAndYear=hsDashboardManager.getIndMaxMonthAndYear(dashIndName);
					year = String.valueOf(maxMonthAndYear.getId());
					month = maxMonthAndYear.getName();
				}	
				
				JSONArray indicatorDetailsList=hsDashboardManager.getYMDThreeParamIndDetailsList(year,month,district,dashIndName,countChildInd,childIndList);
				if(indicatorDetailsList==null){
					throw new SHDRCException("Please check the input data");
				}
				else if(indicatorDetailsList.length()>10){
					JSONArray topDistrictList =getTopDistrictList(indicatorDetailsList);
					JSONArray bottomDistrictList = getBottomDistrictList(indicatorDetailsList);
					 modelView.addObject("topDistrictList", topDistrictList);
					 modelView.addObject("bottomDistrictList", bottomDistrictList);
				}else{
					 modelView.addObject("topDistrictList", indicatorDetailsList);
				}		
				
				if(indicatorDetailsList!=null){
				StringBuilder districtDataList = getAppendDataList(indicatorDetailsList);	
				//StringBuilder stateValue=null;
					/*if(countChildInd==2 || countChildInd==3){
						stateValue=getDerivedStateValue(indicatorDetailsList);
					}else{
						stateValue=getDirectStateValue(indicatorDetailsList);
					}*/			
					JSONArray ymindicatorDetailsList= hsDashboardManager.getYMTwoParamIndDetailsList(year, month, dashIndName,countChildInd,childIndList);					
					modelView.addObject("districtDataList", districtDataList);
					modelView.addObject("stateValue", (ymindicatorDetailsList!=null&&ymindicatorDetailsList.length()>0)?ymindicatorDetailsList.getJSONObject(0).get("value"):null);								
				}
				
			JSONArray indInstDetailsList=hsDashboardManager.getYMIThreeParamIndDetailsList(year,month,institution,dashIndName,countChildInd,childIndList);
			if(indInstDetailsList!=null && indInstDetailsList.length()>0){
				JSONArray indTopInstDetailsList =null;
				StringBuilder institutionDataList=null;
				if(indInstDetailsList.length()>100){
					indTopInstDetailsList =getTopInstitutionList(indInstDetailsList);
					institutionDataList = getAppendDataList(indTopInstDetailsList);
				}
				else{
					institutionDataList = getAppendDataList(indInstDetailsList);
				}
				modelView.addObject("institutionDataList", institutionDataList);
			}		
	        
			JSONArray projectedValue = new JSONArray();
			projectedValue=hsDashboardManager.getProjectedValues(year,district,dashIndName);
			
			JSONArray cumulativeProjectedValue=hsDashboardManager.getCumulativeProjectedValues(dashIndName);
				 
			 List<String> institutionList=hsDashboardManager.getInstitutionList();
			    
			 List<String> searchParamList=getSearchParam(yearList,monthList,districtList);
			 
	        modelView.addObject("searchOptionsList", searchOptionsList);
	        modelView.addObject("searchOption", dashIndName);
	        modelView.addObject("searchParameter", year+","+month+",All Districts");
	        modelView.addObject("indicatorDetailsList", indicatorDetailsList);
			modelView.addObject("dashIndName", dashIndName);
			modelView.addObject("projectedValue", projectedValue);	
			modelView.addObject("cumulativeProjectedValue", cumulativeProjectedValue);	
			modelView.addObject("cumulativeProjectedSubCaption", (cumulativeProjectedValue!=null&&cumulativeProjectedValue.length()>0)?cumulativeProjectedValue.getJSONObject(0).get("subCaption"):null);
			modelView.addObject("projectedValueSubCaption", (projectedValue!=null&&projectedValue.length()>0)?projectedValue.getJSONObject(0).get("subCaption"):null);
			modelView.addObject("searchParamList", new JSONArray(searchParamList));
			modelView.addObject("searchInstParamList", new JSONArray(institutionList));
		}catch(SHDRCException ex){
		}catch (JSONException e){
		}
		return modelView;
	}
	
	@RequestMapping(value="/doSearch", method=RequestMethod.POST)
	public @ResponseBody void doSearch(HttpServletRequest request,HttpServletResponse response)throws IOException {
		PrintWriter out=response.getWriter();
		try{
			HttpSession httpSession=request.getSession();
			//List<String> districtList=(List<String>) httpSession.getAttribute("sessionDistrictList");
			List<String> districtList=hsDashboardManager.getDistrictList();
			@SuppressWarnings("unchecked")
			List<String> yearList=(List<String>) httpSession.getAttribute("sessionYearList");
			List<String> monthList=getMonthsList();	
			
		    String year=null;
	        String district=null;
	        String institution=null;
	        String month=null; 

	        String indName= request.getParameter("indName");	      
			String searchParam=request.getParameter("searchParam");
			String searchInstParam=request.getParameter("searchInstParam");
			
			if(searchParam.contains("All Districts")){
				district = "All Districts";
				searchParam=searchParam.replace("All Districts","ALLDISTRICTS");
			}
			/*if(searchInstParam.contains("All Institutions")){
				institution = "All Institutions";
				searchInstParam=searchInstParam.replace("All Institutions","ALLINSTITUTIONS");
			}*/
			if(searchParam.contains("All Months")){
				month = "All Months";
				searchParam=searchParam.replace("All Months","ALLMONTHS");
			}
			if(searchParam.contains("Current Month")){
				month = "Current Month";
				searchParam=searchParam.replace("Current Month","CURRENTMONTH");
			}
			if(searchParam.contains("Current Year")){
				year = "Current Year";
				searchParam=searchParam.replace("Current Year","CURRENTYEAR");
			}
			//searchParam=searchParam.trim().replaceAll(":", " ");
			searchParam=searchParam.trim().replaceAll("\\s+", ","); 
	        String[] searchParams = searchParam.split(",");
	     /*   int noOfParts=searchParams.length;*/
	      /*  List<String> a = new ArrayList<String>();
	        a.add(searchParams[0]);
	        a.add(searchParams[1]);
	        a.add(searchParams[2]);
	       
	        if(searchInstParam != ""){
	        	a.add(searchInstParam);
	        }		        
	        */
	        for(String param:searchParams)
	        {
	        	if(yearList.contains(param))
		    	{
		    		year=param;
		    	}
	        	else if(districtList.contains(param))
	        	{
	        		district=param;
	        	}
	        	else if(monthList.contains(param))
	        	{
	        		month=param;
	        	}
	        	/*else if(institutionList.contains(param))
	        	{
	        		institution=param;
	        	}*/
	        	else if(!(("CURRENTYEAR").equalsIgnoreCase(param) || ("CURRENTMONTH").equalsIgnoreCase(param) || 
	        			("ALLMONTHS").equalsIgnoreCase(param) || ("ALLDISTRICTS").equalsIgnoreCase(param))){	 /*|| ("ALLINSTITUTIONS").equalsIgnoreCase(param)*/        		
	        		throw new SHDRCException("InvalidInput");	        		
	        	}
	        }
	        if(searchInstParam!=""){
	        	institution=searchInstParam;
	        }
	        getIndDetailsList(request,response,year,district,month,institution,indName);
		}
		catch(SHDRCException ex){
			out.println(ex);
		}
	}
	
	private void getIndDetailsList(HttpServletRequest request, HttpServletResponse response,String year,
			String district,String month,String institution,String indName) throws IOException{
		
		PrintWriter out=response.getWriter();
		
		try{
			JSONObject jsonObject=new JSONObject();
			JSONArray data=new JSONArray();
			
			JSONArray indicatorDetailsList=null;
			JSONArray topDistrictList=null;
			JSONArray bottomDistrictList=null;
			JSONArray ChildIndDataList=null;
			JSONArray projectedValue=null;
			JSONArray institutionScrollList=null;
			StringBuilder institutionDataList=null;
			
			List<String> childIndList = hsDashboardManager.getChildIndList(indName);
			int countChildInd=childIndList.size();
			
			if( year == null || "Current Year".equalsIgnoreCase(year)){
				int curyear = Calendar.getInstance().get(Calendar.YEAR);	
				year=Integer.toString(curyear);
			}
			if("Current Month".equalsIgnoreCase(month)){
				Calendar cal =  Calendar.getInstance();
				cal.add(Calendar.MONTH ,-1);
				month = new SimpleDateFormat("MMMMM").format(cal.getTime());
			}
			if(month!=null && month.length()>3){
				month=month.substring(0,3);			
			}
			
			if(year != null && district != null && month != null && institution !=null){
				indicatorDetailsList=hsDashboardManager.getFourParamIndDetailsList(year,district,month,institution,indName,countChildInd,childIndList);	
				if(indicatorDetailsList.length()>10){
					 topDistrictList = getTopDistrictList(indicatorDetailsList);
					 bottomDistrictList = getBottomDistrictList(indicatorDetailsList);
				}
				/*if(countChildInd>1){
					 ChildIndDataList=hsDashboardManager.getFourParamChildIndDataList(year,district,month,institution,indName);
				}*/
				projectedValue=hsDashboardManager.getProjectedValues(year,district,indName);
				if(indicatorDetailsList!=null && indicatorDetailsList.length()>0){
					int curyear = Calendar.getInstance().get(Calendar.YEAR);	
					year=Integer.toString(curyear);
					Calendar cal =  Calendar.getInstance();
					cal.add(Calendar.MONTH ,-1);
					month = new SimpleDateFormat("MMMMM").format(cal.getTime());
					if(month!=null && month.length()>3){
						month=month.substring(0,3);			
					}
					institution="All Institutions";
					institutionScrollList=hsDashboardManager.getFourParamIndDetailsList(year,district,month,institution,indName,countChildInd,childIndList);
					institutionDataList = getAppendDataList(institutionScrollList);
				}
			}      
			else if(year != null && district != null && month != null && institution ==null){
				indicatorDetailsList=hsDashboardManager.getYMDThreeParamIndDetailsList(year,month,district,indName,countChildInd,childIndList);
				if(indicatorDetailsList.length()>10){
					 topDistrictList = getTopDistrictList(indicatorDetailsList);
					 bottomDistrictList = getBottomDistrictList(indicatorDetailsList);
				}
				/*if(countChildInd>1){
					 ChildIndDataList=hsDashboardManager.getYMDThreeParamChildIndDataList(year,month,district,indName);
				}*/
				projectedValue=hsDashboardManager.getProjectedValues(year,district,indName);
				if(indicatorDetailsList!=null && indicatorDetailsList.length()>0){
					int curyear = Calendar.getInstance().get(Calendar.YEAR);	
					year=Integer.toString(curyear);
					Calendar cal =  Calendar.getInstance();
					cal.add(Calendar.MONTH ,-1);
					month = new SimpleDateFormat("MMMMM").format(cal.getTime());
					if(month!=null && month.length()>3){
						month=month.substring(0,3);			
					}
					institution="All Institutions";
					institutionScrollList=hsDashboardManager.getFourParamIndDetailsList(year,district,month,institution,indName,countChildInd,childIndList);
					institutionDataList = getAppendDataList(institutionScrollList);
				}
			}
			else if((year != null && district != null && month == null && institution !=null)){
				indicatorDetailsList=hsDashboardManager.getYDIThreeParamIndDetailsList(year,district,institution,indName,countChildInd,childIndList);
				if(indicatorDetailsList.length()>10){
					 topDistrictList = getTopDistrictList(indicatorDetailsList);
					 bottomDistrictList = getBottomDistrictList(indicatorDetailsList);
				}
				/*if(countChildInd>1){
					ChildIndDataList=hsDashboardManager.getYDIThreeParamChildIndDataList(year,district,institution,indName);
				}*/
				projectedValue=hsDashboardManager.getProjectedValues(year,district,indName);
				if(indicatorDetailsList!=null && indicatorDetailsList.length()>0){
					int curyear = Calendar.getInstance().get(Calendar.YEAR);	
					year=Integer.toString(curyear);
					Calendar cal =  Calendar.getInstance();
					cal.add(Calendar.MONTH ,-1);
					month = new SimpleDateFormat("MMMMM").format(cal.getTime());
					if(month!=null && month.length()>3){
						month=month.substring(0,3);			
					}
					institution="All Institutions";
					institutionScrollList=hsDashboardManager.getFourParamIndDetailsList(year,district,month,institution,indName,countChildInd,childIndList);
					institutionDataList = getAppendDataList(institutionScrollList);
				}
			}
			else if((year != null && district == null && month != null && institution !=null)){
				indicatorDetailsList=hsDashboardManager.getYMIThreeParamIndDetailsList(year,month,institution,indName,countChildInd,childIndList);
				if(indicatorDetailsList.length()>10){
					 topDistrictList = getTopDistrictList(indicatorDetailsList);
					 bottomDistrictList = getBottomDistrictList(indicatorDetailsList);
				}
				/*if(countChildInd>1){
					 ChildIndDataList=hsDashboardManager.getYMIThreeParamChildIndDataList(year,month,institution,indName);
				}*/
				projectedValue=hsDashboardManager.getProjectedValues(year,district,indName);
			}
			else if((year != null && district == null && month == null && institution !=null)){
				indicatorDetailsList=hsDashboardManager.getYITwoParamIndDetailsList(year,institution,indName,countChildInd,childIndList);
				if(indicatorDetailsList.length()>10){
					 topDistrictList = getTopDistrictList(indicatorDetailsList);
					 bottomDistrictList = getBottomDistrictList(indicatorDetailsList);
				}
				/*if(countChildInd>1){
					 ChildIndDataList=hsDashboardManager.getTwoParamChildIndDataList(year,institution,indName);
				}*/
				projectedValue=hsDashboardManager.getProjectedValues(year,district,indName);
			}
			else if((year != null && district == null && month != null && institution ==null)){
				indicatorDetailsList=hsDashboardManager.getYMTwoParamIndDetailsList(year,month,indName,countChildInd,childIndList);
				if(indicatorDetailsList.length()>10){
					 topDistrictList = getTopDistrictList(indicatorDetailsList);
					 bottomDistrictList = getBottomDistrictList(indicatorDetailsList);
				}
				/*if(countChildInd>1){
					 ChildIndDataList=hsDashboardManager.getTwoParamChildIndDataList(year,month,indName);
				}*/
				projectedValue=hsDashboardManager.getProjectedValues(year,district,indName);
			}
			else if((year != null && district != null && month == null && institution ==null)){
				indicatorDetailsList=hsDashboardManager.getYDTwoParamIndDetailsList(year,district,indName,countChildInd,childIndList);
				if(indicatorDetailsList.length()>10){
					 topDistrictList = getTopDistrictList(indicatorDetailsList);
					 bottomDistrictList = getBottomDistrictList(indicatorDetailsList);
				}
				/*if(countChildInd>1){
					 ChildIndDataList=hsDashboardManager.getTwoParamChildIndDataList(year,district,indName);
				}*/
				projectedValue=hsDashboardManager.getProjectedValues(year,district,indName);
				if(indicatorDetailsList!=null && indicatorDetailsList.length()>0){
					int curyear = Calendar.getInstance().get(Calendar.YEAR);	
					year=Integer.toString(curyear);
					Calendar cal =  Calendar.getInstance();
					cal.add(Calendar.MONTH ,-1);
					month = new SimpleDateFormat("MMMMM").format(cal.getTime());
					if(month!=null && month.length()>3){
						month=month.substring(0,3);			
					}
					institution="All Institutions";
					institutionScrollList=hsDashboardManager.getFourParamIndDetailsList(year,district,month,institution,indName,countChildInd,childIndList);
					institutionDataList = getAppendDataList(institutionScrollList);
				}
			}
			else if((year != null && district == null && month == null && institution ==null)){
				indicatorDetailsList=hsDashboardManager.getOneParamIndDetailsList(year,indName,countChildInd,childIndList);
				if(indicatorDetailsList.length()>10){
					 topDistrictList = getTopDistrictList(indicatorDetailsList);
					 bottomDistrictList = getBottomDistrictList(indicatorDetailsList);
				}
				/*if(countChildInd>1){
					 ChildIndDataList=hsDashboardManager.getOneParamChildIndDataList(year,indName);
				}*/
				projectedValue=hsDashboardManager.getProjectedValues(year,district,indName);
			}			
		
			if(indicatorDetailsList==null || indicatorDetailsList.length()==0){
				throw new SHDRCException("NoData");	    
			}
			else{
				jsonObject.put("indicatorDetailsList", indicatorDetailsList);
	 			jsonObject.put("ChildIndDataList", ChildIndDataList);
	 			jsonObject.put("projectedValue", projectedValue);
	 			jsonObject.put("topDistrictList", topDistrictList);
	 			jsonObject.put("projectedValueSubCaption", (projectedValue!=null&&projectedValue.length()>0)?projectedValue.getJSONObject(0).get("subCaption"):null);
	 			jsonObject.put("barChartSubCaption",(indicatorDetailsList!=null&&indicatorDetailsList.length()==1)?indicatorDetailsList.getJSONObject(0).get("label"):null);
	 			jsonObject.put("bottomDistrictList", bottomDistrictList);
	 			jsonObject.put("institutionDataList",institutionDataList);	 
	 			data.put(jsonObject);
	 			out.println(jsonObject);
			}
		}
         catch (JSONException e) {
        	e.printStackTrace();
        }
		catch(SHDRCException ex){
			out.println(ex);
		}       
	}
	
	@RequestMapping(value="/hsDashboardAllDirstictData", method=RequestMethod.GET)
	public ModelAndView getHSDashboardAllData(HttpServletRequest request){
		 ModelAndView modelView=new ModelAndView();
		 modelView.setViewName("hsDashboardAllDirstictData");
		 String searchParam=request.getParameter("searchParam");
		 List<String> yearList=hsDashboardManager.getYearList();
	     List<String> districtList=hsDashboardManager.getDistrictList();
	     List<String> monthList=getMonthsList();
	     String dashIndName=request.getParameter("indicatorName");
		  String year=null;
		  String month=null;
		  if(searchParam.contains("All Months")){
				month = "All Months";
				searchParam=searchParam.replace("All Months","ALLMONTHS");
			}
			if(searchParam.contains("Current Month")){
				month = "Current Month";
				searchParam=searchParam.replace("Current Month","CURRENTMONTH");
			}
			if(searchParam.contains("Current Year")){
				year = "Current Year";
				searchParam=searchParam.replace("Current Year","CURRENTYEAR");
			}
			searchParam=searchParam.trim().replaceAll("\\s+", ","); 
	        String[] searchParams = searchParam.split(",");
	    
	        for(String param:searchParams)
	        {
	        	if(yearList.contains(param))
		    	{
		    		year=param;
		    	}
	        	else if(monthList.contains(param))
	        	{
	        		month=param;
	        	}
	        }
	        modelView.addObject("year", year);
	        modelView.addObject("month", month);
	        modelView.addObject("dashIndName", dashIndName);
	        modelView.addObject("districtList", districtList);
		   return modelView;
	}
	
	@RequestMapping(value="/getInstitutionByDistrict", method=RequestMethod.POST)
	public @ResponseBody void getInstitutionByDistrict(HttpServletRequest request,HttpServletResponse response)throws IOException {
	/*@RequestMapping(value = "/getInstitutionByDistrict", method = RequestMethod.POST)
	public @ResponseBody
	void getInstitutionByDistrict(HttpServletRequest request,HttpServletResponse response,@RequestBody String dashIndName,@RequestBody String year,
			@RequestBody String month,@RequestBody String district) throws IOException{*/
	 
		PrintWriter out=response.getWriter();
		try{
			String dashIndName=request.getParameter("dashIndName");
			String year=request.getParameter("year");
			String month=request.getParameter("month");
			String district=request.getParameter("districtName");
			String institution="All Institutions";
			
			if(year.equalsIgnoreCase("null")){
				if("Maternal Mortality Rate".equals(dashIndName)
						|| "Infant Mortality Rate".equals(dashIndName)){
				
					if( year == null || "Current Year".equalsIgnoreCase(year)){
						int curyear = Calendar.getInstance().get(Calendar.YEAR);	
						year=Integer.toString(curyear);
					}
					if("Current Month".equalsIgnoreCase(month)){
						Calendar cal =  Calendar.getInstance();
						cal.add(Calendar.MONTH ,-2);
						month = new SimpleDateFormat("MMMMM").format(cal.getTime());
						//month=month.substring(0,3);
					}
				}else{
					CommonStringList maxMonthAndYear=hsDashboardManager.getIndMaxMonthAndYear(dashIndName);
					year = String.valueOf(maxMonthAndYear.getId());
					//month = maxMonthAndYear.getName();
				}	
			}
			
			List<String> childIndList = hsDashboardManager.getChildIndList(dashIndName);
			int countChildInd=childIndList.size();
		/*	JSONObject jsonObject=new JSONObject();*/
			JSONArray hsDistrictData=null;
			
			if("All Districts".equalsIgnoreCase(district) && !month.equalsIgnoreCase("null")){
				hsDistrictData=hsDashboardManager.getYMDThreeParamIndDetailsList(year, month, district, dashIndName, countChildInd, childIndList);
			}else if("All Districts".equalsIgnoreCase(district) && month.equalsIgnoreCase("null")){
				hsDistrictData=hsDashboardManager.getYDTwoParamIndDetailsList(year,district, dashIndName, countChildInd, childIndList);
			}else if(!"All Districts".equalsIgnoreCase(district) && month.equalsIgnoreCase("null")){
				hsDistrictData=hsDashboardManager.getYDIThreeParamIndDetailsList(year,district,institution, dashIndName, countChildInd, childIndList);
			}else{
				hsDistrictData=hsDashboardManager.getFourParamIndDetailsList(year, district, month, institution, dashIndName, countChildInd, childIndList);
			}
			/*jsonObject.put("hsDistrictData", hsDistrictData);*/
			out.println(hsDistrictData);
		}
		catch(Exception ex){
		}
	}
	
	private List<String> getMonthsList(){
		List<String> monthList = new ArrayList<String>();
		monthList.add("Jan");
		monthList.add("Feb");
		monthList.add("Mar");
		monthList.add("Apr");
		monthList.add("May");
		monthList.add("Jun");
		monthList.add("Jul");
		monthList.add("Aug");
		monthList.add("Sep");
		monthList.add("Oct");
		monthList.add("Nov");
		monthList.add("Dec");
		monthList.add("January");
		monthList.add("Febuary");
		monthList.add("March");
		monthList.add("April");
		monthList.add("May");
		monthList.add("June");
		monthList.add("July");
		monthList.add("August");
		monthList.add("September");
		monthList.add("October");
		monthList.add("November");
		monthList.add("December");		
		return monthList;
	}
	
	public StringBuilder getAppendDataList(JSONArray indicatorDetailsList){
		StringBuilder strBuilderList=new StringBuilder();
		JSONObject districtData=new  JSONObject();
		try {
			for(int i=0;i<indicatorDetailsList.length();i++){
				districtData=indicatorDetailsList.getJSONObject(i);	
				/* JLabel label = new JLabel();
				 label=(JLabel) districtData.get("label");
				 label.setForeground(Color.GREEN);*/
				strBuilderList.append(districtData.get("label"));
				/* strBuilderList.append(label);*/
				strBuilderList.append(":&nbsp;&nbsp;");
				strBuilderList.append(districtData.get("value"));
				strBuilderList.append("   ");
				strBuilderList.append( "   >>>>>>   ");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return strBuilderList;
	}
	
	public StringBuilder getDerivedStateValue(JSONArray indicatorDetailsList){
		StringBuilder derivedStateValue=new StringBuilder();
		JSONObject stateData=new  JSONObject();
		int stateValue;
		double stateTotalValue=0;
		try {
			for(int i=0;i<indicatorDetailsList.length();i++){			
				stateData=indicatorDetailsList.getJSONObject(i);
				stateValue=stateData.getInt("value");
				stateTotalValue=stateTotalValue+stateValue;
			}
			stateTotalValue=stateTotalValue/indicatorDetailsList.length();
			derivedStateValue.append("TamilNadu");
			derivedStateValue.append(":     ");
			derivedStateValue.append(Util.formatTwoDecimal(stateTotalValue));
		}catch (JSONException e) {
			e.printStackTrace();
		}		
		return derivedStateValue;
	}
	
	public StringBuilder getDirectStateValue(JSONArray indicatorDetailsList){
		StringBuilder directStateValue=new StringBuilder();
		JSONObject stateData=new  JSONObject();
		int stateValue;
		int stateTotalValue=0;
		try {
			for(int i=0;i<indicatorDetailsList.length();i++){			
				stateData=indicatorDetailsList.getJSONObject(i);
				stateValue=stateData.getInt("value");
				stateTotalValue=stateTotalValue+stateValue;
			}
			directStateValue.append("TamilNadu");
			directStateValue.append(":");
			directStateValue.append(stateTotalValue);
		}catch (JSONException e) {
			e.printStackTrace();
		}		
		return directStateValue;
	}
	
	public JSONArray getTopDistrictList(JSONArray indicatorDetailsList){
		JSONArray topDistrictList=new JSONArray();
		for(int i=0;i<10;i++){
			try {
				topDistrictList.put(indicatorDetailsList.getJSONObject(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return topDistrictList;
	}
	
	public JSONArray getBottomDistrictList(JSONArray indicatorDetailsList){
		JSONArray bottomDistrictList=new JSONArray();
		for(int i=indicatorDetailsList.length()-1;i>indicatorDetailsList.length()-11;i--){
			try {
				bottomDistrictList.put(indicatorDetailsList.getJSONObject(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return bottomDistrictList;
	}
	
	public JSONArray getTopInstitutionList(JSONArray indInstDetailsList){
		JSONArray topInstitutionList=new JSONArray();
		for(int i=0;i<100;i++){
			try {
				topInstitutionList.put(indInstDetailsList.getJSONObject(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return topInstitutionList;
	}
	
	private List<String> getSearchParam(List<String> yearList,List<String> monthList,List<String> districtList)
	{
		List<String> searchParamList = districtList;
		for(int i=0;i<yearList.size();i++){
			searchParamList.add(yearList.get(i));
		}
		for(int j=0;j<monthList.size();j++){
			searchParamList.add(monthList.get(j));
		}
		return searchParamList;
	}

}
