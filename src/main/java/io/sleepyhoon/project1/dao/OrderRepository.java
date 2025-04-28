package io.sleepyhoon.project1.dao;


import io.sleepyhoon.project1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByMember_Email(String email);

    List<Order> findByIsProcessedFalseAndOrderedAtBetween(
            LocalDateTime start, LocalDateTime end);


    @Modifying
    @Query("UPDATE Order o SET o.isProcessed = true WHERE o.id IN :ids")
    int markProcessedTrueByIdIn(@Param("ids") List<Long> ids);

}
