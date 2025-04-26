package io.sleepyhoon.project1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
public class CoffeeResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private List<String> images;

    @Builder
    public CoffeeResponseDto(Long id, String name, Integer price, List<String> images) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.images = images;
    }
}
