package org.example.dto;

import java.math.BigDecimal;

public class PriceResponse {
    private BigDecimal value;
    private String formattedWithCurrency;
    private String formattedWithoutCurrency;
    private BigDecimal netValue;
    private BigDecimal vatAmount;

    public PriceResponse() {}

    public PriceResponse(BigDecimal value, String formattedWithCurrency, String formattedWithoutCurrency,
                         BigDecimal netValue, BigDecimal vatAmount) {
        this.value = value;
        this.formattedWithCurrency = formattedWithCurrency;
        this.formattedWithoutCurrency = formattedWithoutCurrency;
        this.netValue = netValue;
        this.vatAmount = vatAmount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getFormattedWithCurrency() {
        return formattedWithCurrency;
    }

    public String getFormattedWithoutCurrency() {
        return formattedWithoutCurrency;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }
}
