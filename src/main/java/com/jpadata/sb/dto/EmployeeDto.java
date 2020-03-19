package com.jpadata.sb.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeDto {

@JsonProperty
private long id;

@JsonProperty
private String fName;

@JsonProperty
private String lName;

@JsonProperty
private String email;


public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getfName() {
	return fName;
}

public void setfName(String fName) {
	this.fName = fName;
}

public String getlName() {
	return lName;
}

public void setlName(String lName) {
	this.lName = lName;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

}
