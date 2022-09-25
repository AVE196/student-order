package edu.studentorder.domain;

public class Address {

	private String postCode;
	private Street street;
	private String building;
	private String extension;
	private String apartmen;
	
	public Address(String postCode, Street street, String building, String extension, String apartmen) {
		this.postCode = postCode;
		this.street = street;
		this.building = building;
		this.extension = extension;
		this.apartmen = apartmen;
	}
	
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public Street getStreet() {
		return street;
	}
	public void setStreet(Street street) {
		this.street = street;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getApartmen() {
		return apartmen;
	}
	public void setApartmen(String apartmen) {
		this.apartmen = apartmen;
	}

	@Override
	public String toString() {
		return "Address [postCode=" + postCode + ", street=" + street + ", building=" + building + ", extension="
				+ extension + ", apartmen=" + apartmen + "]";
	}
	
	
	
}
