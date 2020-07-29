package com.starter.rest.api.starter.restapi.payroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.starter.rest.api.starter.restapi.payroll.Entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}