package io.sleepyhoon.project1.coffeetests;

import io.sleepyhoon.project1.dao.CoffeeRepository;
import io.sleepyhoon.project1.dto.CoffeeRequestDto;
import io.sleepyhoon.project1.entity.Coffee;
import io.sleepyhoon.project1.exception.CoffeeNotFoundException;
import io.sleepyhoon.project1.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.ReflectionUtils;


import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CoffeeServiceTest {

    @InjectMocks
    private CoffeeService coffeeService;

    @Mock
    private CoffeeRepository coffeeRepository;

    @Test
    void findById_whenCoffeeExists() {
        //given
        Long id = 1L;
        Coffee coffee = new Coffee("existsCoffee",5000,"개맛있어보이는커피사진");
//        coffee.setId(id);
        when(coffeeRepository.findById(id)).thenReturn(Optional.of(coffee));

        //when
        Coffee result = coffeeService.findById(id);

        //then
        assertEquals("existsCoffee", result.getName());
    }

    @Test
    void findById_whenCoffeeDoesNotExist() {
        // given
        Long coffeeId = 2L;
        when(coffeeRepository.findById(coffeeId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CoffeeNotFoundException.class, () -> {
            coffeeService.findById(coffeeId);
        });
    }

    //리플렉션으로 id강제 세팅
    private Coffee createCoffeeWithId(Long id, String name, int price, String img) {
        Coffee coffee = Coffee.builder()
                .name(name)
                .price(price)
                .img(img)
                .build();

        try {
            // Coffee 클래스의 "id" 필드 가져오기
            Field idField = Coffee.class.getDeclaredField("id");
            idField.setAccessible(true); // private 접근 허용
            idField.set(coffee, id); // id 값 세팅
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("ID 필드 설정 실패", e);
        }

        return coffee;
    }

    @Test
    @DisplayName("save 테스트")
    void save() {
        CoffeeRequestDto requestDto = CoffeeRequestDto.builder()
                .name("bugCoffee")
                .price(1000)
                .img("dfdaddfadf")
                .build();

        // 리턴될 mock 객체 (ID 포함)
        Coffee savedCoffee = createCoffeeWithId(1L,"bugCoffee",1000,"dfdaddfadf");

        // any(Coffee.class)로 매칭 범용 처리
        when(coffeeRepository.save(any(Coffee.class))).thenReturn(savedCoffee);

        // when
        Long result = coffeeService.save(requestDto);

        // then
        assertNotNull(result);
        assertEquals(1L, result); // savedCoffee.getId()
        verify(coffeeRepository, times(1)).save(any(Coffee.class));
    }

    @Test
    void delete() {
    }

    @Test
    void update_when() {
        // given
        Long coffeeId = 1L;
        Coffee existingCoffee = createCoffeeWithId(coffeeId,"Latte",4000,"개쩌는 사진링크");


        CoffeeRequestDto requestDto = CoffeeRequestDto.builder()
                .name("newLatte")
                .price(1000)
                .img("새로운 라테 사진")
                .build();

        // findById가 호출되면 existingCoffee를 반환
        when(coffeeRepository.findById(coffeeId)).thenReturn(Optional.of(existingCoffee));

        // when
        Coffee result = coffeeService.update(coffeeId, requestDto);

        // then
        assertEquals("newLatte", result.getName());
        assertEquals(1000, result.getPrice());
        assertEquals("새로운 라테 사진", result.getImg());

        // 실제로 커피 객체가 수정되었는지도 확인
        assertEquals("newLatte", existingCoffee.getName());
        assertEquals(1000, existingCoffee.getPrice());

        // 리포지토리 호출 확인
        verify(coffeeRepository).findById(coffeeId);
    }
}