package edu.studentorder.validator;
import edu.studentorder.domain.StudentOrder;
import edu.studentorder.domain.student.AnswerStudent;

public class StudentValidator {

	String schoolName;
	
	public AnswerStudent checkStudent(StudentOrder so) {
		System.out.println("checkStudent is runnning");
		AnswerStudent ans = new AnswerStudent();
		ans.success = false;
		return ans;
	}
}
