package dev.toleflaco.erpmcpserver.product;

import dev.toleflaco.erpmcpserver.supplier.Supplier;
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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;

    // TODO(sesion-15): revisar precision/scale al migrar a PostgreSQL
    @Column(nullable = false)
    private BigDecimal unitPrice;

    private int stock;
    private int minStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    protected Product() {
    }

    public Product(String name, String category, BigDecimal unitPrice, int stock, int minStock, Supplier supplier) {
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.minStock = minStock;
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public int getMinStock() {
        return minStock;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Long getId() {
        return id;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }


}
