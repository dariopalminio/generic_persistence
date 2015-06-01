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

	PersistenceErrors persistenceErrors;
	
	public PersistenceException(){
	}

	public PersistenceException(PersistenceErrors persistenceErrors){
		super(persistenceErrors.getMessage());
		this.persistenceErrors=persistenceErrors;
	}

	public PersistenceException(Throwable cause){
		super(cause);
	}

	public PersistenceException(PersistenceErrors persistenceErrors, Throwable cause){
		super(persistenceErrors.getMessage(), cause);
		this.persistenceErrors=persistenceErrors;
	}

	public PersistenceErrors getPersistenceErrors() {
		return persistenceErrors;
	}

	public void setPersistenceErrors(PersistenceErrors persistenceErrors) {
		this.persistenceErrors = persistenceErrors;
	}

	
}
