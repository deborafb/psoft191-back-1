package projeto.rest.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projeto.rest.model.Disciplina;

@Repository
public interface DisciplinaDAO<T, ID extends Serializable> extends JpaRepository<Disciplina, Long> {
	Disciplina save(Disciplina disciplina);
	@Query("SELECT d FROM Disciplina d WHERE LOWER(d.nome) LIKE CONCAT('%', LOWER(:nome), '%')")
	List<Disciplina> findByNome(@Param("nome")String nome);
	Disciplina findById(long id);
}

