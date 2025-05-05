package com.supabase.controller;

import com.supabase.model.LoanRequest;
import com.supabase.model.LoanResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/loan")
@Tag(name = "Loan Calculator", description = "Monthly payment calculator based on interest, term and amount")
public class LoanController {

    @Operation(summary = "Calculate loan payment", description = "Returns monthly payment based on loan details")
    @PostMapping("/calculate")
    public ResponseEntity<LoanResponse> calculateLoan(@RequestBody @Valid LoanRequest request) {
        double principal = request.getLoanAmount();
        double annualRate = request.getInterestRate();
        int termYears = request.getLoanTerm();
        double monthlyRate = annualRate / 100 / 12;
        int months = termYears * 12;
        double monthlyPayment = 0.0;

        if (monthlyRate > 0 && months > 0) {
            monthlyPayment = (principal * monthlyRate * Math.pow(1 + monthlyRate, months))
                    / (Math.pow(1 + monthlyRate, months) - 1);
        }

        LoanResponse response = new LoanResponse();
        response.setMonthlyPayment(Math.round(monthlyPayment * 100.0) / 100.0);
        return ResponseEntity.ok(response);
    }
}
