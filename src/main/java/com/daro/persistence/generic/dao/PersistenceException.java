/**
 * Author: Dario Palminio
 * License: GPLv3 (http://www.gnu.org/copyleft/gpl.html)
 */
package com.daro.persistence.generic.dao;

/**
 * PersistenceException
 * 
 * @author dario.palminio
 *
 */
public class PersistenceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * List of errors messages
	 */
	public static final String ENTITY_NULL = "Can not persist a null entity!";
	public static final String SESSION_FACTORY_NULL = "Can not get SessionFactory because SessionFactory is null.";
	public static final String ARGUMENT_NULL = "The argument is null!";
	
	public PersistenceException(){
	}

	public PersistenceException(String message){
		super(message);
	}

	public PersistenceException(Throwable cause){
		super(cause);
	}

	public PersistenceException(String message, Throwable cause){
		super(message, cause);
	}

}
