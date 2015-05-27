/**
 * Author: Dario Palminio
 * License: GPLv3 (http://www.gnu.org/copyleft/gpl.html)
 */
package com.daro.persistence.generic.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;



/**
 * GenericDao
 * 
 * @author Dario Palminio
 * 
 */
public interface GenericDao<T extends Serializable> {

	public void setSessionFactory(SessionFactory sessionFactory);
	public SessionFactory getSessionFactory();
	public void add(T p) throws PersistenceException;
	public void update(T p) throws PersistenceException;
	public List<T> list() throws PersistenceException;
	public T getById(Long id) throws PersistenceException;
	public T getByField(String fieldName, String value) throws PersistenceException;
	public void removeById(Long id) throws PersistenceException;
	public void remove(T t) throws PersistenceException;
	public List<T> search(Map<String, Object> parameterMap) throws PersistenceException;
	public void setLoggerInfoEnabled(boolean loggerEnabled);
	public boolean isLoggerInfoEnabled();
}

