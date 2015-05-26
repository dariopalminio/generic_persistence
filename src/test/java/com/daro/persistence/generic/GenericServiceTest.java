package com.daro.persistence.generic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
	public void testPersonEntityServiceAdd() {
		
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
	public void testPersonEntityServiceRemove() {
		
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
	
}
