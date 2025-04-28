package io.sleepyhoon.project1.exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(Integer orderQuantity, Integer stockQuantity) {
        super(String.format("주문한 수량(%d)를 처리할 수 없습니다. 현재 재고 : %d", orderQuantity, stockQuantity));
    }
}
