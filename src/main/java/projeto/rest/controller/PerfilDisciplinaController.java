package projeto.rest.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.exception.DisciplinaNotFoundException;
import projeto.exception.UsuarioAlreadyExistsException;
import projeto.exception.UsuarioNotFoundException;
import projeto.rest.model.Comentario;
import projeto.rest.model.Disciplina;
import projeto.rest.model.Likes;
import projeto.rest.model.Nota;
import projeto.rest.model.PerfilDisciplina;
import projeto.rest.model.Usuario;
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
	@GetMapping(value = "/{id}") 
	public ResponseEntity<PerfilDisciplina> procurar(@PathVariable long id) {
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
		
		return new ResponseEntity<PerfilDisciplina>(perfil, HttpStatus.OK);
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
	public ResponseEntity<List<Comentario>> comentarios(@PathVariable long id) {
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
			
		List<Comentario> comentarios = this.perfilDisciplinaService.buscarComentarios(perfil);
		return new ResponseEntity<List<Comentario>>(comentarios, HttpStatus.OK);
				
	}
	
	//Dá um like em uma determinado perfil de disciplina
	//!!!!!!!!Falta resolver: De alguma forma tem que passar o email do usuário atual que está logado!!!!!!!!!!
	@PostMapping(value = "/{id}/like/{email}")
	public ResponseEntity<PerfilDisciplina> like(@PathVariable long id, @PathVariable String email) {
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
		return new ResponseEntity<PerfilDisciplina>(perfil, HttpStatus.ACCEPTED);
	}	
	
	// Registra nota em determinado perfil de disciplina
	@PostMapping(value = "/{id}/nota/{nota}")
	public ResponseEntity<PerfilDisciplina> nota(@PathVariable long id, @PathVariable int nota) {
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
		return new ResponseEntity<PerfilDisciplina>(perfil, HttpStatus.ACCEPTED);  

	}
	
	// Registra um comentário do usuário atual que está logado em determinado perfil de disciplina
	//!!!!!!!!Falta resolver: De alguma forma tem que passar o email do usuário atual que está logado!!!!!!!!!!
	@PostMapping(value = "/{id}/comentario/{email}/{comentario}")
	public ResponseEntity<PerfilDisciplina> comentario(@PathVariable long id, @PathVariable String email, @PathVariable String comentario) {
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
		
		Comentario newComentario = new Comentario(comentario, usuario.getPrimeiroNome() + " " + usuario.getUltimoNome(), perfil);
		
		this.perfilDisciplinaService.adicionarComentario(perfil, newComentario);
		return new ResponseEntity<PerfilDisciplina>(perfil, HttpStatus.ACCEPTED);  

	}	

}
