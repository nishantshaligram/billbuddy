package in.techarray.billbuddy.expense_service.dto;

import java.time.LocalDate;
import java.util.List;

import in.techarray.billbuddy.expense_service.model.SplitType;
import lombok.Data;

@Data
public class ExpenseRequestDto {
    private String Description;
    private Double amount;
    private LocalDate date;
    private Long createdByUserId;
    private List<Long> participantUserIds;
    private SplitType splitType;
}
