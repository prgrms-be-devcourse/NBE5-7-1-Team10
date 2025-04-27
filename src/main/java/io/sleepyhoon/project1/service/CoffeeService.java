package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.CoffeeRepository;
import io.sleepyhoon.project1.dto.CoffeeRequestDto;
import io.sleepyhoon.project1.dto.CoffeeResponseDto;
import io.sleepyhoon.project1.entity.Coffee;
import io.sleepyhoon.project1.entity.CoffeeImg;
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
    private final CoffeeImgService coffeeImgService;

    public Coffee findById(Long id) {

        return coffeeRepository.findById(id)
               .orElseThrow(() -> new CoffeeNotFoundException(id));
    }

    public CoffeeResponseDto findByIdAndMapToDto(Long id) {
        Coffee findCoffee = findById(id);
        List<String> coffeeImages = findCoffeeImages(findCoffee);

        return CoffeeResponseDto.builder()
                .id(findCoffee.getId())
                .name(findCoffee.getName())
                .price(findCoffee.getPrice())
                .images(coffeeImages)
                .build();
    }

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

    //Coffee가 가지고 있는 CoffeeImg에서 Url을 뽑아 List로 변환
    private List<String> findCoffeeImages(Coffee coffee) {
        return coffee.getImages().stream()
                .map(CoffeeImg::getUrl)
                .toList();
    }

    public List<CoffeeResponseDto> findEveryCoffee() {
        List<Coffee> coffeeList = coffeeRepository.findAll();
        List<CoffeeResponseDto> coffeeListDto = new ArrayList<>();
        for(Coffee coffee : coffeeList) {
            List<String> images = findCoffeeImages(coffee);
            CoffeeResponseDto responseCoffeeDto = CoffeeResponseDto.builder()
                    .id(coffee.getId())
                    .name(coffee.getName())
                    .price(coffee.getPrice())
                    .images(images)
                    .build();

            coffeeListDto.add(responseCoffeeDto);
        }
        return coffeeListDto;
    }

    public Long save(CoffeeRequestDto requestDto) {

        boolean isInvalidName = checkRequiredField(requestDto);

        if(isInvalidName) {
            throw new CoffeeInvalidRequestException();
        }

        checkDuplication(requestDto.getName());

        Coffee newCoffee = Coffee.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .build();

        List<CoffeeImg> coffeeImgs = coffeeImgService.saveImg(requestDto.getImages(), newCoffee);

        newCoffee.setImages(coffeeImgs);

        return coffeeRepository.save(newCoffee).getId();
    }

    //requestDto 유효성 검사 메소드(null값 유무, 빈칸, 공백만 포함)
    public boolean checkRequiredField(CoffeeRequestDto requestDto) {
        String coffeeName = requestDto.getName();
        return coffeeName == null || coffeeName.isBlank();
    }

    public void deleteById(Long id) {
        Coffee coffee = findById(id);
        coffeeRepository.delete(coffee);
    }

    public CoffeeResponseDto update(Long id,CoffeeRequestDto requestDto) {
        Coffee targetCoffee = findById(id);

        if (requestDto.getName() != null) {
            targetCoffee.setName(requestDto.getName());
        }

        if (requestDto.getPrice() != null) {
            targetCoffee.setPrice(requestDto.getPrice());
        }

        if (requestDto.getImages() != null) {
            List<CoffeeImg> coffeeImgs = coffeeImgService.saveImg(requestDto.getImages(), targetCoffee);
            targetCoffee.getImages().clear();
            targetCoffee.getImages().addAll(coffeeImgs);
        }

        List<String> coffeeImages = findCoffeeImages(targetCoffee);

        return CoffeeResponseDto.builder()
                .id(targetCoffee.getId())
                .name(targetCoffee.getName())
                .price(targetCoffee.getPrice())
                .images(coffeeImages)
                .build();
    }
}
