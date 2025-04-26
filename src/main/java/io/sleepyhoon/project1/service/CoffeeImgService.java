package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.CoffeeImgRepository;
import io.sleepyhoon.project1.entity.CoffeeImg;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CoffeeImgService {
    private final CoffeeImgRepository coffeeImgRepository;

    @Value("${file.path}")
    private String uploadPath;

    public List<CoffeeImg> saveImg(List<MultipartFile> imgs) {

        List<CoffeeImg> coffeeImgs = imgs.stream()
                .map(file -> {
                    try {
                        Path path = Paths.get(uploadPath, file.getOriginalFilename());
                        Files.write(path, file.getBytes());

                        return CoffeeImg.builder()
                                .title(file.getOriginalFilename())
                                .url(uploadPath + file.getOriginalFilename())
                                .build();
                    } catch (IOException e) {
                        throw new RuntimeException("이미지 파일 업로드 실패" + e.getMessage());
                    }
                })
                .toList();

        return coffeeImgs;

    }

}
