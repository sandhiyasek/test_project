package gov.shdrc.servicemaintenance.util;

public class DirectorateMaster {
	    private String directorateName;
	    private String directorateLevel;
	    private String addedOn;
	    private String updatedOn;
	    private int active;
	    private int directorateId;
	    
		public String getDirectorateName() {
			return directorateName;
		}
		public void setDirectorateName(String directorateName) {
			this.directorateName = directorateName;
		}
		public String getDirectorateLevel() {
			return directorateLevel;
		}
		public void setDirectorateLevel(String directorateLevel) {
			this.directorateLevel = directorateLevel;
		}
		public String getAddedOn() {
			return addedOn;
		}
		public void setAddedOn(String addedOn) {
			this.addedOn = addedOn;
		}
		public String getUpdatedOn() {
			return updatedOn;
		}
		public void setUpdatedOn(String updatedOn) {
			this.updatedOn = updatedOn;
		}
		public int getActive() {
			return active;
		}
		public void setActive(int active) {
			this.active = active;
		}
		public int getDirectorateId() {
			return directorateId;
		}
		public void setDirectorateId(int directorateId) {
			this.directorateId = directorateId;
			
		}
}
