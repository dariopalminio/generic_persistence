package com.daro.persistence.generic.testdata;

import com.daro.persistence.generic.dao.GenericDaoImpl;



/**
 * @author Dario Palminio
 * 
 */
public class PersonEntityDaoImpl extends GenericDaoImpl<PersonEntity> implements PersonEntityDao {

	public PersonEntityDaoImpl() {
		setClazz(PersonEntity.class);
	}


}