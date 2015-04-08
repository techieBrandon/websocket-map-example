package com.mapper.app.rest.v1.controller;



import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import twitter4j.Status;

import com.mapper.app.rest.v1.service.TwitterService;

@Controller
public class TwitterController {
	
	private static final Logger logger = LoggerFactory.getLogger(TwitterController.class);
	
	@Resource
	private TwitterService twitterService;

    @MessageMapping("/twitter")
	@SubscribeMapping("/stream")
	public Status getStream() throws Exception {
    	logger.info("getting stream from controller");
		Status status = twitterService.stream();
		return status;
	}
	
	@SubscribeMapping("/fetch")
	public List<Status> getTweets() throws Exception {
    	logger.info("fetching statuses from controller");
		List<Status> results = twitterService.fetch();
		return results;
	}

}
