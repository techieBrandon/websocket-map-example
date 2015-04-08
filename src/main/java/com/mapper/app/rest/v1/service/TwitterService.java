package com.mapper.app.rest.v1.service;

import java.util.List;

import twitter4j.Status;

public interface TwitterService {

	public Status stream();

	public List<Status> fetch();

}
