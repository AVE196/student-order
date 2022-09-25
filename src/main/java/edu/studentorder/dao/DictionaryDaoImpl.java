package edu.studentorder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.studentorder.domain.CountryArea;
import edu.studentorder.domain.PassportOffice;
import edu.studentorder.domain.RegisterOffice;
import edu.studentorder.domain.Street;
import edu.studentorder.exception.DaoException;

public class DictionaryDaoImpl implements DictionaryDao {
	
	private static final String GET_STREET = "SELECT street_code, street_name FROM jc_street WHERE UPPER(street_name) LIKE UPPER(?)";
	private static final String GET_PASSPORT = "SELECT * FROM jc_passport_office WHERE p_office_area_id  = ?";
	private static final String GET_REGISTER = "SELECT * FROM jc_register_office WHERE r_office_area_id  = ?";
	private static final String GET_AREA = "SELECT * FROM jc_country_struct WHERE area_id like ? and area_id <> ?";
	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryDaoImpl.class);
	
	private Connection getConnection() throws SQLException {
		return ConnectionBuilder.getConnection();
	}
	
	@Override
	public List<Street> findStreet(String pattern) throws DaoException {
		List<Street> result = new LinkedList<Street>();	
		try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(GET_STREET)) {
		stmt.setString(1, "%" + pattern + "%");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Street str = new Street(rs.getLong("street_code"), rs.getString("street_name"));
			result.add(str);
		}
		return result;
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DaoException(ex);
		}		
	}

	@Override
	public List<PassportOffice> findPassportOffices(String areaID) throws DaoException {
		List<PassportOffice> result = new LinkedList<PassportOffice>();	
		try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(GET_PASSPORT)) {
		stmt.setString(1, areaID);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			PassportOffice str = new PassportOffice(rs.getLong("p_office_id"), rs.getString("p_office_area_id"), rs.getString("p_office_name"));
			result.add(str);
		}
		return result;
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DaoException(ex);
		}		
	}


	@Override
	public List<RegisterOffice> findRegisterOffices(String areaID) throws DaoException {
		List<RegisterOffice> result = new LinkedList<RegisterOffice>();	
		try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(GET_REGISTER)) {
		stmt.setString(1, areaID);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			RegisterOffice str = new RegisterOffice(rs.getLong("r_office_id"), rs.getString("r_office_area_id"), rs.getString("r_office_name"));
			result.add(str);
		}
		return result;
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DaoException(ex);
		}
	}

	@Override
	public List<CountryArea> findAreas(String areaID) throws DaoException {
			List<CountryArea> result = new LinkedList<CountryArea>();	
			try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(GET_AREA)) {
			String param1 = buildParam(areaID);
			String param2 = areaID;
				
			stmt.setString(1, param1);
			stmt.setString(2, param2);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				CountryArea str = new CountryArea(rs.getString("area_id"), rs.getString("area_name"));
				result.add(str);
			}
			return result;
			}
			catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				throw new DaoException(ex);
			}
		}

	private String buildParam(String areaID) throws SQLException{
		if (areaID == null || areaID.trim() == "")
			return "__0000000000";
		else if (areaID.endsWith("0000000000"))
			return areaID.substring(0, 2) + "___0000000";
		else if (areaID.endsWith("0000000"))
			return areaID.substring(0, 5) + "___0000";
		else if (areaID.endsWith("0000")) 
			return areaID.substring(0, 8) + "____";
		else 
			throw new SQLException("Invalid areaID: " + areaID);
	}
	
	
}
