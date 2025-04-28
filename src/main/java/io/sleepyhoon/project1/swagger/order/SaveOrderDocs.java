package io.sleepyhoon.project1.swagger.order;

import io.sleepyhoon.project1.dto.CoffeeListDto;
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
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "새로운 주문 등록",
        description = "이름,가격,주소,우편번호를 입력받아 새로운 주문을 등록합니다.",
        parameters = {
                @Parameter(
                        name = "email",
                        description = "조회할 사용자의 이메일",
                        example = "example@email.com",
                        required = true
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "조회 성공",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = SaveOrderDocs.OrderSaveApiResponse.class),
                                examples = @ExampleObject(
                                        name = "GetAllOrdersSuccessExample",
                                        summary = "주문 목록 조회 성공 예시",
                                        value = """
                                                {
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
                                                 }
                                                """
                                )
                        )
                )
        }
)
public @interface SaveOrderDocs {
    @Getter
    @Setter
    @Schema(description = "주문 저장에 대한 응답")
    class OrderSaveApiResponse {
        @Schema(description = "응답 데이터")
        private OrderResponseDto data;

        @Schema(description = "응답 메시지", example = "생성 성공")
        private String message;

        @Schema(description = "HTTP 상태 코드", example = "201")
        private Integer status;
    }
}
