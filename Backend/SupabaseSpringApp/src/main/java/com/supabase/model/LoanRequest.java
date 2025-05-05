package com.supabase.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

public class LoanRequest {

    @DecimalMin(value = "1.0", message = "Loan amount must be greater than 0")
    private double loanAmount;

    @DecimalMin(value = "0.1", message = "Interest rate must be greater than 0")
    private double interestRate;

    @Min(value = 1, message = "Loan term must be at least 1 year")
    private int loanTerm;

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }
}
