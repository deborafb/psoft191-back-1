package projeto.rest.model;

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
public class Likes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String emailUsuario;
	private String nome;
	
	@ManyToOne()
	@JoinColumn(name="nomeDisciplina")
	@JsonBackReference
	private PerfilDisciplina perfil;
	
	
	public Likes() {
		
	}
	
	public Likes(String emailUsuario, String nome, PerfilDisciplina perfil) {
		this.emailUsuario = emailUsuario;
		this.nome = nome;
		this.perfil = perfil;
	}

}
