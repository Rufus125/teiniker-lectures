package org.se.lab.server.domain;

public interface UserService
{
	void addUser(String username, String password);
	
	boolean login(String username, String password);	
}
