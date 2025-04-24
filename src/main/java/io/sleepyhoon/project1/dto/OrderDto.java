package io.sleepyhoon.project1.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {

    private String email;
    private String address;
    private String postNum;
}
