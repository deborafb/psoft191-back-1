package projeto.rest.response;

import java.util.List;

import lombok.Data;

import projeto.rest.response.ComentarioResponse;
import projeto.rest.model.Likes;
import projeto.rest.model.Nota;

@Data
public class PerfilDisciplinaResponse {
	
    private String nomeDisciplina; 	
	private List<ComentarioResponse> comentarios;		
	private List<Likes> likes;		
	private List<Nota> notas;    
	private boolean curtiu;
   
    public PerfilDisciplinaResponse(String nome, List<ComentarioResponse> comentarios, List<Likes> likes, List<Nota> notas) {
    	this.nomeDisciplina = nome;
    	this.comentarios = comentarios;
    	this.likes = likes;
    	this.notas = notas;
    }

}
