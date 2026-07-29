package dev.toleflaco.erpmcpserver.product;

import java.math.BigDecimal;

public record ProductInfo(
        Long id,
        String name,
        String category,
        BigDecimal unitPrice,
        Integer stock,
        Integer minStock,
        String supplierName
) {
}
