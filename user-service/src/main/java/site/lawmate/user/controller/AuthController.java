package site.lawmate.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import site.lawmate.user.domain.dto.LoginDTO;
import site.lawmate.user.domain.model.PrincipalUserDetails;
import site.lawmate.user.domain.model.UserModel;
import site.lawmate.user.domain.vo.Registration;
import site.lawmate.user.domain.vo.Role;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/login/local")
    public Mono<PrincipalUserDetails> login(@RequestBody LoginDTO dto) {
        log.info("login: {}", dto);
        return Mono.just(new PrincipalUserDetails(UserModel.builder()
                .id("aaa1234")
                .name("test")
                .email(dto.getEmail())
                .profile("testProfile.jpg")
                .roles(List.of(Role.ROLE_USER))
                .registration(Registration.LOCAL)
                .build(), null));
    }
}