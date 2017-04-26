package gov.shdrc.servicemaintenance.util;

public class InstitutionMaster {
	private Integer institutionId;
	private Integer locationId;
	private String institutionName;
	private String institutionLevel;
	private String institutionSpecialityId;
	private String institutionType;
	private String addedOn;
    private String updatedOn;
	private int active;
	
	public Integer getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(Integer institutionId) {
		this.institutionId = institutionId;
	}
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public String getInstitutionName() {
		return institutionName;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	public String getInstitutionLevel() {
		return institutionLevel;
	}
	public void setInstitutionLevel(String institutionLevel) {
		this.institutionLevel = institutionLevel;
	}
	public String getInstitutionSpecialityId() {
		return institutionSpecialityId;
	}
	public void setInstitutionSpecialityId(String institutionSpecialityId) {
		this.institutionSpecialityId = institutionSpecialityId;
	}
	public String getInstitutionType() {
		return institutionType;
	}
	public void setInstitutionType(String institutionType) {
		this.institutionType = institutionType;
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
	
}
