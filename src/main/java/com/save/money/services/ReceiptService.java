package com.save.money.services;

import com.save.money.exceptions.CreationException;
import com.save.money.exceptions.DeleteException;
import com.save.money.exceptions.NoSuchReceiptException;
import com.save.money.exceptions.UpdateException;
import com.save.money.models.Receipt;
import com.save.money.repositories.ReceiptRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Log4j2
public class ReceiptService {
    @Autowired
    private ReceiptRepository repository;
    @Autowired
    private SavingService savingService;

    public List<Receipt> getAll() {
        List<Receipt> receiptList = repository.findAll();
        log.debug(receiptList);
        log.debug("Number of receipts found: {}", receiptList.size());
        return receiptList;
    }

    public Receipt get(long id) throws NoSuchReceiptException{
        try {
            return repository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            log.error(e.toString());
            throw new NoSuchReceiptException("No receipt found with id <" + id + ">");
        }
    }

    @Transactional
    public Receipt create(Receipt receipt) throws CreationException {
        try {
            Receipt savedReceipt = repository.save(receipt);
            savingService.update(
                    receipt.getReceiptDate().getYear(),
                    receipt.getReceiptDate().getMonthValue(),
                    receipt.getAmount(),
                    0
            );
            log.debug("Receipt created successfully");
            return savedReceipt;
        } catch (Exception e) {
            log.error(e.toString());
            throw new CreationException("Error while creating receipt!!!");
        }
    }

    @Transactional
    public Receipt update(Receipt receipt) throws UpdateException {
        try {
            // Get old receipt
            Receipt oldReceipt = repository.getById(receipt.getReceiptId());

            // Save new receipt
            Receipt newReceipt = repository.save(receipt);

            // Update saving
            // We cancel the old receipt by adding it to expenses
            savingService.update(oldReceipt.getReceiptDate().getYear(),
                    oldReceipt.getReceiptDate().getMonthValue(),
                    newReceipt.getAmount(),
                    oldReceipt.getAmount());

            log.debug("Receipt updated successfully");
            return newReceipt;
        } catch (Exception e) {
            log.error(e.toString());
            throw new UpdateException("Error while updating receipt!!!");
        }
    }

    @Transactional
    public Receipt delete(Receipt receipt) throws DeleteException {
        try {
            // Delete the receipt
            repository.delete(receipt);

            // Update the saving
            savingService.delete(
                    receipt.getReceiptDate().getYear(),
                    receipt.getReceiptDate().getMonthValue(),
                    receipt.getAmount(),
                    0

            );
            log.debug("Receipt deleted successfully");
            return receipt;
        } catch (Exception e) {
            log.error(e.toString());
            throw new DeleteException("Error while deleting receipt!!!");
        }
    }
}
