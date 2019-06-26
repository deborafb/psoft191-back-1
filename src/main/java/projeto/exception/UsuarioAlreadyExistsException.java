package projeto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UsuarioAlreadyExistsException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UsuarioAlreadyExistsException(String msg) {
        super(msg);
    }
}
