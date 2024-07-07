package com.api.ForoHub.infra.exception;


public class DuplicateTopicException extends RuntimeException {
    public DuplicateTopicException(String message) {
        super(message);
    }
    
}
