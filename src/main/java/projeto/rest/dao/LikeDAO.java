package projeto.rest.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.rest.model.Likes;
import projeto.rest.model.PerfilDisciplina;

@Repository
public interface LikeDAO<T, ID extends Serializable> extends JpaRepository<Likes, Long> {
	Likes save(Likes like);
	List<Likes> findAllByPerfil(PerfilDisciplina perfil);
}
