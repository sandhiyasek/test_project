package gov.shdrc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ShdrcQueryList {
	public static String DISTRICT_NAME = null;
	public static String DISTRICT_NAME_COUNT = null;
	public static String ACCESS_RESTRICTED_DISTRICT_NAME_COUNT = null;
	public static String SELECT_DIRECTORATE_MASTER = null;
	public static String SELECT_DIRECTORATE_SERVLET = null;
	public static String SELECT_DEMOGRAPHIC_MASTER = null;
	public static String SELECT_MOBILE_NO = null;
	public static String GET_SMS_INFO = null;
	public static String GET_CRON_TRIGER_SMS_INFO=null;
	public static String UPDATE_SMS_ALERT = null;
	//public static String GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	//public static String INDICATOR_LIST = null;
	//public static String CLASSIFICATION_LIST = null;
	public static String GET_USER_TIER = null;
	public static String GENDER_AGEGROUP = null;
	public static String DEMOGRAPHIC_STATUS = null;
	public static String IS_DEMOGRAPHIC = null;
	public static String SELECT_USER_PWD = null;
	public static String UPDATE_USER_PWD = null;
	public static String SELECT_USER_DETAILS = null;
	public static String UPDATE_USER_DETAILS = null;
	public static String SELECT_AUTHORIZE_LIST = null;
	public static String SELECT_INSTITUTION_LIST = null;
	public static String UPDATE_USER_ACCESS = null;
	public static String INSERT_MESSAGE_ALERT = null;
	public static String GET_USER_ACCESS_LEVEL=null;
	public static String INSERT_USER_AUDIT_LOG=null;
	public static String DIRECTORATE_LIST_BY_ID=null;
	
	
	// File Upload
	public static String ADD_FILE_UPLOAD_AUDIT = null;
	public static String GET_DIR_FILE_LIST = null;
	public static String FILE_LIST_BASED_ON_FREQUENCY=null;
	public static String DME_FILE_LIST_BASED_ON_FREQUENCY=null;
	public static String FILE_AUDIT_DAILY_RECORDS_COUNT=null;
	public static String FILE_AUDIT_WEEKLY_RECORDS_COUNT=null;
	public static String FILE_AUDIT_MONTHLY_RECORDS_COUNT=null;
	public static String FILE_AUDIT_QUARTERLY_RECORDS_COUNT=null;
	public static String FILE_AUDIT_YEARLY_RECORDS_COUNT=null;
	public static String ADD_FILE_UPLOAD_AUDIT_DFS = null;
	public static String ADD_FILE_UPLOAD_AUDIT_MA=null;
	public static String FILE_AUDIT_QUARTERLY_HUD_RECORDS_COUNT=null;
	
	//Notification Message
	public static String GET_NOTIFICATION_LIST = null;
	public static String GET_PREVIOUS_NOTIFICATION_LIST = null;
	public static String GET_NEWS_LIST = null;
	public static String GET_NEWS_INFO=null;

	// DMS Directorate
	public static String DMS_DAILY_RECORDS = null;
	public static String DMS_WEEKLY_RECORDS = null;
	public static String DMS_MONTHLY_RECORDS = null;
	public static String DMS_QUARTERLY_RECORDS = null;
	public static String DMS_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String DMS_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String DMS_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String DMS_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String DMS_GENERAL_TYPE = null;
	public static String INSERT_DMS_DIRECTORATE = null;
	public static String INSERT_DMS_GENDER_AGEGROUP = null;
	public static String UPDATE_DMS_DIRECTORATE = null;
	public static String DMS_INSTITUTION = null;
	public static String DMS_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_DMS_INSTITUTION_COUNT = null;
	public static String SELECT_DMS_INDICATOR_MASTER = null;
	public static String SELECT_DMS_CLASSIFICATION = null;
	public static String DMS_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String DMS_INDICATOR_LIST = null;
	public static String DMS_CLASSIFICATION_LIST = null;
	public static String IS_DMS_DEMOGRAPHIC = null;
	public static String DMS_DISTRICT_NAME=null;
	public static String DMS_ACCESS_RESTRICTED_DISTRICT_NAME_COUNT=null;

	// DPH Directorate
	public static String DPH_DAILY_RECORDS = null;
	public static String DPH_WEEKLY_RECORDS = null;
	public static String DPH_MONTHLY_RECORDS = null;
	public static String DPH_QUARTERLY_RECORDS = null;
	public static String DPH_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String DPH_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String DPH_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String DPH_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String DPH_GENERAL_TYPE = null;
	public static String INSERT_DPH_DIRECTORATE = null;
	public static String INSERT_DPH_GENDER_AGEGROUP = null;
	public static String UPDATE_DPH_DIRECTORATE = null;
	public static String DPH_INSTITUTION_TYPE = null;
	public static String INSTITUTION_NAME = null;
	public static String INSTITUTION_NAME_COUNT = null;
	public static String ACCESS_RESTRICTED_INSTITUTION_NAME_COUNT = null;
	public static String SELECT_DPH_INDICATOR_MASTER = null;
	public static String SELECT_DPH_CLASSIFICATION = null;
	public static String SELECT_DPH_PROGRAM = null;
	public static String DPH_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String DPH_INDICATOR_LIST = null;
	public static String DPH_CLASSIFICATION_LIST = null;
	public static String IS_DPH_DEMOGRAPHIC = null;

	// TNMSC Directorate
	public static String TNMSC_DAILY_RECORDS = null;
	public static String TNMSC_WEEKLY_RECORDS = null;
	public static String TNMSC_MONTHLY_RECORDS = null;
	public static String TNMSC_QUARTERLY_RECORDS = null;
	public static String TNMSC_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String TNMSC_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String TNMSC_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String TNMSC_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String TNMSC_GENERAL_TYPE = null;
	public static String INSERT_TNMSC_DIRECTORATE = null;
	public static String INSERT_TNMSC_GENDER_AGEGROUP = null;
	public static String UPDATE_TNMSC_DIRECTORATE = null;
	public static String SELECT_TNMSC_INSTITUTION = null;
	public static String TNMSC_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_TNMSC_INSTITUTION_COUNT = null;
	public static String SELECT_TNMSC_INDICATOR_MASTER = null;
	public static String SELECT_TNMSC_CLASSIFICATION = null;
	public static String TNMSC_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String TNMSC_INDICATOR_LIST = null;
	public static String TNMSC_CLASSIFICATION_LIST = null;
	public static String IS_TNMSC_DEMOGRAPHIC = null;

	// AIDS Directorate
	public static String AIDS_DAILY_RECORDS = null;
	public static String AIDS_WEEKLY_RECORDS = null;
	public static String AIDS_MONTHLY_RECORDS = null;
	public static String AIDS_QUARTERLY_RECORDS = null;
	public static String AIDS_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String AIDS_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String AIDS_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String AIDS_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String AIDS_GENERAL_TYPE = null;
	public static String INSERT_AIDS_DIRECTORATE = null;
	public static String INSERT_AIDS_GENDER_AGEGROUP = null;
	public static String UPDATE_AIDS_DIRECTORATE = null;
	public static String AIDS_INSTITUTION = null;
	public static String AIDS_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_AIDS_INSTITUTION_COUNT = null;
	public static String SELECT_AIDS_INDICATOR_MASTER = null;
	public static String SELECT_AIDS_CLASSIFICATION = null;
	public static String AIDS_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String AIDS_INDICATOR_LIST = null;
	public static String AIDS_CLASSIFICATION_LIST = null;
	public static String IS_AIDS_DEMOGRAPHIC = null;
	public static String AIDS_INSTITUTION_TYPE = null;

	// NRHM Directorate
	public static String NRHM_DAILY_RECORDS = null;
	public static String NRHM_WEEKLY_RECORDS = null;
	public static String NRHM_MONTHLY_RECORDS = null;
	public static String NRHM_QUARTERLY_RECORDS = null;
	public static String NRHM_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String NRHM_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String NRHM_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String NRHM_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String NRHM_GENERAL_TYPE = null;
	public static String INSERT_NRHM_DIRECTORATE = null;
	public static String INSERT_NRHM_GENDER_AGEGROUP = null;
	public static String UPDATE_NRHM_DIRECTORATE = null;
	public static String SELECT_NRHM_INDICATOR_MASTER = null;
	public static String SELECT_NRHM_CLASSIFICATION = null;
	public static String NRHM_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String NRHM_INDICATOR_LIST = null;
	public static String NRHM_CLASSIFICATION_LIST = null;
	public static String IS_NRHM_DEMOGRAPHIC = null;

	// RNTCP Directorate
	public static String RNTCP_DAILY_RECORDS = null;
	public static String RNTCP_WEEKLY_RECORDS = null;
	public static String RNTCP_MONTHLY_RECORDS = null;
	public static String RNTCP_QUARTERLY_RECORDS = null;
	public static String RNTCP_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String RNTCP_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String RNTCP_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String RNTCP_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String RNTCP_GENERAL_TYPE = null;
	public static String INSERT_RNTCP_DIRECTORATE = null;
	public static String INSERT_RNTCP_GENDER_AGEGROUP = null;
	public static String UPDATE_RNTCP_DIRECTORATE = null;
	public static String RNTCP_INSTITUTION = null;
	public static String RNTCP_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_RNTCP_INSTITUTION_COUNT = null;
	public static String SELECT_RNTCP_INDICATOR_MASTER = null;
	public static String SELECT_RNTCP_CLASSIFICATION = null;
	public static String RNTCP_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String RNTCP_INDICATOR_LIST = null;
	public static String RNTCP_CLASSIFICATION_LIST = null;
	public static String IS_RNTCP_DEMOGRAPHIC = null;
	public static String RNTCP_DISTRICT_NAME = null;
	public static String RNTCP_DISTRICT_NAME_COUNT = null;
	public static String RESTRICTED_RNTCP_DISTRICT_NAME_COUNT = null;

	// MA Directorate
	public static String MA_DAILY_RECORDS = null;
	public static String MA_WEEKLY_RECORDS = null;
	public static String MA_MONTHLY_RECORDS = null;
	public static String MA_QUARTERLY_RECORDS = null;
	public static String MA_YEARLY_RECORDS = null;
	public static String MA_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String MA_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String MA_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String MA_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String MA_DAILY_RECORDS_COUNT = null;
	public static String MA_WEEKLY_RECORDS_COUNT = null;
	public static String MA_MONTHLY_RECORDS_COUNT = null;
	public static String MA_QUARTERLY_RECORDS_COUNT = null;
	public static String MA_GENERAL_TYPE = null;
	public static String INSERT_MA_DIRECTORATE = null;
	public static String INSERT_MA_GENDER_AGEGROUP = null;
	public static String UPDATE_MA_DIRECTORATE = null;
	public static String SELECT_CORPORATION_MUNICIPALITY = null;
	public static String SELECT_MA_INDICATOR_MASTER = null;
	public static String SELECT_MA_CLASSIFICATION = null;
	public static String MA_REGION_NAME = null;
	public static String MA_REGION_NAME_COUNT = null;
	public static String ACCESS_RESTRICTED_MA_REGION_NAME_COUNT = null;
	public static String MA_CORPORATION_INSTITUTION = null;
	public static String MA_REGION_INSTITUTION = null;
	public static String MA_DISTRICT_NAME = null;
	public static String MA_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String MA_INDICATOR_LIST = null;
	public static String MA_CLASSIFICATION_LIST = null;
	public static String IS_MA_DEMOGRAPHIC = null;

	// DME Directorate
	public static String DME_DAILY_RECORDS = null;
	public static String DME_WEEKLY_RECORDS = null;
	public static String DME_MONTHLY_RECORDS = null;
	public static String DME_QUARTERLY_RECORDS = null;
	public static String DME_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String DME_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String DME_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String DME_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String DME_GENERAL_TYPE = null;
	public static String INSERT_DME_DIRECTORATE = null;
	public static String INSERT_DME_GENDER_AGEGROUP = null;
	public static String UPDATE_DME_DIRECTORATE = null;
	public static String DME_INSTITUTION = null;
	public static String DME_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_DME_INSTITUTION_COUNT = null;
	public static String DME_INDICATOR_MASTER = null;
	public static String DME_CLASSIFICATION = null;
	public static String DME_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String DME_INDICATOR_LIST = null;
	public static String DME_CLASSIFICATION_LIST = null;
	public static String IS_DME_DEMOGRAPHIC = null;
	public static String DME_DISTRICT_NAME=null;
	public static String DME_ACCESS_RESTRICTED_DISTRICT_NAME_COUNT=null;

	// DRMGR Directorate
	public static String DRMGR_DAILY_RECORDS = null;
	public static String DRMGR_WEEKLY_RECORDS = null;
	public static String DRMGR_MONTHLY_RECORDS = null;
	public static String DRMGR_QUARTERLY_RECORDS = null;
	public static String DRMGR_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String DRMGR_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String DRMGR_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String DRMGR_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String DRMGR_YEARLY_RECORDS = null;
	public static String DRMGR_GENERAL_TYPE = null;
	public static String INSERT_DRMGR_DIRECTORATE = null;
	public static String INSERT_DRMGR_GENDER_AGEGROUP = null;
	public static String UPDATE_DRMGR_DIRECTORATE = null;
	public static String DRMGR_INSTITUTION = null;
	public static String DRMGR_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_DRMGR_INSTITUTION_COUNT = null;
	public static String DRMGR_INDICATOR_MASTER = null;
	public static String DRMGR_CLASSIFICATION = null;
	public static String DRMGR_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String DRMGR_INDICATOR_LIST = null;
	public static String DRMGR_CLASSIFICATION_LIST = null;
	public static String IS_DRMGR_DEMOGRAPHIC = null;
	public static String DRMGR_INSTITUTIONTYPELIST = null;
	public static String DRMGR_INSTITUTIONSPECIALITYLIST = null;
	public static String DRMGR_INSTITUTIONLIST = null;
	public static String DRMGR_DEPARTMENT = null;
	public static String DRMGR_DEPARTMENT_COUNT = null;
	public static String ACCESS_RESTRICTED_DRMGR_DEPARTMENT_COUNT = null;


	// DFW Directorate
	public static String DFW_DAILY_RECORDS = null;
	public static String DFW_WEEKLY_RECORDS = null;
	public static String DFW_MONTHLY_RECORDS = null;
	public static String DFW_QUARTERLY_RECORDS = null;
	public static String DFW_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String DFW_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String DFW_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String DFW_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String DFW_GENERAL_TYPE = null;
	public static String INSERT_DFW_DIRECTORATE = null;
	public static String INSERT_DFW_GENDER_AGEGROUP = null;
	public static String UPDATE_DFW_DIRECTORATE = null;
	public static String DFW_INSTITUTION = null;
	public static String DFW_INSTITUTION_TYPE = null;
	public static String DFW_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_DFW_INSTITUTION_COUNT = null;
	public static String DFW_INDICATOR_MASTER = null;
	public static String DFW_CLASSIFICATION = null;
	public static String DFW_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String DFW_INDICATOR_LIST = null;
	public static String DFW_CLASSIFICATION_LIST = null;
	public static String IS_DFW_DEMOGRAPHIC = null;
	public static String DFW_POST_LIST = null;
	public static String INSERT_EMPLOYEE_DETAILS = null;
	public static String SELECT_EMPLOYEE_DETAILS = null;
	public static String DFW_EMPLOYEE_LIST = null;
	public static String INSERT_DFW_EMPLOYEE_QUALIFICATION = null;
	public static String INSERT_DFW_EMPLOYEE_LEAVE = null;
	public static String INSERT_DFW_EMPLOYEE_PROMOTION = null;
	public static String DFW_EMPLOYEE_TRANSFER=null;
	public static String DFW_EMPLOYEE_EXISTS=null;
	public static String SELECT_EMPLOYEE_QUALIFICATION_DETAILS = null;
	public static String SELECT_EMPLOYEE_PROMOTION_DETAILS = null;
	public static String SELECT_EMPLOYEE_LEAVE_DETAILS = null;
	public static String UPDATE_EMPLOYEE_DETAILS = null;
	public static String UPDATE_DFW_EMPLOYEE_QUALIFICATION = null;
	public static String UPDATE_DFW_EMPLOYEE_LEAVE = null;
	public static String INSERT_DFW_EMPLOYEE_HISTORY = null;
	public static String DFW_EMPLOYEE_LEAVE_EXISTS=null;
	public static String DFW_TRANSFER_INSTITUTION=null;
	public static String DFW_EMPLOYEE_TRANSFER_LIST=null;
	public static String DFW_TRANSFER_EMPLOYEE_DETAILS=null;
	public static String DFW_EMPLOYEE_TRANSFER_ACCEPT=null;
	public static String DFW_EMPLOYEE_PROMOTION_TYPE=null;
	public static String UPDATE_DFW_EMPLOYEE_PROMOTION=null;
	
	// SHTO Directorate
	public static String SHTO_DAILY_RECORDS = null;
	public static String SHTO_WEEKLY_RECORDS = null;
	public static String SHTO_MONTHLY_RECORDS = null;
	public static String SHTO_QUARTERLY_RECORDS = null;
	public static String SHTO_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String SHTO_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String SHTO_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String SHTO_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String SHTO_GENERAL_TYPE = null;
	public static String INSERT_SHTO_DIRECTORATE = null;
	public static String INSERT_SHTO_GENDER_AGEGROUP = null;
	public static String UPDATE_SHTO_DIRECTORATE = null;
	public static String SHTO_INSTITUTION = null;
	public static String SHTO_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_SHTO_INSTITUTION_COUNT = null;
	public static String SHTO_INDICATOR_MASTER = null;
	public static String SHTO_CLASSIFICATION = null;
	public static String SHTO_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String SHTO_INDICATOR_LIST = null;
	public static String SHTO_CLASSIFICATION_LIST = null;
	public static String IS_SHTO_DEMOGRAPHIC = null;
	public static String SHTO_WORKSHOP_NAME = null;
	public static String SHTO_WORKSHOP_NAME_COUNT = null;
	public static String SHTO_ACCESS_RESTRICTED_WORKSHOP_NAME_COUNT = null;
	public static String SHTO_MOBILE_WORKSHOP = null;
	public static String SHTO_MOBILE_WORKSHOP_COUNT = null;
	public static String ACCESS_RESTRICTED_SHTO_MOBILE_WORKSHOP_COUNT = null;

	// COC Directorate
	public static String COC_DAILY_RECORDS = null;
	public static String COC_WEEKLY_RECORDS = null;
	public static String COC_MONTHLY_RECORDS = null;
	public static String COC_QUARTERLY_RECORDS = null;
	public static String COC_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String COC_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String COC_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String COC_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String COC_GENERAL_TYPE = null;
	public static String INSERT_COC_DIRECTORATE = null;
	public static String INSERT_COC_GENDER_AGEGROUP = null;
	public static String UPDATE_COC_DIRECTORATE = null;
	public static String COC_INSTITUTION = null;
	public static String COC_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_COC_INSTITUTION_COUNT = null;
	public static String COC_INDICATOR_MASTER = null;
	public static String COC_CLASSIFICATION = null;
	public static String COC_REGION_NAME = null;
	public static String COC_REGION_NAME_COUNT = null;
	public static String ACCESS_RESTRICTED_COC_REGION_NAME_COUNT = null;
	public static String COC_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String COC_INDICATOR_LIST = null;
	public static String COC_CLASSIFICATION_LIST = null;
	public static String IS_COC_DEMOGRAPHIC = null;

	// SBCS Directorate
	public static String SBCS_DAILY_RECORDS = null;
	public static String SBCS_WEEKLY_RECORDS = null;
	public static String SBCS_MONTHLY_RECORDS = null;
	public static String SBCS_QUARTERLY_RECORDS = null;
	public static String SBCS_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String SBCS_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String SBCS_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String SBCS_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String SBCS_GENERAL_TYPE = null;
	public static String INSERT_SBCS_DIRECTORATE = null;
	public static String INSERT_SBCS_GENDER_AGEGROUP = null;
	public static String UPDATE_SBCS_DIRECTORATE = null;
	public static String SBCS_INSTITUTION = null;
	public static String SBCS_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_SBCS_INSTITUTION_COUNT = null;
	public static String SBCS_INDICATOR_MASTER = null;
	public static String SBCS_CLASSIFICATION = null;
	public static String SBCS_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String SBCS_INDICATOR_LIST = null;
	public static String SBCS_CLASSIFICATION_LIST = null;
	public static String IS_SBCS_DEMOGRAPHIC = null;

	// DCA Directorate
	public static String DCA_DAILY_RECORDS = null;
	public static String DCA_WEEKLY_RECORDS = null;
	public static String DCA_MONTHLY_RECORDS = null;
	public static String DCA_QUARTERLY_RECORDS = null;
	public static String DCA_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String DCA_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String DCA_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String DCA_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String DCA_GENERAL_TYPE = null;
	public static String INSERT_DCA_DIRECTORATE = null;
	public static String INSERT_DCA_GENDER_AGEGROUP = null;
	public static String UPDATE_DCA_DIRECTORATE = null;
	public static String DCA_INSTITUTION = null;
	public static String DCA_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_DCA_INSTITUTION_COUNT = null;
	public static String DCA_INDICATOR_MASTER = null;
	public static String DCA_CLASSIFICATION = null;
	public static String DCA_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String DCA_INDICATOR_LIST = null;
	public static String DCA_CLASSIFICATION_LIST = null;
	public static String IS_DCA_DEMOGRAPHIC = null;
	public static String DCA_HUD_NAME = null;
	public static String BLOCK_NAME = null;
	public static String DCA_INSTITUTION_NAME = null;

	// MRB Directorate
	public static String MRB_DAILY_RECORDS = null;
	public static String MRB_WEEKLY_RECORDS = null;
	public static String MRB_MONTHLY_RECORDS = null;
	public static String MRB_QUARTERLY_RECORDS = null;
	public static String MRB_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String MRB_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String MRB_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String MRB_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String MRB_YEARLY_RECORDS = null;
	public static String MRB_GENERAL_TYPE = null;
	public static String INSERT_MRB_DIRECTORATE = null;
	public static String INSERT_MRB_GENDER_AGEGROUP = null;
	public static String UPDATE_MRB_DIRECTORATE = null;
	public static String MRB_INSTITUTION = null;
	public static String MRB_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_MRB_INSTITUTION_COUNT = null;
	public static String MRB_INDICATOR_MASTER = null;
	public static String MRB_CLASSIFICATION = null;
	public static String MRB_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String MRB_INDICATOR_LIST = null;
	public static String MRB_CLASSIFICATION_LIST = null;
	public static String IS_MRB_DEMOGRAPHIC = null;

	// DFS Directorate
	public static String DFS_DAILY_RECORDS = null;
	public static String DFS_WEEKLY_RECORDS = null;
	public static String DFS_MONTHLY_RECORDS = null;
	public static String DFS_QUARTERLY_RECORDS = null;
	public static String DFS_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String DFS_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String DFS_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String DFS_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String DFS_GENERAL_TYPE = null;
	public static String INSERT_DFS_DIRECTORATE = null;
	public static String INSERT_DFS_GENDER_AGEGROUP = null;
	public static String UPDATE_DFS_DIRECTORATE = null;
	public static String DFS_INSTITUTION = null;
	public static String DFS_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_DFS_INSTITUTION_COUNT = null;
	public static String DFS_INDICATOR_MASTER = null;
	public static String DFS_CLASSIFICATION = null;
	public static String DFS_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String DFS_INDICATOR_LIST = null;
	public static String DFS_CLASSIFICATION_LIST = null;
	public static String IS_DFS_DEMOGRAPHIC = null;
	public static String DFS_LAB = null;
	public static String DFS_LAB_DISTRICT = null;
	public static String DFS_AREA_TYPE = null;
	public static String DFS_AREA = null;
	public static String DFS_AREACODE = null;

	// CMCHIS Directorate
	public static String CMCHIS_DAILY_RECORDS = null;
	public static String CMCHIS_WEEKLY_RECORDS = null;
	public static String CMCHIS_MONTHLY_RECORDS = null;
	public static String CMCHIS_QUARTERLY_RECORDS = null;
	public static String CMCHIS_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String CMCHIS_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String CMCHIS_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String CMCHIS_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String CMCHIS_GENERAL_TYPE = null;
	public static String INSERT_CMCHIS_DIRECTORATE = null;
	public static String INSERT_CMCHIS_GENDER_AGEGROUP = null;
	public static String UPDATE_CMCHIS_DIRECTORATE = null;
	public static String CMCHIS_INSTITUTION = null;
	public static String CMCHIS_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_CMCHIS_INSTITUTION_COUNT = null;
	public static String CMCHIS_INDICATOR_MASTER = null;
	public static String CMCHIS_CLASSIFICATION = null;
	public static String CMCHIS_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String CMCHIS_INDICATOR_LIST = null;
	public static String CMCHIS_CLASSIFICATION_LIST = null;
	public static String IS_CMCHIS_DEMOGRAPHIC = null;
	public static String CMCHIS_WEBSERVICE_INSERT_PREAUTH = null;
	public static String CMCHIS_WEBSERVICE_INSERT_CLAIMS = null;
	public static String CMCHIS_WEBSERVICE_INSERT_PAYMENT=null;
	public static String CMCHIS_WEBSERVICE_INSERT_DC=null;
	public static String CMCHIS_WEBSERVICE_INSERT_HOSPITAL = null;
	public static String CMCHIS_WEBSERVICE_DISABLE_HOSPITAL = null;

	// DIM Directorate
	public static String DIM_DAILY_RECORDS = null;
	public static String DIM_WEEKLY_RECORDS = null;
	public static String DIM_MONTHLY_RECORDS = null;
	public static String DIM_QUARTERLY_RECORDS = null;
	public static String DIM_YEARLY_RECORDS = null;
	public static String DIM_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String DIM_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String DIM_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String DIM_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String DIM_GENERAL_TYPE = null;
	public static String INSERT_DIM_DIRECTORATE = null;
	public static String INSERT_DIM_GENDER_AGEGROUP = null;
	public static String UPDATE_DIM_DIRECTORATE = null;
	public static String DIM_INSTITUTION = null;
	public static String DIM_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_DIM_INSTITUTION_COUNT = null;
	public static String DIM_INDICATOR_MASTER = null;
	public static String DIM_CLASSIFICATION = null;
	public static String DIM_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String DIM_INDICATOR_LIST = null;
	public static String DIM_INDICATOR_LIST1 = null;
	public static String DIM_CLASSIFICATION_LIST = null;
	public static String IS_DIM_DEMOGRAPHIC = null;
	public static String DIM_INSTITUTION_TYPE = null;
	public static String DIM_SYSTEM = null;

	// NLEP Directorate
	public static String NLEP_DAILY_RECORDS = null;
	public static String NLEP_WEEKLY_RECORDS = null;
	public static String NLEP_MONTHLY_RECORDS = null;
	public static String NLEP_QUARTERLY_RECORDS = null;
	public static String NLEP_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String NLEP_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String NLEP_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String NLEP_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String NLEP_GENERAL_TYPE = null;
	public static String INSERT_NLEP_DIRECTORATE = null;
	public static String INSERT_NLEP_GENDER_AGEGROUP = null;
	public static String UPDATE_NLEP_DIRECTORATE = null;
	public static String NLEP_INSTITUTION = null;
	public static String NLEP_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_NLEP_INSTITUTION_COUNT = null;
	public static String NLEP_INDICATOR_MASTER = null;
	public static String NLEP_CLASSIFICATION = null;
	public static String NLEP_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String NLEP_INDICATOR_LIST = null;
	public static String NLEP_CLASSIFICATION_LIST = null;
	public static String IS_NLEP_DEMOGRAPHIC = null;
	public static String NLEP_YEARLY_RECORDS = null;

	// SBHI Directorate
	public static String SBHI_DAILY_RECORDS = null;
	public static String SBHI_WEEKLY_RECORDS = null;
	public static String SBHI_MONTHLY_RECORDS = null;
	public static String SBHI_QUARTERLY_RECORDS = null;
	public static String SBHI_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String SBHI_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String SBHI_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String SBHI_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String SBHI_GENERAL_TYPE = null;
	public static String INSERT_SBHI_DIRECTORATE = null;
	public static String INSERT_SBHI_GENDER_AGEGROUP = null;
	public static String UPDATE_SBHI_DIRECTORATE = null;
	public static String SBHI_INSTITUTION = null;
	public static String SBHI_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_SBHI_INSTITUTION_COUNT = null;
	public static String SBHI_INDICATOR_MASTER = null;
	public static String SBHI_CLASSIFICATION = null;
	public static String SBHI_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String SBHI_INDICATOR_LIST = null;
	public static String SBHI_CLASSIFICATION_LIST = null;
	public static String IS_SBHI_DEMOGRAPHIC = null;

	// ESI Directorate
	public static String ESI_DAILY_RECORDS = null;
	public static String ESI_WEEKLY_RECORDS = null;
	public static String ESI_MONTHLY_RECORDS = null;
	public static String ESI_QUARTERLY_RECORDS = null;
	public static String ESI_DAILY_DEMOGRAPHIC_RECORDS = null;
	public static String ESI_MONTHLY_DEMOGRAPHIC_RECORDS = null;
	public static String ESI_WEEKLY_DEMOGRAPHIC_RECORDS = null;
	public static String ESI_QUATERLY_DEMOGRAPHIC_RECORDS = null;
	public static String ESI_GENERAL_TYPE = null;
	public static String INSERT_ESI_DIRECTORATE = null;
	public static String INSERT_ESI_GENDER_AGEGROUP = null;
	public static String UPDATE_ESI_DIRECTORATE = null;
	public static String ESI_INSTITUTION = null;
	public static String ESI_INSTITUTION_COUNT = null;
	public static String ACCESS_RESTRICTED_ESI_INSTITUTION_COUNT = null;
	public static String ESI_INDICATOR_MASTER = null;
	public static String ESI_CLASSIFICATION = null;
	public static String ESI_GENERAL_CATEGORY_LIST_BY_FREQUENCY = null;
	public static String ESI_INDICATOR_LIST = null;
	public static String ESI_CLASSIFICATION_LIST = null;
	public static String IS_ESI_DEMOGRAPHIC = null;

	//Health Secretary Dashboard
	public static String HSDASHBOARD_SEARCHOPTIONS=null;	
	public static String YEAR_LIST=null;
	public static String MONTH_LIST=null;
	public static String DISTRICT_LIST=null;
	public static String INSTITUTION_LIST=null;
	public static String CHILDINDDETAILSLIST=null;
	public static String FOURPARAMDERIVEDINDDETAILSLIST=null;
	public static String FOURPARAMDIRECTINDDETAILSLIST=null;
	public static String FOURPARAMCHILDINDDATA=null;
	public static String YMDIFOURPARAMDIRECTINDDYSFUNCTIONDETAILSLIST=null;
	public static String YDMTHREEPARAMDERIVEDINDDETAILSLIST=null;
	public static String YDMTHREEPARAMDIRECTINDDETAILSLIST=null;
	public static String YDMTHREEPARAMDIRECTINDDYSFUNCTIONDETAILSLIST=null;
	public static String YDMTHREEPARAMCHILDINDDATA=null;
	public static String YDITHREEPARAMDERIVEDINDDETAILSLIST=null;
	public static String YDITHREEPARAMDIRECTINDDYSFUNCTIONDETAILSLIST=null;
	public static String YMITHREEPARAMDIRECTINDDYSFUNCTIONDETAILSLIST=null;
	public static String YDITHREEPARAMDIRECTINDDETAILSLIST=null;
	public static String YDITHREEPARAMCHILDINDDATA=null;
	public static String YMITHREEPARAMDERIVEDINDDETAILSLIST=null;
	public static String YMITHREEPARAMDIRECTINDDETAILSLIST=null;
	public static String YMITHREEPARAMCHILDINDDATA=null;
	public static String YMTWOPARAMDERIVEDINDDETAILSLIST=null;
	public static String YMTWOPARAMDERIVEDINDDYSFUNCTIODETAILSLIST=null;
	public static String YITWOPARAMDERIVEDINDDYSFUNCTIODETAILSLIST=null;
	public static String YDTWOPARAMDERIVEDINDDYSFUNCTIODETAILSLIST=null;
	public static String ONEPARAMDERIVEDINDDYSFUNCTIODETAILSLIST=null;
	public static String YMTWOPARAMDIRECTINDDETAILSLIST=null;
	public static String YMTWOPARAMCHILDINDDATA=null;	
	public static String YDTWOPARAMDERIVEDINDDETAILSLIST=null;
	public static String YDTWOPARAMDIRECTINDDETAILSLIST=null;
	public static String YDTWOPARAMCHILDINDDATA=null;	
	public static String YITWOPARAMDERIVEDINDDETAILSLIST=null;
	public static String YITWOPARAMDIRECTINDDETAILSLIST=null;
	public static String YITWOPARAMCHILDINDDATA=null;
	public static String ONEPARAMDERIVEDINDDETAILSLIST=null;
	public static String ONEPARAMDIRECTINDDETAILSLIST=null;
	public static String ONEPARAMCHILDINDDATA=null;
	public static String PROJECTEDVALUES=null;
	public static String PROJECTEDALLDISTRICTVALUES=null;
	public static String PROJECTEDDISTRICTVALUES=null;
	public static String PROJECTEDYEARVALUES=null;
	public static String DIRECT_PROJECTIONS=null;
	public static String IND_MAX_MONTH_YEAR=null;;
	
	//private sector
	public static String PVTSEC_CLASSIFICATION_LIST=null;
	public static String PVTSEC_GENERAL_TYPE=null;
	public static String UPDATE_PVTSEC_DIRECTORATE=null;
	public static String PVTSEC_GENERAL_CATEGORY_LIST_BY_FREQUENCY=null;
	public static String PVTSEC_DAILY_RECORDS=null;
	public static String PVTSEC_WEEKLY_RECORDS=null;
	public static String PVTSEC_MONTHLY_RECORDS=null;
	public static String PVTSEC_QUARTERLY_RECORDS=null;
	public static String PVTSEC_YEARLY_RECORDS=null;
	public static String PVTSEC_INDICATOR_LIST=null;
	public static String INSERT_PVTSEC_DIRECTORATE=null;
	
	//Statistics
	public static String INDICATORS_LIST=null;
	public static String INDICATOR_STATISTICS_LIST=null;
	public static String INDICATOR_DATA_AVAILABILITY=null;
	public static String INDICATOR_MAX_DATA_AVAILABLE=null;
	
	//MMR Demo page
	public static String MMR_BYDIST=null;
	public static String MMR_BYDISTHM=null;
	public static String MMR_BYINST=null;
	public static String MMR_CHINDAN=null;
	public static String MMR_CHINDDL=null;
	public static String MMR_CHINDLD=null;
	public static String MMR_CHINDNC=null;
	public static String MMR_BYINSTHM=null;
	public static String MMR_AXISLINEGT=null;
	public static String MMR_AXISLINELT=null;
	public static String MMR_FCHINDNC=null;
	public static String MMR_BYRISK=null;
	public static String MMR_CHINDDL1=null;
	public static String MMR_CHINDDL2=null;
	public static String MMR_DEL1=null;
	public static String MMR_DEL2=null;
	public static String MMR_DEL3=null;
	public static String MMR_CHINDANM=null;
	public static String MMR_AXISLINEGT1=null;
	public static String MMR_AXISLINELT1=null;
	
	//IMR Demo page
		public static String IMR_BYDIST=null;
		public static String IMR_BYDISTHM=null;
		public static String IMR_BYINST=null;
		public static String IMR_CHINDAN=null;
		public static String IMR_CHINDDL=null;
		public static String IMR_CHINDLD=null;
		public static String IMR_CHINDNC=null;
		public static String IMR_BYINSTHM=null;
		public static String IMR_AXISLINEGT=null;
		public static String IMR_AXISLINELT=null;
		public static String IMR_FCHINDNC=null;
		public static String IMR_BYRISK=null;
		public static String IMR_CHINDDL1=null;
		public static String IMR_CHINDDL2=null;
		public static String IMR_DEL1=null;
		public static String IMR_DEL2=null;
		public static String IMR_DEL3=null;
		public static String IMR_CHINDANM=null;
		public static String IMR_AXISLINEGT1=null;
		public static String IMR_AXISLINELT1=null;
		public static String CHART_HEADER=null;
		public static String CHART_RANGE=null;
		
		//CDNCD
		public static String TM_DISTRICTDATANCD=null;
		public static String TM_DISTRICTDATACD=null;
		public static String TM_JSON=null;	
		public static String TM_JSONGRANDCHILDCD=null;
		public static String TM_JSONGRANDCHILDNCD=null;
		public static String TM_JSONGRANDDATA=null;
		public static String TM_CD=null;
		public static String TM_NCD=null;
		public static String TM_CDCLIKD=null;
		public static String TM_NCDCLIKD=null;
		public static String TM_CDCLIKI=null;
		public static String TM_NCDCLIKI=null;
		public static String TM_JSONI=null;
		public static String CD_DISTRICTDATA = null;
		public static String CD_INSTITUTIONDATA = null;
		public static String NCD_DISTRICTDATA = null;
		public static String NCD_INSTITUTIONDATA = null;
		
		//IND ZONE
		public static String IND_DIST=null;
		
		//SDGS Dashboard
		public static String DIST_MATERNALDEATH=null;
		public static String MATERNALDEATH_MONTH=null;
		public static String SDGS_THRESHOLD=null;
		//DMS Report Zone
		public static String DMS_REPORT=null;
		//Predictive Zone
		public static String GETIMRHEATMAP_TOPLEFTDATA =null;
		public static String GETIMRHEATMAP_TOPRIGHTDATA=null;
		public static String GETIMRHEATMAP_BOTTOMLEFTDATA=null;
		public static String GETIMRHEATMAP_BOTTOMRIGHTDATA=null;
		public static String GETDISTRICTLIST=null;
		//IMR2
		public static String GETIMR2_HEATMAPTOPLEFTDATA =null;
		public static String GETIMR2_LINETOPRIGHTDATA =null;
		public static String GETIMR2_HEATMAPBOTTOMDATA =null;
		//DOB
		public static String GETDISEASELIST=null;
		public static String GETDOB_HEATMAPTOPLEFTDATA=null;
		public static String GETDOB_HEATMAPTOPRIGHTDATA=null;
		public static String GETDOB_LINEBOTTOMLEFTDATA=null;
		public static String GETDOB_HEATMAPBOTTTOMRIGHTDATA=null;
		//HOB
		public static String GETHOB_LINETOPLEFTDATA=null;
		public static String GETHOB_BARTOPRIGHTDATA=null;
		public static String GETHOB_BOTTOMDATA=null;
		//BBSI
		public static String GETBBSI_BARTOPLEFTDATA=null;
		public static String GETBBSI_BARTOPRIGHTDATA=null;
		public static String GETBBSI_BOTTOMLEFTDATA=null;
		//BBS
		public static String GETBBS_TOPLEFTDATA=null;
		public static String GETBBS_TOPRIGHTDATA=null;
		public static String GETBBS_BOTTOMLEFTDATA=null;
		public static String GETBBS_BOTTOMRIGHTDATA=null;
		public static String GETINDCATEGORYLIST=null;
		//FIP
		public static String GETFIP_TOPLEFTDATA=null;
		public static String GETFIP_TOPRIGHTDATA=null;
		public static String GETFIP_BOTTOMLEFTDATA=null;
		public static String GETFIP_BOTTOMRIGHTDATA=null;
		public static String GETYEARLIST=null;
		//CDD
		public static String GETFILETYPELIST=null;
		public static String GETREASONLIST=null;
		public static String GETCDD_TOPLEFTDATA=null;
		public static String GETCDD_TOPRIGHTDATA=null;
		public static String GETCDD_BOTTOMLEFTDATA=null;
		public static String GETCDD_BOTTOMRIGHTDATA=null;
		//CSP
		public static String GETCSP_TOPLEFTDATA=null;
		public static String GETCSP_TOPRIGHTDATA=null;
		public static String GETCSP_BOTTOMLEFTDATA=null;
		public static String GETCSP_BOTTOMRIGHTDATA=null;
		//CPP
		public static String GETCPP_TOPLEFTDATA=null;
		public static String GETCPP_TOPRIGHTDATA=null;
		public static String GETCPP_BOTTOMLEFTDATA=null;
		public static String GETCPP_BOTTOMRIGHTDATA=null;
		public static String GETSPECIALITYLIST=null;
		//CNP
		public static String GETCNP_TOPLEFTDATA=null;
		public static String GETCNP_TOPRIGHTDATA=null;
		public static String GETCNP_BOTTOMLEFTDATA=null;
		public static String GETCNP_BOTTOMRIGHTDATA=null;
		//MMR INSIDES
		public static String GETMMRI_TOPLEFTDATA=null;
		public static String GETMMRI_TOPRIGHTDATA=null;
		public static String GETMMRI_BOTTOMLEFTDATA=null;
		//MMR OPERATIONAL
		public static String GETMMRO_TOPLEFTDATA=null;
		public static String GETMMRO_TOPRIGHTDATA=null;
		public static String GETMMRO_BOTTOMLEFTDATA=null;
		public static String GETMMRO_BOTTOMRIGHTDATA=null;
		public static String GETREASONABLEINDICATORLIST=null;
		//Measles
		public static String GETMEASLES_TOPLEFTDATA=null;
		public static String GETMEASLES_TOPRIGHTDATA=null;
		public static String GETMEASLES_BOTTOMLEFTDATA=null;
		public static String GETMEASLES_BOTTOMRIGHTDATA=null;
		
		
		public static String DIRECTORATE_MASTER_DIM_LIST=null;
		public static String SAVE_USER_DETAILS = null;
		public static String GET_USER_DETAILS = null;
		public static String GET_LDAPORGNAME_DETAILS = null;
		public static String GET_USER_DETAILS_ByOGId = null;
		public static String USER_MANAGEMENT_LIST = null;
		public static String USER_MANAGEMENT_LIST_BY_USERID = null;
		public static String Update_USER_VALUES = null;
		public static String Update_CHANGE_PWD_VALUES = null;
		
		
		
		
		
		
		
		
		
	public static void loadParameters() {
		Properties properties = null;
		try {
			properties = new Properties();
			InputStream inputStream = ShdrcQueryList.class.getClassLoader()
					.getResourceAsStream("shdrcquery.properties");
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (properties != null) {
			DISTRICT_NAME = properties.getProperty("DISTRICT_NAME");
			DISTRICT_NAME_COUNT = properties.getProperty("DISTRICT_NAME_COUNT");
			ACCESS_RESTRICTED_DISTRICT_NAME_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_DISTRICT_NAME_COUNT");
			SELECT_DIRECTORATE_MASTER = properties
					.getProperty("SELECT_DIRECTORATE_MASTER");
			SELECT_MA_INDICATOR_MASTER = properties
					.getProperty("SELECT_MA_INDICATOR_MASTER");
			SELECT_DIRECTORATE_SERVLET = properties
					.getProperty("SELECT_DIRECTORATE_SERVLET");
			SELECT_DEMOGRAPHIC_MASTER = properties
					.getProperty("SELECT_DEMOGRAPHIC_MASTER");
			SELECT_MOBILE_NO = properties.getProperty("SELECT_MOBILE_NO");
			GET_SMS_INFO = properties.getProperty("GET_SMS_INFO");
			GET_CRON_TRIGER_SMS_INFO = properties.getProperty("GET_CRON_TRIGER_SMS_INFO");
			UPDATE_SMS_ALERT = properties.getProperty("UPDATE_SMS_ALERT");
			/*GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("GENERAL_CATEGORY_LIST_BY_FREQUENCY");*/
			//INDICATOR_LIST = properties.getProperty("INDICATOR_LIST");
			//CLASSIFICATION_LIST = properties.getProperty("CLASSIFICATION_LIST");
			GET_USER_TIER = properties.getProperty("GET_USER_TIER");
			GENDER_AGEGROUP = properties.getProperty("GENDER_AGEGROUP");
			DEMOGRAPHIC_STATUS = properties.getProperty("DEMOGRAPHIC_STATUS");
			IS_DEMOGRAPHIC = properties.getProperty("IS_DEMOGRAPHIC");
			SELECT_USER_PWD = properties.getProperty("SELECT_USER_PWD");
			UPDATE_USER_PWD = properties.getProperty("UPDATE_USER_PWD");
			SELECT_USER_DETAILS = properties.getProperty("SELECT_USER_DETAILS");
			UPDATE_USER_DETAILS = properties.getProperty("UPDATE_USER_DETAILS");
			SELECT_AUTHORIZE_LIST = properties
					.getProperty("SELECT_AUTHORIZE_LIST");
			SELECT_INSTITUTION_LIST = properties
					.getProperty("SELECT_INSTITUTION_LIST");
			UPDATE_USER_ACCESS = properties.getProperty("UPDATE_USER_ACCESS");
			INSERT_MESSAGE_ALERT = properties.getProperty("INSERT_MESSAGE_ALERT");
			GET_USER_ACCESS_LEVEL = properties.getProperty("GET_USER_ACCESS_LEVEL");
			INSERT_USER_AUDIT_LOG = properties.getProperty("INSERT_USER_AUDIT_LOG");
			DIRECTORATE_LIST_BY_ID = properties.getProperty("DIRECTORATE_LIST_BY_ID");
			
			// File Upload
			ADD_FILE_UPLOAD_AUDIT = properties
					.getProperty("ADD_FILE_UPLOAD_AUDIT");
			ADD_FILE_UPLOAD_AUDIT_DFS = properties
					.getProperty("ADD_FILE_UPLOAD_AUDIT_DFS");
			ADD_FILE_UPLOAD_AUDIT_MA=properties
					.getProperty("ADD_FILE_UPLOAD_AUDIT_MA");
			GET_DIR_FILE_LIST= properties
					.getProperty("GET_DIR_FILE_LIST");
			FILE_LIST_BASED_ON_FREQUENCY= properties
					.getProperty("FILE_LIST_BASED_ON_FREQUENCY");
			DME_FILE_LIST_BASED_ON_FREQUENCY=properties
					.getProperty("DME_FILE_LIST_BASED_ON_FREQUENCY");
			FILE_AUDIT_QUARTERLY_HUD_RECORDS_COUNT=properties
					.getProperty("FILE_AUDIT_QUARTERLY_HUD_RECORDS_COUNT");
			
			FILE_AUDIT_DAILY_RECORDS_COUNT = properties.getProperty("FILE_AUDIT_DAILY_RECORDS_COUNT");
			FILE_AUDIT_WEEKLY_RECORDS_COUNT = properties.getProperty("FILE_AUDIT_WEEKLY_RECORDS_COUNT");
			FILE_AUDIT_MONTHLY_RECORDS_COUNT = properties.getProperty("FILE_AUDIT_MONTHLY_RECORDS_COUNT");
			FILE_AUDIT_QUARTERLY_RECORDS_COUNT = properties.getProperty("FILE_AUDIT_QUARTERLY_RECORDS_COUNT");
			FILE_AUDIT_YEARLY_RECORDS_COUNT = properties.getProperty("FILE_AUDIT_YEARLY_RECORDS_COUNT");
			
			//Notification Message
			GET_NOTIFICATION_LIST = properties
					.getProperty("GET_NOTIFICATION_LIST");
			GET_PREVIOUS_NOTIFICATION_LIST = properties
					.getProperty("GET_PREVIOUS_NOTIFICATION_LIST");
			GET_NEWS_LIST = properties
					.getProperty("GET_NEWS_LIST");
			GET_NEWS_INFO=properties
					.getProperty("GET_NEWS_INFO");

			// DMS Directorate
			DMS_DAILY_RECORDS = properties.getProperty("DMS_DAILY_RECORDS");
			DMS_WEEKLY_RECORDS = properties.getProperty("DMS_WEEKLY_RECORDS");
			DMS_MONTHLY_RECORDS = properties.getProperty("DMS_MONTHLY_RECORDS");
			DMS_QUARTERLY_RECORDS = properties
					.getProperty("DMS_QUARTERLY_RECORDS");
			DMS_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DMS_DAILY_DEMOGRAPHIC_RECORDS");
			DMS_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DMS_WEEKLY_DEMOGRAPHIC_RECORDS");
			DMS_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DMS_MONTHLY_DEMOGRAPHIC_RECORDS");
			DMS_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DMS_QUATERLY_DEMOGRAPHIC_RECORDS");
			DMS_GENERAL_TYPE = properties.getProperty("DMS_GENERAL_TYPE");
			INSERT_DMS_DIRECTORATE = properties
					.getProperty("INSERT_DMS_DIRECTORATE");
			INSERT_DMS_GENDER_AGEGROUP = properties
					.getProperty("INSERT_DMS_GENDER_AGEGROUP");
			UPDATE_DMS_DIRECTORATE = properties
					.getProperty("UPDATE_DMS_DIRECTORATE");
			DMS_INSTITUTION = properties.getProperty("DMS_INSTITUTION");
			DMS_INSTITUTION_COUNT = properties
					.getProperty("DMS_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_DMS_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_DMS_INSTITUTION_COUNT");
			SELECT_DMS_INDICATOR_MASTER = properties
					.getProperty("SELECT_DMS_INDICATOR_MASTER");
			SELECT_DMS_CLASSIFICATION = properties
					.getProperty("SELECT_DMS_CLASSIFICATION");
			DMS_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("DMS_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			DMS_INDICATOR_LIST = properties.getProperty("DMS_INDICATOR_LIST");
			DMS_CLASSIFICATION_LIST = properties
					.getProperty("DMS_CLASSIFICATION_LIST");
			DMS_DISTRICT_NAME = properties.getProperty("DMS_DISTRICT_NAME");
			DMS_ACCESS_RESTRICTED_DISTRICT_NAME_COUNT = properties.getProperty("DMS_ACCESS_RESTRICTED_DISTRICT_NAME_COUNT");


			// DPH Directorate
			DPH_DAILY_RECORDS = properties.getProperty("DPH_DAILY_RECORDS");
			DPH_WEEKLY_RECORDS = properties.getProperty("DPH_WEEKLY_RECORDS");
			DPH_MONTHLY_RECORDS = properties.getProperty("DPH_MONTHLY_RECORDS");
			DPH_QUARTERLY_RECORDS = properties
					.getProperty("DPH_QUARTERLY_RECORDS");
			DPH_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DPH_DAILY_DEMOGRAPHIC_RECORDS");
			DPH_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DPH_WEEKLY_DEMOGRAPHIC_RECORDS");
			DPH_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DPH_MONTHLY_DEMOGRAPHIC_RECORDS");
			DPH_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DPH_QUATERLY_DEMOGRAPHIC_RECORDS");
			DPH_GENERAL_TYPE = properties.getProperty("DPH_GENERAL_TYPE");
			INSERT_DPH_DIRECTORATE = properties
					.getProperty("INSERT_DPH_DIRECTORATE");
			INSERT_DPH_GENDER_AGEGROUP = properties
					.getProperty("INSERT_DPH_GENDER_AGEGROUP");
			UPDATE_DPH_DIRECTORATE = properties
					.getProperty("UPDATE_DPH_DIRECTORATE");
			DPH_INSTITUTION_TYPE = properties
					.getProperty("DPH_INSTITUTION_TYPE");
			INSTITUTION_NAME = properties.getProperty("INSTITUTION_NAME");
			INSTITUTION_NAME_COUNT = properties
					.getProperty("INSTITUTION_NAME_COUNT");
			ACCESS_RESTRICTED_INSTITUTION_NAME_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_INSTITUTION_NAME_COUNT");
			SELECT_DPH_INDICATOR_MASTER = properties
					.getProperty("SELECT_DPH_INDICATOR_MASTER");
			SELECT_DPH_CLASSIFICATION = properties
					.getProperty("SELECT_DPH_CLASSIFICATION");
			SELECT_DPH_PROGRAM = properties.getProperty("SELECT_DPH_PROGRAM");
			DPH_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("DPH_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			DPH_INDICATOR_LIST = properties.getProperty("DPH_INDICATOR_LIST");
			DPH_CLASSIFICATION_LIST = properties
					.getProperty("DPH_CLASSIFICATION_LIST");
			IS_DPH_DEMOGRAPHIC = properties.getProperty("IS_DPH_DEMOGRAPHIC");

			// TNMSC Directorate
			TNMSC_DAILY_RECORDS = properties.getProperty("TNMSC_DAILY_RECORDS");
			TNMSC_WEEKLY_RECORDS = properties
					.getProperty("TNMSC_WEEKLY_RECORDS");
			TNMSC_MONTHLY_RECORDS = properties
					.getProperty("TNMSC_MONTHLY_RECORDS");
			TNMSC_QUARTERLY_RECORDS = properties
					.getProperty("TNMSC_QUARTERLY_RECORDS");
			TNMSC_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("TNMSC_DAILY_DEMOGRAPHIC_RECORDS");
			TNMSC_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("TNMSC_WEEKLY_DEMOGRAPHIC_RECORDS");
			TNMSC_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("TNMSC_MONTHLY_DEMOGRAPHIC_RECORDS");
			TNMSC_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("TNMSC_QUATERLY_DEMOGRAPHIC_RECORDS");
			TNMSC_GENERAL_TYPE = properties.getProperty("TNMSC_GENERAL_TYPE");
			INSERT_TNMSC_DIRECTORATE = properties
					.getProperty("INSERT_TNMSC_DIRECTORATE");
			INSERT_TNMSC_GENDER_AGEGROUP = properties
					.getProperty("INSERT_TNMSC_GENDER_AGEGROUP");
			UPDATE_TNMSC_DIRECTORATE = properties
					.getProperty("UPDATE_TNMSC_DIRECTORATE");
			SELECT_TNMSC_INSTITUTION = properties
					.getProperty("SELECT_TNMSC_INSTITUTION");
			TNMSC_INSTITUTION_COUNT = properties
					.getProperty("TNMSC_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_TNMSC_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_TNMSC_INSTITUTION_COUNT");
			SELECT_TNMSC_INDICATOR_MASTER = properties
					.getProperty("SELECT_TNMSC_INDICATOR_MASTER");
			SELECT_TNMSC_CLASSIFICATION = properties
					.getProperty("SELECT_TNMSC_CLASSIFICATION");
			TNMSC_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("TNMSC_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			TNMSC_INDICATOR_LIST = properties
					.getProperty("TNMSC_INDICATOR_LIST");
			TNMSC_CLASSIFICATION_LIST = properties
					.getProperty("TNMSC_CLASSIFICATION_LIST");
			IS_TNMSC_DEMOGRAPHIC = properties
					.getProperty("IS_TNMSC_DEMOGRAPHIC");

			// AIDS Directorate
			AIDS_DAILY_RECORDS = properties.getProperty("AIDS_DAILY_RECORDS");
			AIDS_WEEKLY_RECORDS = properties.getProperty("AIDS_WEEKLY_RECORDS");
			AIDS_MONTHLY_RECORDS = properties
					.getProperty("AIDS_MONTHLY_RECORDS");
			AIDS_QUARTERLY_RECORDS = properties
					.getProperty("AIDS_QUARTERLY_RECORDS");
			AIDS_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("AIDS_DAILY_DEMOGRAPHIC_RECORDS");
			AIDS_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("AIDS_WEEKLY_DEMOGRAPHIC_RECORDS");
			AIDS_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("AIDS_MONTHLY_DEMOGRAPHIC_RECORDS");
			AIDS_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("AIDS_QUATERLY_DEMOGRAPHIC_RECORDS");
			AIDS_GENERAL_TYPE = properties.getProperty("AIDS_GENERAL_TYPE");
			INSERT_AIDS_DIRECTORATE = properties
					.getProperty("INSERT_AIDS_DIRECTORATE");
			INSERT_AIDS_GENDER_AGEGROUP = properties
					.getProperty("INSERT_AIDS_GENDER_AGEGROUP");
			INSERT_AIDS_GENDER_AGEGROUP = properties
					.getProperty("INSERT_AIDS_GENDER_AGEGROUP");
			UPDATE_AIDS_DIRECTORATE = properties
					.getProperty("UPDATE_AIDS_DIRECTORATE");
			AIDS_INSTITUTION = properties.getProperty("AIDS_INSTITUTION");
			AIDS_INSTITUTION_COUNT = properties
					.getProperty("AIDS_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_AIDS_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_AIDS_INSTITUTION_COUNT");
			SELECT_AIDS_INDICATOR_MASTER = properties
					.getProperty("SELECT_AIDS_INDICATOR_MASTER");
			SELECT_AIDS_CLASSIFICATION = properties
					.getProperty("SELECT_AIDS_CLASSIFICATION");
			AIDS_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("AIDS_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			AIDS_INDICATOR_LIST = properties.getProperty("AIDS_INDICATOR_LIST");
			AIDS_CLASSIFICATION_LIST = properties
					.getProperty("AIDS_CLASSIFICATION_LIST");
			IS_AIDS_DEMOGRAPHIC = properties.getProperty("IS_AIDS_DEMOGRAPHIC");
			AIDS_INSTITUTION_TYPE = properties.getProperty("AIDS_INSTITUTION_TYPE");

			// NRHM Directorate
			NRHM_DAILY_RECORDS = properties.getProperty("NRHM_DAILY_RECORDS");
			NRHM_WEEKLY_RECORDS = properties.getProperty("NRHM_WEEKLY_RECORDS");
			NRHM_MONTHLY_RECORDS = properties
					.getProperty("NRHM_MONTHLY_RECORDS");
			NRHM_QUARTERLY_RECORDS = properties
					.getProperty("NRHM_QUARTERLY_RECORDS");
			NRHM_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("NRHM_DAILY_DEMOGRAPHIC_RECORDS");
			NRHM_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("NRHM_WEEKLY_DEMOGRAPHIC_RECORDS");
			NRHM_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("NRHM_MONTHLY_DEMOGRAPHIC_RECORDS");
			NRHM_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("NRHM_QUATERLY_DEMOGRAPHIC_RECORDS");
			NRHM_GENERAL_TYPE = properties.getProperty("NRHM_GENERAL_TYPE");
			INSERT_NRHM_DIRECTORATE = properties
					.getProperty("INSERT_NRHM_DIRECTORATE");
			INSERT_NRHM_GENDER_AGEGROUP = properties
					.getProperty("INSERT_NRHM_GENDER_AGEGROUP");
			UPDATE_NRHM_DIRECTORATE = properties
					.getProperty("UPDATE_NRHM_DIRECTORATE");
			SELECT_NRHM_INDICATOR_MASTER = properties
					.getProperty("SELECT_NRHM_INDICATOR_MASTER");
			SELECT_NRHM_CLASSIFICATION = properties
					.getProperty("SELECT_NRHM_CLASSIFICATION");
			NRHM_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("NRHM_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			NRHM_INDICATOR_LIST = properties.getProperty("NRHM_INDICATOR_LIST");
			NRHM_CLASSIFICATION_LIST = properties
					.getProperty("NRHM_CLASSIFICATION_LIST");
			IS_NRHM_DEMOGRAPHIC = properties.getProperty("IS_NRHM_DEMOGRAPHIC");

			// RNTCP Directorate
			RNTCP_DAILY_RECORDS = properties.getProperty("RNTCP_DAILY_RECORDS");
			RNTCP_WEEKLY_RECORDS = properties
					.getProperty("RNTCP_WEEKLY_RECORDS");
			RNTCP_MONTHLY_RECORDS = properties
					.getProperty("RNTCP_MONTHLY_RECORDS");
			RNTCP_QUARTERLY_RECORDS = properties
					.getProperty("RNTCP_QUARTERLY_RECORDS");
			RNTCP_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("RNTCP_DAILY_DEMOGRAPHIC_RECORDS");
			RNTCP_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("RNTCP_WEEKLY_DEMOGRAPHIC_RECORDS");
			RNTCP_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("RNTCP_MONTHLY_DEMOGRAPHIC_RECORDS");
			RNTCP_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("RNTCP_QUATERLY_DEMOGRAPHIC_RECORDS");
			RNTCP_GENERAL_TYPE = properties.getProperty("RNTCP_GENERAL_TYPE");
			INSERT_RNTCP_DIRECTORATE = properties
					.getProperty("INSERT_RNTCP_DIRECTORATE");
			INSERT_RNTCP_GENDER_AGEGROUP = properties
					.getProperty("INSERT_RNTCP_GENDER_AGEGROUP");
			UPDATE_RNTCP_DIRECTORATE = properties
					.getProperty("UPDATE_RNTCP_DIRECTORATE");
			RNTCP_INSTITUTION = properties.getProperty("RNTCP_INSTITUTION");
			RNTCP_INSTITUTION_COUNT = properties
					.getProperty("RNTCP_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_RNTCP_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_RNTCP_INSTITUTION_COUNT");
			SELECT_RNTCP_INDICATOR_MASTER = properties
					.getProperty("SELECT_RNTCP_INDICATOR_MASTER");
			SELECT_RNTCP_CLASSIFICATION = properties
					.getProperty("SELECT_RNTCP_CLASSIFICATION");
			RNTCP_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("RNTCP_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			RNTCP_INDICATOR_LIST = properties
					.getProperty("RNTCP_INDICATOR_LIST");
			RNTCP_CLASSIFICATION_LIST = properties
					.getProperty("RNTCP_CLASSIFICATION_LIST");
			IS_RNTCP_DEMOGRAPHIC = properties
					.getProperty("IS_RNTCP_DEMOGRAPHIC");
			RNTCP_DISTRICT_NAME = properties
					.getProperty("RNTCP_DISTRICT_NAME");
			RNTCP_DISTRICT_NAME_COUNT = properties
					.getProperty("RNTCP_DISTRICT_NAME_COUNT");
			RESTRICTED_RNTCP_DISTRICT_NAME_COUNT = properties
					.getProperty("RESTRICTED_RNTCP_DISTRICT_NAME_COUNT");

			// MA Directorate
			MA_DAILY_RECORDS = properties.getProperty("MA_DAILY_RECORDS");
			MA_WEEKLY_RECORDS = properties.getProperty("MA_WEEKLY_RECORDS");
			MA_MONTHLY_RECORDS = properties.getProperty("MA_MONTHLY_RECORDS");
			MA_QUARTERLY_RECORDS = properties
					.getProperty("MA_QUARTERLY_RECORDS");
			MA_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("MA_DAILY_DEMOGRAPHIC_RECORDS");
			MA_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("MA_WEEKLY_DEMOGRAPHIC_RECORDS");
			MA_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("MA_MONTHLY_DEMOGRAPHIC_RECORDS");
			MA_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("MA_QUATERLY_DEMOGRAPHIC_RECORDS");
			MA_YEARLY_RECORDS = properties.getProperty("MA_YEARLY_RECORDS");
			MA_DAILY_RECORDS_COUNT = properties
					.getProperty("MA_DAILY_RECORDS_COUNT");
			MA_WEEKLY_RECORDS_COUNT = properties
					.getProperty("MA_WEEKLY_RECORDS_COUNT");
			MA_MONTHLY_RECORDS_COUNT = properties
					.getProperty("MA_MONTHLY_RECORDS_COUNT");
			MA_QUARTERLY_RECORDS_COUNT = properties
					.getProperty("MA_QUARTERLY_RECORDS_COUNT");
			MA_GENERAL_TYPE = properties.getProperty("MA_GENERAL_TYPE");
			INSERT_MA_DIRECTORATE = properties
					.getProperty("INSERT_MA_DIRECTORATE");
			INSERT_MA_GENDER_AGEGROUP = properties
					.getProperty("INSERT_MA_GENDER_AGEGROUP");
			UPDATE_MA_DIRECTORATE = properties
					.getProperty("UPDATE_MA_DIRECTORATE");
			SELECT_CORPORATION_MUNICIPALITY = properties
					.getProperty("SELECT_CORPORATION_MUNICIPALITY");
			SELECT_MA_CLASSIFICATION = properties
					.getProperty("SELECT_MA_CLASSIFICATION");
			MA_REGION_NAME = properties.getProperty("MA_REGION_NAME");
			MA_REGION_NAME_COUNT = properties
					.getProperty("MA_REGION_NAME_COUNT");
			ACCESS_RESTRICTED_MA_REGION_NAME_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_MA_REGION_NAME_COUNT");
			MA_CORPORATION_INSTITUTION = properties.getProperty("MA_CORPORATION_INSTITUTION");
			MA_REGION_INSTITUTION = properties.getProperty("MA_REGION_INSTITUTION");
			MA_DISTRICT_NAME = properties.getProperty("MA_DISTRICT_NAME");
			MA_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("MA_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			MA_INDICATOR_LIST = properties.getProperty("MA_INDICATOR_LIST");
			MA_CLASSIFICATION_LIST = properties
					.getProperty("MA_CLASSIFICATION_LIST");
			IS_MA_DEMOGRAPHIC = properties.getProperty("IS_MA_DEMOGRAPHIC");

			// DME Directorate
			DME_DAILY_RECORDS = properties.getProperty("DME_DAILY_RECORDS");
			DME_WEEKLY_RECORDS = properties.getProperty("DME_WEEKLY_RECORDS");
			DME_MONTHLY_RECORDS = properties.getProperty("DME_MONTHLY_RECORDS");
			DME_QUARTERLY_RECORDS = properties
					.getProperty("DME_QUARTERLY_RECORDS");
			DME_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DME_DAILY_DEMOGRAPHIC_RECORDS");
			DME_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DME_WEEKLY_DEMOGRAPHIC_RECORDS");
			DME_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DME_MONTHLY_DEMOGRAPHIC_RECORDS");
			DME_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DME_QUATERLY_DEMOGRAPHIC_RECORDS");
			DME_GENERAL_TYPE = properties.getProperty("DME_GENERAL_TYPE");
			INSERT_DME_DIRECTORATE = properties
					.getProperty("INSERT_DME_DIRECTORATE");
			INSERT_DME_GENDER_AGEGROUP = properties
					.getProperty("INSERT_DME_GENDER_AGEGROUP");
			UPDATE_DME_DIRECTORATE = properties
					.getProperty("UPDATE_DME_DIRECTORATE");
			DME_INSTITUTION = properties.getProperty("DME_INSTITUTION");
			DME_INSTITUTION_COUNT = properties
					.getProperty("DME_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_DME_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_DME_INSTITUTION_COUNT");
			DME_INDICATOR_MASTER = properties
					.getProperty("DME_INDICATOR_MASTER");
			DME_CLASSIFICATION = properties.getProperty("DME_CLASSIFICATION");
			DME_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("DME_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			DME_INDICATOR_LIST = properties.getProperty("DME_INDICATOR_LIST");
			DME_CLASSIFICATION_LIST = properties
					.getProperty("DME_CLASSIFICATION_LIST");
			IS_DME_DEMOGRAPHIC = properties.getProperty("IS_DME_DEMOGRAPHIC");
			DME_DISTRICT_NAME = properties.getProperty("DME_DISTRICT_NAME");
			DME_ACCESS_RESTRICTED_DISTRICT_NAME_COUNT = properties.getProperty("DME_ACCESS_RESTRICTED_DISTRICT_NAME_COUNT");

			// DRMGR Directorate
			DRMGR_DAILY_RECORDS = properties.getProperty("DRMGR_DAILY_RECORDS");
			DRMGR_WEEKLY_RECORDS = properties
					.getProperty("DRMGR_WEEKLY_RECORDS");
			DRMGR_MONTHLY_RECORDS = properties
					.getProperty("DRMGR_MONTHLY_RECORDS");
			DRMGR_QUARTERLY_RECORDS = properties
					.getProperty("DRMGR_QUARTERLY_RECORDS");
			DRMGR_YEARLY_RECORDS = properties
					.getProperty("DRMGR_YEARLY_RECORDS");
			DRMGR_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DRMGR_DAILY_DEMOGRAPHIC_RECORDS");
			DRMGR_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DRMGR_WEEKLY_DEMOGRAPHIC_RECORDS");
			DRMGR_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DRMGR_MONTHLY_DEMOGRAPHIC_RECORDS");
			DRMGR_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DRMGR_QUATERLY_DEMOGRAPHIC_RECORDS");
			DRMGR_GENERAL_TYPE = properties.getProperty("DRMGR_GENERAL_TYPE");
			INSERT_DRMGR_DIRECTORATE = properties
					.getProperty("INSERT_DRMGR_DIRECTORATE");
			INSERT_DRMGR_GENDER_AGEGROUP = properties
					.getProperty("INSERT_DRMGR_GENDER_AGEGROUP");
			UPDATE_DRMGR_DIRECTORATE = properties
					.getProperty("UPDATE_DRMGR_DIRECTORATE");
			DRMGR_INSTITUTION = properties.getProperty("DRMGR_INSTITUTION");
			DRMGR_INSTITUTION_COUNT = properties
					.getProperty("DRMGR_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_DRMGR_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_DRMGR_INSTITUTION_COUNT");
			DRMGR_INDICATOR_MASTER = properties
					.getProperty("DRMGR_INDICATOR_MASTER");
			DRMGR_CLASSIFICATION = properties
					.getProperty("DRMGR_CLASSIFICATION");
			DRMGR_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("DRMGR_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			DRMGR_INDICATOR_LIST = properties
					.getProperty("DRMGR_INDICATOR_LIST");
			DRMGR_CLASSIFICATION_LIST = properties
					.getProperty("DRMGR_CLASSIFICATION_LIST");
			IS_DRMGR_DEMOGRAPHIC = properties
					.getProperty("IS_DRMGR_DEMOGRAPHIC");
			DRMGR_INSTITUTIONTYPELIST = properties
					.getProperty("DRMGR_INSTITUTIONTYPELIST");
			DRMGR_INSTITUTIONSPECIALITYLIST = properties
					.getProperty("DRMGR_INSTITUTIONSPECIALITYLIST");
			DRMGR_INSTITUTIONLIST = properties
					.getProperty("DRMGR_INSTITUTIONLIST");
			DRMGR_DEPARTMENT = properties
					.getProperty("DRMGR_DEPARTMENT");
			ACCESS_RESTRICTED_DRMGR_DEPARTMENT_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_DRMGR_DEPARTMENT_COUNT");
			DRMGR_DEPARTMENT_COUNT = properties.getProperty("DRMGR_DEPARTMENT_COUNT");
			

			// DFW Directorate
			DFW_DAILY_RECORDS = properties.getProperty("DFW_DAILY_RECORDS");
			DFW_WEEKLY_RECORDS = properties.getProperty("DFW_WEEKLY_RECORDS");
			DFW_MONTHLY_RECORDS = properties.getProperty("DFW_MONTHLY_RECORDS");
			DFW_QUARTERLY_RECORDS = properties
					.getProperty("DFW_QUARTERLY_RECORDS");
			DFW_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DFW_DAILY_DEMOGRAPHIC_RECORDS");
			DFW_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DFW_WEEKLY_DEMOGRAPHIC_RECORDS");
			DFW_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DFW_MONTHLY_DEMOGRAPHIC_RECORDS");
			DFW_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DFW_QUATERLY_DEMOGRAPHIC_RECORDS");
			DFW_GENERAL_TYPE = properties.getProperty("DFW_GENERAL_TYPE");
			INSERT_DFW_DIRECTORATE = properties
					.getProperty("INSERT_DFW_DIRECTORATE");
			INSERT_DFW_GENDER_AGEGROUP = properties
					.getProperty("INSERT_DFW_GENDER_AGEGROUP");
			UPDATE_DFW_DIRECTORATE = properties
					.getProperty("UPDATE_DFW_DIRECTORATE");
			DFW_INSTITUTION = properties.getProperty("DFW_INSTITUTION");
			DFW_INSTITUTION_TYPE = properties
					.getProperty("DFW_INSTITUTION_TYPE");
			DFW_INSTITUTION_COUNT = properties
					.getProperty("DFW_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_DFW_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_DFW_INSTITUTION_COUNT");
			DFW_INDICATOR_MASTER = properties
					.getProperty("DFW_INDICATOR_MASTER");
			DFW_CLASSIFICATION = properties.getProperty("DFW_CLASSIFICATION");
			DFW_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("DFW_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			DFW_INDICATOR_LIST = properties.getProperty("DFW_INDICATOR_LIST");
			DFW_CLASSIFICATION_LIST = properties
					.getProperty("DFW_CLASSIFICATION_LIST");
			IS_DFW_DEMOGRAPHIC = properties.getProperty("IS_DFW_DEMOGRAPHIC");
			DFW_POST_LIST = properties.getProperty("DFW_POST_LIST");
			INSERT_EMPLOYEE_DETAILS = properties
					.getProperty("INSERT_EMPLOYEE_DETAILS");
			SELECT_EMPLOYEE_DETAILS = properties
					.getProperty("SELECT_EMPLOYEE_DETAILS");
			DFW_EMPLOYEE_LIST = properties.getProperty("DFW_EMPLOYEE_LIST");
			INSERT_DFW_EMPLOYEE_QUALIFICATION = properties
					.getProperty("INSERT_DFW_EMPLOYEE_QUALIFICATION");
			INSERT_DFW_EMPLOYEE_LEAVE = properties
					.getProperty("INSERT_DFW_EMPLOYEE_LEAVE");
			INSERT_DFW_EMPLOYEE_PROMOTION = properties
					.getProperty("INSERT_DFW_EMPLOYEE_PROMOTION");
			DFW_EMPLOYEE_TRANSFER= properties
					.getProperty("DFW_EMPLOYEE_TRANSFER");
			DFW_EMPLOYEE_EXISTS=properties
					.getProperty("DFW_EMPLOYEE_EXISTS");
			SELECT_EMPLOYEE_QUALIFICATION_DETAILS = properties
					.getProperty("SELECT_EMPLOYEE_QUALIFICATION_DETAILS") ;
			SELECT_EMPLOYEE_PROMOTION_DETAILS = properties
					.getProperty("SELECT_EMPLOYEE_PROMOTION_DETAILS") ;
			SELECT_EMPLOYEE_LEAVE_DETAILS = properties
					.getProperty("SELECT_EMPLOYEE_LEAVE_DETAILS") ;
			UPDATE_EMPLOYEE_DETAILS = properties
					.getProperty("UPDATE_EMPLOYEE_DETAILS") ;
			UPDATE_DFW_EMPLOYEE_QUALIFICATION = properties
					.getProperty("UPDATE_DFW_EMPLOYEE_QUALIFICATION") ;
			UPDATE_DFW_EMPLOYEE_LEAVE = properties
					.getProperty("UPDATE_DFW_EMPLOYEE_LEAVE") ;
			INSERT_DFW_EMPLOYEE_HISTORY = properties
					.getProperty("INSERT_DFW_EMPLOYEE_HISTORY") ;
			DFW_EMPLOYEE_LEAVE_EXISTS=properties
					.getProperty("DFW_EMPLOYEE_LEAVE_EXISTS") ;
			DFW_TRANSFER_INSTITUTION=properties
					.getProperty("DFW_TRANSFER_INSTITUTION") ;
			DFW_EMPLOYEE_TRANSFER_LIST=properties
					.getProperty("DFW_EMPLOYEE_TRANSFER_LIST") ;
			DFW_TRANSFER_EMPLOYEE_DETAILS=properties
					.getProperty("DFW_TRANSFER_EMPLOYEE_DETAILS") ;
			DFW_EMPLOYEE_TRANSFER_ACCEPT=properties
			.getProperty("DFW_EMPLOYEE_TRANSFER_ACCEPT") ;
			DFW_EMPLOYEE_PROMOTION_TYPE=properties
			.getProperty("DFW_EMPLOYEE_PROMOTION_TYPE") ;
		    UPDATE_DFW_EMPLOYEE_PROMOTION=properties
			.getProperty("UPDATE_DFW_EMPLOYEE_PROMOTION") ;

			// SHTO Directorate
			SHTO_DAILY_RECORDS = properties.getProperty("SHTO_DAILY_RECORDS");
			SHTO_WEEKLY_RECORDS = properties.getProperty("SHTO_WEEKLY_RECORDS");
			SHTO_MONTHLY_RECORDS = properties
					.getProperty("SHTO_MONTHLY_RECORDS");
			SHTO_QUARTERLY_RECORDS = properties
					.getProperty("SHTO_QUARTERLY_RECORDS");
			SHTO_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("SHTO_DAILY_DEMOGRAPHIC_RECORDS");
			SHTO_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("SHTO_WEEKLY_DEMOGRAPHIC_RECORDS");
			SHTO_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("SHTO_MONTHLY_DEMOGRAPHIC_RECORDS");
			SHTO_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("SHTO_QUATERLY_DEMOGRAPHIC_RECORDS");
			SHTO_GENERAL_TYPE = properties.getProperty("SHTO_GENERAL_TYPE");
			INSERT_SHTO_DIRECTORATE = properties
					.getProperty("INSERT_SHTO_DIRECTORATE");
			INSERT_SHTO_GENDER_AGEGROUP = properties
					.getProperty("INSERT_SHTO_GENDER_AGEGROUP");
			UPDATE_SHTO_DIRECTORATE = properties
					.getProperty("UPDATE_SHTO_DIRECTORATE");
			SHTO_INSTITUTION = properties.getProperty("SHTO_INSTITUTION");
			SHTO_INSTITUTION_COUNT = properties
					.getProperty("SHTO_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_SHTO_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_SHTO_INSTITUTION_COUNT");
			SHTO_INDICATOR_MASTER = properties
					.getProperty("SHTO_INDICATOR_MASTER");
			SHTO_CLASSIFICATION = properties.getProperty("SHTO_CLASSIFICATION");
			SHTO_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("SHTO_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			SHTO_INDICATOR_LIST = properties.getProperty("SHTO_INDICATOR_LIST");
			SHTO_CLASSIFICATION_LIST = properties
					.getProperty("SHTO_CLASSIFICATION_LIST");
			IS_SHTO_DEMOGRAPHIC = properties.getProperty("IS_SHTO_DEMOGRAPHIC");
			SHTO_WORKSHOP_NAME = properties.getProperty("SHTO_WORKSHOP_NAME");
			SHTO_WORKSHOP_NAME_COUNT = properties.getProperty("SHTO_WORKSHOP_NAME_COUNT");
			SHTO_ACCESS_RESTRICTED_WORKSHOP_NAME_COUNT = properties
					.getProperty("SHTO_ACCESS_RESTRICTED_WORKSHOP_NAME_COUNT");
			SHTO_MOBILE_WORKSHOP = properties.getProperty("SHTO_MOBILE_WORKSHOP");
			SHTO_MOBILE_WORKSHOP_COUNT = properties
					.getProperty("SHTO_MOBILE_WORKSHOP_COUNT");
			ACCESS_RESTRICTED_SHTO_MOBILE_WORKSHOP_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_SHTO_MOBILE_WORKSHOP_COUNT");

			// CC Directorate
			COC_DAILY_RECORDS = properties.getProperty("COC_DAILY_RECORDS");
			COC_WEEKLY_RECORDS = properties.getProperty("COC_WEEKLY_RECORDS");
			COC_MONTHLY_RECORDS = properties.getProperty("COC_MONTHLY_RECORDS");
			COC_QUARTERLY_RECORDS = properties
					.getProperty("COC_QUARTERLY_RECORDS");
			COC_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("COC_DAILY_DEMOGRAPHIC_RECORDS");
			COC_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("COC_WEEKLY_DEMOGRAPHIC_RECORDS");
			COC_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("COC_MONTHLY_DEMOGRAPHIC_RECORDS");
			COC_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("COC_QUATERLY_DEMOGRAPHIC_RECORDS");
			COC_GENERAL_TYPE = properties.getProperty("COC_GENERAL_TYPE");
			INSERT_COC_DIRECTORATE = properties
					.getProperty("INSERT_COC_DIRECTORATE");
			INSERT_COC_GENDER_AGEGROUP = properties
					.getProperty("INSERT_COC_GENDER_AGEGROUP");
			UPDATE_COC_DIRECTORATE = properties
					.getProperty("UPDATE_COC_DIRECTORATE");
			COC_INSTITUTION = properties.getProperty("COC_INSTITUTION");
			COC_INSTITUTION_COUNT = properties
					.getProperty("COC_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_COC_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_COC_INSTITUTION_COUNT");
			COC_INDICATOR_MASTER = properties
					.getProperty("COC_INDICATOR_MASTER");
			COC_CLASSIFICATION = properties.getProperty("COC_CLASSIFICATION");
			COC_REGION_NAME = properties.getProperty("COC_REGION_NAME");
			COC_REGION_NAME_COUNT = properties
					.getProperty("COC_REGION_NAME_COUNT");
			ACCESS_RESTRICTED_COC_REGION_NAME_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_COC_REGION_NAME_COUNT");
			COC_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("COC_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			COC_INDICATOR_LIST = properties.getProperty("COC_INDICATOR_LIST");
			COC_CLASSIFICATION_LIST = properties
					.getProperty("COC_CLASSIFICATION_LIST");
			IS_COC_DEMOGRAPHIC = properties.getProperty("IS_COC_DEMOGRAPHIC");

			// SBCS Directorate
			SBCS_DAILY_RECORDS = properties.getProperty("SBCS_DAILY_RECORDS");
			SBCS_WEEKLY_RECORDS = properties.getProperty("SBCS_WEEKLY_RECORDS");
			SBCS_MONTHLY_RECORDS = properties
					.getProperty("SBCS_MONTHLY_RECORDS");
			SBCS_QUARTERLY_RECORDS = properties
					.getProperty("SBCS_QUARTERLY_RECORDS");
			SBCS_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("SBCS_DAILY_DEMOGRAPHIC_RECORDS");
			SBCS_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("SBCS_WEEKLY_DEMOGRAPHIC_RECORDS");
			SBCS_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("SBCS_MONTHLY_DEMOGRAPHIC_RECORDS");
			SBCS_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("SBCS_QUATERLY_DEMOGRAPHIC_RECORDS");
			SBCS_GENERAL_TYPE = properties.getProperty("SBCS_GENERAL_TYPE");
			INSERT_SBCS_DIRECTORATE = properties
					.getProperty("INSERT_SBCS_DIRECTORATE");
			INSERT_SBCS_GENDER_AGEGROUP = properties
					.getProperty("INSERT_SBCS_GENDER_AGEGROUP");
			UPDATE_SBCS_DIRECTORATE = properties
					.getProperty("UPDATE_SBCS_DIRECTORATE");
			SBCS_INSTITUTION = properties.getProperty("SBCS_INSTITUTION");
			SBCS_INSTITUTION_COUNT = properties
					.getProperty("SBCS_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_SBCS_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_SBCS_INSTITUTION_COUNT");
			SBCS_INDICATOR_MASTER = properties
					.getProperty("SBCS_INDICATOR_MASTER");
			SBCS_CLASSIFICATION = properties.getProperty("SBCS_CLASSIFICATION");
			SBCS_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("SBCS_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			SBCS_INDICATOR_LIST = properties.getProperty("SBCS_INDICATOR_LIST");
			SBCS_CLASSIFICATION_LIST = properties
					.getProperty("SBCS_CLASSIFICATION_LIST");
			IS_SBCS_DEMOGRAPHIC = properties.getProperty("IS_SBCS_DEMOGRAPHIC");

			// DCA Directorate
			DCA_DAILY_RECORDS = properties.getProperty("DCA_DAILY_RECORDS");
			DCA_WEEKLY_RECORDS = properties.getProperty("DCA_WEEKLY_RECORDS");
			DCA_MONTHLY_RECORDS = properties.getProperty("DCA_MONTHLY_RECORDS");
			DCA_QUARTERLY_RECORDS = properties
					.getProperty("DCA_QUARTERLY_RECORDS");
			DCA_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DCA_DAILY_DEMOGRAPHIC_RECORDS");
			DCA_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DCA_WEEKLY_DEMOGRAPHIC_RECORDS");
			DCA_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DCA_MONTHLY_DEMOGRAPHIC_RECORDS");
			DCA_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DCA_QUATERLY_DEMOGRAPHIC_RECORDS");
			DCA_GENERAL_TYPE = properties.getProperty("DCA_GENERAL_TYPE");
			INSERT_DCA_DIRECTORATE = properties
					.getProperty("INSERT_DCA_DIRECTORATE");
			INSERT_DCA_GENDER_AGEGROUP = properties
					.getProperty("INSERT_DCA_GENDER_AGEGROUP");
			UPDATE_DCA_DIRECTORATE = properties
					.getProperty("UPDATE_DCA_DIRECTORATE");
			DCA_INSTITUTION = properties.getProperty("DCA_INSTITUTION");
			DCA_INSTITUTION_COUNT = properties
					.getProperty("DCA_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_DCA_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_DCA_INSTITUTION_COUNT");
			DCA_INDICATOR_MASTER = properties
					.getProperty("DCA_INDICATOR_MASTER");
			DCA_CLASSIFICATION = properties.getProperty("DCA_CLASSIFICATION");
			DCA_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("DCA_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			DCA_INDICATOR_LIST = properties.getProperty("DCA_INDICATOR_LIST");
			DCA_CLASSIFICATION_LIST = properties
					.getProperty("DCA_CLASSIFICATION_LIST");
			IS_DCA_DEMOGRAPHIC = properties.getProperty("IS_DCA_DEMOGRAPHIC");
			DCA_HUD_NAME = properties.getProperty("DCA_HUD_NAME");
			BLOCK_NAME = properties.getProperty("BLOCK_NAME");
			DCA_INSTITUTION_NAME = properties.getProperty("DCA_INSTITUTION_NAME");

			// MRB Directorate
			MRB_DAILY_RECORDS = properties.getProperty("MRB_DAILY_RECORDS");
			MRB_WEEKLY_RECORDS = properties.getProperty("MRB_WEEKLY_RECORDS");
			MRB_MONTHLY_RECORDS = properties.getProperty("MRB_MONTHLY_RECORDS");
			MRB_QUARTERLY_RECORDS = properties
					.getProperty("MRB_QUARTERLY_RECORDS");
			MRB_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("MRB_DAILY_DEMOGRAPHIC_RECORDS");
			MRB_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("MRB_WEEKLY_DEMOGRAPHIC_RECORDS");
			MRB_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("MRB_MONTHLY_DEMOGRAPHIC_RECORDS");
			MRB_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("MRB_QUATERLY_DEMOGRAPHIC_RECORDS");
			MRB_YEARLY_RECORDS = properties.getProperty("MRB_YEARLY_RECORDS");
			MRB_GENERAL_TYPE = properties.getProperty("MRB_GENERAL_TYPE");
			INSERT_MRB_DIRECTORATE = properties
					.getProperty("INSERT_MRB_DIRECTORATE");
			INSERT_MRB_GENDER_AGEGROUP = properties
					.getProperty("INSERT_MRB_GENDER_AGEGROUP");
			UPDATE_MRB_DIRECTORATE = properties
					.getProperty("UPDATE_MRB_DIRECTORATE");
			MRB_INSTITUTION = properties.getProperty("MRB_INSTITUTION");
			MRB_INSTITUTION_COUNT = properties
					.getProperty("MRB_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_MRB_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_MRB_INSTITUTION_COUNT");
			MRB_INDICATOR_MASTER = properties
					.getProperty("MRB_INDICATOR_MASTER");
			MRB_CLASSIFICATION = properties.getProperty("MRB_CLASSIFICATION");
			MRB_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("MRB_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			MRB_INDICATOR_LIST = properties.getProperty("MRB_INDICATOR_LIST");
			MRB_CLASSIFICATION_LIST = properties
					.getProperty("MRB_CLASSIFICATION_LIST");
			IS_MRB_DEMOGRAPHIC = properties.getProperty("IS_MRB_DEMOGRAPHIC");

			// DFS Directorate
			DFS_DAILY_RECORDS = properties.getProperty("DFS_DAILY_RECORDS");
			DFS_WEEKLY_RECORDS = properties.getProperty("DFS_WEEKLY_RECORDS");
			DFS_MONTHLY_RECORDS = properties.getProperty("DFS_MONTHLY_RECORDS");
			DFS_QUARTERLY_RECORDS = properties
					.getProperty("DFS_QUARTERLY_RECORDS");
			DFS_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DFS_DAILY_DEMOGRAPHIC_RECORDS");
			DFS_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DFS_WEEKLY_DEMOGRAPHIC_RECORDS");
			DFS_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DFS_MONTHLY_DEMOGRAPHIC_RECORDS");
			DFS_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DFS_QUATERLY_DEMOGRAPHIC_RECORDS");
			DFS_GENERAL_TYPE = properties.getProperty("DFS_GENERAL_TYPE");
			INSERT_DFS_DIRECTORATE = properties
					.getProperty("INSERT_DFS_DIRECTORATE");
			INSERT_DFS_GENDER_AGEGROUP = properties
					.getProperty("INSERT_DFS_GENDER_AGEGROUP");
			UPDATE_DFS_DIRECTORATE = properties
					.getProperty("UPDATE_DFS_DIRECTORATE");
			DFS_INSTITUTION = properties.getProperty("DFS_INSTITUTION");
			DFS_INSTITUTION_COUNT = properties
					.getProperty("DFS_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_DFS_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_DFS_INSTITUTION_COUNT");
			DFS_INDICATOR_MASTER = properties
					.getProperty("DFS_INDICATOR_MASTER");
			DFS_CLASSIFICATION = properties.getProperty("DFS_CLASSIFICATION");
			DFS_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("DFS_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			DFS_INDICATOR_LIST = properties.getProperty("DFS_INDICATOR_LIST");
			DFS_CLASSIFICATION_LIST = properties
					.getProperty("DFS_CLASSIFICATION_LIST");
			IS_DFS_DEMOGRAPHIC = properties.getProperty("IS_DFS_DEMOGRAPHIC");
			DFS_LAB = properties.getProperty("DFS_LAB");
			DFS_LAB_DISTRICT = properties.getProperty("DFS_LAB_DISTRICT");
			DFS_AREA_TYPE = properties.getProperty("DFS_AREA_TYPE");
			DFS_AREA = properties.getProperty("DFS_AREA");
			DFS_AREACODE = properties.getProperty("DFS_AREACODE");

			// CMCHIS Directorate
			CMCHIS_DAILY_RECORDS = properties
					.getProperty("CMCHIS_DAILY_RECORDS");
			CMCHIS_WEEKLY_RECORDS = properties
					.getProperty("CMCHIS_WEEKLY_RECORDS");
			CMCHIS_MONTHLY_RECORDS = properties
					.getProperty("CMCHIS_MONTHLY_RECORDS");
			CMCHIS_QUARTERLY_RECORDS = properties
					.getProperty("CMCHIS_QUARTERLY_RECORDS");
			CMCHIS_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("CMCHIS_DAILY_DEMOGRAPHIC_RECORDS");
			CMCHIS_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("CMCHIS_WEEKLY_DEMOGRAPHIC_RECORDS");
			CMCHIS_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("CMCHIS_MONTHLY_DEMOGRAPHIC_RECORDS");
			CMCHIS_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("CMCHIS_QUATERLY_DEMOGRAPHIC_RECORDS");
			CMCHIS_GENERAL_TYPE = properties.getProperty("CMCHIS_GENERAL_TYPE");
			INSERT_CMCHIS_DIRECTORATE = properties
					.getProperty("INSERT_CMCHIS_DIRECTORATE");
			INSERT_CMCHIS_GENDER_AGEGROUP = properties
					.getProperty("INSERT_CMCHIS_GENDER_AGEGROUP");
			UPDATE_CMCHIS_DIRECTORATE = properties
					.getProperty("UPDATE_CMCHIS_DIRECTORATE");
			CMCHIS_INSTITUTION = properties.getProperty("CMCHIS_INSTITUTION");
			CMCHIS_INSTITUTION_COUNT = properties
					.getProperty("CMCHIS_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_CMCHIS_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_CMCHIS_INSTITUTION_COUNT");
			CMCHIS_INDICATOR_MASTER = properties
					.getProperty("CMCHIS_INDICATOR_MASTER");
			CMCHIS_CLASSIFICATION = properties
					.getProperty("CMCHIS_CLASSIFICATION");
			CMCHIS_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("CMCHIS_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			CMCHIS_INDICATOR_LIST = properties
					.getProperty("CMCHIS_INDICATOR_LIST");
			CMCHIS_CLASSIFICATION_LIST = properties
					.getProperty("CMCHIS_CLASSIFICATION_LIST");
			IS_CMCHIS_DEMOGRAPHIC = properties
					.getProperty("IS_CMCHIS_DEMOGRAPHIC");
			CMCHIS_WEBSERVICE_INSERT_PREAUTH = properties
					.getProperty("CMCHIS_WEBSERVICE_INSERT_PREAUTH");
			CMCHIS_WEBSERVICE_INSERT_PAYMENT = properties
					.getProperty("CMCHIS_WEBSERVICE_INSERT_PAYMENT");
			CMCHIS_WEBSERVICE_INSERT_DC = properties
					.getProperty("CMCHIS_WEBSERVICE_INSERT_DC");
			CMCHIS_WEBSERVICE_INSERT_CLAIMS = properties
					.getProperty("CMCHIS_WEBSERVICE_INSERT_CLAIMS");
			CMCHIS_WEBSERVICE_INSERT_HOSPITAL = properties
					.getProperty("CMCHIS_WEBSERVICE_INSERT_HOSPITAL");
			CMCHIS_WEBSERVICE_DISABLE_HOSPITAL = properties
					.getProperty("CMCHIS_WEBSERVICE_DISABLE_HOSPITAL");

			// DIM Directorate
			DIM_DAILY_RECORDS = properties.getProperty("DIM_DAILY_RECORDS");
			DIM_WEEKLY_RECORDS = properties.getProperty("DIM_WEEKLY_RECORDS");
			DIM_MONTHLY_RECORDS = properties.getProperty("DIM_MONTHLY_RECORDS");
			DIM_QUARTERLY_RECORDS = properties
					.getProperty("DIM_QUARTERLY_RECORDS");
			DIM_YEARLY_RECORDS = properties.getProperty("DIM_YEARLY_RECORDS");
			DIM_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DIM_DAILY_DEMOGRAPHIC_RECORDS");
			DIM_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DIM_WEEKLY_DEMOGRAPHIC_RECORDS");
			DIM_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DIM_MONTHLY_DEMOGRAPHIC_RECORDS");
			DIM_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("DIM_QUATERLY_DEMOGRAPHIC_RECORDS");
			DIM_GENERAL_TYPE = properties.getProperty("DIM_GENERAL_TYPE");
			INSERT_DIM_DIRECTORATE = properties
					.getProperty("INSERT_DIM_DIRECTORATE");
			INSERT_DIM_GENDER_AGEGROUP = properties
					.getProperty("INSERT_DIM_GENDER_AGEGROUP");
			UPDATE_DIM_DIRECTORATE = properties
					.getProperty("UPDATE_DIM_DIRECTORATE");
			DIM_INSTITUTION = properties.getProperty("DIM_INSTITUTION");
			DIM_INSTITUTION_COUNT = properties
					.getProperty("DIM_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_DIM_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_DIM_INSTITUTION_COUNT");
			DIM_INDICATOR_MASTER = properties
					.getProperty("DIM_INDICATOR_MASTER");
			DIM_CLASSIFICATION = properties.getProperty("DIM_CLASSIFICATION");
			DIM_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("DIM_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			DIM_INDICATOR_LIST = properties.getProperty("DIM_INDICATOR_LIST");
			DIM_INDICATOR_LIST1 = properties.getProperty("DIM_INDICATOR_LIST1");
			DIM_CLASSIFICATION_LIST = properties
					.getProperty("DIM_CLASSIFICATION_LIST");
			IS_DIM_DEMOGRAPHIC = properties.getProperty("IS_DIM_DEMOGRAPHIC");
			DIM_INSTITUTION_TYPE = properties
					.getProperty("DIM_INSTITUTION_TYPE");
			DIM_SYSTEM = properties.getProperty("DIM_SYSTEM");

			// NLEP Directorate
			NLEP_DAILY_RECORDS = properties.getProperty("NLEP_DAILY_RECORDS");
			NLEP_WEEKLY_RECORDS = properties.getProperty("NLEP_WEEKLY_RECORDS");
			NLEP_MONTHLY_RECORDS = properties
					.getProperty("NLEP_MONTHLY_RECORDS");
			NLEP_QUARTERLY_RECORDS = properties
					.getProperty("NLEP_QUARTERLY_RECORDS");
			NLEP_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("NLEP_DAILY_DEMOGRAPHIC_RECORDS");
			NLEP_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("NLEP_WEEKLY_DEMOGRAPHIC_RECORDS");
			NLEP_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("NLEP_MONTHLY_DEMOGRAPHIC_RECORDS");
			NLEP_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("NLEP_QUATERLY_DEMOGRAPHIC_RECORDS");
			NLEP_GENERAL_TYPE = properties.getProperty("NLEP_GENERAL_TYPE");
			INSERT_NLEP_DIRECTORATE = properties
					.getProperty("INSERT_NLEP_DIRECTORATE");
			INSERT_NLEP_GENDER_AGEGROUP = properties
					.getProperty("INSERT_NLEP_GENDER_AGEGROUP");
			UPDATE_NLEP_DIRECTORATE = properties
					.getProperty("UPDATE_NLEP_DIRECTORATE");
			NLEP_INSTITUTION = properties.getProperty("NLEP_INSTITUTION");
			NLEP_INSTITUTION_COUNT = properties
					.getProperty("NLEP_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_NLEP_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_NLEP_INSTITUTION_COUNT");
			NLEP_INDICATOR_MASTER = properties
					.getProperty("NLEP_INDICATOR_MASTER");
			NLEP_CLASSIFICATION = properties.getProperty("NLEP_CLASSIFICATION");
			NLEP_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("NLEP_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			NLEP_INDICATOR_LIST = properties.getProperty("NLEP_INDICATOR_LIST");
			NLEP_CLASSIFICATION_LIST = properties
					.getProperty("NLEP_CLASSIFICATION_LIST");
			IS_NLEP_DEMOGRAPHIC = properties.getProperty("IS_NLEP_DEMOGRAPHIC");
			NLEP_YEARLY_RECORDS = properties.getProperty("NLEP_YEARLY_RECORDS");

			// SBHI Directorate
			SBHI_DAILY_RECORDS = properties.getProperty("SBHI_DAILY_RECORDS");
			SBHI_WEEKLY_RECORDS = properties.getProperty("SBHI_WEEKLY_RECORDS");
			SBHI_MONTHLY_RECORDS = properties
					.getProperty("SBHI_MONTHLY_RECORDS");
			SBHI_QUARTERLY_RECORDS = properties
					.getProperty("SBHI_QUARTERLY_RECORDS");
			SBHI_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("SBHI_DAILY_DEMOGRAPHIC_RECORDS");
			SBHI_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("SBHI_WEEKLY_DEMOGRAPHIC_RECORDS");
			SBHI_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("SBHI_MONTHLY_DEMOGRAPHIC_RECORDS");
			SBHI_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("SBHI_QUATERLY_DEMOGRAPHIC_RECORDS");
			SBHI_GENERAL_TYPE = properties.getProperty("SBHI_GENERAL_TYPE");
			INSERT_SBHI_DIRECTORATE = properties
					.getProperty("INSERT_SBHI_DIRECTORATE");
			INSERT_SBHI_GENDER_AGEGROUP = properties
					.getProperty("INSERT_SBHI_GENDER_AGEGROUP");
			UPDATE_SBHI_DIRECTORATE = properties
					.getProperty("UPDATE_SBHI_DIRECTORATE");
			SBHI_INSTITUTION = properties.getProperty("SBHI_INSTITUTION");
			SBHI_INSTITUTION_COUNT = properties
					.getProperty("SBHI_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_SBHI_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_SBHI_INSTITUTION_COUNT");
			SBHI_INDICATOR_MASTER = properties
					.getProperty("SBHI_INDICATOR_MASTER");
			SBHI_CLASSIFICATION = properties.getProperty("SBHI_CLASSIFICATION");
			SBHI_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("SBHI_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			SBHI_INDICATOR_LIST = properties.getProperty("SBHI_INDICATOR_LIST");
			SBHI_CLASSIFICATION_LIST = properties
					.getProperty("SBHI_CLASSIFICATION_LIST");
			IS_SBHI_DEMOGRAPHIC = properties.getProperty("IS_SBHI_DEMOGRAPHIC");

			// ESI Directorate
			ESI_DAILY_RECORDS = properties.getProperty("ESI_DAILY_RECORDS");
			ESI_WEEKLY_RECORDS = properties.getProperty("ESI_WEEKLY_RECORDS");
			ESI_MONTHLY_RECORDS = properties.getProperty("ESI_MONTHLY_RECORDS");
			ESI_QUARTERLY_RECORDS = properties
					.getProperty("ESI_QUARTERLY_RECORDS");
			ESI_DAILY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("ESI_DAILY_DEMOGRAPHIC_RECORDS");
			ESI_WEEKLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("ESI_WEEKLY_DEMOGRAPHIC_RECORDS");
			ESI_MONTHLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("ESI_MONTHLY_DEMOGRAPHIC_RECORDS");
			ESI_QUATERLY_DEMOGRAPHIC_RECORDS = properties
					.getProperty("ESI_QUATERLY_DEMOGRAPHIC_RECORDS");
			ESI_GENERAL_TYPE = properties.getProperty("ESI_GENERAL_TYPE");
			INSERT_ESI_DIRECTORATE = properties
					.getProperty("INSERT_ESI_DIRECTORATE");
			INSERT_ESI_GENDER_AGEGROUP = properties
					.getProperty("INSERT_ESI_GENDER_AGEGROUP");
			UPDATE_ESI_DIRECTORATE = properties
					.getProperty("UPDATE_ESI_DIRECTORATE");
			ESI_INSTITUTION = properties.getProperty("ESI_INSTITUTION");
			ESI_INSTITUTION_COUNT = properties
					.getProperty("ESI_INSTITUTION_COUNT");
			ACCESS_RESTRICTED_ESI_INSTITUTION_COUNT = properties
					.getProperty("ACCESS_RESTRICTED_ESI_INSTITUTION_COUNT");
			ESI_INDICATOR_MASTER = properties
					.getProperty("ESI_INDICATOR_MASTER");
			ESI_CLASSIFICATION = properties.getProperty("ESI_CLASSIFICATION");
			ESI_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties
					.getProperty("ESI_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			ESI_INDICATOR_LIST = properties.getProperty("ESI_INDICATOR_LIST");
			ESI_CLASSIFICATION_LIST = properties
					.getProperty("ESI_CLASSIFICATION_LIST");
			IS_ESI_DEMOGRAPHIC = properties.getProperty("IS_ESI_DEMOGRAPHIC");

			//Health Secretary Dashboard
			HSDASHBOARD_SEARCHOPTIONS = properties.getProperty("HSDASHBOARD_SEARCHOPTIONS");			
			YEAR_LIST = properties.getProperty("YEAR_LIST");
			MONTH_LIST = properties.getProperty("MONTH_LIST");
			DISTRICT_LIST = properties.getProperty("DISTRICT_LIST");
			INSTITUTION_LIST = properties.getProperty("INSTITUTION_LIST");
			CHILDINDDETAILSLIST = properties.getProperty("CHILDINDDETAILSLIST");			
			FOURPARAMDERIVEDINDDETAILSLIST =  properties.getProperty("FOURPARAMDERIVEDINDDETAILSLIST");
			FOURPARAMDIRECTINDDETAILSLIST =  properties.getProperty("FOURPARAMDIRECTINDDETAILSLIST");
			FOURPARAMCHILDINDDATA =  properties.getProperty("FOURPARAMCHILDINDDATA");
			YMDIFOURPARAMDIRECTINDDYSFUNCTIONDETAILSLIST=properties.getProperty("YMDIFOURPARAMDIRECTINDDYSFUNCTIONDETAILSLIST");
			YDMTHREEPARAMDERIVEDINDDETAILSLIST = properties.getProperty("YDMTHREEPARAMDERIVEDINDDETAILSLIST");
			YDMTHREEPARAMDIRECTINDDYSFUNCTIONDETAILSLIST=properties.getProperty("YDMTHREEPARAMDIRECTINDDYSFUNCTIONDETAILSLIST");
			YDMTHREEPARAMDIRECTINDDETAILSLIST =  properties.getProperty("YDMTHREEPARAMDIRECTINDDETAILSLIST");
			YDMTHREEPARAMCHILDINDDATA =  properties.getProperty("YDMTHREEPARAMCHILDINDDATA");			
			YDITHREEPARAMDERIVEDINDDETAILSLIST = properties.getProperty("YDITHREEPARAMDERIVEDINDDETAILSLIST");
			YDITHREEPARAMDIRECTINDDYSFUNCTIONDETAILSLIST = properties.getProperty("YDITHREEPARAMDIRECTINDDYSFUNCTIONDETAILSLIST");
			YMITHREEPARAMDIRECTINDDYSFUNCTIONDETAILSLIST = properties.getProperty("YMITHREEPARAMDIRECTINDDYSFUNCTIONDETAILSLIST");
			YDITHREEPARAMDIRECTINDDETAILSLIST =  properties.getProperty("YDITHREEPARAMDIRECTINDDETAILSLIST");
			YDITHREEPARAMCHILDINDDATA =  properties.getProperty("YDITHREEPARAMCHILDINDDATA");			
			YMITHREEPARAMDERIVEDINDDETAILSLIST = properties.getProperty("YMITHREEPARAMDERIVEDINDDETAILSLIST");
			YMITHREEPARAMDIRECTINDDETAILSLIST =  properties.getProperty("YMITHREEPARAMDIRECTINDDETAILSLIST");
			YMITHREEPARAMCHILDINDDATA =  properties.getProperty("YMITHREEPARAMCHILDINDDATA");			
			ONEPARAMDERIVEDINDDETAILSLIST =  properties.getProperty("ONEPARAMDERIVEDINDDETAILSLIST");
			ONEPARAMDIRECTINDDETAILSLIST =  properties.getProperty("ONEPARAMDIRECTINDDETAILSLIST");
			ONEPARAMCHILDINDDATA =  properties.getProperty("ONEPARAMCHILDINDDATA");
			PROJECTEDVALUES =  properties.getProperty("PROJECTEDVALUES");
			PROJECTEDALLDISTRICTVALUES =  properties.getProperty("PROJECTEDALLDISTRICTVALUES");
			PROJECTEDYEARVALUES =  properties.getProperty("PROJECTEDYEARVALUES");
			PROJECTEDDISTRICTVALUES =  properties.getProperty("PROJECTEDDISTRICTVALUES");
			YMTWOPARAMDERIVEDINDDETAILSLIST =  properties.getProperty("YMTWOPARAMDERIVEDINDDETAILSLIST");
			YMTWOPARAMDIRECTINDDETAILSLIST =  properties.getProperty("YMTWOPARAMDIRECTINDDETAILSLIST");
			YMTWOPARAMCHILDINDDATA =  properties.getProperty("YMTWOPARAMCHILDINDDATA");
			YDTWOPARAMDERIVEDINDDETAILSLIST =  properties.getProperty("YDTWOPARAMDERIVEDINDDETAILSLIST");
			YDTWOPARAMDIRECTINDDETAILSLIST =  properties.getProperty("YDTWOPARAMDIRECTINDDETAILSLIST");
			YDTWOPARAMCHILDINDDATA =  properties.getProperty("YDTWOPARAMCHILDINDDATA");
			YITWOPARAMDERIVEDINDDETAILSLIST =  properties.getProperty("YITWOPARAMDERIVEDINDDETAILSLIST");
			YITWOPARAMDIRECTINDDETAILSLIST =  properties.getProperty("YITWOPARAMDIRECTINDDETAILSLIST");
			YITWOPARAMCHILDINDDATA =  properties.getProperty("YITWOPARAMCHILDINDDATA");
			DIRECT_PROJECTIONS =  properties.getProperty("DIRECT_PROJECTIONS");
			IND_MAX_MONTH_YEAR =  properties.getProperty("IND_MAX_MONTH_YEAR");
			
			//private sector
			PVTSEC_CLASSIFICATION_LIST = properties.getProperty("PVTSEC_CLASSIFICATION_LIST");
			PVTSEC_GENERAL_TYPE = properties.getProperty("PVTSEC_GENERAL_TYPE");
			UPDATE_PVTSEC_DIRECTORATE = properties.getProperty("UPDATE_PVTSEC_DIRECTORATE");
			PVTSEC_GENERAL_CATEGORY_LIST_BY_FREQUENCY = properties.getProperty("PVTSEC_GENERAL_CATEGORY_LIST_BY_FREQUENCY");
			PVTSEC_DAILY_RECORDS = properties.getProperty("PVTSEC_DAILY_RECORDS");
			PVTSEC_WEEKLY_RECORDS = properties.getProperty("PVTSEC_WEEKLY_RECORDS");
			PVTSEC_MONTHLY_RECORDS = properties.getProperty("PVTSEC_MONTHLY_RECORDS");
			PVTSEC_QUARTERLY_RECORDS = properties.getProperty("PVTSEC_QUARTERLY_RECORDS");
			PVTSEC_YEARLY_RECORDS = properties.getProperty("PVTSEC_YEARLY_RECORDS");
			PVTSEC_INDICATOR_LIST = properties.getProperty("PVTSEC_INDICATOR_LIST");
			INSERT_PVTSEC_DIRECTORATE = properties.getProperty("INSERT_PVTSEC_DIRECTORATE");
			
			//Statistics
			INDICATORS_LIST = properties.getProperty("INDICATORS_LIST");
			INDICATOR_STATISTICS_LIST = properties.getProperty("INDICATOR_STATISTICS_LIST");
			INDICATOR_DATA_AVAILABILITY = properties.getProperty("INDICATOR_DATA_AVAILABILITY");
			INDICATOR_MAX_DATA_AVAILABLE = properties.getProperty("INDICATOR_MAX_DATA_AVAILABLE");
			
			// MMR DEMO
			MMR_BYDIST =  properties.getProperty("MMR_BYDIST");
			MMR_BYDISTHM = properties.getProperty("MMR_BYDISTHM");
			MMR_BYINST =  properties.getProperty("MMR_BYINST");
			MMR_CHINDAN = properties.getProperty("MMR_CHINDAN");
			MMR_CHINDDL = properties.getProperty("MMR_CHINDDL");
			MMR_CHINDLD = properties.getProperty("MMR_CHINDLD");
			MMR_CHINDNC = properties.getProperty("MMR_CHINDNC");
			MMR_BYINSTHM = properties.getProperty("MMR_BYINSTHM");
			MMR_AXISLINEGT = properties.getProperty("MMR_AXISLINEGT");
			MMR_AXISLINELT = properties.getProperty("MMR_AXISLINELT");
			MMR_FCHINDNC = properties.getProperty("MMR_FCHINDNC");
			MMR_BYRISK = properties.getProperty("MMR_BYRISK");
			MMR_CHINDDL1 = properties.getProperty("MMR_CHINDDL1");
			MMR_CHINDDL2 = properties.getProperty("MMR_CHINDDL2");
			MMR_DEL1 = properties.getProperty("MMR_DEL1");
			MMR_DEL2 = properties.getProperty("MMR_DEL2");
			MMR_DEL3 = properties.getProperty("MMR_DEL3");
			MMR_CHINDANM = properties.getProperty("MMR_CHINDANM");
			MMR_AXISLINEGT1 = properties.getProperty("MMR_AXISLINEGT1");
			MMR_AXISLINELT1  = properties.getProperty("MMR_AXISLINELT1");
			
			// IMR DEMO
			IMR_BYDIST =  properties.getProperty("IMR_BYDIST");
			IMR_BYDISTHM = properties.getProperty("IMR_BYDISTHM");
			IMR_BYINST =  properties.getProperty("IMR_BYINST");
			IMR_CHINDAN = properties.getProperty("IMR_CHINDAN");
			IMR_CHINDDL = properties.getProperty("IMR_CHINDDL");
			IMR_CHINDLD = properties.getProperty("IMR_CHINDLD");
			IMR_CHINDNC = properties.getProperty("IMR_CHINDNC");
			IMR_BYINSTHM = properties.getProperty("IMR_BYINSTHM");
			IMR_AXISLINEGT = properties.getProperty("IMR_AXISLINEGT");
			IMR_AXISLINELT = properties.getProperty("IMR_AXISLINELT");
			IMR_FCHINDNC = properties.getProperty("IMR_FCHINDNC");
			IMR_BYRISK = properties.getProperty("IMR_BYRISK");
			IMR_CHINDDL1 = properties.getProperty("IMR_CHINDDL1");
			IMR_CHINDDL2 = properties.getProperty("IMR_CHINDDL2");
			IMR_DEL1 = properties.getProperty("IMR_DEL1");
			IMR_DEL2 = properties.getProperty("IMR_DEL2");
			IMR_DEL3 = properties.getProperty("IMR_DEL3");
			IMR_CHINDANM = properties.getProperty("IMR_CHINDANM");
			IMR_AXISLINEGT1 = properties.getProperty("IMR_AXISLINEGT1");
			IMR_AXISLINELT1  = properties.getProperty("IMR_AXISLINELT1");
			CHART_HEADER = properties.getProperty("CHART_HEADER");
			CHART_RANGE = properties.getProperty("CHART_RANGE");
			
			//CDNCD
			TM_DISTRICTDATANCD  = properties.getProperty("TM_DISTRICTDATANCD");
			TM_DISTRICTDATACD  = properties.getProperty("TM_DISTRICTDATACD");
			TM_JSON = properties.getProperty("TM_JSON");
			TM_JSONGRANDCHILDCD = properties.getProperty("TM_JSONGRANDCHILDCD");
			TM_JSONGRANDCHILDNCD = properties.getProperty("TM_JSONGRANDCHILDNCD");
			TM_JSONGRANDDATA = properties.getProperty("TM_JSONGRANDDATA");
			TM_CD = properties.getProperty("TM_CD");
			TM_NCD = properties.getProperty("TM_NCD");
			TM_CDCLIKD = properties.getProperty("TM_CDCLIKD");
			TM_NCDCLIKD = properties.getProperty("TM_NCDCLIKD");
			TM_CDCLIKI = properties.getProperty("TM_CDCLIKI"); 
			TM_NCDCLIKI = properties.getProperty("TM_NCDCLIKI");
			TM_JSONI = properties.getProperty("TM_JSONI");
			CD_DISTRICTDATA = properties.getProperty("CD_DISTRICTDATA");
			CD_INSTITUTIONDATA = properties.getProperty("CD_INSTITUTIONDATA");
			NCD_DISTRICTDATA = properties.getProperty("NCD_DISTRICTDATA");
			NCD_INSTITUTIONDATA = properties.getProperty("NCD_INSTITUTIONDATA");

			
			// INDICATOR ZONE
			IND_DIST = properties.getProperty("IND_DIST");
			
			//SDGS Dashboard
			DIST_MATERNALDEATH = properties.getProperty("DIST_MATERNALDEATH");
			MATERNALDEATH_MONTH = properties.getProperty("MATERNALDEATH_MONTH");
			SDGS_THRESHOLD = properties.getProperty("SDGS_THRESHOLD");

			//DMS Report Zone
			DMS_REPORT = properties.getProperty("DMS_REPORT");
			//Predictive Zone
			GETIMRHEATMAP_TOPLEFTDATA = properties.getProperty("GETIMRHEATMAP_TOPLEFTDATA");
			GETIMRHEATMAP_TOPRIGHTDATA = properties.getProperty("GETIMRHEATMAP_TOPRIGHTDATA");
			GETIMRHEATMAP_BOTTOMLEFTDATA = properties.getProperty("GETIMRHEATMAP_BOTTOMLEFTDATA");
			GETIMRHEATMAP_BOTTOMRIGHTDATA = properties.getProperty("GETIMRHEATMAP_BOTTOMRIGHTDATA");
			GETDISTRICTLIST = properties.getProperty("GETDISTRICTLIST");
			//IMR2
			GETIMR2_HEATMAPTOPLEFTDATA =properties.getProperty("GETIMR2_HEATMAPTOPLEFTDATA");
			GETIMR2_LINETOPRIGHTDATA =properties.getProperty("GETIMR2_LINETOPRIGHTDATA");
			GETIMR2_HEATMAPBOTTOMDATA =properties.getProperty("GETIMR2_HEATMAPBOTTOMDATA");
			//DOB
			GETDISEASELIST=properties.getProperty("GETDISEASELIST");
			GETDOB_HEATMAPTOPLEFTDATA=properties.getProperty("GETDOB_HEATMAPTOPLEFTDATA");
			GETDOB_HEATMAPTOPRIGHTDATA=properties.getProperty("GETDOB_HEATMAPTOPRIGHTDATA");
			GETDOB_LINEBOTTOMLEFTDATA=properties.getProperty("GETDOB_LINEBOTTOMLEFTDATA");
			GETDOB_HEATMAPBOTTTOMRIGHTDATA=properties.getProperty("GETDOB_HEATMAPBOTTTOMRIGHTDATA");
			//HOB
			GETHOB_LINETOPLEFTDATA=properties.getProperty("GETHOB_LINETOPLEFTDATA");
			GETHOB_BARTOPRIGHTDATA=properties.getProperty("GETHOB_BARTOPRIGHTDATA");
			GETHOB_BOTTOMDATA=properties.getProperty("GETHOB_BOTTOMDATA");
			//BBSI
			GETBBSI_BARTOPLEFTDATA=properties.getProperty("GETBBSI_BARTOPLEFTDATA");
			GETBBSI_BARTOPRIGHTDATA=properties.getProperty("GETBBSI_BARTOPRIGHTDATA");
			GETBBSI_BOTTOMLEFTDATA=properties.getProperty("GETBBSI_BOTTOMLEFTDATA");
			//BBS
			GETBBS_TOPLEFTDATA=properties.getProperty("GETBBS_TOPLEFTDATA");
			GETBBS_TOPRIGHTDATA=properties.getProperty("GETBBS_TOPRIGHTDATA");
			GETBBS_BOTTOMLEFTDATA=properties.getProperty("GETBBS_BOTTOMLEFTDATA");
			GETBBS_BOTTOMRIGHTDATA=properties.getProperty("GETBBS_BOTTOMRIGHTDATA");
			GETINDCATEGORYLIST=properties.getProperty("GETINDCATEGORYLIST");
			//FIP
			GETFIP_TOPLEFTDATA=properties.getProperty("GETFIP_TOPLEFTDATA");
			GETFIP_TOPRIGHTDATA=properties.getProperty("GETFIP_TOPRIGHTDATA");
			GETFIP_BOTTOMLEFTDATA=properties.getProperty("GETFIP_BOTTOMLEFTDATA");
			GETFIP_BOTTOMRIGHTDATA=properties.getProperty("GETFIP_BOTTOMRIGHTDATA");
			GETYEARLIST=properties.getProperty("GETYEARLIST");
			//CDD
			GETFILETYPELIST=properties.getProperty("GETFILETYPELIST");
			GETREASONLIST=properties.getProperty("GETREASONLIST");
			GETCDD_TOPLEFTDATA=properties.getProperty("GETCDD_TOPLEFTDATA");
			GETCDD_TOPRIGHTDATA=properties.getProperty("GETCDD_TOPRIGHTDATA");
			GETCDD_BOTTOMLEFTDATA=properties.getProperty("GETCDD_BOTTOMLEFTDATA");
			GETCDD_BOTTOMRIGHTDATA=properties.getProperty("GETCDD_BOTTOMRIGHTDATA");
			//CSP
			GETCSP_TOPLEFTDATA=properties.getProperty("GETCSP_TOPLEFTDATA");
			GETCSP_TOPRIGHTDATA=properties.getProperty("GETCSP_TOPRIGHTDATA");
			GETCSP_BOTTOMLEFTDATA=properties.getProperty("GETCSP_BOTTOMLEFTDATA");
			GETCSP_BOTTOMRIGHTDATA=properties.getProperty("GETCSP_BOTTOMRIGHTDATA");
			//CPP
			GETCPP_TOPLEFTDATA=properties.getProperty("GETCPP_TOPLEFTDATA");
			GETCPP_TOPRIGHTDATA=properties.getProperty("GETCPP_TOPRIGHTDATA");
			GETCPP_BOTTOMLEFTDATA=properties.getProperty("GETCPP_BOTTOMLEFTDATA");
			GETCPP_BOTTOMRIGHTDATA=properties.getProperty("GETCPP_BOTTOMRIGHTDATA");
			GETSPECIALITYLIST=properties.getProperty("GETSPECIALITYLIST");
			//CNP
			GETCNP_TOPLEFTDATA=properties.getProperty("GETCNP_TOPLEFTDATA");
			GETCNP_TOPRIGHTDATA=properties.getProperty("GETCNP_TOPRIGHTDATA");
			GETCNP_BOTTOMLEFTDATA=properties.getProperty("GETCNP_BOTTOMLEFTDATA");
			GETCNP_BOTTOMRIGHTDATA=properties.getProperty("GETCNP_BOTTOMRIGHTDATA");
			//MMR INSIDES
			GETMMRI_TOPLEFTDATA=properties.getProperty("GETMMRI_TOPLEFTDATA");
			GETMMRI_TOPRIGHTDATA=properties.getProperty("GETMMRI_TOPRIGHTDATA");
			GETMMRI_BOTTOMLEFTDATA=properties.getProperty("GETMMRI_BOTTOMLEFTDATA");
			//MMR OPERATIONAL
			GETMMRO_TOPLEFTDATA=properties.getProperty("GETMMRO_TOPLEFTDATA");
			GETMMRO_TOPRIGHTDATA=properties.getProperty("GETMMRO_TOPRIGHTDATA");
			GETMMRO_BOTTOMLEFTDATA=properties.getProperty("GETMMRO_BOTTOMLEFTDATA");
			GETMMRO_BOTTOMRIGHTDATA=properties.getProperty("GETMMRO_BOTTOMRIGHTDATA");
			GETREASONABLEINDICATORLIST=properties.getProperty("GETREASONABLEINDICATORLIST");
			//Measles
			GETMEASLES_TOPLEFTDATA=properties.getProperty("GETMEASLES_TOPLEFTDATA");
			GETMEASLES_TOPRIGHTDATA=properties.getProperty("GETMEASLES_TOPRIGHTDATA");
			GETMEASLES_BOTTOMLEFTDATA=properties.getProperty("GETMEASLES_BOTTOMLEFTDATA");
			GETMEASLES_BOTTOMRIGHTDATA=properties.getProperty("GETMEASLES_BOTTOMRIGHTDATA");	
			
			
			
			DIRECTORATE_MASTER_DIM_LIST=properties.getProperty("Directorate_Master_Dim");
			SAVE_USER_DETAILS=properties.getProperty("SAVE_USER_DETAILS");
			GET_USER_DETAILS=properties.getProperty("GET_USER_DETAILS");
			GET_LDAPORGNAME_DETAILS=properties.getProperty("GET_LDAPORGNAME_DETAILS");
			GET_USER_DETAILS_ByOGId=properties.getProperty("GET_USER_DETAILS_ByOGId");
			USER_MANAGEMENT_LIST=properties.getProperty("USER_MANAGEMENT_LIST");
			USER_MANAGEMENT_LIST_BY_USERID=properties.getProperty("USER_MANAGEMENT_LIST_BY_USERID");
			Update_USER_VALUES=properties.getProperty("Update_USER_VALUES");
			Update_CHANGE_PWD_VALUES=properties.getProperty("Update_CHANGE_PWD_VALUES");
			
		}
	}

}
