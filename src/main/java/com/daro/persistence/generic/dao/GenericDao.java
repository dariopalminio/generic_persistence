/**
 * Author: Dario Palminio
 * License: GPLv3 (http://www.gnu.org/copyleft/gpl.html)
 */
package com.daro.persistence.generic.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.daro.persistence.generic.error.PersistenceException;

/**
 * GenericDao
 * 
 * @author Dario Palminio
 * 
 * @param <T>
 *            Data type expected in query return as entity
 */
public interface GenericDao<T extends Serializable> {

	public void setSessionFactory(SessionFactory sessionFactory);

	public SessionFactory getSessionFactory();

	/**
	 * Persist new entity p.
	 * 
	 * @param p Data type of entity.
	 * @throws PersistenceException
	 */
	public void add(T p) throws PersistenceException;

	/**
	 * Update entity p. Persists existing entity p.
	 * 
	 * @param p T Data type of entity
	 * @throws PersistenceException
	 */
	public void update(T p) throws PersistenceException;

	/**
	 * Returns a list of entities of the type T (clazz type).
	 * 
	 * @return List<T> List of entities T type.
	 * @throws PersistenceException
	 */
	public List<T> list() throws PersistenceException;

	/**
	 * Returns a entity t (clazz type) that it corresponds to the id passed.
	 * Made a searching by id.
	 * 
	 * @param id
	 * @return T Data type of entity
	 * @throws PersistenceException
	 */
	public T getById(Long id) throws PersistenceException;

	/**
	 * Get by Field. Search a entity T (clazz type) by field criteria.
	 * When the value of the passed field matches then returns the entity.
	 * 
	 * @param id
	 * @return T
	 * @throws PersistenceException
	 */
	public T getByField(String fieldName, String value)
			throws PersistenceException;

	/**
	 * Remove entity with id passed.
	 * 
	 * @param id Entity identifier
	 * @throws PersistenceException
	 */
	public void removeById(Long id) throws PersistenceException;

	/**
	 * Remove entity T (clazz type) passed.
	 * 
	 * @param t
	 * @throws PersistenceException
	 */
	public void remove(T t) throws PersistenceException;

	/**
	 * Search a entity list of T (clazz type) using a map with criteria.
	 * The parameter map is a collection of pairs (field name and value).
	 * 
	 * @param parameterMap
	 * @return List<T>
	 * @throws PersistenceException
	 */
	public List<T> search(Map<String, Object> parameterMap)
			throws PersistenceException;

	/**
	 * Get Entity T using a SQL query string and SqlQuery object.
	 * 
	 * @param sql
	 * @return Entity T type
	 * @throws PersistenceException
	 */
	public T executeSqlQuery(String sql) throws PersistenceException;

	/**
	 * Get Entity T using a SQL query string and SqlQuery object with parameters list.
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws PersistenceException
	 */
	public T executeSqlQuery(String sql, List<String> params) throws PersistenceException;
	
	/**
	 * Set if information logging is allow.
	 * 
	 * @param loggerInfoEnabled
	 */
	public void setLoggerInfoEnabled(boolean loggerEnabled);

	/**
	 * Return true if information logging is allow.
	 * @return boolean
	 */
	public boolean isLoggerInfoEnabled();

}
