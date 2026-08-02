package dev.toleflaco.erpmcpserver.product;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductWriteTools {

    private final ProductRepository productRepository;


    public ProductWriteTools(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @Tool(description = "Updates the stock of a product, given its product Id.")
    public void updateProductStock(
            @ToolParam(description = "The identifier of the product whose stock will be updated.") Long productId,
            @ToolParam(description = "The new stock quantity for the product. Must be zero or positive.") int newStock) {

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setStock(newStock);
    }
}
