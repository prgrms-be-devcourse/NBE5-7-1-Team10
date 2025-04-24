package io.sleepyhoon.project1.dao;

import io.sleepyhoon.project1.dto.OrderDto;
import io.sleepyhoon.project1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByEmailAndAddress(String email, String Address);

    @Query("SELECT new io.sleepyhoon.project1.dto.OrderDto(o.email, o.address, o.postNum) FROM Order o WHERE o.email = :email")
    List<OrderDto> findByEmail(@Param("email")String email);

    @Query("SELECT new io.sleepyhoon.project1.dto.OrderDto(o.email, o.address, o.postNum) FROM Order o WHERE o.id = :id")
    Optional<OrderDto> findDtoById(@Param("id")Long id);
}
