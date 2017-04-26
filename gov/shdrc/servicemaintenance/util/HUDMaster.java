package gov.shdrc.servicemaintenance.util;

public class HUDMaster {
	private Integer directorateId;
	private Integer hudId;
	private String hudName;
	private String hudType;
	private String hudGroup;
	private String addedOn;
    private String updatedOn;
	private int active;
	private String directorateName; 
	
	public String getDirectorateName() {
		return directorateName;
	}
	public void setDirectorateName(String directorateName) {
		this.directorateName = directorateName;
	}
	public Integer getDirectorateId() {
		return directorateId;
	}
	public void setDirectorateId(Integer directorateId) {
		this.directorateId = directorateId;
	}	
	public Integer getHudId() {
		return hudId;
	}
	public void setHudId(Integer hudId) {
		this.hudId = hudId;
	}
	public String getHudName() {
		return hudName;
	}
	public void setHudName(String hudName) {
		this.hudName = hudName;
	}
	public String getHudType() {
		return hudType;
	}
	public void setHudType(String hudType) {
		this.hudType = hudType;
	}
	public String getHudGroup() {
		return hudGroup;
	}
	public void setHudGroup(String hudGroup) {
		this.hudGroup = hudGroup;
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
