package com.linbrox.purchase.entity;


import com.linbrox.purchase.common.CryptoCurrencyEnum;
import com.linbrox.purchase.common.HyundaiModelEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Entity(name = "purchase")
@Data
@Builder
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "version")
    private String version;
    @Enumerated(EnumType.STRING)
    @Column(name = "model")
    private HyundaiModelEnum hyundaiModel;
    @Enumerated(EnumType.STRING)
    @Column(name = "cryptocurrency")
    private CryptoCurrencyEnum cryptoCurrencyEnum;
    @Column(name = "price_usd")
    private Double priceUSD;
    @Column(name = "price_crypto")
    private Double priceCryptoCurrency;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "convertion_id")
    private UUID convertionId;

    public Purchase(UUID id, String fullName, String version, HyundaiModelEnum hyundaiModel, CryptoCurrencyEnum cryptoCurrencyEnum, Double priceUSD, Double priceCryptoCurrency, Date createdAt, UUID convertionId) {
        this.id = id;
        this.fullName = fullName;
        this.version = version;
        this.hyundaiModel = hyundaiModel;
        this.cryptoCurrencyEnum = cryptoCurrencyEnum;
        this.priceUSD = priceUSD;
        this.priceCryptoCurrency = priceCryptoCurrency;
        this.createdAt = createdAt;
        this.convertionId = convertionId;
    }

    @Override
    public String toString( ) {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
