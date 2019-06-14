package dao;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Usuario;

@Repository
public interface UsuarioDao<T, ID extends Serializable> extends JpaRepository<Usuario, String> {
    @SuppressWarnings("unchecked")
	Usuario save(Usuario usuario);
    //Falta resolver isso aqui:
	Usuario findById(String email);
}



