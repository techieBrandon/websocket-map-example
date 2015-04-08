package com.mapper.app.rest.v1.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import com.mapper.app.rest.v1.dao.TwitterDAO;

@Component
public class TwitterDAOImpl implements TwitterDAO{

	private static final Logger logger = LoggerFactory.getLogger(TwitterDAOImpl.class);

    private TwitterStream twitterStream;
    
    @Autowired
	private static SimpMessageSendingOperations messagingTemplate;
    

    @Autowired
    private SimpMessagingTemplate sender;
    
    private static ArrayList<Status> statuses = new ArrayList<Status>();
    private static Status lastStatus;
	
	public TwitterDAOImpl() {
		super();
		logger.info("Setting up Twitter stream");
		twitterStream = new TwitterStreamFactory().getInstance();
		
	    twitterStream.addListener(listener);
	    // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
	    twitterStream.sample();
	}
	

    StatusListener listener = new StatusListener(){
		@Override
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			logger.error("Track limitation notice: "+numberOfLimitedStatuses);
		}
		
		@Override
        public void onException(Exception ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
		
		@Override
		public void onStatus(Status status) {
			if(status.getGeoLocation()!=null){
				logger.info(status.getUser().getName() + "["+status.getGeoLocation()+"] : " + status.getText());
				lastStatus = status;
				statuses.add(status);
				
				sender.convertAndSend("/stream", status);
			}
//            status.getGeoLocation()
			
		}
		
		@Override
		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			// TODO Auto-generated method stub
//			logger.info("Deletion notice for status ID: "+statusDeletionNotice.getStatusId());
		}
		
		@Override
		public void onScrubGeo(long userId, long upToStatusId) {
			// TODO Auto-generated method stub
//			logger.info("User ["+userId+"] scrub geo for status ID: "+upToStatusId);
		}
		
		@Override
		public void onStallWarning(StallWarning warning) {
			// TODO Auto-generated method stub
			logger.error(warning.getMessage());
		}
    };
    
    public List<Status> fetch(){
    	return statuses;
    }

	public Status stream(){
		return lastStatus;
	}

	
    
}
