package com.example.apigateway.presentationlayer;

import com.example.apigateway.businesslayer.EmployeeService;
import com.example.apigateway.presentationlayer.employeedto.EmployeeRequestModel;
import com.example.apigateway.presentationlayer.employeedto.EmployeeResponseModel;
import com.example.apigateway.utils.exception.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    private final Integer UUID_SIZE = 36; // On s'assure que l'ID est bien un UUID valide

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<EmployeeResponseModel>> getEmployees() {
        log.debug("1. Received in API-Gateway Employees Controller: getAllEmployees()");
        return ResponseEntity.ok().body(employeeService.getEmployees());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseModel> getEmployeeByEmployeeId(@PathVariable String employeeId) {
        if (employeeId.length() != UUID_SIZE) {
            throw new InvalidInputException("Employee id is invalid: " + employeeId);
        }
        return ResponseEntity.ok().body(employeeService.getEmployeeByEmployeeId(employeeId));
    }

    @PostMapping()
    public ResponseEntity<EmployeeResponseModel> addEmployee(@RequestBody EmployeeRequestModel postData) {
        log.debug("2. Received in API-Gateway Employees Controller: addEmployee()");
        EmployeeResponseModel employee = employeeService.addEmployee(postData);
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseModel> updateEmployee(@PathVariable String employeeId,
                                                                @RequestBody EmployeeRequestModel postData) {
        log.debug("3. Received in API-Gateway Employees Controller: updateEmployee()");
        EmployeeResponseModel employee = employeeService.updateEmployee(employeeId, postData);
        return ResponseEntity.ok().body(employee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) {
        log.debug("4. Received in API-Gateway Employees Controller: deleteEmployee()");
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}
