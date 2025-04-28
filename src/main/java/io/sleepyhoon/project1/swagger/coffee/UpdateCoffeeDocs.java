package io.sleepyhoon.project1.swagger.coffee;

import io.sleepyhoon.project1.dto.CoffeeRequestDto;
import io.sleepyhoon.project1.dto.CoffeeResponseDto;
import io.sleepyhoon.project1.dto.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "커피 수정",
        description = "커피 ID를 통해 기존 커피 정보를 수정합니다.",
        parameters =    {
                @Parameter(
                        name = "id",
                        description = "수정할 커피 ID",
                        example = "1",
                        required = true
                )
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "수정할 커피 정보 (이름, 가격, 이미지 URL)",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CoffeeRequestDto.class),
                        examples = @ExampleObject(
                                name = "CoffeeUpdateExample",
                                summary = "커피 수정 요청 예시",
                                value = """
                                        {
                                          "name": "아메리카노",
                                          "price": 4500,
                                          "img": "https://example.com/americano.jpg"
                                        }
                                        """
                        )
                )
        )
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "커피 수정 성공",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UpdateCoffeeDocs.UpdateCoffeeApiResponse.class),
                        examples = @ExampleObject(
                                name = "UpdateCoffeeSuccessExample",
                                summary = "커피 수정 성공 예시",
                                value = """
                                        {
                                          "data": {
                                            "id": 1,
                                            "name": "Vanilla Latte",
                                            "price": 5500,
                                            "img": "https://example.com/images/vanilla_latte.jpg"
                                          },
                                          "message": "수정 성공",
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
                                name = "CoffeeNotFoundExample",
                                summary = "커피를 찾을 수 없음 예시",
                                value = """
                                        {
                                          "error": {
                                            "code": "COFFEE_NOT_FOUND",
                                            "message": "Coffee with id {number} not found"
                                          },
                                          "status": 404,
                                          "timestamp": "2024-04-29T12:00:00"
                                        }
                                        """
                        )
                )
        )
})
public @interface UpdateCoffeeDocs {

    @Getter
    @Setter
    @Schema(description = "커피 수정 응답")
    class UpdateCoffeeApiResponse {

        @Schema(description = "수정된 커피 데이터")
        private CoffeeResponseDto data;

        @Schema(description = "응답 메시지", example = "수정 성공")
        private String message;

        @Schema(description = "HTTP 상태 코드", example = "200")
        private int status;
    }
}
