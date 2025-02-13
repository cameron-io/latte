package io.netstacker.latte.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
