package net.croz.qed.bank.gateway;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@SpringBootApplication
public class QedBankGatewayApplication {

    public static void main(final String[] args) {
        SpringApplication.run(QedBankGatewayApplication.class, args);
    }

    @Bean
    public static JaegerTracer getTracer() {
        final Configuration.SamplerConfiguration samplerConfig =
            Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
        final Configuration.ReporterConfiguration reporterConfig =
            Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        return new Configuration("qed-bank-gateway").withSampler(samplerConfig).withReporter(reporterConfig)
            .getTracer();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
