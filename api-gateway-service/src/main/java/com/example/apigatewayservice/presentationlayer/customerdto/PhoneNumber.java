package com.example.apigatewayservice.presentationlayer.customerdto;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.jetbrains.annotations.NotNull;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@EqualsAndHashCode
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

    //in case PhoneNumber doesn't use lombok during tests phase:
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return type == that.type &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, number);
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
