package com.crediwatch.loanApprovalService.model;

import javax.validation.constraints.NotNull;

public class ApprovalRequest {

    @NotNull
    private String cin;

    @NotNull
    private String duration;

    @NotNull
    private String loanAmount;

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Override
    public String toString() {
        return "ApprovalRequest{" +
                "cin='" + cin + '\'' +
                '}';
    }
}
