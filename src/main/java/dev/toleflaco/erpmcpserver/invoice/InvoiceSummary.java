package dev.toleflaco.erpmcpserver.invoice;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record InvoiceSummary(
        Long id,
        Long purchaseOrderId,
        BigDecimal amount,
        InvoiceStatus status,
        OffsetDateTime issuedAt
) {}
