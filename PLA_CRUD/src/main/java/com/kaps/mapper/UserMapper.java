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
	
	@Insert("INSERT INTO LOCATION(name,created_date,updated_date) values(#{locationName},now(),null)")
	public int insertNewLocation(@Param("locationName") String locationName);
	
	@Insert("insert into person(name,email,birthdate,city_id,status,created_date,updated_date) \r\n" + 
			"values(#{name},#{email},#{birthdate},#{cityId},#{status},now(),null)")
	public int insertNewUser(NewUser user);
	
	@Update("UPDATE person SET name=#{name},email=#{email},birthdate=#{birthdate},city_id=#{cityId},status=#{status},updated_date=now() where id=#{personId}")
	public int updateUser(Person person);
	
	@Update("UPDATE location SET name=#{name},updated_date=now() where id=#{locationId}")
	public int updateLocation(Location location);
}
