package io.sleepyhoon.project1.swagger.coffee;

import io.sleepyhoon.project1.dto.CoffeeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Target({ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Operation(
        summary = "모든 커피 메뉴 조회",
        description = "모든 커피의 상세 정보를 조회합니다.",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "조회 성공",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = GetAllCoffeesDocs.CoffeeListApiResponse.class),
                                examples = @ExampleObject(
                                        name = "GetAllCoffeeSuccessExample",
                                        summary = "성공 응답 예시",
                                        value = """
                                                    {
                                                      "data": [
                                                       {
                                                         "id": 1,
                                                         "name": "Americano",
                                                         "price": 4500,
                                                         "img": "https://example.com/images/americano.jpg"
                                                       },
                                                       {
                                                         "id": 2,
                                                         "name": "Latte",
                                                         "price": 5000,
                                                         "img": "https://example.com/images/latte.jpg"
                                                       }
                                                     ],
                                                      "message": "조회 성공",
                                                      "status": 200
                                                    }
                                                    """
                                )
                        )
                ),
                @ApiResponse(responseCode = "500", description = "서버 오류")
        }
)
public @interface GetAllCoffeesDocs {
    @Getter
    @Setter
    @Schema(description = "커피 목록 조회 응답")
    class CoffeeListApiResponse {

        @Schema(description = "응답 데이터 (커피 리스트)")
        private List<CoffeeResponseDto> data;

        @Schema(description = "응답 메시지", example = "조회 성공")
        private String message;

        @Schema(description = "HTTP 상태 코드", example = "200")
        private Integer status;
    }

}
