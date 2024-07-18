package site.lawmate.gateway.service.impl;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import site.lawmate.gateway.domain.dto.LoginDTO;
import site.lawmate.gateway.domain.model.PrincipalUserDetails;
import site.lawmate.gateway.service.AuthService;
import site.lawmate.gateway.service.provider.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final WebClient webClient;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<ServerResponse> localLogin(LoginDTO dto) {
        return Mono.just(dto)
                .log()
                .flatMap(i ->
                        webClient.post()
                                .uri("lb://user-service/auth/login/local")
                                .accept(MediaType.APPLICATION_JSON)
                                .bodyValue(i)
                                .retrieve()
                                .bodyToMono(PrincipalUserDetails.class)
                )
                .log()
                .flatMap(i ->
                        jwtTokenProvider.generateToken(i, false)
                                .log()
                                .flatMap(accessToken ->
                                        jwtTokenProvider.generateToken(i, true)
                                                .log()
                                                .flatMap(refreshToken ->
                                                        ServerResponse.ok()
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .cookie(
                                                                        ResponseCookie.from("accessToken")
                                                                                .value(accessToken)
                                                                                .maxAge(jwtTokenProvider.getAccessTokenExpired())
                                                                                .path("/")
                                                                                // .httpOnly(true)
                                                                                .build()
                                                                )
                                                                .cookie(
                                                                        ResponseCookie.from("refreshToken")
                                                                                .value(refreshToken)
                                                                                .maxAge(jwtTokenProvider.getRefreshTokenExpired())
                                                                                .path("/")
                                                                                // .httpOnly(true)
                                                                                .build()
                                                                )
                                                                .bodyValue(Boolean.TRUE)
                                                )
                                )
                )
                ;


    }

    @Override
    public Mono<ServerResponse> refresh(String refreshToken) {
        return Mono.just(refreshToken)
                .flatMap(i -> Mono.just(jwtTokenProvider.removeBearer(refreshToken)))
                .filter(i -> jwtTokenProvider.isTokenValid(refreshToken, true))
                .filterWhen(i -> jwtTokenProvider.isTokenInRedis(refreshToken))
                .flatMap(i -> Mono.just(jwtTokenProvider.extractPrincipalUserDetails(refreshToken)))
                .flatMap(i -> jwtTokenProvider.generateToken(i, false))
                .flatMap(accessToken ->
                        ServerResponse.ok()
                                .cookie(
                                        ResponseCookie.from("accessToken")
                                                .value(accessToken)
                                                .maxAge(jwtTokenProvider.getAccessTokenExpired())
                                                .path("/")
                                                // .httpOnly(true)
                                                .build()
                                )
                                .build()
                );
    }

    @Override
    public Mono<ServerResponse> logout(String refreshToken) {
        return Mono.just(refreshToken)
                .flatMap(i -> Mono.just(jwtTokenProvider.removeBearer(refreshToken)))
                .filter(i -> jwtTokenProvider.isTokenValid(refreshToken, true))
                .filterWhen(i -> jwtTokenProvider.isTokenInRedis(refreshToken))
                .filterWhen(i -> jwtTokenProvider.removeTokenInRedis(refreshToken))
                .flatMap(i -> ServerResponse.ok().build());
    }
}
