package com.example.apigatewayservice.presentationlayer;

import com.example.apigatewayservice.businesslayer.CustomerService;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerRequestModel;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public ResponseEntity<List<CustomerResponseModel>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }


    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseModel> getCustomerById(@PathVariable String customerId) {
        return ResponseEntity.ok(customerService.getCustomerByCustomerId(customerId));
    }

    @PostMapping("")
    public ResponseEntity<CustomerResponseModel> addCustomer(@RequestBody CustomerRequestModel customerRequestModel) {
        return ResponseEntity.ok(customerService.addCustomer(customerRequestModel));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseModel> updateCustomer(@PathVariable String customerId,
                                                                @RequestBody CustomerRequestModel requestModel) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, requestModel));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable String customerId) {
        String result = customerService.deleteCustomerByCustomerId(customerId);
        return ResponseEntity.ok(result);
    }

}
