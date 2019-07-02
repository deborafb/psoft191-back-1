package projeto.rest.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.bytebuddy.asm.Advice.This;
import projeto.exception.DisciplinaNotFoundException;
import projeto.exception.UsuarioAlreadyExistsException;
import projeto.exception.UsuarioNotFoundException;
import projeto.rest.model.Comentario;
import projeto.rest.model.Disciplina;
import projeto.rest.model.Likes;
import projeto.rest.model.Nota;
import projeto.rest.model.PerfilDisciplina;
import projeto.rest.model.Usuario;
import projeto.rest.response.ComentarioResponse;
import projeto.rest.response.PerfilDisciplinaResponse;
import projeto.rest.service.DisciplinaService;
import projeto.rest.service.PerfilDisciplinaService;
import projeto.rest.service.UsuarioService;

//Privado, apenas para usuários logados
@RestController
@RequestMapping({"v1/disciplina"})
public class PerfilDisciplinaController {

	private PerfilDisciplinaService perfilDisciplinaService;
	private DisciplinaService disciplinaService; 
    private UsuarioService usuarioService;
   
    
	public PerfilDisciplinaController(PerfilDisciplinaService perfilDisciplinaService, DisciplinaService disciplinaService, UsuarioService usuarioService) {
		this.perfilDisciplinaService = perfilDisciplinaService;
		this.disciplinaService = disciplinaService;
		this.usuarioService = usuarioService;
	}	
		
	// Recupera o perfil da disciplina a partir do id
	@GetMapping(value = "/{id}/{email}") 
	public ResponseEntity<PerfilDisciplinaResponse> procurar(@PathVariable long id, @PathVariable String email) {
		Disciplina disciplina = disciplinaService.findById(id);		
		if (disciplina == null) {
			throw new DisciplinaNotFoundException("Disciplina não encontrada");
		} 		
		
		PerfilDisciplina perfil;
		// Se o perfil da disciplina ainda não existe então ele é criado
		if (perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome()) == null) {
			perfil = new PerfilDisciplina(disciplina.getNome());
			
			this.perfilDisciplinaService.create(perfil);
		} else {
			perfil = perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome());
		}
		
		List<Comentario> comentarios = this.perfilDisciplinaService.buscarComentarios(disciplina.getNome());		
		List<ComentarioResponse> response = new ArrayList<ComentarioResponse>();
		
		for (int i = 0; i < comentarios.size(); i ++) {
			if(!comentarios.get(i).getApagado().equals("sim")) {
				response.add(new ComentarioResponse(comentarios.get(i).getId(), comentarios.get(i).getComentario(), comentarios.get(i).getUsuario(), comentarios.get(i).getEmailUsuario(), comentarios.get(i).getDataEHora()));	
			}
		}	
		
		boolean curtiu = false;
		
		for (int i = 0; i < perfil.getLikes().size(); i ++) {
			if(perfil.getLikes().get(i).getEmailUsuario().equals(email)) {
				curtiu = true;
			}
		}		
		PerfilDisciplinaResponse perfilResponse = new PerfilDisciplinaResponse(perfil.getNomeDisciplina(), response, perfil.getLikes(), perfil.getNotas());

        if (curtiu == true) {
			perfilResponse.setCurtiu(true);
		}
		return new ResponseEntity<PerfilDisciplinaResponse>(perfilResponse, HttpStatus.OK);
	}
	
	//Retorna a lista que contém os likes de determinado perfil de disciplina
	@GetMapping(value = "/{id}/like")
	public ResponseEntity<List<Likes>> likes(@PathVariable long id) {
		Disciplina disciplina = disciplinaService.findById(id);
		if (disciplina == null) {
			throw new DisciplinaNotFoundException("Disciplina não encontrada");
		} 		
		PerfilDisciplina perfil;

		// Se o perfil da disciplina ainda não existe então ele é criado

		if (perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome()) == null) {
			perfil = new PerfilDisciplina(disciplina.getNome());
			this.perfilDisciplinaService.create(perfil);
		} else {
			perfil = perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome());			
		}				
			
		List<Likes> likes = this.perfilDisciplinaService.buscarLikes(perfil);
		return new ResponseEntity<List<Likes>>(likes, HttpStatus.OK);
				
	}
	
	//Média da nota de determinado perfil de disciplina
	@GetMapping(value = "/{id}/nota/media")
	public ResponseEntity<Double> mediaNotas(@PathVariable long id) {
		Disciplina disciplina = disciplinaService.findById(id);
		if (disciplina == null) {
			throw new DisciplinaNotFoundException("Disciplina não encontrada");
		} 
		
		PerfilDisciplina perfil;

		// Se o perfil da disciplina ainda não existe então ele é criado

		if (perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome()) == null) {
			perfil = new PerfilDisciplina(disciplina.getNome());
			this.perfilDisciplinaService.create(perfil);
		} else {
			perfil = perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome());
		}		
			
		double notaGeral = perfil.notaDisciplina();
		return new ResponseEntity<Double>(notaGeral, HttpStatus.OK);
				
	}
	
	//Quantidade de notas que já foram registradas em determinado perfil de disciplina
	@GetMapping(value = "/{id}/nota")
	public ResponseEntity<Integer> notas(@PathVariable long id) {
		Disciplina disciplina = disciplinaService.findById(id);
		if (disciplina == null) {
			throw new DisciplinaNotFoundException("Disciplina não encontrada");
		} 
		
		PerfilDisciplina perfil;

		// Se o perfil da disciplina ainda não existe então ele é criado

		if (perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome()) == null) {
			perfil = new PerfilDisciplina(disciplina.getNome());
			this.perfilDisciplinaService.create(perfil);
		} else {
			perfil = perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome());
		}		
			
		int notas = perfil.sizeNotas();
		return new ResponseEntity<Integer>(notas, HttpStatus.OK);
				
	}
	
	//Retorna a lista dos comentários que já foram feitos em determinado perfil de disciplina	
	@GetMapping(value = "/{id}/comentario")
	public ResponseEntity<List<ComentarioResponse>> comentarios(@PathVariable long id) {
		Disciplina disciplina = disciplinaService.findById(id);
		if (disciplina == null) {
			throw new DisciplinaNotFoundException("Disciplina não encontrada");
		} 
		
		PerfilDisciplina perfil;
		
		// Se o perfil da disciplina ainda não existe então ele é criado

		if (perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome()) == null) {
			perfil = new PerfilDisciplina(disciplina.getNome());
			this.perfilDisciplinaService.create(perfil);
		} else {
			perfil = perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome());
		}		
			
		List<Comentario> comentarios = this.perfilDisciplinaService.buscarComentarios(disciplina.getNome());		
		List<ComentarioResponse> response = new ArrayList<ComentarioResponse>();
		
		for (int i = 0; i < comentarios.size(); i ++) {
			if(!comentarios.get(i).getApagado().equals("sim")) {
				response.add(new ComentarioResponse(comentarios.get(i).getId(), comentarios.get(i).getComentario(), comentarios.get(i).getUsuario(), comentarios.get(i).getEmailUsuario(), comentarios.get(i).getDataEHora()));	
			}
		}		    
		return new ResponseEntity<List<ComentarioResponse>>(response, HttpStatus.OK);
				
	}	
	
	//Dá um like em uma determinado perfil de disciplina
	@PostMapping(value = "/{id}/like/{email}")
	public ResponseEntity<PerfilDisciplinaResponse> like(@PathVariable long id, @PathVariable String email) {
		Disciplina disciplina = disciplinaService.findById(id);
		if (disciplina == null) {
			throw new DisciplinaNotFoundException("Disciplina não encontrada");
		} 
		
		Usuario usuario = usuarioService.findByEmail(email);
		if (usuario == null) {
			throw new UsuarioNotFoundException("Usuário não encontrado");
		} 
		
		PerfilDisciplina perfil;
		
		// Se o perfil da disciplina ainda não existe então ele é criado

		if (perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome()) == null) {
			perfil = new PerfilDisciplina(disciplina.getNome());
			this.perfilDisciplinaService.create(perfil);
			
		} else {
			perfil = perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome());
		}			
		 
		// Checa se um usuário já deu like na disciplina
		Boolean contem = false;
		for (int i = 0; i < perfil.getLikes().size(); i ++) {
			if (perfil.getLikes().get(i).getEmailUsuario().equals(email)) {
				contem = true;
			}			
		}		
		
		if (contem == false) {
			Likes like = new Likes(email, usuario.getPrimeiroNome() + " " + usuario.getUltimoNome(), perfil);
			this.perfilDisciplinaService.adicionarLike(perfil, like);
		} else {
			throw new UsuarioAlreadyExistsException("Usuário já deu like");
		}	
		
		List<Comentario> comentarios = this.perfilDisciplinaService.buscarComentarios(disciplina.getNome());		
		List<ComentarioResponse> response = new ArrayList<ComentarioResponse>();
		
		for (int i = 0; i < comentarios.size(); i ++) {
			if(!comentarios.get(i).getApagado().equals("sim")) {
				response.add(new ComentarioResponse(comentarios.get(i).getId(), comentarios.get(i).getComentario(), comentarios.get(i).getUsuario(), comentarios.get(i).getEmailUsuario(), comentarios.get(i).getDataEHora()));	
			}
		}	
		
		boolean curtiu = false;
		
		for (int i = 0; i < perfil.getLikes().size(); i ++) {
			if(perfil.getLikes().get(i).getEmailUsuario().equals(email)) {
				curtiu = true;
			}
		}		
		
		PerfilDisciplinaResponse perfilResponse = new PerfilDisciplinaResponse(perfil.getNomeDisciplina(), response, perfil.getLikes(), perfil.getNotas());

        if (curtiu == true) {
			perfilResponse.setCurtiu(true);
		}

		return new ResponseEntity<PerfilDisciplinaResponse>(perfilResponse, HttpStatus.ACCEPTED);  
    } 
	
	//Remove o like de determinada disciplina (apenas likes do usuario atual poderão ser removidos)
	@PutMapping(value = "/{id}/remove_like/{email}")
	public ResponseEntity<PerfilDisciplinaResponse> removeLike(@PathVariable long id, @PathVariable String email) {
		Disciplina disciplina = disciplinaService.findById(id);
		if (disciplina == null) {
			throw new DisciplinaNotFoundException("Disciplina não encontrada");
		}		
		
		PerfilDisciplina perfil = perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome());
			
		this.perfilDisciplinaService.retirarLike(perfil, email);
		
		List<Comentario> comentarios = this.perfilDisciplinaService.buscarComentarios(disciplina.getNome());		
		List<ComentarioResponse> response = new ArrayList<ComentarioResponse>();
		
		for (int i = 0; i < comentarios.size(); i ++) {
			if(!comentarios.get(i).getApagado().equals("sim")) {
				response.add(new ComentarioResponse(comentarios.get(i).getId(), comentarios.get(i).getComentario(), comentarios.get(i).getUsuario(), comentarios.get(i).getEmailUsuario(), comentarios.get(i).getDataEHora()));	
			}
		}		
		PerfilDisciplinaResponse perfilResponse = new PerfilDisciplinaResponse(perfil.getNomeDisciplina(), response, perfil.getLikes(), perfil.getNotas());
		
		return new ResponseEntity<PerfilDisciplinaResponse>(perfilResponse, HttpStatus.ACCEPTED);		 
	}	
	
	
	// Registra nota em determinado perfil de disciplina
	@PostMapping(value = "/{id}/nota/{nota}")
	public ResponseEntity<PerfilDisciplinaResponse> nota(@PathVariable long id, @PathVariable int nota) {
		Disciplina disciplina = disciplinaService.findById(id);
		if (disciplina == null) {
			throw new DisciplinaNotFoundException("Disciplina não encontrada");
		} 
		
		PerfilDisciplina perfil;

		// Se o perfil da disciplina ainda não existe então ele é criado

		if (perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome()) == null) {
			perfil = new PerfilDisciplina(disciplina.getNome());
			this.perfilDisciplinaService.create(perfil);
		} else {
			perfil = perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome());
		}	
		
		Nota newNota = new Nota(nota, perfil);
		this.perfilDisciplinaService.adicionarNota(perfil, newNota);
		
		List<Comentario> comentarios = this.perfilDisciplinaService.buscarComentarios(disciplina.getNome());		
		List<ComentarioResponse> response = new ArrayList<ComentarioResponse>();
		
		for (int i = 0; i < comentarios.size(); i ++) {
			if(!comentarios.get(i).getApagado().equals("sim")) {
				response.add(new ComentarioResponse(comentarios.get(i).getId(), comentarios.get(i).getComentario(), comentarios.get(i).getUsuario(), comentarios.get(i).getEmailUsuario(), comentarios.get(i).getDataEHora()));	
			}
		}		
		PerfilDisciplinaResponse perfilResponse = new PerfilDisciplinaResponse(perfil.getNomeDisciplina(), response, perfil.getLikes(), perfil.getNotas());

		return new ResponseEntity<PerfilDisciplinaResponse>(perfilResponse, HttpStatus.ACCEPTED);  

	}
	
	// Registra um comentário do usuário atual que está logado em determinado perfil de disciplina
	@PostMapping(value = "/{id}/comentario/{email}/{comentario}")
	public ResponseEntity<PerfilDisciplinaResponse> comentario(@PathVariable long id, @PathVariable String email, @PathVariable String comentario) {
		Disciplina disciplina = disciplinaService.findById(id);
		if (disciplina == null) {
			throw new DisciplinaNotFoundException("Disciplina não encontrada");
		} 
		
		Usuario usuario = usuarioService.findByEmail(email);
		if (usuario == null) {
			throw new UsuarioNotFoundException("Usuário não encontrado");
		}
		
		PerfilDisciplina perfil;
		
		// Se o perfil da disciplina ainda não existe então ele é criado

		
		if (perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome()) == null) {
			perfil = new PerfilDisciplina(disciplina.getNome());
			this.perfilDisciplinaService.create(perfil);
		} else {
			perfil = perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome());
		}			
		
		Comentario newComentario = new Comentario(comentario, usuario.getPrimeiroNome() + " " + usuario.getUltimoNome(), email, disciplina.getNome());
		
		this.perfilDisciplinaService.adicionarComentario(perfil.getNomeDisciplina(), newComentario);
		
		List<Comentario> comentarios = this.perfilDisciplinaService.buscarComentarios(perfil.getNomeDisciplina());		
		List<ComentarioResponse> response = new ArrayList<ComentarioResponse>();
		
		for (int i = 0; i < comentarios.size(); i ++) {
			if(!comentarios.get(i).getApagado().equals("sim")) {
				response.add(new ComentarioResponse(comentarios.get(i).getId(), comentarios.get(i).getComentario(), comentarios.get(i).getUsuario(), comentarios.get(i).getEmailUsuario(), comentarios.get(i).getDataEHora()));	
			}
		}	
		
		boolean curtiu = false;
		
		for (int i = 0; i < perfil.getLikes().size(); i ++) {
			if(perfil.getLikes().get(i).getEmailUsuario().equals(email)) {
				curtiu = true;
			}
		}		
		PerfilDisciplinaResponse perfilResponse = new PerfilDisciplinaResponse(perfil.getNomeDisciplina(), response, perfil.getLikes(), perfil.getNotas());

        if (curtiu == true) {
			perfilResponse.setCurtiu(true);
		}

		return new ResponseEntity<PerfilDisciplinaResponse>(perfilResponse, HttpStatus.ACCEPTED);  

	}
	// Apaga um comentário
	@PutMapping(value = "/{id}/comentario/apagar/{idComentario}")
	public ResponseEntity<PerfilDisciplinaResponse> apagarComentario(@PathVariable long id, @PathVariable long idComentario) {
		Disciplina disciplina = disciplinaService.findById(id);
		if (disciplina == null) {
			throw new DisciplinaNotFoundException("Disciplina não encontrada");
		} 		
		
		PerfilDisciplina perfil;		
	
	    perfil = perfilDisciplinaService.findByNomeDisciplina(disciplina.getNome());	    

		this.perfilDisciplinaService.deletarComentario(perfil.getNomeDisciplina(), idComentario);	
		
		List<Comentario> comentarios = this.perfilDisciplinaService.buscarComentarios(perfil.getNomeDisciplina());		
		List<ComentarioResponse> response = new ArrayList<ComentarioResponse>();
		
		for (int i = 0; i < comentarios.size(); i ++) {
			if(!comentarios.get(i).getApagado().equals("sim")) {
				response.add(new ComentarioResponse(comentarios.get(i).getId(), comentarios.get(i).getComentario(), comentarios.get(i).getUsuario(), comentarios.get(i).getEmailUsuario(), comentarios.get(i).getDataEHora()));	
			}
		}		
		
		PerfilDisciplinaResponse perfilResponse = new PerfilDisciplinaResponse(perfil.getNomeDisciplina(), response, perfil.getLikes(), perfil.getNotas());

		return new ResponseEntity<PerfilDisciplinaResponse>(perfilResponse, HttpStatus.ACCEPTED);  

	}
	
	//Faz comentário em outros comentários
	//O ID é do comentário pai
	@PostMapping(value = "/{id}/comentario_resposta/{email}/{comentario}")
	public ResponseEntity<List<ComentarioResponse>> comentarioResposta(@PathVariable long id, @PathVariable String email, @PathVariable String comentario) {
		Usuario usuario = usuarioService.findByEmail(email);
		if (usuario == null) {
			throw new UsuarioNotFoundException("Usuário não encontrado");
		}		
		Comentario newComentario = new Comentario(comentario, usuario.getPrimeiroNome() + " " + usuario.getUltimoNome(), email, id);
		this.perfilDisciplinaService.adicionarComentarioEmComentario(id, newComentario);
		
		List<Comentario> comentarios = this.perfilDisciplinaService.buscarComentariosFilhos(id);
		List<ComentarioResponse> response = new ArrayList<ComentarioResponse>();
		
		for (int i = 0; i < comentarios.size(); i ++) {
			if(!comentarios.get(i).getApagado().equals("sim")) {
				response.add(new ComentarioResponse(comentarios.get(i).getId(), comentarios.get(i).getComentario(), comentarios.get(i).getUsuario(), comentarios.get(i).getEmailUsuario(), comentarios.get(i).getDataEHora()));	
			}
		}	
		return new ResponseEntity<List<ComentarioResponse>>(response, HttpStatus.ACCEPTED);

	}
	
	// Busca os comentário de outro comentário
	@GetMapping(value = "/{id}/comentario_resposta")
	public ResponseEntity<List<ComentarioResponse>> buscarComentariosFilhos(@PathVariable long id) {
		
		List<Comentario> comentarios = this.perfilDisciplinaService.buscarComentariosFilhos(id);
		List<ComentarioResponse> response = new ArrayList<ComentarioResponse>();
		
		for (int i = 0; i < comentarios.size(); i ++) {
			if(!comentarios.get(i).getApagado().equals("sim")) {
				response.add(new ComentarioResponse(comentarios.get(i).getId(), comentarios.get(i).getComentario(), comentarios.get(i).getUsuario(), comentarios.get(i).getEmailUsuario(), comentarios.get(i).getDataEHora()));	
			}
		}	
		return new ResponseEntity<List<ComentarioResponse>>(response, HttpStatus.OK);
	}
	
	//Apaga comentário de outro comentário
	@PutMapping(value = "/{id}/comentario_resposta/apagar")
	public ResponseEntity<List<ComentarioResponse>> apagarComentarioFilho(@PathVariable long id) {		   

		this.perfilDisciplinaService.deletarComentarioFilho(id);	
		
		List<Comentario> comentarios = this.perfilDisciplinaService.buscarComentariosFilhos(id);
		List<ComentarioResponse> response = new ArrayList<ComentarioResponse>();
		
		for (int i = 0; i < comentarios.size(); i ++) {
			if(!comentarios.get(i).getApagado().equals("sim")) {
				response.add(new ComentarioResponse(comentarios.get(i).getId(), comentarios.get(i).getComentario(), comentarios.get(i).getUsuario(), comentarios.get(i).getEmailUsuario(), comentarios.get(i).getDataEHora()));	
			}
		}	
		return new ResponseEntity<List<ComentarioResponse>>(response, HttpStatus.ACCEPTED);  

	}
	
	@GetMapping(value = "/ranking") 
	public ResponseEntity<List<String>> ranking() {		
		
		List<Disciplina> disciplinas = this.disciplinaService.getAll();
		
		for(int i = 0; i < disciplinas.size(); i ++) {
			PerfilDisciplina perfil;
			// Se o perfil da disciplina ainda não existe então ele é criado
			if (perfilDisciplinaService.findByNomeDisciplina(disciplinas.get(i).getNome()) == null) {
				perfil = new PerfilDisciplina(disciplinas.get(i).getNome());				
				this.perfilDisciplinaService.create(perfil);
			} 
		}
				
		List<PerfilDisciplina> perfis = this.perfilDisciplinaService.getAll();		
		
        List<List<Likes>> likes = new ArrayList<>(); 
        
        for(int i = 0; i < perfis.size(); i ++) {
        	likes.add(perfis.get(i).getLikes());
        }
        
        Collections.sort(likes, new Comparator<List<Likes>>(){
            public int compare(List<Likes> a1, List<Likes> a2) {
                return a2.size() - a1.size(); // ordena do maior para o menor
            }
        }); 
        
        List<String> rank = new ArrayList<>();

        for(int i = 0; i < likes.size(); i ++) {
        	if (likes.get(i).size() > 0) {
            	rank.add(i + 1 + " - " + likes.get(i).get(0).getPerfil().getNomeDisciplina() + " - " + likes.get(i).size() + " like(s)");
        	} 
        } 
        
        for(int i = 0; i < perfis.size(); i ++) {
        	if (perfis.get(i).getLikes().size() == 0) {
        		rank.add(rank.size() + 1 + " - " + perfis.get(i).getNomeDisciplina() + " - 0 like(s)");
        	}
        }
        
        return new ResponseEntity<List<String>>(rank, HttpStatus.OK); 		
	}	
}
