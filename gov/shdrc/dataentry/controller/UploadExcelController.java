package gov.shdrc.dataentry.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.dataentry.service.IDfsDataEntryManager;
import gov.shdrc.dataentry.service.IDmeDataEntryManager;
import gov.shdrc.dataentry.service.IMaDataEntryManager;
import gov.shdrc.dataentry.service.IRntcpDataEntryManager;
import gov.shdrc.dataentry.service.ITnmscDataEntryManager;
import gov.shdrc.exception.SHDRCException;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.SplCharsConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class UploadExcelController {
	private static Logger log=Logger.getLogger(ShtdDataEntryController.class);
	@Autowired
	ICommonManager commonManager;
	@Autowired
	IRntcpDataEntryManager rntcpDataEntryManager;
	@Autowired
	ITnmscDataEntryManager tnmscDataEntryManager;
	@Autowired
	IDfsDataEntryManager dfsDataEntryManager;
	@Autowired
	IMaDataEntryManager maDataEntryManager;
	@Autowired
	IDmeDataEntryManager dmeDataEntryManager;
	
	@RequestMapping(value="/uploadExcel", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		ModelAndView modelView=null;	
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
			modelView=uploadExcelOnLoad();
			log.debug(this.getClass().getName() + "- Exit ");
		}catch (Exception e) {
			log.error(this.getClass().getName() + "- onLoad "+e.getMessage());
		} finally{}
		 return modelView;		
	}
	
	@RequestMapping(value="/uploadTNMSCExcel", method=RequestMethod.GET)
	public ModelAndView onLoadTNMSCExcel(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		ModelAndView modelView=null;	
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
			 modelView=new ModelAndView();
			 modelView.setViewName("tnmscFileUpload");
			 
			modelView.addObject("currentHours", Util.getCurrentHours());
		    modelView.addObject("currentDate", Util.getStrDate(new Date()));
		    modelView.addObject("directorateList", getDirectorateList());
		    modelView.addObject("fileList", getFileList());
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
			log.debug(this.getClass().getName() + "- Exit ");
		}catch (Exception e) {
			log.error(this.getClass().getName() + "- onLoadTNMSCExcel "+e.getMessage());
		} finally{}
		 return modelView;		
	}
	
	private ModelAndView uploadExcelOnLoad(){
		ModelAndView modelView=null;
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
	    modelView=new ModelAndView();
	    modelView.setViewName("fileUpload");		
	    
	    modelView.addObject("currentHours", Util.getCurrentHours());
	    modelView.addObject("currentDate", Util.getStrDate(new Date()));
	    modelView.addObject("directorateList", getDirectorateList());
	    modelView.addObject("fileList", getFileList());
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
	    log.debug(this.getClass().getName() + "- Exit ");
		}catch (Exception e) {
			log.error(this.getClass().getName() + "- uploadExcelOnLoad "+e.getMessage());
		} finally{}
	    return modelView;
    
}
	
	private List<gov.shdrc.dataentry.fileupload.File> getFileList(){
		
		List<String> userRoleList = UserInfo.getUserRoleList(); 
		List<gov.shdrc.dataentry.fileupload.File> getFileList=null;
		try{
			log.debug(this.getClass().getName() + "- Entering ");
		boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
		
		String role = null;
		
		if(!isAllDirectorate)
			
			role = UserInfo.getRolesForUser();
		
		String userName = UserInfo.getLoggedInUserName();
		
		getFileList = commonManager.getFileList(isAllDirectorate, role,userName);
		 log.debug(this.getClass().getName() + "- Exit ");
		}catch (Exception e) {
			log.error(this.getClass().getName() + "- uploadExcelOnLoad "+e.getMessage());
		} finally{}
		return getFileList;
	}
	
	private List<CommonStringList> getDirectorateList(){
	
		List<String> userRoleList = UserInfo.getUserRoleList(); 
		boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
		String role = null;
		if(!isAllDirectorate)
			role = UserInfo.getRolesForUser();
		List<CommonStringList> directorateList=null;
		try{
			log.debug(this.getClass().getName() + "- Entering ");
		if(!userRoleList.contains("CMCHIS")){
			directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
		}else{
			directorateList =new ArrayList<CommonStringList>();
			CommonStringList commonStringList=new CommonStringList();
			commonStringList.setId(17);
			commonStringList.setName("Chief Ministers Comprehensive Health Insurance Scheme");
			directorateList.add(commonStringList);
		}
		 log.debug(this.getClass().getName() + "- Exit ");
		}catch (Exception e) {
			log.error(this.getClass().getName() + "- getDirectorateList "+e.getMessage());
		} finally{}
		return directorateList;
	}

	@RequestMapping(value="/uploadExcelDelete", method=RequestMethod.POST)
	public @ResponseBody void deleteFile(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		String root =null;
		try{
			log.debug(this.getClass().getName() + "- Entering ");

			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			root = File.separator+"home"+File.separator+"DataIntegration"+File.separator+"DI Source Files";
			String fileName= request.getParameter("fileName");
			String directorateFolder=getDirectorateFolder(directorateId);
	        File path = new File(root + File.separator + ShdrcConstants.TEMPFILEDIRECTORY + File.separator +directorateFolder+  File.separator +fileName);
	        path.delete();
	        PrintWriter out = response.getWriter();
	        out.println("The File has been deleted successfully");
	        log.debug(this.getClass().getName() + "- Exit ");
			}catch (Exception e) {
				log.error(this.getClass().getName() + "- deleteFile "+e.getMessage());
			} finally{}
	}	
	
	private String getDirectorateFolder(Integer directorateId){
		String folderName=null;
		try{
			log.debug(this.getClass().getName() + "- Entering ");
	        switch (directorateId) {
	            case ShdrcConstants.DirectorateId.DMS		:	folderName = ShdrcConstants.FolderName.DMS;
	                     	 									break;
	            case ShdrcConstants.DirectorateId.DPH		:	folderName = ShdrcConstants.FolderName.DPH;
	            			 									break;
	            case ShdrcConstants.DirectorateId.TNMSC		:	folderName = ShdrcConstants.FolderName.TNMSC;
	            			  									break;
	            case ShdrcConstants.DirectorateId.TANSACS	: 	folderName =  ShdrcConstants.FolderName.TANSACS;
	            			 									break;
	            case ShdrcConstants.DirectorateId.NRHM		:	folderName = ShdrcConstants.FolderName.NRHM;
	                     	 									break;
	            case ShdrcConstants.DirectorateId.RNTCP		: 	folderName = ShdrcConstants.FolderName.RNTCP;
	                     	 									break;
	            case ShdrcConstants.DirectorateId.MA		:  	folderName = ShdrcConstants.FolderName.MA;
	                     										break;
	            case ShdrcConstants.DirectorateId.DME		:  	folderName = ShdrcConstants.FolderName.DME;
																break;
	            case ShdrcConstants.DirectorateId.DRMGR		:  	folderName = ShdrcConstants.FolderName.DRMGR;
																break;
	            case ShdrcConstants.DirectorateId.DFW		:  	folderName = ShdrcConstants.FolderName.DFW;
																break;
	            case ShdrcConstants.DirectorateId.SHTO		:  	folderName = ShdrcConstants.FolderName.SHTO;
																break;
	            case ShdrcConstants.DirectorateId.COC		:  	folderName = ShdrcConstants.FolderName.COC;
																break;
	            case ShdrcConstants.DirectorateId.SBCS		:  	folderName = ShdrcConstants.FolderName.SBCS;
																break;
	            case ShdrcConstants.DirectorateId.DCA		:  	folderName = ShdrcConstants.FolderName.DCA;
																break;
	            case ShdrcConstants.DirectorateId.MRB		:  	folderName = ShdrcConstants.FolderName.MRB;
																break;
	            case ShdrcConstants.DirectorateId.DFS		:  	folderName = ShdrcConstants.FolderName.DFS;
																break;
	            case ShdrcConstants.DirectorateId.CMCHIS	:  	folderName = ShdrcConstants.FolderName.CMCHIS;
																break;
	            case ShdrcConstants.DirectorateId.DIM		:  	folderName = ShdrcConstants.FolderName.DIM;
																break;
	            case ShdrcConstants.DirectorateId.NLEP		:  	folderName = ShdrcConstants.FolderName.NLEP;
																break;
	            case ShdrcConstants.DirectorateId.SBHI		:  	folderName = ShdrcConstants.FolderName.SBHI;
																break;
	            case ShdrcConstants.DirectorateId.ESI		:  	folderName = ShdrcConstants.FolderName.ESI;
																break;	
	        }
	        log.debug(this.getClass().getName() + "- Exit ");
			}catch (Exception e) {
				log.error(this.getClass().getName() + "- getDirectorateFolder "+e.getMessage());
			} finally{}
			return folderName;
	}
	
	@RequestMapping(value="/uploadExcelFrequencyChange", method=RequestMethod.POST)
	public @ResponseBody void fileUploadFreqChange(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			String frequency= request.getParameter("frequency");
			 String userName = UserInfo.getLoggedInUserName();
			List<CommonStringList> list=commonManager.getFileListBasedOnFrequency(directorateId,frequency,userName);
			PrintWriter out = response.getWriter();
			String json=new Gson().toJson(list);  
			out.println(json);
			log.debug(this.getClass().getName() + "- Exit ");
			}catch (Exception e) {
				log.error(this.getClass().getName() + "- fileUploadFreqChange "+e.getMessage());
			} finally{}
	}	
	
	@RequestMapping(value="/uploadExcelChangeDirectorate", method=RequestMethod.POST)
	public @ResponseBody void fileUploadDirectorateChange(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			List<CommonStringList> list=getHierarchyDropdown1List(directorateId);
			PrintWriter out = response.getWriter();
			String json=new Gson().toJson(list);  
			out.println(json);
			log.debug(this.getClass().getName() + "- Exit ");
			}catch (Exception e) {
				log.error(this.getClass().getName() + "- uploadExcelChangeDirectorate "+e.getMessage());
			} finally{}
	}	
	
	private List<CommonStringList> getHierarchyDropdown1List(Integer directorateId){
		
		List<CommonStringList> hierarchyDropdown1List=new ArrayList<CommonStringList>();
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			if(directorateId==3){
				hierarchyDropdown1List=  tnmscDataEntryManager.getInstitutionList();
			}else if(directorateId==6){
				String userName = UserInfo.getLoggedInUserName();
				 hierarchyDropdown1List= rntcpDataEntryManager.getDistricts(userName);
			}else if(directorateId==16){
				String userName = UserInfo.getLoggedInUserName();
				 hierarchyDropdown1List= dfsDataEntryManager.getLabList(userName);
			}else if(directorateId==7){
				String userName = UserInfo.getLoggedInUserName();
				 hierarchyDropdown1List= maDataEntryManager.getRegions(userName);
			}
			else{
				String userName = UserInfo.getLoggedInUserName();
				hierarchyDropdown1List= commonManager.getDistricts(userName);
			}
			log.debug(this.getClass().getName() + "- Exit ");
			}catch (Exception e) {
				log.error(this.getClass().getName() + "- getHierarchyDropdown1List "+e.getMessage());
			} finally{}
			return hierarchyDropdown1List;
	}
	
	@RequestMapping(value="/uploadExcelChangeHierarchyDropdown1", method=RequestMethod.POST)
	public @ResponseBody void fileUploadHierarchyChange(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			Integer hierarchyDropdown1Id=(Util.isNotNull(request.getParameter("hierarchyDropdown1Id"))?Integer.parseInt(request.getParameter("hierarchyDropdown1Id")):null);
			String hierarchyDropdown1Name=request.getParameter("hierarchyDropdown1Name");
			List<CommonStringList> list=getHierarchyDropdown2List(directorateId,hierarchyDropdown1Id,hierarchyDropdown1Name);
			PrintWriter out = response.getWriter();
			String json=new Gson().toJson(list);  
			out.println(json);	
			log.debug(this.getClass().getName() + "- Exit ");
			}catch (Exception e) {
				log.error(this.getClass().getName() + "- fileUploadHierarchyChange "+e.getMessage());
			} finally{}
	}
	
	private List<CommonStringList> getHierarchyDropdown2List(Integer directorateId,Integer hierarchyDropdown1Id,String hierarchyDropdown1Name){
		
		 List<CommonStringList> hierarchyDropdown2List=new ArrayList<CommonStringList>();
		 try{
				log.debug(this.getClass().getName() + "- Entering ");
			 String userName = UserInfo.getLoggedInUserName();
			if(directorateId==8){
				 hierarchyDropdown2List= dmeDataEntryManager.getInstitutionList(hierarchyDropdown1Id,userName);
			}else if(directorateId==6){
				hierarchyDropdown2List= rntcpDataEntryManager.getInstitutionList(hierarchyDropdown1Id,userName);
			}else if(directorateId==16){
				 hierarchyDropdown2List= dfsDataEntryManager.getDistrictList(hierarchyDropdown1Id,userName);
			}else if(directorateId==7){
	       	int corporationIndex=hierarchyDropdown1Name.indexOf("Corporation");
	       	if(corporationIndex==-1){
	       		hierarchyDropdown2List= maDataEntryManager.getCorporationAndMunicipalityList(hierarchyDropdown1Id, userName);
	       	}else{
	       		CommonStringList comString=new CommonStringList();
	       		comString.setId(-99);
	       		comString.setName("All");
	       		hierarchyDropdown2List.add(comString);
	       	}
			}else{
				CommonStringList comString=new CommonStringList();
	   		comString.setId(-99);
	   		comString.setName("All");
	   		hierarchyDropdown2List.add(comString);
			}
			log.debug(this.getClass().getName() + "- Exit ");
			}catch (Exception e) {
				log.error(this.getClass().getName() + "- getHierarchyDropdown2List "+e.getMessage());
			} finally{}
			return hierarchyDropdown2List;
	}
	
	@RequestMapping(value="/uploadExcelFile", method=RequestMethod.POST)
	public  ModelAndView saveUploadedExcel(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		ModelAndView modelView=null;
		modelView=uploadExcelOnLoad();		    
		   
		boolean successFlag=false;
		String root =null;  String tmpFilesPath=null;
		Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
		//process only if its multipart content
        if(ServletFileUpload.isMultipartContent(request)){
        	 String fileName = null; 
        	 String renamedFileName = null;
            try {
    			log.debug(this.getClass().getName() + "- Entering ");
                @SuppressWarnings("unchecked")
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                String frequency= request.getParameter("frequency");
                String hierarchyName1= request.getParameter("hierarchyName1");
                String hierarchyName2= request.getParameter("hierarchyName2");
                String searchDate= null;
                String week=null;
                String month= null;
                String quarter= null;
                Integer year= null;
                String userName = UserInfo.getLoggedInUserName();
                String currentDate=Util.getStrDate(new Date());
              
                for(FileItem item : multiparts){
                    if(item.isFormField()){
                    	String fieldname = item.getFieldName();
                    	
                    	if("directorateId".equalsIgnoreCase(fieldname)){
                    		directorateId = (Util.isNotNull(item.getString())?Integer.parseInt(item.getString()):null);
                    	}
                    	if("timeperiod".equalsIgnoreCase(fieldname)){
                    		frequency=getFrequencyById(item.getString());
                    	}	
                    	if("hierarchyName1".equalsIgnoreCase(fieldname)){
                    		hierarchyName1=item.getString();
                    	}
                    	if("hierarchyName2".equalsIgnoreCase(fieldname)){
                    		hierarchyName2=item.getString();
                    	}
                    	if("searchDate".equalsIgnoreCase(fieldname)){
                    		searchDate=item.getString();
                    	}

                    	if("week".equalsIgnoreCase(fieldname)){
                    		week=item.getString();
                    	}

                    	if("month".equalsIgnoreCase(fieldname)){
                    		month=item.getString();
                    	}

                    	if("quarter".equalsIgnoreCase(fieldname)){
                    		quarter=item.getString();
                    	}
                    	if("year".equalsIgnoreCase(fieldname)){
                    		year=(Util.isNotNull(item.getString())?Integer.parseInt(item.getString()):null);
                    	}
                    }
                    else {
                    	fileName = new File(item.getName()).getName();
                    	 root = ShdrcConstants.ROOTDIRECTORY;
                    	//String tempFilesPath=File.separator+"home"+File.separator+"DataIntegration"+File.separator+"Temp_Files";
                    	tmpFilesPath=root+File.separator+"Temp_Files";
                    	item.write(new File(tmpFilesPath+File.separator+fileName));
                    	FileInputStream inputStream = new FileInputStream(new File(tmpFilesPath+File.separator+fileName));
                        Workbook workbook=null;
                        if (fileName.endsWith("xlsx")) {
                        	try{
                        		workbook = new XSSFWorkbook(inputStream);
                        	}catch(IllegalArgumentException e){
                        		throw new IllegalArgumentException("The File Content is MALFORMED");
                        	} 
                       } else if (fileName.endsWith("xls")) {
                	    	try{
                	    		workbook = new HSSFWorkbook(inputStream);
                	    	}catch(IllegalArgumentException e){
                        		throw new IllegalArgumentException("The File Content is MALFORMED");
                        	}
                	    } else {
                	    	inputStream.close();
                	        throw new IllegalArgumentException("The specified file is not Excel file");
                	    }  
                        String fileExtention=getFileExtention(fileName);
                        String fileNameWithoutExtention=getFileNameWithoutExtention(fileName);
                        Integer hierarchyId1=(Util.isNotNull(hierarchyName1))?Integer.parseInt(hierarchyName1):null;
                        Integer hierarchyId2=(Util.isNotNull(hierarchyName2))?Integer.parseInt(hierarchyName2):null;
                        boolean isFileExists=commonManager.isFileExists(directorateId,hierarchyId1,hierarchyId2,fileNameWithoutExtention,
                    			frequency,week,quarter,
                    			searchDate,month,year);
                        if(isFileExists){
                        	throw new SHDRCException("File Already Exists");
                        }
                        validateFile(directorateId,fileNameWithoutExtention,fileExtention);
                       //Moving File                      
                       //root = File.separator+"home"+File.separator+"DataIntegration"+File.separator+"DI_Source_Files";
                        //root = File.separator+"home"+File.separator+"pentaho"+File.separator+"DataIntegration"+File.separator+"DI_Source_Files";
                        File path = null;
                        String folderPath=null;
                        String directorateFolder=getDirectorateFolder(directorateId);
                 
                        	folderPath=root +  File.separator+ShdrcConstants.UPLOADFILEDIRECTORY + File.separator+directorateFolder;
                        	if(!isDirectorateFolderExists(folderPath)){
                        		createDirectorateFolder(folderPath);
                        	}
                        	
                        	String selectedFrequencyValue=getSelectedFrequencyValue(frequency,week,quarter,searchDate,month,year);
                        	renamedFileName= fileNameWithoutExtention+SplCharsConstants.UNDERSCORE+userName+SplCharsConstants.UNDERSCORE
                    				+frequency+SplCharsConstants.UNDERSCORE+selectedFrequencyValue+SplCharsConstants.UNDERSCORE+hierarchyName1+
                    				SplCharsConstants.UNDERSCORE+hierarchyName2+SplCharsConstants.UNDERSCORE+currentDate+SplCharsConstants.DOT+fileExtention;
                        	
                        	/*renamedFileName= fileNameWithoutExtention+SplCharsConstants.UNDERSCORE+userName+SplCharsConstants.UNDERSCORE
                    				+frequency+SplCharsConstants.UNDERSCORE+hierarchyName1+SplCharsConstants.UNDERSCORE+hierarchyName2+SplCharsConstants.UNDERSCORE+currentDate+SplCharsConstants.DOT+fileExtention;*/
                        	path = new File(root +  File.separator+ShdrcConstants.UPLOADFILEDIRECTORY + File.separator +directorateFolder+File.separator+renamedFileName+File.separator);

                        	//path = new File(folderPath + File.separator+ fileName);
                        	if(path.exists()){
                        		throw new FileAlreadyExistsException(fileName);
                        	}
                        	FileOutputStream fileOut = new FileOutputStream(path);
                            workbook.write(fileOut);
                            fileOut.close();                     
                            new File(tmpFilesPath+File.separator+fileName).delete();
                        successFlag=commonManager.addFileUploadAuditLog(directorateId, hierarchyName1,hierarchyName2,renamedFileName, frequency, userName,searchDate,week,month,quarter,year);
                       }
                }
                if(successFlag){           
	               //File uploaded successfully
                	 modelView.addObject("successMessage", "The File has been uploaded successfully");
                }else{
                	String directorateFolder=getDirectorateFolder(directorateId);
                    File path = new File(root + File.separator + ShdrcConstants.UPLOADFILEDIRECTORY + File.separator +directorateFolder+  File.separator +fileName);
                    path.delete();
                    modelView.addObject("successMessage", "File Upload Failed"); 
                    modelView.addObject("currentHours", Util.getCurrentHours());
                    modelView.addObject("currentDate", Util.getStrDate(new Date()));
                }
                log.debug(this.getClass().getName() + "- Exit ");
            }
            
         
            catch(FileAlreadyExistsException e) {
            	 modelView.addObject("errorMessage", "The File Already Exists!");
            	/*onLoad(request,response);
    	        return;*/
            }catch(SHDRCException e) {
            	 modelView.addObject("errorMessage", e.getMessage());
            	List<CommonStringList> freuencyList= Util.freuencyList;
            	 modelView.addObject("freuencyList", freuencyList);
            	 modelView.addObject("currentHours", Util.getCurrentHours());
            	 modelView.addObject("currentDate", Util.getStrDate(new Date()));
            }catch (Exception ex) {
            	 new File(tmpFilesPath+File.separator+fileName).delete(); 
            	 modelView.addObject("successMessage", ex.getMessage());
            	 log.error(this.getClass().getName() + "- saveUploadedExcel "+ex.getMessage());
            } 
          /*  if(Util.isNullOrBlank(submitType))
            	download(request,response,fileName,filePath);
            else{
            	onLoad(request,response);
            }*/
        }else{
        	 modelView.addObject("successMessage",
                                 "Sorry this Servlet only handles file upload request");
        }
        
        return modelView;
	}
	
	private String getFrequencyById(String frquencyId){
		
	        String frequency=null;
	        try{
				log.debug(this.getClass().getName() + "- Entering ");
		        switch (frquencyId) {
		            case "1"	:	frequency = "Daily";
		                     	 	break;
		            case "2"	:	frequency = "Weekly";
		     	 					break;
		            case "3"	:	frequency = "Monthly";
		     	 					break;
		            case "4"	:	frequency = "Quarterly";
									break;
		            case "5"	:	frequency = "Yearly";
					break;				
		        }  
		        log.debug(this.getClass().getName() + "- Exit ");
				}catch (Exception e) {
					log.error(this.getClass().getName() + "- getFrequencyById "+e.getMessage());
				} finally{}
		        return frequency;
	}
	
	private  String getFileExtention(String fileName){
		 
			int dot = fileName.lastIndexOf(SplCharsConstants.DOT);
			return fileName.substring(dot + 1);
	}
	
	private String getFileNameWithoutExtention(String fileName){
		int dot = fileName.lastIndexOf(SplCharsConstants.DOT);
		return fileName.substring(0,dot);
	}
	
	private static void validateFile(Integer directorateId,String fileName,String fileExtension) throws SHDRCException{
		
		if(directorateId==null){
			 throw new SHDRCException("Please select the Directorate");
		  }else if(fileName==null || fileName==""){
			  throw new SHDRCException("Please select the upload file");
		  }else if(!isValidFileName(directorateId,fileName)){
			  throw new SHDRCException("Please upload the valid file");
		  }else if(!isValidFileExtension(fileExtension)){
			  throw new SHDRCException("Please select the 'xls or xlsx or csv' file");
		  }
	}
	
	private static boolean isValidFileName(Integer directorateId,String fileName){
		
		  //String pattern = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])";
		  String pattern = "^[_A-Za-z0-9- \\+]+(\\[_A-Za-z0-9-]+)*";
	      // Create a Pattern object
	      Pattern r = Pattern.compile(pattern);
	      // Now create matcher object.
	      Matcher m = r.matcher(fileName);
	      return m.matches();
	}
	
	private static boolean isValidFileExtension(String fileExtension){
		
		if(ShdrcConstants.XLS.equalsIgnoreCase(fileExtension) 
				|| ShdrcConstants.XLSX.equalsIgnoreCase(fileExtension) 
						|| ShdrcConstants.CSV.equalsIgnoreCase(fileExtension))
			return true;
		return false;
	}
	
	private boolean isDirectorateFolderExists(String filePath){
		File folderDir = new File(filePath);
		return folderDir.exists();
	}
	
	private void createDirectorateFolder(String filePath){
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			File folderDir = new File(filePath);
			folderDir.mkdir();
			log.debug(this.getClass().getName() + "- Exit ");
			}catch (Exception e) {
				log.error(this.getClass().getName() + "- createDirectorateFolder "+e.getMessage());
			} finally{}
	}
	
	@RequestMapping(value="/fileDownload", method=RequestMethod.GET)
	public void downloadFileTemplate(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		ModelAndView modelView=null;
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    /*modelView.setViewName("downloadExcelFile");		*/
		    
		    String fileName= request.getParameter("fileName");
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			//String filePath = ShdrcConstants.ROOTDIRECTORY + File.separator+ShdrcConstants.DOWNLOADEDDIRECTORY+ File.separator+getDirectorateFolder(directorateId)+File.separator+fileName;
			String filePath = File.separator+"home"+File.separator+"pentaho"+File.separator+"DataIntegration"+File.separator+"DI_Source_Files" + File.separator+"Download_Source_Files"+ File.separator+getDirectorateFolder(directorateId)+File.separator+fileName;
			File file = new File(filePath);
	        if(fileName == null || fileName.equals("")){
	            
					throw new ServletException("File Name can't be null or empty");
	        }
	        if(!file.exists()){
	            throw new ServletException("File doesn't exists on server.");
	        }
	
	        ServletContext ctx = request.getServletContext();
	        InputStream fis = new FileInputStream(file);
	        String mimeType = ctx.getMimeType(file.getAbsolutePath());
	        response.setContentType(mimeType != null? mimeType:"application/octet-stream");
	        response.setContentLength((int) file.length());
	        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	         
	        ServletOutputStream os = response.getOutputStream();
	        byte[] bufferData = new byte[1024];
	        int read=0;
	        while((read = fis.read(bufferData))!= -1){
	            os.write(bufferData, 0, read);
	        }
	        os.flush();
	        os.close();
	        fis.close();	
	        
	       /* return modelView;*/
		        log.debug(this.getClass().getName() + "- Exit ");
			}catch (Exception e) {
				log.error(this.getClass().getName() + "- downloadFileTemplate "+e.getMessage());
			} finally{}
	}
	
	private String getSelectedFrequencyValue(String frequency,String week,String quarter,String searchDate,String month,Integer year){
		String freqValue=null;
		  switch (frequency) {
          	case "Daily"		:	freqValue = searchDate;
                   	 				break;
          	case "Weekly"		:	freqValue = week+SplCharsConstants.UNDERSCORE+month+SplCharsConstants.UNDERSCORE+year;
									break;
          	case "Monthly"		:	freqValue = month+SplCharsConstants.UNDERSCORE+year;
          							break;
          	case "Quarterly"	:	freqValue = quarter+SplCharsConstants.UNDERSCORE+year;
          							break;
          	case "Yearly"		:	freqValue = String.valueOf(year);
          							break;						
		  }         	 				
		
		return freqValue;
	}
		
}
