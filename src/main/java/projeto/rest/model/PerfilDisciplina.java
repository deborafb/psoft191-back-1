package projeto.rest.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class PerfilDisciplina {
	
	@Id
    private String nomeDisciplina; 
	
	@OneToMany(mappedBy="perfil")
	@JsonManagedReference
	private List<Likes> likes;	
	
	@OneToMany(mappedBy="perfil")
	@JsonManagedReference
	private List<Nota> notas;	

		
    
    public PerfilDisciplina() {
    	
    }
    
    public PerfilDisciplina(String nome) {
    	this.nomeDisciplina = nome;    	
    }
    
    public double notaDisciplina() {
    	double nota = 0.0;
    	for (int i = 0; i < this.notas.size(); i++) {
    		nota += this.notas.get(i).getNota();
    	}
    	if (this.notas.size() > 0) {
        	nota = nota/this.notas.size();
    	}
    	return nota;
    }
    
    public int sizeNotas() {
    	return notas.size();
    }
}
