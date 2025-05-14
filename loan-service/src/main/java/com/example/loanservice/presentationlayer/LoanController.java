package com.example.loanservice.presentationlayer;

import com.example.loanservice.businesslayer.LoanService;
import com.example.loanservice.datalayer.Loan;
import com.example.loanservice.datalayer.LoanRepository;
import com.example.loanservice.presentationlayer.LoanResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final LoanRepository loanRepository;

    @GetMapping
    public ResponseEntity<List<LoanResponseModel>> getLoans() {
        List<LoanResponseModel> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    /*
    @GetMapping("/raw-loans")
    public ResponseEntity<List<Loan>> getRawLoans() {
        return ResponseEntity.ok(loanRepository.findAll());
    }
*/
    @GetMapping("/{loanId}")
    public LoanResponseModel getLoanById(@PathVariable String loanId) {
        return loanService.getLoanByLoanId(loanId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponseModel addLoan(@RequestBody LoanRequestModel requestModel) {
        return loanService.addLoan(requestModel);
    }

    @PutMapping("/{loanId}")
    public LoanResponseModel updateLoan(@PathVariable String loanId, @RequestBody LoanRequestModel requestModel) {
        return loanService.updateLoan(loanId, requestModel);
    }

    @DeleteMapping("/{loanId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoan(@PathVariable String loanId) {
        loanService.deleteLoan(loanId);
    }

}
