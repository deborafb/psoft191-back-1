package projeto.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import projeto.exception.*;
import projeto.rest.model.Usuario;
import projeto.rest.response.UsuarioResponse;
import projeto.rest.service.UsuarioService;

@RestController
@RequestMapping({"/v1/usuarios"})
public class UsuarioController {
	
	private UsuarioService usuarioService;
	
	UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@GetMapping(value = "/{email}")	   
	@ResponseBody
	public ResponseEntity<UsuarioResponse> findByEmail(@PathVariable String email) {

	    Usuario usuario = usuarioService.findByEmail(email);
	    if (usuario == null) {
	        throw new UsuarioNotFoundException("Usuário não encontrado");
	    }	    
	    UsuarioResponse usuarioResponse = new UsuarioResponse(usuario.getPrimeiroNome(), usuario.getUltimoNome(), usuario.getEmail());
	    return new ResponseEntity<UsuarioResponse>(usuarioResponse, HttpStatus.OK);
	}
	
	@PostMapping(value = "/")
	@ResponseBody
	public ResponseEntity<UsuarioResponse> create(@RequestBody Usuario usuario) {

		if (usuarioService.findByEmail(usuario.getEmail()) != null){
			throw new UsuarioAlreadyExistsException("Usuário já existe, tente outro e-mail");
		}

		if (usuario.getEmail() == null || usuario.getPrimeiroNome() == null 
			|| usuario.getUltimoNome() == null || usuario.getSenha() == null) {
			throw new UsuarioInvalidoException("Usuário inválido, preencha todos os campos corretamente");
		}
		
	    Usuario newUsuario = usuarioService.create(usuario);
	    
	    if (newUsuario == null) {
	        //500?!?!
	        throw  new InternalError("Algo não está certo");
	    }
	    UsuarioResponse usuarioResponse = new UsuarioResponse(newUsuario.getPrimeiroNome(), newUsuario.getUltimoNome(), newUsuario.getEmail());

	    return new ResponseEntity<UsuarioResponse>(usuarioResponse, HttpStatus.CREATED);
	}

}
