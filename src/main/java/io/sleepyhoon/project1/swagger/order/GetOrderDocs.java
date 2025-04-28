package io.sleepyhoon.project1.swagger.order;

import io.sleepyhoon.project1.dto.ErrorResponseDto;
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
import lombok.Getter;
import lombok.Setter;

@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "하나의 주문 상세 정보 조회",
        description = "주문 ID를 통해 하나의 주문 상세 정보를 조회합니다.",
        parameters = {
                @Parameter(name = "id", description = "조회하려는 주문의 id")
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "조회 성공",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = GetOrderDocs.OrderGetApiResponse.class),
                                examples = @ExampleObject(
                                        name = "SuccessExample",
                                        summary = "성공 응답 예시",
                                        value = """
                                                    "data": {
                                                     "id": 1,
                                                     "price": 12000,
                                                     "email": "user@example.com",
                                                     "address": "서울특별시 강남구 테헤란로 123",
                                                     "postNum": "12345",
                                                     "coffee-list": [
                                                       {
                                                         "coffeeName": "아메리카노",
                                                         "quantity": 2
                                                       },
                                                       {
                                                         "coffeeName": "카푸치노",
                                                         "quantity": 3
                                                       }
                                                     ]
                                                   },
                                                   "message": "생성 성공",
                                                   "status": 201
                                                    """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "주문을 찾을 수 없음",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class),
                                examples = @ExampleObject(
                                        name = "Order_Not_Found_Example",
                                        summary = "주문을 찾을 수 없음 응답 예시",
                                        value = """
                                                {
                                                  "error": {
                                                    "code": "ORDER_NOT_FOUND",
                                                    "message": "Order not found"
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
public @interface GetOrderDocs {
    @Getter
    @Setter
    @Schema(description = "주문 조회에 대한 응답")
    class OrderGetApiResponse {
        @Schema(description = "응답 데이터")
        private OrderResponseDto data;

        @Schema(description = "응답 메시지", example = "생성 성공")
        private String message;

        @Schema(description = "HTTP 상태 코드", example = "200")
        private Integer status;
    }
}
