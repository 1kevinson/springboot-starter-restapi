package com.starter.rest.api.starter.restapi.payroll.Controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.starter.rest.api.starter.restapi.payroll.Entities.Employee;
import com.starter.rest.api.starter.restapi.payroll.Entities.EmployeeModelAssembler;
import com.starter.rest.api.starter.restapi.payroll.Exception.EmployeeNotFoundException;
import com.starter.rest.api.starter.restapi.payroll.Repository.EmployeeRepository;

@SuppressWarnings("deprecation")
@RestController
public class EmployeeController {

	private final EmployeeRepository repository;
	private final EmployeeModelAssembler assembler;

	EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	// All employees
	@GetMapping("/employees")
	public CollectionModel<EntityModel<Employee>> fetchAllEmployee() {
		List<EntityModel<Employee>> employees = repository.findAll().stream() //
				.map(assembler::toModel) //
				.collect(Collectors.toList());

		return CollectionModel.of(employees, linkTo(methodOn(this.getClass()).fetchAllEmployee()).withSelfRel());
	}

	// Save One employee
	@PostMapping("/employees")
	public ResponseEntity<?> saveEmployee(@RequestBody Employee newEmployee) {
		EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}

	// Get One employee
	@GetMapping("/employees/{id}")
	public EntityModel<Employee> fetchEmployee(@PathVariable Long id) {
		Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

		return assembler.toModel(employee);
	}

	// Update or Create One employee
	@PutMapping("/employees/{id}")
	public ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		Employee updatedEmployee = repository.findById(id) //
				.map(employee -> {
					employee.setName(newEmployee.getName());
					employee.setRole(newEmployee.getRole());
					return repository.save(employee);
				}) //
				.orElseGet(() -> {
					newEmployee.setId(id);
					return repository.save(newEmployee);
				});

		EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

		return ResponseEntity //
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
				.body(entityModel);
	}

	// Delete One Employee
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}