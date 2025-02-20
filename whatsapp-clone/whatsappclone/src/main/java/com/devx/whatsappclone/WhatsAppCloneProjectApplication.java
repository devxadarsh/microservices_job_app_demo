package com.devx.whatsappclone;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@SecurityScheme( // It is for the swagger and can be done using a separate config file
		name = "keycloak",
		type = SecuritySchemeType.OAUTH2,
		bearerFormat = "JWT",
		scheme = "bearer",
		in = SecuritySchemeIn.HEADER,
		flows = @OAuthFlows(
				password = @OAuthFlow(
						authorizationUrl = "http://localhost:9090/realms/whatsapp-clone/protocol/openid-connect/auth",
						tokenUrl = "http://localhost:9090/realms/whatsapp-clone/protocol/openid-connect/token",
						refreshUrl = "http://localhost:9090/realms/whatsapp-clone/protocol/openid-connect/token"
				)
)
)
public class WhatsAppCloneProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatsAppCloneProjectApplication.class, args);
	}

}
