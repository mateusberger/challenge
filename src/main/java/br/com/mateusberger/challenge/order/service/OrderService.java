package br.com.mateusberger.challenge.order.service;

import br.com.mateusberger.challenge.commons.exceptions.NoContentFoundException;
import br.com.mateusberger.challenge.order.domain.Item;
import br.com.mateusberger.challenge.order.domain.Order;
import br.com.mateusberger.challenge.order.dto.CreateNewOrderDTO;
import br.com.mateusberger.challenge.order.dto.OrderDTO;
import br.com.mateusberger.challenge.order.repository.OrderRepository;
import br.com.mateusberger.challenge.user.domain.User;
import br.com.mateusberger.challenge.user.domain.UserPublicInformation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    private final ModelMapper modelMapper;

    public OrderDTO createNewOrder(UserPublicInformation user, CreateNewOrderDTO request){

        var items = request.getItems().stream()
                .map(itemDTO -> modelMapper.map(itemDTO, Item.class))
                .toList();

        var mappedOrder = new Order(null, items, new User(user.getId(), user.getUsername(), null, user.getRole()));

        var savedOrder = repository.save(mappedOrder);

        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    public List<OrderDTO> listOrdersFromAuthenticatedUser(UserPublicInformation user){

        List<Order> orders = repository.findAllByUserId(user.getId());

        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();
    }

    public OrderDTO getOrderById(Long orderId){

        var order = repository.findById(orderId)
                .orElseThrow(NoContentFoundException::new);

        return modelMapper.map(order, OrderDTO.class);
    }

    public Order getEntityOrderById(Long orderId){

        return repository.findById(orderId).orElseThrow(NoContentFoundException::new);
    }
}