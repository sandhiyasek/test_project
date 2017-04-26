package gov.shdrc.util;

import java.io.File;

public class ShdrcConstants {
	public static final String UPLOADFILEDIRECTORY="Fact_Source_Files";
	public static final String TEMPFILEDIRECTORY="Staging_Source_Files";
	public static final String DOWNLOADEDDIRECTORY="Download_Source_Files";
	//For Windows
	//public static final String ROOTDIRECTORY="C:\\FileUpload";
	//For Linux
	public static final String ROOTDIRECTORY=File.separator+"home"+File.separator+"pentaho"+File.separator+"DataIntegration"+File.separator+"DI_Source_Files";
	public static final String XLS="xls";
	public static final String XLSX="xlsx";
	public static final String CSV="csv";
	public static final int SELECTALLID=-99;
	public static final String SELECTALLVALUE="All";
	public static final String STATELEVEL="State Level";
	public static final String STATE="State";
	public static final String DISTRICT="District";
	public static final String INSTITUTION="Institution";
	public static final int NAID=-99;
	public static final String NAVALUE="NA";
	public static final String ALLDIRECTORATE="AllDirectorate";
	public static final String INSTITUTIONTYPE="Institution Type";
	public static final String BLOCK ="Block";
	public static final String LAB ="Lab";
	public static final String ZONE="Zone";
	public static final String DISTRICTWORKSHOP="District Workshop";
	public static final String WAREHOUSE="Warehouse";
	public static final String PROGRAM="Program";
	public static final String REGIONCORPORATION="Region/Corporation";
	public static final String HOSPITAL="Hospital";
	public static final String MOBILEWORKSHOP="Mobile Workshop";
	public static final String RANGE="Range";
	public static final String REGION="Region";
	public static final String REGIONALWORKSHOP="Regional Workshop";
	public static final String MUNICIPALITY="Municipality";
	
	public static final String DATAENTRYLEVELSTATE="S"; //S-State level Indicator
	public static final String DATAENTRYLEVELDISTRICT="D"; //D-District level Indicator
	public static final String DATAENTRYLEVELINSTITUTIONTYPE="T"; //T-Institution Type level Indicator
	public static final String DATAENTRYLEVELINSTITUTION="I"; //I-Institution level Indicator
	public static final String DATAENTRYLEVELBLOCK="B"; //B-Block level Indicator
	public static final String DATAENTRYLEVELLAB="L"; //L-Lab level Indicator
	public static final String DATAENTRYLEVELZONEHEADQUARTERS="ZQ"; //HN-Zone Headquarters level Indicator
	public static final String DATAENTRYLEVELRANGEHEADQUARTERS="H"; //H-Range Headquarters level Indicator
	public static final String DATAENTRYLEVELZONEMOBILESQUAD="ZS"; //MN-Zone Mobilesquad level Indicator
	public static final String DATAENTRYLEVELRANGEMOBILESQUAD="M"; //M-Range Mobilesquad level Indicator
	public static final String DATAENTRYLEVELZONE="ZO"; //ON-Zone level Indicator
	public static final String DATAENTRYLEVELRANGE="Y"; //O-Range level Indicator
	public static final String DATAENTRYLEVELDISTRICTWORKSHOP="DW"; //DW- District Workshop level Indicator
	public static final String DATAENTRYLEVELREGIONALWORKSHOP="RW"; //RW-Regional Workshop level Indicator
	public static final String DATAENTRYLEVELMOBILEWORKSHOP="MW"; //MW-Mobile Workshop level Indicator
	public static final String DATAENTRYLEVELWAREHOUSE="W"; //W-Warehouse level Indicator
	public static final String DATAENTRYLEVELPROGRAM="P"; //P-Program level Indicator
	public static final String DATAENTRYLEVELREGIONCORPORATION="RC"; //RC-Region/Corporation level Indicator
	public static final String DATAENTRYLEVELHOSPITAL="H"; //H-Hospital level Indicator
	public static final String DATAENTRYLEVELREGION="R"; //R-Region level Indicator
	public static final String DATAENTRYLEVELMUNICIPALITY="MU"; //MU-Municipality level Indicator
	
	
	
	
	public static final String DAILY="Daily";
	public static final String WEEKLY="Weekly";
	public static final String MONTHLY="Monthly";
	public static final String QUARTERLY="Quarterly";
	public static final String YEARLY="Yearly";
	
	public static final String DAILYSHORTNAME="D";
	public static final String WEEKLYSHORTNAME="W";
	public static final String MONTHLYSHORTNAME="M";
	public static final String QUARTERLYSHORTNAME="Q";
	public static final String YEARLYSHORTNAME="Y";	
	
	public static final String NORMALFACT="N";
	public static final String DASHBOARDFACT="D";
	public static final String CALCULATION="C";
	
	public static final String DATAENTRY="Y";
	public static final String NOTDATAENTRY="N";
	
	public class DirectorateId{
		public static final int DMS=1;
		public static final int DPH=2;
		public static final int TNMSC=3;
		public static final int TANSACS=4;
		public static final int NRHM=5;
		public static final int RNTCP=6;
		public static final int MA=7;
		public static final int DME=8;
		public static final int DRMGR=9;
		public static final int DFW=10;
		public static final int SHTO=11;
		public static final int COC=12;
		public static final int SBCS=13;
		public static final int DCA=14;
		public static final int MRB=15;
		public static final int DFS=16;
		public static final int CMCHIS=17;
		public static final int DIM=18;
		public static final int NLEP=19;
		public static final int SBHI=20;
		public static final int ESI=21;
		public static final int PVTSEC=24;
		public static final int AMB=25;
	}
	public class Role{
		public static final String ALLDIRECTORATE="AllDirectorate";
		public static final String DMS="DMS";
		public static final String DPH="DPH";
		public static final String TNMSC="TNMSC";
		public static final String TANSACS="TANSACS";
		public static final String NRHM="NRHM";
		public static final String RNTCP="RNTCP";
		public static final String MA="MA";
		public static final String DME="DME";
		public static final String DRMGR="DRMGR";
		public static final String DFW="DFW";
		public static final String SHTO="SHTO";
		public static final String COC="COC";
		public static final String SBCS="SBCS";
		public static final String DCA="DCA";
		public static final String MRB="MRB";
		public static final String DFS="DFS";
		public static final String CMCHIS="CMCHIS";
		public static final String DIM="DIM";
		public static final String NLEP="NLEP";
		public static final String SBHI="SBHI";
		public static final String ESI="ESI";
		public static final String NCD="NCD";
		public static final String PVTSEC="PVTSEC";
		public static final String AMB="AMB";
	}
	
	public class UserTier{
		public static final String TIER1="T1";
		public static final String TIER2="T2";
		public static final String TIER3="T3";
		public static final String TIER4="T4";
	}
	
	public class FolderName{
		public static final String DMS="DMS_Fact";
		public static final String DPH="DPH_Fact";
		public static final String TNMSC="TNMSC_Fact";
		public static final String TANSACS="TANSACS_Fact";
		public static final String NRHM="NRHM_Fact";
		public static final String RNTCP="RNTCP_Fact";
		public static final String MA="MA_Fact";
		public static final String DME="DME_Fact";
		public static final String DRMGR="DRMGR_Fact";
		public static final String DFW="DFW_Fact";
		public static final String SHTO="SHTO_Fact";
		public static final String COC="COC_Fact";
		public static final String SBCS="SBCS_Fact";
		public static final String DCA="DCA_Fact";
		public static final String MRB="MRB_Fact";
		public static final String DFS="DFS_Fact";
		public static final String CMCHIS="CMCHIS_Fact";
		public static final String DIM="DIM_Fact";
		public static final String NLEP="NLEP_Fact";
		public static final String SBHI="SBHI_Fact";
		public static final String ESI="ESI_Fact";
		public static final String PVTSEC="PVTSEC_Fact";
		
	}
	
	public class ExcelSheetName{
		public static final String PERF="PERF";
		public static final String CF="CF";
		public static final String SC="SC";
		public static final String RT="RT";
		public static final String PMRCY="PMR-Current Year";
		public static final String PMRLY="PMR-Last Year";
		
	}
	
	//User Info
	public static final String ERROR_INVALID_USER="Invalid User";
	
	//report pagination
	public static final int FIRSTPAGE=1;
	public static final int RECORDSPERPAGE=18;
}
