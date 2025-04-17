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
    private Address customerAddress;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "customer_phonenumbers",
            joinColumns = @JoinColumn(name = "customer_id")
    )
//    @Column(name = "phone_number")
    private List<PhoneNumber> phoneNumbers;

}
