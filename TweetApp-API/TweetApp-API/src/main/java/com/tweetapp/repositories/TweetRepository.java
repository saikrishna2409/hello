package com.tweetapp.repositories;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.entities.Tweet;

@Repository
@EnableScan
public interface TweetRepository extends CrudRepository<Tweet, String>{
	
	List<Tweet> findByUsername(String username);
}
