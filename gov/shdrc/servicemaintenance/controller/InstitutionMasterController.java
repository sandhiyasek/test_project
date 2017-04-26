package gov.shdrc.servicemaintenance.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.servicemaintenance.service.IInstitutionMasterManager;
import gov.shdrc.servicemaintenance.util.InstitutionMaster;
import gov.shdrc.util.CSRFTokenUtil;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SHDRCException;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InstitutionMasterController {
	@Autowired(required=true)
	IInstitutionMasterManager iInstitutionMasterManager;
	@Autowired(required=true)
	ICommonManager commonManager;
	
	@RequestMapping(value="/InstitutionMasterMaintenance", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("institutionMasterMaintenance");
			
			csrfToken = CSRFTokenUtil.getToken (request.getSession());
			modelView.addObject("CSRF_TOKEN", csrfToken);
			
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
			 List<CommonStringList> directorateList= commonManager.getDirectorateListById(directorateId);
			 modelView.addObject("directorateList", directorateList);
		        List<CommonStringList> districtList= commonManager.getDistricts(userName);
		        modelView.addObject("districts", districtList);
		        List<InstitutionMaster> institutionDetailsList=iInstitutionMasterManager.getInstitutionDetailsList(directorateId);
		        modelView.addObject("institutionDetailsList", institutionDetailsList);
		        modelView.addObject("directorateId", directorateId);
		        
		        
		        
		
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/InstitutionMasterAdd", method=RequestMethod.GET)
	public ModelAndView onLoadInstitutionMasterAdd(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("institutionMasterAdd");
			csrfToken = CSRFTokenUtil.getToken (request.getSession());
			modelView.addObject("CSRF_TOKEN", csrfToken);
			
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
			  List<CommonStringList> directorateList= commonManager.getDirectorateListById(directorateId);
			 modelView.addObject("directorateList", directorateList);
		        List<CommonStringList> districtList= commonManager.getDistricts(userName);
		        modelView.addObject("districts", districtList);
		        List<InstitutionMaster> institutionDetailsList=iInstitutionMasterManager.getInstitutionDetailsList(directorateId);
		        modelView.addObject("institutionDetailsList", institutionDetailsList);
		        modelView.addObject("directorateId", directorateId);
		        
		        String hudType=iInstitutionMasterManager.getHUDType(directorateId);
				 List<CommonStringList> hudList= iInstitutionMasterManager.getHudNameList(directorateId,hudType);
				 modelView.addObject("hudList", hudList);
				 modelView.addObject("hudType", hudType);
				 modelView.addObject("directorateId", directorateId);
			
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/InstitutionMasterAddOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void addInstitutionMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			
			
	}finally{}
	}
	
	@RequestMapping(value="/InstitutionMasterChangeOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void changeInstitutionMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			
			
	}finally{}
	}
}
