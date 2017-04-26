package gov.shdrc.reports.service;

import org.springframework.stereotype.Service;

@Service
public interface IRReportManager {
	public Double getIMRPredition(int lowBirthWeightInt,int highRiskDetectedInt,int highRiskReferredInt,int lscsDeliveryInt,int weighedLtTwoKgInt,int weighedbwTwondThreeInt,int weighedThreeKgInt,int liveBirth,int perCapitaInt,int femLitInt,int agriLabourInt);
	public Double getMMRPredition(int motherVisitsInt,int HBLtSevengmsInt,int HBLtSevengmsRefCEMONIntC,int HBLtSevengmsAttendedPHCsInt,int highRiskCasesDetectedInt,int refCEMONCMedicalInt,int IFALargeInt,int ifaLargeTabletsInt,int normalDeliveriesInt,int assistDeliveriesInt,int caesarianDeliveriesInt,int deliveriesOccuredHscInt,int deliveriesOccuredPHCInt,int deliveriesOccurredGHInt,int deliveriesOccurredNHInt,int deliveriesOccurredHomeInt,int percapitaInt,int femLitInt,int agriLabourInt);

}
