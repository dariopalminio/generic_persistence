package com.daro.persistence.generic;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.daro.persistence.generic.error.PersistenceErrors;
import com.daro.persistence.generic.error.PersistenceException;
import com.daro.persistence.generic.testdata.PersonEntity;
import com.daro.persistence.generic.testdata.PersonEntityService;

/**
 * Test persistence with GenericService.
 * This also test the GenericDaoImpl class because the class GenericService uses 
 * GenericDaoImpl to persist.
 * The different test cases are not completely independent or atomics because they 
 * work on the same temporal database in memory.
 * The temporal database in memory is configured by "classpath:test-spring-context.xml" file.
 * This test uses HSQLDB database. HSQLDB (HyperSQL DataBase) is the leading SQL relational 
 * database software written in Java. HSQLDB is initialized with test-schema.sql file.
 * 
 * @author dario.palminio
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-context.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class GenericServiceTest {
	
	@Autowired
    private PersonEntityService personEntityService;
	
	/**
	 * Test if injection by Autowired is ok.
	 * personEntityService is injected from test-spring-context.xml
	 */
	@Test
	public void testPersonEntityServiceAutowired() {
		assertNotNull(personEntityService);
	}
	
	/**
	 * Test add, update, getById and getByField method of GenericService class.
	 */
	@Test
	@Transactional
	public void testPersonEntityService() throws PersistenceException {
		
		//Data to test
		String name = "name-test";
		String lastName = "lastname-test";
		String dni = "00000000";	
		String dni2 = "11000111";
		PersonEntity personEntity = new PersonEntity();
		personEntity.setFirstName(name);
		personEntity.setLastName(lastName);
		personEntity.setDniCode(dni);
		
		//Test add method
		personEntityService.add(personEntity);
		
		//Test getByField method
		PersonEntity personEntityFound = personEntityService.getByField("firstName", "name-test");
		assertNotNull(personEntityFound);
		assertTrue(lastName.equals(personEntityFound.getLastName()));
		assertEquals(personEntityFound.getDniCode(), dni);
		
		//Test update method
		personEntityFound.setDniCode(dni2);
		personEntityService.update(personEntityFound);
		
		//Test getById method
		PersonEntity personEntityFound2 = personEntityService.getById(personEntityFound.getId());
		assertEquals(personEntityFound2.getDniCode(), dni2);
		
		//Test list method
		List<PersonEntity> list = null;
		list = personEntityService.list();
		assertNotNull(list);
		assertEquals(list.size(),1);
		
	}

	/**
	 * Test remove method of GenericService class.
	 */
	@Test
	@Transactional
	public void testPersonEntityServiceRemove() throws PersistenceException{
		
		String name = "name-test";
		String lastName = "lastname-test";
		String dni = "00000000";
		
		PersonEntity personEntity = new PersonEntity();
		personEntity.setFirstName(name);
		personEntity.setLastName(lastName);
		personEntity.setDniCode(dni);
		
		personEntityService.add(personEntity);
		PersonEntity personEntityFound = personEntityService.getByField("firstName", "name-test");
		
		assertNotNull(personEntityFound);
		assertTrue(lastName.equals(personEntityFound.getLastName()));
		assertEquals(personEntityFound.getDniCode(), dni);
		
		personEntityService.remove(personEntityFound);
		PersonEntity personEntityFound2 = personEntityService.getByField("firstName", "name-test");
		assertNull(personEntityFound2);		
	}
	
	/**
	 * Test Add Exceptions null.
	 */
	@Test
	@Transactional
	public void testPersonEntityServiceAddExceptions() {
		
		//Test when Entity to persist is null
		PersistenceException exeption = null;
		try {
			personEntityService.add(null);
		} catch (PersistenceException e) {
			exeption = e;
		}
		assertNotNull(exeption);
		PersistenceErrors error = PersistenceErrors.ENTITY_NULL;
		assertEquals(exeption.getMessage(), error.getMessage());
		
		//Test when Session Factory is null
		exeption = null;
		SessionFactory sessionFactory = personEntityService.getSessionFactory();
		personEntityService.setSessionFactory(null);
		try {
			personEntityService.add(null);
		} catch (PersistenceException e) {
			exeption = e;
		}
		assertNotNull(exeption);
		error = PersistenceErrors.SESSION_FACTORY_NULL;
		assertEquals(exeption.getMessage(), error.getMessage());
		personEntityService.setSessionFactory(sessionFactory);
	}
	
	/**
	 * Test Update Exceptions null.
	 */
	@Test
	@Transactional
	public void testPersonEntityServiceUpdateExceptions() {
		
		//Test when Entity to persist is null
		PersistenceException exeption = null;
		try {
			personEntityService.update(null);
		} catch (PersistenceException e) {
			exeption = e;
		}
		assertNotNull(exeption);
		PersistenceErrors error = PersistenceErrors.ENTITY_NULL;
		assertEquals(exeption.getMessage(), error.getMessage());
		
		//Test when Session Factory is null		
		exeption = null;
		SessionFactory sessionFactory = personEntityService.getSessionFactory();
		personEntityService.setSessionFactory(null);
		try {
			personEntityService.update(null);
		} catch (PersistenceException e) {
			exeption = e;
		}
		assertNotNull(exeption);
		error = PersistenceErrors.SESSION_FACTORY_NULL;
		assertEquals(exeption.getMessage(), error.getMessage());
		personEntityService.setSessionFactory(sessionFactory);
	}
	
}
