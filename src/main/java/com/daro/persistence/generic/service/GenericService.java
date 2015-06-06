/**
 * Author: Dario Palminio
 * License: GPLv3 (http://www.gnu.org/copyleft/gpl.html)
 */
package com.daro.persistence.generic.service;

import java.io.Serializable;

import com.daro.persistence.generic.dao.GenericDao;

/**
 * Generic Service
 * 
 * @author Dario Palminio
 * 
 * @param <T> Data type expected in query return as entity
 */
public interface GenericService<T extends Serializable> extends GenericDao<T>{

	public void setDao(GenericDao<T> dao);
	public GenericDao<T> getDao();
	
}
