package dev.toleflaco.erpmcpserver.purchaseorder;

import dev.toleflaco.erpmcpserver.product.Product;
import dev.toleflaco.erpmcpserver.product.ProductRepository;
import dev.toleflaco.erpmcpserver.supplier.Supplier;
import dev.toleflaco.erpmcpserver.supplier.SupplierRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PurchaseOrderWriteTools {

    private final SupplierRepository supplierRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;

    public PurchaseOrderWriteTools(SupplierRepository supplierRepository, PurchaseOrderRepository purchaseOrderRepository, ProductRepository productRepository) {
        this.supplierRepository = supplierRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @Tool(description = "Create a purchase order with one or more product lines. The order starts in DRAFT status." +
            " Returns its ID, the supplier, the status, the creation date, the total order value, and the order lines.")
    public PurchaseOrderInfo createPurchaseOrder(
            @ToolParam(description = "The purchase order to create. Must include a supplier ID and a non-empty list of lines," +
                    " where each line has a product ID and a positive quantity.")
            CreatePurchaseOrderRequest request) {

        List<String> errors = new ArrayList<>();
        Map<Integer, Product> productsByLineIndex = new HashMap<>();
        if (request.supplierId() == null) {
            throw new IllegalArgumentException("Supplier cannot be null");
        }
        if (request.lines() == null || request.lines().isEmpty()) {
            throw new IllegalArgumentException("Purchase order line cannot be null or empty");
        }
        Supplier supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        for (int i = 0; i < request.lines().size(); i++) {
            CreatePurchaseOrderLineRequest lineRequest = request.lines().get(i);

            // 1. ¿quantity > 0?
            if (lineRequest.quantity() <= 0) {
                errors.add("Line " + (i + 1) + ": quantity must be positive, got " + lineRequest.quantity());
            }
            // 2. ¿existe el producto?
            Optional<Product> productOpt = productRepository.findById(lineRequest.productId());
            if (productOpt.isEmpty()) {
                errors.add("Line " + (i + 1) + ": product " + lineRequest.productId() + " not found");
            } else {
                Product product = productOpt.get();
                productsByLineIndex.put(i, product);
            }
        }
        if (!errors.isEmpty()) throw new IllegalArgumentException(String.join("; ", errors));

        PurchaseOrder purchaseOrder = new PurchaseOrder(supplier, PurchaseOrderStatus.DRAFT);

        for (int i = 0; i < request.lines().size(); i++) {
            Product product = productsByLineIndex.get(i);
            CreatePurchaseOrderLineRequest lineRequest = request.lines().get(i);
            PurchaseOrderLine line = new PurchaseOrderLine(
                    purchaseOrder,
                    product,
                    lineRequest.quantity(),
                    product.getUnitPrice()
            );
            purchaseOrder.addLine(line);
        }
        PurchaseOrder saved = purchaseOrderRepository.save(purchaseOrder);
        return PurchaseOrderMapper.toInfo(saved);
    }

    @Transactional
    @Tool(description = "Send a purchase order. The order must exist and be in DRAFT status.")
    public void sendPurchaseOrder(
            @ToolParam(description = "Purchase order ID") Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(()->new IllegalArgumentException("Purchase Order not found"));
        purchaseOrder.send();
    }
    @Transactional
    @Tool(description = "Cancel a purchase order. The order must exist and be in DRAFT or SENT status.")
    public void cancelPurchaseOrder(
            @ToolParam(description = "Purchase order ID") Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(()->new IllegalArgumentException("Purchase Order not found"));
        purchaseOrder.cancel();
    }
}

