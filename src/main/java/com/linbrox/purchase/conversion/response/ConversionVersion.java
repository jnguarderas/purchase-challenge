package com.linbrox.purchase.conversion.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ConversionVersion {
    private UUID id;
    private String hyundaiModel;
    private String version;
    private String cryptoCurrency;
    private Double priceUSD;
    private Double priceCryptoCurrency;
}
