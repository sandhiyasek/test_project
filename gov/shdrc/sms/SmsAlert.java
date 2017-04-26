package gov.shdrc.sms;

import java.util.Date;

public class SmsAlert {
	private Long smsId=null;
	private int directorateId;
	private String directorateName;
	private int districtId;
	private String districtName=null;
	private String insttitutionName=null;
	private String thresholdComparator=null;
	private String drillDownLevel=null;
	private String drillDownLevlName=null;
	private int indicatorId;
	private String indicatorName=null;
	private Long indicatorValue=null;
	private Long targetValue=null;
	private String message=null;
	private Date addedOn=null;
	private Date updatedOn=null;
	private Long active=null;
	private Long mobileNo=null;
	private String month=null;
	private int year;
	private String hierarchyName=null; 
	
	public Long getSmsId() {
		return smsId;
	}
	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}
	public int getDirectorateId() {
		return directorateId;
	}
	public void setDirectorateId(int directorateId) {
		this.directorateId = directorateId;
	}
	public String getDirectorateName() {
		return directorateName;
	}
	public void setDirectorateName(String directorateName) {
		this.directorateName = directorateName;
	}
	public int getDistrictId() {
		return districtId;
	}
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getInsttitutionName() {
		return insttitutionName;
	}
	public void setInsttitutionName(String insttitutionName) {
		this.insttitutionName = insttitutionName;
	}
	public String getThresholdComparator() {
		return thresholdComparator;
	}
	public void setThresholdComparator(String thresholdComparator) {
		this.thresholdComparator = thresholdComparator;
	}
	public String getDrillDownLevel() {
		return drillDownLevel;
	}
	public void setDrillDownLevel(String drillDownLevel) {
		this.drillDownLevel = drillDownLevel;
	}
	public String getDrillDownLevlName() {
		return drillDownLevlName;
	}
	public void setDrillDownLevlName(String drillDownLevlName) {
		this.drillDownLevlName = drillDownLevlName;
	}
	public int getIndicatorId() {
		return indicatorId;
	}
	public void setIndicatorId(int indicatorId) {
		this.indicatorId = indicatorId;
	}
	public String getIndicatorName() {
		return indicatorName;
	}
	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}
	public Long getIndicatorValue() {
		return indicatorValue;
	}
	public void setIndicatorValue(Long indicatorValue) {
		this.indicatorValue = indicatorValue;
	}
	public Long getTargetValue() {
		return targetValue;
	}
	public void setTargetValue(Long targetValue) {
		this.targetValue = targetValue;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getAddedOn() {
		return addedOn;
	}
	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Long getActive() {
		return active;
	}
	public void setActive(Long active) {
		this.active = active;
	}
	public Long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getHierarchyName() {
		return hierarchyName;
	}
	public void setHierarchyName(String hierarchyName) {
		this.hierarchyName = hierarchyName;
	}

	
}
