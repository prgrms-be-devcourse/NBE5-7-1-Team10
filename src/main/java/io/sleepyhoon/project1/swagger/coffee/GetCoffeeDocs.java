package io.sleepyhoon.project1.swagger.coffee;

import io.sleepyhoon.project1.dto.CoffeeResponseDto;
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

@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "커피 상세 정보 조회",
        description = "커피 ID를 통해 하나의 커피 상세 정보를 조회합니다.",
        parameters = {
                @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "조회하려는 커피의 id")
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "조회 성공",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = GetCoffeeDocs.CoffeeDetailApiResponse.class),
                                examples = @ExampleObject(
                                        name = "SuccessExample",
                                        summary = "성공 응답 예시",
                                        value = """
                                                    {
                                                      "data": {
                                                        "id": 1,
                                                        "name": "Americano",
                                                        "price": 4500,
                                                        "img": "https://example.com/images/americano.jpg"
                                                      },
                                                      "message": "조회 성공",
                                                      "status": 200
                                                    }
                                                    """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "커피를 찾을 수 없음",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class),
                                examples = @ExampleObject(
                                        name = "Not_Found_Example",
                                        summary = "커피를 찾을 수 없음 응답 예시",
                                        value = """
                                                {
                                                  "error": {
                                                    "code": "COFFEE_NOT_FOUND",
                                                    "message": "Coffee with id {number} not found"
                                                  },
                                                  "status": 404,
                                                  "timestamp": "2024-04-28T21:30:45.123"
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(responseCode = "500", description = "서버 오류")
        }
)
public @interface GetCoffeeDocs {
        @Getter
        @Setter
        @Schema(description = "커피 단건 조회 응답")
        class CoffeeDetailApiResponse {
                @Schema(description = "응답 데이터")
                private CoffeeResponseDto data;

                @Schema(description = "응답 메시지", example = "조회 성공")
                private String message;

                @Schema(description = "HTTP 상태 코드", example = "200")
                private Integer status;
        }
}
