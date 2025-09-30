package com.example.loanservice.datamapperlayer.customerdto;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
public class PhoneNumber {
    @Enumerated(EnumType.STRING)
    private PhoneType type;
    private String number;
    public PhoneNumber(@NotNull PhoneType type, @NotNull String number) {
        this.type = type;
        this.number = number;
    }

    // ✅ Méthodes nécessaires pour que les tests passent
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return type == that.type && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, number);
    }
}
