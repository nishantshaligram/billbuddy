package in.techarray.billbuddy.expense_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.Expense;
import in.techarray.billbuddy.expense_service.service.ExpenseService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    private ExpenseService expenseService;

    
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @PostMapping("")
    public ResponseEntity<Expense> createExpense( @RequestBody ExpenseRequestDto expenseRequestDto ) {
        Expense expense = expenseService.createExpenseWithSplits(expenseRequestDto);
        return ResponseEntity.ok(expense);
    }
    
}
