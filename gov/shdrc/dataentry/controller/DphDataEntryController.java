package gov.shdrc.dataentry.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.dataentry.service.IDphDataEntryManager;
import gov.shdrc.exception.SHDRCException;
import gov.shdrc.util.CSRFTokenUtil;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

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
public class DphDataEntryController {
	private static Logger log= Logger.getLogger(DphDataEntryController.class);
	@Autowired
	IDphDataEntryManager dphDataEntryManager;
	@Autowired
	ICommonManager commonManager;
	
	@RequestMapping(value="/dphDataEntry", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws UnsupportedEncodingException, SHDRCException, NoSuchAlgorithmException, ServletException{		
	   
		ModelAndView modelView=null;
	  
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.DPH);
		    modelView.setViewName("dphDataentry");			
			
		    String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
		
	    	JSONArray jsonArray = Util.getColumnHearders();
			String encodedJsonArray = encodeJSONArray(jsonArray.toString());
			modelView.addObject("encodedJsonArray", encodedJsonArray);
	        
	        String csrfToken =null;
			
				 csrfToken = CSRFTokenUtil.getToken (request.getSession());
				 modelView.addObject("CSRF_TOKEN", csrfToken);
					        	        
	        List<CommonStringList> directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
	        modelView.addObject("directorateList", directorateList);
	        
	        JSONArray directorateServletList= commonManager.getDirecorateServletList();
	        modelView.addObject("directorateServletList", encodeJSONArray(directorateServletList.toString()));
	        
	        List<CommonStringList> districtList= commonManager.getDistricts(userName);
	        modelView.addObject("districts", districtList);
	        
	        String districtName=null;
	        String institutionName=null;
	        List<CommonStringList> institutionList=null;
	        if(Util.isNotNull(districtList) && districtList.size()>0){
	        	CommonStringList district = districtList.get(0);
	        	districtName=district.getName();
	        	List<CommonStringList> institutionTypeList=null;
	        	
	        	if(ShdrcConstants.SELECTALLID==district.getId()){
	        		institutionTypeList = new ArrayList<CommonStringList>();
	        		CommonStringList commonStringList = new CommonStringList();
	    			commonStringList.setId(ShdrcConstants.SELECTALLID);
	    			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
	    			institutionTypeList.add(commonStringList);
	    			
	    			institutionList = new ArrayList<CommonStringList>();
	        		CommonStringList lst = new CommonStringList();
	        		lst.setId(ShdrcConstants.SELECTALLID);
	        		lst.setName(ShdrcConstants.SELECTALLVALUE);
	    			institutionList.add(lst);
	        	}else{
	        		institutionTypeList= dphDataEntryManager.getInstitutionTypeList(district.getId(),userName);
	        		if(institutionTypeList!=null && institutionTypeList.size()>0){
	        			CommonStringList inst = institutionTypeList.get(0);
	        			if(ShdrcConstants.SELECTALLID==inst.getId()){
	        				institutionList = new ArrayList<CommonStringList>();
	    	        		CommonStringList lst = new CommonStringList();
	    	        		lst.setId(ShdrcConstants.SELECTALLID);
	    	        		lst.setName(ShdrcConstants.SELECTALLVALUE);
	    	    			institutionList.add(lst);
	        			}else{
		        			institutionList= dphDataEntryManager.getInstitutionList(district.getId(),inst.getName(),userName);
		        		}	        			
	        		}
	        	}
	        	if(institutionList!=null && institutionList.size()>0){
		        	CommonStringList institution = institutionList.get(0);
		        	institutionName=institution.getName();
		        }
	        	modelView.addObject("institutionList", institutionList);
	        	modelView.addObject("institutionTypeList", institutionTypeList);
				
	        	modelView.addObject("districtName", districtName);
	        	modelView.addObject("institutionName", institutionName);
	        }
	        
	        List<CommonStringList> classificationList= commonManager.getClassificationList(ShdrcConstants.DirectorateId.DPH,ShdrcQueryList.DPH_CLASSIFICATION_LIST);
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
	        
	        List<CommonStringList> programList= dphDataEntryManager.getProgramList();
	        modelView.addObject("programList", programList);
	        
	        modelView.addObject("currentHours", Util.getCurrentHours());
	        modelView.addObject("currentDate", Util.getStrDate(new Date()));
			
			modelView.addObject("directorateId", ShdrcConstants.DirectorateId.DPH);
			log.debug(this.getClass().getName() + "- Exit ");	
			}finally{} 
		
			return modelView;
	}
	
	private String encodeJSONArray(String jsonArray) throws UnsupportedEncodingException{
		 
		String encodedJsonArray=null;
			log.debug(this.getClass().getName() + "- Entering ");
			encodedJsonArray = URLEncoder.encode(jsonArray, "UTF-8")   
					   .replaceAll("\\%28", "(")                          
					   .replaceAll("\\%29", ")")   		
					   .replaceAll("\\+", "%20")                          
					   .replaceAll("\\%27", "'")   			   
					   .replaceAll("\\%21", "!")
					   .replaceAll("\\%7E", "~");
			log.debug(this.getClass().getName() + "- Exit ");
		 return encodedJsonArray;		
	}

	@RequestMapping(value="/dphdistrictChange", method=RequestMethod.POST)
	public @ResponseBody void getInstitutionType(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		PrintWriter out = response.getWriter();
			log.debug(this.getClass().getName() + "- Entering ");
		String userName = UserInfo.getLoggedInUserName();
		List<CommonStringList> institutionTypeList= null;
		if(Util.isNotNull(request.getParameter("districtId"))){
			Integer districtId = Integer.parseInt(request.getParameter("districtId"));
			institutionTypeList= dphDataEntryManager.getInstitutionTypeList(districtId,userName);
		}else{
			institutionTypeList = new ArrayList<CommonStringList>();
			CommonStringList commonStringList = new CommonStringList();
			commonStringList.setId(ShdrcConstants.SELECTALLID);
			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
			institutionTypeList.add(commonStringList);
		}
		String json=new Gson().toJson(institutionTypeList);  
		out.println(json);
		log.debug(this.getClass().getName() + "- Exit ");
	}	
	
	@RequestMapping(value="/dphinstitutionTypeChange", method=RequestMethod.POST)
	public @ResponseBody void getInstitution(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		PrintWriter out = response.getWriter();
			log.debug(this.getClass().getName() + "- Entering ");
			String userName = UserInfo.getLoggedInUserName();
			List<CommonStringList> institutionList= null;
			if(Util.isNotNull(request.getParameter("districtId")) && Util.isNotNull(request.getParameter("institutionType"))){
				Integer districtId = Integer.parseInt(request.getParameter("districtId"));
				String institutionType = request.getParameter("institutionType");
				institutionList= dphDataEntryManager.getInstitutionList(districtId,institutionType,userName);
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
	}	
		
	@RequestMapping(value="/dphsearch", method=RequestMethod.POST)
	public @ResponseBody void search(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
			log.debug(this.getClass().getName() + "- Entering ");
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			String institutionType = request.getParameter("institutionType");
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			String programName=request.getParameter("programName");
			String frequency = request.getParameter("frequency");
			String week = request.getParameter("week");
			String quarter = request.getParameter("quarter");
			String month = request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			String searchDate = request.getParameter("searchDate");
			
			PrintWriter out = response.getWriter();
			JSONArray data=new JSONArray();
			JSONObject obj = new JSONObject();
			String dataEntryLevel = Util.getDataEntryLevel(districtId,institutionType,institutionId);
			List<CommonStringList> classificationList=null;
			String classificationName =null;
			JSONArray dphDirectorateRecords =null;
			
				 classificationList=commonManager.getGeneralCategoryListByFrequency(ShdrcConstants.DirectorateId.DPH,frequency,dataEntryLevel,ShdrcQueryList.DPH_GENERAL_CATEGORY_LIST_BY_FREQUENCY);
				 if(classificationList!=null){
			        	CommonStringList commonStringList = classificationList.get(0);
			        	classificationName = commonStringList.getName();
			        	dphDirectorateRecords = dphDataEntryManager.getDphDirectorateRecords(districtId,institutionType,institutionId,classificationName,programName,frequency,week,quarter,
			   				 searchDate, month, year);
			   
			        	if(dphDirectorateRecords==null){
			    			dphDirectorateRecords = dphDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.DPH,classificationName,frequency,"N");
			    		}			  	
			}
		
			String json=null;
			if(classificationList!=null){
				obj.put("isFrequencySelectionValid", true);
			}else
				obj.put("isFrequencySelectionValid", false);
			json=new Gson().toJson(classificationList);
			obj.put("dphDirectorateRecords", dphDirectorateRecords);
			obj.put("genearlCategoryList", json);
			data.put(obj);
			out.println(obj);
			log.debug(this.getClass().getName() + "- Exit ");
	}
	
	@RequestMapping(value="/dphtabChange", method=RequestMethod.POST)
	public @ResponseBody void onTabChange(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try{
			log.debug(this.getClass().getName() + "- Entering ");
		Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
		String institutionType = request.getParameter("institutionType");
		Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
		String programName=request.getParameter("programName");
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
		JSONArray dphDirectorateRecords = dphDataEntryManager.getDphDirectorateRecords(districtId,institutionType,institutionId,classificationName,
				programName,frequency,week,quarter,searchDate, month, year);
		if(dphDirectorateRecords==null && classificationName!=null){
			dphDirectorateRecords = dphDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.DPH,classificationName,frequency,"N");
		}
		obj.put("dphDirectorateRecords", dphDirectorateRecords);
		obj.put("genearlCategoryList", json);
		data.put(obj);
		out.println(obj);
		log.debug(this.getClass().getName() + "- Exit ");
		}finally{}
	}
	
	@RequestMapping(value="/dphsave", method=RequestMethod.POST)
	public @ResponseBody void doSave(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException, JSONException{
	
		PrintWriter out = response.getWriter();
			log.debug(this.getClass().getName() + "- Entering ");
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			String institutionType = request.getParameter("institutionType");
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			String classificationName = request.getParameter("classificationName");
			String programName=request.getParameter("programName");
			String frequency = request.getParameter("frequency");
			String week = request.getParameter("week");
			String quarter = request.getParameter("quarter");
			String month = request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			String searchDate = request.getParameter("searchDate");
			
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray gridRecords = new JSONArray(handsOnTableData);
			
			boolean successFlag =dphDataEntryManager.insertDphDirectorateRecords(districtId,institutionType,institutionId, classificationName,programName,frequency,week,quarter,
						searchDate, month, year,gridRecords);
			if(successFlag)
				out.println("The record has been saved successfully");
			else
				out.println("Save Error");
				log.debug(this.getClass().getName() + "- Exit ");
	}
	
	@RequestMapping(value="/dphupdate", method=RequestMethod.POST)
	public @ResponseBody void doUpdate(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException, JSONException{
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			PrintWriter out = response.getWriter();
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray jsonArray = new JSONArray(handsOnTableData);
			
			boolean successFlag = dphDataEntryManager.updateDphDirectorateRecords(jsonArray);
			if(successFlag)
				out.println("The record has been updated successfully");
			else
				out.println("Save Error");
				log.debug(this.getClass().getName() + "- Exit ");
		}finally{}
	
	}
	
}
