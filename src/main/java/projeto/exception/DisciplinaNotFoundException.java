package projeto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

	
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DisciplinaNotFoundException extends RuntimeException {
	 /**
      * 
	  */
	private static final long serialVersionUID = 1L;

	public DisciplinaNotFoundException(String msg) {
	     super(msg);
	}	
}
