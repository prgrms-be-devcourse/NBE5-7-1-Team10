package io.sleepyhoon.project1.swagger.coffee;

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
@Operation(summary = "커피 삭제",
        description = "커피 ID를 통해 커피를 삭제합니다.",
        parameters = {
                @Parameter(name = "id", description = "삭제하려는 주문의 id"),
                @Parameter(name = "email", description = "삭제하려는 주문의 email", example = "example@email.com")
        }
)
@ApiResponses({
        @ApiResponse(
                responseCode = "204",
                description = "주문 삭제 성공",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = DeleteCoffeeDocs.DeleteCoffeeApiResponse.class),
                        examples = @ExampleObject(
                                name = "DeleteCoffeeSuccessExample",
                                summary = "커피 삭제 성공 예시",
                                value = """
                                        {
                                          "data": 1,
                                          "message": "삭제 성공",
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
public @interface DeleteCoffeeDocs {
    @Getter
    @Setter
    @Schema(description = "커피 삭제 응답")
    class DeleteCoffeeApiResponse {

        @Schema(description = "삭제된 커피 ID", example = "1")
        private Long data;

        @Schema(description = "응답 메시지", example = "삭제 성공")
        private String message;

        @Schema(description = "HTTP 상태 코드", example = "200")
        private Integer status;
    }
}
