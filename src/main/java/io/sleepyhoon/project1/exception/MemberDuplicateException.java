package io.sleepyhoon.project1.exception;

public class MemberDuplicateException extends RuntimeException {
    public MemberDuplicateException(String message) {
        super(message);
    }
}
