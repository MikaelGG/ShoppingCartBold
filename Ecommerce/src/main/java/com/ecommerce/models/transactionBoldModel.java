package com.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_detail")
@Data
public class transactionBoldModel {

    @Id
    private String idInternal;

    @Column(name = "id_bold_order")
    private String idBoldOrder;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status")
    private String status;

    @Column(name = "raw_payload", columnDefinition = "JSON")
    private String rawPayload;

    @Column(name = "integrity_hash")
    private String integrityHash;

    @Column(name = "id_client")
    private Long idClient;

    @Column(name = "id_address")
    private Long idAddress;

    @Column(name = "create_at")
    private LocalDateTime createAt;

}
