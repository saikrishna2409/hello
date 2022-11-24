package com.tweetapp.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "users")
public class UserModel {
	
	@DynamoDBHashKey
	private String username;
	@DynamoDBAttribute
	private String firstName;
	@DynamoDBAttribute
	private String lastName;
	@DynamoDBAttribute
	private String email;
	@DynamoDBAttribute
	private String contactNum;
	@DynamoDBAttribute
	private String password;
}
