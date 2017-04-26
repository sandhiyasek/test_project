package gov.shdrc.dataentry.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.dataentry.service.IDrmgrDataEntryManager;
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
public class DrmgrDataEntryController {
	private static Logger log =Logger.getLogger(DrmgrDataEntryController.class);
	@Autowired
	IDrmgrDataEntryManager drmgrDataEntryManager;
	@Autowired
	ICommonManager commonManager;
	
	@RequestMapping(value="/drmgrDataEntry", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException, SHDRCException{		
	   
		ModelAndView modelView=null;
	  
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.DRMGR);
		    modelView.setViewName("drmgrDataentry");			
			
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
	        
	        List<CommonStringList> departmentList= drmgrDataEntryManager.getDepartmentList(userName);
	        modelView.addObject("departmentList", departmentList);
	        
	        List<Integer> yearList= Util.yearList;
	        modelView.addObject("yearList", yearList);
	        
	        List<CommonStringList> quarterList= Util.quarterList;
	        modelView.addObject("quarterList", quarterList);
	      
	        List<CommonStringList> monthsList= Util.monthsList;
	        modelView.addObject("monthsList", monthsList);
	        
            modelView.addObject("directorateId", ShdrcConstants.DirectorateId.DRMGR);
	        
	        List<CommonStringList> classificationList= commonManager.getClassificationList(ShdrcConstants.DirectorateId.DRMGR,ShdrcQueryList.DRMGR_CLASSIFICATION_LIST);
	        modelView.addObject("classificationList", classificationList);
	     
	        modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));
			log.debug(this.getClass().getName() + "- Exit ");		
			} finally{} 
		
			return modelView;
	}
		
	@RequestMapping(value="/drmgrsearch", method=RequestMethod.POST)
	public @ResponseBody void search(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
			Integer departmentId=(Util.isNotNull(request.getParameter("departmentId"))?Integer.parseInt(request.getParameter("departmentId")):null);
			String departmentName = request.getParameter("departmentName");
			String frequency = request.getParameter("frequency");
			String quarter = request.getParameter("quarter");
			String month = request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
		
			PrintWriter out = response.getWriter();
			JSONArray data=new JSONArray();
			JSONObject obj = new JSONObject();
			
			String dataEntryLevel = getDataEntryLevel(departmentName);
			List<CommonStringList> classificationList=null;
			String classificationName =null;
			JSONArray drmgrDirectorateRecords =null;
	
				if(departmentName.equalsIgnoreCase("Dept of Immunology")){/*Column Ind_Freq is Limited to 1 Character in Indicator Master table but Some common classification needs to be displayed on 
					Quarterly Frequency for this Department and on Monthly Frequency for all other Departments. Because of this 'M' is added in Ind Freq column and so its hardcoded as monthly for this Department here*/
	    			frequency="Monthly";	
				}
				 classificationList=commonManager.getGeneralCategoryListByFrequency(ShdrcConstants.DirectorateId.DRMGR,frequency,dataEntryLevel,ShdrcQueryList.DRMGR_GENERAL_CATEGORY_LIST_BY_FREQUENCY);
				
				 if(classificationList!=null){
			        	CommonStringList commonStringList = classificationList.get(0);
			        	classificationName = commonStringList.getName();
			        	if(departmentName.equalsIgnoreCase("Dept of Immunology")){ /*While saving the values of this Department, Frequency is saved as Quarterly. So to retrieve the values from 
			        	Transaction table, Frequency is hardcoded to Quarterly*/
			    			frequency="Quarterly";	
						}
			        	drmgrDirectorateRecords = drmgrDataEntryManager.getDrMgrDirectorateRecords(departmentId,classificationName,frequency,
			   				 quarter,month, year);
			        	
			        	if(drmgrDirectorateRecords==null){
			        		if(departmentName.equalsIgnoreCase("Dept of Immunology")){/*Column Ind_Freq is Limited to 1 Character in Indicator Master table but Some common classification needs to be displayed on 
			    				Quarterly Frequency for this Department and on Monthly Frequency for all other Departments. Because of this 'M' is added in Ind Freq column and so its hardcoded as monthly for this Department here*/
			        			frequency="Monthly";   
			        		}
			    			drmgrDirectorateRecords = drmgrDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.DRMGR,classificationName,frequency,"N",dataEntryLevel);
			    		}
				 } 	
		
			String json=null;
			if(classificationList!=null){
				obj.put("isFrequencySelectionValid", true);
			}else
				obj.put("isFrequencySelectionValid", false);
			json=new Gson().toJson(classificationList);
			obj.put("drmgrDirectorateRecords", drmgrDirectorateRecords);
			obj.put("genearlCategoryList", json);
			data.put(obj);
			out.println(obj);	
			log.debug(this.getClass().getName() + "- Exit ");		
			}finally{} 
	}
	
	private String getDataEntryLevel(String departmentName){
		String indicatorHierarchy=null;
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
			 switch (departmentName) {
	         case "Finance Section":
	        	 indicatorHierarchy="FI";
	             break;	         
	         case "Academic Section":
	        	 indicatorHierarchy="A";
	             break;	
	         case "Dept of Transfusion Medicine":
	        	 indicatorHierarchy="TM";
	             break;	
	         case "Dept of Experimental medicine":
	        	 indicatorHierarchy="EX";
	             break;	
	         case "Dept of Curriculum Development":
	        	 indicatorHierarchy="CD";
	             break;	
	         case "Dept of Immunology":
	        	 indicatorHierarchy="IM";
	             break;	
	         case "Dept of Medical Genetics":
	        	 indicatorHierarchy="MG";
	             break;	
	         case "Dept of Epidemology":
	        	 indicatorHierarchy="EP";
	             break;	
	         case "Dept of Siddha":
	        	 indicatorHierarchy="SI";
	             break;	
	         case "University Library":
	        	 indicatorHierarchy="LIB";
	             break;
	         case "University Computer Centre":
	        	 indicatorHierarchy="UC";
	             break;
	         case "Students Welfare section":
	        	 indicatorHierarchy="SW";
	             break;
	         default:
	             throw new IllegalArgumentException("Invalid Department name: " + departmentName);
			 	}
			 log.debug(this.getClass().getName() + "- Exit ");		
			} finally{} 
			 return indicatorHierarchy;
		}
	
	@RequestMapping(value="/drmgrtabChange", method=RequestMethod.POST)
	public @ResponseBody void onTabChange(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
			Integer departmentId=(Util.isNotNull(request.getParameter("departmentId"))?Integer.parseInt(request.getParameter("departmentId")):null);
			String departmentName = request.getParameter("departmentName");
			String frequency = request.getParameter("frequency");
			String quarter = request.getParameter("quarter");
			String month = request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			
			PrintWriter out = response.getWriter();
			JSONArray data=new JSONArray();
			JSONObject obj = new JSONObject();
			
			String dataEntryLevel = getDataEntryLevel(departmentName);
			List<CommonStringList> classificationList=null;
			String classificationName = request.getParameter("classificationName");
			String json=null;
			obj.put("isFrequencySelectionValid", true);
			json=new Gson().toJson(classificationList);
			if(departmentName.equalsIgnoreCase("Dept of Immunology")){/*While saving the values of this Department, Frequency is saved as Quarterly. So to retrieve the values from 
	        	Transaction table, Frequency is hardcoded to Quarterly*/
				frequency="Quarterly";	
			}
			JSONArray drmgrDirectorateRecords = drmgrDataEntryManager.getDrMgrDirectorateRecords(departmentId,classificationName,frequency,quarter, month, year);
		
			if(drmgrDirectorateRecords==null && classificationName!=null){
				if(departmentName.equalsIgnoreCase("Dept of Immunology")){/*Column Ind_Freq is Limited to 1 Character in Indicator Master table but Some common classification needs to be displayed on 
					Quarterly Frequency for this Department and on Monthly Frequency for all other Departments. Because of this 'M' is added in Ind Freq column and so its hardcoded as monthly for this Department here*/
	    			frequency="Monthly";	
				}
				drmgrDirectorateRecords = drmgrDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.DRMGR,classificationName,frequency,"N",dataEntryLevel);
			}
			obj.put("drmgrDirectorateRecords", drmgrDirectorateRecords);
			obj.put("genearlCategoryList", json);
			data.put(obj);
			out.println(obj);	
			 log.debug(this.getClass().getName() + "- Exit ");		
			} finally{} 
	}
	
	@RequestMapping(value="/drmgrsave", method=RequestMethod.POST)
	public @ResponseBody void doSave(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException, JSONException{
		
		PrintWriter out = response.getWriter();
			log.debug(this.getClass().getName() + "- Entering ");
			Integer departmentId=(Util.isNotNull(request.getParameter("departmentId"))?Integer.parseInt(request.getParameter("departmentId")):null);
			String departmentName = request.getParameter("departmentName");
			String classificationName = request.getParameter("classificationName");
			String frequency = request.getParameter("frequency");
			String quarter = request.getParameter("quarter");
			String month = request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray gridRecords = new JSONArray(handsOnTableData);
			String dataEntryLevel = getDataEntryLevel(departmentName);
		
			if(departmentName.equalsIgnoreCase("Dept of Immunology")){ /*Values of this Department are saved on Quarterly Frequency in Transaction table*/
    			frequency="Quarterly";	
			}
			boolean successFlag =drmgrDataEntryManager.insertDrMgrDirectorateRecords(departmentId, classificationName,frequency,quarter, month,year,
					gridRecords,dataEntryLevel);
			if(successFlag)
				out.println("The record has been saved successfully");
			else
				out.println("Save Error");
				log.debug(this.getClass().getName() + "- Exit ");
	}
	
	@RequestMapping(value="/drmgrupdate", method=RequestMethod.POST)
	public @ResponseBody void doUpdate(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException, JSONException{
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			PrintWriter out = response.getWriter();
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray jsonArray = new JSONArray(handsOnTableData);
			
			boolean successFlag = drmgrDataEntryManager.updateDrMgrDirectorateRecords(jsonArray);
			if(successFlag)
				out.println("The record has been updated successfully");
			else
				out.println("Save Error");
				log.debug(this.getClass().getName() + "- Exit ");
		}finally{}
	
	}
	
}
