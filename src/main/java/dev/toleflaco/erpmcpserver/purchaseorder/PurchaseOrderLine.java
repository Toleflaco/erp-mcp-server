package dev.toleflaco.erpmcpserver.purchaseorder;

import dev.toleflaco.erpmcpserver.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_lines")
public class PurchaseOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal unitPriceAtOrder;

    protected PurchaseOrderLine() {
    }

    public PurchaseOrderLine(PurchaseOrder purchaseOrder, Product product, int quantity, BigDecimal unitPriceAtOrder) {
        this.purchaseOrder = purchaseOrder;
        this.product = product;
        this.quantity = quantity;
        this.unitPriceAtOrder = unitPriceAtOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitPriceAtOrder(BigDecimal unitPriceAtOrder) {
        this.unitPriceAtOrder = unitPriceAtOrder;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPriceAtOrder() {
        return unitPriceAtOrder;
    }

    public Long getId() {
        return id;
    }
}
