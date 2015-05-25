/**
 * Author: Dario Palminio
 * License: GPLv3 (http://www.gnu.org/copyleft/gpl.html)
 */
package com.daro.persistence.generic.service;

import java.io.Serializable;

import com.daro.persistence.generic.dao.GenericDao;

/**
 * @author Dario Palminio
 * 
 */
public interface GenericService<T extends Serializable> extends GenericDao<T>{

	public void setDao(GenericDao<T> dao);
	public GenericDao<T> getDao();
	
}
