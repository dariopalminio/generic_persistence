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



/**
 * @author Dario Palminio
 * 
 */
@Service
public abstract class GenericServiceImpl<T extends Serializable> implements GenericService<T>{
	

	protected GenericDao<T> dao;

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
	public void add(T p) {
		this.dao.add(p);
	}

	@Override
	@Transactional
	public void update(T p) {
		this.dao.update(p);
	}
	

	@Override
	@Transactional
	public List<T> list() {
		return this.dao.list();
	}

	@Override
	@Transactional
	public T getById(Long id) {
		return this.dao.getById(id);
	}

	@Override
	@Transactional
	public T getByField(String fieldName, String value){
		return this.dao.getByField(fieldName, value);
	}
	
	@Override
	@Transactional
	public void removeById(Long id) {
		this.dao.removeById(id);
	}

	@Override
	@Transactional
	public void remove(T t) {
		this.dao.remove(t);
	}
	
	@Override
	@Transactional
	public List<T> search(Map<String, Object> parameterMap) {
		// TODO Auto-generated method stub
		return this.dao.search(parameterMap);
	}
	
}