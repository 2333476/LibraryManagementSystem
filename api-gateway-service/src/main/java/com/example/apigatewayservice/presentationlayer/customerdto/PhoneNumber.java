package com.example.apigatewayservice.presentationlayer.customerdto;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.jetbrains.annotations.NotNull;

@Embeddable
public class PhoneNumber {

    @Enumerated(EnumType.STRING)
    private PhoneType type;
    private String number;

    public PhoneNumber() {
    }

    public PhoneNumber(@NotNull PhoneType type, @NotNull String number) {
        this.type = type;
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
