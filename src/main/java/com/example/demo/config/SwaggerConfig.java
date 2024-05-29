

package com.example.demo.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
		info = @Info(
				title = "Demo API",
				version = "1.0",
				description = "Demo API",
				contact = @Contact(
					name = "Demo",
					email = "contact@demo.com",
					url = "http://demo.com"
					),
				license = @License(
					name = "License of API",
					url = "http://demo.com/license"
					),
				// ),
				// license = @License(
				// 	name = "License of API",
				// 	url = "http://demo.com/license"
				// 	),
				termsOfService = "Terms of service"
		),
		servers = {
			@Server(
				description = "Local Environment",
				url = "http://localhost:8080"
			),
			
		}
)

public class SwaggerConfig {
}