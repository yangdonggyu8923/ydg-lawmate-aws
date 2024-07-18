package site.lawmate.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import site.lawmate.gateway.domain.dto.LoginDTO;
import site.lawmate.gateway.service.AuthService;

@Configuration
//@RestController
//@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
//
//    @PostMapping("/login/local")
//    public Mono<ServerResponse> login(@RequestBody LoginDTO dto) {
//        return authService.localLogin(dto);
//    }
//
//    @PostMapping("/refresh")
//    public Mono<ServerResponse> refresh(@RequestHeader(name = "Authorization") String refreshToken) {
//        return authService.refresh(refreshToken);
//    }
//
//    @PostMapping("/logout")
//    public Mono<ServerResponse> logout(@RequestHeader(name = "Authorization") String refreshToken) {
//        return authService.logout(refreshToken);
//    }
    @Bean
    RouterFunction<ServerResponse> authRoutes() {
        return RouterFunctions.route()
                .POST("/auth/login/local", req -> req.bodyToMono(LoginDTO.class).flatMap(authService::localLogin))
                .build();
    }
}