package br.com.mateusberger.challenge.order.domain;

import br.com.mateusberger.challenge.order.domain.Item;
import br.com.mateusberger.challenge.order.domain.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.TWO;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Order entity unity testes")
class OrderTest {

    @Test
    @DisplayName("Should calculate total order value successfully")
    void shouldCalculateTotalOrderValueSuccessfully() {

        var itemsList = List.of(
                new Item("Item cod 142351", 15l, TEN),
                new Item("Item cod 674584", 10l, TWO),
                new Item("Item cod 967348", 5l, ONE),
                new Item("Item cod 123537", 3l, BigDecimal.valueOf(0.5))
        );

        var order = new Order(1l, itemsList, null);

        assertThat(order.getTotalValue()).isEqualByComparingTo(BigDecimal.valueOf(176.5));
    }

    @Test
    @DisplayName("Should return total value as zero when list of item is null")
    void shouldReturnTotalvalueAsZero_whenListOfItemIsNull() {

        var order = new Order(1l, null, null);

        assertThat(order.getTotalValue()).isZero();
    }

    @Test
    @DisplayName("Should return total value as zero when item has quantity zero or negative")
    void shouldReturnTotalvalueAsZero_whenItemHasQuantityZeroOrNegative() {

        var itemList = List.of(new Item("Item cod 142351", 0l, TEN),
                new Item("Item cod 674584", -10l, TEN));

        var order = new Order(1l, itemList, null);

        assertThat(order.getTotalValue()).isZero();
    }

    @Test
    @DisplayName("Should return total value as zero when item has unity value zero or negative")
    void shouldReturnTotalvalueAsZero_whenItemHasUnityvalueZeroOrNegative() {

        var itemList = List.of(new Item("Item cod 142351", 10l, TEN.negate()),
                new Item("Item cod 674584", 10l, ZERO));

        var order = new Order(1l, itemList, null);

        assertThat(order.getTotalValue()).isZero();
    }

}