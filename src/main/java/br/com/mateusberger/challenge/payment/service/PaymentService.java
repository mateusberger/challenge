package br.com.mateusberger.challenge.payment.service;

import br.com.mateusberger.challenge.commons.exceptions.NoContentFoundException;
import br.com.mateusberger.challenge.commons.exceptions.PaymentAlreadyProcessedException;
import br.com.mateusberger.challenge.commons.exceptions.UnauthorizedUserException;
import br.com.mateusberger.challenge.order.service.OrderService;
import br.com.mateusberger.challenge.payment.domain.Payment;
import br.com.mateusberger.challenge.payment.dto.PaymentDTO;
import br.com.mateusberger.challenge.payment.repository.PaymentRepository;
import br.com.mateusberger.challenge.user.domain.UserPublicInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.valueOf;

@Service
@Log
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    private final OrderService orderService;

    private final ModelMapper modelMapper;

    public PaymentDTO processPayment(UserPublicInformation user, Long orderId){

        var payment = getPreviuosPaymentOrCreateANew(orderId);

        validatePayment(user, payment);

        processPayment(user, payment);

        setAsPaid(payment);

        var savedPayment = repository.save(payment);

        return modelMapper.map(savedPayment, PaymentDTO.class);
    }

    private static void setAsPaid(Payment payment) {
        payment.setIsPaid(true);

        payment.setValuePaid(payment.getOrder().getTotalValue());
    }

    private void processPayment(UserPublicInformation user, Payment payment){
        log.warning("Processing payment of user %s and order %s".formatted(user.getId(), payment.getOrder().getId()));
        //payment logic here
    }

    private void validatePayment(UserPublicInformation user, Payment payment) {

        if (!Objects.equals(payment.getOrder().getUser().getId(), user.getId())) throw new UnauthorizedUserException();

        if (TRUE.equals(payment.getIsPaid())) throw new PaymentAlreadyProcessedException();

    }

    private Payment getPreviuosPaymentOrCreateANew(Long orderId){

        Optional<Payment> previousPayment = repository.findByOrderId(orderId);

        return previousPayment.orElseGet(() -> repository.save(
                new Payment(
                        null,
                        orderService.getEntityOrderById(orderId),
                        false,
                        null
                )
        ));

    }

    public PaymentDTO getPayment(UserPublicInformation user, Long paymentId){

        var payment = repository.findById(paymentId).orElseThrow(NoContentFoundException::new);

        if (!Objects.equals(payment.getOrder().getUser().getId(), user.getId())) throw new UnauthorizedUserException();

        return modelMapper.map(payment, PaymentDTO.class);
    }

}
