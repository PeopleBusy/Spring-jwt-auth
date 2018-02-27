package org.rachidcorp.controller;

import org.rachidcorp.entity.AppUser;
import org.rachidcorp.entity.RegisterForm;
import org.rachidcorp.exception.PasswordConfirmException;
import org.rachidcorp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AccountRestController {
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public AppUser registerUser(@RequestBody RegisterForm userForm) {
		
		if(!userForm.getPassword().equals(userForm.getConfirmationpassword()))
			throw new PasswordConfirmException();
		
		AppUser user = accountService.findUserByUsername(userForm.getUsername());
		if(user != null)
			throw new RuntimeException("That user already exits");
		
		AppUser appUser = new AppUser();
		appUser.setUsername(userForm.getUsername());
		appUser.setPassword(userForm.getPassword());
		
		accountService.saveUser(user);
		accountService.addRoleToUser(userForm.getUsername(), "USER");
		
		return appUser;
	}
}
