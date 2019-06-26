package projeto.rest.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.rest.model.Comentario;
import projeto.rest.model.PerfilDisciplina;

@Repository
public interface ComentarioDAO <T, ID extends Serializable> extends JpaRepository<Comentario, Long> {
	Comentario save(Comentario comentario);
	List<Comentario> findAllByPerfil(PerfilDisciplina perfil);

}
