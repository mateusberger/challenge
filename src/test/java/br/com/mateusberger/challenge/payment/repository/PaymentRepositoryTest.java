package br.com.mateusberger.challenge.payment.repository;

import br.com.mateusberger.challenge.order.domain.Order;
import br.com.mateusberger.challenge.order.repository.OrderRepository;
import br.com.mateusberger.challenge.payment.domain.Payment;
import br.com.mateusberger.challenge.user.domain.RoleEnum;
import br.com.mateusberger.challenge.user.domain.User;
import br.com.mateusberger.challenge.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@DisplayName("Payment repository integration test")
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should create a payment")
    void shouldCreatePaymentIntoTheRepository(){

        var user = userRepository.save(new User(null, "fulano", "fulpass", RoleEnum.DEFAULT));
        var order = orderRepository.save(new Order(null, new ArrayList<>(), user));
        var payment = new Payment(null, order, true, TEN);

        Payment savedPayment = paymentRepository.save(payment);

        assertThat(savedPayment).isNotNull();
        assertThat(savedPayment.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should read a payment")
    void shouldReadPaymentIntoTheRepository(){

        var savedUser = userRepository.save(new User(null, "fulano", "fulpass", RoleEnum.DEFAULT));
        var savedOrder = orderRepository.save(new Order(null, new ArrayList<>(), savedUser));
        var savedPayment = paymentRepository.save(new Payment(null, savedOrder, true, TEN));

        var payment = paymentRepository.findById(savedPayment.getId());

        assertThat(payment).isNotNull();
        assertThat(payment).isPresent();
        assertThat(payment.get().getId()).isEqualTo(savedPayment.getId());
    }

}