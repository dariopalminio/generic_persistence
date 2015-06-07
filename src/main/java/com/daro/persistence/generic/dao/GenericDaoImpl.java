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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.daro.persistence.generic.error.PersistenceError;
import com.daro.persistence.generic.error.PersistenceException;

/**
 * Generic DAO Implementation
 * 
 * Implements persistence in a data source using a Hibernate Session Factory.
 * 
 * @author Dario Palminio
 * 
 * @param <T> Data type expected in query return as entity
 */
@Repository
public abstract class GenericDaoImpl<T extends Serializable> implements
		GenericDao<T> {

	public boolean loggerInfoEnabled = true; //information logging

	private Logger logger;

	private Class<T> clazz; //entity class expected in query return
	
	@Autowired
	public SessionFactory sessionFactory;

    /**
     * Default Constructor
     */
    protected GenericDaoImpl() {
    }
    
	/**
	 * Constructor with parameters
	 * 
	 * @param clazz
	 * @param sessionFactory
	 */
	protected GenericDaoImpl(Class<T> clazz, SessionFactory sessionFactory) {
		this.clazz = clazz;
		this.sessionFactory = sessionFactory;
	}	

	/**
	 * Get SessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Set SessionFactory
	 * 
	 * @param clazz
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
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
	 * @param p Data type of entity.
	 * @throws PersistenceException
	 */
	@Override
	public void add(T p) throws PersistenceException {
		
		Session session = this.getCurrentSession();
		
		try{
			session.persist(p);
		}catch(java.lang.IllegalArgumentException illegalArgumentEx){
			logger.error("Persistence layer error: " + illegalArgumentEx.getStackTrace());
			PersistenceError error;
			if (p == null) {
				error = PersistenceError.ENTITY_NULL;
			}else{
				error = PersistenceError.PERSISTENCE_INTERNAL_ERROR;
			}
			throw new PersistenceException(error, illegalArgumentEx.getCause());
		}
		
		if (loggerInfoEnabled)
			logger.debug("Persistence layer info: " + "Saved successfully, Details=" + p);
	}

	/**
	 * Update entity p. Persists existing entity p.
	 * 
	 * @param p T Data type of entity
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
			PersistenceError error;
			if (p == null) {
				error = PersistenceError.ENTITY_NULL;
			}else{
				error = PersistenceError.PERSISTENCE_INTERNAL_ERROR;
			}
			throw new PersistenceException(error, illegalArgumentEx.getCause());
		}
		
		if (loggerInfoEnabled)
			logger.debug("Persistence layer info: " + "Person updated successfully, Person Details=" + p);
	}

	/**
	 * Returns a list of entities of the type T (clazz type).
	 * 
	 * @return List<T> List of entities T type.
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
	 * @return T Data type of entity
	 * @throws PersistenceException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T getById(Long id) throws PersistenceException {
		Session session = this.getCurrentSession();
		
		if (id == null) {
			logger.error("Persistence layer error: Illegal Argument in getById method, Id is null (" + PersistenceError.ARGUMENT_NULL.getMessage() + ")");
			throw new PersistenceException(PersistenceError.ARGUMENT_NULL);
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
	 * @param id Entity identifier
	 * @throws PersistenceException
	 */
	@Override
	@SuppressWarnings("unchecked")
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
	 * @param t
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
	@SuppressWarnings("unchecked")
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
        lst = (List<T>) criteria.list();
        
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
	 * Get Entity T using a SQL query string and SqlQuery object.
	 * 
	 * @param sql
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	public T executeSqlQuery(String sql) throws PersistenceException {
		T t = null;

		SQLQuery query = createSqlQuery(sql);
		
		try {
			query.addEntity(clazz);	
			t = (T) query.uniqueResult();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return t;
	}

	/**
	 * Get Entity T using a SQL query string and SqlQuery object with parameters list.
	 * 
	 * @param sql For example "SELECT * FROM table WHERE id = ?"
	 * @param params Parameters list to match with ? in SQL query
	 * @return Entity T type
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	public T executeSqlQuery(String sql, List<String> params) throws PersistenceException {
		T t = null;
		
		SQLQuery query = createSqlQuery(sql);
		
		try {
			query.addEntity(clazz);
			
			//inserts parameters into sql string replacing ? symbols by order
			for (int i = 0; i < params.size(); i++){
				query.setParameter(i, params.get(i));
			}
			
			t = (T) query.uniqueResult();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return t;
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
	 * @return Session Session instance
	 * @throws PersistenceException
	 */
	protected Session getCurrentSession() throws PersistenceException{		
		Session session = null;
		
		try{
			session = this.sessionFactory.getCurrentSession();
		}catch(java.lang.NullPointerException nullPpointEx){
			logger.error("Persistence layer error: " + nullPpointEx.getStackTrace());
			throw new PersistenceException(PersistenceError.SESSION_FACTORY_NULL, nullPpointEx.getCause());
		}catch(Exception ex){
			logger.error("Persistence layer error: " + ex.getStackTrace());
			PersistenceError error = PersistenceError.PERSISTENCE_INTERNAL_ERROR;
			throw new PersistenceException(error, ex.getCause());
		}
		
		return session;
	}
	
    /**
     * Return a SQLQuery from SQL string passed as argument.
     *
     * @param String sql
     * @return SQLQuery with an instance of the sql query
     * @throws PersistenceException
     */
    protected SQLQuery createSqlQuery(String sql) throws PersistenceException{

        if (sql == null) {
            throw new PersistenceException(PersistenceError.SQL_IS_NULL);
        }

        return getCurrentSession().createSQLQuery(sql);
    }

}
