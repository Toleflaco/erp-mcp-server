package dev.toleflaco.erpmcpserver;

import dev.toleflaco.erpmcpserver.product.ProductQueryTools;
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
    ToolCallbackProvider erpTools(ProductQueryTools productQueryTools) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(productQueryTools)
				.build();
	}

}
