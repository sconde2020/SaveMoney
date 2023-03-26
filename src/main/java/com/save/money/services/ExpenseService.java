package com.save.money.services;

import com.save.money.exceptions.CreationException;
import com.save.money.exceptions.NoSuchExpenseException;
import com.save.money.models.Expense;
import com.save.money.repositories.ExpenseRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

import static com.save.money.utils.Utils.endTrace;
import static com.save.money.utils.Utils.startTrace;

@Service
@Log4j2
public class ExpenseService {
    @Autowired
    private ExpenseRepository repository;
    @Autowired
    private SavingService savingService;

    public List<Expense> getAll() {
        log.debug(startTrace());
        List<Expense> expenseList = repository.findAll();
        log.debug("Number of expenses found: " + expenseList.size());
        log.debug(endTrace());
        return expenseList;
    }

    public Expense get(long id) throws NoSuchExpenseException{
        try {
            log.debug(startTrace());
            Expense expense = repository.findById(id).orElseThrow();
            log.debug(endTrace());
            return expense;
        } catch (NoSuchElementException e) {
            log.error(e.toString());
            throw new NoSuchExpenseException("No expense found with id <" + id + ">");
        }
    }

    @Transactional
    public Expense create(Expense expense) throws CreationException {
        try {
            log.debug(startTrace());
            Expense savedExpense = repository.save(expense);
            savingService.update(
                    expense.getExpenseDate().getYear(),
                    expense.getExpenseDate().getMonthValue(),
                    0,
                    expense.getAmount()
            );
            log.debug("Expense created successfully");
            log.debug(endTrace());
            return savedExpense;
        } catch (Exception e) {
            log.error(e.toString());
            throw new CreationException("Error while creating expense!!!");
        }
    }
}
