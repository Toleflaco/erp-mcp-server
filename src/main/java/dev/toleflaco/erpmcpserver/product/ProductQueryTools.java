package dev.toleflaco.erpmcpserver.product;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductQueryTools {

    private final ProductRepository productRepository;

    public ProductQueryTools(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    @Tool(description = "Get detailed information about a product by its ID. " +
            "Returns product name, category, unit price, current stock, " +
            "minimum stock threshold, and supplier name.")
    public ProductInfo getProduct(Long id) {

        return productRepository.findById(id)
                .map(product -> new ProductInfo(
                        product.getId(),
                        product.getName(),
                        product.getCategory(),
                        product.getUnitPrice(),
                        product.getStock(),
                        product.getMinStock(),
                        product.getSupplier().getName()
                ))
                .orElse(null);
    }
}
