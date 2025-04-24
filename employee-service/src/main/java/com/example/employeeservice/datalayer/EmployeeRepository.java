package com.example.employeeservice.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findEmployeeByEmployeeIdentifier_EmployeeId(String employeeId);

    boolean existsByEmailAddress(String emailAddress); // <- bon nom ici

    Employee findEmployeeByEmailAddress(String emailAddress);



}
