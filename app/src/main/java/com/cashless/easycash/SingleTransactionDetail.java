package com.cashless.easycash;



public class SingleTransactionDetail {
    private String transactionId,date,with,sentOrReceived;
    private int amount;

    SingleTransactionDetail(String transactionId, String date, String with, String sentOrReceived, int amount){
        this.transactionId=transactionId;
        this.date=date;
        this.with=with;
        this.sentOrReceived=sentOrReceived;
        this.amount=amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    public String getSentOrReceived() {
        return sentOrReceived;
    }

    public void setSentOrReceived(String sentOrReceived) {
        this.sentOrReceived = sentOrReceived;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
