package projeto.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Classe apenas para teste de autenticação
@RestController
public class PrivateController {
	
	@GetMapping("/private") 
	public String privateMSG() {
		return "Mensagem apenas para usuários com token!";
	}

	@GetMapping("/public") 
	public String publicMSG() {
		return "Todos podem acessar!";
	}
}
