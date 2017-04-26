package gov.shdrc.servicemaintenance.controller;

import gov.shdrc.servicemaintenance.service.IDirectorateMasterManager;
import gov.shdrc.servicemaintenance.util.DirectorateMaster;
import gov.shdrc.util.CSRFTokenUtil;
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
public class DirectorateMasterController {
	@Autowired(required=true)
	IDirectorateMasterManager directorateManager;
	
	@RequestMapping(value="/DirectorateMasterMaintenance", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{		
	   
		ModelAndView modelView=null;
		String csrfToken =null;		
			
		try { 
		    modelView=new ModelAndView();
		    modelView.setViewName("directorateMasterMaintenance");			
			
		    csrfToken = CSRFTokenUtil.getToken (request.getSession()); 
	        modelView.addObject("CSRF_TOKEN", csrfToken);
	       	        
	        List<DirectorateMaster> directorateDetailsList=directorateManager.getDirectorateDetailsList();
	        modelView.addObject("directorateDetailsList", directorateDetailsList);
	      
			}finally{} 
		
			return modelView;
	}
	
	@RequestMapping(value="/DirectorateMasterAdd", method=RequestMethod.GET)
	public ModelAndView onLoadDirectorateMasterAdd(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{		
	   
		ModelAndView modelView=null;
		String csrfToken =null;		
			
		try { 
		    modelView=new ModelAndView();
		    modelView.setViewName("directorateMasterAdd");			
			
		    csrfToken = CSRFTokenUtil.getToken (request.getSession()); 
	        modelView.addObject("CSRF_TOKEN", csrfToken);
		}finally{} 
		
		return modelView;
	}
	
	@RequestMapping(value="/DirectorateMasterChange", method=RequestMethod.GET)
	public ModelAndView onLoadDirectorateMasterChange(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{		
	   
		ModelAndView modelView=null;
		String csrfToken =null;		
			
		try { 
		    modelView=new ModelAndView();
		    modelView.setViewName("directorateMasterChange");			
			
		    csrfToken = CSRFTokenUtil.getToken (request.getSession()); 
	        modelView.addObject("CSRF_TOKEN", csrfToken);
	        
	        Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			String directorateName=request.getParameter("directorateName");
			String directorateLevel=request.getParameter("directorateLevel");
			//Integer directorateActive=(Util.isNotNull(request.getParameter("directorateActive"))?Integer.parseInt(request.getParameter("directorateActive")):null);
			modelView.addObject("directorateId", directorateId);
			modelView.addObject("directorateName", directorateName);
			modelView.addObject("directorateLevel",directorateLevel);
			//modelView.addObject("directorateActive", directorateActive);
		}finally{} 
		
		return modelView;
	}
	
	@RequestMapping(value="/DirectorateMasterAddOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void addDirectorateMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			String directorateName=request.getParameter("directorateName");
			String directorateLevel=request.getParameter("directorateLevel");
			//Integer active=(Util.isNotNull(request.getParameter("directorateActive"))?Integer.parseInt(request.getParameter("directorateActive")):null);
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			String userName = UserInfo.getLoggedInUserName();
			boolean  isvalid=false; 
			isvalid = CSRFTokenUtil.isValid (request);
		    validateFields(directorateName,directorateLevel);				
			successFlag=directorateManager.insertDirectorate(directorateName,directorateLevel,userName);        
	        if(successFlag)
	        	out.println("Directorate has been added successfully");
			else
				out.println("Problem in adding the Directorate");
			
	}finally{}
	}
	
	@RequestMapping(value="/DirectorateMasterChangeOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void changeDirectorateMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			String directorateName=request.getParameter("directorateName");
			String directorateLevel=request.getParameter("directorateLevel");
			//Integer active=(Util.isNotNull(request.getParameter("directorateActive"))?Integer.parseInt(request.getParameter("directorateActive")):null);
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			String userName = UserInfo.getLoggedInUserName();
			boolean  isvalid=false; 
			isvalid = CSRFTokenUtil.isValid (request);
		    validateFields(directorateName,directorateLevel);				
		    successFlag=directorateManager.updateDirectorate(directorateId,directorateName,directorateLevel);        
	        if(successFlag)
	        	out.println("Directorate details have been changed successfully");
			else
				out.println("Problem in changing the Directorate details");
			
	}finally{}
	}
	
	private void validateFields(String directorateName,String directorateLevel )throws SHDRCException{
		if(Util.isNullOrBlank(directorateName)){
			throw new SHDRCException("Please Enter the name of the Directorate");
		}
		if(Util.isNullOrBlank(directorateLevel)){
			throw new SHDRCException("Please select the level of the Directorate");
		}
		
	}

}
