package io.sleepyhoon.project1.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class CoffeeRequestDto {
    private String name;
    private Integer price;
    private List<MultipartFile> images;

    @Builder
    public CoffeeRequestDto(String name, Integer price, List<MultipartFile> images) {
        this.name = name;
        this.price = price;
        this.images = images;
    }
}
