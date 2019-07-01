package projeto.rest.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Comentario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String comentario;
	private String usuario;
	private Date dataEHora;	
	private String apagado;
	private Long comentarioPai;
	private String nomeDisciplina;
	
	public Comentario() {
		
	}
    
	public Comentario(String comentario, String emailUsuario, String nomeDisciplina) {
		this.comentario = comentario;
		this.usuario = emailUsuario;
	    this.dataEHora = new Date();
	    this.apagado = "nao";
	    this.nomeDisciplina = nomeDisciplina;
	}	
	
	public Comentario(String comentario, String emailUsuario, long comentarioPaiId) {
		this.comentario = comentario;
		this.usuario = emailUsuario;
	    this.dataEHora = new Date();
	    this.apagado = "nao";
	    this.comentarioPai = comentarioPaiId;
	}	
}
