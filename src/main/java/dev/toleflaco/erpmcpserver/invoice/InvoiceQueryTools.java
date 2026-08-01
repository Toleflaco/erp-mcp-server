package dev.toleflaco.erpmcpserver.invoice;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvoiceQueryTools {

    private final InvoiceRepository invoiceRepository;

    public InvoiceQueryTools(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional(readOnly = true)
    @Tool(description = "List all invoices in a given status. Returns the invoice ID, purchase order ID, amount, invoice status and issued date.")
    public List<InvoiceSummary> listInvoicesByStatus(
            @ToolParam(description = "One of: PENDING, PAID.") InvoiceStatus status) {

        return invoiceRepository.findByStatusOrderByIssuedAtDesc(status)
                .stream().map(this::toSummary)
                .toList();
    }

    private InvoiceSummary toSummary(Invoice invoice) {
        return new InvoiceSummary(
                invoice.getId(),
                invoice.getPurchaseOrder().getId(),
                invoice.getAmount(),
                invoice.getStatus(),
                invoice.getIssuedAt()
        );
    }
}

