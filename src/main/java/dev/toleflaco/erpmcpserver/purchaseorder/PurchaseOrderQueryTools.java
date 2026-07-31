package dev.toleflaco.erpmcpserver.purchaseorder;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PurchaseOrderQueryTools {

    private final PurchaseOrderRepository purchaseOrderRepository;


    public PurchaseOrderQueryTools(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Transactional(readOnly = true)
    @Tool(description = "Get detailed information about a purchase order. " +
            "Returns purchase order id, supplier name, status, created date, order total, " +
            "and list of purchase order lines.")
    public PurchaseOrderInfo getPurchaseOrder(Long id) {

        return purchaseOrderRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }


    private PurchaseOrderInfo toDto(PurchaseOrder purchaseOrder) {

        return new PurchaseOrderInfo(
                purchaseOrder.getId(),
                purchaseOrder.getSupplier().getName(),
                purchaseOrder.getStatus(),
                purchaseOrder.getCreatedAt(),
                calculateOrderTotal(purchaseOrder),
                purchaseOrder.getLines().stream().map(this::toDto).toList()
        );
    }

    private PurchaseOrderLineSummary toDto(PurchaseOrderLine line) {
        return new PurchaseOrderLineSummary(
                line.getProduct().getId(),
                line.getProduct().getName(),
                line.getQuantity(),
                line.getUnitPriceAtOrder(),
                calculateLineTotal(line)
        );
    }

    private BigDecimal calculateLineTotal(PurchaseOrderLine line) {
        return line.getUnitPriceAtOrder().multiply(BigDecimal.valueOf(line.getQuantity()));
    }

    private BigDecimal calculateOrderTotal(PurchaseOrder purchaseOrder) {

        return purchaseOrder.getLines().stream()
                .map(this::calculateLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
