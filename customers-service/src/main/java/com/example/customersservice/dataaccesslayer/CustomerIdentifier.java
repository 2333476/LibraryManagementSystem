package com.example.customersservice.dataaccesslayer;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@AllArgsConstructor
@Getter
@Setter
public class CustomerIdentifier {

    private String customerId;

    public CustomerIdentifier() {
        this.customerId = UUID.randomUUID().toString();
    }
}
