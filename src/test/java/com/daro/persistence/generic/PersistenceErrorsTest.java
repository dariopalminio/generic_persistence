package com.daro.persistence.generic;

import static org.junit.Assert.*;

import org.junit.Test;

import com.daro.persistence.generic.error.PersistenceErrors;

public class PersistenceErrorsTest {

	@Test
	public void testPersonEntityServiceAutowired() {
		assertEquals(PersistenceErrors.ENTITY_NULL.getWord(), "ENTITY_NULL");
	}
	
}
