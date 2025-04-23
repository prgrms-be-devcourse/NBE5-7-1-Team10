package io.sleepyhoon.project1.controller;


import io.sleepyhoon.project1.dto.ApiResponse;
import io.sleepyhoon.project1.dto.CoffeeRequestDto;
import io.sleepyhoon.project1.dto.CoffeeResponseDto;
import io.sleepyhoon.project1.entity.Coffee;
import io.sleepyhoon.project1.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/coffees")
public class CoffeeController {

    private final CoffeeService coffeeService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CoffeeResponseDto>> getCoffee(@PathVariable long id) {

        Coffee findCoffee = coffeeService.findById(id);

        CoffeeResponseDto coffeeResponseDto = CoffeeResponseDto.builder()
                .id(findCoffee.getId())
                .name(findCoffee.getName())
                .price(findCoffee.getPrice())
                .img(findCoffee.getImg())
                .build();

        return ResponseEntity.ok(new ApiResponse<>(coffeeResponseDto,"조회 성공",200));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CoffeeResponseDto>>> getAllCoffees() {
        List<Coffee> everyCoffee = coffeeService.findEveryCoffee();
        List<CoffeeResponseDto> coffeeListDto = new ArrayList<>();
        for(Coffee coffee : everyCoffee) {
            CoffeeResponseDto responseCoffeeDto = CoffeeResponseDto.builder()
                    .id(coffee.getId())
                    .name(coffee.getName())
                    .price(coffee.getPrice())
                    .build();

            coffeeListDto.add(responseCoffeeDto);
        }

        return ResponseEntity.ok(new ApiResponse<>(coffeeListDto, "조회 성공",200));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> saveCoffee(@RequestBody CoffeeRequestDto requestDto) {
        Coffee newCoffee = Coffee.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .img(requestDto.getImg())
                .build();


        Coffee save = coffeeService.save(newCoffee);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(save.getId(), "생성 성공", 201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CoffeeResponseDto>> updateCoffee(@PathVariable long id, @RequestBody CoffeeRequestDto requestDto) {
        Coffee updated = coffeeService.update(id, requestDto);
        CoffeeResponseDto coffeeResponseDto = CoffeeResponseDto.builder()
                .id(updated.getId())
                .name(updated.getName())
                .price(updated.getPrice())
                .img(updated.getImg())
                .build();

        return ResponseEntity.ok(new ApiResponse<>(coffeeResponseDto,"수정 성공",200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> deleteCoffee(@PathVariable long id) {
        coffeeService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(id, "삭제 성공", 200));
    }
}
