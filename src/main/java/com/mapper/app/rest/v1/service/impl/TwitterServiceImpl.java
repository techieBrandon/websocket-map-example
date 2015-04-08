package com.mapper.app.rest.v1.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twitter4j.Status;

import com.mapper.app.rest.v1.dao.TwitterDAO;
import com.mapper.app.rest.v1.service.TwitterService;

@Service
public class TwitterServiceImpl implements TwitterService{
	
	@Autowired
	TwitterDAO twitterhDAO;

	public TwitterServiceImpl() {
		super();
	}

	public Status stream() {
		return twitterhDAO.stream();
		
	}

	public List<Status> fetch() {
		return twitterhDAO.fetch();
	}

}
