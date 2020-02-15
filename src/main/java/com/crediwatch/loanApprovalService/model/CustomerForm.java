package com.crediwatch.loanApprovalService.model;

public class CustomerForm {

    private String cin;

    private String email;

    private String companyClass;

    private String companyName;

    private String companyType;

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyClass() {
        return companyClass;
    }

    public void setCompanyClass(String companyClass) {
        this.companyClass = companyClass;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    @Override
    public String toString() {
        return "CustomerForm{" +
                "cin='" + cin + '\'' +
                ", email='" + email + '\'' +
                ", companyClass='" + companyClass + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyType='" + companyType + '\'' +
                '}';
    }
}
