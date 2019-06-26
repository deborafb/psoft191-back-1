package projeto.rest.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projeto.rest.model.Nota;
import projeto.rest.model.PerfilDisciplina;

@Repository
public interface NotaDAO <T, ID extends Serializable> extends JpaRepository<Nota, Long> {
	Nota save(Nota nota);
	List<Nota> findAllByPerfil(PerfilDisciplina perfil);

}
