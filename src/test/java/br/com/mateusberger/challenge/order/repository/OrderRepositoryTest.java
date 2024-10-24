package br.com.mateusberger.challenge.order.repository;

import br.com.mateusberger.challenge.order.domain.Order;
import br.com.mateusberger.challenge.user.domain.RoleEnum;
import br.com.mateusberger.challenge.user.domain.User;
import br.com.mateusberger.challenge.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@DisplayName("Order repository integration test")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should create a order")
    void shouldCreateOrderIntoTheRepository(){

        var order = new Order(null, new ArrayList<>(), new User(1l, "fulano", "fulpass", RoleEnum.DEFAULT));

        Order savedOrder = orderRepository.save(order);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should read a order")
    void shouldReadOrderIntoTheRepository(){

        var savedUser = userRepository.save(new User(null, "fulano", "fulpass", RoleEnum.DEFAULT));

        var savedOrder = orderRepository.save(new Order(null, new ArrayList<>(), savedUser));

        List<Order> orders = orderRepository.findAllByUserId(savedUser.getId());

        Optional<Order> first = orders.stream().findFirst();

        assertThat(orders).isNotNull();
        assertThat(first).isPresent();
        assertThat(first.get().getId()).isEqualTo(savedOrder.getId());
    }
}