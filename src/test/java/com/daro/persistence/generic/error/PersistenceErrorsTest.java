package com.daro.persistence.generic.error;

import static org.junit.Assert.*;

import org.junit.Test;

import com.daro.persistence.generic.error.PersistenceError;

public class PersistenceErrorsTest {

	@Test
	public void testGetWord() {
		assertEquals(PersistenceError.ENTITY_NULL.getWord(), "ENTITY_NULL");
	}
	
	@Test
	public void testToString() {
		assertEquals(PersistenceError.ENTITY_NULL.toString(), PersistenceError.ENTITY_NULL.getMessage());
	}
	
	@Test
	public void testGetErrorCodeFromMessage() {
		assertEquals(0, PersistenceError.getErrorCodeFromMessage(PersistenceError.UNIDENTIFIED_ERROR.getMessage()));
	}
	
}
