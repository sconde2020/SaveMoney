package com.save.money.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel("Receipt data")
@Getter
@Setter
@ToString
@Entity
@Table(name = "receipt")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long receiptId;

    @NotNull
    private double amount;

    @NotBlank
    @Length(min=3, max=3, message="Property CURRENCY must have exactly 3 characters")
    private String currency;

    @NotBlank
    @Length(max=50, message="Property SOURCE can contain at most 50 characters")
    @ApiModelProperty("Description")
    private String source;

    private LocalDate receiptDate = LocalDate.now();
}
