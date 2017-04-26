package gov.shdrc.dataentry.controller;

import gov.shdrc.dataentry.service.ICocDataEntryManager;
import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.exception.SHDRCException;
import gov.shdrc.util.CSRFTokenUtil;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
public class CocDataEntryController {
	private static Logger log=Logger.getLogger(CocDataEntryController.class);
	@Autowired
	ICocDataEntryManager cocDataEntryManager;
	@Autowired
	ICommonManager commonManager;
	
	@RequestMapping(value="/cocDataEntry", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws SHDRCException, NoSuchAlgorithmException, ServletException{		
	   
		ModelAndView modelView=null;
	  
		try {
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.COC);
		    modelView.setViewName("cocDataentry");			
			
		    String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
		
			JSONArray jsonArray = Util.getColumnHearders();
			String encodedJsonArray = Util.encodeJSONArray(jsonArray.toString());
	        modelView.addObject("encodedJsonArray", encodedJsonArray);
	        
	        String csrfToken =null;
			
			 csrfToken = CSRFTokenUtil.getToken (request.getSession());
			 modelView.addObject("CSRF_TOKEN", csrfToken);
					   
	        List<CommonStringList> directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
	        modelView.addObject("directorateList", directorateList);
	        
	        JSONArray directorateServletList= commonManager.getDirecorateServletList();
	        modelView.addObject("directorateServletList", Util.encodeJSONArray(directorateServletList.toString()));
	        
	        List<CommonStringList> regionList= cocDataEntryManager.getRegions(userName);
	        modelView.addObject("regionList", regionList);	        
	              
	        String regionName=null;
	        String institutionName=null;
	        List<CommonStringList> institutionList=null;
	        
			if(Util.isNotNull(regionList)){
	        	CommonStringList region = regionList.get(0);
	        	regionName=region.getName();
	        		        	
	        	if(ShdrcConstants.SELECTALLVALUE.equalsIgnoreCase(region.getName())){
	        		institutionList = new ArrayList<CommonStringList>();
	        		CommonStringList commonStringList = new CommonStringList();
	    			commonStringList.setId(ShdrcConstants.SELECTALLID);
	    			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
	    			institutionList.add(commonStringList);
	        	}else{
	        		institutionList= cocDataEntryManager.getInstitutionList(region.getName(),userName);
	        	}
	        	modelView.addObject("institutionList", institutionList);
	        }
			
			if(institutionList!=null && institutionList.size()>0){
	        	CommonStringList institution = institutionList.get(0);
	        	institutionName=institution.getName();
	        }
			
			modelView.addObject("regionName", regionName);
	        modelView.addObject("institutionName", institutionName);
	        
	        
	        List<CommonStringList> classificationList= commonManager.getClassificationList(ShdrcConstants.DirectorateId.COC,ShdrcQueryList.COC_CLASSIFICATION_LIST);
	        modelView.addObject("classificationList", classificationList);
	        
	        List<Integer> yearList= Util.yearList;
	        modelView.addObject("yearList", yearList);
	        
	        List<CommonStringList> freuencyList= Util.freuencyList;
	        modelView.addObject("freuencyList", freuencyList);
	        
	        List<CommonStringList> weeksList= Util.weeksList;
	        modelView.addObject("weeksList", weeksList);
	        
	        List<CommonStringList> quarterList= Util.quarterList;
	        modelView.addObject("quarterList", quarterList);
	        
	        List<CommonStringList> monthsList= Util.monthsList;
	        modelView.addObject("monthsList", monthsList);
	        	        
	        modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));
			
	        modelView.addObject("directorateId", ShdrcConstants.DirectorateId.COC);
	        log.debug(this.getClass().getName() + "- Exit ");	
			} finally{} 
		
			return modelView;
	}
	
	@RequestMapping(value="/cocregionChange", method=RequestMethod.POST)
	public @ResponseBody void getInstitution(HttpServletRequest request,HttpServletResponse response) throws SHDRCException,IOException, JSONException{
		try {
			log.debug(this.getClass().getName() + "- Entering ");
		PrintWriter out = response.getWriter();
		String userName = UserInfo.getLoggedInUserName();
		List<CommonStringList> institutionList= null;
		if(Util.isNotNull(request.getParameter("regionName"))){
			String regionName = request.getParameter("regionName");
			institutionList= cocDataEntryManager.getInstitutionList(regionName,userName);
		}else{
			institutionList = new ArrayList<CommonStringList>();
			CommonStringList commonStringList = new CommonStringList();
			commonStringList.setId(ShdrcConstants.SELECTALLID);
			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
			institutionList.add(commonStringList);
		}
		String json=new Gson().toJson(institutionList);  
		out.println(json);
		log.debug(this.getClass().getName() + "- Exit ");	
		}finally{} 
	}
		
	@RequestMapping(value="/cocsearch", method=RequestMethod.POST)
	public @ResponseBody void search(HttpServletRequest request,HttpServletResponse response) throws SHDRCException,IOException, JSONException{
		try {
			log.debug(this.getClass().getName() + "- Entering ");
		String regionName = request.getParameter("regionName");
		Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
		String frequency = request.getParameter("frequency");
		String week = request.getParameter("week");
		String quarter = request.getParameter("quarter");
		String month = request.getParameter("month");
		Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
		String searchDate = request.getParameter("searchDate");
		
		PrintWriter out = response.getWriter();
		JSONArray data=new JSONArray();
		JSONObject obj = new JSONObject();
		
		String dataEntryLevel = Util.getDataEntryLevel(null,null,institutionId);
		List<CommonStringList> classificationList=null;
		String classificationName =null;
		JSONArray cocDirectorateRecords =null;

			 classificationList=commonManager.getGeneralCategoryListByFrequency(ShdrcConstants.DirectorateId.COC,frequency,dataEntryLevel,ShdrcQueryList.COC_GENERAL_CATEGORY_LIST_BY_FREQUENCY);
			 if(classificationList!=null){
		        	CommonStringList commonStringList = classificationList.get(0);
		        	classificationName = commonStringList.getName();
		        	cocDirectorateRecords = cocDataEntryManager.getCocDirectorateRecords(regionName,institutionId,classificationName,frequency,week,quarter,
		   				 searchDate, month, year);
		        	
		        	if(cocDirectorateRecords==null){
		    			cocDirectorateRecords = cocDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.COC,classificationName,frequency,"N",null);
		    		}
			 } 		
		String json=null;
		if(classificationList!=null){
			obj.put("isFrequencySelectionValid", true);
		}else
			obj.put("isFrequencySelectionValid", false);
		json=new Gson().toJson(classificationList);
		obj.put("cocDirectorateRecords", cocDirectorateRecords);
		obj.put("genearlCategoryList", json);
		data.put(obj);
		out.println(obj);
		log.debug(this.getClass().getName() + "- Exit ");	
		} finally{}
	}	
	
	@RequestMapping(value="/coctabChange", method=RequestMethod.POST)
	public @ResponseBody void onTabChange(HttpServletRequest request,HttpServletResponse response) throws SHDRCException,IOException, JSONException{
		try {
			log.debug(this.getClass().getName() + "- Entering ");
		String regionName = request.getParameter("regionName");
		Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
		String frequency = request.getParameter("frequency");
		String week = request.getParameter("week");
		String quarter = request.getParameter("quarter");
		String month = request.getParameter("month");
		Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
		String searchDate = request.getParameter("searchDate");
		
		PrintWriter out = response.getWriter();
		JSONArray data=new JSONArray();
		JSONObject obj = new JSONObject();
		
		List<CommonStringList> classificationList=null;
		String classificationName = request.getParameter("classificationName");
		String json=null;
		obj.put("isFrequencySelectionValid", true);
		json=new Gson().toJson(classificationList);
		JSONArray cocDirectorateRecords = cocDataEntryManager.getCocDirectorateRecords(regionName,institutionId,classificationName,frequency,week,quarter,
				 searchDate, month, year);
		
		if(cocDirectorateRecords==null && classificationName!=null){
			cocDirectorateRecords = cocDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.COC,classificationName,frequency,"N",null);
		}
		obj.put("cocDirectorateRecords", cocDirectorateRecords);
		obj.put("genearlCategoryList", json);
		data.put(obj);
		out.println(obj);
		log.debug(this.getClass().getName() + "- Exit ");	
		} finally{}
		
	}
	
	@RequestMapping(value="/cocsave", method=RequestMethod.POST)
	public @ResponseBody void doSave(HttpServletRequest request,HttpServletResponse response) throws SHDRCException,IOException, ServletException, JSONException{
		
		PrintWriter out = response.getWriter();
			log.debug(this.getClass().getName() + "- Entering ");
			String regionName = request.getParameter("regionName");
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			String classificationName = request.getParameter("classificationName");
			String frequency = request.getParameter("frequency");
			String week = request.getParameter("week");
			String quarter = request.getParameter("quarter");
			String month = request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			String searchDate = request.getParameter("searchDate");
			
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray gridRecords = new JSONArray(handsOnTableData);
			
			boolean successFlag =cocDataEntryManager.insertCocDirectorateRecords(regionName,institutionId, classificationName,frequency,week,quarter,
						searchDate, month, year,gridRecords);
			if(successFlag)
				out.println("The record has been saved successfully");
			else
				out.println("Save Error");
			    log.debug(this.getClass().getName() + "- Exit ");
	}
	
	@RequestMapping(value="/cocupdate", method=RequestMethod.POST)
	public @ResponseBody void doUpdate(HttpServletRequest request,HttpServletResponse response) throws SHDRCException,IOException, ServletException, JSONException{
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			PrintWriter out = response.getWriter();
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray jsonArray = new JSONArray(handsOnTableData);
			
			boolean successFlag = cocDataEntryManager.updateCocDirectorateRecords(jsonArray);
			if(successFlag)
				out.println("The record has been updated successfully");
			else
				out.println("Save Error");
			    log.debug(this.getClass().getName() + "- Exit ");
		}finally{}
	
	}
	
}
