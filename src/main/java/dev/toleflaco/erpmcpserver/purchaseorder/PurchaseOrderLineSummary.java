package dev.toleflaco.erpmcpserver.purchaseorder;

import java.math.BigDecimal;

public record PurchaseOrderLineSummary(
        Long productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
}
