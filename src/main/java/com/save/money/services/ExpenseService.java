package com.save.money.services;

import com.save.money.exceptions.CreationException;
import com.save.money.exceptions.DeleteException;
import com.save.money.exceptions.NoSuchExpenseException;
import com.save.money.exceptions.UpdateException;
import com.save.money.models.Expense;
import com.save.money.repositories.ExpenseRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Log4j2
public class ExpenseService {
    @Autowired
    private ExpenseRepository repository;
    @Autowired
    private SavingService savingService;

    public List<Expense> getAll() {
        List<Expense> expenseList = repository.findAll();
        log.debug("Number of expenses found: {}", expenseList.size());
        return expenseList;
    }

    public Expense get(long id) throws NoSuchExpenseException{
        try {
            return repository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            log.error(e.toString());
            throw new NoSuchExpenseException("No expense found with id <" + id + ">");
        }
    }

    @Transactional
    public Expense create(Expense expense) throws CreationException {
        try {
            Expense savedExpense = repository.save(expense);
            savingService.update(
                    expense.getExpenseDate().getYear(),
                    expense.getExpenseDate().getMonthValue(),
                    0,
                    expense.getAmount()
            );
            log.debug("Expense created successfully");
            return savedExpense;
        } catch (Exception e) {
            log.error(e.toString());
            throw new CreationException("Error while creating expense!!!");
        }
    }

    @Transactional
    public Expense update(Expense expense) throws UpdateException {
        try {
            // Get old expense
            Expense oldExpense = repository.getById(expense.getExpenseId());

            // Save new expense
            Expense newExpense = repository.save(expense);

            // Update saving
            // We cancel the old expense by adding it to receipts
            savingService.update(oldExpense.getExpenseDate().getYear(),
                    oldExpense.getExpenseDate().getMonthValue(),
                    oldExpense.getAmount(),
                    newExpense.getAmount());

            log.debug("Expense updated successfully");
            return newExpense;
        } catch (Exception e) {
            log.error(e.toString());
            throw new UpdateException("Error while updating expense!!!");
        }
    }

    @Transactional
    public Expense delete(Expense expense) throws DeleteException {
        try {
            // Delete the expense
            repository.delete(expense);

            // Updating the saving
            savingService.delete(
                    expense.getExpenseDate().getYear(),
                    expense.getExpenseDate().getMonthValue(),
                    0,
                    expense.getAmount()
            );

            log.debug("Expense deleted successfully");
            return expense;
        } catch (Exception e) {
            log.error(e.toString());
            throw new DeleteException("Error while deleting expense!!!");
        }
    }
}
