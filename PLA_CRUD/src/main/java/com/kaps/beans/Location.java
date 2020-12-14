package com.kaps.beans;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Location {

	public Integer locationId;
	public String name;
	public Date createdDate;
	public Date updatedDate;
}
