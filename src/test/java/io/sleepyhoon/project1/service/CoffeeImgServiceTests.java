package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.CoffeeImgRepository;
import io.sleepyhoon.project1.entity.Coffee;
import io.sleepyhoon.project1.entity.CoffeeImg;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest
public class CoffeeImgServiceTests {

    @Autowired
    private CoffeeImgRepository coffeeImgRepository;

    @Autowired
    private CoffeeImgService coffeeImgService;

    @Test
    @DisplayName("img저장 성공 테스트")
    void imgSaveSuccessTest() throws Exception {
        //given
        MultipartFile mockFile1 = new MockMultipartFile(
                "file",
                "test-image1.jpg",
                "image/jpeg",
                "test 1 image content".getBytes()
        );

        MultipartFile mockFile2 = new MockMultipartFile(
                "file2",
                "test-image2.jpg",
                "image/jpeg",
                "test 2 image content".getBytes()
        );

        List<MultipartFile> imgList = List.of(mockFile1, mockFile2);

        Coffee coffee = Coffee.builder()
                .name("coffee")
                .price(456)
                .build();

        //when
        List<CoffeeImg> result = coffeeImgService.saveImg(imgList,coffee);

        //then
        assertThat(result).hasSize(2);

        CoffeeImg coffeeImg1 = result.get(0);
        assertThat(coffeeImg1.getTitle()).isEqualTo("test-image1.jpg");
        assertThat(coffeeImg1.getUrl()).isEqualTo("img/"+"test-image1.jpg");

        CoffeeImg coffeeImg2 = result.get(1);
        assertThat(coffeeImg2.getTitle()).isEqualTo("test-image2.jpg");
        assertThat(coffeeImg2.getUrl()).isEqualTo("img/"+"test-image2.jpg");



    }

}
