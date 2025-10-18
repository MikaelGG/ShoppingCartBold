package com.ecommerce.services;

import com.ecommerce.dtos.InitButtonRequest;
import com.ecommerce.dtos.InitButtonResponse;
import com.ecommerce.models.transactionBoldModel;
import com.ecommerce.repositories.transactionBoldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class transactionBoldService {

    @Autowired
    hashIntegrityBoldService hashIntegrityBold;

    @Autowired
    transactionBoldRepository transactionBoldRepository;

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
        transaction.setStatus("PENDING");
        transaction.setIntegrityHash(hash);
        transaction.setIdClient(req.getIdClient());
        transaction.setIdAddress(req.getIdAddress());
        transaction.setCreateAt(LocalDateTime.now());

        transactionBoldRepository.save(transaction);

        InitButtonResponse res = new InitButtonResponse();
        res.setApiKey(apiKey);
        res.setOrderId(idInternal);
        res.setAmount(req.getAmount());
        res.setIntegrityHash(hash);

        return res;

    }

}
