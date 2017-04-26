package gov.shdrc.servicemaintenance.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.shdrc.servicemaintenance.service.IGeneralMasterManager;
import gov.shdrc.servicemaintenance.util.GeneralMaster;
import gov.shdrc.util.CSRFTokenUtil;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SHDRCException;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GeneralMasterController {	
	@Autowired(required=true)
	IGeneralMasterManager generalMasterManager;
	
	@RequestMapping(value="/GeneralMasterMaintenance", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("generalMasterMaintenance");
			
			csrfToken = CSRFTokenUtil.getToken (request.getSession());
			modelView.addObject("CSRF_TOKEN", csrfToken);
			 Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			 String indicatorName = request.getParameter("indicatorName");
			 String submitType = request.getParameter("submitType");
			 String prevServlet = request.getParameter("prevServlet");
			 
			 String directorateName=(Util.getFullRole(Util.getDirectorateShortName(directorateId)));
			 List<CommonStringList> typeList= generalMasterManager.getTypeList(directorateId);
			 modelView.addObject("directorateName", directorateName);
			 modelView.addObject("indicatorName", indicatorName);
			 modelView.addObject("directorateId", directorateId);
			 modelView.addObject("indicatorId", indicatorId); 
			 modelView.addObject("typeList", typeList);  	
			 modelView.addObject("prevServlet", prevServlet);
			List<GeneralMaster> generalDetailsList=generalMasterManager.getGeneralDetailsList(directorateId);
			modelView.addObject("generalDetailsList", generalDetailsList);
		
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/GeneralMasterAdd", method=RequestMethod.GET)
	public ModelAndView onLoadGeneralMasterAdd(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("generalMasterAdd");
			
			csrfToken = CSRFTokenUtil.getToken (request.getSession());
			modelView.addObject("CSRF_TOKEN", csrfToken);
			
			 Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			 String indicatorName = request.getParameter("indicatorName");
			 String submitType = request.getParameter("submitType");
			 String prevServlet = request.getParameter("prevServlet");
			 
			 String directorateName=(Util.getFullRole(Util.getDirectorateShortName(directorateId)));
			 List<CommonStringList> typeList= generalMasterManager.getTypeList(directorateId);
			 modelView.addObject("directorateName", directorateName);
			 modelView.addObject("indicatorName", indicatorName);
			 modelView.addObject("directorateId", directorateId);
			 modelView.addObject("indicatorId", indicatorId); 
			 modelView.addObject("typeList", typeList);  	
			 modelView.addObject("prevServlet", prevServlet);
			 
			 
			
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/GeneralMasterChange", method=RequestMethod.GET)
	public ModelAndView onLoadGeneralMasterChange(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("generalMasterChange");
			
			csrfToken = CSRFTokenUtil.getToken (request.getSession());
			modelView.addObject("CSRF_TOKEN", csrfToken);
			
			 Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			 String indicatorName = request.getParameter("indicatorName");
			 String submitType = request.getParameter("submitType");
			 String prevServlet = request.getParameter("prevServlet");
			 
			 String directorateName=(Util.getFullRole(Util.getDirectorateShortName(directorateId)));
			 List<CommonStringList> typeList= generalMasterManager.getTypeList(directorateId);
			 modelView.addObject("directorateName", directorateName);
			 modelView.addObject("indicatorName", indicatorName);
			 modelView.addObject("directorateId", directorateId);
			 modelView.addObject("indicatorId", indicatorId); 
			 modelView.addObject("typeList", typeList);  	
			 modelView.addObject("prevServlet", prevServlet);
			String prevSubmitType=request.getParameter("prevSubmitType");
    		GeneralMaster generalDetails =generalMasterManager.getGeneralDetails(directorateId,indicatorId);
    		
    		request.setAttribute("prevSubmitType", prevSubmitType);
    		request.setAttribute("generalDetails", generalDetails);
		
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/GeneralMasterAddOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void addGeneralMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		String userName = UserInfo.getLoggedInUserName();
		try{
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			String indicatorName=request.getParameter("indicatorName");
			String generalCategory=request.getParameter("generalCategory");
			String type=request.getParameter("type");
			String otherType=request.getParameter("otherType");
			String generalName=request.getParameter("generalName");
			String subCategory=request.getParameter("subCategory");
			String subSubCategory=request.getParameter("subSubCategory");
			String classification=request.getParameter("classification");
			boolean  isvalid=false; 
			isvalid = CSRFTokenUtil.isValid (request);
			validateFields(generalCategory,type,otherType,generalName,subCategory,subSubCategory,classification);
			successFlag=generalMasterManager.insertGeneral(directorateId,indicatorId,generalCategory,type,otherType,generalName,subCategory,subSubCategory,classification,userName);  
		    if(successFlag)
		    	response.sendRedirect("indicatorSummary.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId);
		    else
		    	out.println("Error in adding the Indicator. Please Contact system administrator");
			
	}finally{}
	}
	
	@RequestMapping(value="/GeneralMasterChangeOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void changeGeneralMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			String indicatorName=request.getParameter("indicatorName");
			String generalCategory=request.getParameter("generalCategory");
			String type=request.getParameter("type");
			String otherType=request.getParameter("otherType");
			String generalName=request.getParameter("generalName");
			String subCategory=request.getParameter("subCategory");
			String subSubCategory=request.getParameter("subSubCategory");
			String classification=request.getParameter("classification");
			boolean  isvalid=false; 
			isvalid = CSRFTokenUtil.isValid (request);
			validateFields(generalCategory,type,otherType,generalName,subCategory,subSubCategory,classification);
			successFlag=generalMasterManager.updateGeneral(directorateId,indicatorId,generalCategory,type,otherType,generalName,subCategory,subSubCategory,classification);  
		    if(successFlag)
		    	response.sendRedirect("indicatorSummary.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId);
		    else
		    	out.println("Error in changing the Indicator. Please Contact system administrator");
			
			
	}finally{}
	}
	private void validateFields(String generalCategory,String type,String otherType,String generalName,String subCategory,
			String subSubCategory,String classification)throws SHDRCException{
		if(Util.isNullOrBlank(generalCategory)){
			throw new SHDRCException("Please enter the General Category of the Indicator");
		}
		if(Util.isNullOrBlank(type)){
			throw new SHDRCException("Please select the General Type of the Indicator");
		}
		else if(type.equalsIgnoreCase("others")){
			if(Util.isNullOrBlank(otherType)){
				throw new SHDRCException("Please enter the General Type of the Indicator");
			}
		}
		if(Util.isNullOrBlank(generalName)){
			throw new SHDRCException("Please enter the General Name");
		}
		if(Util.isNullOrBlank(subCategory)){
			throw new SHDRCException("Please enter General SubCategory of the Indicator");
		}
		if(Util.isNullOrBlank(subSubCategory)){
			throw new SHDRCException("Please enter General SubSubCategory of the Indicator");
		}
		if(Util.isNullOrBlank(classification)){
			throw new SHDRCException("Please enter the General Classification of the Indicator");
		}
	}
}
