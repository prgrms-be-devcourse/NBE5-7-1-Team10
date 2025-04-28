package io.sleepyhoon.project1.swagger.order;

import io.sleepyhoon.project1.dto.OrderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
        summary = "해당 이메일을 가진 유저의 모든 주문 조회",
        description = "해당 유저의 모든 주문의 상세 정보를 조회합니다.",
        parameters = {
                @Parameter(
                        name = "email",
                        description = "조회할 유저의 이메일",
                        example = "example@email.com"
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "조회 성공",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = GetAllOrdersDocs.OrderListApiResponse.class),
                                examples = @ExampleObject(
                                        name = "GetAllCoffeeSuccessExample",
                                        summary = "성공 응답 예시",
                                        value = """
                                                    {
                                                      "data": [
                                                       {
                                                         "id": 1,
                                                         "price": 4500,
                                                         "email": "user@user.com",
                                                         "address": "서울시 강남구",
                                                         "postNum": "12345"
                                                       },
                                                       {
                                                         "id": 2,
                                                         "price": 100000,
                                                         "email": "user@user.com",
                                                         "address": "서울시 강남구",
                                                         "postNum": "12345"
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
public @interface GetAllOrdersDocs {
    @Getter
    @Setter
    @Schema(description = "주문 목록 조회 응답")
    class OrderListApiResponse {

        @Schema(description = "응답 데이터 (주문 리스트)")
        private List<OrderResponseDto> data;

        @Schema(description = "응답 메시지", example = "조회 성공")
        private String message;

        @Schema(description = "HTTP 상태 코드", example = "200")
        private Integer status;
    }
}
