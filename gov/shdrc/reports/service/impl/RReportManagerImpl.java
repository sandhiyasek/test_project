package gov.shdrc.reports.service.impl;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import org.springframework.stereotype.Service;

import gov.shdrc.reports.service.IRReportManager;
import gov.shdrc.util.REngineHelper;
@Service
public class RReportManagerImpl implements IRReportManager{
//public class RReportManagerImpl{
	public Double getIMRPredition(int lowBirthWeightInt,int highRiskDetectedInt,int highRiskReferredInt,int lscsDeliveryInt,int weighedLtTwoKgInt,int weighedbwTwondThreeInt,int weighedThreeKgInt,int liveBirthInt,int perCapitaInt,int femLitInt,int agriLabourInt){
		Double prediction=null;
		Rengine rengine= REngineHelper.getIMRInstance();
		rengine.eval(String.format("df1 <- data.frame(Number_of_Low_Birth_Weight_babies = "+lowBirthWeightInt+", Number_of_High_Risk_New_Borns_Detected = "+highRiskDetectedInt+",Number_of_High_Risk_New_Borns_Referred = "+highRiskReferredInt+",Number_of_New_Borns_Breast_Fed_within_1hour_of_LSCS_Delivery = "+lscsDeliveryInt+",No_of_Births_weighed_lt2.5kg = "+weighedLtTwoKgInt+",No_of_Births_weighed_bt_2.5and3kg = "+weighedbwTwondThreeInt+", No_of_Births_weighed_gt3kg = "+weighedThreeKgInt+", Live_Birth = "+liveBirthInt+","
				+ " PerCapita = "+perCapitaInt+", Fem_Lit = "+femLitInt+", Agri_Labour = "+agriLabourInt+")"));
		REXP result =rengine.eval("predict(fit1, df1)");
		/*System.out.println("result********"+result.asDouble());*/
		
		return result.asDouble();
	}
	public Double getMMRPredition(int motherVisitsInt,int HBLtSevengms,int HBLtSevengmsRefCEMONCInt,int HBLtSevengmsAttendedPHCsInt,int highRiskCasesDetectedInt,int refCEMONCMedicalInt,int IFALargeInt,int ifaLargeTabletsInt,int normalDeliveriesInt,int assistDeliveriesInt,int caesarianDeliveriesInt,int deliveriesOccuredHscInt,int deliveriesOccuredPHCInt,int deliveriesOccurredGHInt,int deliveriesOccurredNHInt,int deliveriesOccurredHomeInt,int percapitaInt,int femLitInt,int agriLabourInt){

		Double prediction=null;
		Rengine rengine= REngineHelper.getMMRInstance();
		rengine.eval(String.format("df <- data.frame(Number_of_AN_Mothers_Given_Visits = "+motherVisitsInt+","
				+ " Number_of_AN_Mothers_with_HB_lt_7grams = "+HBLtSevengms+",Number_of_AN_Mothers_with_HB_lt_7grams_Referred_to_CEmONC = "+HBLtSevengmsRefCEMONCInt+",Number_of_AN_Mothers_with_HB_lt_7grams_Attended_PHCs ="+HBLtSevengmsAttendedPHCsInt+","
				+ "  Number_of_High_Risk_AN_Cases_Detected = "+highRiskCasesDetectedInt+",Number_of_High_Risk_AN_Cases_Referred_to_CEmONC_Higher_Medical_I = "+refCEMONCMedicalInt+","
				+ "  Number_of_AN_Mothers_Given_IFA_Large = "+IFALargeInt+",No_of_PostNatal_Mothers_given_IFA_large_tablets = "+ifaLargeTabletsInt+",Total_Number_of_Normal_Deliveries = "+normalDeliveriesInt+",Total_Number_of_Assisted_Deliveries = "+assistDeliveriesInt+",Total_Number_of_Caesarians_Deliveries ="+caesarianDeliveriesInt+","
                + "Number_of_Deliveries_Occurred_HSC = "+deliveriesOccuredHscInt+",Number_of_Deliveries_Occurred_PHC = "+deliveriesOccuredPHCInt+",Number_of_Deliveries_Occurred_GH = "+deliveriesOccurredGHInt+","
                + "Number_of_Deliveries_Occurred_Private_NH = "+deliveriesOccurredNHInt+",Number_of_Deliveries_Occurred_Home = "+deliveriesOccurredHomeInt+",PerCapita = "+percapitaInt+",Fem_Lit = "+femLitInt+",Agri_Labour = "+agriLabourInt+")"));
		REXP result =rengine.eval("(predict(fit,df)");
		System.out.println("result********"+result.asDouble());
		return result.asDouble();
	}
	

/*	public static void main(String[] arg){
		Double prediction=null;
		Rengine rengine= new Rengine(new String[] { " --save " }, true, null);
		rengine.eval("source(\"C:/SHDRC/shiny_imr/negbin_imr.R\")");
		rengine.eval(String.format("df1 <- data.frame(Number_of_Low_Birth_Weight_babies = 20, Number_of_High_Risk_New_Borns_Detected = 20,"
				+ " Number_of_High_Risk_New_Borns_Referred = 20, Number_of_New_Borns_Breast_Fed_within_1hour_of_LSCS_Delivery = 30,"
				+ "No_of_Births_weighed_lt2.5kg = 10,No_of_Births_weighed_bt_2.5and3kg = 1, No_of_Births_weighed_gt3kg = 1, Live_Birth = 10,"
				+ " PerCapita = 10, Fem_Lit = 10, Agri_Labour = 10)"));
		REXP result =rengine.eval("predict(fit1, df1)");
		System.out.println("result********"+result.asDouble());
	}*/
	//MMR
	/*public static void main(String[] arg){
		Double prediction=null;
		Rengine rengine= new Rengine(new String[] { " --save " }, true, null);
		rengine.eval("source(\"C:/SHDRC/shiny_mmr/mmr_load.R\")");
		rengine.eval(String.format("df <- data.frame(District = 'Chennai',Month = 2,Number_of_AN_Mothers_Given_Visits = 10,"
				+ " Number_of_AN_Mothers_with_HB_lt_7grams = 10,Number_of_AN_Mothers_with_HB_lt_7grams_Referred_to_CEmONC = 10,Number_of_AN_Mothers_with_HB_lt_7grams_Attended_PHCs =10,"
				+ "  Number_of_High_Risk_AN_Cases_Detected = 10,Number_of_High_Risk_AN_Cases_Referred_to_CEmONC_Higher_Medical_I = 10,"
                + "  Number_of_AN_Mothers_Given_IFA_Large = 10,No_of_PostNatal_Mothers_given_IFA_large_tablets = 10,"
                + " Total_Number_of_Normal_Deliveries = 10,Total_Number_of_Assisted_Deliveries = 10,Total_Number_of_Caesarians_Deliveries =10,"
                + "Number_of_Deliveries_Occurred_HSC = 10,Number_of_Deliveries_Occurred_PHC = 10,Number_of_Deliveries_Occurred_GH = 10,"
                + "Number_of_Deliveries_Occurred_Private_NH = 10,Number_of_Deliveries_Occurred_Home = 10,PerCapita = 10,Fem_Lit = 10,Agri_Labour = 10)"));
		//rengine.eval("df$Month = as.factor(df$Month)");
		REXP result =rengine.eval("(predict(fit,df)");
		System.out.println("result********"+result.asDouble());
	}*/
	
}
