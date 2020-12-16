package com.kaps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaps.beans.Location;
import com.kaps.beans.NewUser;
import com.kaps.beans.Person;
import com.kaps.beans.PersonLocationAssn;
import com.kaps.beans.UserStatus;
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
			Location loc = new Location();
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
							temp = mapper.insertNewUser(user);
							if (temp != 0) {
								Integer entryStatus=statusEntry(userName, locationName);
								if(entryStatus!=0) {
									System.out.println("status entry created.");
									return "new user created with exist location successfully";
								}
									System.out.println("status entry failed to create.");
									return "new user with exist location created successfully but failed to status entry";
							} else {
								return "new user failed to create";
							}
						} else {
							temp = addNewLocation(locationName);
							if (temp != 0) {
								System.out.println("new location has been created");
								locDetails = getLocationDetailsByName(loc);
								if (locDetails != null) {
									temp = mapper.insertNewUser(user);
									if (temp != 0) {
										Integer entryStatus=statusEntry(userName, locationName);
										if(entryStatus!=0) {
											System.out.println("status entry created.");
											return "new user created with new location successfully";
										}
											System.out.println("status entry failed to create.");
											return "new user and location created successfully but failed to status entry";
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
			Location loc = new Location();
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
			if (locationDetails != null) {
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

	@Override
	public UserStatus deactivateUser(UserStatus newUser) {
		PersonLocationAssn personLocationAssn = null;
		UserStatus userStatus = new UserStatus();
		try {
			personLocationAssn = plaDetailsByNames(newUser);
			if (personLocationAssn != null) {
				if (personLocationAssn.getStatus().equals("active")) {
					personLocationAssn.setStatus("inactive");
					int result = mapper.activateDeactiveUser(personLocationAssn);
					if (result != 0) {
						personLocationAssn = mapper.getPersonLocationDetails(personLocationAssn);
						userStatus.setUserName(newUser.getUserName());
						userStatus.setLocationName(newUser.getLocationName());
						userStatus.setStatus(personLocationAssn.getStatus());
						return userStatus;
					}
				} else {
					userStatus.setUserName(newUser.getUserName());
					userStatus.setLocationName(newUser.getLocationName());
					userStatus.setStatus(personLocationAssn.getStatus());
					return userStatus;
				}
			} else {
				userStatus = null;
				return userStatus;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		userStatus = null;
		return userStatus;
	}

	@Override
	public UserStatus reactivateUser(UserStatus newUser) {
		PersonLocationAssn personLocationAssn = null;
		UserStatus userStatus = new UserStatus();
		try {
			personLocationAssn = plaDetailsByNames(newUser);
			if (personLocationAssn != null) {
				if (personLocationAssn.getStatus().equals("inactive")) {
					mapper.deactivateUser(personLocationAssn);
					personLocationAssn.setStatus("active");
					int result = mapper.activateDeactiveUser(personLocationAssn);
					if (result != 0) {
						personLocationAssn = mapper.getPersonLocationDetails(personLocationAssn);
						userStatus.setUserName(newUser.getUserName());
						userStatus.setLocationName(newUser.getLocationName());
						userStatus.setStatus(personLocationAssn.getStatus());
						return userStatus;
					}
				} else {
					userStatus.setUserName(newUser.getUserName());
					userStatus.setLocationName(newUser.getLocationName());
					userStatus.setStatus(personLocationAssn.getStatus());
					return userStatus;
				}
			} else {
				userStatus = null;
				return userStatus;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		userStatus = null;
		return userStatus;
	}
	
	public Integer statusEntry(String userName, String locationName) {
		Location locationDetails = null;
		Person personDetails = null;
		Integer result = 0;
		try {
			Person person = new Person();
			person.setName(userName);
			Location location = new Location();
			location.setName(locationName);
			locationDetails = getLocationDetailsByName(location);
			personDetails = getPersonDetailsById(person);
			if (locationDetails != null && personDetails != null) {
				PersonLocationAssn personLocationAssn = new PersonLocationAssn();
				personLocationAssn.setPersonId(personDetails.getPersonId());
				personLocationAssn.setLocationId(locationDetails.getLocationId());
				if(mapper.getPersonLocationDetails(personLocationAssn)==null)
				result = mapper.insertStatusEntry(personLocationAssn);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public PersonLocationAssn plaDetailsByNames(UserStatus newUser) {
		Location locationDetails = null;
		Person personDetails = null;
		PersonLocationAssn personLocationAssn = null;
		try {
			Person person = new Person();
			person.setName(newUser.getUserName());
			Location location = new Location();
			location.setName(newUser.getLocationName());
			locationDetails = getLocationDetailsByName(location);
			personDetails = getPersonDetailsById(person);
			if (locationDetails != null && personDetails != null) {
				personLocationAssn = new PersonLocationAssn();
				personLocationAssn.setPersonId(personDetails.getPersonId());
				personLocationAssn.setLocationId(locationDetails.getLocationId());
				personLocationAssn = mapper.getPersonLocationDetails(personLocationAssn);
				return personLocationAssn;
			} else {
				return personLocationAssn;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personLocationAssn;
	}
}
