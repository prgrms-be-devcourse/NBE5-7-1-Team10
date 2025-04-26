package io.sleepyhoon.project1.controller;


import io.sleepyhoon.project1.dto.ApiResponse;
import io.sleepyhoon.project1.dto.CoffeeRequestDto;
import io.sleepyhoon.project1.dto.CoffeeResponseDto;
import io.sleepyhoon.project1.entity.Coffee;
import io.sleepyhoon.project1.entity.CoffeeImg;
import io.sleepyhoon.project1.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        List<String> images = findCoffee.getImages().stream()
                .map(CoffeeImg::getUrl)
                .toList();

        CoffeeResponseDto coffeeResponseDto = CoffeeResponseDto.builder()
                .id(findCoffee.getId())
                .name(findCoffee.getName())
                .price(findCoffee.getPrice())
                .images(images)
                .build();

        return ResponseEntity.ok(new ApiResponse<>(coffeeResponseDto,"조회 성공",200));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CoffeeResponseDto>>> getAllCoffees() {
        List<CoffeeResponseDto> responseCoffeeDto = coffeeService.findEveryCoffee();
        return ResponseEntity.ok(new ApiResponse<>(responseCoffeeDto, "조회 성공",200));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> saveCoffee(@RequestBody CoffeeRequestDto requestDto) {

        Long newId = coffeeService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(newId, "생성 성공", 201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CoffeeResponseDto>> updateCoffee(@PathVariable long id, @RequestBody CoffeeRequestDto requestDto) {
        Coffee updated = coffeeService.update(id, requestDto);
        CoffeeResponseDto coffeeResponseDto = CoffeeResponseDto.builder()
                .id(updated.getId())
                .name(updated.getName())
                .price(updated.getPrice())
//                .img(updated.getImg())
                .build();

        return ResponseEntity.ok(new ApiResponse<>(coffeeResponseDto,"수정 성공",200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> deleteCoffee(@PathVariable long id) {
        coffeeService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(id, "삭제 성공", 200));
    }
}
