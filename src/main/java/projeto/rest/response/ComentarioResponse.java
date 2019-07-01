package projeto.rest.response;

import java.util.Date;

import lombok.Data;

@Data
public class ComentarioResponse {

    private Long id;	
	private String comentario;
	private String emailUsuario;
	private String usuario;
	private Date dataEHora;	
    
	public ComentarioResponse(long id, String comentario, String emailUsuario, String usuario, Date data) {
		this.id = id;
		this.comentario = comentario;
		this.emailUsuario = emailUsuario;
	    this.usuario = usuario;
		this.dataEHora = data;	    
	}		
}
