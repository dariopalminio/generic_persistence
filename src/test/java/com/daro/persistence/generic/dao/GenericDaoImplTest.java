package com.daro.persistence.generic.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.daro.persistence.generic.testdata.PersonEntity;
import com.daro.persistence.generic.testdata.PersonPersistence;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;

/**
 * Test GenericDaoImpl using mockit
 * 
 * @author dario.palminio
 *
 */
public class GenericDaoImplTest {

	private GenericDaoImpl<PersonEntity> genericDaoImpl; //class objective to be tested
	
	@Mocked
	private SessionFactory sessionFactory; //to inject into genericDaoImpl

	@Mocked
	private Session session; //to inject into genericDaoImpl

	private PersonEntity personEntity;

	@Before
	public void setUp() {

		genericDaoImpl = new GenericDaoImpl<PersonEntity>(sessionFactory) {
			@Override
			public SessionFactory getSessionFactory() {
				return super.getSessionFactory();
			}

		};
		
		personEntity = new PersonEntity("Daro", "test", "0000000");
	}

	@Test
	public void testGetCurrentSession() throws Exception {
		
		//Mocking
		new Expectations() {
			{
				sessionFactory.getCurrentSession();
				result = session;
			}
		};
		
		assertNotNull("A session should be returned",
				genericDaoImpl.getCurrentSession());
	}

	@Test
	public void testGetSessionFactory() throws Exception {
		assertEquals("A sessionFactory correctly returned", sessionFactory,
				genericDaoImpl.getSessionFactory());
	}
	
	@Test
	public void testAdd() throws Exception{
		PersonPersistence personPersistence = new PersonPersistence();
		
		personPersistence.setSessionFactory(sessionFactory);
		
		//Mocking
		new NonStrictExpectations() {
			{
				sessionFactory.getCurrentSession();
				result = session;
				session.persist(personEntity);
			}
		};
		
		
		assertNotNull("A session should be returned",
				personPersistence.getCurrentSession());
		
		personPersistence.add(personEntity);
	}
	

}
