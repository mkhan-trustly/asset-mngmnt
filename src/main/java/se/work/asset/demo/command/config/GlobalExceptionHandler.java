package se.work.asset.demo.command.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.work.asset.demo.command.exception.NoAssetFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoAssetFoundException.class})
    public ResponseEntity<Object> handleNoAssetFoundException(NoAssetFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
