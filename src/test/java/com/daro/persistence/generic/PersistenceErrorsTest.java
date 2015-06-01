package com.daro.persistence.generic;

import static org.junit.Assert.*;

import org.junit.Test;

import com.daro.persistence.generic.error.PersistenceErrors;

public class PersistenceErrorsTest {

	@Test
	public void testGetWord() {
		assertEquals(PersistenceErrors.ENTITY_NULL.getWord(), "ENTITY_NULL");
	}
	
	@Test
	public void testToString() {
		assertEquals(PersistenceErrors.ENTITY_NULL.toString(), PersistenceErrors.ENTITY_NULL.getMessage());
	}
	
}
