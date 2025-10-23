package com.ecommerce.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
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

    @Column(name = "products", columnDefinition = "JSON", updatable = false)
    private String products;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_status")
    private ShippingStatus shippingStatus = ShippingStatus.PROCESO_ENVIO;

    @CreationTimestamp
    @Column(name = "create_at", updatable = false)
    private Timestamp createAt;

    public enum ShippingStatus {
        PROCESO_ENVIO,
        ENVIADO,
        ENTREGADO,
        PAGO_PENDIENTE,
        PROCESANDO_PAGO,
        PAGO_RECHAZADO,
        PAGO_CANCELADO
    }

}
