package edu.studentorder.dao;

import java.util.List;

import edu.studentorder.domain.CountryArea;
import edu.studentorder.domain.PassportOffice;
import edu.studentorder.domain.RegisterOffice;
import edu.studentorder.domain.Street;
import edu.studentorder.exception.DaoException;

public interface DictionaryDao {
	
	List<Street> findStreet(String pattern) throws DaoException;
	List<PassportOffice> findPassportOffices(String areaID) throws DaoException;
	List<RegisterOffice> findRegisterOffices(String areaID) throws DaoException;
	List<CountryArea> findAreas(String areaID) throws DaoException;

	
}
