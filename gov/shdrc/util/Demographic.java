package gov.shdrc.util;

public class Demographic {
		private Long demographicId=null;
		private String gender=null;
		private String social=null;
		private String ageInDays=null;
		private String ageGroup=null;
		
		public Long getDemographicId() {
			return demographicId;
		}
		public void setDemographicId(Long demographicId) {
			this.demographicId = demographicId;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getSocial() {
			return social;
		}
		public void setSocial(String social) {
			this.social = social;
		}
		public String getAgeInDays() {
			return ageInDays;
		}
		public void setAgeInDays(String ageInDays) {
			this.ageInDays = ageInDays;
		}
		public String getAgeGroup() {
			return ageGroup;
		}
		public void setAgeGroup(String ageGroup) {
			this.ageGroup = ageGroup;
		}
}
