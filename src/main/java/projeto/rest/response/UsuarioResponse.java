package projeto.rest.response;

import lombok.Data;

@Data
public class UsuarioResponse {
	
    private String email;    
    private String primeiroNome;
    private String ultimoNome;
    
    public UsuarioResponse(String primeiroNome, String ultimoNome, String email) {
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.email = email;
    }  
}
