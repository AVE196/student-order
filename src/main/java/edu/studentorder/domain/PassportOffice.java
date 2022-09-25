package edu.studentorder.domain;

public class PassportOffice {

	private long officeID;
	private String officeAreaID;
	private String officeName;
	
	public PassportOffice(long officeID, String officeAreaID, String officeName) {
		this.officeID = officeID;
		this.officeAreaID = officeAreaID;
		this.officeName = officeName;
	}
	
		public PassportOffice() {
	}

	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public String getOfficeAreaID() {
		return officeAreaID;
	}
	public void setOfficeAreaID(String officeAreaID) {
		this.officeAreaID = officeAreaID;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	@Override
	public String toString() {
		return "PassportOffice [officeID=" + officeID + ", officeAreaID=" + officeAreaID + ", officeName=" + officeName
				+ "]";
	}
	
	
	
}
