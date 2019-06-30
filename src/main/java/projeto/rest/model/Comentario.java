package projeto.rest.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

//Falta resolver	
	//@ManyToOne()
//	@JsonBackReference
//	private Comentario pai;
	
//	@OneToMany(mappedBy="pai")
//	@JsonManagedReference
 //   private List<Comentario> filhos;	
	
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
	    this.apagado = "nao";
	}	
}
