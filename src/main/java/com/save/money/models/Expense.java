package com.save.money.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel("Expense data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expense")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long expenseId;

    @NotNull
    private double amount;

    @NotBlank
    @Length(min=3, max=3, message="Property CURRENCY must have exactly 3 characters")
    private String currency;

    @NotBlank
    @Length(max=50, message="Property REASON can contain at most 50 characters")
    private String reason;

    private LocalDate expenseDate = LocalDate.now();
}
