package edu.studentorder.validator;
import edu.studentorder.domain.StudentOrder;
import edu.studentorder.domain.children.AnswerChildren;

public class ChildrenValidator {
	
	String childName;
	
	public AnswerChildren checkChildren(StudentOrder so) {
		System.out.println("checkChildren is runnning");
		AnswerChildren ans = new AnswerChildren();
		ans.success = false;
		
		return ans;
	}

}
