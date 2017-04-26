package gov.shdrc.dataentry.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.dataentry.service.IMaDataEntryManager;
import gov.shdrc.exception.SHDRCException;
import gov.shdrc.util.CSRFTokenUtil;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
public class MaDataEntryController {
	private static Logger log=Logger.getLogger(MaDataEntryController.class);
	@Autowired
	IMaDataEntryManager maDataEntryManager;
	@Autowired
	ICommonManager commonManager;
	
	@RequestMapping(value="/maDataEntry", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException, SHDRCException, UnsupportedEncodingException{		
	   
		ModelAndView modelView=null;
	  
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.MA);
		    modelView.setViewName("maDataentry");			
			
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
	       modelView.addObject("directorateServletList", encodeJSONArray(directorateServletList.toString()));
	        
	       List<CommonStringList> regionList= maDataEntryManager.getRegions(userName);
	       modelView.addObject("regionList", regionList);
	        
	        String regionName=null;
	        String regionOrCorporationName=null;
	        String municipalityName=null;
	        String isCorporation=null;
	        String selectedInstitutionName=null;
			List<CommonStringList> corporationAndMunicipalityList=null;
			List<CommonStringList> institutionList =null;
			if(Util.isNotNull(regionList) && regionList.size()>0){
	        	CommonStringList region = regionList.get(0);
	        	regionOrCorporationName=region.getName();
	        	regionName=regionOrCorporationName;
	        	int corporationIndex=regionOrCorporationName.indexOf("Corporation");
	        	isCorporation=(corporationIndex!=-1)?"Y":"N";
	        	
	        	if("N".equalsIgnoreCase(isCorporation)){
		        	if(ShdrcConstants.SELECTALLID==region.getId()){
		        		corporationAndMunicipalityList = new ArrayList<CommonStringList>();
		        		CommonStringList commonStringList = new CommonStringList();
		    			commonStringList.setId(ShdrcConstants.SELECTALLID);
		    			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
		    			corporationAndMunicipalityList.add(commonStringList);
		        	}else{
		        		corporationAndMunicipalityList= maDataEntryManager.getCorporationAndMunicipalityList(region.getId(),userName);
		        	}
		        	 modelView.addObject("corporationAndMunicipalityList", corporationAndMunicipalityList);
	        	}else{
	        		if(ShdrcConstants.SELECTALLID==region.getId()){
	        			institutionList = new ArrayList<CommonStringList>();
		        		CommonStringList commonStringList = new CommonStringList();
		    			commonStringList.setId(ShdrcConstants.SELECTALLID);
		    			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
		    			institutionList.add(commonStringList);
		        	}else{
		        		institutionList= maDataEntryManager.getInstitutionList(region.getId(), userName, isCorporation);
		        		 modelView.addObject("institutionList", institutionList);
		        	}
	        		selectedInstitutionName=institutionList.get(0).getName();
	        	}	
	        }
			 if(corporationAndMunicipalityList!=null && corporationAndMunicipalityList.size()>0){
		        	CommonStringList municipality = corporationAndMunicipalityList.get(0);
		        	if(ShdrcConstants.SELECTALLID==municipality.getId()){
	        			institutionList = new ArrayList<CommonStringList>();
		        		CommonStringList commonStringList = new CommonStringList();
		    			commonStringList.setId(ShdrcConstants.SELECTALLID);
		    			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
		    			institutionList.add(commonStringList);
		        	}else{
		        		institutionList= maDataEntryManager.getInstitutionList(municipality.getId(), userName, isCorporation);
		        	}
		        	municipalityName=municipality.getName();
		        	 modelView.addObject("institutionList", institutionList);
		        	selectedInstitutionName=institutionList.get(0).getName();
		        	
		      }
			
			modelView.addObject("districtName", regionOrCorporationName);
		    modelView.addObject("regionName", regionName);
		    modelView.addObject("municipalityName", municipalityName);
		    modelView.addObject("selectedInstitutionName", selectedInstitutionName);
	        
	        List<CommonStringList> classificationList= commonManager.getClassificationList(ShdrcConstants.DirectorateId.MA,ShdrcQueryList.MA_CLASSIFICATION_LIST);
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

	       modelView.addObject("directorateId", ShdrcConstants.DirectorateId.MA);
	       log.debug(this.getClass().getName() + "- Exit ");
			}finally{} 
		
			return modelView;
	}
	
	private String encodeJSONArray(String jsonArray) throws SHDRCException, UnsupportedEncodingException{
		 
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

	@RequestMapping(value="/maregionChange", method=RequestMethod.POST)
	public @ResponseBody void getMunicipality(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		
		PrintWriter out = response.getWriter();
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
			String userName = UserInfo.getLoggedInUserName();
			List<CommonStringList> corporationAndMunicipalityList= null;
			String isCorporation = request.getParameter("isCorporation");
			Integer regionOrCorporationId = Integer.parseInt(request.getParameter("regionOrCorporationId"));
			String json=null;
			if("N".equalsIgnoreCase(isCorporation)){
				if(Util.isNotNull(request.getParameter("regionOrCorporationId")) && !regionOrCorporationId.equals(ShdrcConstants.SELECTALLID)){
					corporationAndMunicipalityList= maDataEntryManager.getCorporationAndMunicipalityList(regionOrCorporationId,userName);
				}else{
					corporationAndMunicipalityList = new ArrayList<CommonStringList>();
					CommonStringList commonStringList = new CommonStringList();
					commonStringList.setId(ShdrcConstants.SELECTALLID);
					commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
					corporationAndMunicipalityList.add(commonStringList);
				}
				json=new Gson().toJson(corporationAndMunicipalityList); 
			}else{
				List<CommonStringList> institutionList= null;
				if(Util.isNotNull(request.getParameter("regionOrCorporationId")) && !regionOrCorporationId.equals(ShdrcConstants.SELECTALLID)){
					institutionList= maDataEntryManager.getInstitutionList(regionOrCorporationId,userName,isCorporation);
				}else{
					institutionList = new ArrayList<CommonStringList>();
					CommonStringList commonStringList = new CommonStringList();
					commonStringList.setId(ShdrcConstants.SELECTALLID);
					commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
					institutionList.add(commonStringList);
				}
				json=new Gson().toJson(institutionList); 
			}	
			out.println(json);	
			log.debug(this.getClass().getName() + "- Exit ");
			}finally{}
	}	
	
	@RequestMapping(value="/macorpChange", method=RequestMethod.POST)
	public @ResponseBody void getInstitution(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
			PrintWriter out = response.getWriter();
			String userName = UserInfo.getLoggedInUserName();
			List<CommonStringList> institutionList= null;
			Integer corpMunicipalityId = Integer.parseInt(request.getParameter("corpMunicipalityId"));
			if(Util.isNotNull(corpMunicipalityId)&&!corpMunicipalityId.equals(ShdrcConstants.SELECTALLID)){
				institutionList= maDataEntryManager.getInstitutionList(corpMunicipalityId,userName,"N");
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
		
	@RequestMapping(value="/masearch", method=RequestMethod.POST)
	public @ResponseBody void search(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
			Integer municipalityId=(Util.isNotNull(request.getParameter("municipalityId"))?Integer.parseInt(request.getParameter("municipalityId")):null);
			String regionName = request.getParameter("regionName");
			Integer regionOrCorporationId = (Util.isNotNull(request.getParameter("regionOrCorporationId"))?Integer.parseInt(request.getParameter("regionOrCorporationId")):null);
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
			String classificationName =null;
			JSONArray maDirectorateRecords =null;
			String dataEntryLevel=null;
			if(regionOrCorporationId!=null
					&& regionOrCorporationId!=ShdrcConstants.SELECTALLID
					&& municipalityId==-99 && institutionId!=null && institutionId==ShdrcConstants.SELECTALLID)//Corporation
				dataEntryLevel="CM";
			else if(regionOrCorporationId!=null
					&& regionOrCorporationId!=ShdrcConstants.SELECTALLID
					&& municipalityId==-99 && institutionId!=null && institutionId!=ShdrcConstants.SELECTALLID) //Corporation - Institution Level
				dataEntryLevel="C";
			else if(regionOrCorporationId!=null
					&& regionOrCorporationId!=ShdrcConstants.SELECTALLID
					&& municipalityId!=null && municipalityId!=ShdrcConstants.SELECTALLID 
					&& institutionId!=null && institutionId!=ShdrcConstants.SELECTALLID)//Municipality - Institution Level
				dataEntryLevel="M";
			else //Municipality Level
				dataEntryLevel="CM";
			
				 classificationList=commonManager.getGeneralCategoryListByFrequency(ShdrcConstants.DirectorateId.MA,frequency,dataEntryLevel,ShdrcQueryList.MA_GENERAL_CATEGORY_LIST_BY_FREQUENCY);
				 if(classificationList!=null){
			        	CommonStringList commonStringList = classificationList.get(0);
			        	classificationName = commonStringList.getName();
			        	maDirectorateRecords = maDataEntryManager.getMaDirectorateRecords(regionName,municipalityId,regionOrCorporationId,institutionId,classificationName,frequency,week,quarter,
			   				 searchDate, month, year);
			        	
			        	if(maDirectorateRecords==null)
			    			maDirectorateRecords = maDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.MA,classificationName,frequency,"N",dataEntryLevel);
				 } 			
		
			String json=null;
			if(classificationList!=null){
				obj.put("isFrequencySelectionValid", true);
			}else
				obj.put("isFrequencySelectionValid", false);
			json=new Gson().toJson(classificationList);
			obj.put("maDirectorateRecords", maDirectorateRecords);
			obj.put("genearlCategoryList", json);
			data.put(obj);
			out.println(obj);
			log.debug(this.getClass().getName() + "- Exit ");
			}finally{}
	}
	
	@RequestMapping(value="/matabChange", method=RequestMethod.POST)
	public @ResponseBody void onTabChange(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
			Integer municipalityId=(Util.isNotNull(request.getParameter("municipalityId"))?Integer.parseInt(request.getParameter("municipalityId")):null);
			String regionName = request.getParameter("regionName");
			Integer regionOrCorporationId = (Util.isNotNull(request.getParameter("regionOrCorporationId"))?Integer.parseInt(request.getParameter("regionOrCorporationId")):null);
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
			String dataEntryLevel=null;
			if(regionOrCorporationId!=null
					&& regionOrCorporationId!=ShdrcConstants.SELECTALLID
					&& municipalityId==-99 && institutionId!=null && institutionId==ShdrcConstants.SELECTALLID)//Corporation
				dataEntryLevel="CM";
			else if(regionOrCorporationId!=null
					&& regionOrCorporationId!=ShdrcConstants.SELECTALLID
					&& municipalityId==-99 && institutionId!=null && institutionId!=ShdrcConstants.SELECTALLID) //Corporation - Institution Level
				dataEntryLevel="C";
			else if(regionOrCorporationId!=null
					&& regionOrCorporationId!=ShdrcConstants.SELECTALLID
					&& municipalityId!=null && municipalityId!=ShdrcConstants.SELECTALLID 
					&& institutionId!=null && institutionId!=ShdrcConstants.SELECTALLID)//Municipality - Institution Level
				dataEntryLevel="M";
			else //Municipality Level
				dataEntryLevel="CM";
			JSONArray maDirectorateRecords = maDataEntryManager.getMaDirectorateRecords(regionName,municipalityId,regionOrCorporationId,institutionId,classificationName,frequency,week,quarter,
	  				 searchDate, month, year);
	
			if(maDirectorateRecords==null && classificationName!=null){
				maDirectorateRecords = maDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.MA,classificationName,frequency,"N",dataEntryLevel);
			}		
			
			obj.put("maDirectorateRecords", maDirectorateRecords);
			obj.put("genearlCategoryList", json);
			data.put(obj);
			out.println(obj);
			log.debug(this.getClass().getName() + "- Exit ");
			}finally{}
	}
	
	@RequestMapping(value="/masave", method=RequestMethod.POST)
	public @ResponseBody void doSave(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException, JSONException{
		PrintWriter out = response.getWriter();
			log.debug(this.getClass().getName() + "- Entering ");
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer municipalityId=(Util.isNotNull(request.getParameter("municipalityId"))?Integer.parseInt(request.getParameter("municipalityId")):null);
			String classificationName = request.getParameter("classificationName");
			String regionName = request.getParameter("regionName");
			Integer regionOrCorporationId = (Util.isNotNull(request.getParameter("regionOrCorporationId"))?Integer.parseInt(request.getParameter("regionOrCorporationId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			String frequency = request.getParameter("frequency");
			String week = request.getParameter("week");
			String quarter = request.getParameter("quarter");
			String month = request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			String searchDate = request.getParameter("searchDate");
			
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray gridRecords = new JSONArray(handsOnTableData);
			
			String dataEntryLevel=null;
			if(regionOrCorporationId!=null
					&& regionOrCorporationId!=ShdrcConstants.SELECTALLID
					&& municipalityId==-99 && institutionId!=null && institutionId==ShdrcConstants.SELECTALLID)
				dataEntryLevel="C";
			else if(regionOrCorporationId!=null
					&& regionOrCorporationId!=ShdrcConstants.SELECTALLID
					&& municipalityId==-99 && institutionId!=null && institutionId!=ShdrcConstants.SELECTALLID)
				dataEntryLevel="CI";
			else if(regionOrCorporationId!=null
					&& regionOrCorporationId!=ShdrcConstants.SELECTALLID
					&& municipalityId!=null && municipalityId!=ShdrcConstants.SELECTALLID 
					&& institutionId!=null && institutionId==ShdrcConstants.SELECTALLID)
				dataEntryLevel="M";
			else
				dataEntryLevel="MI";
			
			boolean successFlag =maDataEntryManager.insertMaDirectorateRecords(regionName,regionOrCorporationId,municipalityId,districtId,institutionId,classificationName,frequency,week,quarter,
	  				 searchDate, month, year,gridRecords,dataEntryLevel);
			if(successFlag)
				out.println("The record has been saved successfully");
			else
				out.println("Save Error");
				log.debug(this.getClass().getName() + "- Exit ");		
	}
	
	@RequestMapping(value="/maupdate", method=RequestMethod.POST)
	public @ResponseBody void doUpdate(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException, JSONException{
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			PrintWriter out = response.getWriter();
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray jsonArray = new JSONArray(handsOnTableData);
			boolean successFlag = maDataEntryManager.updateMADirectorateRecords(jsonArray);
			if(successFlag)
				out.println("The record has been updated successfully");
			else
				out.println("Save Error");
				log.debug(this.getClass().getName() + "- Exit ");
			
		}finally{}
	
	}
	
}
