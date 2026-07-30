package dev.toleflaco.erpmcpserver.supplier;

import dev.toleflaco.erpmcpserver.product.ProductSummary;

import java.util.List;

public record SupplierInfo(
        Long id,
        String name,
        List<ProductSummary> products
) {}
