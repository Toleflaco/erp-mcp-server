package dev.toleflaco.erpmcpserver.invoice;

import dev.toleflaco.erpmcpserver.purchaseorder.PurchaseOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime issuedAt;

    protected Invoice() {
    }

    public Invoice(PurchaseOrder purchaseOrder, BigDecimal amount, InvoiceStatus status) {
        this.purchaseOrder = purchaseOrder;
        this.amount = amount;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        this.issuedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public Long getId() {
        return id;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public OffsetDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
}
