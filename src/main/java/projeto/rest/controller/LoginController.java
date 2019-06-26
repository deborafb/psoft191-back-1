package projeto.rest.controller;

import projeto.exception.UsuarioNotFoundException;
import projeto.rest.model.Usuario;
import projeto.rest.service.UsuarioService;
import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/v1/auth")
public class LoginController {
    
    private final String TOKEN_KEY = "melancia";
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/login")
    public LoginResponse authenticate(@RequestBody Usuario usuario) throws UsuarioNotFoundException {

        // Recupera o usuario
        Usuario user = usuarioService.findByEmail(usuario.getEmail());
        
        // verificacoes
        if(user == null) {
            throw new UsuarioNotFoundException("Usuario não encontrado!");
        }
        
        if(!user.getSenha().equals(usuario.getSenha())) {
            throw new UsuarioNotFoundException("Senha inválida!");
        }
        
        String token = Jwts.builder().
                setSubject(user.getEmail()).
                signWith(SignatureAlgorithm.HS512, TOKEN_KEY).
                setExpiration(new Date(System.currentTimeMillis() + 10 * 600 * 100000))
                .compact();
        
        return new LoginResponse(token);        
        
    }
    
    private class LoginResponse {
        public String token;
        
        public LoginResponse(String token) {
            this.token = token;
        }
    }

}    

