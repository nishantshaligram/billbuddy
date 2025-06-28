package in.techarray.billbuddy.expense_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.Expense;
import in.techarray.billbuddy.expense_service.service.ExpenseService;
import in.techarray.billbuddy.expense_service.service.ExpenseServiceImpl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    private ExpenseService expenseService;

    
    public ExpenseController(ExpenseServiceImpl expenseService) {
        this.expenseService = expenseService;
    }


    @PostMapping("")
    public ResponseEntity<Expense> createExpense( @RequestBody ExpenseRequestDto expenseRequestDto ) {
        Expense expense = expenseService.createExpenseWithSplits(expenseRequestDto);
        return ResponseEntity.ok(expense);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpense( @PathVariable UUID id ){
        Expense expense = expenseService.getExpenseById( id );
        return ResponseEntity.ok(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable UUID id, @RequestBody ExpenseRequestDto expenseRequestDto) {
       Expense updatedExpense = expenseService.updateExpense(id, expenseRequestDto);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID id){
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
