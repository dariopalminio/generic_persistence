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

	private PersistenceError persistenceErrors;
	
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
	public PersistenceException(PersistenceError persistenceErrors){
		super(persistenceErrors.getMessage());
		this.persistenceErrors=persistenceErrors;
	}

	public PersistenceException(Throwable cause){
		super(cause);
	}

	public PersistenceException(PersistenceError persistenceErrors, Throwable cause){
		super(persistenceErrors.getMessage(), cause);
		this.persistenceErrors=persistenceErrors;
	}

	public PersistenceError getPersistenceErrors() {
		return persistenceErrors;
	}

	public void setPersistenceErrors(PersistenceError persistenceErrors) {
		this.persistenceErrors = persistenceErrors;
	}

	
}
