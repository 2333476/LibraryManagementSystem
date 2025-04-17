package com.example.customersservice.presentationlayer;

import com.example.customersservice.businesslogiclayer.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerServiceImpl customerService;
    @Autowired
    public CustomerController(CustomerServiceImpl customerService) {

        this.customerService = customerService;
    }
    @GetMapping()
    public ResponseEntity<List<CustomerResponseModel>> getCustomers(){

        return ResponseEntity.ok().body(this.customerService.getCustomers());
    }
    @GetMapping("/{customer_id}")
    public ResponseEntity<CustomerResponseModel> getCustomerbyCustomerId(
            @PathVariable String customer_id){
        return ResponseEntity.ok().body(this.customerService.getCustomerbyCustomerId(customer_id));
    }
    @PostMapping()
    public ResponseEntity<CustomerResponseModel> addCustomer(@RequestBody CustomerRequestModel newCustomerData){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.customerService.addCustomer(newCustomerData));
    }
    @PutMapping("/{customer_id}")
    public ResponseEntity<CustomerResponseModel> updateCustomer(@PathVariable String customer_id,
                                                                @RequestBody CustomerRequestModel newCustomerData){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.customerService.updateCustomer(customer_id,newCustomerData));
    }
    @DeleteMapping("/{customer_id}")
    public ResponseEntity<String> deleteCustomerbyCustomerId(@PathVariable String customer_id){
        String message = this.customerService.deleteCustomerbyCustomerId(customer_id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
