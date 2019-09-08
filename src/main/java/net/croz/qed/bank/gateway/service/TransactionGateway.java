package net.croz.qed.bank.gateway.service;

import net.croz.qed.bank.gateway.model.ModifyBalanceRequest;
import net.croz.qed.bank.gateway.model.Response;

import java.util.Optional;

public interface TransactionGateway {

    Optional<Response<String>> performAdd(ModifyBalanceRequest request);

    Optional<Response<String>> performWithdraw(ModifyBalanceRequest request);

}
