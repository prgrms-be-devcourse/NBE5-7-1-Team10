package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.CoffeeRepository;
import io.sleepyhoon.project1.dto.CoffeeRequestDto;
import io.sleepyhoon.project1.dto.CoffeeResponseDto;
import io.sleepyhoon.project1.entity.Coffee;
import io.sleepyhoon.project1.exception.CoffeeInvalidRequestException;
import io.sleepyhoon.project1.exception.CoffeeDuplicationException;
import io.sleepyhoon.project1.exception.CoffeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    //Coffee Name으로 커피 조회
    public Coffee findFirstCoffeeByName(String coffeeName) {
        return coffeeRepository.findFirstByName(coffeeName)
                .orElseThrow(() -> new CoffeeNotFoundException(coffeeName));
    }

    public void checkDuplication(String coffeeName) {
        List<Coffee> byName = coffeeRepository.findByName(coffeeName);
        if (!byName.isEmpty()) {
            throw new CoffeeDuplicationException(coffeeName);
        }
    }

    public List<CoffeeResponseDto> findEveryCoffee() {
        List<Coffee> coffeeList = coffeeRepository.findAll();
        List<CoffeeResponseDto> coffeeListDto = new ArrayList<>();
        for(Coffee coffee : coffeeList) {
            CoffeeResponseDto responseCoffeeDto = CoffeeResponseDto.builder()
                    .id(coffee.getId())
                    .name(coffee.getName())
                    .price(coffee.getPrice())
                    .img(coffee.getImg())
                    .build();

            coffeeListDto.add(responseCoffeeDto);
        }
        return coffeeListDto;
    }

    //Coffee 저장하는 메소드
    public Long save(CoffeeRequestDto requestDto) {

        //유효하지 않은 값 = ture
        boolean isInvalidName = checkRequiredField(requestDto);

        if(isInvalidName) {
            throw new CoffeeInvalidRequestException();
        }

        checkDuplication(requestDto.getName());

        Coffee newCoffee = Coffee.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .img(requestDto.getImg())
                .build();

        return coffeeRepository.save(newCoffee).getId();
    }

    //requestDto 유효성 검사 메소드(null값 유무, 빈칸, 공백만 포함)
    public boolean checkRequiredField(CoffeeRequestDto requestDto) {
        String coffeeName = requestDto.getName();
        return coffeeName == null || coffeeName.isBlank();
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

        if (requestDto.getImg() != null) {
            targetCoffee.setImg(requestDto.getImg());
        }

        return targetCoffee;

    }


}
