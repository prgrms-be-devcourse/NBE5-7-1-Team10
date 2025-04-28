package io.sleepyhoon.project1.swagger.order;

import io.sleepyhoon.project1.dto.ErrorResponseDto;
import io.sleepyhoon.project1.swagger.coffee.DeleteCoffeeDocs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
@Operation(summary = "주문 삭제",
        description = "주문 ID를 통해 주문를 삭제합니다.",
        parameters = {
                @Parameter(
                        name = "id",
                        description = "삭제할 주문 ID",
                        example = "1",
                        required = true
                ),
                @Parameter(
                        name = "email",
                        description = "삭제하는 멤버의 이메일",
                        example = "user@user.com",
                        required = true
                )
        })
@ApiResponses({
        @ApiResponse(
                responseCode = "204",
                description = "주문 삭제 성공"
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
        @ApiResponse(
                responseCode = "404",
                description = "다른 유저의 주문 내역을 삭제할 수 없습니다.",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class),
                        examples = @ExampleObject(
                                name = "Order_Owner_Mismatch_Example",
                                summary = "다른 유저의 주문 내역을 삭제할 수 없습니다.",
                                value = """
                                                {
                                                  "error": {
                                                    "code": "ORDER_OWNER_MISMATCH",
                                                    "message": "OrderOwner Mismatch: {input_email} != {order.email}"
                                                  },
                                                  "status": 404,
                                                  "timestamp": "2024-04-28T21:30:45.123"
                                                }
                                                """
                        )
                )
        ),
})
public @interface DeleteOrderDocs {
}

