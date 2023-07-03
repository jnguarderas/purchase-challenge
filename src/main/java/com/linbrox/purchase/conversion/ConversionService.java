package com.linbrox.purchase.conversion;

import com.linbrox.purchase.conversion.response.ConversionResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ConversionService {
    private final ConversionAPI conversionAPI;

    public ConversionService(ConversionAPI conversionAPI) {
        this.conversionAPI = conversionAPI;
    }

    @CircuitBreaker(name = "externalAPI", fallbackMethod = "fallbackResponse")
    public Mono<ConversionResponse> callExternalAPI(UUID hyundaiModel) {
        return this.conversionAPI.retrieveByUUID(hyundaiModel.toString());
    }

    private Mono<ConversionResponse> fallbackResponse(Throwable throwable) {
        // Handle the case when the external API call fails
        return Mono.error(new RuntimeException("Something is wrong"));
    }
}
