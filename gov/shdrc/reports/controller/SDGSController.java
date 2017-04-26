package gov.shdrc.reports.controller;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.reports.service.ISDGSManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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

@Controller
public class SDGSController {
	@Autowired(required=true)
	ISDGSManager SDGSManager;
	
@RequestMapping(value="/sdgs", method=RequestMethod.GET)
public ModelAndView showOnLoadData(HttpServletRequest request) throws IOException, JSONException,SHDRCException{
	ModelAndView modelAndView=null;
	SimpleDateFormat sdf=null;
	CommonStringList commonStringList=null;
	String maxMonth=null;
	int maxYear=0;
	Map<String, Integer> monthsMap =null;
	Calendar cal=null;
	String currentMonth=null;
	String previousMonth=null;
	String previousMonth1=null;
	List<CommonStringList> monthsList=null;
	try{
		modelAndView=new ModelAndView();
		UserInfo.hasValidReportAccess(ShdrcConstants.Role.ALLDIRECTORATE);
		modelAndView.setViewName("fusion");
		sdf=new SimpleDateFormat("MMMM");
		commonStringList=new CommonStringList();
		commonStringList.setId(2016);
		commonStringList.setName("April");
		maxMonth=commonStringList.getName();
		maxYear=commonStringList.getId();
		monthsMap = Util.monthsMap;
		cal=Calendar.getInstance();
		cal.set(maxYear, monthsMap.get(maxMonth),1);
		currentMonth=sdf.format(cal.getTime());
		cal.set(maxYear, monthsMap.get(maxMonth)-1,1);
		previousMonth=sdf.format(cal.getTime());
		cal.set(maxYear, monthsMap.get(maxMonth)-2,1);
		previousMonth1=sdf.format(cal.getTime());
		currentMonth = currentMonth.substring(0, 3);
		previousMonth = previousMonth.substring(0, 3);
		previousMonth1 = previousMonth1.substring(0, 3);
		
		monthsList= Util.monthsList;
		modelAndView.addObject("monthsList", monthsList);
		modelAndView.addObject("currentMonth", maxMonth);
		modelAndView.addObject("currentYear", maxYear);
		modelAndView.addObject("directorateId", 1);
		String indicators = request.getParameter("indicators");
	    String ind = indicators==null?"maternal mortality rate":indicators;
	    JSONArray array = SDGSManager.getNewColumnData(currentMonth,ind);
		JSONArray chartColumnHeader =SDGSManager.getChartColumnHeader(ind);
		JSONArray heatMapChildList = SDGSManager.heatMapData(currentMonth,previousMonth,previousMonth1,ind);
		JSONArray axisLineGT= SDGSManager.getndAxisLineGTData(ind);
		JSONArray axisLineLT= SDGSManager.getndAxisLineLTData(ind);
		JSONArray nca= SDGSManager.ncaDel(currentMonth,ind);			
		JSONArray del1= SDGSManager.del1(currentMonth,ind);
		JSONArray del2= SDGSManager.del1(previousMonth,ind);
		JSONArray del3= SDGSManager.del1(previousMonth1,ind);
		JSONArray anm= SDGSManager.anmst(currentMonth,ind);
		JSONArray risk= SDGSManager.drisk(currentMonth,ind);
		JSONArray chartRange= SDGSManager.getChartColumnRange(ind);
		modelAndView.addObject("jsonData", array);
		modelAndView.addObject("chartRange", chartRange);
		modelAndView.addObject("chartColumnHeader", chartColumnHeader);
		modelAndView.addObject("heatMapChildList", heatMapChildList);
		modelAndView.addObject("axisLineGT", axisLineGT);
		modelAndView.addObject("axisLineLT", axisLineLT);
		modelAndView.addObject("selectedInd", ind);
		modelAndView.addObject("nca", nca);
		modelAndView.addObject("risk", risk);
		modelAndView.addObject("del1", del1);
		modelAndView.addObject("del2", del2);
		modelAndView.addObject("del3", del3);
		modelAndView.addObject("anm", anm);
	}finally{
		sdf=null;
		commonStringList=null;
		maxMonth=null;
		monthsMap =null;
		cal=null;
		currentMonth=null;
		previousMonth=null;
		previousMonth1=null;
		monthsList=null;
	}
	return modelAndView;
	
}

@RequestMapping(value="/showInstData", method=RequestMethod.POST)
public @ResponseBody void showInstData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
	String districtName =null;
	String month =null;
	String ind =null;
	SimpleDateFormat sdf=null;
    String previousMonth=null;
    String previousMonth1=null;
    Map<String, Integer> monthsMap =null;
    Calendar cal=null;
    int curyear =0;
	PrintWriter out=null;
	JSONObject data =null;
	try {
		out=response.getWriter();
		districtName = request.getParameter("districtName");
        month = request.getParameter("monthName");
        ind = request.getParameter("indName");
        if(month!=null){
	        sdf=new SimpleDateFormat("MMMM");
	        monthsMap = Util.monthsMap;
			cal=Calendar.getInstance();
			curyear = Calendar.getInstance().get(Calendar.YEAR);
			cal.set(curyear, monthsMap.get(month)-1,1);
			previousMonth=sdf.format(cal.getTime());
			cal.set(curyear, monthsMap.get(month)-2,1);
			previousMonth1=sdf.format(cal.getTime());
			month = month.substring(0, 3);
			previousMonth = previousMonth.substring(0, 3);
			previousMonth1 = previousMonth1.substring(0, 3);

        }
		JSONArray instlist = SDGSManager.getInstDataByDist(districtName,month,ind);
		JSONArray chartColumnHeader =SDGSManager.getChartColumnHeader(ind);
		JSONArray indlist = SDGSManager.getIndDataByDist(districtName,month,ind);
		JSONArray dellist = SDGSManager.getDelDataByDist(districtName,previousMonth1,ind);
		JSONArray dellist1 = SDGSManager.getDelDataByDist(districtName,previousMonth,ind);
		JSONArray dellist2 = SDGSManager.getDelDataByDist(districtName,month,ind);
		JSONArray lbdList = SDGSManager.getLdDataByDist(districtName,month,ind);
		JSONArray ncList = SDGSManager.getndDataByDist(districtName,month,ind);
		JSONArray childListHM = SDGSManager.getHMDataByDist(districtName,month,previousMonth,previousMonth1,ind);
		JSONArray axisLineGT1= SDGSManager.getndAxisLineGTData1(districtName,month,ind);
		JSONArray axisLineLT1= SDGSManager.getndAxisLineLTData1(districtName,month,ind);
		data = new JSONObject();
		data.put("chartColumnHeader", chartColumnHeader);
		data.put("chartRange", SDGSManager.getChartColumnRange(ind));
		data.put("instlist", instlist);
		data.put("indlist", indlist);
		data.put("dellist", dellist);
		data.put("dellist1", dellist1);
		data.put("dellist2", dellist2);
		data.put("lbdlist", lbdList);
		data.put("ncList", ncList);
		data.put("childListHM", childListHM);
		data.put("axisLineGT1", axisLineGT1);
		data.put("axisLineLT1", axisLineLT1);
		out.println(data);
	}finally{
		districtName =null;
		month =null;
		ind =null;
		sdf=null;
	    previousMonth=null;
	    previousMonth1=null;
	    monthsMap =null;
	    cal=null;
	}
}

@RequestMapping(value="/onChangeMonth", method=RequestMethod.POST)
public @ResponseBody void onChangeMonth(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
	String districtName =null;
	String month =null;
	String ind =null;
	SimpleDateFormat sdf=null;
    String previousMonth=null;
    String previousMonth1=null;
    Map<String, Integer> monthsMap =null;
    Calendar cal=null;
    int curyear =0;
	PrintWriter out=null;
	JSONObject data =null;
	try {
		out=response.getWriter();
		districtName = request.getParameter("districtName");
        month = request.getParameter("monthName");
        ind = request.getParameter("indName");
        if(month!=null){
	        sdf=new SimpleDateFormat("MMMM");
	        monthsMap = Util.monthsMap;
			cal=Calendar.getInstance();
			curyear = Calendar.getInstance().get(Calendar.YEAR);
			cal.set(curyear, monthsMap.get(month)-1,1);
			previousMonth=sdf.format(cal.getTime());
			cal.set(curyear, monthsMap.get(month)-2,1);
			previousMonth1=sdf.format(cal.getTime());
			month = month.substring(0, 3);
			previousMonth = previousMonth.substring(0, 3);
			previousMonth1 = previousMonth1.substring(0, 3);

        }
		JSONArray array = SDGSManager.getNewColumnData(month,ind);
		JSONArray chartColumnHeader =SDGSManager.getChartColumnHeader(ind);
		JSONArray heatMapChildList = SDGSManager.heatMapData(month,previousMonth,previousMonth1,ind);
		JSONArray axisLineGT= SDGSManager.getndAxisLineGTData(ind);
		JSONArray axisLineLT= SDGSManager.getndAxisLineLTData(ind);
		JSONArray nca= SDGSManager.ncaDel(month,ind);			
		JSONArray del1= SDGSManager.del1(month,ind);
		JSONArray del2= SDGSManager.del1(previousMonth,ind);
		JSONArray del3= SDGSManager.del1(previousMonth1,ind);
		JSONArray anm= SDGSManager.anmst(month,ind);
		JSONArray risk= SDGSManager.drisk(month,ind);
		data = new JSONObject();
		data.put("array", array);
		data.put("chartRange", SDGSManager.getChartColumnRange(ind));
		data.put("chartColumnHeader", chartColumnHeader);
		data.put("heatMapChildList", heatMapChildList);
		data.put("axisLineGT", axisLineGT);
		data.put("axisLineLT", axisLineLT);
		data.put("nca", nca);
		data.put("del1", del1);
		data.put("del2", del2);
		data.put("del3", del3);
		data.put("anm", anm);
		data.put("risk", risk);
		out.println(data);
	}finally{
		districtName =null;
		month =null;
		ind =null;
		sdf=null;
	    previousMonth=null;
	    previousMonth1=null;
	    monthsMap =null;
	    cal=null;
	}
}

/*@RequestMapping(value="/showOnLoadTreeMapData", method=RequestMethod.GET)
public ModelAndView showOnLoadTreeMapData(HttpServletRequest request){
	ModelAndView modelView=null;
	String flag=null;
	String param=null;
	List<CommonStringList> monthsList=null;
	String indicators =null;
	try{
		indicators = request.getParameter("indicators");
		modelView=new ModelAndView();
		modelView.addObject("directorateId", 1);
		modelView.setViewName("treeMap");
		monthsList= Util.monthsList;
		flag="State";
		param="NoDisease";
		JSONArray districtDataCd=SDGSManager.getDistrictDataCd(flag,param);
		JSONArray districtDataNcd=SDGSManager.getDistrictDataNcd(flag,param);
		JSONArray gcd=SDGSManager.getcd();
		JSONArray gncd=SDGSManager.getncd();
		modelView.addObject("selectedInd", indicators);
		modelView.addObject("monthsList", monthsList);
		modelView.addObject("districtDataCd", districtDataCd);
		modelView.addObject("districtDataNcd", districtDataNcd);
		modelView.addObject("gcd", gcd);
		modelView.addObject("gncd", gncd);
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		flag=null;
		param=null;
	}
	return modelView;
}*/

@RequestMapping(value="/showInstDataNcd", method=RequestMethod.POST)
public @ResponseBody void showInstDataNcd(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
	String distName=null;
	String header=null;
	String submitType=null;
	String generalName=null;
	JSONObject data = null;
	PrintWriter out=null;
	try{
		out=response.getWriter();
		distName=request.getParameter("distName");
		header=request.getParameter("header");
		submitType=request.getParameter("submitType");
		generalName=request.getParameter("generalName");
		JSONArray instData=SDGSManager.getInstitutionData(distName,submitType,generalName);
		JSONArray grandChildDataNcd=SDGSManager.getGrandChildDataNcd(distName,submitType,generalName);
		data = new JSONObject();
		data.put("instData", instData);
		data.put("grandChildDataNcd", grandChildDataNcd);
		out.println(data);
	}finally{
		distName=null;
		header=null;
		submitType=null;
		generalName=null;
	}
}



/*@RequestMapping(value="/showGeneralNameData", method=RequestMethod.POST)
public @ResponseBody void showGeneralNameData(HttpServletRequest request,HttpServletResponse response) throws IOException{
	String flag=null;
	String param=null;
	JSONObject data = null;
	PrintWriter out=null;
	try{
		out=response.getWriter();
		flag=request.getParameter("flag");
		param=request.getParameter("param");
		JSONArray distDataCD=SDGSManager.getDistrictDataCd(flag,param);
		JSONArray distDataNCD=SDGSManager.getDistrictDataNcd(flag,param);
		data = new JSONObject();
		data.put("distDataCD", distDataCD);
		data.put("distDataNCD", distDataNCD);
		out.println(data);
	}catch (JSONException e) {
		e.printStackTrace();
	}finally{
		flag=null;
		param=null;
	}
}*/

	@RequestMapping(value="/showOnLoadTreeMapData", method=RequestMethod.GET)
	public ModelAndView showOnLoadCDData(HttpServletRequest request) throws IOException, JSONException,SHDRCException{
		ModelAndView modelView=null;
		List<CommonStringList> monthsList=null;
		String indicators =null;
		String month=null;
		Integer year;
		String disease=null;
		String firstCDName=null;
		try{
			indicators = request.getParameter("indicators");
			disease = request.getParameter("disease");
			modelView=new ModelAndView();
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.ALLDIRECTORATE);
			modelView.addObject("directorateId", 1);
			modelView.setViewName("treeMap");
			monthsList= Util.monthsList;
			String monthName="August";
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			year=2016;
			JSONArray gcd=null;
			if(disease==null){
				gcd=SDGSManager.getcd();
				JSONObject firstCD = gcd.getJSONObject(0);
			    firstCDName = firstCD.getString("label");
			}
			else{
				firstCDName=disease;
			}
			   
			JSONArray districtDataCd=SDGSManager.getDistrictDataCd(firstCDName,month,year);
			String firstCDDistName = null;
			if(districtDataCd!=null){
				JSONObject firstCDDistrict = districtDataCd.getJSONObject(0);
			    firstCDDistName = firstCDDistrict.getString("label");
			}
			JSONArray instDataCd=SDGSManager.getInstDataCd(firstCDDistName,firstCDName,month,year);
			
			modelView.addObject("selectedInd", indicators);
			modelView.addObject("monthsList", monthsList);
			modelView.addObject("gcd", gcd);
			modelView.addObject("firstCDName", firstCDName);
			modelView.addObject("districtDataCd", districtDataCd);
			modelView.addObject("firstCDDistName", firstCDDistName);
			modelView.addObject("instDataCd", instDataCd);
			modelView.addObject("monthName", monthName);
			modelView.addObject("year", year);
		}finally{
		}
		return modelView;
	}
	
	@RequestMapping(value="/showInstDataCd", method=RequestMethod.POST)
	public @ResponseBody void showInstDataCd(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
		String firstCDDistName=null;
		String header=null;
		JSONObject data = null;
		PrintWriter out=null;
		String firstCDName=null;
		String month=null;
		try{
			out=response.getWriter();
			firstCDDistName=request.getParameter("distName");
			firstCDName=request.getParameter("disease");
			String monthName=request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			header=request.getParameter("header");
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			JSONArray instDataCd=SDGSManager.getInstDataCd(firstCDDistName,firstCDName,month,year);
			data = new JSONObject();
			data.put("instDataCd", instDataCd);
			data.put("firstCDDistName", firstCDDistName);
			data.put("firstCDName", firstCDName);
			out.println(data);
		}finally{
			header=null;
			
		}
	}
	
	@RequestMapping(value="/showCorrespondingCDData", method=RequestMethod.POST)
	public @ResponseBody void showCorrespondingCDData(HttpServletRequest request,HttpServletResponse response)  throws IOException, JSONException,SHDRCException{
		String firstCDDistName=null;
		String header=null;
		JSONObject data = null;
		PrintWriter out=null;
		String firstCDName=null;
		String month=null;
		try{
			out=response.getWriter();
			firstCDName=request.getParameter("disease");
			String monthName=request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			header=request.getParameter("header");
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			JSONArray districtDataCd=SDGSManager.getDistrictDataCd(firstCDName,month,year);
			if(districtDataCd!=null){
				JSONObject firstCDDistrict = districtDataCd.getJSONObject(0);
			    firstCDDistName = firstCDDistrict.getString("label");
			}
			JSONArray instDataCd=SDGSManager.getInstDataCd(firstCDDistName,firstCDName,month,year);
			data = new JSONObject();
			data.put("districtDataCd", districtDataCd);
			data.put("instDataCd", instDataCd);
			data.put("firstCDDistName", firstCDDistName);
			data.put("firstCDName", firstCDName);
			out.println(data);
		}finally{
			header=null;
			
		}
	}
	
	@RequestMapping(value="/showOnLoadNCDData", method=RequestMethod.GET)
	public ModelAndView showOnLoadNCDData(HttpServletRequest request) throws IOException, JSONException,SHDRCException{
		ModelAndView modelView=null;
		List<CommonStringList> monthsList=null;
		String indicators =null;
		String month=null;
		Integer year;
		String disease=null;
		String firstCDName=null;
		try{
			indicators = request.getParameter("indicators");
			disease = request.getParameter("disease");
			modelView=new ModelAndView();
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.ALLDIRECTORATE);
			modelView.addObject("directorateId", 1);
			modelView.setViewName("NCD");
			monthsList= Util.monthsList;
			String monthName="August";
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			year=2016;
			JSONArray gcd=null;
			if(disease==null){
				gcd=SDGSManager.getncd();
				JSONObject firstCD = gcd.getJSONObject(0);
			    firstCDName = firstCD.getString("label");
			}
			else{
				firstCDName=disease;
			}
			   
			JSONArray districtDataNCd=SDGSManager.getDistrictDataNCd(firstCDName,month,year);
			String firstCDDistName = null;
			if(districtDataNCd!=null){
				JSONObject firstCDDistrict = districtDataNCd.getJSONObject(0);
			    firstCDDistName = firstCDDistrict.getString("label");
			}
			JSONArray instDataNCd=SDGSManager.getInstDataNCd(firstCDDistName,firstCDName,month,year);
			
			modelView.addObject("selectedInd", indicators);
			modelView.addObject("monthsList", monthsList);
			modelView.addObject("gcd", gcd);
			modelView.addObject("firstCDName", firstCDName);
			modelView.addObject("districtDataCd", districtDataNCd);
			modelView.addObject("firstCDDistName", firstCDDistName);
			modelView.addObject("instDataCd", instDataNCd);
			modelView.addObject("monthName", monthName);
			modelView.addObject("year", year);
		}finally{
		}
		return modelView;
	}
	
	@RequestMapping(value="/showInstDataNCd", method=RequestMethod.POST)
	public @ResponseBody void showInstDataNCd(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
		String firstCDDistName=null;
		String header=null;
		JSONObject data = null;
		PrintWriter out=null;
		String firstCDName=null;
		String month=null;
		try{
			out=response.getWriter();
			firstCDDistName=request.getParameter("distName");
			firstCDName=request.getParameter("disease");
			String monthName=request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			header=request.getParameter("header");
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			JSONArray instDataNCd=SDGSManager.getInstDataNCd(firstCDDistName,firstCDName,month,year);
			data = new JSONObject();
			data.put("instDataCd", instDataNCd);
			data.put("firstCDDistName", firstCDDistName);
			data.put("firstCDName", firstCDName);
			out.println(data);
		}finally{
			header=null;
			
		}
	}
	
	@RequestMapping(value="/showCorrespondingNCDData", method=RequestMethod.POST)
	public @ResponseBody void showCorrespondingNCDData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
		String firstCDDistName=null;
		String header=null;
		JSONObject data = null;
		PrintWriter out=null;
		String firstCDName=null;
		String month=null;
		try{
			out=response.getWriter();
			firstCDName=request.getParameter("disease");
			String monthName=request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			header=request.getParameter("header");
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			JSONArray districtDataNCd=SDGSManager.getDistrictDataNCd(firstCDName,month,year);
			if(districtDataNCd!=null){
				JSONObject firstCDDistrict = districtDataNCd.getJSONObject(0);
			    firstCDDistName = firstCDDistrict.getString("label");
			}
			JSONArray instDataNCd=SDGSManager.getInstDataNCd(firstCDDistName,firstCDName,month,year);
			data = new JSONObject();
			data.put("districtDataCd", districtDataNCd);
			data.put("instDataCd", instDataNCd);
			data.put("firstCDDistName", firstCDDistName);
			data.put("firstCDName", firstCDName);
			out.println(data);
		}finally{
			header=null;
			
		}
	}
}

