package io.sleepyhoon.project1.dao;

import io.sleepyhoon.project1.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    List<Coffee> findByName(String name);

    Optional<Coffee> findFirstByName(String name);
}
