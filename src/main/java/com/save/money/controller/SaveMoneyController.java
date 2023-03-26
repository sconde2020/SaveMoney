package com.save.money.controller;

import com.save.money.exceptions.NoSuchExpenseException;
import com.save.money.exceptions.NoSuchReceiptException;
import com.save.money.exceptions.NoSuchSavingException;
import com.save.money.models.Expense;
import com.save.money.models.Receipt;
import com.save.money.models.Saving;
import com.save.money.services.ExpenseService;
import com.save.money.services.ReceiptService;
import com.save.money.services.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SaveMoneyController {
    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private SavingService savingService;

    @GetMapping("/")
    public String home() { return "Welcome on SaveMoney, Application to reach your saving goals !"; }

    @GetMapping("/receipts")
    public List<Receipt> getAllReceipts() {
        return receiptService.getAll();
    }

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return expenseService.getAll();
    }

    @GetMapping("/savings")
    public List<Saving> getAllSavings() {
        return savingService.getAll();
    }

    @GetMapping("/receipts/{id}")
    public ResponseEntity<Receipt> getReceipt(@PathVariable long id) throws NoSuchReceiptException {
        return new ResponseEntity<>(receiptService.get(id), HttpStatus.FOUND);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense> getExpense(@PathVariable long id) throws NoSuchExpenseException {
        return new ResponseEntity<>(expenseService.get(id), HttpStatus.FOUND);
    }

    @GetMapping("/savings/find")
    public ResponseEntity<Saving> getSaving(@RequestParam int year, @RequestParam int month) throws NoSuchSavingException {
        return new ResponseEntity<>(savingService.get(year, month), HttpStatus.FOUND);
    }

    @PostMapping("/receipts")
    public ResponseEntity<Receipt> createReceipt(@Valid @RequestBody Receipt receipt) {
        return new ResponseEntity<>(receiptService.create(receipt), HttpStatus.CREATED);
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.create(expense), HttpStatus.CREATED);
    }
}
