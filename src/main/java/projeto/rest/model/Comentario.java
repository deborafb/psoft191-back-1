package projeto.rest.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	
	@ManyToOne()
	@JoinColumn(name="nomeDisciplina")
	@JsonBackReference
	private PerfilDisciplina perfil;
	
	public Comentario() {
		
	}
    
	public Comentario(String comentario, String emailUsuario, PerfilDisciplina perfil) {
		this.comentario = comentario;
		this.usuario = emailUsuario;
	    this.dataEHora = new Date();
	    this.perfil = perfil;
	}	
}
