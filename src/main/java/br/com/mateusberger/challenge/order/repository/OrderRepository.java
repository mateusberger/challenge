package br.com.mateusberger.challenge.order.repository;

import br.com.mateusberger.challenge.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    Order save(Order order);

    List<Order> findAllByUserId(Long userId);
}
