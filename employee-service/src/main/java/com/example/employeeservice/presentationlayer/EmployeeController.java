package com.example.employeeservice.presentationlayer;

import com.example.employeeservice.businesslayer.EmployeeService;
import com.example.employeeservice.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    private final Integer UUID_SIZE = 36;

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeResponseModel>> getEmployees() {
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
        log.debug("3. Received in Employees-Service  addEmployee");
        EmployeeResponseModel employee = employeeService.addEmployee(postData);
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseModel> updateEmployee(@PathVariable String employeeId,
                                                                @RequestBody EmployeeRequestModel postData) {
        log.debug("4. Received in Employees-Service  updateEmployee");
        EmployeeResponseModel employee = employeeService.updateEmployee(employeeId, postData);
        return ResponseEntity.ok().body(employee);
    }



    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) {
        if (employeeId.length() != UUID_SIZE) {
            throw new InvalidInputException("Employee id is invalid: " + employeeId);
        }
        log.debug("5. Received in Employees-Service deleteEmployee");
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }



}
