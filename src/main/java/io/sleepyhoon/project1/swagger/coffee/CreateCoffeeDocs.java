package io.sleepyhoon.project1.swagger.coffee;

import io.sleepyhoon.project1.dto.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import lombok.Getter;
import lombok.Setter;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "새로운 커피 등록",
        description = "이름과 가격, 이미지를 입력받아 새로운 커피를 등록합니다.",
        parameters = {
                @io.swagger.v3.oas.annotations.Parameter(name = "name", description = "등록하려는 커피의 이름"),
                @io.swagger.v3.oas.annotations.Parameter(name = "price", description = "등록하려는 커피의 가격"),
                @io.swagger.v3.oas.annotations.Parameter(name = "img", description = "등록하려는 커피의 이미지")
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "등록 성공",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CreateCoffeeDocs.CoffeeCreateApiResponse.class),
                                examples = @ExampleObject(
                                        name = "CreateCoffeeSuccessExample",
                                        summary = "커피 등록 성공 예시",
                                        value = """
                                                {
                                                  "data": 123,
                                                  "message": "생성 성공",
                                                  "status": 201
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "잘못된 요청 (입력값 오류)",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class),
                                examples = @ExampleObject(
                                        name = "InvalidRequestExample",
                                        summary = "잘못된 요청 예시",
                                        value = """
                                                {
                                                  "error": {
                                                    "code": "COFFEE_INVALID_REQUEST",
                                                    "message": "Cannot create coffee with a null or empty name."
                                                  },
                                                  "status": 400,
                                                  "timestamp": "2024-04-29T12:00:00"
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "409",
                        description = "중복된 커피 등록",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class),
                                examples = @ExampleObject(
                                        name = "DuplicationExample",
                                        summary = "커피 이름 중복 예시",
                                        value = """
                                                {
                                                  "error": {
                                                    "code": "COFFEE_DUPLICATION",
                                                    "message": "A coffee with {coffeeName} already exists."
                                                  },
                                                  "status": 409,
                                                  "timestamp": "2024-04-29T12:00:00"
                                                }
                                                """
                                )
                        )
                )
        }
)

public @interface CreateCoffeeDocs {
    @Getter
    @Setter
    @Schema(description = "커피 단건 조회 응답")
    class CoffeeCreateApiResponse {
        @Schema(description = "응답 데이터")
        private Long id;

        @Schema(description = "응답 메시지", example = "조회 성공")
        private String message;

        @Schema(description = "HTTP 상태 코드", example = "200")
        private Integer status;
    }
}
