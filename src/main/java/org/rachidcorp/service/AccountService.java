package org.rachidcorp.service;

import org.rachidcorp.entity.AppRole;
import org.rachidcorp.entity.AppUser;

public interface AccountService {
	public AppUser saveUser(AppUser user);
	public AppRole saveRole(AppRole role);
	public void addRoleToUser(String username, String roleName);
	public AppUser findUserByUsername(String username);
}
