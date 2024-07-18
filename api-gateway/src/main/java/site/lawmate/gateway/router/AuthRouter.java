package site.lawmate.gateway.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import site.lawmate.gateway.domain.dto.LoginDTO;
import site.lawmate.gateway.filter.AuthFilter;
import site.lawmate.gateway.service.AuthService;

@Configuration
@RequiredArgsConstructor
public class AuthRouter {
    private final AuthFilter authFilter;

    @Bean
    RouterFunction<ServerResponse> authRoutes() {
        return RouterFunctions.route()
                .POST("/auth/login/local", req -> req.bodyToMono(LoginDTO.class).flatMap(authFilter::localLogin))
                .build();
    }
}