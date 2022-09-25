package edu.studentorder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.studentorder.config.Config;
import edu.studentorder.domain.Address;
import edu.studentorder.domain.Adult;
import edu.studentorder.domain.Child;
import edu.studentorder.domain.PassportOffice;
import edu.studentorder.domain.Person;
import edu.studentorder.domain.RegisterOffice;
import edu.studentorder.domain.Street;
import edu.studentorder.domain.StudentOrder;
import edu.studentorder.domain.StudentOrderStatus;
import edu.studentorder.domain.University;
import edu.studentorder.exception.DaoException;

public class StudentOrderDaoImpl implements StudentOrderDao{
	
	public static final Logger logger = LoggerFactory.getLogger(StudentOrderDaoImpl.class);
	
	public static final String INSERT_ORDER = "INSERT INTO jc_student_order("
			+ "	student_order_status, student_order_date, h_sur_name, h_given_name, h_patronymic, h_date_of_birth, h_passport_seria, h_passport_number,"
			+ " h_issuedate, h_passport_office, h_post_index, h_street_code, h_building, h_extension, h_apartmen, h_university_id, h_student_number,"
			+ " w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_seria, w_passport_number, w_issuedate, w_passport_office, w_post_index,"
			+ " w_street_code, w_building, w_extension, w_apartmen, w_university_id, w_student_number, sertificate_id, register_office_id, marriage_date)"
			+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?,"
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?,"
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?,"
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	public static final String INSERT_CHILD = "INSERT INTO jc_student_child("
			+ "	student_order_id, c_sur_name, c_given_name, c_patronymic, c_date_of_birth, c_certificate_number,"
			+ " c_certificate_date, c_register_office_id, c_post_index, c_street_code, c_building, c_extension, c_apartmen)"
			+ "	VALUES (?, ?, ?, ?, ?, ?,"
			+ "?, ?, ?, ?, ?, ?, ?);";
	
	public static final String SELECT_ORDERS = "SELECT so.*, ro.r_office_area_id, ro.r_office_name,"
			+ " po_h.p_office_area_id as h_p_office_area_id,"
			+ " po_h.p_office_name as h_p_office_name,"
			+ " po_w.p_office_area_id as w_p_office_area_id,"
			+ " po_w.p_office_name as w_p_office_name"
			+ " FROM jc_student_order so"
			+ " INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id"
			+ " INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office"
			+ " INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office"
			+ " WHERE so.student_order_status = ? ORDER BY student_order_date LIMIT ?";

	public static final String SELECT_CHILD ="SELECT soc.*,"
			+ " ro.r_office_name as c_r_office_name," 
			+ " ro.r_office_area_id as c_r_office_area_id"
			+ " FROM jc_student_child soc"
			+ " INNER JOIN jc_register_office ro ON soc.c_register_office_id = ro.r_office_id"
			+ " WHERE soc.student_order_id IN ";
	
	public static final String SELECT_ORDERS_FULL = "SELECT so.*, ro.r_office_area_id, ro.r_office_name,"
			+ " po_h.p_office_area_id as h_p_office_area_id,"
			+ " po_h.p_office_name as h_p_office_name,"
			+ " po_w.p_office_area_id as w_p_office_area_id,"
			+ " po_w.p_office_name as w_p_office_name,"
			+ " soc.*,"
			+ " ro_c.r_office_name as c_r_office_name,"
			+ " ro_c.r_office_area_id as c_r_office_area_id"
			+ " FROM jc_student_order so"
			+ " INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id"
			+ " INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office"
			+ " INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office"
			+ " INNER JOIN jc_student_child soc ON soc.student_order_id = so.student_order_id"
			+ " INNER JOIN jc_register_office ro_c ON ro_c.r_office_id = soc.c_register_office_id"
			+ " WHERE so.student_order_status = ? ORDER BY so.student_order_id LIMIT ?";
	
		private Connection getConnection() throws SQLException {
			return ConnectionBuilder.getConnection();
	}
		
		@Override
		public long saveStudentOrder(StudentOrder so) throws DaoException {
				long result = -1L;
				logger.debug("SO {}", so);
			try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(INSERT_ORDER, new String[] {"student_order_id"})) {
				con.setAutoCommit(false);
				try {
				// Header
				stmt.setInt(1, StudentOrderStatus.START.ordinal());
				stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
				
				// Husband and Wife
				setParamsForAdult(stmt, 3, so.getHusband());
				setParamsForAdult(stmt, 18, so.getWife());
				stmt.setString(33, so.getMarriageSertificateID());
				stmt.setLong(34, so.getMarriageOffice().getOfficeID());
				stmt.setDate(35, java.sql.Date.valueOf(so.getMarriageDate()));
				
				stmt.executeUpdate();
			    ResultSet rs = stmt.getGeneratedKeys();
			    if(rs.next()) {
			    	result = rs.getLong(1);
			    }
			    rs.close();
			    
			    saveChildren(con, so, result);
				con.commit();
				}
				catch (SQLException ex) {
					con.rollback();
					throw ex;
				}			    
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				throw new DaoException(ex);
			}
		return result;
		}

		private void saveChildren(Connection con, StudentOrder so, long soID) throws SQLException {
			try (PreparedStatement stmt = con.prepareStatement(INSERT_CHILD)) {
				for(Child child : so.getChildren()) {
				stmt.setLong(1, soID);
				setParamsForChild(stmt, child);
				stmt.addBatch();				
				}
				stmt.executeBatch();
			}
		}

		private void setParamsForChild(PreparedStatement stmt, Child child) throws SQLException {
				setParamsForPerson(stmt,2,child);
				stmt.setString(6, child.getCertificateNumber());
				stmt.setDate(7, java.sql.Date.valueOf(child.getIssueDate()));
				stmt.setLong(8, child.getIssueDepartmen().getOfficeID());
				setParamsForAdress(stmt,9,child);
		}

		private void setParamsForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
			setParamsForPerson(stmt, start, adult);
			stmt.setString(start+4, adult.getPassportSeria());
			stmt.setString(start+5, adult.getPassportNumber());
			stmt.setDate(start+6, java.sql.Date.valueOf(adult.getIssueDate()));
			stmt.setLong(start+7, adult.getIssueDepartment().getOfficeID());
			setParamsForAdress(stmt, start+8, adult);
			stmt.setLong(start+13, adult.getUniversity().getUnivercityID());
			stmt.setString(start+14, adult.getStudentID());
		}

		private void setParamsForAdress(PreparedStatement stmt, int start, Person person) throws SQLException {
			Address address = person.getAddress();
			stmt.setString(start, address.getPostCode());
			stmt.setLong(start+1, address.getStreet().getStreetCode());
			stmt.setString(start+2, address.getBuilding());
			stmt.setString(start+3, address.getExtension());
			stmt.setString(start+4, address.getApartmen());
		}

		private void setParamsForPerson(PreparedStatement stmt, int start, Person person) throws SQLException {
			stmt.setString(start, person.getSurName());
			stmt.setString(start+1, person.getGivenName());
			stmt.setString(start+2, person.getPatronymic());
			stmt.setDate(start+3, java.sql.Date.valueOf(person.getDateOfBirth()));
		}

		@Override
		public List<StudentOrder> getStudentOrders() throws DaoException {
			return getStudentOrdersOneSelect();
//			return getStudentOrdersTwoSelect()
		}
		
		public List<StudentOrder> getStudentOrdersOneSelect() throws DaoException {
			List<StudentOrder> result = new LinkedList<>();
			int counter = 0;
			int limit = Integer.parseInt(Config.getProperty(Config.DB_LIMIT));
 			try (Connection con = getConnection(); 
 					PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS_FULL)) {
 				Map<Long, StudentOrder> maps = new HashMap<>();
 				stmt.setInt(1, StudentOrderStatus.START.ordinal());
 				stmt.setInt(2, limit);
 				ResultSet rs = stmt.executeQuery();
 				while (rs.next()) {
 					Long soID = rs.getLong("student_order_id");
 					if (!maps.containsKey(soID)) {
 						StudentOrder so = getFullStudentOrder(rs);		
 						result.add(so);
 						maps.put(rs.getLong("student_order_id"), so);
 					} 
 					StudentOrder so = maps.get(soID);
 					so.addChild(fillChildren(rs));
 					counter++;
 				}
 				rs.close();	
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
				throw new DaoException(ex);
			}
 			if (counter >= limit) {
 				result.remove((result.size() -1));
 			}
			return result;
		}
				
		public List<StudentOrder> getStudentOrdersTwoSelect() throws DaoException {
			List<StudentOrder> result = new LinkedList<>();
 			try (Connection con = getConnection(); 
 					PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS)) {
 				stmt.setInt(1, StudentOrderStatus.START.ordinal());
 				stmt.setInt(2, Integer.parseInt(Config.DB_LIMIT));
 				ResultSet rs = stmt.executeQuery();
 				while (rs.next()) {
 				StudentOrder so = getFullStudentOrder(rs);
 				result.add(so);
 				}
 				rs.close();	
 				findChildren(con, result);
			} catch (SQLException ex) {
				logger.error(ex.getMessage(), ex);
				throw new DaoException(ex);
			}
			return result;
		}

		private StudentOrder getFullStudentOrder(ResultSet rs) throws SQLException {
			StudentOrder so = new StudentOrder();
			
			fillStudentOrder(rs, so);
			fillMarriage(rs, so);
			
			so.setHusband(fillAdult(rs, "h_"));
			so.setWife(fillAdult(rs, "w_"));
			return so;
		}

		private void fillStudentOrder(ResultSet rs, StudentOrder so) throws SQLException{
			so.setStudentOrderID(rs.getLong("student_order_id"));
			so.setStudentOrderstatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));
			so.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
		}
		
		private void fillMarriage(ResultSet rs, StudentOrder so) throws SQLException{
			so.setMarriageSertificateID(rs.getString("sertificate_id"));
			Long roID = rs.getLong("register_office_id");
			String areaID = rs.getString("r_office_area_id");
			String areaName = rs.getString("r_office_name");
			RegisterOffice ro = new RegisterOffice(roID, areaID, areaName);
			so.setMarriageOffice(ro);
			so.setMarriageDate(rs.getDate("marriage_date").toLocalDate());
		}

		private Adult fillAdult(ResultSet rs, String pref) throws SQLException {
			Adult adult = new Adult();
			adult.setSurName(rs.getString(pref + "sur_name"));
			adult.setGivenName(rs.getString(pref + "given_name"));
			adult.setPatronymic(rs.getString(pref + "patronymic"));
			adult.setDateOfBirth(rs.getDate(pref + "date_of_birth").toLocalDate());
			adult.setPassportSeria(rs.getString(pref + "passport_seria"));
			adult.setPassportNumber(rs.getString(pref + "passport_number"));
			adult.setIssueDate(rs.getDate(pref + "issueDate").toLocalDate());
			long poID = rs.getLong(pref + "passport_office");
			String poAreaID = rs.getString(pref + "p_office_area_id");
			String poName = rs.getString(pref + "p_office_name");
			PassportOffice po = new PassportOffice(poID, poAreaID, poName);
			adult.setIssueDepartment(po);
			Street street = new Street(rs.getLong(pref + "street_code"), "");
			Address address = new Address(rs.getString(pref + "post_index"), street, rs.getString(pref + "building"), 
										rs.getString(pref + "extension"), rs.getString(pref + "apartmen"));
			adult.setAddress(address);
			University university = new University(rs.getLong(pref + "university_id"), "");
			adult.setUniversity(university);
			adult.setStudentID(rs.getString(pref + "student_number"));			
			return adult;
		}

		private void findChildren(Connection con, List<StudentOrder> result) throws SQLException {
			String listSoID = "(" + result.stream().map(so -> String.valueOf(so.getStudentOrderID())).collect(Collectors.joining(",")) + ")";
			Map<Long, StudentOrder> maps = result.stream().collect(Collectors.toMap(so -> so.getStudentOrderID(), so -> so));
			
			try (PreparedStatement stmt = con.prepareStatement(SELECT_CHILD + listSoID)) {
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					Child ch = fillChildren(rs);
					StudentOrder so = maps.get(rs.getLong("student_order_id"));
					so.addChild(ch);
				}
				rs.close();
			}			
		}

		private Child fillChildren(ResultSet rs) throws SQLException{
			String surName = rs.getString("c_sur_name");
			String givenName = rs.getString("c_given_name");
			String patronymic = rs.getString("c_patronymic");
			LocalDate dateOfBirth = rs.getDate("c_date_of_birth").toLocalDate();
			Child ch = new Child(surName,givenName,patronymic,dateOfBirth);
			Street street = new Street(rs.getLong("c_street_code"), "");
			Address address = new Address(rs.getString("c_post_index"), street, rs.getString("c_building"), 
					rs.getString("c_extension"), rs.getString("c_apartmen"));
			ch.setAddress(address);
			ch.setCertificateNumber(rs.getString("c_certificate_number"));
			ch.setIssueDate((rs.getDate("c_certificate_date")).toLocalDate());
			RegisterOffice ro = new RegisterOffice();
			ro.setOfficeID(rs.getLong("c_register_office_id"));
			ro.setOfficeAreaID(rs.getString("c_r_office_area_id"));
			ro.setOfficeName(rs.getString("c_r_office_name"));
			ch.setIssueDepartmen(ro);
					
			return ch;
		}

}