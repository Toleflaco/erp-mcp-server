package dev.toleflaco.erpmcpserver.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p WHERE p.stock <= p.minStock")
    List<Product> findLowStockProducts();

    List<Product> findByCategory(String category);
    List<Product> findBySupplierId(Long supplierId);
}
