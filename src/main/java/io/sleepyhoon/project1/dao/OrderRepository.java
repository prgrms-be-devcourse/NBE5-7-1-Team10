package io.sleepyhoon.project1.dao;

import io.sleepyhoon.project1.dto.CoffeeListDto;
import io.sleepyhoon.project1.dto.OrderDto;
import io.sleepyhoon.project1.dto.OrderRequestDto;
import io.sleepyhoon.project1.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByEmailAndAddress(String email, String Address);

    List<Order> findByEmail(@Param("email")String email);

    @Query("SELECT new io.sleepyhoon.project1.dto.OrderRequestDto(o.price,o.email, o.address, o.postNum) " +
            "FROM Order o " +
            "WHERE o.id = :id")
    Optional<OrderRequestDto> findDtoById(@Param("id")Long id);

    @Query("SELECT new io.sleepyhoon.project1.dto.CoffeeListDto(c.name, co.quantity) " +
            "FROM Order o " +
            "JOIN o.coffeeOrders co " +
            "JOIN co.coffee c " +
            "WHERE o.id = :id")
    List<CoffeeListDto> findCoffeeListByOrderId(@Param("id")Long id);

    List<Order> findByIsProcessedFalseAndOrderedAtBetween(
            LocalDateTime start, LocalDateTime end);


    @Modifying
    @Query("UPDATE Order o SET o.isProcessed = true WHERE o.id IN :ids")
    int markProcessedTrueByIdIn(@Param("ids") List<Long> ids);

}
