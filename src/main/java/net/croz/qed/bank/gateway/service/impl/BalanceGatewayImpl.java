package net.croz.qed.bank.gateway.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.croz.qed.bank.gateway.config.ServiceUrlProperties;
import net.croz.qed.bank.gateway.model.Balance;
import net.croz.qed.bank.gateway.service.BalanceGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BalanceGatewayImpl implements BalanceGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceGatewayImpl.class);

    private static final Map<String, Optional<List<Balance>>> OIB_CACHE = new HashMap<>();
    private static final Map<String, Optional<Balance>> IBAN_CACHE = new HashMap<>();

    private final RestTemplate restTemplate;
    private final ServiceUrlProperties serviceUrlProperties;

    @Autowired
    public BalanceGatewayImpl(final RestTemplate restTemplate, final ServiceUrlProperties serviceUrlProperties) {
        this.restTemplate = restTemplate;
        this.serviceUrlProperties = serviceUrlProperties;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackGetByOib")
    public Optional<List<Balance>> getByOib(final String oib) {
        LOGGER.info("Calling balance service to get balance by oib...");

        final ResponseEntity<Optional<List<Balance>>> response = restTemplate.exchange( //
            serviceUrlProperties.getBalance() + "/balance/oib/" + oib, //
            HttpMethod.GET, //
            null, //
            new ParameterizedTypeReference<Optional<List<Balance>>>() { //
            } //
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.info("Balance service is alive and responded with OK.");
            OIB_CACHE.put(oib, response.getBody());
            return response.getBody();
        }

        LOGGER.info("Balance service is alive but responded with " + response.getStatusCode().name() + ".");
        OIB_CACHE.put(oib, Optional.empty());
        return Optional.empty();
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackGetByIban")
    public Optional<Balance> getByIban(final String iban) {
        LOGGER.info("Calling balance service to get balance by iban...");

        final ResponseEntity<Optional<Balance>> response = restTemplate.exchange( //
            serviceUrlProperties.getBalance() + "/balance/iban/" + iban, //
            HttpMethod.GET, //
            null, //
            new ParameterizedTypeReference<Optional<Balance>>() { //
            } //
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.info("Balance service is alive and responded with OK.");
            IBAN_CACHE.put(iban, response.getBody());
            return response.getBody();
        }

        LOGGER.info("Balance service is alive but responded with " + response.getStatusCode().name() + ".");
        IBAN_CACHE.put(iban, Optional.empty());
        return Optional.empty();
    }

    public Optional<List<Balance>> fallbackGetByOib(final String oib) {
        if (OIB_CACHE.containsKey(oib)) {
            return OIB_CACHE.get(oib);
        }
        return Optional.empty();
    }

    public Optional<Balance> fallbackGetByIban(final String iban) {
        if (IBAN_CACHE.containsKey(iban)) {
            return IBAN_CACHE.get(iban);
        }
        return Optional.empty();
    }

}
