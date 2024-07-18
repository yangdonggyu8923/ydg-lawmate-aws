package site.lawmate.user.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import site.lawmate.user.enums.PaymentStatus;

@Entity(name = "payments")
@Getter
@NoArgsConstructor
@Component
@Setter
public class Payment extends BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentUid; //결제 고유 번호
    private PaymentStatus status;

    @Builder
    public Payment(Long id, String paymentUid, PaymentStatus status, User buyer, Product product) {
        this.id = id;
        this.paymentUid = paymentUid;
        this.status = status;
        this.buyer = buyer;
        this.product = product;
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;
}
