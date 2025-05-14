package com.example.apigatewayservice.presentationlayer;

import com.example.apigatewayservice.businesslayer.LoanService;
import com.example.apigatewayservice.presentationlayer.loandto.LoanRequestModel;
import com.example.apigatewayservice.presentationlayer.loandto.LoanResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<LoanResponseModel> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{loanId}")
    public LoanResponseModel getLoanById(@PathVariable String loanId) {
        return loanService.getLoanById(loanId);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addLoan(@RequestBody LoanRequestModel request) {
        loanService.addLoan(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "✅ Loan successfully added.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{loanId}")
    public ResponseEntity<Map<String, String>> updateLoan(@PathVariable String loanId, @RequestBody LoanRequestModel request) {
        loanService.updateLoan(loanId, request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "✅ Loan successfully updated.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Map<String, String>> deleteLoan(@PathVariable String loanId) {
        loanService.deleteLoan(loanId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "🗑️ Loan successfully deleted.");
        return ResponseEntity.ok(response);
    }
}


/*
package com.example.apigatewayservice.presentationlayer;

import com.example.apigatewayservice.businesslayer.LoanService;
import com.example.apigatewayservice.presentationlayer.loandto.LoanRequestModel;
import com.example.apigatewayservice.presentationlayer.loandto.LoanResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<LoanResponseModel> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{loanId}")
    public LoanResponseModel getLoanById(@PathVariable String loanId) {
        return loanService.getLoanById(loanId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponseModel addLoan(@RequestBody LoanRequestModel request) {
        return loanService.addLoan(request);
    }

    @PutMapping("/{loanId}")
    public void updateLoan(@PathVariable String loanId, @RequestBody LoanRequestModel request) {
        loanService.updateLoan(loanId, request);
    }

    @DeleteMapping("/{loanId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoan(@PathVariable String loanId) {
        loanService.deleteLoan(loanId);
    }
}

 */
