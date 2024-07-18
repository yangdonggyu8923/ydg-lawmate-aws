//package site.lawmate.user.component;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import site.lawmate.user.domain.dto.UserDto;
//import site.lawmate.user.repository.UserRepository;
//
//import javax.crypto.SecretKey;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Base64;
//import java.util.Date;
//
//@Log4j2
//@Component
//public class JwtProvider {
//    @Value("${jwt.iss}")
//    private String issuer;
//    private final SecretKey secretKey;
//    private final UserRepository repository;
//    Instant expiredDate = Instant.now().plus(1, ChronoUnit.DAYS);
//
//    public JwtProvider(UserRepository repository, @Value("${jwt.secret}") String secretKey) {
//        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
//        this.repository = repository;
//    }
//
//    public String createToken(UserDto dto) {
//        String token = Jwts.builder()
//                .issuer(issuer)
//                .signWith(secretKey)
//                .expiration(Date.from(expiredDate))
//                .subject("user")
//                .claim("id", dto.getId())
//                .claim("email", dto.getEmail())
//                .compact();
//        log.info("Generated token after login : " + token);
//        return token;
//    }
//
//    public String extractTokenFromHeader(HttpServletRequest request) {
//        log.info("Request getServletPath value from frontend : {}", request.getServletPath());
//        String bearerToken = request.getHeader("Authorization");
//        log.info("Token from frontend : {}", bearerToken);
//        return bearerToken != null && bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : "undefined";
//    }
//
//    public void printPayload(String accessToken) {
//        //토큰을 각 세션(Header, Payload, Signature)으로 분할
//        String[] chunks = accessToken.split("\\.");
//        Base64.Decoder decoder = Base64.getDecoder();
//
//        String header = new String(decoder.decode(chunks[0]));
//        String payload = new String(decoder.decode(chunks[1]));
//
//        log.info("JwtProvider Access Token header: " + header);
//        log.info("JwtProvider Access Token payload: " + payload);
//
//        //return new StringBuilder().append(header).append(payload).toString();
//    }
//
//    public Claims getPayload(String token) {
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
//        //secretKey는 provider에서만 있어야 함
//    }
//
//}
