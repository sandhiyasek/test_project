package gov.shdrc.dataentry.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.dataentry.service.INlepDataEntryManager;
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
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class NlepDataEntryController {
	private static Logger log= Logger.getLogger(NlepDataEntryController.class);
	@Autowired
	INlepDataEntryManager nlepDataEntryManager;
	@Autowired
	ICommonManager commonManager;
	
	@RequestMapping(value="/nlepDataEntry", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws SHDRCException, NoSuchAlgorithmException, ServletException{		
	   
		ModelAndView modelView=null;
	  
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.NLEP);
		    modelView.setViewName("nlepDataentry");			
			
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
	        
	        List<CommonStringList> districtList= commonManager.getDistricts(userName);
	        modelView.addObject("districts", districtList);	        
	     
	        String districtName=null;	      
	        String institutionName=null;
	        List<CommonStringList> institutionList=null;	        
	        if(Util.isNotNull(districtList)){
	        	CommonStringList district = districtList.get(0);	        
	        	districtName=district.getName();
        	      	if(ShdrcConstants.SELECTALLID==district.getId()){
	        		institutionList = new ArrayList<CommonStringList>();
	        		CommonStringList commonStringList = new CommonStringList();
	    			commonStringList.setId(ShdrcConstants.SELECTALLID);
	    			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
	    			institutionList.add(commonStringList);
	        	}else{
	        		institutionList= nlepDataEntryManager.getInstitutionList(district.getId(),userName);
	        	}	        	
				modelView.addObject("institutionList", institutionList);
	        }
			
	        if(institutionList!=null && institutionList.size()>0){
	        	CommonStringList institution = institutionList.get(0);
	        	institutionName=institution.getName();	        	
	        }
	        	
		    modelView.addObject("districtName", districtName);
		    modelView.addObject("institutionName", institutionName);		          	
			
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
			
	        modelView.addObject("directorateId", ShdrcConstants.DirectorateId.NLEP);
	        
	        List<CommonStringList> classificationList= commonManager.getClassificationList(ShdrcConstants.DirectorateId.NLEP,ShdrcQueryList.NLEP_CLASSIFICATION_LIST);
	        modelView.addObject("classificationList", classificationList);
	        log.debug(this.getClass().getName() + "- Exit ");
			}finally{} 
		
			return modelView;
	}

	@RequestMapping(value="/nlepdistrictChange", method=RequestMethod.POST)
	public @ResponseBody void getHospital(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		
		PrintWriter out = response.getWriter();
		try { 
				log.debug(this.getClass().getName() + "- Entering ");
			String userName = UserInfo.getLoggedInUserName();
			List<CommonStringList> institutionList= null;
			if(Util.isNotNull(request.getParameter("districtId"))){
				Integer districtId = Integer.parseInt(request.getParameter("districtId"));
				institutionList= nlepDataEntryManager.getInstitutionList(districtId,userName);
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
		
	@RequestMapping(value="/nlepsearch", method=RequestMethod.POST)
	public @ResponseBody void search(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
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
			String dataEntryLevel = Util.getDataEntryLevel(districtId,null,institutionId);
			List<CommonStringList> classificationList=null;
			String classificationName =null;
			JSONArray nlepDirectorateRecords =null;
			
			 classificationList=commonManager.getGeneralCategoryListByFrequency(ShdrcConstants.DirectorateId.NLEP,frequency,dataEntryLevel,ShdrcQueryList.NLEP_GENERAL_CATEGORY_LIST_BY_FREQUENCY);
			 if(classificationList!=null){
		        	CommonStringList commonStringList = classificationList.get(0);
		        	classificationName = commonStringList.getName();
		        	nlepDirectorateRecords = nlepDataEntryManager.getNlepDirectorateRecords(districtId,institutionId,classificationName,"N",frequency,week,quarter,
		   				 searchDate, month, year);
		        	if(nlepDirectorateRecords==null){
		    			nlepDirectorateRecords = nlepDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.NLEP,classificationName,frequency,"N",null);
		    		}
		        	boolean isDemographicExists=commonManager.isDemographicExists(ShdrcConstants.DirectorateId.NLEP,classificationName,frequency,ShdrcQueryList.IS_DEMOGRAPHIC);
		        	obj.put("isDemographicExists", isDemographicExists);
			 }
				 
			String json=null;
			if(classificationList!=null){
				obj.put("isFrequencySelectionValid", true);
			}else
				obj.put("isFrequencySelectionValid", false);
			json=new Gson().toJson(classificationList);
			obj.put("nlepDirectorateRecords", nlepDirectorateRecords);
			obj.put("genearlCategoryList", json);
			data.put(obj);
			out.println(obj);
			log.debug(this.getClass().getName() + "- Exit ");
			}finally{}
	}
	
	@RequestMapping(value="/nleptabChange", method=RequestMethod.POST)
	public @ResponseBody void onTabChange(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			String isDemographic = request.getParameter("isDemographic");
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
			JSONArray nlepDirectorateRecords =null;
			if(isDemographic!=null && "N".equalsIgnoreCase(isDemographic))
				nlepDirectorateRecords = nlepDataEntryManager.getNlepDirectorateRecords(districtId,institutionId,classificationName,isDemographic,frequency,week,quarter,
					 searchDate, month, year);
			if(nlepDirectorateRecords==null && classificationName!=null){
				nlepDirectorateRecords = nlepDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.NLEP,classificationName,frequency,isDemographic,null);
			}
			boolean isDemographicExists=commonManager.isDemographicExists(ShdrcConstants.DirectorateId.NLEP,classificationName,frequency,ShdrcQueryList.IS_DEMOGRAPHIC);
	    	obj.put("isDemographicExists", isDemographicExists);
			obj.put("nlepDirectorateRecords", nlepDirectorateRecords);
			obj.put("genearlCategoryList", json);
			data.put(obj);
			out.println(obj);	
			log.debug(this.getClass().getName() + "- Exit ");
			}finally{}
	}
	
	@RequestMapping(value="/nlepsave", method=RequestMethod.POST)
	public @ResponseBody void doSave(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException, JSONException{
		
		PrintWriter out = response.getWriter();
			log.debug(this.getClass().getName() + "- Entering ");
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
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
			
			boolean successFlag =nlepDataEntryManager.insertNlepDirectorateRecords(districtId,institutionId, classificationName,frequency,week,quarter,
						searchDate, month, year,gridRecords);
			if(successFlag)
				out.println("The record has been saved successfully");
			else
				out.println("Save Error");
				log.debug(this.getClass().getName() + "- Exit ");
	}
	
	@RequestMapping(value="/nlepupdate", method=RequestMethod.POST)
	public @ResponseBody void doUpdate(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException, JSONException{
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			PrintWriter out = response.getWriter();
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray jsonArray = new JSONArray(handsOnTableData);
			boolean successFlag = nlepDataEntryManager.updateNlepDirectorateRecords(jsonArray);
			if(successFlag)
				out.println("The record has been updated successfully");
				else
				out.println("Save Error");
				log.debug(this.getClass().getName() + "- Exit ");
			
		}finally{}
	
	}
	
}
