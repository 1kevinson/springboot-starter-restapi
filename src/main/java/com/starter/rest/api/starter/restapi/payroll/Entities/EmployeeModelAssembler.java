package com.starter.rest.api.starter.restapi.payroll.Entities;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.starter.rest.api.starter.restapi.payroll.Controller.EmployeeController;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

	@Override
	public EntityModel<Employee> toModel(Employee employee) {

		return EntityModel.of(employee, //
				linkTo(methodOn(EmployeeController.class).fetchEmployee(employee.getId())).withSelfRel(),
				linkTo(methodOn(EmployeeController.class).fetchAllEmployee()).withRel("employees"));
	}
}