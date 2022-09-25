package edu.studentorder.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentOrder {

	
	private long studentOrderID;
	private StudentOrderStatus studentOrderstatus;
	private LocalDateTime studentOrderDate;
	private Adult husband;
	private Adult wife;
	private List<Child> children;
	private String MarriageSertificateID;
	private RegisterOffice MarriageOffice;
	private LocalDate MarriageDate;

	public StudentOrderStatus getStudentOrderstatus() {                        
		return studentOrderstatus;                                             
	}                                                                          
	public void setStudentOrderstatus(StudentOrderStatus studentOrderstatus) { 
		this.studentOrderstatus = studentOrderstatus;                          
	}                                                                          
	public LocalDateTime getStudentOrderDate() {                          
		return studentOrderDate;                                               
	}                                                                          
	public void setStudentOrderDate(LocalDateTime studentOrderDate) {     
		this.studentOrderDate = studentOrderDate;                              
	}                                                                          
	public long getStudentOrderID() {
		return studentOrderID;
	}
	public void setStudentOrderID(long studentOrderID) {
		this.studentOrderID = studentOrderID;
	}
	public Adult getHusband() {
		return husband;
	}
	public void setHusband(Adult husband) {
		this.husband = husband;
	}
	public Adult getWife() {
		return wife;
	}
	public void setWife(Adult wife) {
		this.wife = wife;
	}

	public List<Child> getChildren() {
		return children;
	}
	
	public void addChild(Child child) {
		if (children == null) {
			children = new ArrayList<Child>(5);
		}
		children.add(child);
	}

	public String getMarriageSertificateID() {
		return MarriageSertificateID;
	}
	public void setMarriageSertificateID(String marriageSertificateID) {
		MarriageSertificateID = marriageSertificateID;
	}

	public RegisterOffice getMarriageOffice() {
		return MarriageOffice;
	}
	public void setMarriageOffice(RegisterOffice marriageOffice) {
		MarriageOffice = marriageOffice;
	}
	public LocalDate getMarriageDate() {
		return MarriageDate;
	}
	public void setMarriageDate(LocalDate marriageDate) {
		MarriageDate = marriageDate;
	}
	@Override
	public String toString() {
		return "StudentOrder [studentOrderID=" + studentOrderID + ", studentOrderstatus=" + studentOrderstatus
				+ ", studentOrderDate=" + studentOrderDate + ", husband=" + husband + ", wife=" + wife + ", children="
				+ children + ", MarriageSertificateID=" + MarriageSertificateID + ", MarriageOffice=" + MarriageOffice
				+ ", MarriageDate=" + MarriageDate + "]";
	}
	
	
	
}
