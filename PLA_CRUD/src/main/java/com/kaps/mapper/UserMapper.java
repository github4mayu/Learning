package com.kaps.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kaps.beans.Location;
import com.kaps.beans.NewUser;
import com.kaps.beans.Person;
import com.kaps.beans.PersonLocationAssn;

@Mapper
public interface UserMapper {

	@Select("SELECT * FROM person where id=#{personId} OR name=#{name}")
	@Results(value ={
		@Result(property = "personId", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "email", column = "email"),
		@Result(property = "birthdate", column = "birthdate"),
		@Result(property = "status", column = "status"),
		@Result(property = "cityId", column = "city_id"),
		@Result(property = "createdDate", column = "created_date"),
		@Result(property = "updatedDate", column = "updated_date"),
	})
	public Person getPersonDetails(Person user);
	
	
	@Select("SELECT * FROM location where id=#{locationId} OR name=#{name}")
	@Results(value= {
		@Result(property="locationId", column="id"),
		@Result(property="name", column="name"),
		@Result(property="createdDate", column="created_date"),
		@Result(property="updatedDate", column="updated_date")
	})
	public Location getLocationDetails(Location location);
	

	@Select("SELECT * FROM Person_Location_assn where person_id=#{personId} and location_id=#{locationId}")
	@Results(value= {
		@Result(property="id", column="id"),
		@Result(property="personId", column="person_id"),
		@Result(property="locationId", column="location_id"),
		@Result(property="status", column="status"),
		@Result(property="createdDate", column="created_date"),
		@Result(property="updatedDate", column="updated_date")
	})
	public PersonLocationAssn getPersonLocationDetails(PersonLocationAssn personLocationAssn);
		
	@Insert("INSERT INTO LOCATION(name,created_date,updated_date) values(#{locationName},now(),null)")
	public int insertNewLocation(@Param("locationName") String locationName);
	
	@Insert("insert into person(name,email,birthdate,status,created_date,updated_date) \r\n" + 
			"values(#{name},#{email},#{birthdate},#{status},now(),null)")
	public int insertNewUser(NewUser user);
	
	@Update("UPDATE person SET name=#{name},email=#{email},birthdate=#{birthdate},status=#{status},updated_date=now() where id=#{personId}")
	public int updateUser(Person person);
	
	@Update("UPDATE location SET name=#{name},updated_date=now() where id=#{locationId}")
	public int updateLocation(Location location);
	
	@Update("UPDATE Person_Location_assn set status='inactive' where person_id=#{personId} and status='active'")
	public int deactivateUser(PersonLocationAssn personLocationAssn);
	
	@Insert("INSERT INTO Person_Location_assn (person_id,location_id,status,created_date,updated_date) "
			+ "values (#{personId},#{locationId},'active',now(),null)")
	public int insertStatusEntry(PersonLocationAssn personLocationAssn);

	@Update("UPDATE Person_Location_assn set status=#{status} where person_id=#{personId} and location_id=#{locationId}")
	public int activateDeactiveUser(PersonLocationAssn personLocationAssn);
	
}
