package projeto.rest.response;

import java.util.Date;

import lombok.Data;

@Data
public class ComentarioResponse {

    private Long id;	
	private String comentario;
	private String usuario;
	private Date dataEHora;	
    
	public ComentarioResponse(long id, String comentario, String emailUsuario, Date data) {
		this.id = id;
		this.comentario = comentario;
		this.usuario = emailUsuario;
	    this.dataEHora = data;
	}	
	
}
