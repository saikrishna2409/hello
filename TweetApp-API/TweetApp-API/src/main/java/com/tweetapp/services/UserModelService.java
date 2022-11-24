package com.tweetapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.entities.UserModel;
import com.tweetapp.exception.UsernameAlreadyExists;
import com.tweetapp.repositories.UserRepository;

@Service
public class UserModelService {
	
	@Autowired
	private UserRepository userRepository;
	
	//find user by username
	public UserModel findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	//create new user
	public UserModel createUser(UserModel user) throws UsernameAlreadyExists {
		UserModel foundedUser = userRepository.findByUsername(user.getUsername());
		if(foundedUser != null) {
			throw new UsernameAlreadyExists("username already exists");
		}
		return userRepository.save(user);
	}
	
	// Method to return a list of all users
	public List<UserModel> getAllUsers() {
		return (List<UserModel>) userRepository.findAll();
	}
		
	//Method to change a user's password
	public UserModel changePassword(String username, String newPassword) throws Exception {
		UserModel userDetails = userRepository.findByUsername(username);
		if(userDetails != null) {
			userDetails.setPassword(newPassword);
			return userRepository.save(userDetails);
		} else {
			throw new Exception("Unable to change password");
		}
	}
	
	//Method to search for like users by username
	public List<UserModel> getUsersByUsername(String username){
		return userRepository.searchByUsername(username);
	}
}
