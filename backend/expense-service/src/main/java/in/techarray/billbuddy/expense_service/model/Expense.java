package in.techarray.billbuddy.expense_service.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private Double totalAmount;
    private UUID createdByUserId;
    @Enumerated(EnumType.STRING)
    private SplitType splitType;
    private LocalDate date;
    @Lazy
    @OneToMany
    private List<ExpenseSplit> expenseSplits;
}
