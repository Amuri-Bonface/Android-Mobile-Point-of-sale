package com.ngaitai.ngaitaiafricaapp;


public class ProductDescription {


        private String image;
        private String Description;
        private String SellingPrice;
        private String Remarks;
        private String BV;
        private String distributorCode;
        private String distributorName;
        private String distributorPhone;
    private String quantity;
    private int totBv;
    private float totPrice;


        public  ProductDescription(){

        }

    public ProductDescription(String image, String description, String sellingPrice, String remarks, String BV, String distributorCode, String distributorName, String distributorPhone, String quantity, int totBv, float totPrice) {
        this.image = image;
        Description = description;
        SellingPrice = sellingPrice;
        Remarks = remarks;
        this.BV = BV;
        this.distributorCode = distributorCode;
        this.distributorName = distributorName;
        this.distributorPhone = distributorPhone;
        this.quantity = quantity;
        this.totBv = totBv;
        this.totPrice = totPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getBV() {
        return BV;
    }

    public void setBV(String BV) {
        this.BV = BV;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getDistributorPhone() {
        return distributorPhone;
    }

    public void setDistributorPhone(String distributorPhone) {
        this.distributorPhone = distributorPhone;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getTotBv() {
        return totBv;
    }

    public void setTotBv(int totBv) {
        this.totBv = totBv;
    }

    public float getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(float totPrice) {
        this.totPrice = totPrice;
    }
}


