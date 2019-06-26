package projeto.rest.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.rest.model.Usuario;


@Repository
public interface UsuarioDAO<T, ID extends Serializable> extends JpaRepository<Usuario, String> {	
    Usuario save(Usuario usuario);
    Usuario findByEmail(String email);

}







