package edu.studentorder.mail;
import edu.studentorder.domain.StudentOrder;

public class MailSender {
	
	String mail;

	public void sendMail(StudentOrder so) {
		System.out.println("Send mail to: " + so.getStudentOrderID());

	}
	
}
