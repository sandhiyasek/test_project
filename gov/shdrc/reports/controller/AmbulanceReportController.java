package gov.shdrc.reports.controller;

import gov.shdrc.reports.service.IAmbulanceReportManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SHDRCException;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
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
public class AmbulanceReportController {
	@Autowired(required=true)
	IAmbulanceReportManager ambulanceReportManager;
		
	@RequestMapping(value="/ambulanceReport", method=RequestMethod.GET)
	public ModelAndView onLoadAmbulanceReport(HttpServletRequest request) throws JSONException
	{	
		ModelAndView modelView=null;
		List<CommonStringList> districtsList=null;
		List<CommonStringList> emergencyTypeList=null;
		String maxDate=null;
		String date=null;
		String month=null;
		String year=null;
		String frequency=null;
		String emergencyType=null;
		String districtName=null;
		JSONArray ambulanceGISReportData=null;
		JSONArray ambulanceHourwiseHeatGridData=null;
		JSONArray ambulanceDaywiseHeatGridData=null;
		JSONArray ambulanceComparisonFirstSeriesData=null;
		JSONArray ambulanceComparisonSecondSeriesData=null;
		JSONObject geoMapData=null;
		JSONObject geoMapDistColor=null;
		JSONObject heatMapDataRange=null;
		List<CommonStringList> predictionDistrictsList=null;
		List<CommonStringList> predictionEmerTypeList=null;
		JSONArray predictionTalukData=null;
		JSONArray predictionVillageData=null;
		String district=null;
		String talukName = null;
		try{
			modelView = new ModelAndView();
			modelView.setViewName("AmbulanceReport");
			
			Integer directorateId = 1;
			String methodName="onLoad";
			districtsList = ambulanceReportManager.getOnLoadDistrictList();
			emergencyTypeList = ambulanceReportManager.getOnLoadEmergencyTypeList();
			maxDate = Util.getStrDate(Util.convertToUtilDate(ambulanceReportManager.getAmbulanceReportMaxDate()));
			
			geoMapData = ambulanceReportManager.getAmbulanceGISReportData(date,month,year,frequency,emergencyType,districtName,methodName);
			if(geoMapData!=null && geoMapData.length()>0){
				ambulanceGISReportData=geoMapData.getJSONArray("GISReportData");
				geoMapDistColor=geoMapData.getJSONObject("geoMapDistColor");
			}	
			
			ambulanceHourwiseHeatGridData = ambulanceReportManager.getAmbulanceHourwiseHeatGridData(date,month,year,frequency,emergencyType,districtName,methodName);
			heatMapDataRange = ambulanceReportManager.getHeatMapDataRange(date,month,year,frequency,emergencyType,districtName,methodName);
			ambulanceDaywiseHeatGridData = ambulanceReportManager.getAmbulanceDaywiseHeatGridData(date,month,year,frequency,emergencyType,districtName,methodName);
			
			JSONObject ambulanceComparisonData = ambulanceReportManager.getAmbulanceComparisonData(date,month,year,frequency,emergencyType,districtName,methodName);
			if(ambulanceComparisonData!=null && ambulanceComparisonData.length()>0){
				ambulanceComparisonFirstSeriesData=ambulanceComparisonData.getJSONArray("ambulanceComparisonFirstSeriesData");
				ambulanceComparisonSecondSeriesData=ambulanceComparisonData.getJSONArray("ambulanceComparisonSecondSeriesData");
			}	
			
			predictionDistrictsList = ambulanceReportManager.getPredictionDistrictList();
			predictionEmerTypeList = ambulanceReportManager.getPredictionEmerTypeList();
			
			 if(Util.isNotNull(predictionDistrictsList)){
		        	CommonStringList predictionDistrict = predictionDistrictsList.get(0);
		        	district=predictionDistrict.getName();
			 }
			 
			predictionTalukData = ambulanceReportManager.getPredictionTalukData(district,null);				
			
			if(predictionTalukData != null){
				JSONObject taluk = predictionTalukData.getJSONObject(0);
				talukName = taluk.getString("district");
			}
			
			predictionVillageData = ambulanceReportManager.getPredictionVillageData(district,talukName,null);	
			
			List<Integer> yearList= Util.yearList;
		    modelView.addObject("yearList", yearList);
		    
		    List<CommonStringList> monthsList= Util.monthsList;
	        modelView.addObject("monthsList", monthsList);
			
	        modelView.addObject("districts", districtsList);
	        modelView.addObject("directorateId", directorateId);
	        modelView.addObject("maxDate", maxDate);
	        modelView.addObject("emergencyTypeList", emergencyTypeList);
	        modelView.addObject("ambulanceGISReportData", ambulanceGISReportData);
	        modelView.addObject("geoMapDistColor", geoMapDistColor);
	        modelView.addObject("ambulanceHourwiseHeatGridData", ambulanceHourwiseHeatGridData);
	        modelView.addObject("ambulanceDaywiseHeatGridData", ambulanceDaywiseHeatGridData);
	        modelView.addObject("ambulanceComparisonFirstSeriesData", ambulanceComparisonFirstSeriesData);
	        modelView.addObject("ambulanceComparisonSecondSeriesData", ambulanceComparisonSecondSeriesData);
	        modelView.addObject("heatMapDataRange", heatMapDataRange);
	        modelView.addObject("predictionDistrictsList", predictionDistrictsList);
	        modelView.addObject("predictionEmerTypeList", predictionEmerTypeList);
	        modelView.addObject("PredictionDistrict", district);
	        modelView.addObject("predictionTalukData", predictionTalukData);
	        modelView.addObject("predictionVillageData", predictionVillageData);
	        
		}finally{			
			
		}
		return modelView;
	}

	@RequestMapping(value="/ambulancePredictionReport", method=RequestMethod.GET)
	public ModelAndView onLoadAmbulancePredictionReport(HttpServletRequest request) throws JSONException
	{	
		ModelAndView modelView=null;
		List<CommonStringList> predictionDistrictsList=null;
		List<CommonStringList> predictionEmerTypeList=null;
		JSONArray predictionTalukData=null;
		JSONArray predictionVillageData=null;
		String district=null;
		String talukName = null;
		try{
			modelView = new ModelAndView();
			modelView.setViewName("AmbulancePredictionReport");
			
			predictionDistrictsList = ambulanceReportManager.getPredictionDistrictList();
			predictionEmerTypeList = ambulanceReportManager.getPredictionEmerTypeList();
			
			 if(Util.isNotNull(predictionDistrictsList)){
		        	CommonStringList predictionDistrict = predictionDistrictsList.get(0);
		        	district=predictionDistrict.getName();
			 }
			 
			predictionTalukData = ambulanceReportManager.getPredictionTalukData(district,null);				
			
			if(predictionTalukData != null){
				JSONObject taluk = predictionTalukData.getJSONObject(0);
				talukName = taluk.getString("district");
			}
			
			predictionVillageData = ambulanceReportManager.getPredictionVillageData(district,talukName,null);	
			
	        modelView.addObject("predictionDistrictsList", predictionDistrictsList);
	        modelView.addObject("predictionEmerTypeList", predictionEmerTypeList);
	        modelView.addObject("PredictionDistrict", district);
	        modelView.addObject("predictionTalukData", predictionTalukData);
	        modelView.addObject("predictionVillageData", predictionVillageData);
	        
		}finally{			
			
		}
		return modelView;
	}
		
	@RequestMapping(value="/ambulanceReportSearchDateChange", method=RequestMethod.POST)
	public @ResponseBody void getEmergencyType(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{			
		PrintWriter out = response.getWriter();
		try{				
			String frequency=request.getParameter("frequency");
			String date=request.getParameter("date");
			String month=request.getParameter("month");
			String year=request.getParameter("year");
			List<CommonStringList> emergencyTypeList= null;
			emergencyTypeList= ambulanceReportManager.getEmergencyTypeList(frequency,date,month,year);				
			String json=new Gson().toJson(emergencyTypeList);  
			out.println(json);
			}
		finally{} 				 
	}	
		
	@RequestMapping(value="/ambulanceReportSearchEmergencyTypeChange", method=RequestMethod.POST)
	public @ResponseBody void getDistrictName(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{			
		PrintWriter out = response.getWriter();
		try{				
			String frequency=request.getParameter("frequency");
			String date=request.getParameter("date");
			String month=request.getParameter("month");
			String year=request.getParameter("year");
			String emergencyType=request.getParameter("emergencyType");
			List<CommonStringList> districtList= null;
			districtList= ambulanceReportManager.getDistrictList(frequency,date,month,year,emergencyType);				
			String json=new Gson().toJson(districtList);  
			out.println(json);
			}
		finally{}				 
	}	
		
	@RequestMapping(value="/ambulanceReportOnSearch", method=RequestMethod.POST)
	public @ResponseBody void getAmbulanceReportOnSearch(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONArray ambulanceGISReportData=null;
		JSONArray ambulanceHourwiseHeatGridData=null;
		JSONArray ambulanceDaywiseHeatGridData=null;
		JSONArray ambulanceComparisonFirstSeriesData=null;
		JSONArray ambulanceComparisonSecondSeriesData=null;
		JSONObject data=null;
		JSONObject geoMapData=null;
		JSONObject geoMapDistColor=null;
		JSONObject heatMapDataRange=null;
		JSONArray predictionTalukData=null;
		JSONArray predictionVillageData=null;
		String predictionDistrict=null;
		String predictionTalukName = null;
		String predictionEmerType = null;
		try{
			String date=request.getParameter("date");
			String month=request.getParameter("month");
			String year=request.getParameter("year");
			String frequency=request.getParameter("frequency");
			String emergencyType=request.getParameter("emergencyType");
			String districtName=request.getParameter("districtName");	
			String jspAction=request.getParameter("methodName");	
			predictionDistrict=request.getParameter("predictionDistrict");
			predictionTalukName=request.getParameter("predictionTalukName");	
			predictionEmerType=request.getParameter("predictionEmerType");	
			
			data=new JSONObject();
			
			String methodName="onSearch";
			if("searchBtnClick".equalsIgnoreCase(jspAction)){
				geoMapData = ambulanceReportManager.getAmbulanceGISReportData(date,month,year,frequency,emergencyType,districtName,methodName);
			}
			ambulanceHourwiseHeatGridData = ambulanceReportManager.getAmbulanceHourwiseHeatGridData(date,month,year,frequency,emergencyType,districtName,methodName);
			heatMapDataRange = ambulanceReportManager.getHeatMapDataRange(date,month,year,frequency,emergencyType,districtName,methodName);
			ambulanceDaywiseHeatGridData = ambulanceReportManager.getAmbulanceDaywiseHeatGridData(date,month,year,frequency,emergencyType,districtName,methodName);
			JSONObject ambulanceComparisonData = ambulanceReportManager.getAmbulanceComparisonData(date,month,year,frequency,emergencyType,districtName,methodName);
			
			if(ambulanceComparisonData!=null && ambulanceComparisonData.length()>0){
				ambulanceComparisonFirstSeriesData=ambulanceComparisonData.getJSONArray("ambulanceComparisonFirstSeriesData");
				ambulanceComparisonSecondSeriesData=ambulanceComparisonData.getJSONArray("ambulanceComparisonSecondSeriesData");
			}	
			
			if(geoMapData!=null && geoMapData.length()>0){
				ambulanceGISReportData=geoMapData.getJSONArray("GISReportData");
				geoMapDistColor=geoMapData.getJSONObject("geoMapDistColor");
			}	
			
			predictionTalukData = ambulanceReportManager.getPredictionTalukData(predictionDistrict,predictionEmerType);	
			predictionVillageData = ambulanceReportManager.getPredictionVillageData(predictionDistrict,predictionTalukName,predictionEmerType);	
			
		data.put("ambulanceGISReportData", ambulanceGISReportData);
		data.put("geoMapDistColor", geoMapDistColor);
        data.put("ambulanceHourwiseHeatGridData", ambulanceHourwiseHeatGridData);
        data.put("ambulanceDaywiseHeatGridData", ambulanceDaywiseHeatGridData);
        data.put("ambulanceComparisonFirstSeriesData", ambulanceComparisonFirstSeriesData);
        data.put("ambulanceComparisonSecondSeriesData", ambulanceComparisonSecondSeriesData);
        data.put("heatMapDataRange", heatMapDataRange);
        data.put("predictionTalukData", predictionTalukData);
        data.put("predictionVillageData", predictionVillageData);
		out.println(data);
		}
		finally{}
	}
		
	@RequestMapping(value="/ambulancePredictionOnSearch", method=RequestMethod.POST)
	public @ResponseBody void getAmbulancePredictionReportOnSearch(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONObject data=null;
		JSONArray predictionTalukData=null;
		JSONArray predictionVillageData=null;
		String predictionDistrict=null;
		String predictionTalukName = null;
		String predictionEmerType = null;
		String predictionmethodName = null;
		try{	
			predictionDistrict=request.getParameter("predictionDistrict");
			predictionTalukName=request.getParameter("predictionTalukName");	
			predictionEmerType=request.getParameter("predictionEmerType");	
			predictionmethodName=request.getParameter("predictionmethodName");	
			
			data=new JSONObject();
			
			if("PredDistChange".equalsIgnoreCase(predictionmethodName)){
				predictionTalukData = ambulanceReportManager.getPredictionTalukData(predictionDistrict,predictionEmerType);	
				data.put("predictionTalukData", predictionTalukData);
				
				if(predictionTalukData != null){
					JSONObject taluk = predictionTalukData.getJSONObject(0);
					predictionTalukName = taluk.getString("district");
				}				
			}
			predictionVillageData = ambulanceReportManager.getPredictionVillageData(predictionDistrict,predictionTalukName,predictionEmerType);					
       
	        data.put("predictionVillageData", predictionVillageData);
			out.println(data);
		}
		finally{}
	}
		
	@RequestMapping(value="/ambulanceCaseReport", method=RequestMethod.GET)
	public ModelAndView onLoadAmbulanceCaseReport(HttpServletRequest request) throws JSONException
	{	
		ModelAndView modelView=null;
		List<CommonStringList> districtsList=null;
		String maxDate=null;
		JSONArray ambIncTableData = null;
		String districtName = null;
		String hospitalName = null;
		JSONArray ambIncByEmerTypeData = null;
		JSONArray hospitalIncData = null;
		JSONArray hospitalIncByEmerTypeData = null;
		JSONArray hospitalCaseData = null;
		JSONArray hospitalCaseTimingData = null;
		JSONArray hospitalCaseComparisonData = null;
		try{
			modelView = new ModelAndView();
			modelView.setViewName("AmbulanceCaseReport");
			
			Integer directorateId = 1;
			maxDate = ambulanceReportManager.getAmbulanceCaseReportMaxDate();
			districtsList = ambulanceReportManager.getOnLoadDistrictList();				
			ambIncTableData=ambulanceReportManager.getAmbIncTableData(maxDate);
			if(ambIncTableData!=null){
				JSONObject district = ambIncTableData.getJSONObject(0);
				districtName = district.getString("label");
			}
			
			ambIncByEmerTypeData=ambulanceReportManager.getAmbIncByEmerTypeData(districtName,maxDate);
			
			modelView.addObject("districts", districtsList);
	        modelView.addObject("directorateId", directorateId);
	        modelView.addObject("maxDate", maxDate);	
	        modelView.addObject("ambIncTableData", ambIncTableData);	
	        modelView.addObject("districtName", districtName);	
	        modelView.addObject("ambIncByEmerTypeData", ambIncByEmerTypeData);			      
			
			JSONObject hospitalIncidentData=ambulanceReportManager.getHospitalIncData(districtName,maxDate);
			if(hospitalIncidentData!=null && hospitalIncidentData.length()>0){
				hospitalIncData=hospitalIncidentData.getJSONArray("hospitalIncData");
				hospitalIncByEmerTypeData=hospitalIncidentData.getJSONArray("hospitalIncByEmerTypeData");							
			}	
			modelView.addObject("hospitalIncData", hospitalIncData);
	        modelView.addObject("hospitalIncByEmerTypeData", hospitalIncByEmerTypeData);	
	        
	        if(hospitalIncData!=null && hospitalIncData.length()>0){
				JSONObject hospital = hospitalIncData.getJSONObject(0);
				hospitalName = hospital.getString("name");
			}
	        hospitalCaseData=ambulanceReportManager.getHospCaseData(districtName,maxDate,hospitalName);
	        hospitalCaseTimingData=ambulanceReportManager.getHospCaseTimingData(districtName,maxDate,hospitalName);
	        hospitalCaseComparisonData=ambulanceReportManager.getHospCaseComparisonData(districtName,maxDate,hospitalName);
	        
	        modelView.addObject("hospitalName", hospitalName);
	        modelView.addObject("hospitalCaseData", hospitalCaseData);
	        modelView.addObject("hospitalCaseTimingData", hospitalCaseTimingData);
	        modelView.addObject("hospitalCaseComparisonData", hospitalCaseComparisonData);
	        
		}finally{			
			
		}
		return modelView;
	}
		
	@RequestMapping(value="/ambulanceCaseReportOnSearch", method=RequestMethod.POST)
	public @ResponseBody void getAmbulanceCaseReportOnSearch(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONObject data=null;
		JSONArray ambIncTableData = null;
		String hospitalName = null;
		JSONArray ambIncByEmerTypeData = null;
		JSONArray hospitalIncData = null;
		JSONArray hospitalIncByEmerTypeData = null;
		JSONArray hospitalCaseData = null;
		JSONArray hospitalCaseTimingData = null;
		JSONArray hospitalCaseComparisonData = null;
		try{
			String date=request.getParameter("date");
			String districtName=request.getParameter("districtName");
			String searchMode=request.getParameter("searchMode");
			data=new JSONObject();
			
			ambIncByEmerTypeData=ambulanceReportManager.getAmbIncByEmerTypeData(districtName,date);				
			JSONObject hospitalIncidentData=ambulanceReportManager.getHospitalIncData(districtName,date);
			if(hospitalIncidentData!=null && hospitalIncidentData.length()>0){
				hospitalIncData=hospitalIncidentData.getJSONArray("hospitalIncData");
				hospitalIncByEmerTypeData=hospitalIncidentData.getJSONArray("hospitalIncByEmerTypeData");							
			}
	        if(hospitalIncData!=null && hospitalIncData.length()>0){
				JSONObject hospital = hospitalIncData.getJSONObject(0);
				hospitalName = hospital.getString("name");
			}
	        hospitalCaseData=ambulanceReportManager.getHospCaseData(districtName,date,hospitalName);
	        hospitalCaseTimingData=ambulanceReportManager.getHospCaseTimingData(districtName,date,hospitalName);
	        hospitalCaseComparisonData=ambulanceReportManager.getHospCaseComparisonData(districtName,date,hospitalName);
	        
	        if("onSearch".equalsIgnoreCase(searchMode)){
	        	ambIncTableData=ambulanceReportManager.getAmbIncTableData(date);
	        	data.put("ambIncTableData", ambIncTableData);
	        }
			
			data.put("ambIncByEmerTypeData", ambIncByEmerTypeData);
	        data.put("hospitalIncData", hospitalIncData);
	        data.put("hospitalIncByEmerTypeData", hospitalIncByEmerTypeData);
	        data.put("hospitalName", hospitalName);
	        data.put("hospitalCaseData", hospitalCaseData);
	        data.put("hospitalCaseTimingData", hospitalCaseTimingData);
	        data.put("hospitalCaseComparisonData", hospitalCaseComparisonData);
			out.println(data);
		}
		finally{}
	}
	
	@RequestMapping(value="/108HomePage", method=RequestMethod.GET)
	public ModelAndView displayHomePage(HttpServletRequest request) throws JSONException
	{	
		ModelAndView modelView=new ModelAndView();
		modelView.setViewName("108HomePageAction");
	   return modelView;
	}
		
	@RequestMapping(value="/totalCaseAvailed", method=RequestMethod.GET)
	public ModelAndView HcdmVisualizer(HttpServletRequest request,HttpServletResponse response) throws JSONException{
		ModelAndView model=null;	
		JSONArray treeChartArray=null;
		JSONArray sunburstArray=null;
		JSONArray talukCaseCount=null;
		JSONArray villageCaseCount=null;
		JSONArray talukCaseCountTableData=null;
		JSONArray villageCaseCountTableData=null;
		String emgData=null;
		String distData=null;
		JSONArray gisTotalCasesAvailedList = null;
		try {
			model=new ModelAndView();		
		    model.setViewName("108TotalCaseAvailed");
		    
			treeChartArray = ambulanceReportManager.getChartArray("TreeChart",null);
			model.addObject("treeChartArray", treeChartArray);		
		    
		    String districtName=null;
		    if(treeChartArray!=null && treeChartArray.length()>0){
			    JSONObject treeChartObj = treeChartArray.getJSONObject(0);
			    districtName = treeChartObj.getString("name");
		    }
		    model.addObject("districtName", districtName);	
		    
		    sunburstArray = ambulanceReportManager.getChartArray("SunBurst",districtName);
		    model.addObject("sunburstArray", sunburstArray);	
		    
		    JSONObject caseCountData=ambulanceReportManager.getTotalCaseAvailedBarArray(districtName);
			if(caseCountData!=null && caseCountData.length()>0){
				talukCaseCount=caseCountData.getJSONArray("talukCaseData");
				villageCaseCount=caseCountData.getJSONArray("talukByVillageCaseData");							
			}	
			model.addObject("talukCaseCount", talukCaseCount);
			model.addObject("villageCaseCount", villageCaseCount);	
			
			talukCaseCountTableData = ambulanceReportManager.getTalukCaseCountTableData(districtName);
			model.addObject("talukCaseCountTableData", talukCaseCountTableData);
			
			String talukName=null;
			if(talukCaseCountTableData!=null && talukCaseCountTableData.length()>0){
				JSONObject talukCaseCountData = talukCaseCountTableData.getJSONObject(0);
				talukName = talukCaseCountData.getString("talukName");
			}
		   
			villageCaseCountTableData = ambulanceReportManager.getVillageCaseCountTableData(districtName,talukName);
			model.addObject("villageCaseCountTableData", villageCaseCountTableData);
			
			emgData=ambulanceReportManager.getOnLoadEmgTypeScrollList(districtName);
			model.addObject("emgData", emgData);
			
			distData=ambulanceReportManager.getOnLoadDistScrollList();
			model.addObject("distData", distData);
			
			gisTotalCasesAvailedList=ambulanceReportManager.getGisTotalCasesAvailedData(districtName);
			 model.addObject("gisTotalCasesAvailedList", gisTotalCasesAvailedList);
		  
	    }finally{}
		    
		return model;
	}
		
	@RequestMapping(value="/totalCaseAvailedOnChange", method=RequestMethod.POST)
	public @ResponseBody void getTotalCaseAvailedOnChange(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONObject data=null;			
		JSONArray sunburstArray=null;
		JSONArray talukCaseCount=null;
		JSONArray villageCaseCount=null;
		JSONArray talukCaseCountTableData=null;
		JSONArray villageCaseCountTableData=null;
		JSONArray gisTotalCasesAvailedList=null;
		String emgData=null;
		try{				
			String districtName=request.getParameter("districtName");				
			data=new JSONObject();			
			
			sunburstArray = ambulanceReportManager.getChartArray("SunBurst",districtName);
		    
		    JSONObject caseCountData=ambulanceReportManager.getTotalCaseAvailedBarArray(districtName);
			if(caseCountData!=null && caseCountData.length()>0){
				talukCaseCount=caseCountData.getJSONArray("talukCaseData");
				villageCaseCount=caseCountData.getJSONArray("talukByVillageCaseData");							
			}	
			
			talukCaseCountTableData = ambulanceReportManager.getTalukCaseCountTableData(districtName);
			
			String talukName=null;
			if(talukCaseCountTableData!=null && talukCaseCountTableData.length()>0){
				JSONObject talukCaseCountData = talukCaseCountTableData.getJSONObject(0);
				talukName = talukCaseCountData.getString("talukName");
			}
		   
			villageCaseCountTableData = ambulanceReportManager.getVillageCaseCountTableData(districtName,talukName);
			
			gisTotalCasesAvailedList=ambulanceReportManager.getGisTotalCasesAvailedData(districtName);
			
			emgData=ambulanceReportManager.getOnLoadEmgTypeScrollList(districtName);
			
	       data.put("sunburstArray", sunburstArray);
	       data.put("talukCaseCount", talukCaseCount);
	       data.put("villageCaseCount", villageCaseCount);
	       data.put("talukCaseCountTableData", talukCaseCountTableData);
	       data.put("villageCaseCountTableData", villageCaseCountTableData);
	       data.put("gisTotalCasesAvailedList", gisTotalCasesAvailedList);
	       data.put("emgData", emgData);
	       out.println(data);
		}
		finally{}
	}
		
	@RequestMapping(value="/totalCaseAvailedSunburst", method=RequestMethod.POST)
	public @ResponseBody void getTotalCaseAvailedSunburst(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONArray sunburstArray=null;
		try{				
			String districtName=request.getParameter("districtName");	
			sunburstArray = ambulanceReportManager.getChartArray("SunBurst",districtName);			    
		  
	       out.println(sunburstArray);
		}
		finally{}
	}
		
	@RequestMapping(value="/totalCaseAvailedMap", method=RequestMethod.POST)
	public @ResponseBody void InstitutionMap(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONArray map=null;
		try{				
			
			String districtName=request.getParameter("districtName");
			map=ambulanceReportManager.getGisTotalCasesAvailedData(districtName);
	       out.println(map);
		}
		finally{}
	}
		
	@RequestMapping(value="/villageCaseCountTableData", method=RequestMethod.POST)
	public @ResponseBody void getVillageCaseCountTableData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONArray villageCaseCountTableData=null;
		try{				
			String districtName=request.getParameter("districtName");	
			String talukName=request.getParameter("talukName");	
			
			villageCaseCountTableData = ambulanceReportManager.getVillageCaseCountTableData(districtName,talukName);
			out.println(villageCaseCountTableData);
		}
		finally{}
	}
		
	@RequestMapping(value="/getBaseLocAmbByDistrict", method=RequestMethod.POST)
	public @ResponseBody void  baseLocAmbByDistrit(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
		PrintWriter out = response.getWriter();
		String districtName=null;
		String vehicleNo=null;
		JSONArray baseLocAmbList = null;
		JSONObject data=null;
		JSONArray gisBaseList = null;
		JSONArray streamingDataList = null;
		JSONArray gisPathStatusList = null;
		try{			
			districtName=request.getParameter("district");
			 
			baseLocAmbList=ambulanceReportManager.getBaseLocAmbData(districtName);	
			gisBaseList=ambulanceReportManager.getGisBaseData(districtName);	
			if(gisBaseList!=null && gisBaseList.length()>0){
				JSONArray district = gisBaseList.getJSONArray(0);
				vehicleNo = district.getString(3);
			}
			 
			streamingDataList=ambulanceReportManager.getGPSSteamingDataList(districtName,vehicleNo);				 
			gisPathStatusList=ambulanceReportManager.getGisPathStatusData(districtName,vehicleNo);
			 
			data=new JSONObject();	
			data.put("baseLocAmbList", baseLocAmbList);
			data.put("gisBaseList", gisBaseList);
			data.put("vehicleNo", vehicleNo);
			data.put("streamingDataList", streamingDataList);
			data.put("gisPathStatusList", gisPathStatusList);
			data.put("districtName", districtName);
			out.println(data);
		}finally{
		}		
	}
		
	@RequestMapping(value="/getGPSStatusStreamingData", method=RequestMethod.POST)
	public @ResponseBody void  streamingData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
		PrintWriter out = response.getWriter();
		String districtName=null;
		String vehicleNo=null;
		JSONArray streamingDataList = null;
		JSONObject data=null;
		JSONArray gisPathStatusList = null;
		JSONArray gisBaseList =null;
		try{
			districtName=request.getParameter("districtName");
			vehicleNo=request.getParameter("vehicleNo");
			 
			gisBaseList=ambulanceReportManager.getGisBaseData(districtName);	
			streamingDataList=ambulanceReportManager.getGPSSteamingDataList(districtName,vehicleNo);				 
			gisPathStatusList=ambulanceReportManager.getGisPathStatusData(districtName,vehicleNo);
			 
			data=new JSONObject();	
			data.put("gisBaseList", gisBaseList);
			data.put("vehicleNo", vehicleNo);
			data.put("streamingDataList", streamingDataList);
			data.put("gisPathStatusList", gisPathStatusList);			
			out.println(data);
		}finally{
		}			
	}	
		
	@RequestMapping(value="/108TotalTrips", method=RequestMethod.GET)
	public ModelAndView showOnLoadData(HttpServletRequest request) throws IOException, JSONException,SHDRCException{
		ModelAndView modelAndView=null;
		String districtName=null;
		JSONObject ambTotTripsTypeData = null;
		JSONArray ambTotalTripsData = null;
		JSONArray ambTotalTripsByTypeData = null;
		JSONObject ambulanceCaseType = null;
		JSONArray ambulanceCaseTypeData = null;
		JSONArray ambulanceCaseByTypeData = null;
		JSONArray ambBaseLocTickerData = null;
		String talukScrollData = null;
		String villageScrollData = null;
		JSONArray predictionScrollData = null;
		try{
			modelAndView=new ModelAndView();
			JSONArray array = ambulanceReportManager.getTotalTripsData();
			if(array!=null){
				JSONObject district = array.getJSONObject(0);
				districtName = district.getString("districtName");
			}
			 
			 ambTotTripsTypeData=ambulanceReportManager.getAmbTotTripsTypeData(districtName);
			 if(ambTotTripsTypeData!=null && ambTotTripsTypeData.length()>0){
				 ambTotalTripsData =ambTotTripsTypeData.getJSONArray("ambTotalTripsData");
				 ambTotalTripsByTypeData=ambTotTripsTypeData.getJSONArray("ambTotalTripsByTypeData");							
			 }	
			 
			 ambulanceCaseType=ambulanceReportManager.ambulanceCaseType(districtName);
			 if(ambulanceCaseType!=null && ambulanceCaseType.length()>0){
				 ambulanceCaseTypeData =ambulanceCaseType.getJSONArray("ambulanceCaseTypeData");
				 ambulanceCaseByTypeData=ambulanceCaseType.getJSONArray("ambulanceCaseByTypeData");							
				}
			 
			 ambBaseLocTickerData = ambulanceReportManager.getAmbBaseLocTickerData();				 
			 talukScrollData=ambulanceReportManager.getTalukTickerData(districtName);
			 villageScrollData=ambulanceReportManager.getVillageTickerData(districtName);		
			 predictionScrollData=ambulanceReportManager.getPredictionScrollData();		
			 
			 modelAndView.setViewName("108TotalTripsAction");
			 modelAndView.addObject("jsonData", array);
			 modelAndView.addObject("districtName", districtName);
			 modelAndView.addObject("ambTotalTripsData", ambTotalTripsData);
			 modelAndView.addObject("ambTotalTripsByTypeData", ambTotalTripsByTypeData);
			 modelAndView.addObject("ambulanceCaseTypeData", ambulanceCaseTypeData);
			 modelAndView.addObject("ambulanceCaseByTypeData", ambulanceCaseByTypeData);
			 modelAndView.addObject("ambBaseLocTickerData", ambBaseLocTickerData);
			 modelAndView.addObject("talukScrollData", talukScrollData);
			 modelAndView.addObject("villageScrollData", villageScrollData);
			 modelAndView.addObject("predictionScrollData", predictionScrollData);
		}finally{
		}
		return modelAndView;
	}
		
	@RequestMapping(value="/getScrollByDistrict", method=RequestMethod.POST)
	public @ResponseBody void  getScrollByDistrict(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
		PrintWriter out = response.getWriter();
		JSONObject data = null;
		String districtName=null;
		String talukScrollData = null;
		String villageScrollData = null;
		try{
			districtName = request.getParameter("districtName");
			talukScrollData=ambulanceReportManager.getTalukTickerData(districtName);
			villageScrollData=ambulanceReportManager.getVillageTickerData(districtName);	
			
			data=new JSONObject();	
			data.put("talukScrollData", talukScrollData);
			data.put("villageScrollData", villageScrollData);
			out.println(data);
		}finally{
		}
		
	}	
		
	@RequestMapping(value="/getTotalTripsValuesByDistrict", method=RequestMethod.POST)
	public @ResponseBody void  showOnLoadDataByDistrit(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
		PrintWriter out = response.getWriter();
		String districtName=null;
		JSONObject ambTotTripsTypeData = null;
		JSONArray ambTotalTripsData = null;
		JSONArray ambTotalTripsByTypeData = null;
		JSONObject data=null;
		JSONObject ambulanceCaseType = null;
		JSONArray ambulanceCaseTypeData = null;
		JSONArray ambulanceCaseByTypeData = null;
		try{
			
			 districtName=request.getParameter("district");
			 ambTotTripsTypeData=ambulanceReportManager.getAmbTotTripsTypeData(districtName);
			 if(ambTotTripsTypeData!=null && ambTotTripsTypeData.length()>0){
				 ambTotalTripsData =ambTotTripsTypeData.getJSONArray("ambTotalTripsData");
				 ambTotalTripsByTypeData=ambTotTripsTypeData.getJSONArray("ambTotalTripsByTypeData");							
				}	
			 ambulanceCaseType=ambulanceReportManager.ambulanceCaseType(districtName);
			 if(ambulanceCaseType!=null && ambulanceCaseType.length()>0){
				 ambulanceCaseTypeData =ambulanceCaseType.getJSONArray("ambulanceCaseTypeData");
				 ambulanceCaseByTypeData=ambulanceCaseType.getJSONArray("ambulanceCaseByTypeData");							
				}
			 data=new JSONObject();	
			 data.put("ambTotalTripsData", ambTotalTripsData);
			 data.put("ambTotalTripsByTypeData", ambTotalTripsByTypeData);
			 data.put("ambulanceCaseTypeData", ambulanceCaseTypeData);
			 data.put("ambulanceCaseByTypeData", ambulanceCaseByTypeData);
			 out.println(data);
		}finally{
		}		
	}		
				
	@RequestMapping(value="/108EmergencyType", method=RequestMethod.GET)
	public ModelAndView onLoadEmergencyTypeReport(HttpServletRequest request) throws JSONException
	{	
		ModelAndView modelView=null;
		List<CommonStringList> districtsList=null;
		String maxDate=null;
		JSONArray ambIncTableData = null;
		String districtName = null;
		String hospitalName = null;
		JSONArray ambIncByEmerTypeData = null;
		JSONArray hospitalIncData = null;
		JSONArray hospitalIncByEmerTypeData = null;
		List<CommonStringList> predictionDistrictsList=null;
		List<CommonStringList> predictionEmerTypeList=null;
		JSONArray predictionTalukData=null;
		JSONArray predictionVillageData=null;
		String districts=null;
		String talukName = null;
		String emergencyType = null ;
		try{
			modelView = new ModelAndView();
			modelView.setViewName("108EmergencyTypeReport");
	        emergencyType =  request.getParameter("emergencyType");
			
			Integer directorateId = 1;
			maxDate = ambulanceReportManager.getEmergencyTypeAmbulanceCaseReportMaxDate(emergencyType);
			districtsList = ambulanceReportManager.getEmergencyTypeOnLoadDistrictList(emergencyType);				
			ambIncTableData=ambulanceReportManager.getEmergencyTypeAmbIncTableData(maxDate,emergencyType);
			if(ambIncTableData!=null){
				JSONObject district = ambIncTableData.getJSONObject(0);
				districtName = district.getString("label");
			}
			
			ambIncByEmerTypeData=ambulanceReportManager.getEmergencyTypeAmbIncByEmerTypeData(districtName,maxDate,emergencyType);
			modelView.addObject("emergencyType", emergencyType); 
			modelView.addObject("districts", districtsList);
	        modelView.addObject("directorateId", directorateId);
	        modelView.addObject("maxDate", maxDate);	
	        modelView.addObject("ambIncTableData", ambIncTableData);	
	        modelView.addObject("districtName", districtName);	
	        modelView.addObject("ambIncByEmerTypeData", ambIncByEmerTypeData);			      
			
			JSONObject hospitalIncidentData=ambulanceReportManager.getEmergencyTypeHospitalIncData(districtName,maxDate,emergencyType);
			if(hospitalIncidentData!=null && hospitalIncidentData.length()>0){
				hospitalIncData=hospitalIncidentData.getJSONArray("hospitalIncData");
				hospitalIncByEmerTypeData=hospitalIncidentData.getJSONArray("hospitalIncByEmerTypeData");							
			}	
			modelView.addObject("hospitalIncData", hospitalIncData);
	        modelView.addObject("hospitalIncByEmerTypeData", hospitalIncByEmerTypeData);	
	        
	        if(hospitalIncData!=null && hospitalIncData.length()>0){
				JSONObject hospital = hospitalIncData.getJSONObject(0);
				hospitalName = hospital.getString("name");
			}	        
            modelView.addObject("hospitalName", hospitalName);
	        
			predictionDistrictsList = ambulanceReportManager.getEmergencyTypePredictionDistrictList(emergencyType);
			predictionEmerTypeList = ambulanceReportManager.getPredictionEmerTypeList();
			
			 if(Util.isNotNull(predictionDistrictsList)){
		        	CommonStringList predictionDistrict = predictionDistrictsList.get(0);
		        	districts=predictionDistrict.getName();
			 }
			 
			predictionTalukData = ambulanceReportManager.getPredictionTalukData(districts,emergencyType);				
			
			if(predictionTalukData != null){
				JSONObject taluk = predictionTalukData.getJSONObject(0);
				talukName = taluk.getString("district");
			}
			
			predictionVillageData = ambulanceReportManager.getPredictionVillageData(districts,talukName,emergencyType);	
			
	        modelView.addObject("predictionDistrictsList", predictionDistrictsList);
	        modelView.addObject("predictionEmerTypeList", predictionEmerTypeList);
	        modelView.addObject("PredictionDistrict", districts);
	        modelView.addObject("predictionTalukData", predictionTalukData);
	        modelView.addObject("predictionVillageData", predictionVillageData);
	        modelView.addObject("talukName",talukName);
	        
		}finally{			
		}
		return modelView;
	}
		
	@RequestMapping(value="/108EmergencyTypeOnSearch", method=RequestMethod.POST)
	public @ResponseBody void getEmergencyTypeReportOnSearch(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONObject data=null;
		JSONArray ambIncTableData = null;
		String hospitalName = null;
		JSONArray ambIncByEmerTypeData = null;
		JSONArray hospitalIncData = null;
		JSONArray hospitalIncByEmerTypeData = null;
		JSONArray hospitalCaseData = null;
		JSONArray hospitalCaseTimingData = null;
		JSONArray hospitalCaseComparisonData = null;
		JSONArray predictionTalukData=null;
		JSONArray predictionVillageData=null;
		String predictionTalukName = null;	
		String emergencyType = null ;
		try{
			emergencyType =  request.getParameter("emergencyType");
			String districtName=request.getParameter("districtName");
			String searchLevel=request.getParameter("searchLevel");
			data=new JSONObject();
			
			if("districtChange".equalsIgnoreCase(searchLevel)){
				String date=request.getParameter("date");
				String searchMode=request.getParameter("searchMode");
				
				if("onSearch".equalsIgnoreCase(searchMode)){
			      	ambIncTableData=ambulanceReportManager.getEmergencyTypeAmbIncTableData(date,emergencyType);
			       	data.put("ambIncTableData", ambIncTableData);
			    }
				
				ambIncByEmerTypeData=ambulanceReportManager.getEmergencyTypeAmbIncByEmerTypeData(districtName,date,emergencyType);				
				JSONObject hospitalIncidentData=ambulanceReportManager.getEmergencyTypeHospitalIncData(districtName,date,emergencyType);
				if(hospitalIncidentData!=null && hospitalIncidentData.length()>0){
					hospitalIncData=hospitalIncidentData.getJSONArray("hospitalIncData");
					hospitalIncByEmerTypeData=hospitalIncidentData.getJSONArray("hospitalIncByEmerTypeData");							
				}
				data.put("ambIncByEmerTypeData", ambIncByEmerTypeData);
		        data.put("hospitalIncData", hospitalIncData);
		        data.put("hospitalIncByEmerTypeData", hospitalIncByEmerTypeData);
		        data.put("hospitalName", hospitalName);
		        data.put("hospitalCaseData", hospitalCaseData);
		        data.put("hospitalCaseTimingData", hospitalCaseTimingData);
		        data.put("hospitalCaseComparisonData", hospitalCaseComparisonData);
				
				predictionTalukData = ambulanceReportManager.getPredictionTalukData(districtName,emergencyType);	
				data.put("predictionTalukData", predictionTalukData);
					
				if(predictionTalukData != null){
					JSONObject taluk = predictionTalukData.getJSONObject(0);
					predictionTalukName = taluk.getString("district");
				}
			}
			
			if("talukChange".equalsIgnoreCase(searchLevel)){
				predictionTalukName = request.getParameter("predictionTalukName");
			}
			
			predictionVillageData = ambulanceReportManager.getPredictionVillageData(districtName,predictionTalukName,emergencyType);					
       
	        data.put("predictionVillageData", predictionVillageData);
	        data.put("predictionTalukName", predictionTalukName);
			out.println(data);
		}
		finally{}
	}
		
	@RequestMapping(value="/108UploadExcel", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		ModelAndView modelView=null;	
		try { 
			modelView=uploadExcelOnLoad();
		}catch (Exception e) {
		} finally{}
		return modelView;		
	}
		
	private ModelAndView uploadExcelOnLoad(){
		ModelAndView modelView=null;
		try { 
		    modelView=new ModelAndView();
		    modelView.setViewName("108FileUpload");		
		    
		    modelView.addObject("currentHours", Util.getCurrentHours());
		    modelView.addObject("currentDate", Util.getStrDate(new Date()));
		    modelView.addObject("directorateList", getDirectorateList());		   
		   
			List<Integer> yearList= Util.yearList;
			modelView.addObject("yearList", yearList);
		    
		    List<CommonStringList> freuencyList= Util.freuencyList;
		    
		    List<CommonStringList> subList = freuencyList.subList(0, 2);
		    modelView.addObject("freuencyList", subList);
		    
		    List<CommonStringList> weeksList= Util.weeksList;
		    modelView.addObject("weeksList", weeksList);
		    
		    List<CommonStringList> quarterList= Util.quarterList;
		    modelView.addObject("quarterList", quarterList);		
		    
		    List<CommonStringList> monthsList= Util.monthsList;
		    modelView.addObject("monthsList", monthsList);
		}catch (Exception e) {
		} finally{}
	    return modelView;	    
	}
	
	private List<CommonStringList> getDirectorateList(){
		List<String> userRoleList = UserInfo.getUserRoleList(); 
		boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
		String role = null;
		if(!isAllDirectorate)
			role = UserInfo.getRolesForUser();
			List<CommonStringList> directorateList=null;
			try{
				if(!userRoleList.contains("CMCHIS")){
					directorateList= ambulanceReportManager.getDirecorateList(isAllDirectorate,role);
				}else{
					directorateList =new ArrayList<CommonStringList>();
					CommonStringList commonStringList=new CommonStringList();
					commonStringList.setId(17);
					commonStringList.setName("Chief Ministers Comprehensive Health Insurance Scheme");
					directorateList.add(commonStringList);
				}			 
			}catch (Exception e) {
			} finally{}
		return directorateList;
	}
		
	@RequestMapping(value="/108cycleTime", method=RequestMethod.GET)
	public ModelAndView cycleTime(HttpServletRequest request){			
		ModelAndView modelView=null;
		String districtName = null;
		JSONArray cycleTimeData = null;
		JSONArray cycleTimeStackData = null;
		JSONArray cycleTimePieData = null;
		JSONObject cycleTimeComparisonData = null;
		JSONArray cycleTimeFirstSeriesData =null;
		JSONArray cycleTimeSecondSeriesData = null;
		try { 				   
			modelView=new ModelAndView();	    
			modelView.setViewName("cycleTimeAction");
				    
			cycleTimeData = ambulanceReportManager.getCycleTimeData();
			if(cycleTimeData!=null && cycleTimeData.length()>0){
				JSONObject firstObj = cycleTimeData.getJSONObject(0);
		        districtName = firstObj.getString("State");
		    }
				    
		    cycleTimeStackData = ambulanceReportManager.getCycleTimeDataStack(districtName,"MAX");
		    cycleTimePieData = ambulanceReportManager.getCycleTimeDataPie(districtName);
		    cycleTimeComparisonData = ambulanceReportManager.getCycleTimeComparisonData(districtName,"MAX");
		    if(cycleTimeComparisonData!=null && cycleTimeComparisonData.length()>0){
		    	cycleTimeFirstSeriesData=cycleTimeComparisonData.getJSONArray("cycleTimeFirstSeriesData");
		    	cycleTimeSecondSeriesData=cycleTimeComparisonData.getJSONArray("cycleTimeSecondSeriesData");
			}
		    
		    modelView.addObject("districtName", districtName);
		    modelView.addObject("cycleTimeData", cycleTimeData);
		    modelView.addObject("cycleTimeStackData", cycleTimeStackData);
		    modelView.addObject("cycleTimePieData", cycleTimePieData);
		    modelView.addObject("cycleTimeFirstSeriesData", cycleTimeFirstSeriesData);
		    modelView.addObject("cycleTimeSecondSeriesData", cycleTimeSecondSeriesData);
		  
			} catch (Exception e) {
				e.printStackTrace();
			}finally{			
			} 
	   return modelView;
	}
		
	@RequestMapping(value="cycleTimeDistrictChange", method=RequestMethod.POST)
	public @ResponseBody void getcycleTimeStackOnSearch(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONObject data=null;
		JSONArray cycleTimeStackData = null;
		JSONArray cycleTimePieData = null;
		JSONObject cycleTimeComparisonData = null;
		JSONArray cycleTimeFirstSeriesData =null;
		JSONArray cycleTimeSecondSeriesData = null;
		try{
			String districtName=request.getParameter("districtName");
			data=new JSONObject();
			
			cycleTimeStackData = ambulanceReportManager.getCycleTimeDataStack(districtName,"MAX");
		    cycleTimePieData = ambulanceReportManager.getCycleTimeDataPie(districtName);
		    cycleTimeComparisonData = ambulanceReportManager.getCycleTimeComparisonData(districtName,"MAX");
		    if(cycleTimeComparisonData!=null && cycleTimeComparisonData.length()>0){
		    	cycleTimeFirstSeriesData=cycleTimeComparisonData.getJSONArray("cycleTimeFirstSeriesData");
		    	cycleTimeSecondSeriesData=cycleTimeComparisonData.getJSONArray("cycleTimeSecondSeriesData");
			}
			
	        data.put("cycleTimeStackData", cycleTimeStackData);
	        data.put("cycleTimePieData", cycleTimePieData);
	        data.put("cycleTimeFirstSeriesData", cycleTimeFirstSeriesData);
	        data.put("cycleTimeSecondSeriesData", cycleTimeSecondSeriesData);
			out.println(data);
		}
		finally{}
	}
		
	@RequestMapping(value="getMinMaxValueStack", method=RequestMethod.POST)
	public @ResponseBody void getMinMaxValueStackList(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONArray cycleTimeStackData = null;
		String buttonClick=null;
		String districtName = null;
		try{
			districtName=request.getParameter("districtName");
			buttonClick=request.getParameter("buttonClick");				
			cycleTimeStackData=ambulanceReportManager.getCycleTimeDataStack(districtName,buttonClick);
			out.println(cycleTimeStackData);
		}
		finally{}
	}
		
	@RequestMapping(value="getMinMaxValueBar", method=RequestMethod.POST)
	public @ResponseBody void getMinMaxValueBar(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONObject cycleTimeComparisonData = null;
		JSONArray cycleTimeFirstSeriesData =null;
		JSONArray cycleTimeSecondSeriesData = null;
		String buttonClick=null;
		String districtName = null;
		JSONObject data=null;
		try{
			districtName=request.getParameter("districtName");
			buttonClick=request.getParameter("buttonClick");				
			cycleTimeComparisonData = ambulanceReportManager.getCycleTimeComparisonData(districtName,buttonClick);
		    if(cycleTimeComparisonData!=null && cycleTimeComparisonData.length()>0){
		    	cycleTimeFirstSeriesData=cycleTimeComparisonData.getJSONArray("cycleTimeFirstSeriesData");
		    	cycleTimeSecondSeriesData=cycleTimeComparisonData.getJSONArray("cycleTimeSecondSeriesData");
			}
		    data=new JSONObject();
		    data.put("cycleTimeFirstSeriesData", cycleTimeFirstSeriesData);
		    data.put("cycleTimeSecondSeriesData", cycleTimeSecondSeriesData);
		    out.println(data);
		}
		finally{}
	}		
		
	@RequestMapping(value="/kilometersCovered", method=RequestMethod.GET)
    public ModelAndView kilometersCovered(HttpServletRequest request){                                
       ModelAndView modelView=null;
       JSONArray kilometersCoveredAmbData=null;
       JSONArray kilometersCoveredDrilldownData = null;
       String districtName=null;
       String ambNo = null;
       int kms = 0;
       String mins = null;
       JSONArray kmCoveredFirstSeriesData=null;
       JSONArray kmCoveredSecondSeriesData=null;
       try {                            
            modelView=new ModelAndView();
            modelView.setViewName("kilometersCoveredAction");
            
            kilometersCoveredDrilldownData = ambulanceReportManager.getKilometersCoveredDrilldown();
            if(kilometersCoveredDrilldownData!=null && kilometersCoveredDrilldownData.length()>0){
            	JSONObject firstObj = kilometersCoveredDrilldownData.getJSONObject(0);
            	districtName = firstObj.getString("State");
            }
            String buttonClick="ift";
            JSONObject kmCoveredMultiSeriesData= ambulanceReportManager.getKmCoveredMultiSeriesData(districtName,buttonClick);
            if(kmCoveredMultiSeriesData!=null && kmCoveredMultiSeriesData.length()>0){
                kmCoveredFirstSeriesData=kmCoveredMultiSeriesData.getJSONArray("kmCoveredFirstSeriesData");
                kmCoveredSecondSeriesData=kmCoveredMultiSeriesData.getJSONArray("kmCoveredSecondSeriesData");
            }
            
            if(kmCoveredFirstSeriesData!=null && kmCoveredFirstSeriesData.length()>0){
            	JSONObject firstObj = kmCoveredFirstSeriesData.getJSONObject(0);
            	ambNo = firstObj.getString("label");
                kms = firstObj.getInt("value");
            }
            if(kmCoveredSecondSeriesData!=null && kmCoveredSecondSeriesData.length()>0){
            	JSONObject firstObj = kmCoveredSecondSeriesData.getJSONObject(0);
                mins = firstObj.getString("value");
            }

            kilometersCoveredAmbData = ambulanceReportManager.getkilometersCoveredAmbulanceData(districtName,ambNo,kms,mins);
        
            modelView.addObject("kilometersCoveredAmbData", kilometersCoveredAmbData);
            modelView.addObject("kilometersCoveredDrilldownData", kilometersCoveredDrilldownData);          
            modelView.addObject("kmCoveredFirstSeriesData", kmCoveredFirstSeriesData);
            modelView.addObject("kmCoveredSecondSeriesData", kmCoveredSecondSeriesData);
            modelView.addObject("districtName", districtName);
            modelView.addObject("VehicalNo", ambNo);
          
            } catch (Exception e) {
            	e.printStackTrace();
            }finally{                                 
            } 
       return modelView;
    }        
        
    @RequestMapping(value="/getIftandNonIftValues", method=RequestMethod.POST)
    public @ResponseBody void getgetIftandNonIftValues(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
    	PrintWriter out = response.getWriter();	        
    	JSONObject data=null;
    	String ambNo = null;
    	int kms = 0;
    	String mins = null;
    	JSONArray kmCoveredFirstSeriesData=null;
    	JSONArray kmCoveredSecondSeriesData=null;
    	JSONArray kilometersCoveredAmbData=null;
    	try{
    		String buttonClick=request.getParameter("buttonClick");
    		String districtName=request.getParameter("district");
    		JSONObject kmCoveredMultiSeriesData= ambulanceReportManager.getKmCoveredMultiSeriesData(districtName,buttonClick);
            if(kmCoveredMultiSeriesData!=null && kmCoveredMultiSeriesData.length()>0){
               kmCoveredFirstSeriesData=kmCoveredMultiSeriesData.getJSONArray("kmCoveredFirstSeriesData");
               kmCoveredSecondSeriesData=kmCoveredMultiSeriesData.getJSONArray("kmCoveredSecondSeriesData");
            }
            if(kmCoveredFirstSeriesData!=null && kmCoveredFirstSeriesData.length()>0){
            	JSONObject firstObj = kmCoveredFirstSeriesData.getJSONObject(0);
            	ambNo = firstObj.getString("label");
                kms = firstObj.getInt("value");
            }
            if(kmCoveredSecondSeriesData!=null && kmCoveredSecondSeriesData.length()>0){
            	JSONObject firstObj = kmCoveredSecondSeriesData.getJSONObject(0);
                mins = firstObj.getString("value");
            }
            kilometersCoveredAmbData = ambulanceReportManager.getkilometersCoveredAmbulanceData(districtName,ambNo,kms,mins);
                        
            data=new JSONObject();              
            data.put("kmCoveredFirstSeriesData", kmCoveredFirstSeriesData);
            data.put("kmCoveredSecondSeriesData", kmCoveredSecondSeriesData);
            data.put("kilometersCoveredAmbData", kilometersCoveredAmbData);
            out.println(data);
        }finally{
        }
    }
        
    @RequestMapping(value="/kilometersCoveredAmbDataValues", method=RequestMethod.POST)
    public @ResponseBody void kilometersCoveredAmbDataValues(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
    	PrintWriter out = response.getWriter();
    	JSONObject data=null;JSONArray kilometersCoveredAmbData=null;
        String ambNo = null;
        int kms = 0;
        String mins = null;
        String districtName = null;
        try{
           districtName=request.getParameter("district");
           ambNo=request.getParameter("ambNumber");
           kms=(Util.isNotNull(request.getParameter("kms"))?Integer.parseInt(request.getParameter("kms")):null);
           mins=request.getParameter("mints");
               kilometersCoveredAmbData = ambulanceReportManager.getkilometersCoveredAmbulanceData(districtName,ambNo,kms,mins);
   
		       data=new JSONObject();              
		       data.put("kilometersCoveredAmbData", kilometersCoveredAmbData);
	       data.put("districtName", districtName);
	       data.put("ambNo", ambNo);
	       out.println(data);
	   }finally{
	    }
    }
        
    @RequestMapping(value="/108LiveGPSStatus", method=RequestMethod.GET)
    public ModelAndView showOnLiveGPSStatusData(HttpServletRequest request) throws IOException, JSONException,SHDRCException{
    	ModelAndView modelAndView=null;
        String districtName=null;
        JSONArray baseLocAmbList = null;
        JSONArray gisBaseList = null;
        JSONArray streamingDataList = null;
        String vehicleNo = null;
        try{
        	modelAndView=new ModelAndView();
        	modelAndView.setViewName("108LiveGPSStatusAction");
        	JSONArray array = ambulanceReportManager.getTotalNoOfAmbList();
        	if(array!=null){
        		JSONObject district = array.getJSONObject(0);
        		districtName = district.getString("districtName");
        	}	                 
        	modelAndView.addObject("jsonData", array);
        	modelAndView.addObject("districtName", districtName);
        	baseLocAmbList=ambulanceReportManager.getBaseLocAmbData(districtName);                    
            gisBaseList=ambulanceReportManager.getGisBaseData(districtName);    
            if(gisBaseList!=null && gisBaseList.length()>0){
            	JSONArray district = gisBaseList.getJSONArray(0);
                vehicleNo = district.getString(3);
            }    
                 
            URL url = new URL("http://59.144.132.170/getweb/rest/webservice/getdatadistrict?district="+districtName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
            	throw new RuntimeException("Failed : HTTP error code : "
                                                             + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
         	String output;
         	JSONArray gisBaseListNew = null;                
         	while ((output = br.readLine()) != null) {
                gisBaseListNew = new JSONArray(output);                                      
         	}
         	JSONArray parentJSON=new JSONArray();
         	JSONArray childJsonArray=null;
         	for (int i = 0; i < gisBaseListNew.length(); i++){
         		childJsonArray=new JSONArray();
         		JSONObject jsonObj = gisBaseListNew.getJSONObject(i);
         		childJsonArray.put(jsonObj.getString("vehicleno"));
         		childJsonArray.put(jsonObj.getString("latitude"));
         		childJsonArray.put(jsonObj.getString("longitude"));
         		childJsonArray.put(jsonObj.getString("vehicleno"));
         		childJsonArray.put(jsonObj.optString("flag", "Y"));
         		parentJSON.put(childJsonArray);
         	}
             
         	URL urln = new URL("http://59.144.132.170/getweb/rest/webservice/getdatatimeperiod?ambulanceno="+vehicleNo+"&timeperiod=30");
         	HttpURLConnection connc = (HttpURLConnection) urln.openConnection();
         	connc.setRequestMethod("POST");
         	connc.setRequestProperty("Accept", "application/json");

         	if (connc.getResponseCode() != 200) {
         		throw new RuntimeException("Failed : HTTP error code : " + connc.getResponseCode());
         	}

         	BufferedReader brr = new BufferedReader(new InputStreamReader((connc.getInputStream())));
            String outputn;
            JSONArray gisBaseListNewn = null;                
            while ((outputn = brr.readLine()) != null) {
            	gisBaseListNewn = new JSONArray(outputn);               
            }
            JSONArray parentJSONPath=new JSONArray();
            JSONArray childJsonArrayPath=null;
            for (int i = 0; i < gisBaseListNewn.length(); i++){
            	childJsonArrayPath=new JSONArray();
                JSONObject jsonObj = gisBaseListNewn.getJSONObject(i);
                childJsonArrayPath.put(jsonObj.getString("vehicleno"));
                childJsonArrayPath.put(jsonObj.getString("latitude"));
                childJsonArrayPath.put(jsonObj.getString("longitude"));
                childJsonArrayPath.put(jsonObj.getString("vehicleno"));
                childJsonArrayPath.put(jsonObj.optString("flag", "Y"));
                parentJSONPath.put(childJsonArrayPath);
            }
            
            if(parentJSON!=null && parentJSON.length()>0){
            	JSONArray district = parentJSON.getJSONArray(0);
                vehicleNo = district.getString(3);
            }
                 
            streamingDataList=ambulanceReportManager.getGPSSteamingDataList(districtName,vehicleNo);
            
            JSONArray ambBaseLocTickerData = null;
            String talukScrollData = null;
            String villageScrollData = null;
            JSONArray predictionScrollData = null;
            
            ambBaseLocTickerData = ambulanceReportManager.getAmbBaseLocTickerData();                        
            talukScrollData=ambulanceReportManager.getTalukTickerData(districtName);
            villageScrollData=ambulanceReportManager.getVillageTickerData(districtName);        
            predictionScrollData=ambulanceReportManager.getPredictionScrollData();  
            
			modelAndView.addObject("baseLocAmbList", baseLocAmbList);
			modelAndView.addObject("parentJSON", parentJSON);
			modelAndView.addObject("gisBaseList", gisBaseList);
			modelAndView.addObject("gisAmbCurrentLoc", streamingDataList);
			modelAndView.addObject("gisPathStatusList", parentJSONPath);
			modelAndView.addObject("vehicleNo", vehicleNo);
            modelAndView.addObject("ambBaseLocTickerData", ambBaseLocTickerData);
            modelAndView.addObject("talukScrollData", talukScrollData);
            modelAndView.addObject("villageScrollData", villageScrollData);
            modelAndView.addObject("predictionScrollData", predictionScrollData);
           
        }catch (IOException e) {
        }finally{
        }
        return modelAndView;
    }
        
    @RequestMapping(value="/getLiveBaseLocAmbByDistrict", method=RequestMethod.POST)
    public @ResponseBody void  liveBaseLocAmbByDistrit(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
    	PrintWriter out = response.getWriter();
    	String districtName=null;
    	String vehicleNo=null;
    	JSONArray baseLocAmbList = null;
    	JSONObject data=null;
    	JSONArray gisBaseList = null;
    	JSONArray streamingDataList = null;
        try{
        	districtName=request.getParameter("district");
            baseLocAmbList=ambulanceReportManager.getBaseLocAmbData(districtName);       
            gisBaseList=ambulanceReportManager.getGisBaseData(districtName);    
            if(gisBaseList!=null && gisBaseList.length()>0){
            	JSONArray district = gisBaseList.getJSONArray(0);
                vehicleNo = district.getString(3);
            }
                        
            URL url = new URL("http://59.144.132.170/getweb/rest/webservice/getdatadistrict?district="+districtName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
            	throw new RuntimeException("Failed : HTTP error code : "
                                                             + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            JSONArray gisBaseListNew = null;                
            while ((output = br.readLine()) != null) {
            	gisBaseListNew = new JSONArray(output);   
            }
            JSONArray parentJSON=new JSONArray();
            JSONArray childJsonArray=null;
            for (int i = 0; i < gisBaseListNew.length(); i++){
            	childJsonArray=new JSONArray();
                JSONObject jsonObj = gisBaseListNew.getJSONObject(i);
                childJsonArray.put(jsonObj.getString("vehicleno"));
                childJsonArray.put(jsonObj.getString("latitude"));
                childJsonArray.put(jsonObj.getString("longitude"));
                childJsonArray.put(jsonObj.getString("vehicleno"));
                childJsonArray.put(jsonObj.optString("flag", "Y"));
                parentJSON.put(childJsonArray);
            }
             
            URL urln = new URL("http://59.144.132.170/getweb/rest/webservice/getdatatimeperiod?ambulanceno="+vehicleNo+"&timeperiod=30");
            HttpURLConnection connc = (HttpURLConnection) urln.openConnection();
            connc.setRequestMethod("POST");
            connc.setRequestProperty("Accept", "application/json");

            if (connc.getResponseCode() != 200) {
            	throw new RuntimeException("Failed : HTTP error code : "
                                                             + connc.getResponseCode());
            }

            BufferedReader brr = new BufferedReader(new InputStreamReader((connc.getInputStream())));
            String outputn;
            JSONArray gisBaseListNewn = null;                
            while ((outputn = brr.readLine()) != null) {
            	gisBaseListNewn = new JSONArray(outputn);                      
            }
            JSONArray parentJSONPath=new JSONArray();
            JSONArray childJsonArrayPath=null;
            for (int i = 0; i < gisBaseListNewn.length(); i++){
            	childJsonArrayPath=new JSONArray();
                JSONObject jsonObj = gisBaseListNewn.getJSONObject(i);
                childJsonArrayPath.put(jsonObj.getString("vehicleno"));
                childJsonArrayPath.put(jsonObj.getString("latitude"));
                childJsonArrayPath.put(jsonObj.getString("longitude"));
                childJsonArrayPath.put(jsonObj.getString("vehicleno"));
                childJsonArrayPath.put(jsonObj.optString("flag", "Y"));
                parentJSONPath.put(childJsonArrayPath);
            }
                      
            streamingDataList=ambulanceReportManager.getGPSSteamingDataList(districtName,vehicleNo); 
                 
            data=new JSONObject();    
            data.put("baseLocAmbList", baseLocAmbList);
            data.put("gisBaseList", gisBaseList);
            data.put("vehicleNo", vehicleNo);
            data.put("parentJSON", parentJSON);
            data.put("streamingDataList", streamingDataList);
            data.put("gisPathStatusList", parentJSONPath);
            data.put("districtName", districtName);
            out.println(data);
        }finally{
        }
    }
    
    @RequestMapping(value="/getLiveGPSStatusStreamingData", method=RequestMethod.POST)
    public @ResponseBody void  liveStreamingData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
    	PrintWriter out = response.getWriter();
        String districtName=null;
        String vehicleNo=null;
        JSONObject data=null;
        JSONArray gisBaseList =null;
        try{
        	districtName=request.getParameter("districtName");
            vehicleNo=request.getParameter("vehicleNo");
                 
            URL url = new URL("http://59.144.132.170/getweb/rest/webservice/getdatadistrict?district="+districtName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
            	throw new RuntimeException("Failed : HTTP error code : "
                                                             + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            JSONArray gisBaseListNew = null;                
            while ((output = br.readLine()) != null) {
            	gisBaseListNew = new JSONArray(output);                      
            }
            JSONArray parentJSON=new JSONArray();
            JSONArray childJsonArray=null;
            for (int i = 0; i < gisBaseListNew.length(); i++){
            	childJsonArray=new JSONArray();
                JSONObject jsonObj = gisBaseListNew.getJSONObject(i);
                childJsonArray.put(jsonObj.getString("vehicleno"));
                childJsonArray.put(jsonObj.getString("latitude"));
                childJsonArray.put(jsonObj.getString("longitude"));
                childJsonArray.put(jsonObj.getString("vehicleno"));
                childJsonArray.put(jsonObj.optString("flag", "Y"));
                parentJSON.put(childJsonArray);
            }
            
            URL urln = new URL("http://59.144.132.170/getweb/rest/webservice/getdatatimeperiod?ambulanceno="+vehicleNo+"&timeperiod=30");
            HttpURLConnection connc = (HttpURLConnection) urln.openConnection();
            connc.setRequestMethod("POST");
            connc.setRequestProperty("Accept", "application/json");

            if (connc.getResponseCode() != 200) {
            	throw new RuntimeException("Failed : HTTP error code : "
                                                             + connc.getResponseCode());
            }

            BufferedReader brr = new BufferedReader(new InputStreamReader((connc.getInputStream())));
            String outputn;
            JSONArray gisBaseListNewn = null;                
            while ((outputn = brr.readLine()) != null) {
            	gisBaseListNewn = new JSONArray(outputn);                   
            }
            JSONArray parentJSONPath=new JSONArray();
            JSONArray childJsonArrayPath=null;
            for (int i = 0; i < gisBaseListNewn.length(); i++){
            	childJsonArrayPath=new JSONArray();
                JSONObject jsonObj = gisBaseListNewn.getJSONObject(i);
                childJsonArrayPath.put(jsonObj.getString("vehicleno"));
                childJsonArrayPath.put(jsonObj.getString("latitude"));
                childJsonArrayPath.put(jsonObj.getString("longitude"));
                childJsonArrayPath.put(jsonObj.getString("vehicleno"));
                childJsonArrayPath.put(jsonObj.optString("flag", "Y"));
                parentJSONPath.put(childJsonArrayPath);
            }
            gisBaseList=ambulanceReportManager.getGisBaseData(districtName);  
                 
            data=new JSONObject();    
            data.put("gisBaseList", gisBaseList);
            data.put("vehicleNo", vehicleNo);
            data.put("parentJSON", parentJSON);
            data.put("gisPathStatusList", parentJSONPath);
            out.println(data);
        }finally{
        }
    }
        

    @RequestMapping(value="/ambulanceTnega", method=RequestMethod.GET)
	public @ResponseBody void getTnegaDistrictName(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{			
    	PrintWriter out = response.getWriter();
		try{				
			//Get Default Column TNega column values 
			JSONArray tnegaColumnArray = negaColumnList("Non-Agricultural Usage");
			
			//Set Default Column Attrubute values
			JSONArray tnegaColumnAttributeArray =new JSONArray();
			JSONObject negaColumnAttributeObj =new JSONObject();
			negaColumnAttributeObj.put("code", "District");
			negaColumnAttributeObj.put("avalue", "null");
			tnegaColumnAttributeArray.put(negaColumnAttributeObj);			

			JSONArray columnValues = ambulanceReportManager.getTnegaDistrictName();	
			
			//Frame Meta data Object
			JSONObject metaDataObj =new JSONObject();
			metaDataObj.put("column", tnegaColumnArray);
			metaDataObj.put("column_attribute", tnegaColumnAttributeArray);
			metaDataObj.put("attribute", columnValues);
			
			JSONObject finalMapvalues =new JSONObject();
			finalMapvalues.put("metadata",metaDataObj);
			
			out.println(finalMapvalues);
			System.out.println("Final Map Values"+finalMapvalues);
			}
		finally{}				 
	}
	
	private JSONArray negaColumnList(String title) throws JSONException{
		JSONArray tnegaColumnArray =new JSONArray();
		JSONObject columnObj1 =new JSONObject();
		columnObj1.put("code","sp_field_link");
		columnObj1.put("value","drd_dcode");
		JSONObject columnObj2 =new JSONObject();
		columnObj2.put("code","sp_label_column");
		columnObj2.put("value","dist_name");
		JSONObject columnObj3 =new JSONObject();
		columnObj3.put("code","lyr_id");
		columnObj3.put("value","A2");
		JSONObject columnObj4 =new JSONObject();
		columnObj4.put("code","symbology_type");
		columnObj4.put("value","ec");
		JSONObject columnObj5 =new JSONObject();
		columnObj5.put("code","range");
		columnObj5.put("value","4");
		JSONObject columnObj6 =new JSONObject();
		columnObj6.put("code","filter_column");
		columnObj6.put("value","dcode");
		JSONObject columnObj7 =new JSONObject();
		columnObj7.put("code","filter_value");
		columnObj7.put("value","col1");
		JSONObject columnObj8 =new JSONObject();
		columnObj8.put("code","Non spatial filter_column");
		columnObj8.put("value","col1");
		JSONObject columnObj9 =new JSONObject();
		columnObj9.put("code","Non spatial filter_value");
		columnObj9.put("value","col1");
		JSONObject columnObj10 =new JSONObject();
		columnObj10.put("code","title");
		columnObj10.put("value",title);
		JSONObject columnObj11 =new JSONObject();
		columnObj11.put("code","colors");
		columnObj11.put("value","#FF0055,#ff974a,#c2fce8,#87ce67");
		JSONObject columnObj12 =new JSONObject();
		columnObj12.put("code","drill_down_url");
		columnObj12.put("value","null");
		JSONObject columnObj13 =new JSONObject();
		columnObj13.put("code","subtitle");
		columnObj13.put("value","null");
		JSONObject columnObj14 =new JSONObject();
		columnObj14.put("code","Department");
		columnObj14.put("value","920");
		
		tnegaColumnArray.put(columnObj1);
		tnegaColumnArray.put(columnObj2);
		tnegaColumnArray.put(columnObj3);
		tnegaColumnArray.put(columnObj4);
		tnegaColumnArray.put(columnObj5);
		tnegaColumnArray.put(columnObj6);
		tnegaColumnArray.put(columnObj7);
		tnegaColumnArray.put(columnObj8);
		tnegaColumnArray.put(columnObj9);
		tnegaColumnArray.put(columnObj10);
		tnegaColumnArray.put(columnObj11);
		tnegaColumnArray.put(columnObj12);
		tnegaColumnArray.put(columnObj13);
		tnegaColumnArray.put(columnObj14);
		return tnegaColumnArray;
	}        
        
	JSONArray tempJsonArray = new JSONArray(); 
	
	
	@RequestMapping(value="/108GPSStatus", method=RequestMethod.GET)
	public ModelAndView showOnGPSStatusData(HttpServletRequest request) throws IOException, JSONException,SHDRCException{
		ModelAndView modelAndView=null;
		String distName=null;
		JSONArray gisBaseList = null;
		String vehicleNo = null;
		try{
			modelAndView=new ModelAndView();
			modelAndView.setViewName("108GPSStatusAction");
			
			JSONArray emriArray = ambulanceReportManager.getEmriDistricltList();
            JSONArray emriActiveDet = new JSONArray();         
            
            String Str_new = "{DistrictId: 999}";
            JSONArray jsonObjNew = emriResult(Str_new);
            JSONArray  parentArray = new JSONArray();     
            for (int i = 0; i < jsonObjNew.length(); i++){                  
                JSONObject  emrijo = jsonObjNew.getJSONObject(i);
                for (int j = 0; j < emriArray.length(); j++){
                	JSONObject  jo = emriArray.getJSONObject(j);
                    JSONObject  childArray = new JSONObject();
                    if(jo.getString("districtName").equalsIgnoreCase(emrijo.getString("District"))){
                    	childArray.put("ActiveCasesCount",emrijo.getString("ActiveCasesCount"));
                        childArray.put("ClosedCasesCount",emrijo.getString("ClosedCasesCount"));
                        childArray.put("districtName",jo.getString("districtName"));
                        childArray.put("District",emrijo.getString("District"));
                        childArray.put("Tot_Amb",jo.getString("Tot_Amb"));
                        childArray.put("lat",jo.getString("lat"));
                        childArray.put("lon",jo.getString("lon"));
                        parentArray.put(childArray);
                        break;
                    }
                }
            }       
            
            JSONArray sortedJsonArray = new JSONArray();

            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for (int i = 0; i < parentArray.length(); i++) {
                jsonValues.add(parentArray.getJSONObject(i));
            }
            Collections.sort( jsonValues, new Comparator<JSONObject>() {
                //You can change "Name" with "ID" if you want to sort by ID
                private static final String KEY_NAME = "ActiveCasesCount";

                @Override
                public int compare(JSONObject a, JSONObject b) {
                    String valA = new String();
                    String valB = new String();

                    try {
                        valA = (String) a.get(KEY_NAME);
                        valB = (String) b.get(KEY_NAME);
                    } 
                    catch (JSONException e) {
                        //do something
                    }
                    int firstValue = Integer.parseInt(valA);
                    int secondValue = Integer.parseInt(valB);
                    /*return valA.compareTo(valB);*/
                    //if you want to change the sort order, simply use the following:
                    //return -valA.compareTo(valB);
                    
                    if (firstValue >= secondValue) {
                        return -1;
                    } 
                    else if (firstValue >= secondValue) {
                        return 0;
                    } 
                    else {
                        return 1;
                    }
                }
            });

            for (int i = 0; i < parentArray.length(); i++) {
                sortedJsonArray.put(jsonValues.get(i));
            }
            
            modelAndView.addObject("emriCaseCntDet", sortedJsonArray);          
            tempJsonArray = sortedJsonArray;
            
            JSONObject  firstObj = sortedJsonArray.getJSONObject(1);    
            distName = firstObj.getString("District");
            modelAndView.addObject("districtName", distName);
            
            JSONArray talukIdArray = ambulanceReportManager.getEmriTaluktList(distName);
            JSONObject  firstTalukObj = talukIdArray.getJSONObject(0);    
            String disId = firstTalukObj.getString("DistrictId");
            for (int i = 0; i < talukIdArray.length(); i++){ 
            	JSONObject  tal = talukIdArray.getJSONObject(i);
                String talId =tal.getString("TalukId");
                String talName =tal.getString("Taluk");
                       
                String Str_Dis_Id = "{DistrictId: "+disId+",TalukId: "+talId+"}"; 
	            JSONObject emrijsonObj = emriActiveCaseDetails(Str_Dis_Id);
	            String emriActive = emrijsonObj.getString("CaseDetails");
	            JSONArray jaa = new JSONArray(emriActive);
           
	            for (int j = 0; j < jaa.length(); j++){                 
	            	JSONObject jobja = jaa.getJSONObject(j);
	            	String CaseId = jobja.getString("CaseId");
	            	String AmbulanceNumber = jobja.getString("AmbulanceNumber"); 
	            	String IncidentCity = jobja.getString("IncidentCity");
	            	String IncidentLocation = jobja.getString("IncidentLocation");
	            	int count = jaa.length();
	            	
	            	JSONObject emriActiveArray=new JSONObject();
		            emriActiveArray.put( "DistrictId",disId);
		            emriActiveArray.put("districtName", distName);
		            emriActiveArray.put("CaseId",CaseId);
		            emriActiveArray.put("AmbulanceNumber" ,AmbulanceNumber);
		            emriActiveArray.put("IncidentCity",IncidentCity);
		            emriActiveArray.put("IncidentLocation" ,IncidentLocation);
		            emriActiveArray.put("count" ,count);
		            emriActiveArray.put("talName" ,talName);
		            emriActiveArray.put("talName" ,talName);            
		            emriActiveDet.put(emriActiveArray); 
	            }           
            }
            modelAndView.addObject("emriActiveDet", emriActiveDet);     
            
            gisBaseList=ambulanceReportManager.getGisBaseData(distName);    
			modelAndView.addObject("gisBaseList", gisBaseList);
      
            URL url = new URL("http://gps.tngvkemri.in/getweb/rest/webservice/getdatadistrict?district="+distName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
            	throw new RuntimeException("Failed : HTTP error code : "
                                                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            JSONArray gisBaseListNew = null;                
            while ((output = br.readLine()) != null) {
            	gisBaseListNew = new JSONArray(output);                     
            }
            JSONArray parentJSON=new JSONArray();
            JSONArray childJsonArray=null;
            for (int i = 0; i < gisBaseListNew.length(); i++){
            	childJsonArray=new JSONArray();
	            JSONObject jsonObj = gisBaseListNew.getJSONObject(i);
	            childJsonArray.put(jsonObj.getString("vehicleno"));
	            childJsonArray.put(jsonObj.getString("latitude"));
	            childJsonArray.put(jsonObj.getString("longitude"));
	            childJsonArray.put(jsonObj.getString("vehicleno"));
	            childJsonArray.put(jsonObj.optString("flag", "Y"));
	            parentJSON.put(childJsonArray);
	        }
            modelAndView.addObject("parentJSON", parentJSON);
            
            if(parentJSON!=null && parentJSON.length()>0){
		           JSONArray district = parentJSON.getJSONArray(0);
		           vehicleNo = district.getString(3);
		    }
			modelAndView.addObject("vehicleNo", vehicleNo);

            URL urln = new URL("http://gps.tngvkemri.in/getweb/rest/webservice/getdatatimeperiod?ambulanceno="+vehicleNo+"&timeperiod=30");
            HttpURLConnection connc = (HttpURLConnection) urln.openConnection();
            connc.setRequestMethod("POST");
            connc.setRequestProperty("Accept", "application/json");

            if (connc.getResponseCode() != 200) {
            	throw new RuntimeException("Failed : HTTP error code : "
                                                            + connc.getResponseCode());
            }

            BufferedReader brr = new BufferedReader(new InputStreamReader((connc.getInputStream())));
            String outputn;
            JSONArray gisBaseListNewn = null;                
            while ((outputn = brr.readLine()) != null) {
            	gisBaseListNewn = new JSONArray(outputn);                   
                 
            }
            JSONArray parentJSONPath=new JSONArray();
            JSONArray childJsonArrayPath=null;
            for (int i = 0; i < gisBaseListNewn.length(); i++){
            	childJsonArrayPath=new JSONArray();
                JSONObject jsonObj = gisBaseListNewn.getJSONObject(i);
                childJsonArrayPath.put(jsonObj.getString("vehicleno"));
	            childJsonArrayPath.put(jsonObj.getString("latitude"));
	            childJsonArrayPath.put(jsonObj.getString("longitude"));
	            childJsonArrayPath.put(jsonObj.getString("vehicleno"));
	            childJsonArrayPath.put(jsonObj.optString("flag", "Y"));
	            parentJSONPath.put(childJsonArrayPath);
            }
			modelAndView.addObject("gisPathStatusList", parentJSONPath);
		   
      }finally{
      }
      return modelAndView;
	}
             
	private JSONArray emriResult(String distId) throws JSONException{
		String output;
		JSONObject jsonObjectn = new JSONObject();
		JSONArray parentJSONPath=new JSONArray();
		JSONArray jarr= new JSONArray();
        try {
        	URL url = new URL("http://avlt.tngvkemri.in/EMSReportsService1/EMSReportsService.svc/GetCountOfCases");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(distId);
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((output = br.readLine()) != null) {
            	jsonObjectn  = new JSONObject(output);
           }
           String emrijo = jsonObjectn.getString("CasesCount");       
           
           jarr = new JSONArray(emrijo);  
           JSONArray childJsonArrayPath=new JSONArray();
           for (int i = 0; i < jarr.length(); i++){
        	   childJsonArrayPath=new JSONArray();
        	   JSONObject jsonObj = jarr.getJSONObject(i);
        	   childJsonArrayPath.put(jsonObj.getString("District"));
        	   childJsonArrayPath.put(jsonObj.getString("ActiveCasesCount"));
        	   childJsonArrayPath.put(jsonObj.getString("ClosedCasesCount"));
        	   parentJSONPath.put(childJsonArrayPath);
           }
           conn.disconnect();
        } catch (MalformedURLException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
       return jarr;
	} 
	
	private JSONObject emriActiveCaseDetails(String Str_Dis_Id) throws JSONException{
		String output;
		JSONObject jsonObjectn = new JSONObject();
        try {
        	URL url = new URL("http://avlt.tngvkemri.in/EMSReportsService1/EMSReportsService.svc/GetActiveCaseDetails");
        	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(Str_Dis_Id);
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((output = br.readLine()) != null) {
            	jsonObjectn  = new JSONObject(output);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
        	e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObjectn;
	} 
	
	@RequestMapping(value="/getLiveGPSStatusStreamingDataVehicleNo", method=RequestMethod.POST)
    public @ResponseBody void  liveStreamingDataVehicleNo(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
		PrintWriter out = response.getWriter();
        String vehicleNo = null;
        String districtName = null; 
        JSONObject data = null;
        try{
        	vehicleNo=request.getParameter("vehicleNo");    
        	districtName = request.getParameter("districtName");  
        	
        	JSONArray vehicleBaseLoc = null;
        	vehicleBaseLoc = ambulanceReportManager.getVehicleBaseLoc(districtName,vehicleNo);   
        	
        	data = new JSONObject();
        	data.put("vehicleBaseLoc", vehicleBaseLoc);
        	
            URL url = new URL("http://gps.tngvkemri.in/getweb/rest/webservice/getdata?ambulanceno="+vehicleNo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
            	throw new RuntimeException("Failed : HTTP error code : "
                                                                    + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            JSONArray gisBaseListNew = null;                
            while ((output = br.readLine()) != null) {
            	gisBaseListNew = new JSONArray(output);                
            }
            JSONArray parentJSON=new JSONArray();
            JSONArray childJsonArray=null;
            for (int i = 0; i < gisBaseListNew.length(); i++){
            	childJsonArray=new JSONArray();
                JSONObject jsonObj = gisBaseListNew.getJSONObject(i);
                childJsonArray.put(jsonObj.getString("latitude"));
                childJsonArray.put(jsonObj.getString("longitude"));
                parentJSON.put(childJsonArray);
            }
            data.put("vehicleCurrentLoc", parentJSON);
            out.println(data);
        }
        finally{}                  
	}
	
	@RequestMapping(value="/getCaseCountOnRefresh", method=RequestMethod.POST)
    public @ResponseBody void  getCaseCountOnRefresh(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
		PrintWriter out = response.getWriter();
        try{
        	JSONArray emriArray = ambulanceReportManager.getEmriDistricltList();
            
            String Str_new = "{DistrictId: 999}";
            JSONArray jsonObjNew = emriResult(Str_new);
            JSONArray  parentArray = new JSONArray();     
            for (int i = 0; i < jsonObjNew.length(); i++){                  
                JSONObject  emrijo = jsonObjNew.getJSONObject(i);
                for (int j = 0; j < emriArray.length(); j++){
                	JSONObject  jo = emriArray.getJSONObject(j);
                    JSONObject  childArray = new JSONObject();
                    if(jo.getString("districtName").equalsIgnoreCase(emrijo.getString("District"))){
                    	childArray.put("ActiveCasesCount",emrijo.getString("ActiveCasesCount"));
                        childArray.put("ClosedCasesCount",emrijo.getString("ClosedCasesCount"));
                        childArray.put("districtName",jo.getString("districtName"));
                        childArray.put("District",emrijo.getString("District"));
                        childArray.put("Tot_Amb",jo.getString("Tot_Amb"));
                        childArray.put("lat",jo.getString("lat"));
                        childArray.put("lon",jo.getString("lon"));
                        parentArray.put(childArray);
                        break;
                    }
                }
            }
            
           /* for (int j = 0; j < parentArray.length(); j++)
            {  JSONObject  emriNewData = parentArray.getJSONObject(j);
                for (int i = 0; i < tempJsonArray.length(); i++)
                   { JSONObject  emriOldData = tempJsonArray.getJSONObject(i);
                              if(emriOldData.getString("districtName").equalsIgnoreCase(emriNewData.getString("districtName")))
                                    {
                                    int old = Integer.parseInt(emriOldData.getString("ActiveCasesCount"));
                                    
                                    int newn =Integer.parseInt(emriNewData.getString("ActiveCasesCount"));
                                    System.out.println(newn);
                                    if(old == newn)
                                          {emriNewData.put("flag", "e");
                                          }   
                                    else if (old > newn)
                                          {emriNewData.put("flag", "l");
                                          }
                                    else 
                                          {emriNewData.put("flag", "h");
                                          }
                                    parentArray.put(emriNewData);
                                      break;
                                    }
                   }
                
             }
       System.out.println("emriNewData"+parentArray);*/

            out.println(parentArray);
        }
        finally{}                  
	}
	
	@RequestMapping(value="getDetailsByDistrict", method=RequestMethod.POST)
    public @ResponseBody void getDetailsByDistrict(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
        JSONObject data=null;
        String districtName = null;
        JSONArray gisBaseList = null;
        String vehicleNo = null;
        JSONArray emriActiveDet = new JSONArray(); 
        try{
        	districtName=request.getParameter("districtName").trim();
            JSONArray talukIdArray = ambulanceReportManager.getEmriTaluktList(districtName);
            JSONObject  firstTalukObj = talukIdArray.getJSONObject(0);    
            String disId = firstTalukObj.getString("DistrictId");
            for (int i = 0; i < talukIdArray.length(); i++){ 
            	JSONObject  tal = talukIdArray.getJSONObject(i);
                String talId =tal.getString("TalukId");
                String talName =tal.getString("Taluk");
                          
                String Str_Dis_Id = "{DistrictId: "+disId+",TalukId: "+talId+"}"; 
                JSONObject emrijsonObj = emriActiveCaseDetails(Str_Dis_Id);
                String emriActive = emrijsonObj.getString("CaseDetails");
                JSONArray jaa = new JSONArray(emriActive);
               
                for (int j = 0; j < jaa.length(); j++){                 
                	JSONObject jobja = jaa.getJSONObject(j);
                    String CaseId = jobja.getString("CaseId");
                    String AmbulanceNumber = jobja.getString("AmbulanceNumber"); 
                    String IncidentCity = jobja.getString("IncidentCity");
                    String IncidentLocation = jobja.getString("IncidentLocation");
                    int count = jaa.length();
                            
                    JSONObject emriActiveArray=new JSONObject();
                    emriActiveArray.put( "DistrictId",disId);
                    emriActiveArray.put("districtName", districtName);
                    emriActiveArray.put("CaseId",CaseId);
                    emriActiveArray.put("AmbulanceNumber" ,AmbulanceNumber);
                    emriActiveArray.put("IncidentCity",IncidentCity);
                    emriActiveArray.put("IncidentLocation" ,IncidentLocation);
                    emriActiveArray.put("count" ,count);
                    emriActiveArray.put("talName" ,talName);
                    emriActiveArray.put("talName" ,talName);            
                    emriActiveDet.put(emriActiveArray); 
                }          
            }
               
            data = new JSONObject();
            data.put("emriActiveDet", emriActiveDet);
                
            gisBaseList=ambulanceReportManager.getGisBaseData(districtName);    
            data.put("districtName", districtName);
            data.put("gisBaseList", gisBaseList);
                
            URL url = new URL("http://gps.tngvkemri.in/getweb/rest/webservice/getdatadistrict?district="+districtName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
            	throw new RuntimeException("Failed : HTTP error code : "
            			+ conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            JSONArray gisBaseListNew = null;                
            while ((output = br.readLine()) != null) {
             	gisBaseListNew = new JSONArray(output);                     
            }
            JSONArray parentJSON=new JSONArray();
            JSONArray childJsonArray=null;
            for (int i = 0; i < gisBaseListNew.length(); i++){
            	childJsonArray=new JSONArray();
               	JSONObject jsonObj = gisBaseListNew.getJSONObject(i);
               	childJsonArray.put(jsonObj.getString("vehicleno"));
               	childJsonArray.put(jsonObj.getString("latitude"));
               	childJsonArray.put(jsonObj.getString("longitude"));
               	childJsonArray.put(jsonObj.getString("vehicleno"));
               	childJsonArray.put(jsonObj.optString("flag", "Y"));
               	parentJSON.put(childJsonArray);
            }                
            data.put("parentJSON", parentJSON);
                
            if(parentJSON!=null && parentJSON.length()>0){
               	JSONArray district = parentJSON.getJSONArray(0);
                vehicleNo = district.getString(3);
            }
            data.put("vehicleNo", vehicleNo);

            URL urln = new URL("http://gps.tngvkemri.in/getweb/rest/webservice/getdatatimeperiod?ambulanceno="+vehicleNo+"&timeperiod=30");
            HttpURLConnection connc = (HttpURLConnection) urln.openConnection();
            connc.setRequestMethod("POST");
            connc.setRequestProperty("Accept", "application/json");
                
            if (connc.getResponseCode() != 200) {
            	throw new RuntimeException("Failed : HTTP error code : "
                     	+ connc.getResponseCode());
            }
                
            BufferedReader brr = new BufferedReader(new InputStreamReader((connc.getInputStream())));
            String outputn;
            JSONArray gisBaseListNewn = null;                
            while ((outputn = brr.readLine()) != null) {
             	gisBaseListNewn = new JSONArray(outputn);                   
            }
            JSONArray parentJSONPath=new JSONArray();
            JSONArray childJsonArrayPath=null;
            for (int i = 0; i < gisBaseListNewn.length(); i++){
              	childJsonArrayPath=new JSONArray();
               	JSONObject jsonObj = gisBaseListNewn.getJSONObject(i);
               	childJsonArrayPath.put(jsonObj.getString("vehicleno"));
               	childJsonArrayPath.put(jsonObj.getString("latitude"));
               	childJsonArrayPath.put(jsonObj.getString("longitude"));
               	childJsonArrayPath.put(jsonObj.getString("vehicleno"));
               	childJsonArrayPath.put(jsonObj.optString("flag", "Y"));
               	parentJSONPath.put(childJsonArrayPath);
            }
            data.put("gisPathStatusList", parentJSONPath);                
            out.println(data);                
        }
        catch (IOException e) {
        }finally{
        }
	}

}