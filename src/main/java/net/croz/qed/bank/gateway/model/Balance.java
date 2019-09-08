package net.croz.qed.bank.gateway.model;

import java.math.BigDecimal;

public class Balance {

    private String oib;
    private String iban;
    private BigDecimal amount;
    private String currency;
    private String country;

    public String getOib() {
        return oib;
    }

    public void setOib(final String oib) {
        this.oib = oib;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(final String iban) {
        this.iban = iban;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

}
