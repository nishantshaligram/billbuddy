package in.techarray.billbuddy.expense_service.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class UserExpenseRequestDto {
    private UUID userId;
}
