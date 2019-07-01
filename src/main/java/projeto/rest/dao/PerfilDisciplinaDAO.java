package projeto.rest.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import projeto.rest.model.PerfilDisciplina;

@Repository
public interface PerfilDisciplinaDAO<T, ID extends Serializable> extends JpaRepository<PerfilDisciplina, String> {	
	PerfilDisciplina save(PerfilDisciplina perfil);	
	PerfilDisciplina findByNomeDisciplina(String nome);
}
