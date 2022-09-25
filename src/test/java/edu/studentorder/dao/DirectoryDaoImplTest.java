package edu.studentorder.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.LogManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.studentorder.domain.CountryArea;
import edu.studentorder.domain.PassportOffice;
import edu.studentorder.domain.RegisterOffice;
import edu.studentorder.domain.Street;
import edu.studentorder.exception.DaoException;

public class DirectoryDaoImplTest {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DirectoryDaoImplTest.class);

	@BeforeClass
	public static void createDB() throws Exception {
		DBInit.startUp();		
	}
	
	@Test
	public void testStreet() throws DaoException{
		LOGGER.info("TEST {}", LocalDateTime.now());
		List<Street> st = new DictionaryDaoImpl().findStreet("про");
		Assert.assertTrue(st.size() == 1);
	
	}
	@Test
	public void testPassportOffice() throws DaoException{
		List<PassportOffice> po = new DictionaryDaoImpl().findPassportOffices("010020000000");
		Assert.assertTrue(po.size() == 2);
	}
	
	@Test
	public void testRegisterOffice() throws DaoException{
		List<RegisterOffice> ro = new DictionaryDaoImpl().findRegisterOffices("010010000000");
		Assert.assertTrue(ro.size() == 2);
	}

	@Test
	public void testAreas() throws DaoException {
		List<CountryArea> ca1 = new DictionaryDaoImpl().findAreas("");
		Assert.assertTrue(ca1.size() == 2);
		List<CountryArea> ca2 = new DictionaryDaoImpl().findAreas("020000000000");
		Assert.assertTrue(ca2.size() == 2);
		List<CountryArea> ca3 = new DictionaryDaoImpl().findAreas("020010000000");
		Assert.assertTrue(ca3.size() == 2);
		List<CountryArea> ca4 = new DictionaryDaoImpl().findAreas("020010010000");
		Assert.assertTrue(ca4.size() == 2);
	}
}