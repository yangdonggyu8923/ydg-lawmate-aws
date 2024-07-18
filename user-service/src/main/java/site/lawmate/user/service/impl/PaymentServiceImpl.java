package site.lawmate.user.service.impl;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.lawmate.user.component.Messenger;
import site.lawmate.user.domain.model.PaymentCallbackRequest;
import site.lawmate.user.domain.dto.PaymentDto;
import site.lawmate.user.repository.PaymentRepository;
import site.lawmate.user.service.PaymentService;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository payRepository;
    private final IamportClient iamportClient;

    @Transactional
    @Override
    public Messenger save(PaymentDto dto) {
        log.info("Parameters received through payment service: " + dto);

        site.lawmate.user.domain.model.Payment payment = dtoToEntity(dto);
        site.lawmate.user.domain.model.Payment savedPayment = payRepository.save(payment);
        boolean exists = payRepository.existsById(savedPayment.getId());
        return Messenger.builder()
                .message(exists ? "SUCCESS" : "FAILURE")
                .build();
    }

    @Transactional
    @Override
    public Messenger delete(Long id) {
        return null;
    }

    @Override
    public List<PaymentDto> findAll() {
        return null;
    }

    @Override
    public Optional<PaymentDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Messenger count() {
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return payRepository.existsById(id);
    }

    @Transactional
    @Override
    public Messenger update(PaymentDto dto) {
        return null;
    }

    @Override
    public PaymentDto findRequestDto(String orderUid) {
        return null;
    }

    @Override
    public IamportResponse<Payment> paymentByCallback(PaymentCallbackRequest request) {
        return null;
    }

}
