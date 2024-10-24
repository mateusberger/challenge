package br.com.mateusberger.challenge.order.service;

import br.com.mateusberger.challenge.commons.exceptions.NoContentFoundException;
import br.com.mateusberger.challenge.order.domain.Item;
import br.com.mateusberger.challenge.order.domain.Order;
import br.com.mateusberger.challenge.order.dto.CreateNewOrderDTO;
import br.com.mateusberger.challenge.order.dto.ItemDTO;
import br.com.mateusberger.challenge.order.dto.OrderDTO;
import br.com.mateusberger.challenge.order.repository.OrderRepository;
import br.com.mateusberger.challenge.user.domain.RoleEnum;
import br.com.mateusberger.challenge.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DisplayName("Order service unity test")
class OrderServiceTest {

    private OrderService orderService;

    private OrderRepository orderRepository;

    @BeforeEach
    void setup(){

        var modelMapper = new ModelMapper().registerModule(new RecordModule());

        orderRepository = Mockito.mock(OrderRepository.class);

        orderService = new OrderService(orderRepository, modelMapper);
    }

    @Test
    @DisplayName("Should create a new order successfully")
    void shouldCreateANewOrderSuccessfully(){

        doReturn(getAOrder(1L)).when(orderRepository).save(any(Order.class));

        List<ItemDTO> items = List.of(
                new ItemDTO("Item 1", 1L, BigDecimal.TEN),
                new ItemDTO("Item 2", 2L, BigDecimal.TWO),
                new ItemDTO("Item 3", 3L, BigDecimal.TWO)
        );

        CreateNewOrderDTO createNewOrderDTO = new CreateNewOrderDTO(items);

        OrderDTO response = orderService.createNewOrder(getOneUser(), createNewOrderDTO);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getItems()).hasSize(3);
        assertThat(response.getTotalValue()).isEqualByComparingTo("20");

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Should retrieve order by ID successfully")
    void shouldRetrieveOrderByIdSuccessfully(){

        doReturn(Optional.of(getAOrder(1L)))
                .when(orderRepository).findById(1L);

        OrderDTO response = orderService.getOrderById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getItems()).hasSize(3);
        assertThat(response.getTotalValue()).isEqualByComparingTo("20");

        verify(orderRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when no order is find")
    void shouldThrowsExceptionWhenNoOrderIsFind(){

        doReturn(Optional.empty())
                .when(orderRepository).findById(1L);

        assertThrows(NoContentFoundException.class, () -> {
            orderService.getOrderById(1L);
        });

        verify(orderRepository).findById(1L);
    }
    
    @Test
    @DisplayName("Should list all orders from authenticated user successfully")
    void shouldListAllOrdersFromAuthenticatedUserSuccessfully(){

        var orders = List.of( getAOrder(1L), getAOrder(2L), getAOrder(3L) );

        var user = getOneUser();

        doReturn(orders)
                .when(orderRepository).findAllByUserId(user.getId());

        var response = orderService.listOrdersFromAuthenticatedUser(user);

        assertThat(response).isNotNull();
        assertThat(response).hasSize(3);

        verify(orderRepository).findAllByUserId(user.getId());
    }

    @Test
    @DisplayName("Should return empty list of orders from authenticated user when has no orders")
    void shouldReturnEmptyListOfOrdersFromAuthenticatedUserWhenHasNoOrders(){

        List<Order> orders = emptyList();

        var user = getOneUser();

        doReturn(orders)
                .when(orderRepository).findAllByUserId(user.getId());

        var response = orderService.listOrdersFromAuthenticatedUser(user);

        assertThat(response).isNotNull();
        assertThat(response).isEmpty();

        verify(orderRepository).findAllByUserId(user.getId());
    }

    @Test
    @DisplayName("Should return entity order successfully")
    void shouldReturnEntityOrderSuccessfully(){
        doReturn(Optional.of(getAOrder(1L)))
                .when(orderRepository).findById(1L);

        var response = orderService.getEntityOrderById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getItems()).hasSize(3);
        assertThat(response.getTotalValue()).isEqualByComparingTo("20");
        verify(orderRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw no content retrieved exception when order entity not found")
    void shouldThrowNoContentRetrievedException_whenOrderEntityNotFound(){
        doReturn(Optional.empty())
                .when(orderRepository).findById(1L);

        assertThrows(NoContentFoundException.class, () -> {
            orderService.getEntityOrderById(1L);
        });

        verify(orderRepository).findById(1L);
    }

    private static Order getAOrder(Long id) {
        return new Order(id, List.of(
                new Item("Item 1", 1L, BigDecimal.TEN),
                new Item("Item 2", 2L, BigDecimal.TWO),
                new Item("Item 3", 3L, BigDecimal.TWO)
        ), getOneUser());
    }

    private static User getOneUser() {
        return new User(1L, "username", "password", RoleEnum.DEFAULT);
    }
}