package dev.toleflaco.erpmcpserver.purchaseorder;

import java.util.List;

public record CreatePurchaseOrderRequest(
        Long supplierId,
        List<CreatePurchaseOrderLineRequest> lines
) {
}
