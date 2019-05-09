package net.croz.qed.bank.gateway.controller;

import net.croz.qed.bank.gateway.model.Balance;
import net.croz.qed.bank.gateway.model.ModifyBalanceRequest;
import net.croz.qed.bank.gateway.model.Response;
import net.croz.qed.bank.gateway.service.BalanceGateway;
import net.croz.qed.bank.gateway.service.TransactionGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GatewayController {

    private final BalanceGateway balanceGateway;
    private final TransactionGateway transactionGateway;

    @Autowired
    public GatewayController(final BalanceGateway balanceGateway, final TransactionGateway transactionGateway) {
        this.balanceGateway = balanceGateway;
        this.transactionGateway = transactionGateway;
    }

    @GetMapping("/balance/{iban}")
    public ResponseEntity<Balance> getBalanceByIban(@PathVariable final String iban) {
        return ResponseEntity.of(balanceGateway.getByIban(iban));
    }

    @GetMapping("/balances/{oib}")
    public ResponseEntity<List<Balance>> getBalanceByOib(@PathVariable final String oib) {
        return ResponseEntity.of(balanceGateway.getByOib(oib));
    }

    @PostMapping("/transaction/add")
    public ResponseEntity<Response<String>> addFund(@RequestBody final ModifyBalanceRequest request) {
        return ResponseEntity.of(transactionGateway.performAdd(request));
    }

    @PostMapping("/transaction/withdraw")
    public ResponseEntity<Response<String>> withdrawFund(@RequestBody final ModifyBalanceRequest request) {
        return ResponseEntity.of(transactionGateway.performWithdraw(request));
    }

}
