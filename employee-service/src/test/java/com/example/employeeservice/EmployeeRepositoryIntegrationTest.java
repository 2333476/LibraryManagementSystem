package com.example.employeeservice;

import com.example.employeeservice.datalayer.Employee;
import com.example.employeeservice.datalayer.EmployeeIdentifier;
import com.example.employeeservice.datalayer.EmployeeRepository;
import com.example.employeeservice.datalayer.Department;
import com.example.employeeservice.datalayer.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee savedEmployee;

    @BeforeEach
    void setup() {
        savedEmployee = new Employee(
                new EmployeeIdentifier(),
                "Sarah", "Connor", "sarah.connor@example.com", "514-123-4567",
                70000.0, 0.0, Department.Marketing,
                new Address("12 Cyberdyne", "Laval", "QC", "Canada", "J1B2B3")
        );

        employeeRepository.save(savedEmployee);
    }

    @Test
    void findEmployeeByEmployeeId_existingId_returnsEmployee() {
        var found = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(savedEmployee.getEmployeeIdentifier().getEmployeeId());
        assertNotNull(found);
        assertEquals(savedEmployee.getEmailAddress(), found.getEmailAddress());
    }

    @Test
    void existsByEmail_existingEmail_returnsTrue() {
        assertTrue(employeeRepository.existsByEmailAddress("sarah.connor@example.com"));
    }

    @Test
    void existsByEmail_nonExistingEmail_returnsFalse() {
        assertFalse(employeeRepository.existsByEmailAddress("nope@example.com"));
    }
}
