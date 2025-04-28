package io.sleepyhoon.project1.dao;

import io.sleepyhoon.project1.entity.CoffeeImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeImgRepository extends JpaRepository<CoffeeImg, Long> {
}
