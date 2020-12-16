package com.kaps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kaps.beans.Location;
import com.kaps.beans.NewUser;
import com.kaps.beans.Person;
import com.kaps.beans.PersonLocationAssn;
import com.kaps.beans.UserStatus;
import com.kaps.service.impl.PersonServiceImpl;

@Controller
public class MainController {

	@Autowired
	PersonServiceImpl personService;

	@RequestMapping(value = "test", method = RequestMethod.GET)
	public ResponseEntity<String> Test() {
		return new ResponseEntity<String>("Testing the api", HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "personinfo")
	public ResponseEntity<Person> getPersonDetails(@RequestBody Person user) {
		Person personDetails = null;
		try {
			personDetails = personService.getPersonDetailsById(user);
			if (personDetails != null) {
				return new ResponseEntity<Person>(personDetails, HttpStatus.OK);
			} else {
				return new ResponseEntity<Person>(personDetails, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Person>(personDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(method = RequestMethod.GET, value = "locationinfo")
	public ResponseEntity<Location> getPersonDetails(@RequestBody Location location) {
		Location locationDetails = null;
		try {
			locationDetails = personService.getLocationDetailsByName(location);
			if (locationDetails != null) {
				return new ResponseEntity<Location>(locationDetails, HttpStatus.OK);
			} else {
				return new ResponseEntity<Location>(locationDetails, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Location>(locationDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/insert-new-user-with-location", method=RequestMethod.POST)
	public ResponseEntity<String> insertNewUserWithLocation(@RequestBody NewUser newUser ){
		String result=null;
		try {
			if(newUser!=null) {
				result=personService.addNewUserwithLocation(newUser);
				return new ResponseEntity<String>(result,HttpStatus.OK);	
			}else {
				return new ResponseEntity<String>("please provide details",HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/update-person", method=RequestMethod.PUT)
	public ResponseEntity<Person> updateUser(@RequestBody Person person){
		Person personDetails=null;
		try {
			if(person!=null) {
				personDetails=personService.updateUser(person);
				return new ResponseEntity<Person>(personDetails,HttpStatus.OK);	
			}else {
				return new ResponseEntity<Person>(personDetails,HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Person>(personDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/update-location", method=RequestMethod.PUT)
	public ResponseEntity<Location> updateLoacation(@RequestBody Location location){
		Location locationDetails=null;
		try {
			if(location!=null) {
				locationDetails=personService.updateLocation(location);
				return new ResponseEntity<Location>(locationDetails,HttpStatus.OK);	
			}else {
				return new ResponseEntity<Location>(locationDetails,HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Location>(locationDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@RequestMapping(value="/deactivate-user", method=RequestMethod.PUT)
	public ResponseEntity<UserStatus> deactivateUser(@RequestBody UserStatus newUser){
		UserStatus personLocationAssnStatus=null;
		try {
			if(newUser!=null) {
				personLocationAssnStatus=personService.deactivateUser(newUser);
				if(personLocationAssnStatus!=null) {
					return new ResponseEntity<UserStatus>(personLocationAssnStatus,HttpStatus.OK);
				}else {
					return new ResponseEntity<UserStatus>(personLocationAssnStatus,HttpStatus.NOT_FOUND);
				}
			}else {
				return new ResponseEntity<UserStatus>(personLocationAssnStatus,HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<UserStatus>(personLocationAssnStatus, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping(value="/reactivate-user", method=RequestMethod.PUT)
	public ResponseEntity<UserStatus> reactivateUser(@RequestBody UserStatus newUser){
		UserStatus personLocationAssnStatus=null;
		try {
			if(newUser!=null) {
				personLocationAssnStatus=personService.reactivateUser(newUser);
				if(personLocationAssnStatus!=null) {
					return new ResponseEntity<UserStatus>(personLocationAssnStatus,HttpStatus.OK);
				}else {
					return new ResponseEntity<UserStatus>(personLocationAssnStatus,HttpStatus.NOT_FOUND);
				}
			}else {
				return new ResponseEntity<UserStatus>(personLocationAssnStatus,HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<UserStatus>(personLocationAssnStatus, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping(value="/assign-location", method=RequestMethod.POST)
	public ResponseEntity<String> assignLocation(@RequestBody UserStatus newUser ){
		try {
			if(newUser!=null) {
				Integer status=personService.statusEntry(newUser.getUserName(), newUser.getLocationName());
				if(status!=0) {
					return new ResponseEntity<String>("location assigned",HttpStatus.OK);	
				}else {
					return new ResponseEntity<String>("location failed to assign",HttpStatus.OK);
				}
			}else {
				return new ResponseEntity<String>("please provide details",HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
