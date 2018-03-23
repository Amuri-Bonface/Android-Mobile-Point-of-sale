package com.ngaitai.ngaitaiafricaapp;


public class DistributorModel {

    private String distributorCode;
    private String distibutorName;
    private String distributorphone;


    public DistributorModel(){

    }

    public DistributorModel(String distibutorName, String distributorCode, String distributorphone) {
        this.distibutorName = distibutorName;
        this.distributorCode = distributorCode;
        this.distributorphone = distributorphone;
    }

    public String getDistibutorName() {
        return distibutorName;
    }

    public void setDistibutorName(String distibutorName) {
        this.distibutorName = distibutorName;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public String getDistributorphone() {
        return distributorphone;
    }

    public void setDistributorphone(String distributorphone) {
        this.distributorphone = distributorphone;
    }
}
