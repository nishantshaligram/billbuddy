package in.techarray.billbuddy.expense_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import in.techarray.billbuddy.expense_service.model.ExpenseSplit;

public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, UUID> {
    
}
