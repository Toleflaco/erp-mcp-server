package dev.toleflaco.erpmcpserver.purchaseorder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record PurchaseOrderInfo(
        Long id,
        String supplierName,
        PurchaseOrderStatus status,
        OffsetDateTime createdAt,
        BigDecimal orderTotal,
        List<PurchaseOrderLineSummary> lines
) {
}
