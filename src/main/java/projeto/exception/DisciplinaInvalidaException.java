package projeto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)

public class DisciplinaInvalidaException extends RuntimeException {
	 /**
     * 
	  */
	private static final long serialVersionUID = 1L;

	public DisciplinaInvalidaException(String msg) {
	     super(msg);
	}	
}
