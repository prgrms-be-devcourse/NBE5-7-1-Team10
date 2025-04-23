package io.sleepyhoon.project1.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {

    private String email;
    private String address;
    private String postNum;

    @Builder
    public OrderDto(String email, String address, String postNum) {
        this.email = email;
        this.address = address;
        this.postNum = postNum;
    }
}
