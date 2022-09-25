package edu.studentorder.validator;

import edu.studentorder.domain.Child;
import edu.studentorder.domain.Person;
import edu.studentorder.domain.StudentOrder;
import edu.studentorder.domain.register.AnswerCityRegister;
import edu.studentorder.domain.register.AnswerCityRegisterItem;
import edu.studentorder.domain.register.CityRegisterResponse;
import edu.studentorder.exception.CityRegisterException;
import edu.studentorder.exception.TransportException;
import edu.studentorder.validator.register.CityRegisterChecker;
import edu.studentorder.validator.register.FakeCityRegisterChecker;

public class CityRegisterValidator {
	
	public final static String IN_CODE = "NO GRN";
	
	public String hostName;
	protected int port;
	private String login;
	String password;
	private CityRegisterChecker personChecker;
	
	public CityRegisterValidator() {
		personChecker = new FakeCityRegisterChecker();
	}
	
	public AnswerCityRegister checkCityRegister(StudentOrder so) {
		AnswerCityRegister ans = new AnswerCityRegister();
		
		ans.addItem(checkPerson(so.getHusband()));
		ans.addItem(checkPerson(so.getWife()));		
		for (Child child: so.getChildren()) {
			ans.addItem(checkPerson(child));
		}		
		
		return ans;
	}
	
	private AnswerCityRegisterItem checkPerson(Person person) {
		AnswerCityRegisterItem.CityError error = null;
		AnswerCityRegisterItem.CityStatus status = null;
		
		try {
		CityRegisterResponse cans = new CityRegisterResponse();
		cans = personChecker.checkPerson(person);
		System.out.println(cans.toString());
		status = cans.isExisting() ? AnswerCityRegisterItem.CityStatus.YES : AnswerCityRegisterItem.CityStatus.NO;
		
		} catch (CityRegisterException ex){
			ex.printStackTrace(System.out);
			status = AnswerCityRegisterItem.CityStatus.ERROR;
			error = new AnswerCityRegisterItem.CityError(ex.getCode(), ex.getMessage());
		} catch (TransportException ex){
			ex.printStackTrace(System.out);
			status = AnswerCityRegisterItem.CityStatus.ERROR;
			error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
		} catch (Exception ex){
			ex.printStackTrace(System.out);
			status = AnswerCityRegisterItem.CityStatus.ERROR;
			error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
		}
		
		
		AnswerCityRegisterItem ans = new AnswerCityRegisterItem(person, status, error);

		
		return ans;
	}
}
