/**
 * Author: Dario Palminio
 * License: GPLv3 (http://www.gnu.org/copyleft/gpl.html)
 */
package com.daro.persistence.generic.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daro.persistence.generic.dao.GenericDao;
import com.daro.persistence.generic.error.PersistenceException;



/**
 * Generic Service Implementation.
 * This works as a bridge between the service client and the DAO.
 * 
 * @author Dario Palminio
 * 
 * @param <T> Data type expected in query return as entity
 */
@Service
public abstract class GenericServiceImpl<T extends Serializable> implements GenericService<T>{
	

	protected GenericDao<T> dao; //persistence object

	public void setDao(GenericDao<T> dao) {
		this.dao = dao;
	}

	public GenericDao<T> getDao() {
		return dao;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		dao.setSessionFactory(sessionFactory);
		
	}

	public SessionFactory getSessionFactory() {
		return dao.getSessionFactory();
	}
	
	@Override
	@Transactional
	public void add(T p) throws PersistenceException{
		this.dao.add(p);
	}

	@Override
	@Transactional
	public void update(T p) throws PersistenceException {
		this.dao.update(p);
	}
	

	@Override
	@Transactional
	public List<T> list() throws PersistenceException {
		return this.dao.list();
	}

	@Override
	@Transactional
	public T getById(Long id) throws PersistenceException {
		return this.dao.getById(id);
	}

	@Override
	@Transactional
	public T getByField(String fieldName, String value) throws PersistenceException{
		return this.dao.getByField(fieldName, value);
	}
	
	@Override
	@Transactional
	public void removeById(Long id) throws PersistenceException {
		this.dao.removeById(id);
	}

	@Override
	@Transactional
	public void remove(T t) throws PersistenceException {
		this.dao.remove(t);
	}
	
	@Override
	@Transactional
	public List<T> search(Map<String, Object> parameterMap) throws PersistenceException {
		return this.dao.search(parameterMap);
	}
	
	@Override
	@Transactional
	public T executeSqlQuery(String sql) throws PersistenceException{
		return this.dao.executeSqlQuery(sql);
	}
	
	@Override
	@Transactional
	public T executeSqlQuery(String sql, List<String> params) throws PersistenceException{
		return this.dao.executeSqlQuery(sql, params);		
	}
	
	@Override
	public void setLoggerInfoEnabled(boolean loggerEnabled) {
		this.dao.setLoggerInfoEnabled(loggerEnabled);		
	}

	@Override
	public boolean isLoggerInfoEnabled() {
		return this.dao.isLoggerInfoEnabled();
	}
}