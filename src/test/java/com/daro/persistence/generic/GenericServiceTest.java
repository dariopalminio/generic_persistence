package com.daro.persistence.generic;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.daro.persistence.generic.dao.PersistenceException;
import com.daro.persistence.generic.testdata.PersonEntity;
import com.daro.persistence.generic.testdata.PersonEntityService;

/**
 * Test GenericService.
 * 
 * @author dario.palminio
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-context.xml")
//@TransactionConfiguration(defaultRollback = true)
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
	 * Test add and getByField method of GenericService class.
	 */
	@Test
	@Transactional
	public void testPersonEntityServiceAdd() throws PersistenceException {
		
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
		assertEquals(exeption.getMessage(), PersistenceException.ENTITY_NULL);
		
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
		assertEquals(exeption.getMessage(), PersistenceException.SESSION_FACTORY_NULL);
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
		assertEquals(exeption.getMessage(), PersistenceException.ENTITY_NULL);
		
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
		assertEquals(exeption.getMessage(), PersistenceException.SESSION_FACTORY_NULL);
		personEntityService.setSessionFactory(sessionFactory);
	}
	
}
