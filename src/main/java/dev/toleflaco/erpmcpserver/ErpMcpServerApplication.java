package dev.toleflaco.erpmcpserver;

import dev.toleflaco.erpmcpserver.invoice.InvoiceQueryTools;
import dev.toleflaco.erpmcpserver.product.ProductQueryTools;
import dev.toleflaco.erpmcpserver.product.ProductWriteTools;
import dev.toleflaco.erpmcpserver.purchaseorder.PurchaseOrderQueryTools;
import dev.toleflaco.erpmcpserver.purchaseorder.PurchaseOrderWriteTools;
import dev.toleflaco.erpmcpserver.supplier.SupplierQueryTools;
import dev.toleflaco.erpmcpserver.supplier.SupplierWriteTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ErpMcpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpMcpServerApplication.class, args);
    }


    @Bean
    ToolCallbackProvider erpTools(
            ProductQueryTools productQueryTools,
            SupplierQueryTools supplierQueryTools,
            PurchaseOrderQueryTools purchaseOrderQueryTools,
            InvoiceQueryTools invoiceQueryTools,
            SupplierWriteTools supplierWriteTools,
            ProductWriteTools productWriteTools,
            PurchaseOrderWriteTools purchaseOrderWriteTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(
                        productQueryTools,
                        supplierQueryTools,
                        purchaseOrderQueryTools,
                        invoiceQueryTools,
                        supplierWriteTools,
                        productWriteTools,
                        purchaseOrderWriteTools)
                .build();
    }

}
