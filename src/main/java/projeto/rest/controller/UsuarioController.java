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
	
	@PostMapping(value = "/")
	@ResponseBody
	public ResponseEntity<UsuarioResponse> create(@RequestBody Usuario usuario) {
		
	    Usuario novoUsuario = usuarioService.create(usuario);
	    
	    if (novoUsuario == null) {
	        throw  new InternalError("Algo não está certo");
	    }
	    
	    UsuarioResponse usuarioResponse = new UsuarioResponse(novoUsuario.getPrimeiroNome(), novoUsuario.getUltimoNome(), novoUsuario.getEmail());

	    return new ResponseEntity<UsuarioResponse>(usuarioResponse, HttpStatus.CREATED);
	}

	@GetMapping(value = "/{email}")	  
	@ResponseBody
	public ResponseEntity<UsuarioResponse> findByEmail(@PathVariable String email) {
		
	    Usuario usuario = usuarioService.findByEmail(email);
	    UsuarioResponse usuarioResponse = new UsuarioResponse(usuario.getPrimeiroNome(), usuario.getUltimoNome(), usuario.getEmail());

	    return new ResponseEntity<UsuarioResponse>(usuarioResponse, HttpStatus.OK);
	}
	
	
}
