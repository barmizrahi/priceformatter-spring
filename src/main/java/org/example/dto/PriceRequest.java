package org.example.dto;

public class PriceRequest {
    private String amount;
    private String currency;

    public PriceRequest() {}

    public PriceRequest(String amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public String getAmount() {
        return amount == null? "noAmount" : amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
