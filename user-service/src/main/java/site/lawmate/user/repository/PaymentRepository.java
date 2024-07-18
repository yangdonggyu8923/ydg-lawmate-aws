package site.lawmate.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.lawmate.user.domain.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}