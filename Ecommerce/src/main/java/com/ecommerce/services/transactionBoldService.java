package com.ecommerce.services;

import com.ecommerce.dtos.InitButtonRequest;
import com.ecommerce.dtos.InitButtonResponse;
import com.ecommerce.models.transactionBoldModel;
import com.ecommerce.repositories.transactionBoldRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class transactionBoldService {

    @Autowired
    hashIntegrityBoldService hashIntegrityBold;

    @Autowired
    transactionBoldRepository transactionBoldRepository;

    @Autowired
    ObjectMapper mapper;

    @Value("${apiKey}")
    private String apiKey;

    @Transactional
    public InitButtonResponse createTransaction(InitButtonRequest req) {
        String idInternal = "ORD-" + UUID.randomUUID().toString();

        String hash = hashIntegrityBold.generateIntegrityHash(idInternal, req.getAmount(), req.getCurrency());

        transactionBoldModel transaction = new transactionBoldModel();
        transaction.setIdInternal(idInternal);
        transaction.setAmount(req.getAmount());
        transaction.setCurrency(req.getCurrency());
        transaction.setStatus("CREATED");
        transaction.setIntegrityHash(hash);
        transaction.setIdClient(req.getIdClient());
        transaction.setIdAddress(req.getIdAddress());

        try {
            String products = mapper.writeValueAsString(req.getProducts());
            transaction.setProducts(products);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        transactionBoldRepository.save(transaction);

        InitButtonResponse res = new InitButtonResponse();
        res.setApiKey(apiKey);
        res.setOrderId(idInternal);
        res.setAmount(req.getAmount());
        res.setIntegrityHash(hash);

        return res;

    }

    @Transactional(readOnly = true)
    public List<transactionBoldModel> getAllTransactions() {
        return transactionBoldRepository.findAllByOrderByCreateAtDesc();
    }

    @Transactional(readOnly = true)
    public List<transactionBoldModel> getTransactionsByIdClient(Long idClient) {
        if (!transactionBoldRepository.existsByIdClient(idClient)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }
        return transactionBoldRepository.findByIdClientOrderByCreateAtDesc(idClient);
    }

    @Transactional(readOnly = true)
    public List<InitButtonRequest.ProductBoldDTO> getProductsByTransaction(String idInternal) {
        if (!transactionBoldRepository.existsByIdInternal(idInternal)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
        }

        String products = transactionBoldRepository.findProductsByIdInternal(idInternal);
        try {
            List<InitButtonRequest.ProductBoldDTO> productsList = mapper.readValue(products, new TypeReference<List<InitButtonRequest.ProductBoldDTO>>() {});

            log.info("List converted {}", productsList);

            return productsList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public transactionBoldModel updateShippingStatus(String idInternal, transactionBoldModel.ShippingStatus newStatus) {
        if (!transactionBoldRepository.existsByIdInternal(idInternal)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
        }
        transactionBoldModel transaction = transactionBoldRepository.findByIdInternal(idInternal).orElse(null);
        transaction.setShippingStatus(newStatus);

        return transactionBoldRepository.save(transaction);

    }

}
