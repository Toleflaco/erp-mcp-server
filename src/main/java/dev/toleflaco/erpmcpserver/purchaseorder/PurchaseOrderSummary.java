package dev.toleflaco.erpmcpserver.purchaseorder;

import java.time.OffsetDateTime;

public record PurchaseOrderSummary(
        Long id,
        String supplierName,
        PurchaseOrderStatus status,
        OffsetDateTime createdAt
) {}



