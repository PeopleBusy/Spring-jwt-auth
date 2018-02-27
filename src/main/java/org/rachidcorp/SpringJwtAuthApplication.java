package org.rachidcorp;

import java.util.stream.Stream;

import org.rachidcorp.dao.TaskRepository;
import org.rachidcorp.entity.AppRole;
import org.rachidcorp.entity.AppUser;
import org.rachidcorp.entity.Task;
import org.rachidcorp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringJwtAuthApplication implements CommandLineRunner {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private AccountService accountService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringJwtAuthApplication.class, args);
	}
	
	@Bean //Cette annotation permet d'exécuter cette methode au démarrage de l'application et ensuite elle pourra être utilisée partout dans l'appli.
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		accountService.saveUser(new AppUser("admin", "1234"));
		accountService.saveUser(new AppUser("user", "1234"));
		
		accountService.saveRole(new AppRole("ADMIN"));
		accountService.saveRole(new AppRole("USER"));
		
		accountService.addRoleToUser("admin", "ADMIN");
		accountService.addRoleToUser("admin", "USER");
		
		accountService.addRoleToUser("user", "USER");
		
		Stream.of("Task 1","Task 2","Task 3","Task 4","Task 5").forEach(t->{
			taskRepository.save(new Task(t));
		});
		
		/**taskRepository.findAll().forEach(t->{
			System.out.println(t.getTaskName());
		});**/
	}
}
