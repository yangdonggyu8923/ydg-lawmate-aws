package site.lawmate.user.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.lawmate.user.component.Messenger;
import site.lawmate.user.enums.PaymentStatus;
import site.lawmate.user.domain.dto.PaymentDto;
import site.lawmate.user.service.PaymentService;

import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/payment")
@Slf4j
@Controller
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
})
public class PaymentController {
    private final PaymentService paymentService;

    @Value("${iamport.key}")
    private String restApiKey;
    @Value("${iamport.secret}")
    private String restApiSecret;

    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(restApiKey, restApiSecret);
    }

    @PostMapping("/save")
    public ResponseEntity<Messenger> savePayment(@RequestBody PaymentDto dto) {
        log.info("Parameters received through controller" + dto);
        return ResponseEntity.ok(paymentService.save(dto));
    }

    @PostMapping("/status")
    public ResponseEntity<String> paymentStatus(@RequestBody PaymentStatus status) {
        log.info("Parameters received through controller" + status);
        if (status == PaymentStatus.OK) {
            // 결제 성공 시 처리할 로직 작성
            return new ResponseEntity<>("Payment SUCCESS", HttpStatus.OK);
        } else {
            // 결제 실패 시 처리할 로직 작성
            return new ResponseEntity<>("Payment FAILURE", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verifyIamport/{imp_uid}")
    public ResponseEntity<?> paymentByImpUid(@PathVariable("imp_uid") String imp_uid) throws IamportResponseException, IOException {
        log.info("imp_uid={}", imp_uid);
        IamportResponse<Payment> response = iamportClient.paymentByImpUid(imp_uid);
        return ResponseEntity.ok(response);
    }

}
