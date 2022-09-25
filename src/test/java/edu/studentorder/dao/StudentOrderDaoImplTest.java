package edu.studentorder.dao;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.studentorder.domain.Address;
import edu.studentorder.domain.Adult;
import edu.studentorder.domain.Child;
import edu.studentorder.domain.PassportOffice;
import edu.studentorder.domain.RegisterOffice;
import edu.studentorder.domain.Street;
import edu.studentorder.domain.StudentOrder;
import edu.studentorder.domain.University;
import edu.studentorder.exception.DaoException;

public class StudentOrderDaoImplTest {

	@BeforeClass
	public static void createDB() throws Exception {
		DBInit.startUp();
	}
	
	@Test
	public void saveStudentOrder() throws DaoException {
		StudentOrder so = buildStudentOrder(10L);
		Long index = new StudentOrderDaoImpl().saveStudentOrder(so);
	}
	
	@Test(expected = DaoException.class)
	public void saveStudentOrderError() throws DaoException {
		StudentOrder so = buildStudentOrder(10L);
		so.getWife().setSurName(null);
		Long index = new StudentOrderDaoImpl().saveStudentOrder(so);
	}
	
	@Test
	public void getStudentOrderOne() throws DaoException {
		List<StudentOrder> soGet = new StudentOrderDaoImpl().getStudentOrders();
		Assert.assertTrue(soGet.size() == 1);
	}
		
	public static StudentOrder buildStudentOrder(long id) {
		StudentOrder so = new StudentOrder();
		so.setStudentOrderID(id);
		RegisterOffice ro = new RegisterOffice(1L, "", "");
		so.setMarriageOffice(ro);
		so.setMarriageSertificateID("" + (100000 + id));
		so.setMarriageDate(LocalDate.of(2008, 11, 8));
		
		Street street = new Street(1L, "First Street");
		Address address = new Address("670139", street, "17", "", "118");
		
		Adult husband = new Adult("Иванов", "Григорий", "Петрович", LocalDate.of(1983, 11, 21));
		husband.setPassportNumber("" + (100000 + id));
		husband.setPassportSeria("" + (1000 + id));
		husband.setIssueDate(LocalDate.of(2000, 4, 16));
		PassportOffice po1 = new PassportOffice(1L, "", "");
		husband.setIssueDepartment(po1);
		husband.setStudentID("" + (100 + id));
		husband.setUniversity(new University(2L, ""));
		husband.setStudentID("HH12345");
		husband.setAddress(address);
		
		Adult wife = new Adult("Иванова","Екатерина","Олеговна", LocalDate.of(1991, 5, 13));
		wife.setPassportNumber("" + (200000 + id));
		wife.setPassportSeria("" + (2000 + id));
		wife.setIssueDate(LocalDate.of(2001, 5, 19));
		PassportOffice po2 = new PassportOffice(2L, "", "");
		wife.setIssueDepartment(po2);
		wife.setStudentID("" + (200 + id));
		wife.setUniversity(new University(1L, ""));
		wife.setStudentID("WW12345");
		wife.setAddress(address);
		
		Child child1 = new Child("Иванов","Лев","Григорьевич", LocalDate.of(2010, 3, 17));
		child1.setCertificateNumber("" + (3000000 + id));
		child1.setIssueDate(LocalDate.of(2010, 3, 19));
		RegisterOffice ro2 = new RegisterOffice(2L, "", "");
		child1.setIssueDepartmen(ro2);
		child1.setAddress(address);
		
		Child child2 = new Child("Иванов","Валерий","Григорьевич", LocalDate.of(2010, 3, 17));
		child2.setCertificateNumber("" + (4000000 + id));
		child2.setIssueDate(LocalDate.of(2010, 5, 7));
		RegisterOffice ro3 = new RegisterOffice(3L, "", "");
		child2.setIssueDepartmen(ro3);
		child2.setAddress(address);

		so.setHusband(husband);
		so.setWife(wife);
		so.addChild(child1);
		so.addChild(child2);
		
		return so;
		}
}
