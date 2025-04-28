package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.MemberRepository;
import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.CoffeeListDto;
import io.sleepyhoon.project1.dto.OrderRequestDto;
import io.sleepyhoon.project1.dto.OrderResponseDto;
import io.sleepyhoon.project1.entity.*;
import io.sleepyhoon.project1.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderServiceTests {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CoffeeOrderService coffeeOrderService;

    @Mock
    private ApplicationEventPublisher publisher;

    @Mock
    private MemberRepository memberRepository;

    @Captor
    private ArgumentCaptor<OrderCreatedEvent> eventCaptor;

    @Test
    @DisplayName("email에 해당하는 주문 리스트 읽기")
    void readAllOrders() throws Exception {
        // given
        Member member1 = genMember("ex@example.com", "ex", "ex");
        Member member2 = genMember("test@test.com", "test", "test");
        Member member3 = genMember("user@user.com", "user", "user");

        CoffeeImg coffeeImg = genCoffeeImg("1", "url", null);
        List<CoffeeImg> coffeeImages = genImages(coffeeImg, coffeeImg, coffeeImg);

        Coffee americano = genCoffee("아메리카노", 1500, coffeeImages, 100);
        Coffee cappuccino = genCoffee("카푸치노", 3000, coffeeImages, 100);

        Order order1 = genOrder(member1, "경기도", "12345", 4500);
        Order order2 = genOrder(member2, "서울", "12242", 9000);
        Order order3 = genOrder(member3, "부산", "14442", 13500);

        setId(order1, 1L);
        setId(order2, 2L);
        setId(order3, 3L);

        List<CoffeeOrder> coffeeOrders1 = genCoffeeOrderList(
                genCoffeeOrder(americano, order1, 1),
                genCoffeeOrder(cappuccino, order1, 1)
        );
        List<CoffeeOrder> coffeeOrders2 = genCoffeeOrderList(
                genCoffeeOrder(americano, order2, 2),
                genCoffeeOrder(cappuccino, order2, 2)
        );
        List<CoffeeOrder> coffeeOrders3 = genCoffeeOrderList(
                genCoffeeOrder(americano, order3, 3),
                genCoffeeOrder(cappuccino, order3, 3)
        );

        order1.setCoffeeOrders(coffeeOrders1);
        order2.setCoffeeOrders(coffeeOrders2);
        order3.setCoffeeOrders(coffeeOrders3);

        List<Order> orderList = List.of(order1, order3);

        when(orderRepository.findByMember_Email("ex@example.com")).thenReturn(orderList);

        // when
        List<OrderResponseDto> orders = orderService.findAllOrdersByEmail("ex@example.com");

        // then
        assertThat(orders.size()).isEqualTo(2);
        assertThat(orders.get(0).getId()).isEqualTo(order1.getId());
        assertThat(orders.get(1).getPrice()).isEqualTo(order3.getPrice());
        assertThat(order1.getPrice()).isEqualTo(getPrice(coffeeOrders1));
        assertThat(orders.get(1).getAddress()).isNotEqualTo(order1.getAddress());
        assertThat(orders.get(1).getEmail()).isNotEqualTo(order2.getMember().getEmail());
    }

    @Test
    @DisplayName("주문 저장 시 OrderCreatedEvent 발행 및 주문 정보 검증")
    void saveOrderAndPublishEvent() throws Exception {
        log.info("주문 저장 및 이벤트 발행 테스트 시작");

        // given
        OrderRequestDto requestDto = new OrderRequestDto(
                List.of(new CoffeeListDto("아메리카노", 2)),
                0,
                "ex@example.com",
                "경기도",
                "12345"
        );

        Member member = genMember(requestDto.getEmail(), "ex", "ex");

        when(memberRepository.findByEmail(requestDto.getEmail()))
                .thenReturn(Optional.of(member));

        Order fakeOrder = Order.builder()
                .member(member)
                .address(requestDto.getAddress())
                .postNum(requestDto.getPostNum())
                .price(3000)
                .build();
        setId(fakeOrder, 1L);

        when(orderRepository.save(any(Order.class))).thenReturn(fakeOrder);
        when(coffeeOrderService.genCoffeeOrderList(any(), any())).thenReturn(List.of());

        // when
        orderService.save(requestDto);

        log.info("주문 저장 및 이벤트 발행 완료");

        // then
        verify(publisher).publishEvent(eventCaptor.capture());
        OrderCreatedEvent capturedEvent = eventCaptor.getValue();

        assertThat(capturedEvent.order().getMember().getEmail()).isEqualTo("ex@example.com");
        assertThat(capturedEvent.order().getAddress()).isEqualTo("경기도");

        log.info("이벤트 발행 및 주문 정보 검증 완료");
    }

    // =============== Helper Methods ===============

    private Member genMember(String email, String username, String password) {
        return Member.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();
    }

    private CoffeeImg genCoffeeImg(String title, String url, Coffee coffee) {
        return CoffeeImg.builder()
                .title(title)
                .url(url)
                .coffee(coffee)
                .build();
    }

    private List<CoffeeImg> genImages(CoffeeImg... coffeeImages) {
        return List.of(coffeeImages);
    }

    private Coffee genCoffee(String name, Integer price, List<CoffeeImg> images, Integer stock) {
        return Coffee.builder()
                .name(name)
                .price(price)
                .images(images)
                .stock(stock)
                .build();
    }

    private Order genOrder(Member member, String address, String postNum, Integer price) {
        return Order.builder()
                .member(member)
                .address(address)
                .postNum(postNum)
                .price(price)
                .build();
    }

    private CoffeeOrder genCoffeeOrder(Coffee coffee, Order order, Integer quantity) {
        return CoffeeOrder.builder()
                .coffee(coffee)
                .order(order)
                .quantity(quantity)
                .build();
    }

    private List<CoffeeOrder> genCoffeeOrderList(CoffeeOrder... coffeeOrders) {
        return List.of(coffeeOrders);
    }

    private void setId(Order order, Long id) throws Exception {
        Field idField = Order.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(order, id);
    }

    private Integer getPrice(List<CoffeeOrder> coffeeOrders) {
        return coffeeOrders.stream()
                .mapToInt(coffeeOrder -> coffeeOrder.getCoffee().getPrice() * coffeeOrder.getQuantity())
                .sum();
    }
}
