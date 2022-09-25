package edu.studentorder.domain;

public class University {

	private long univercityID;
	private String universityName;
		
	public University() {
	}

	public University(long univercityID, String universityName) {
		this.univercityID = univercityID;
		this.universityName = universityName;
	}
	
	public long getUnivercityID() {
		return univercityID;
	}
	public void setUnivercityID(long univercityID) {
		this.univercityID = univercityID;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	@Override
	public String toString() {
		return "University [univercityID=" + univercityID + ", universityName=" + universityName + "]";
	}
	
	
	
}
