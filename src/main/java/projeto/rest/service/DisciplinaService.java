package projeto.rest.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
	public List<Disciplina> procurar(String string) { 
        return disciplinaDao.findByNome(string);
    }
    
	public Disciplina findById(long id) {
    	return disciplinaDao.findById(id);
    }

	public static Disciplina create(Disciplina disciplina) {
		return disciplinaDao.save(disciplina);
	}
}
