package com.kaps.beans;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Person {

	public Integer personId;
	public String name;
	public String email;
	public Date birthdate;
	public String status;
	public Date createdDate;
	public Date updatedDate;

}
