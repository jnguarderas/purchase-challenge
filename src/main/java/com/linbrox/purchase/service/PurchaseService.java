package com.linbrox.purchase.service;


import com.linbrox.purchase.common.CryptoCurrencyEnum;
import com.linbrox.purchase.common.HyundaiModelEnum;
import com.linbrox.purchase.config.RabbitMQConfig;
import com.linbrox.purchase.conversion.ConversionService;
import com.linbrox.purchase.conversion.response.ConversionResponse;
import com.linbrox.purchase.conversion.response.ConversionVersion;
import com.linbrox.purchase.entity.Purchase;
import com.linbrox.purchase.repository.PurchaseRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ConversionService conversionService;

    private final RabbitTemplate rabbitTemplate;

    public PurchaseService(PurchaseRepository purchaseRepository,
                           ConversionService conversionService,
                           RabbitTemplate rabbitTemplate) {
        this.purchaseRepository = purchaseRepository;
        this.conversionService = conversionService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Purchase create(String uuidConvertion, String fullName, String version, HyundaiModelEnum modelEnum, CryptoCurrencyEnum cryptoCurrency){
        ConversionResponse response = this.conversionService.callExternalAPI(UUID.fromString(uuidConvertion)).block();
        for(ConversionVersion conversionVersion: response.getConversionVersionList()){
            if(conversionVersion.getVersion().equals(version) &&
            conversionVersion.getHyundaiModel().equals(modelEnum.name()) &&
            conversionVersion.getCryptoCurrency().equals(cryptoCurrency.name())){
                Purchase purchase = Purchase.builder()
                        .fullName(fullName)
                        .version(conversionVersion.getVersion())
                        .hyundaiModel(modelEnum)
                        .priceUSD(conversionVersion.getPriceUSD())
                        .priceCryptoCurrency(conversionVersion.getPriceCryptoCurrency())
                        .cryptoCurrencyEnum(CryptoCurrencyEnum.valueOf(conversionVersion.getCryptoCurrency()))
                        .convertionId(response.getId())
                        .createdAt(new Date())
                        .build();
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_NAME, purchase.toString());
                return this.purchaseRepository.save(purchase);
            }
        }
        return null;
    }
}
