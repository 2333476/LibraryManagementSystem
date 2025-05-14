package com.example.loanservice;

import com.example.loanservice.datalayer.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class LoanServiceApplication {

    @Bean
    RestTemplate restTemplate() { return new RestTemplate();}

    @Autowired
    private LoanRepository loanRepository;

    @PostConstruct
    public void testFindAll() {
        System.out.println("Nb loans in DB: " + loanRepository.findAll().size());
    }

    /*
    @Bean
    CommandLineRunner initData(LoanRepository loanRepository) {
        return args -> {
            loanRepository.deleteAll();

            loanRepository.save(Loan.builder()
                    .loanIdentifier(new LoanIdentifier("05c8ab76-4f75-45c1-b6e2-aa8e914ea08f"))
                    .bookIdentifier(new BookIdentifier("9d1f3c52-f44e-4767-89e4-065cceac3f58"))
                    .customerIdentifier(new CustomerIdentifier("123e4567-e89b-12d3-a456-556642440000"))
                    .employeeIdentifier(new EmployeeIdentifier("e5913a79-9b1e-4516-9ffd-06578e7af261"))
                    .loanDate(LocalDate.of(2024, 2, 13))
                    .returnDate(LocalDate.of(2024, 3, 13))
                    .loanStatus(LoanStatus.ACTIVE)
                    .penaltyAmount(0.0)
                    .build());
        };
    }
*/
    @Bean
    public CommandLineRunner loadData(LoanRepository loanRepository) {
        return args -> {

            if (loanRepository.count() > 0) {
                System.out.println("Loans already exist in Mongo. Skipping preload.");
                return;
            }

            List<Loan> loans = List.of(
                    Loan.builder()
                            .loanIdentifier(new LoanIdentifier("05c8ab76-4f75-45c1-b6e2-aa8e914ea08f"))
                            .bookIdentifier(new BookIdentifier("9d1f3c52-f44e-4767-89e4-065cceac3f58"))
                            .customerIdentifier(new CustomerIdentifier("123e4567-e89b-12d3-a456-556642440000"))
                            .employeeIdentifier(new EmployeeIdentifier("e5913a79-9b1e-4516-9ffd-06578e7af261"))
                            .loanDate(LocalDate.of(2024, 2, 13))
                            .returnDate(LocalDate.of(2024, 3, 13))
                            .loanStatus(LoanStatus.ACTIVE)
                            .penaltyAmount(0.0)
                            .build(),

                    Loan.builder()
                            .loanIdentifier(new LoanIdentifier("15c8ab76-4f75-45c1-b6e2-aa8e914ea08f"))
                            .bookIdentifier(new BookIdentifier("e56d3c87-3f1c-4e77-a3a9-b46a3050a83e"))
                            .customerIdentifier(new CustomerIdentifier("223e4567-e89b-12d3-a456-556642440001"))
                            .employeeIdentifier(new EmployeeIdentifier("6d8e5f5b-8350-40ed-ac06-e484498f4f41"))
                            .loanDate(LocalDate.of(2024, 3, 1))
                            .returnDate(LocalDate.of(2024, 4, 1))
                            .loanStatus(LoanStatus.RETURNED)
                            .penaltyAmount(2.5)
                            .build(),

                    Loan.builder()
                            .loanIdentifier(new LoanIdentifier("25c8ab76-4f75-45c1-b6e2-aa8e914ea08f"))
                            .bookIdentifier(new BookIdentifier("30893b7c-0e6c-46f3-97b3-4f3482e97914"))
                            .customerIdentifier(new CustomerIdentifier("323e4567-e89b-12d3-a456-556642440002"))
                            .employeeIdentifier(new EmployeeIdentifier("bfe6b6f3-9316-4886-b150-f42eb8d91ef0"))
                            .loanDate(LocalDate.of(2024, 3, 10))
                            .returnDate(LocalDate.of(2024, 4, 10))
                            .loanStatus(LoanStatus.ACTIVE)
                            .penaltyAmount(0.0)
                            .build(),

                    Loan.builder()
                            .loanIdentifier(new LoanIdentifier("35c8ab76-4f75-45c1-b6e2-aa8e914ea08f"))
                            .bookIdentifier(new BookIdentifier("a3160a45-4c87-4427-a960-6c735e7c882b"))
                            .customerIdentifier(new CustomerIdentifier("423e4567-e89b-12d3-a456-556642440003"))
                            .employeeIdentifier(new EmployeeIdentifier("f8af8ab3-0b63-4e11-a470-52ae284e451b"))
                            .loanDate(LocalDate.of(2024, 1, 15))
                            .returnDate(LocalDate.of(2024, 2, 15))
                            .loanStatus(LoanStatus.OVERDUE)
                            .penaltyAmount(7.0)
                            .build()
            );

            loanRepository.saveAll(loans);
            System.out.println("📦 Loan data loaded into MongoDB.");
        };
}

    public static void main(String[] args) {
        SpringApplication.run(LoanServiceApplication.class, args);
    }

}
