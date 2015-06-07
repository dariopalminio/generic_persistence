/**
 * Author: Dario Palminio
 * License: GPLv3 (http://www.gnu.org/copyleft/gpl.html)
 */
package com.daro.persistence.generic.error;


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

	private PersistenceError persistenceError;
	
	/**
	 * Constructor.
	 */
	public PersistenceException(){
	}

	/**
	 * Constructor with parameter.
	 * 
	 * @param persistenceErrors
	 */
	public PersistenceException(PersistenceError persistenceError){
		super(persistenceError.getMessage());
		this.persistenceError = persistenceError;
	}

	/**
	 * Constructor using other Exception or Throwable.
	 * 
	 * @param cause
	 */
	public PersistenceException(Throwable cause){
		super(cause);
		this.persistenceError = PersistenceError.PERSISTENCE_INTERNAL_ERROR;
	}

	/**
	 * Constructor with PersistenceError and Throwable as arguments
	 * 
	 * @param persistenceErrors
	 * @param cause
	 */
	public PersistenceException(PersistenceError persistenceError, Throwable cause){
		super(persistenceError.getMessage(), cause);
		this.persistenceError = persistenceError;
	}

	/**
	 * Get PersistenceError
	 * 
	 * @return
	 */
	public PersistenceError getPersistenceError() {
		return persistenceError;
	}

	/**
	 * Set PersistenceError
	 * 
	 * @param persistenceError
	 */
	public void setPersistenceError(PersistenceError persistenceError) {
		this.persistenceError = persistenceError;
	}
	
}
