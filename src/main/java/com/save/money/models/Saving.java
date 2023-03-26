package com.save.money.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.save.money.config.CompositeKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(CompositeKey.class)
@Table(name = "saving")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Saving implements Serializable {
    @Id
    private int savingYear;
    @Id
    private int savingMonth;
    private double totalReceipt;
    private double totalExpense;
    private double value;
    private String currency;
}
