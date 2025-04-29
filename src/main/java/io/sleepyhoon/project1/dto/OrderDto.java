package io.sleepyhoon.project1.dto;

import io.sleepyhoon.project1.entity.Order;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {

    private String email;
    private String address;
    private String postNum;
}

