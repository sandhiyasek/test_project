package gov.shdrc.servicemaintenance.util;

public class LocationMaster {
	private Integer locationId;
	private String hudType;
	private String blockName;
	private String talukName;
	private String addedOn;
    private String updatedOn;
	private int active;
	
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public String getHudType() {
		return hudType;
	}
	public void setHudType(String hudType) {
		this.hudType = hudType;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getTalukName() {
		return talukName;
	}
	public void setTalukName(String talukName) {
		this.talukName = talukName;
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
