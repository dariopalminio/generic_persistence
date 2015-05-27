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
 * Implements persistence in a data source using a Hibernate Session Factory.
 * 
 * @author Dario Palminio
 * 
 */
@Repository
public abstract class GenericDaoImpl<T extends Serializable> implements
		GenericDao<T> {

	public boolean loggerInfoEnabled = true; //Information logging

	private Logger logger;

	private Class<T> clazz;

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}		

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Set specific T class for Java Generic.
	 * 
	 * @param clazzToSet
	 */
	public void setClazz(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
		this.logger = Logger.getLogger(this.clazz); //Initialize Logger
	}

	/**
	 * Persist new entity p.
	 * 
	 * @param p
	 * @throws PersistenceException
	 */
	@Override
	public void add(T p) throws PersistenceException {
		
		Session session = this.getCurrentSession();
		
		try{
			session.persist(p);
		}catch(java.lang.IllegalArgumentException illegalArgumentEx){
			logger.error("Persistence layer error: " + illegalArgumentEx.getStackTrace());
			String msg;
			if (p == null) {
				msg = PersistenceException.ENTITY_NULL;
			}else{
				msg = illegalArgumentEx.getMessage();
			}
			throw new PersistenceException(msg, illegalArgumentEx.getCause());
		}
		
		if (loggerInfoEnabled)
			logger.debug("Persistence layer info: " + "Saved successfully, Details=" + p);
	}

	/**
	 * Update entity p. Persists existing entity p.
	 * 
	 * @param p
	 * @throws PersistenceException
	 */
	@Override
	public void update(T p) throws PersistenceException {
		Session session = this.getCurrentSession();
		
		try{
			//session.update(p);
			session.merge(p);
		}catch(java.lang.IllegalArgumentException illegalArgumentEx){
			logger.error("Persistence layer error: " + illegalArgumentEx.getStackTrace());
			String msg;
			if (p == null) {
				msg = PersistenceException.ENTITY_NULL;
			}else{
				msg = illegalArgumentEx.getMessage();
			}
			throw new PersistenceException(msg, illegalArgumentEx.getCause());
		}
		
		if (loggerInfoEnabled)
			logger.debug("Persistence layer info: " + "Person updated successfully, Person Details=" + p);
	}

	/**
	 * Returns a list of entities of the type T (clazz type).
	 * 
	 * @return List<T>
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> list() throws PersistenceException {
		Session session = this.getCurrentSession();
		String strQuery = "from " + clazz.getSimpleName();
		List<T> lst = session.createQuery(strQuery).list();
		
		if (loggerInfoEnabled && !(lst.isEmpty())){
			logger.debug("Persistence layer info: " + clazz.getSimpleName()
					+ " search successfully,  list size=" + lst.size());
			for (T elem : lst) {
				logger.debug("Persistence layer info: " + clazz.getSimpleName() + " List::" + elem);
			}
		}
		
		return lst;
	}

	/**
	 * Returns a entity t (clazz type) that it corresponds to the id passed.
	 * Made a searching by id.
	 * 
	 * @param id
	 * @return T
	 * @throws PersistenceException
	 */
	@Override
	public T getById(Long id) throws PersistenceException {
		Session session = this.getCurrentSession();
		
		if (id == null) {
			logger.error("Persistence layer error: Illegal Argument in getById method, Id is null (" + PersistenceException.ARGUMENT_NULL + ")");
			throw new PersistenceException(PersistenceException.ARGUMENT_NULL);
		}
		
		T t = (T) session.load(clazz, id.longValue());
		
		if (loggerInfoEnabled)
			logger.debug("Persistence layer info: " + clazz.getSimpleName()
					+ " loaded successfully, details=" + t);
		
		return t;
	}

	/**
	 * Remove entity with id passed.
	 * 
	 * @param id
	 * @throws PersistenceException
	 */
	@Override
	public void removeById(Long id) throws PersistenceException {
		Session session = this.getCurrentSession();
		T t = (T) session.load(clazz, id.longValue());
		if (null != t) {
			session.delete(t);
			if (loggerInfoEnabled)
				logger.debug("Persistence layer info: " + clazz.getSimpleName()
						+ " deleted successfully,  details=" + t);
		}else{
			if (loggerInfoEnabled)
				logger.debug("Persistence layer info: " + clazz.getSimpleName()
						+ " can not be deleted because is null!");
		}
	}

	/**
	 * Remove entity T (clazz type) passed.
	 * 
	 * @param id
	 * @throws PersistenceException
	 */
	@Override
	public void remove(T t) throws PersistenceException {
		Session session = this.getCurrentSession();
		if (null != t) {
			session.delete(t);
			if (loggerInfoEnabled)
				logger.debug("Persistence layer info: " + clazz.getSimpleName()
						+ " deleted successfully,  details=" + t);
		}else{
			if (loggerInfoEnabled)
				logger.debug("Persistence layer info: " + clazz.getSimpleName()
						+ " can not be deleted because is null!");
		}
	}
	
	/**
	 * Get by Field. Search a entity T (clazz type) by field criteria.
	 * When the value of the passed field matches then returns the entity.
	 * 
	 * @param id
	 * @return T
	 * @throws PersistenceException
	 */
	@Override
	public T getByField(String fieldName, String value) throws PersistenceException {
		T founded = null;
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put(fieldName, value);
		
		List<T> lst = this.search(parameterMap);
		
		if (! lst.isEmpty()){
			founded = lst.get(0);
		}
		
		if (loggerInfoEnabled)
			logger.debug("Persistence layer info: " + clazz.getSimpleName()
					+ " founded=" + founded);
		
		return founded;
	}
	
	/**
	 * Search a entity list of T (clazz type) using a map with criteria.
	 * The parameter map is a collection of pairs (field name and value).
	 * 
	 * @param parameterMap
	 * @return List<T>
	 * @throws PersistenceException
	 */
	@Override
    public List<T> search(Map<String, Object> parameterMap) throws PersistenceException {
    	//logger.debug(clazz.getSimpleName() + " ######### SEARCH ##################" );
    	List<T> lst = null;
    	//try{
        Session session = this.getCurrentSession();
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
        
		if (loggerInfoEnabled && !(lst.isEmpty())){
			logger.debug("Persistence layer info: " + clazz.getSimpleName()
					+ " search successfully,  list size=" + lst.size());
			for (T elem : lst) {
				logger.debug("Persistence layer info: " + clazz.getSimpleName() + " List::" + elem);
			}
		}
		//session.getTransaction().commit();
        /*} catch (Exception e) {
            //throw new BookStoreDAORuntimeException(e.getMessage(), e);
        	logger.error(e.getStackTrace());
        }*/
        return lst;
    }

	/**
	 * Return true if information logging is allow.
	 * @return boolean
	 */
	public boolean isLoggerInfoEnabled() {
		return loggerInfoEnabled;
	}

	/**
	 * Set if information logging is allow.
	 * 
	 * @param loggerInfoEnabled
	 */
	public void setLoggerInfoEnabled(boolean loggerInfoEnabled) {
		this.loggerInfoEnabled = loggerInfoEnabled;
	}
	
	/**
	 * Get Current Session from Session Factory.
	 * 
	 * @return Session
	 * @throws PersistenceException
	 */
	private Session getCurrentSession() throws PersistenceException{		
		Session session = null;
		
		try{
			session = this.sessionFactory.getCurrentSession();
		}catch(java.lang.NullPointerException nullPpointEx){
			logger.error("Persistence layer error: " + nullPpointEx.getStackTrace());
			throw new PersistenceException(PersistenceException.SESSION_FACTORY_NULL, nullPpointEx.getCause());
		}catch(Exception ex){
			logger.error("Persistence layer error: " + ex.getStackTrace());
			throw new PersistenceException(ex.getMessage(),ex.getCause());
		}
		
		return session;
	}
	
}
