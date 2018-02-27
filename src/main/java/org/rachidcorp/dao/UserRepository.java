package org.rachidcorp.dao;

import org.rachidcorp.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
	public AppUser findByUsername(String username);
}
