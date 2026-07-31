package dev.toleflaco.erpmcpserver.purchaseorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {
    @Query("SELECT DISTINCT po FROM PurchaseOrder po " +
            "JOIN FETCH po.supplier " +
            "LEFT JOIN FETCH po.lines l " +
            "JOIN FETCH l.product " +
            "WHERE po.id = :id")
    Optional<PurchaseOrder> findByIdWithLinesAndProducts(@Param("id") Long id);

}
