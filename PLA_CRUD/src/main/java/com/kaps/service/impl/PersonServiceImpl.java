package com.kaps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaps.beans.Location;
import com.kaps.beans.NewUser;
import com.kaps.beans.Person;
import com.kaps.mapper.UserMapper;
import com.kaps.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
	@Autowired
	private UserMapper mapper;

	@Override
	public Person getPersonDetailsById(Person user) {
		Person personDetails = null;
		try {
			personDetails = mapper.getPersonDetails(user);
			return personDetails;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personDetails;
	}

	@Override
	public Location getLocationDetailsByName(Location location) {
		Location locationDetails = null;
		try {
			locationDetails = mapper.getLocationDetails(location);
			return locationDetails;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locationDetails;
	}

	@Override
	public String addNewUserwithLocation(NewUser user) {
		String result = null;
		Location locDetails = null;
		int temp = 0;
		try {
			String locationName = user.getLocationName();
			String userName = user.getName();
			Location loc=new Location();
			loc.setName(locationName);
			
			if (userName != null) {
				Person person = new Person();
				person.setName(userName);
				Person existUserDetails = getPersonDetailsById(person);
				if (existUserDetails != null) {
					if (existUserDetails.getName().equals(userName))
						return "username already exist";
				} else {
					if (locationName != null) {
						locDetails = getLocationDetailsByName(loc);
						if (locDetails != null) {
							user.setCityId(locDetails.getLocationId());
							temp = mapper.insertNewUser(user);
							if (temp != 0) {
								return "new user created successfully";
							} else {
								return "new user failed to create";
							}
						} else {
							temp = addNewLocation(locationName);
							if (temp != 0) {
								System.out.println("new location has been created");
								locDetails = getLocationDetailsByName(loc);
								if (locDetails != null) {
									user.setCityId(locDetails.getLocationId());
									temp = mapper.insertNewUser(user);
									if (temp != 0) {
										return "new user created with new location successfully";
									} else {
										return "new user failed to create";
									}
								}
							}
						}
					} else {
						return "provide valid location name";
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer addNewLocation(String locationName) {
		int temp = 0;
		Location locDetails = null;
		try {
			Location loc=new Location();
			loc.setName(locationName);
			if (locationName != null) {
				locDetails = getLocationDetailsByName(loc);
				if (locDetails == null) {
					temp = mapper.insertNewLocation(locationName);
					if (temp != 0) {
						System.out.println("new location has been created");
						return temp;
					} else {
						System.out.println("failed to create new location");
						return temp;
					}
				} else {
					System.out.println("location already exist");
					return temp;
				}
			} else {
				System.out.println("location name missing");
				return temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public Person updateUser(Person person) {
		Person personDetails = null;
		try {
			personDetails = getPersonDetailsById(person);
			if (personDetails != null) {
				int temp = mapper.updateUser(person);
				if (temp != 0) {
					personDetails = getPersonDetailsById(person);
					return personDetails;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personDetails;
	}

	@Override
	public Location updateLocation(Location location) {
		Location locationDetails = null;
		try {
			locationDetails = getLocationDetailsByName(location);
			if(locationDetails!=null) {
			int temp = mapper.updateLocation(location);
			if (temp != 0) {
				locationDetails = getLocationDetailsByName(location);
				return locationDetails;
			}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locationDetails;
	}

}
