package com.linbrox.purchase.controller;


import com.linbrox.purchase.entity.Purchase;
import com.linbrox.purchase.request.PurchaseRequest;
import com.linbrox.purchase.service.PurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@Api(tags = "Purchase")
public class PurchaseController {
    private final Logger logger = Logger.getLogger(PurchaseController.class.getName());

    private PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService){
        this.purchaseService = purchaseService;
    }


    @ApiOperation(value = "Execute operations of transactions", notes = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Something bad happened")
    })
    @PostMapping("/purchase/create")
    public Purchase create(@RequestBody PurchaseRequest request){
        return this.purchaseService.create(request.getConvertionId().toString(),
                request.getFullName(),
                request.getVersion(),
                request.getModel(),
                request.getCryptocurrency());
    }
}
