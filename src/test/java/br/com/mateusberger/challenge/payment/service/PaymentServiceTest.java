package br.com.mateusberger.challenge.payment.service;

import br.com.mateusberger.challenge.commons.exceptions.NoContentFoundException;
import br.com.mateusberger.challenge.commons.exceptions.PaymentAlreadyProcessedException;
import br.com.mateusberger.challenge.commons.exceptions.UnauthorizedUserException;
import br.com.mateusberger.challenge.order.domain.Item;
import br.com.mateusberger.challenge.order.domain.Order;
import br.com.mateusberger.challenge.order.service.OrderService;
import br.com.mateusberger.challenge.payment.domain.Payment;
import br.com.mateusberger.challenge.payment.dto.PaymentDTO;
import br.com.mateusberger.challenge.payment.repository.PaymentRepository;
import br.com.mateusberger.challenge.user.domain.RoleEnum;
import br.com.mateusberger.challenge.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Payment service unity test")
class PaymentServiceTest {

    private PaymentService paymentService;

    private OrderService orderService;

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setup() {

        paymentRepository = mock(PaymentRepository.class);

        orderService = mock(OrderService.class);

        paymentService = new PaymentService(
                paymentRepository,
                orderService,
                new ModelMapper().registerModule(new RecordModule())
        );

    }

    @Test
    @DisplayName("Should process payment successfully when no previous attempt was made")
    void shouldProcessPaymentSuccessfully_whenNoPreviousAttemptWasMade() {

        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer(invocationOnMock -> {

                    var argument = (Payment) invocationOnMock.getArgument(0);
                    System.out.println(argument);
                    argument.setOrder(getAOrder(4L));
                    argument.setId(1L);
                    return argument;

                });

        when(paymentRepository.findByOrderId(4L))
                .thenReturn(Optional.empty());

        when(orderService.getEntityOrderById(anyLong()))
                .thenReturn(getAOrder(4L));

        PaymentDTO paymentDTO = paymentService.processPayment(getAUser(1L), 1L);

        assertThat(paymentDTO).isNotNull();
        assertThat(paymentDTO.getId()).isEqualTo(1L);
        assertThat(paymentDTO.getOrderId()).isEqualTo(4L);
        assertThat(paymentDTO.getIsPaid()).isTrue();
        assertThat(paymentDTO.getPaidValue()).isEqualByComparingTo("20");
    }

    @Test
    @DisplayName("Should process payment successfully when a previous attempt was made")
    void shouldProcessPaymentSuccessfully_whenAPreviousAttemptWasMade() {

        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer(invocationOnMock -> {

                    var argument = (Payment) invocationOnMock.getArgument(0);
                    System.out.println(argument);
                    argument.setOrder(getAOrder(4L));
                    argument.setId(1L);
                    return argument;

                });

        when(paymentRepository.findByOrderId(anyLong()))
                .thenReturn(Optional.of(getAPayment(false, null)));

        PaymentDTO paymentDTO = paymentService.processPayment(getAUser(1L), 1L);

        assertThat(paymentDTO).isNotNull();
        assertThat(paymentDTO.getId()).isEqualTo(1L);
        assertThat(paymentDTO.getOrderId()).isEqualTo(4L);
        assertThat(paymentDTO.getIsPaid()).isTrue();
        assertThat(paymentDTO.getPaidValue()).isEqualByComparingTo("20");
    }



    @Test
    @DisplayName("Should throw unauthorized user exception when user of order is not the same")
    void shouldThrowUnauthorizedUserException_whenUserOfOrderIsNotTheSame(){

        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer(invocationOnMock -> {

                    var argument = (Payment) invocationOnMock.getArgument(0);
                    System.out.println(argument);
                    argument.setOrder(getAOrder(4L));
                    argument.setId(1L);
                    return argument;

                });

        when(paymentRepository.findByOrderId(anyLong()))
                .thenReturn(Optional.of(getAPayment(false, null)));

        when(orderService.getEntityOrderById(anyLong()))
                .thenReturn(getAOrder(4L));

        var user = getAUser(2L);

        assertThrows(UnauthorizedUserException.class, () -> paymentService.processPayment(user, 4L));
    }

    @Test
    @DisplayName("Should throw payment already processed exception when trying to pay again")
    void shouldThrowPaymentAlreadyProcessedException_whenTryingToPayAgain(){

        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer(invocationOnMock -> {

                    var argument = (Payment) invocationOnMock.getArgument(0);
                    System.out.println(argument);
                    argument.setOrder(getAOrder(4L));
                    argument.setId(1L);
                    return argument;

                });

        when(paymentRepository.findByOrderId(anyLong()))
                .thenReturn(Optional.of(getAPayment(true, null)));

        when(orderService.getEntityOrderById(anyLong()))
                .thenReturn(getAOrder(4L));

        var user = getAUser(1L);

        assertThrows(PaymentAlreadyProcessedException.class, () -> paymentService.processPayment(user, 4L));
    }

    @Test
    @DisplayName("Should retrieve payment by id successfully")
    void shouldRetrievePaymentByIdSuccessfully() {

        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(getAPayment(true, BigDecimal.valueOf(20L))));

        var payment = paymentService.getPayment(getAUser(1L), 1L);

        assertThat(payment).isNotNull();
        assertThat(payment.getIsPaid()).isTrue();
        assertThat(payment.getId()).isEqualTo(1L);
        assertThat(payment.getOrderId()).isEqualTo(4L);
        assertThat(payment.getPaidValue()).isEqualByComparingTo("20");

        verify(paymentRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw no content retrieved exception when no payment is find")
    void shouldThrowNoContentRetrievedException_whenNoPaymentIsFind() {

        when(paymentRepository.findById(1L))
                .thenReturn(Optional.empty());

        var user = getAUser(1L);

        assertThrows(NoContentFoundException.class, () ->  paymentService.getPayment(user, 1L));

        verify(paymentRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw unauthorized user exception when getting payment of a user that is not the same")
    void shouldThrowUnauthorizedUserException_whenGettingPaymentOfAUserThatIsNotTheSame() {

        when(paymentRepository.findById(1L))
                .thenReturn(Optional.empty());

        var user = getAUser(2L);

        assertThrows(NoContentFoundException.class, () ->  paymentService.getPayment(user, 1L));

        verify(paymentRepository).findById(1L);
    }

    private Payment getAPayment(boolean isPaid, BigDecimal valuePaid) {
        return new Payment(1L, getAOrder(4L), isPaid, valuePaid);
    }

    private static Order getAOrder(Long id) {
        return new Order(id, List.of(
                new Item("Item 1", 2L, BigDecimal.TEN)
        ), getAUser(1L));
    }

    private static User getAUser(Long userId) {
        return new User(userId, "username", "password", RoleEnum.DEFAULT);
    }
}