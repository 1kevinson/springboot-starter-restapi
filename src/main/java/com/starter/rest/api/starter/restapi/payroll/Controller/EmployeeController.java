package com.starter.rest.api.starter.restapi.payroll.Controller;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.starter.rest.api.starter.restapi.payroll.Entities.Employee;
import com.starter.rest.api.starter.restapi.payroll.Exception.EmployeeNotFoundException;
import com.starter.rest.api.starter.restapi.payroll.Repository.EmployeeRepository;

@RestController
class EmployeeController {

	private final EmployeeRepository repository;

	EmployeeController(EmployeeRepository repository) {
		this.repository = repository;
	}

	// All employees
	@GetMapping("/employees")
	List<Employee> fetchAllEmployee() {
		return repository.findAll();
	}

	// Save One employee
	@PostMapping("/employees")
	Employee saveEmployee(@RequestBody Employee newEmployee) {
		return repository.save(newEmployee);
	}

	// Get One employee
	@SuppressWarnings("deprecation")
	@GetMapping("/employees/{id}")
	EntityModel<Employee> fetchEmployee(@PathVariable Long id) {
	   Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		
	   return EntityModel.of(employee, //
		      linkTo(methodOn(this.getClass()).fetchEmployee(id)).withSelfRel(),
		      linkTo(methodOn(EmployeeController.class).fetchAllEmployee()).withRel("employees"));
	}

	// Update or Create One employee
	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		return repository.findById(id).map(employee -> {
			employee.setName(newEmployee.getName());
			employee.setRole(newEmployee.getRole());
			return repository.save(employee);
		}).orElseGet(() -> {
			newEmployee.setId(id);
			return repository.save(newEmployee);
		});
	}

	// Delete One Employee
	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}
}