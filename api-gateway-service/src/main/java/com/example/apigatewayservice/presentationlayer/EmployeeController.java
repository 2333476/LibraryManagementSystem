package com.example.apigatewayservice.presentationlayer;

import com.example.apigatewayservice.businesslayer.EmployeeService;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeRequestModel;
import com.example.apigatewayservice.presentationlayer.employeedto.EmployeeResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public ResponseEntity<List<EmployeeResponseModel>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }



    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseModel> getEmployeeById(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeByEmployeeId(employeeId));
    }


    @PostMapping("")
    public ResponseEntity<EmployeeResponseModel> addEmployee(@RequestBody EmployeeRequestModel employeeRequestModel) {
        return ResponseEntity.ok(employeeService.addEmployee(employeeRequestModel));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseModel> updateEmployee(@PathVariable String employeeId,
                                                                @RequestBody EmployeeRequestModel requestModel) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, requestModel));
    }


    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }



}
