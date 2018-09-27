package com.mymoid.batch.configuration;

import java.util.Date;

public class Transaction {

   private Date createdAt;
   private String concept;
   private String reference;
   private String partialPan;
   private String holderName;
   private String expirationDate;
   private String brand;
   private String comercial;
   private String issuer;
   private String contry;
   private String interchangeFee;
   private String pecunpayFee;
   private String discountRate;
   private String fileName;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPartialPan() {
        return partialPan;
    }

    public void setPartialPan(String partialPan) {
        this.partialPan = partialPan;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getComercial() {
        return comercial;
    }

    public void setComercial(String comercial) {
        this.comercial = comercial;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    public String getInterchangeFee() {
        return interchangeFee;
    }

    public void setInterchangeFee(String interchangeFee) {
        this.interchangeFee = interchangeFee;
    }

    public String getPecunpayFee() {
        return pecunpayFee;
    }

    public void setPecunpayFee(String pecunpayFee) {
        this.pecunpayFee = pecunpayFee;
    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("brand='").append(brand).append('\'');
        sb.append(", comercial='").append(comercial).append('\'');
        sb.append(", concept='").append(concept).append('\'');
        sb.append(", contry='").append(contry).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", discountRate='").append(discountRate).append('\'');
        sb.append(", expirationDate='").append(expirationDate).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", holderName='").append(holderName).append('\'');
        sb.append(", interchangeFee='").append(interchangeFee).append('\'');
        sb.append(", issuer='").append(issuer).append('\'');
        sb.append(", partialPan='").append(partialPan).append('\'');
        sb.append(", pecunpayFee='").append(pecunpayFee).append('\'');
        sb.append(", reference='").append(reference).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
