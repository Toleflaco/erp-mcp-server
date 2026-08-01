package dev.toleflaco.erpmcpserver.supplier;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
public class SupplierWriteTools {

    private final SupplierRepository supplierRepository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    public SupplierWriteTools(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    @Tool(description = "Create a new supplier using a valid name and email address.")
    public Long createSupplier(
            @ToolParam(description = "The name cannot be empty or contain only whitespace.") String name,
            @ToolParam(description ="The email cannot be empty and must be a valid email address.") String contactEmail) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be blank");
        }
        if (contactEmail == null || contactEmail.isBlank()) {
            throw new IllegalArgumentException("contactEmail cannot be blank");
        }
        if (!EMAIL_PATTERN.matcher(contactEmail).matches()) {
            throw new IllegalArgumentException("contactEmail must be a valid email address");
        }
        Supplier supplier = new Supplier(name, contactEmail);
        return supplierRepository.save(supplier).getId();
    }
}
