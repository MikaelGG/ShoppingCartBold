package com.ecommerce.controllers;

import com.ecommerce.dtos.InitButtonRequest;
import com.ecommerce.dtos.InitButtonResponse;
import com.ecommerce.models.transactionBoldModel;
import com.ecommerce.services.transactionBoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class transactionBoldRESTController {

    @Autowired
    transactionBoldService transactionBoldService;

    @PostMapping
    public ResponseEntity<InitButtonResponse> createTransaction(@RequestBody InitButtonRequest req) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionBoldService.createTransaction(req));
    }

    @GetMapping
    public ResponseEntity<List<transactionBoldModel>> getAllTransactions() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionBoldService.getAllTransactions());
    }

    @GetMapping("/{idClient}")
    public ResponseEntity<List<transactionBoldModel>> getTransactionsByIdClient(@PathVariable Long idClient) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionBoldService.getTransactionsByIdClient(idClient));
    }

    @GetMapping("/products/{idInternal}")
    public ResponseEntity<List<InitButtonRequest.ProductBoldDTO>> getProductsByTransaction(@PathVariable String idInternal) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionBoldService.getProductsByTransaction(idInternal));
    }

    @PutMapping("/{idInternal}")
    public ResponseEntity<transactionBoldModel> updateShippingStatus(@PathVariable String idInternal, @RequestBody transactionBoldModel.ShippingStatus newStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionBoldService.updateShippingStatus(idInternal, newStatus));
    }

}
