package kp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler {
    @ExceptionHandler({
            Exception.class,
            RuntimeException.class
    })
    public ResponseEntity handleAllExceptions(Exception e) {
        log.error("Exception:", e);
        return ResponseEntity.internalServerError().build();
    }
}
