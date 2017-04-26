package gov.shdrc.util;

import java.util.Date;

public class User {
	private int userid;
    private String ufname;
    private String ulname;
    private String upass;
    private String email;
    private Long mobile;
    private String tier;
    private Date addedon;
    private Date updated;
    private int roleid;
    private String organisation;
    
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUfname() {
		return ufname;
	}
	public void setUfname(String ufname) {
		this.ufname = ufname;
	}
	public String getUlname() {
		return ulname;
	}
	public void setUlname(String ulname) {
		this.ulname = ulname;
	}
	public String getUpass() {
		return upass;
	}
	public void setUpass(String upass) {
		this.upass = upass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getMobile() {
		return mobile;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public Date getAddedon() {
		return addedon;
	}
	public void setAddedon(Date addedon) {
		this.addedon = addedon;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	} 
}
