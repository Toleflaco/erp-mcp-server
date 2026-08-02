package dev.toleflaco.erpmcpserver.purchaseorder;

import java.math.BigDecimal;

public class PurchaseOrderMapper {

    public static PurchaseOrderInfo toInfo(PurchaseOrder purchaseOrder) {

        return new PurchaseOrderInfo(
                purchaseOrder.getId(),
                purchaseOrder.getSupplier().getName(),
                purchaseOrder.getStatus(),
                purchaseOrder.getCreatedAt(),
                calculateOrderTotal(purchaseOrder),
                purchaseOrder.getLines().stream().map(PurchaseOrderMapper::toDto).toList()
        );
    }

    private static PurchaseOrderLineSummary toDto(PurchaseOrderLine line) {
        return new PurchaseOrderLineSummary(
                line.getProduct().getId(),
                line.getProduct().getName(),
                line.getQuantity(),
                line.getUnitPriceAtOrder(),
                calculateLineTotal(line)
        );
    }

    private static BigDecimal calculateLineTotal(PurchaseOrderLine line) {
        return line.getUnitPriceAtOrder().multiply(BigDecimal.valueOf(line.getQuantity()));
    }

    private static BigDecimal calculateOrderTotal(PurchaseOrder purchaseOrder) {

        return purchaseOrder.getLines().stream()
                .map(PurchaseOrderMapper::calculateLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
