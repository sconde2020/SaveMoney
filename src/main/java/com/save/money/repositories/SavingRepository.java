package com.save.money.repositories;

import com.save.money.models.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Long> {
    Optional<Saving> findBySavingYearAndSavingMonth(int savingYear, int savingMonth);
}
