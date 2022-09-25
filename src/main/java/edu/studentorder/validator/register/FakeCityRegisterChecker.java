package edu.studentorder.validator.register;

import edu.studentorder.domain.Person;
import edu.studentorder.domain.register.CityRegisterResponse;
import edu.studentorder.exception.CityRegisterException;
import edu.studentorder.exception.TransportException;
import edu.studentorder.domain.Adult;
import edu.studentorder.domain.Child;

public class FakeCityRegisterChecker implements CityRegisterChecker{
	

		final private static String GOOD1 = "100000";
		final private static String GOOD2 = "200000";
		final private static String BAD1 = "100001";
		final private static String BAD2 = "200001";
		final private static String ERROR1 = "100002";
		final private static String ERROR2 = "200002";
		final private static String ERROR_T_1 = "100003";
		final private static String ERROR_T_2 = "200003";

public CityRegisterResponse checkPerson(Person person) throws CityRegisterException, TransportException{
		
		CityRegisterResponse resp = new CityRegisterResponse();

		if (person instanceof Adult) {
			Adult ps = (Adult)person;
			
			if (ps.getPassportNumber().equals(GOOD1) || ps.getPassportNumber().equals(GOOD2)) {
				resp.setExisting(true);
				resp.setTemporal(false);
			}
			if (ps.getPassportNumber().equals(BAD1) || ps.getPassportNumber().equals(BAD2)) {
				resp.setExisting(false);
			}
			if (ps.getPassportNumber().equals(ERROR1) || ps.getPassportNumber().equals(ERROR2)) {
				CityRegisterException ex = new CityRegisterException("1", "GRN ERROR " + ps.getPassportNumber());
				throw ex;
			}
			
			if (ps.getPassportNumber().equals(ERROR_T_1) || ps.getPassportNumber().equals(ERROR_T_2)) {
				TransportException ex = new TransportException("Transport ERROR " + ps.getPassportNumber());
				throw ex;
			}
		}
			
		if (person instanceof Child) {
			resp.setExisting(true);
			resp.setTemporal(true);
		}
		
		return resp;
	}
	
}


