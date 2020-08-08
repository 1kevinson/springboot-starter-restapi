package com.starter.rest.api.starter.restapi.payroll.Exception;


@SuppressWarnings("serial")
public class EmployeeNotFoundException extends RuntimeException {

	public EmployeeNotFoundException(Long id) {
    super("Could not find employee " + id);
  }
}