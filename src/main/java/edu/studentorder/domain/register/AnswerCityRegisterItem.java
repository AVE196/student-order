package edu.studentorder.domain.register;

import edu.studentorder.domain.Person;

public class AnswerCityRegisterItem {

	public enum CityStatus {
		YES, NO, ERROR
	}
	
	public static class CityError {
		private String code;
		private String error;
		
		public CityError(String code, String error) {
			this.code = code;
			this.error = error;
		}

		public String getCode() {
			return code;
		}

		public String getError() {
			return error;
		}
			
	}
	
	private Person person;
	private CityStatus status;
	private CityError error;
	
	public AnswerCityRegisterItem(Person person, CityStatus status, CityError error) {
		this.person = person;
		this.status = status;
		this.error = error;
	}

	public AnswerCityRegisterItem(Person person, CityStatus status) {
		this.person = person;
		this.status = status;
	}

	public Person getPerson() {
		return person;
	}

	public CityStatus getStatus() {
		return status;
	}

	public CityError getError() {
		return error;
	}
	
	
}
