/**
 * Author: Dario Palminio
 * License: GPLv3 (http://www.gnu.org/copyleft/gpl.html)
 */
package com.daro.persistence.generic.error;

/**
 * 
 * @author dario.palminio
 *
 */
public enum PersistenceErrors {

	/**
	 * List of errors messages
	 */
	UNIDENTIFIED_ERROR(0, "UNIDENTIFIED ERROR!"),
	ENTITY_NULL(1, "Can not persist a null entity!"),
	SESSION_FACTORY_NULL(2, "Can not get SessionFactory because SessionFactory is null."),
	ARGUMENT_NULL(3, "The argument is null!");
	
	private int code;
	private String message;
	
	private PersistenceErrors(int code, String message){
		this.code=code;
		this.message=message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}
	
	public String getWord() {
		return this.name();
	}
	
	@Override
	public String toString(){
		return this.getMessage();		
	}
}
