/**
 * Author: Dario Palminio
 * License: GPLv3 (http://www.gnu.org/copyleft/gpl.html)
 */
package com.daro.persistence.generic.error;

/**
 * Enum of errors code and messages
 * 
 * @author dario.palminio
 *
 */
public enum PersistenceError {

	/**
	 * List of errors code and messages
	 */
	UNIDENTIFIED_ERROR(0, "UNIDENTIFIED ERROR"),
	PERSISTENCE_INTERNAL_ERROR(1, "PERSISTENCE INTERNAL ERROR"),
	ENTITY_NULL(2, "Can not persist a null entity!"),
	SESSION_FACTORY_NULL(3, "Can not get SessionFactory because SessionFactory is null."),
	ARGUMENT_NULL(4, "The argument is null!"),
	SQL_IS_NULL(5,"SQL query string is null");
	
	private int code;
	private String message;
	
	/**
	 * Constructor.
	 * 
	 * @param code
	 * @param message
	 */
	private PersistenceError(int code, String message){
		this.code=code;
		this.message=message;
	}

	/**
	 * Get exception code.
	 * 
	 * @param code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Get exception message.
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Get dictionary word.
	 * This dictionary word can be used to internationalization.
	 * 
	 * @return String
	 */
	public String getWord() {
		return this.name();
	}
	
	@Override
	public String toString(){
		return this.getMessage();		
	}
	
	/**
	 * Get integer Error Code from a string Message
	 * 
	 * @param error
	 * @return integer
	 */
	public static int getErrorCodeFromMessage (String stringMessageError){
		if (stringMessageError != null){
			for (PersistenceError e : PersistenceError.values()){
				if(stringMessageError.matches(e.message)){
					return e.code;
				}
			}
		}
		return UNIDENTIFIED_ERROR.code;
	}
	
}
