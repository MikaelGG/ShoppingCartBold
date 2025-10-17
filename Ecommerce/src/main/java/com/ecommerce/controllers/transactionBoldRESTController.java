package com.ecommerce.controllers;

import com.ecommerce.dtos.InitButtonRequest;
import com.ecommerce.dtos.InitButtonResponse;
import com.ecommerce.services.transactionBoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class transactionBoldRESTController {

    @Autowired
    transactionBoldService transactionBoldService;

    @PostMapping
    public ResponseEntity<InitButtonResponse> createTransaction(@RequestBody InitButtonRequest req) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionBoldService.createTransaction(req));
    }

}
