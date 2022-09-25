package edu.studentorder.domain;

public enum StudentOrderStatus {
	START, CHECKED;
	

	public static StudentOrderStatus fromValue(int status) {
		for (StudentOrderStatus sos: StudentOrderStatus.values()) {
			if(sos.ordinal() == status) return sos;
		}
		throw new RuntimeException("invalid value: "+ status);
	}
}