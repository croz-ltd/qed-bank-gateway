package net.croz.qed.bank.gateway.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.croz.qed.bank.gateway.model.ModifyBalanceRequest;
import net.croz.qed.bank.gateway.model.Response;
import net.croz.qed.bank.gateway.service.TransactionGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TransactionGatewayImpl implements TransactionGateway {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionGatewayImpl.class);

    private final RestTemplate restTemplate;
    private final String serviceUrl;

    @Autowired
    public TransactionGatewayImpl(final RestTemplate restTemplate, @Value("${service-url.transaction}") final String serviceUrl) {
        this.restTemplate = restTemplate;
        this.serviceUrl = serviceUrl;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackPerformAdd")
    public Optional<Response<String>> performAdd(final ModifyBalanceRequest request) {
        LOGGER.info("Calling transaction service to perform balance addition...");

        final ResponseEntity<Response> response = restTemplate.postForEntity(
                serviceUrl + "/transaction/add", //
                request, //
                Response.class //
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.info("Transaction service is alive and responded with OK.");
            return Optional.ofNullable(response.getBody());
        }

        LOGGER.info("Transaction service is alive but responded with " + response.getStatusCode().name() + ".");
        return Optional.empty();
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackPerformWithdraw")
    public Optional<Response<String>> performWithdraw(final ModifyBalanceRequest request) {
        LOGGER.info("Calling transaction service to perform balance withdrawal...");

        final ResponseEntity<Response> response = restTemplate.postForEntity(
                serviceUrl + "/transaction/withdraw", //
                request, //
                Response.class //
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.info("Transaction service is alive and responded with OK.");
            return Optional.ofNullable(response.getBody());
        }

        LOGGER.info("Transaction service is alive but responded with " + response.getStatusCode().name() + ".");
        return Optional.empty();
    }

    public Optional<Response<String>> fallbackPerformAdd(final ModifyBalanceRequest request) {
        return Optional.of(Response.fail("Currently is not possible to execute transaction. Please try again."));
    }

    public Optional<Response<String>> fallbackPerformWithdraw(final ModifyBalanceRequest request) {
        return Optional.of(Response.fail("Currently is not possible to execute transaction. Please try again."));
    }
}
