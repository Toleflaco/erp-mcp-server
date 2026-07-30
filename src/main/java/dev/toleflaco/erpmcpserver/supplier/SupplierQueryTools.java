package dev.toleflaco.erpmcpserver.supplier;

import dev.toleflaco.erpmcpserver.product.Product;
import dev.toleflaco.erpmcpserver.product.ProductRepository;
import dev.toleflaco.erpmcpserver.product.ProductSummary;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierQueryTools {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;


    public SupplierQueryTools(ProductRepository productRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    @Transactional(readOnly = true)
    @Tool(description = "Get detailed information about a supplier by its ID. " +
            "Returns supplier id, supplier name and all its products (id and name)")
    public SupplierInfo getSupplier(Long id) {

        return supplierRepository.findById(id)
                .map(s -> {
                    List<Product> products = productRepository.findBySupplierId(id);
                    List<ProductSummary> summaries = products.stream()
                            .map(p -> new ProductSummary(p.getId(), p.getName()))
                            .toList();
                    return toDto(s, summaries);
                })
                .orElse(null);
    }

    private SupplierInfo toDto(Supplier supplier, List<ProductSummary> products) {

        return new SupplierInfo(
                supplier.getId(),
                supplier.getName(),
                products
        );
    }


}
