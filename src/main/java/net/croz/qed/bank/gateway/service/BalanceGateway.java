package net.croz.qed.bank.gateway.service;

import net.croz.qed.bank.gateway.model.Balance;

import java.util.List;
import java.util.Optional;

public interface BalanceGateway {

    Optional<List<Balance>> getByOib(final String oib);

    Optional<Balance> getByIban(final String iban);

}
