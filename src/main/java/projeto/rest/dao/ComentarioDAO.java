package projeto.rest.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.rest.model.Comentario;

@Repository
public interface ComentarioDAO <T, ID extends Serializable> extends JpaRepository<Comentario, Long> {
	Comentario save(Comentario comentario);
	List<Comentario> findAllByNomeDisciplina(String nome);
    Comentario findById(long id);
    List<Comentario> findAllByComentarioPai(long id);
}
