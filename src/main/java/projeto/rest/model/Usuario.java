package projeto.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Usuario {

    private String primeiroNome;
    private String ultimoNome;
    private String senha;

    @Id
    private String email;
    public Usuario() {

    }

    public Usuario(String primeiroNome, String ultimoNome, String email, String senha) {
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.email = email;
        this.senha = senha;
    }

}
