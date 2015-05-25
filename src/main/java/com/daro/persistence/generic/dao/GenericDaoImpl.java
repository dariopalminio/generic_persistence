/**
 * Author: Dario Palminio
 * License: GPLv3 (http://www.gnu.org/copyleft/gpl.html)
 */
package com.daro.persistence.generic.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * GenericDaoImpl
 * 
 * @author Dario Palminio
 * 
 */
@Repository
public abstract class GenericDaoImpl<T extends Serializable> implements
		GenericDao<T> {

	public boolean loggerEnabled = true;

	Logger logger;

	private Class<T> clazz;

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}		

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setClazz(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
		this.logger = Logger.getLogger(this.clazz);
	}

	@Override
	public void add(T p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		if (loggerEnabled)
			logger.debug("Saved successfully, Details=" + p);
	}

	@Override
	public void update(T p) {
		Session session = this.sessionFactory.getCurrentSession();
		//session.update(p);
		session.merge(p);
		if (loggerEnabled)
			logger.debug("Person updated successfully, Person Details=" + p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> list() {
		Session session = this.sessionFactory.getCurrentSession();
		List<T> lst = session.createQuery("from " + clazz.getSimpleName())
				.list();
		
		if (loggerEnabled && !(lst.isEmpty())){
			logger.debug(clazz.getSimpleName()
					+ " search successfully,  list size=" + lst.size());
			for (T elem : lst) {
				logger.debug(clazz.getSimpleName() + " List::" + elem);
			}
		}
		
		return lst;
	}

	@Override
	public T getById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		T t = (T) session.load(clazz, id.longValue());
		if (loggerEnabled)
			logger.debug(clazz.getSimpleName()
					+ " loaded successfully, details=" + t);
		return t;
	}

	@Override
	public void removeById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		T t = (T) session.load(clazz, id.longValue());
		if (null != t) {
			session.delete(t);
			if (loggerEnabled)
				logger.debug(clazz.getSimpleName()
						+ " deleted successfully,  details=" + t);
		}else{
			if (loggerEnabled)
				logger.debug(clazz.getSimpleName()
						+ " can not be deleted because is null!");
		}
	}

	@Override
	public void remove(T t) {
		Session session = this.sessionFactory.getCurrentSession();
		if (null != t) {
			session.delete(t);
			if (loggerEnabled)
				logger.debug(clazz.getSimpleName()
						+ " deleted successfully,  details=" + t);
		}else{
			if (loggerEnabled)
				logger.debug(clazz.getSimpleName()
						+ " can not be deleted because is null!");
		}
	}
	
	@Override
	public T getByField(String fieldName, String value) {
		T founded = null;
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(fieldName, value);
		List<T> lst = this.search(parameterMap);
		if (! lst.isEmpty()){
			founded = lst.get(0);
		}
		return founded;
	}
	
	@Override
    public List<T> search(Map<String, Object> parameterMap) {
    	logger.debug(clazz.getSimpleName() + " ######### SEARCH ##################" );
    	List<T> lst = null;
    	//try{
        Session session = this.sessionFactory.getCurrentSession();
        if (this.sessionFactory.isClosed()){
        		logger.debug("Session Factory is Closed!");
        	}
        //org.hibernate.HibernateException: No Session found for current thread
        //session.getTransaction().begin();
        Criteria criteria = session.createCriteria(clazz);
        Set<String> fieldName = parameterMap.keySet();
        for (String field : fieldName) {
            criteria.add(Restrictions.ilike(field, parameterMap.get(field)));
        }
        lst = criteria.list();
        
		if (loggerEnabled && !(lst.isEmpty())){
			logger.debug(clazz.getSimpleName()
					+ " search successfully,  list size=" + lst.size());
			for (T elem : lst) {
				logger.debug(clazz.getSimpleName() + " List::" + elem);
			}
		}
		//session.getTransaction().commit();
        /*} catch (Exception e) {
            //throw new BookStoreDAORuntimeException(e.getMessage(), e);
        	logger.error(e.getStackTrace());
        }*/
        return lst;
    }
    

	public boolean isLoggerEnabled() {
		return loggerEnabled;
	}


	public void setLoggerEnabled(boolean loggerEnabled) {
		this.loggerEnabled = loggerEnabled;
	}

	
}
