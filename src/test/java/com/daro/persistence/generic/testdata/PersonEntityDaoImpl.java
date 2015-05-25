package com.daro.persistence.generic.testdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daro.persistence.generic.dao.GenericDaoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;


/**
 * @author Dario Palminio
 * 
 */
public class PersonEntityDaoImpl extends GenericDaoImpl<PersonEntity> implements PersonEntityDao {

	public PersonEntityDaoImpl() {
		setClazz(PersonEntity.class);
	}


}