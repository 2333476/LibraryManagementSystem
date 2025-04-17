package com.example.customersservice.dataaccesslayer;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class PhoneNumber {
    @Enumerated(EnumType.STRING)
    private PhoneType type;
    private String number;
}
