package com.daro.persistence.generic.testdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Dario Palminio
 * 
 */
@Entity
@Table(name = "PERSONENTITY")
public class PersonEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", nullable = false)
	private Long id;
	
	@Column(name="FIRSTNAME", nullable = false, length = 40)
	private String firstName;
	
	@Column(name="LASTNAME", nullable = false, length = 40)
	private String lastName; 
	
	@Column(name="DNICODE", nullable = false, length = 40)
	private String dniCode;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDniCode() {
		return dniCode;
	}

	public void setDniCode(String dniCode) {
		this.dniCode = dniCode;
	}

	public String getFullName() {
		return lastName + " " + firstName;
	}
	
	@Override
	public String toString() {
	  return "Person: firstName" + this.firstName;
	}
    
	
}
