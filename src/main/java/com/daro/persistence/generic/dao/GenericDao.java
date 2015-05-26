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
	public void add(T p);
	public void update(T p);
	public List<T> list();
	public T getById(Long id);
	public T getByField(String fieldName, String value);
	public void removeById(Long id);
	public void remove(T t);
	public List<T> search(Map<String, Object> parameterMap);
	
}

