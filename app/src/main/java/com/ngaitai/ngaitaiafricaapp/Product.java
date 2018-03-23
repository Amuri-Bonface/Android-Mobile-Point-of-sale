package com.ngaitai.ngaitaiafricaapp;


public class Product {
    private String image;
    private String Description;
    private String SellingPrice;
    private String Remarks;
    private String BV;

    public  Product(){

    }

    public Product(String image, String Description, String SellingPrice, String Remarks,String BV) {
        this.image = image;
        this.Description = Description;
        this.SellingPrice = SellingPrice;
        this.Remarks = Remarks;
        this.BV=BV;
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
}
