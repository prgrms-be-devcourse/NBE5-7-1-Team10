package io.sleepyhoon.project1.dao;

import io.sleepyhoon.project1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByEmailAndAddress(String email, String Address);

    List<Order> findByEmail(String email);

    @Query("SELECT DISTINCT o.email FROM Order o")
    List<String> findDistinctEmails();
}
