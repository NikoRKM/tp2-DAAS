package ar.edu.unju.fi.tp2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TokenActivacionException extends RuntimeException {

    public TokenActivacionException(String mensaje) {
        super(mensaje);
    }
}
