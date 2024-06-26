package com.save.money.services;

import com.save.money.exceptions.CreationException;
import com.save.money.exceptions.NoSuchSavingException;
import com.save.money.exceptions.UpdateException;
import com.save.money.models.Saving;
import com.save.money.repositories.SavingRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Log4j2
public class SavingService {
    @Autowired
    private SavingRepository repository;

    public List<Saving> getAll() {
        List<Saving> savingList = repository.findAll();
        log.debug("Number of savings found: {}", savingList.size());
        return savingList;
    }

    public Saving get(int savingYear, int savingMonth) throws NoSuchSavingException {
        try {
            return repository.findBySavingYearAndSavingMonth(savingYear, savingMonth).orElseThrow();
        } catch (NoSuchElementException e) {
            log.error(e.toString());
            throw new NoSuchSavingException("No saving found for month <" + savingMonth + "> and year <" + savingYear + ">");
        }
    }

    public void update(int savingYear, int savingMonth, double newReceipt, double newExpense) throws CreationException {
        try {
            log.debug("Year = {} and month = {}", savingYear, savingMonth);

            // Get the saving if it exists
            Saving saving = repository.findBySavingYearAndSavingMonth(savingYear, savingMonth).orElse(null);

            // Update existing saving
            if (saving != null) {
                updateSaving(saving, newReceipt, newExpense);
               log.debug("Saving updated successfully!");
            }
            // Or create new saving
            else {
                createNewSaving(savingYear,savingMonth,newReceipt, newExpense);
                log.debug("New Saving has been created for the given date !");
            }
        } catch (Exception e) {
            log.error(e.toString());
            throw new UpdateException("Error while updating saving!!!");
        }
    }

    public void delete(int savingYear, int savingMonth, double receipt, double expense) {
        try {
            log.debug("Year = {}, month = {}", savingYear, savingMonth);

            // Get the saving if it exists
            Saving saving = repository.findBySavingYearAndSavingMonth(savingYear, savingMonth).orElse(null);

            // Update existing saving
            if (saving != null) {
                // We interchange receipt and expense to cancel in saving
                updateSaving(saving, expense, receipt);
                log.debug("Movement deleted from Saving successfully!");
            }
            else {
                throw new UpdateException("No saving exists for this period");
            }

        } catch (Exception e) {
            log.error(e.toString());
            throw new UpdateException("Error while deleting movement from saving!!!");
        }
    }

    private void updateSaving(Saving saving, double newReceipt, double newExpense) {
        saving.setTotalReceipt(saving.getTotalReceipt() + newReceipt);
        saving.setTotalExpense(saving.getTotalExpense() + newExpense);
        saving.setValue(saving.getTotalReceipt() - saving.getTotalExpense());
        repository.save(saving);
    }

    private void createNewSaving(int savingYear, int savingMonth, double newReceipt, double newExpense) {
        Saving newSaving = new Saving();
        newSaving.setSavingYear(savingYear);
        newSaving.setSavingMonth(savingMonth);
        newSaving.setTotalExpense(newExpense);
        newSaving.setTotalReceipt(newReceipt);
        newSaving.setValue(newReceipt - newExpense);
        newSaving.setCurrency("EUR");
        repository.save(newSaving);
    }
}
