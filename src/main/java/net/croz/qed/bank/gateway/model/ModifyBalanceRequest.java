package net.croz.qed.bank.gateway.model;

import java.math.BigDecimal;

public class ModifyBalanceRequest {

    private String     iban;
    private BigDecimal amount;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
