package com.mapper.app.rest.v1.dao;

import java.util.List;

import twitter4j.Status;

public interface TwitterDAO {

    public List<Status> fetch();

	public Status stream();
}
