package edu.studentorder.domain;
import java.time.LocalDate;

public class Child extends Person{

	public Child(String surName, String givenName, String patronymic, LocalDate dateOfBirth) {
		super(surName, givenName, patronymic, dateOfBirth);
	}
	
	private String certificateNumber;
	private LocalDate issueDate;
	private RegisterOffice issueDepartmen;

	
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	public LocalDate getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}
	
	public RegisterOffice getIssueDepartmen() {
		return issueDepartmen;
	}
	public void setIssueDepartmen(RegisterOffice issueDepartmen) {
		this.issueDepartmen = issueDepartmen;
	}
	@Override
	public String toString() {
		return "Child [certificateNumber=" + certificateNumber + ", issueDate=" + issueDate + ", issueDepartmen="
				+ issueDepartmen + super.toString() + "]";
	}

	
	
}
