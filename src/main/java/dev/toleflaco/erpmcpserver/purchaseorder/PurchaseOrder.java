package dev.toleflaco.erpmcpserver.purchaseorder;

import dev.toleflaco.erpmcpserver.supplier.Supplier;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseOrderStatus status;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderLine> lines = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;


    protected PurchaseOrder() {
    }

    public PurchaseOrder(Supplier supplier, PurchaseOrderStatus status) {
        this.supplier = supplier;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public Long getId() {
        return id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    private void setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }

    public List<PurchaseOrderLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    // Métodos helper
    public void addLine(PurchaseOrderLine line) {
        lines.add(line);
        line.setPurchaseOrder(this);
    }

    public void removeLine(PurchaseOrderLine line) {
        lines.remove(line);
        line.setPurchaseOrder(null);
    }

    public void send() {
        if (this.status != PurchaseOrderStatus.DRAFT) {
            throw new IllegalStateException("Cannot send order in status " + this.status + ", must be DRAFT");
        }
        this.status = PurchaseOrderStatus.SENT;
    }

    public void cancel() {
        if (this.status != PurchaseOrderStatus.DRAFT && this.status != PurchaseOrderStatus.SENT) {
            throw new IllegalStateException("Cannot cancel order in status " + this.status + ", must be DRAFT or SENT");
        }
        this.status = PurchaseOrderStatus.CANCELLED;
    }
}
