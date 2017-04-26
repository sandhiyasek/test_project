package gov.shdrc.util;

public class DFWEmployeeForm {
		private Long employeeId=null;
		private String employeeName=null;
		private String gpfOrCpsNo=null;
		private String gender=null;
		private String designation=null;
		private String payBand=null;
		private String groupCategory=null;
		private String dutyPay=null;
		private String recruitedBy=null;
		private String gradePay=null;
		private String community=null;
		private String dobDate=null;
		private String qualification=null;
		private String ugMajor=null;
		private String pgMajor=null;
		private String others=null;
		private String firstAppointmentPost=null;
		private String firstAppointmentDOJ=null;
		private String dateOfRegularisation=null;
		private String dateOfProbationDecleration=null;
		private String dateOfRetirement=null;
		private String incrementDueMonth=null;
		private String slsDueDate=null;
		private String physicalStatus=null;
		private String recentPromotion=null;
		private String recentPromotionDOJ=null;
		private String promotion1=null;
		private String promotion1DOJ=null;
		private String promotion2=null;
		private String promotion2DOJ=null;
		private String promotion3=null;
		private String promotion3DOJ=null;
		private String promotion4=null;
		private String promotion4DOJ=null;
		private String promotion5=null;
		private String promotion5DOJ=null;
		private String punishments=null;
		private String ELTakenFrom=null;
		private String ELTakenTo=null;
		private Integer balanceEL;
		private String MLTakenFrom=null;
		private String MLTakenTo=null;
		private Integer balanceML;
		private String UELTakenFrom=null;
		private String UELTakenTo=null;
		private Integer balanceUEL;
		private String nomineeDetails=null;
		private Integer ELTakenDays;
		private Integer MLTakenDays;
		private Integer UELTakenDays;
		private Integer districtId;
		private String retirementType=null;
		private Integer institutionId;
		private String districtName=null;
		private String institutionName=null;
		private String transferType=null;
		private String transferStatus=null;
		private String workingLocation=null;
		private String maritalStatus=null;
		
		
		public String getTransferType() {
			return transferType;
		}
		public void setTransferType(String transferType) {
			this.transferType = transferType;
		}
		public String getTransferStatus() {
			return transferStatus;
		}
		public void setTransferStatus(String transferStatus) {
			this.transferStatus = transferStatus;
		}
		public String getDistrictName() {
			return districtName;
		}
		public void setDistrictName(String districtName) {
			this.districtName = districtName;
		}
		public String getInstitutionName() {
			return institutionName;
		}
		public void setInstitutionName(String institutionName) {
			this.institutionName = institutionName;
		}
		public Long getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(Long employeeId) {
			this.employeeId = employeeId;
		}
		public String getEmployeeName() {
			return employeeName;
		}
		public void setEmployeeName(String employeeName) {
			this.employeeName = employeeName;
		}
		public String getGpfOrCpsNo() {
			return gpfOrCpsNo;
		}
		public void setGpfOrCpsNo(String gpfOrCpsNo) {
			this.gpfOrCpsNo = gpfOrCpsNo;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getDesignation() {
			return designation;
		}
		public void setDesignation(String designation) {
			this.designation = designation;
		}
		public String getPayBand() {
			return payBand;
		}
		public void setPayBand(String payBand) {
			this.payBand = payBand;
		}
		public String getGroupCategory() {
			return groupCategory;
		}
		public void setGroupCategory(String groupCategory) {
			this.groupCategory = groupCategory;
		}
		public String getDutyPay() {
			return dutyPay;
		}
		public void setDutyPay(String dutyPay) {
			this.dutyPay = dutyPay;
		}
		public String getRecruitedBy() {
			return recruitedBy;
		}
		public void setRecruitedBy(String recruitedBy) {
			this.recruitedBy = recruitedBy;
		}
		public String getGradePay() {
			return gradePay;
		}
		public void setGradePay(String gradePay) {
			this.gradePay = gradePay;
		}
		public String getCommunity() {
			return community;
		}
		public void setCommunity(String community) {
			this.community = community;
		}
		public String getDobDate() {
			return dobDate;
		}
		public void setDobDate(String dobDate) {
			this.dobDate = dobDate;
		}
		public String getQualification() {
			return qualification;
		}
		public void setQualification(String qualification) {
			this.qualification = qualification;
		}
		public String getUgMajor() {
			return ugMajor;
		}
		public void setUgMajor(String ugMajor) {
			this.ugMajor = ugMajor;
		}
		public String getPgMajor() {
			return pgMajor;
		}
		public void setPgMajor(String pgMajor) {
			this.pgMajor = pgMajor;
		}
		public String getOthers() {
			return others;
		}
		public void setOthers(String others) {
			this.others = others;
		}
		public String getFirstAppointmentPost() {
			return firstAppointmentPost;
		}
		public void setFirstAppointmentPost(String firstAppointmentPost) {
			this.firstAppointmentPost = firstAppointmentPost;
		}
		public String getFirstAppointmentDOJ() {
			return firstAppointmentDOJ;
		}
		public void setFirstAppointmentDOJ(String firstAppointmentDOJ) {
			this.firstAppointmentDOJ = firstAppointmentDOJ;
		}
		public String getDateOfRegularisation() {
			return dateOfRegularisation;
		}
		public void setDateOfRegularisation(String dateOfRegularisation) {
			this.dateOfRegularisation = dateOfRegularisation;
		}
		public String getDateOfProbationDecleration() {
			return dateOfProbationDecleration;
		}
		public void setDateOfProbationDecleration(String dateOfProbationDecleration) {
			this.dateOfProbationDecleration = dateOfProbationDecleration;
		}
		public String getDateOfRetirement() {
			return dateOfRetirement;
		}
		public void setDateOfRetirement(String dateOfRetirement) {
			this.dateOfRetirement = dateOfRetirement;
		}
		public String getIncrementDueMonth() {
			return incrementDueMonth;
		}
		public void setIncrementDueMonth(String incrementDueMonth) {
			this.incrementDueMonth = incrementDueMonth;
		}
		public String getSlsDueDate() {
			return slsDueDate;
		}
		public void setSlsDueDate(String slsDueDate) {
			this.slsDueDate = slsDueDate;
		}
		public String getPhysicalStatus() {
			return physicalStatus;
		}
		public void setPhysicalStatus(String physicalStatus) {
			this.physicalStatus = physicalStatus;
		}
		public String getRecentPromotion() {
			return recentPromotion;
		}
		public void setRecentPromotion(String recentPromotion) {
			this.recentPromotion = recentPromotion;
		}
		public String getRecentPromotionDOJ() {
			return recentPromotionDOJ;
		}
		public void setRecentPromotionDOJ(String recentPromotionDOJ) {
			this.recentPromotionDOJ = recentPromotionDOJ;
		}
		public String getPromotion1() {
			return promotion1;
		}
		public void setPromotion1(String promotion1) {
			this.promotion1 = promotion1;
		}
		public String getPromotion1DOJ() {
			return promotion1DOJ;
		}
		public void setPromotion1DOJ(String promotion1doj) {
			promotion1DOJ = promotion1doj;
		}
		public String getPromotion2() {
			return promotion2;
		}
		public void setPromotion2(String promotion2) {
			this.promotion2 = promotion2;
		}
		public String getPromotion2DOJ() {
			return promotion2DOJ;
		}
		public void setPromotion2DOJ(String promotion2doj) {
			promotion2DOJ = promotion2doj;
		}
		public String getPromotion3() {
			return promotion3;
		}
		public void setPromotion3(String promotion3) {
			this.promotion3 = promotion3;
		}
		public String getPromotion3DOJ() {
			return promotion3DOJ;
		}
		public void setPromotion3DOJ(String promotion3doj) {
			promotion3DOJ = promotion3doj;
		}
		public String getPromotion4() {
			return promotion4;
		}
		public void setPromotion4(String promotion4) {
			this.promotion4 = promotion4;
		}
		public String getPromotion4DOJ() {
			return promotion4DOJ;
		}
		public void setPromotion4DOJ(String promotion4doj) {
			promotion4DOJ = promotion4doj;
		}
		public String getPromotion5() {
			return promotion5;
		}
		public void setPromotion5(String promotion5) {
			this.promotion5 = promotion5;
		}
		public String getPromotion5DOJ() {
			return promotion5DOJ;
		}
		public void setPromotion5DOJ(String promotion5doj) {
			promotion5DOJ = promotion5doj;
		}
		public String getPunishments() {
			return punishments;
		}
		public void setPunishments(String punishments) {
			this.punishments = punishments;
		}
		public String getELTakenFrom() {
			return ELTakenFrom;
		}
		public void setELTakenFrom(String eLTakenFrom) {
			ELTakenFrom = eLTakenFrom;
		}
		public String getELTakenTo() {
			return ELTakenTo;
		}
		public void setELTakenTo(String eLTakenTo) {
			ELTakenTo = eLTakenTo;
		}
		public Integer getBalanceEL() {
			return balanceEL;
		}
		public void setBalanceEL(Integer balanceEL) {
			this.balanceEL = balanceEL;
		}
		public String getMLTakenFrom() {
			return MLTakenFrom;
		}
		public void setMLTakenFrom(String mLTakenFrom) {
			MLTakenFrom = mLTakenFrom;
		}
		public String getMLTakenTo() {
			return MLTakenTo;
		}
		public void setMLTakenTo(String mLTakenTo) {
			MLTakenTo = mLTakenTo;
		}
		public Integer getBalanceML() {
			return balanceML;
		}
		public void setBalanceML(Integer balanceML) {
			this.balanceML = balanceML;
		}
		public String getUELTakenFrom() {
			return UELTakenFrom;
		}
		public void setUELTakenFrom(String uELTakenFrom) {
			UELTakenFrom = uELTakenFrom;
		}
		public String getUELTakenTo() {
			return UELTakenTo;
		}
		public void setUELTakenTo(String uELTakenTo) {
			UELTakenTo = uELTakenTo;
		}
		public Integer getBalanceUEL() {
			return balanceUEL;
		}
		public void setBalanceUEL(Integer balanceUEL) {
			this.balanceUEL = balanceUEL;
		}
		public String getNomineeDetails() {
			return nomineeDetails;
		}
		public void setNomineeDetails(String nomineeDetails) {
			this.nomineeDetails = nomineeDetails;
		}
		public Integer getELTakenDays() {
			return ELTakenDays;
		}
		public void setELTakenDays(Integer eLTakenDays) {
			ELTakenDays = eLTakenDays;
		}
		public Integer getMLTakenDays() {
			return MLTakenDays;
		}
		public void setMLTakenDays(Integer mLTakenDays) {
			MLTakenDays = mLTakenDays;
		}
		public Integer getUELTakenDays() {
			return UELTakenDays;
		}
		public void setUELTakenDays(Integer uELTakenDays) {
			UELTakenDays = uELTakenDays;
		}
		public Integer getDistrictId() {
			return districtId;
		}
		public void setDistrictId(Integer districtId) {
			this.districtId = districtId;
		}
		public String getRetirementType() {
			return retirementType;
		}
		public void setRetirementType(String retirementType) {
			this.retirementType = retirementType;
		}
		public Integer getInstitutionId() {
			return institutionId;
		}
		public void setInstitutionId(Integer institutionId) {
			this.institutionId = institutionId;
		}
		public String getWorkingLocation() {
			return workingLocation;
		}
		public void setWorkingLocation(String workingLocation) {
			this.workingLocation = workingLocation;
		}
		public String getMaritalStatus() {
			return maritalStatus;
		}
		public void setMaritalStatus(String maritalStatus) {
			this.maritalStatus = maritalStatus;
		}
		
		
}