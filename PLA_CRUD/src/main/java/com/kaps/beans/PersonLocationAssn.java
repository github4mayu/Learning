package com.kaps.beans;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PersonLocationAssn {

	public Integer id;
	public Integer personId;
	public Integer locationId;
	public String status;
	public Date createdDate;
	public Date updatedDate;
}
