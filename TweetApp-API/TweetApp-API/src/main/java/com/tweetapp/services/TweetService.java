package com.tweetapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.micrometer.core.instrument.util.StringUtils;

import com.tweetapp.dto.Comment;
import com.tweetapp.dto.Like;
import com.tweetapp.dto.TweetResponse;
import com.tweetapp.entities.Tweet;
import com.tweetapp.exception.InvalidUsernameException;
import com.tweetapp.exception.TweetDoesNotExistException;
import com.tweetapp.repositories.TweetRepository;

@Service
public class TweetService {
	
	@Autowired
	private TweetRepository tweetRepository;
	
	//Methos to return all tweets
	public Iterable<Tweet> getAllTweets() {
		return tweetRepository.findAll();
	}
	
	//Method to return all of a user's tweets
	public List<TweetResponse> getUserTweets(String username, String loggedInUser) throws InvalidUsernameException {
		// use username as login id
		if(!StringUtils.isBlank(username)) {
			List<Tweet> tweets = tweetRepository.findByUsername(username);
			//List<TweetResponse> tweetResponse= new ArrayList<>();
			List<TweetResponse> tweetResponse = tweets.stream().map(tweet ->{
				Integer likesCount = tweet.getLikes().size();
				Boolean likeStatus = tweet.getLikes().contains(loggedInUser);
				Integer commentsCount = tweet.getComments().size();
				return new TweetResponse(tweet.getTweetId(), username, tweet.getTweetText(),
						tweet.getFirstName(), tweet.getLastName(), tweet.getTweetDate(),
						likesCount, commentsCount, likeStatus, tweet.getComments());
			})
			.collect(Collectors.toList());
				//	.stream().filter(tweet -> tweet.getUsername().contentEquals(username))
				//	.collect(Collectors.toList());
			return tweetResponse;
		} else {
			throw new InvalidUsernameException("Username/loginId provided is invalid");
		}
			
	}

	//Method to post a new tweet
	public Tweet postNewTweet(String username, Tweet newTweet) {
	
		newTweet.setTweetId(UUID.randomUUID().toString());
		//logProducer.logNewPostEvents(newTweet);
		return tweetRepository.save(newTweet);
	}
	
	//method to get tweet data by id
	public TweetResponse getTweet(String tweetId,String username) throws TweetDoesNotExistException {
		Optional<Tweet> tweetFounded =  tweetRepository.findById(tweetId);
		if(tweetFounded.isPresent()) {
			Tweet tweet = tweetFounded.get();
			Integer likesCount = tweet.getLikes().size();
			Boolean likeStatus = tweet.getLikes().contains(username);
			Integer commentsCount = tweet.getComments().size();
			return new TweetResponse(tweet.getTweetId(), tweet.getUsername(), tweet.getTweetText(),
					tweet.getFirstName(), tweet.getLastName(), tweet.getTweetDate(),
					likesCount, commentsCount, likeStatus, tweet.getComments());
		}else {
			throw new TweetDoesNotExistException("This tweet does not exist anymore.");
		}
		
	}

	//Method to update an existing tweet
	public Tweet updateTweet(String userId, String tweetId, String updatedTweetText) throws TweetDoesNotExistException {
			
		Optional<Tweet> originalTweetOptional = tweetRepository.findById(tweetId);
		System.out.println("===========>"+tweetId);
		System.out.println("===========>"+updatedTweetText);
		if(originalTweetOptional.isPresent()) {
			Tweet tweet = originalTweetOptional.get();
			tweet.setTweetText(updatedTweetText);
			
			return tweetRepository.save(tweet);
		} else {
			throw new TweetDoesNotExistException("This tweet does not exist anymore."); 
		}
				
	}

	//Method to delete a tweet
	public boolean deleteTweet(String tweetId) throws TweetDoesNotExistException {
		if(tweetRepository.existsById(tweetId) && !StringUtils.isBlank(tweetId)) {
			tweetRepository.deleteById(tweetId);
			return true;
		} else {

			throw new TweetDoesNotExistException("This tweet does not exist anymore.");
		}
	}
	
	//Method to like a tweet
	public Tweet likeTweet(String username, String tweetId) throws TweetDoesNotExistException{
		Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);
		if(tweetOptional.isPresent()) {
			Tweet tweet = tweetOptional.get();
			tweet.getLikes().add(username);
			return tweetRepository.save(tweet);
		} else {	
			throw new TweetDoesNotExistException("This tweet does not exist anymore.");
		}
	}
		
	//Method to comment on a tweet
	public Tweet replyTweet(String username, String tweetId, String tweetReply) throws TweetDoesNotExistException {
		Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);
		if(tweetOptional.isPresent()) {
			Tweet tweet = tweetOptional.get();
			tweet.getComments().add(new Comment(username, tweetReply));
			return tweetRepository.save(tweet);
			//return tweetReply;
		} else {	
			throw new TweetDoesNotExistException("This tweet does not exist anymore.");
		}
	}

}
