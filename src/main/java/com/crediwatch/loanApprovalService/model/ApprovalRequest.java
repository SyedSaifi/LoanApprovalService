package com.crediwatch.loanApprovalService.model;

public class ApprovalRequest {

    private String cin;

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
        return "ApprovalRequest{" +
                "cin='" + cin + '\'' +
                ", x1='" + x1 + '\'' +
                ", x2='" + x2 + '\'' +
                ", x3='" + x3 + '\'' +
                ", x4='" + x4 + '\'' +
                ", x5='" + x5 + '\'' +
                '}';
    }
}
