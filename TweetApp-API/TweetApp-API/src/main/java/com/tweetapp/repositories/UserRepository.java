package com.tweetapp.repositories;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.entities.UserModel;

@Repository
@EnableScan
public interface UserRepository extends CrudRepository<UserModel, String>{
	
	UserModel findByUsername(String username);
	
	@Query(fields =  "{'username':{'$regex':'?0','$options':'i'}}")
	List<UserModel> searchByUsername(String username);
}
