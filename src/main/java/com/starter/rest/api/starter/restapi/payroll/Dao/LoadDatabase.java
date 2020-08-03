package com.starter.rest.api.starter.restapi.payroll.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.starter.rest.api.starter.restapi.payroll.Entities.Employee;
import com.starter.rest.api.starter.restapi.payroll.Repository.EmployeeRepository;


@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(EmployeeRepository repository) {

		return args -> {
			log.info("Preloading " + repository.save(new Employee("Bilbo", "Baggins", "burglar")));
			log.info("Preloading " + repository.save(new Employee("Frodo", "Baggins", "thief")));
			log.info("Preloading " + repository.save(new Employee("jordan", "Boodwin", "thief")));
			log.info("Preloading " + repository.save(new Employee("El Ray", "Mago", "thief")));
			log.info("Preloading " + repository.save(new Employee("Arsene", "Kevin", "thief")));
		};
	}
}