package io.sleepyhoon.project1.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class CoffeeRequestDto {
    private String name;
    private Integer price;
    private Integer stock;
    private List<MultipartFile> images;
}
