package projeto.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import projeto.rest.model.Disciplina;
import projeto.rest.response.DisciplinaResponse;
import projeto.rest.service.DisciplinaService;

@RestController
@RequestMapping({"/v1/disciplinas"})
public class DisciplinaController {
	
	private DisciplinaService disciplinaService;
	
	public DisciplinaController(DisciplinaService disciplinaService) {
		this.disciplinaService = disciplinaService;
	}

	@GetMapping(value = "/{disciplinaString}")
	public ResponseEntity<List<DisciplinaResponse>> procurar(@PathVariable String disciplinaString) {
		List<Disciplina> disciplina = disciplinaService.procurar(disciplinaString);		
		
		List<DisciplinaResponse> disciplinas = new ArrayList<DisciplinaResponse>();

		for (int i = 0; i < disciplina.size(); i ++) {
			disciplinas.add(new DisciplinaResponse(disciplina.get(i).getId(), disciplina.get(i).getNome()));
		}
	    return new ResponseEntity<List<DisciplinaResponse>>(disciplinas, HttpStatus.OK);		
	}
	
	@PostMapping(value = "/")
	@ResponseBody
	public ResponseEntity<Disciplina> create(@RequestBody Disciplina disciplina) {

		if (disciplina.getNome() == null) { 
			throw new DisciplinaInvalidaException("Disciplina inválida, preencha todos os campos corretamente");
		}
		
	    Disciplina newDisciplina = DisciplinaService.create(disciplina);
	    
	    if (newDisciplina == null) {
	        //500?!?!
	        throw  new InternalError("Algo não está certo");
	    }

	    return new ResponseEntity<Disciplina>(newDisciplina, HttpStatus.CREATED);
	}

}
