package in.techarray.billbuddy.expense_service.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import in.techarray.billbuddy.expense_service.model.SplitType;
import lombok.Data;

@Data
public class ExpenseRequestDto {
    private String Description;
    private Double totalAmount;
    private LocalDate date;
    private UUID createdByUserId;
    private List<UUID> participantUserIds;
    private SplitType splitType;
}
