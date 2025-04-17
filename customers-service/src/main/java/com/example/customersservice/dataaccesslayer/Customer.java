package com.example.customersservice.dataaccesslayer;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //private identifier
    private CustomerIdentifier customerIdentifier; //public identifier
    private String lastName;
    private String firstName;
    private String emailAddress;
    private String username;
    private String password;

    @Embedded
    @NonNull
    private Address customerAddress;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "customer_phonenumbers",
            joinColumns = @JoinColumn(name = "customer_id")
    )
//    @Column(name = "phone_number")
    private List<PhoneNumber> phoneNumbers;


    public Customer(CustomerIdentifier customerIdentifier, String firstName, String lastName, String emailAddress, Address customerAddress) {
        this.customerIdentifier = customerIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.customerAddress = customerAddress;
    }






}
