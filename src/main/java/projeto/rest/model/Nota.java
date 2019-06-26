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
public class Nota {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int nota;
	
	@ManyToOne()
	@JoinColumn(name="nomeDisciplina")
	@JsonBackReference
	private PerfilDisciplina perfil;
	
	
	public Nota() {		
		
	}
	
	public Nota(int nota, PerfilDisciplina perfil) {
		this.nota = nota;
		this.perfil = perfil;
	}
}
