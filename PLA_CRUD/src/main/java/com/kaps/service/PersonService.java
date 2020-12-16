package com.kaps.service;

import com.kaps.beans.Location;
import com.kaps.beans.NewUser;
import com.kaps.beans.Person;
import com.kaps.beans.UserStatus;

public interface PersonService {

	public Person getPersonDetailsById(Person user);
	public Location getLocationDetailsByName(Location location);
	public String addNewUserwithLocation(NewUser user);
	public Integer addNewLocation(String locationName);
	public Person updateUser(Person person);
	public Location updateLocation(Location location);
	public UserStatus deactivateUser(UserStatus userStatus);
	public Integer statusEntry(String userName,String locationName);
	public UserStatus reactivateUser(UserStatus newUser);
}
