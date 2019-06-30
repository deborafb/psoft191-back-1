package projeto.rest.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import projeto.rest.model.Likes;
import projeto.rest.model.PerfilDisciplina;

@Repository
public interface LikeDAO<T, ID extends Serializable> extends JpaRepository<Likes, Long> {
	Likes save(Likes like);
	@Transactional
	@Modifying
	@Query("DELETE FROM Likes l WHERE l.id=:id")
	void removeById(@Param("id") long id);
	Likes findById(long id);
	List<Likes> findAllByPerfil(PerfilDisciplina perfil);
}
