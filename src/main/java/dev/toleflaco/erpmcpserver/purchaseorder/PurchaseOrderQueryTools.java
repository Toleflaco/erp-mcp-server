package dev.toleflaco.erpmcpserver.purchaseorder;

import dev.toleflaco.erpmcpserver.product.ProductInfo;
import dev.toleflaco.erpmcpserver.product.ProductSummary;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

        return purchaseOrderRepository.findByIdWithLinesAndProducts(id)
                .map(PurchaseOrderMapper::toInfo)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    @Tool(description = "List all purchase orders in a given status. Returns the order ID, supplier name, order status, and creation date.")
    public List<PurchaseOrderSummary> listPurchaseOrdersByStatus(
            @ToolParam(description = "One of: DRAFT, SENT, RECEIVED, CANCELLED.") PurchaseOrderStatus status) {

        return purchaseOrderRepository.findByStatusOrderByCreatedAtDesc(status)
                .stream().map(this::toSummary)
                .toList();
    }


    private PurchaseOrderSummary toSummary(PurchaseOrder purchaseOrder) {
        return new PurchaseOrderSummary(
                purchaseOrder.getId(),
                purchaseOrder.getSupplier().getName(),
                purchaseOrder.getStatus(),
                purchaseOrder.getCreatedAt()
        );
    }



}
