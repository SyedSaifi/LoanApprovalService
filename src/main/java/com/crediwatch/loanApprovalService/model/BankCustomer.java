package com.crediwatch.loanApprovalService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "bankCustomer")
public class BankCustomer {

    @Id
    private String cin;
    private String companyName;
    private String dateOfRegistration;
    private String monthName;
    private String state;
    private String roc;
    private String companyStatus;
    private String category;
    private String companyClass;
    private String companyType;
    private String authorizedCapital;
    private String paidUpCapital;
    private String activityCode;
    private String activityDescription;
    private String registeredOfficeAddress;
    private String email;
    private String x1;
    private String x2;
    private String x3;
    private String x4;
    private String x5;

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRoc() {
        return roc;
    }

    public void setRoc(String roc) {
        this.roc = roc;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompanyClass() {
        return companyClass;
    }

    public void setCompanyClass(String companyClass) {
        this.companyClass = companyClass;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getAuthorizedCapital() {
        return authorizedCapital;
    }

    public void setAuthorizedCapital(String authorizedCapital) {
        this.authorizedCapital = authorizedCapital;
    }

    public String getPaidUpCapital() {
        return paidUpCapital;
    }

    public void setPaidUpCapital(String paidUpCapital) {
        this.paidUpCapital = paidUpCapital;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getRegisteredOfficeAddress() {
        return registeredOfficeAddress;
    }

    public void setRegisteredOfficeAddress(String registeredOfficeAddress) {
        this.registeredOfficeAddress = registeredOfficeAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getX1() {
        return x1;
    }

    public void setX1(String x1) {
        this.x1 = x1;
    }

    public String getX2() {
        return x2;
    }

    public void setX2(String x2) {
        this.x2 = x2;
    }

    public String getX3() {
        return x3;
    }

    public void setX3(String x3) {
        this.x3 = x3;
    }

    public String getX4() {
        return x4;
    }

    public void setX4(String x4) {
        this.x4 = x4;
    }

    public String getX5() {
        return x5;
    }

    public void setX5(String x5) {
        this.x5 = x5;
    }

    @Override
    public String toString() {
        return "BankCustomer{" +
                "cin='" + cin + '\'' +
                ", companyName='" + companyName + '\'' +
                ", dateOfRegistration='" + dateOfRegistration + '\'' +
                ", monthName='" + monthName + '\'' +
                ", state='" + state + '\'' +
                ", roc='" + roc + '\'' +
                ", companyStatus='" + companyStatus + '\'' +
                ", category='" + category + '\'' +
                ", companyClass='" + companyClass + '\'' +
                ", companyType='" + companyType + '\'' +
                ", authorizedCapital='" + authorizedCapital + '\'' +
                ", paidUpCapital='" + paidUpCapital + '\'' +
                ", activityCode='" + activityCode + '\'' +
                ", activityDescription='" + activityDescription + '\'' +
                ", registeredOfficeAddress='" + registeredOfficeAddress + '\'' +
                ", email='" + email + '\'' +
                ", x1='" + x1 + '\'' +
                ", x12='" + x2 + '\'' +
                ", x3='" + x3 + '\'' +
                ", x4='" + x4 + '\'' +
                ", x5='" + x5 + '\'' +
                '}';
    }
}
