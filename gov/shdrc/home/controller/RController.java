package gov.shdrc.home.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.home.service.IHomeManager;
import gov.shdrc.reports.service.IRReportManager;
import gov.shdrc.reports.service.ISDGSManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
/**
 * @author lakshmi
 *
 */
@Controller
public class RController {
	@Autowired
	ICommonManager commonManager;

	@Autowired(required=true)
	IHomeManager homeManager;
	@Autowired(required=true)
	ISDGSManager SDGSManager;
	@Autowired
	IRReportManager irReportManager;
	@RequestMapping(value="/shdrcSlider", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request){			
	   ModelAndView modelView=null;
	   

	   try { 
		   String userName = UserInfo.getLoggedInUserName();
		    modelView=new ModelAndView();
		    modelView.setViewName("shdrcSliderAction");
			
		    
		    List<CommonStringList> districtList= commonManager.getDistricts(userName);
	        modelView.addObject("districts", districtList);
		    
	        List<CommonStringList> monthsList= Util.monthsList;
	        /*System.out.println("monthsList"+monthsList);*/
	        modelView.addObject("monthsList", monthsList);
		    
			} catch (Exception e) {
				e.printStackTrace();
			}finally{			
			} 
	   return modelView;
	}	
	
	
	@RequestMapping(value="/shdrcSliderpost", method=RequestMethod.POST)
	public @ResponseBody void getcorrespondingMonthData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		/*String month=request.getParameter("month");
		String district=request.getParameter("district");*/
		        double motherVisits=Double.parseDouble(request.getParameter("motherVisits"));
	         	double HBLtSevengms=Double.parseDouble(request.getParameter("HBLtSevengms"));
	         	double HBLtSevengmsRefCEMONC=Double.parseDouble(request.getParameter("HBLtSevengmsRefCEMONC"));
	         	double HBLtSevengmsAttendedPHCs=Double.parseDouble(request.getParameter("HBLtSevengmsAttendedPHCs"));
	         	double highRiskCasesDetected=Double.parseDouble(request.getParameter("highRiskCasesDetected"));
	         	
	         	double refCEMONCMedical=Double.parseDouble(request.getParameter("refCEMONCMedical"));
	         	double IFALarge=Double.parseDouble(request.getParameter("IFALarge"));
	         	double ifaLargeTablets=Double.parseDouble(request.getParameter("ifaLargeTablets"));
	         	double normalDeliveries=Double.parseDouble(request.getParameter("normalDeliveries"));
	         	double assistDeliveries=Double.parseDouble(request.getParameter("assistDeliveries"));
	         	
	         	double caesarianDeliveries=Double.parseDouble(request.getParameter("caesarianDeliveries"));
	         	double deliveriesOccuredHsc=Double.parseDouble(request.getParameter("deliveriesOccuredHsc"));
				double deliveriesOccuredPHC=Double.parseDouble(request.getParameter("deliveriesOccuredPHC"));
				double deliveriesOccurredGH=Double.parseDouble(request.getParameter("deliveriesOccurredGH"));
				double deliveriesOccurredNH=Double.parseDouble(request.getParameter("deliveriesOccurredNH"));
				double deliveriesOccurredHome=Double.parseDouble(request.getParameter("deliveriesOccurredHome"));
				double percapita=Double.parseDouble(request.getParameter("percapita"));
				double femLit=Double.parseDouble(request.getParameter("femLit"));
				double agriLabour=Double.parseDouble(request.getParameter("agriLabour"));
				
				int motherVisitsInt=(int)motherVisits;
				int HBLtSevengmsInt=(int)HBLtSevengms;
				int HBLtSevengmsRefCEMONCInt=(int)HBLtSevengmsRefCEMONC;
				int HBLtSevengmsAttendedPHCsInt=(int)HBLtSevengmsAttendedPHCs;
				int highRiskCasesDetectedInt=(int)highRiskCasesDetected;
				
				int refCEMONCMedicalInt=(int)refCEMONCMedical;
				int IFALargeInt=(int)IFALarge;
				
				int ifaLargeTabletsInt=(int)ifaLargeTablets;
				int normalDeliveriesInt=(int)normalDeliveries;
				int assistDeliveriesInt=(int)assistDeliveries;
				
				int caesarianDeliveriesInt=(int)caesarianDeliveries;
				int deliveriesOccuredHscInt=(int)deliveriesOccuredHsc;
				int deliveriesOccuredPHCInt=(int)deliveriesOccuredPHC;
				int deliveriesOccurredGHInt=(int)deliveriesOccurredGH;
				int deliveriesOccurredNHInt=(int)deliveriesOccurredNH;
				
				int deliveriesOccurredHomeInt=(int)deliveriesOccurredHome;
				int percapitaInt=(int)percapita;
				int femLitInt=(int)femLit;
				int agriLabourInt=(int)agriLabour;
		
				
			Double total=irReportManager.getMMRPredition(motherVisitsInt, HBLtSevengmsInt, HBLtSevengmsRefCEMONCInt, HBLtSevengmsAttendedPHCsInt, highRiskCasesDetectedInt, refCEMONCMedicalInt,IFALargeInt,ifaLargeTabletsInt, normalDeliveriesInt,assistDeliveriesInt, caesarianDeliveriesInt,deliveriesOccuredHscInt,deliveriesOccuredPHCInt,deliveriesOccurredGHInt,deliveriesOccurredNHInt,deliveriesOccurredHomeInt,percapitaInt,femLitInt,agriLabourInt);
			out.println(total.intValue());
			
				/*			double total=lowBirthWeight+highRiskDetected+highRiskReferred+lscsDelivery+weighedLtTwoKg+weighedbwTwondThree+weighedThreeKg+liveBirth+perCapita+femLit+agriLabour;
				*/			
		//String district=request.getParameter("district");
/*		double motherVisits=Double.parseDouble(request.getParameter("motherVisits"));
		double HBLtSevengms=Double.parseDouble(request.getParameter("HBLtSevengms"));
		double HBLtSevengmsRefCEMONC=Double.parseDouble(request.getParameter("HBLtSevengmsRefCEMONC"));
		double HBLtSevengmsAttendedPHCs=Double.parseDouble(request.getParameter("HBLtSevengmsAttendedPHCs"));
		double highRiskCasesDetected=Double.parseDouble(request.getParameter("highRiskCasesDetected"));
		double refCEMONCMedical=Double.parseDouble(request.getParameter("refCEMONCMedical"));
		double IFALarge=Double.parseDouble(request.getParameter("IFALarge"));
		double ifaLargeTablets=Double.parseDouble(request.getParameter("ifaLargeTablets"));
		double normalDeliveries=Double.parseDouble(request.getParameter("normalDeliveries"));
		double assistDeliveries=Double.parseDouble(request.getParameter("assistDeliveries"));
		double caesarianDeliveries=Double.parseDouble(request.getParameter("caesarianDeliveries"));
		double deliveriesOccuredHsc=Double.parseDouble(request.getParameter("deliveriesOccuredHsc"));
		double deliveriesOccuredPHC=Double.parseDouble(request.getParameter("deliveriesOccuredPHC"));
		double deliveriesOccurredGH=Double.parseDouble(request.getParameter("deliveriesOccurredGH"));
		double deliveriesOccurredNH=Double.parseDouble(request.getParameter("deliveriesOccurredNH"));
		double deliveriesOccurredHome=Double.parseDouble(request.getParameter("deliveriesOccurredHome"));
		double percapita=Double.parseDouble(request.getParameter("percapita"));
		double femLit=Double.parseDouble(request.getParameter("femLit"));
		double agriLabour=Double.parseDouble(request.getParameter("agriLabour"));*/
		
		/*Integer displayfirst=Integer.valueOf(request.getParameter("displayfirst"));
		Integer displaythird=Integer.valueOf(request.getParameter("displaythird"));*/
		/*Integer displayfour=Integer.valueOf(request.getParameter("displayfour"));
		
		
		Integer highRiskCasesDetected=(Util.isNotNull(request.getParameter("highRiskCasesDetected"))?Integer.parseInt(request.getParameter("highRiskCasesDetected")):null);
		Integer refCEMONCMedical=(Util.isNotNull(request.getParameter("refCEMONCMedical"))?Integer.parseInt(request.getParameter("refCEMONCMedical")):null);
		Integer displayseven=(Util.isNotNull(request.getParameter("displayseven"))?Integer.parseInt(request.getParameter("displayseven")):null);
		Integer displayeight=(Util.isNotNull(request.getParameter("displayeight"))?Integer.parseInt(request.getParameter("displayeight")):null);
		Integer displaynine=(Util.isNotNull(request.getParameter("displaynine"))?Integer.parseInt(request.getParameter("displaynine")):null);
		Integer displayten=(Util.isNotNull(request.getParameter("displayten"))?Integer.parseInt(request.getParameter("displayten")):null);
		Integer displayeleven=(Util.isNotNull(request.getParameter("displayeleven"))?Integer.parseInt(request.getParameter("displayeleven")):null);
		Integer displaytwelve=(Util.isNotNull(request.getParameter("displaytwelve"))?Integer.parseInt(request.getParameter("displaytwelve")):null);
		Integer displaythirteen=(Util.isNotNull(request.getParameter("displaythirteen"))?Integer.parseInt(request.getParameter("displaythirteen")):null);
		Integer displayfourteen=(Util.isNotNull(request.getParameter("displayfourteen"))?Integer.parseInt(request.getParameter("displayfourteen")):null);
		Integer displayfifteen=(Util.isNotNull(request.getParameter("displayfifteen"))?Integer.parseInt(request.getParameter("displayfifteen")):null);
		Integer refCEMONCMedicalteen=(Util.isNotNull(request.getParameter("refCEMONCMedicalteen"))?Integer.parseInt(request.getParameter("refCEMONCMedicalteen")):null);
		Integer displayseventeen=(Util.isNotNull(request.getParameter("displayseventeen"))?Integer.parseInt(request.getParameter("displayseventeen")):null);
		Integer displayeighteen=(Util.isNotNull(request.getParameter("displayeighteen"))?Integer.parseInt(request.getParameter("displayeighteen")):null);*/
		//Integer displaynineteen=(Util.isNotNull(request.getParameter("displaynineteen"))?Integer.parseInt(request.getParameter("displaynineteen")):null);
		
		/*JSONObject obj = new JSONObject(); 
		obj.put("dmsDirectorateRecords", dmsDirectorateRecords); 
		out.println(obj);	 
		*/
		/*double total=motherVisits+HBLtSevengms+HBLtSevengmsRefCEMONC+HBLtSevengmsAttendedPHCs+highRiskCasesDetected+refCEMONCMedical+IFALarge+ifaLargeTablets+normalDeliveries+assistDeliveries+caesarianDeliveries+deliveriesOccuredHsc+deliveriesOccuredPHC+deliveriesOccurredGH+deliveriesOccurredNH+deliveriesOccurredHome+percapita+femLit+agriLabour;
		out.println(total);*/
		
	}
	
	

		
		@RequestMapping(value="/shdrcPredictionSlider", method=RequestMethod.GET)
		public ModelAndView onLoads(HttpServletRequest request){			
		   ModelAndView modelView=null;
		   

		   try { 
			   String userName = UserInfo.getLoggedInUserName();
			    modelView=new ModelAndView();
			    modelView.setViewName("shdrcPredictionSliderAction");
				
			    
			    List<CommonStringList> districtList= commonManager.getDistricts(userName);
		        modelView.addObject("districts", districtList);
			    
		        List<CommonStringList> monthsList= Util.monthsList;
		        /*System.out.println("monthsList"+monthsList);*/
		        modelView.addObject("monthsList", monthsList);
			    
				} catch (Exception e) {
					e.printStackTrace();
				}finally{			
				} 
		   return modelView;
		}	
		
		
		
		
		@RequestMapping(value="/shdrcPredictionSliderpost", method=RequestMethod.POST)
		public @ResponseBody void getcorrespondingPredictionDeathData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
			PrintWriter out = response.getWriter();
			//String month=request.getParameter("month");
			//String district=request.getParameter("district");
			 double lowBirthWeight=Double.parseDouble(request.getParameter("lowBirthWeight"));
			 double highRiskDetected=Double.parseDouble(request.getParameter("highRiskDetected"));
			 double highRiskReferred=Double.parseDouble(request.getParameter("highRiskReferred"));
			 double lscsDelivery=Double.parseDouble(request.getParameter("lscsDelivery"));
			 double weighedLtTwoKg=Double.parseDouble(request.getParameter("weighedLtTwoKg"));
			 double weighedbwTwondThree=Double.parseDouble(request.getParameter("weighedbwTwondThree"));
			 double weighedThreeKg=Double.parseDouble(request.getParameter("weighedThreeKg"));
			 double liveBirth=Double.parseDouble(request.getParameter("liveBirth"));
			 double perCapita=Double.parseDouble(request.getParameter("perCapita"));
			 double femLit=Double.parseDouble(request.getParameter("femLit"));
			 double agriLabour=Double.parseDouble(request.getParameter("agriLabour"));
			 
			 int lowBirthWeightInt=(int)lowBirthWeight;
			 int highRiskDetectedInt=(int)highRiskDetected;
			 int highRiskReferredInt=(int)highRiskReferred;
			 int lscsDeliveryInt=(int)lscsDelivery;
			 int weighedLtTwoKgInt=(int)weighedLtTwoKg;
			 int weighedbwTwondThreeInt=(int)weighedbwTwondThree; 
			 int weighedThreeKgInt=(int)weighedThreeKg;
			 int liveBirthInt=(int)liveBirth;
			 int perCapitaInt=(int)perCapita;
			 int femLitInt=(int)femLit;
			 int agriLabourInt=(int)agriLabour;
			
				Double total=irReportManager.getIMRPredition(lowBirthWeightInt, highRiskDetectedInt, highRiskReferredInt, lscsDeliveryInt, weighedLtTwoKgInt, weighedbwTwondThreeInt,weighedThreeKgInt, liveBirthInt,perCapitaInt, femLitInt,agriLabourInt);

				out.println(total);
			/*int lowBirthWeight=Integer.parseInt(request.getParameter("lowBirthWeight"));
			int highRiskDetected=Integer.parseInt(request.getParameter("highRiskDetected"));
			int highRiskReferred=Integer.parseInt(request.getParameter("highRiskReferred"));
			int lscsDelivery=Integer.parseInt(request.getParameter("lscsDelivery"));
			int weighedLtTwoKg=Integer.parseInt(request.getParameter("weighedLtTwoKg"));
			int weighedbwTwondThree=Integer.parseInt(request.getParameter("weighedbwTwondThree"));
			int weighedThreeKg=Integer.parseInt(request.getParameter("weighedThreeKg"));
			int liveBirth=Integer.parseInt(request.getParameter("liveBirth"));
			int perCapita=Integer.parseInt(request.getParameter("perCapita"));
			int femLit=Integer.parseInt(request.getParameter("femLit"));
			int agriLabour=Integer.parseInt(request.getParameter("agriLabour"));*/
		
		
/*			double total=lowBirthWeight+highRiskDetected+highRiskReferred+lscsDelivery+weighedLtTwoKg+weighedbwTwondThree+weighedThreeKg+liveBirth+perCapita+femLit+agriLabour;
*/			
			
		}
		
	


}
