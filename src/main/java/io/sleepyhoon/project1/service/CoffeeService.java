package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.CoffeeRepository;
import io.sleepyhoon.project1.dto.CoffeeRequestDto;
import io.sleepyhoon.project1.entity.Coffee;
import io.sleepyhoon.project1.exception.CoffeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;

    //Coffee ID로 커피 찾아 반환하는 메소드(Optional로 반환)
    public Coffee findById(Long id) {

        return coffeeRepository.findById(id)
               .orElseThrow(() -> new CoffeeNotFoundException(id));
    }

    public List<Coffee> findEveryCoffee() {
        return coffeeRepository.findAll();
    }

    //Coffee 저장하는 메소드
    public Coffee save(Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    //Coffee 삭제하는 메소드
    public void deleteById(Long id) {
        Coffee coffee = findById(id);
        coffeeRepository.delete(coffee);
    }

    //Coffee 정보 변경하는 메소드
    public Coffee update(Long id,CoffeeRequestDto requestDto) {
        Coffee targetCoffee = findById(id);

        if (requestDto.getName() != null) {
            targetCoffee.setName(requestDto.getName());
        }

        if (requestDto.getPrice() != null) {
            targetCoffee.setPrice(requestDto.getPrice());
        }

        return targetCoffee;

    }


}
