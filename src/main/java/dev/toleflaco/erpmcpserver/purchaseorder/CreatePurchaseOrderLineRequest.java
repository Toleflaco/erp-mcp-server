package dev.toleflaco.erpmcpserver.purchaseorder;

public record CreatePurchaseOrderLineRequest(
        Long productId,
        int quantity
) {
}
