package projeto.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import projeto.exception.DisciplinaInvalidaException;
import projeto.exception.DisciplinaNotFoundException;
import projeto.rest.dao.DisciplinaDAO;
import projeto.rest.model.Disciplina;

@Service
public class DisciplinaService {
	
	@SuppressWarnings("rawtypes")
	private static  DisciplinaDAO disciplinaDao;

	@SuppressWarnings("rawtypes")
	public DisciplinaService(DisciplinaDAO disciplinaDao){
        DisciplinaService.disciplinaDao = disciplinaDao;
    }     

    @SuppressWarnings("unchecked")
	public List<Disciplina> procurar(String nomeDisciplina) { 
		List<Disciplina> disciplina = disciplinaDao.findByNome(nomeDisciplina);

    	if (disciplina.size() == 0) {
	        throw new DisciplinaNotFoundException("Disciplina não encontrada");
	    }		
		
        return disciplina;
    }
    
	public Disciplina findById(long id) {
		
		Disciplina disciplina = disciplinaDao.findById(id);
		
		if (disciplina == null) {
			throw new DisciplinaNotFoundException("Disciplina não encontrada");
		} 	
		
    	return disciplina;
    }

	public static Disciplina create(Disciplina disciplina) {
		if (disciplina.getNome() == null || disciplina.getNome() == "") { 
			throw new DisciplinaInvalidaException("Disciplina inválida, preencha todos os campos corretamente");
		}
		
	    Disciplina novaDisciplina = disciplinaDao.save(disciplina);
	    
	    if (novaDisciplina == null) {
	        throw  new InternalError("Algo não está certo");
	    }	    
		
		return disciplinaDao.save(disciplina);
	}
	
	public List<Disciplina> getAll() {
		return disciplinaDao.findAll();
	}
}
