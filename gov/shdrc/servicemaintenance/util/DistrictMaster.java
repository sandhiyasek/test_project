package gov.shdrc.servicemaintenance.util;

public class DistrictMaster {
	 private int districtId; 
	 private String districtName;
	 private String districtHeadQuarters;
	 private int yearOfPopulationCount;
	 private int population;
	 private String addedOn;
	 private String updatedOn;
	 private int active;
	 private String state;
	 
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
	public String getDistrictHeadQuarters() {
		return districtHeadQuarters;
	}
	public void setDistrictHeadQuarters(String districtHeadQuarters) {
		this.districtHeadQuarters = districtHeadQuarters;
	}
	public int getYearOfPopulationCount() {
		return yearOfPopulationCount;
	}
	public void setYearOfPopulationCount(int yearOfPopulationCount) {
		this.yearOfPopulationCount = yearOfPopulationCount;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}	
	
}
