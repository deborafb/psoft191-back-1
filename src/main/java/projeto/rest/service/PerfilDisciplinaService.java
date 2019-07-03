package projeto.rest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import projeto.rest.dao.ComentarioDAO;
import projeto.rest.dao.LikeDAO;
import projeto.rest.dao.NotaDAO;
import projeto.rest.dao.PerfilDisciplinaDAO;
import projeto.rest.model.Comentario;
import projeto.rest.model.Disciplina;
import projeto.rest.model.Likes;
import projeto.rest.model.Nota;
import projeto.rest.model.PerfilDisciplina;

@Service
public class PerfilDisciplinaService {
	
	private PerfilDisciplinaDAO perfilDisciplinaDao;
	private LikeDAO likeDao;
	private NotaDAO notaDao;
	private ComentarioDAO comentarioDao;
	private DisciplinaService disciplinaService;
	
	public PerfilDisciplinaService(PerfilDisciplinaDAO PerfildisciplinaDao, LikeDAO likeDao, NotaDAO notaDao, 
			ComentarioDAO comentarioDao, DisciplinaService disciplinaService){
        this.perfilDisciplinaDao = PerfildisciplinaDao;
        this.likeDao = likeDao;
        this.notaDao = notaDao;
        this.comentarioDao = comentarioDao;
        this.disciplinaService = disciplinaService;
    }	
	
	public PerfilDisciplina findByNomeDisciplina(String nome) {
		PerfilDisciplina perfil;

		// Se o perfil da disciplina ainda não existe então ele é criado

		if (this.perfilDisciplinaDao.findByNomeDisciplina(nome) == null) {
			perfil = create(new PerfilDisciplina(nome));
		} else {
			perfil = this.perfilDisciplinaDao.findByNomeDisciplina(nome);			
		}			
		return perfil;
	}
	
	public PerfilDisciplina create(PerfilDisciplina perfil) {
		return perfilDisciplinaDao.save(perfil);
	}	
	
	public List<Likes> buscarLikes(PerfilDisciplina perfil) {
		return this.likeDao.findAllByPerfil(perfil);
	}
	
	public PerfilDisciplina adicionarLike(PerfilDisciplina perfil, Likes like) {		
		like.setPerfil(perfil);
		this.likeDao.save(like);				
		return perfil;
	}
	
	public List<Nota> buscarNotas(PerfilDisciplina perfil) {
		return this.notaDao.findAllByPerfil(perfil);
	}
	
	public PerfilDisciplina adicionarNota(PerfilDisciplina perfil, Nota nota) {		
		nota.setPerfil(perfil);
		this.notaDao.save(nota);				
		return perfil;
	}
	
	public List<Comentario> buscarComentarios(String disciplina) {
		return this.comentarioDao.findAllByNomeDisciplina(disciplina);
	}
	
	public PerfilDisciplina adicionarComentario(String disciplina, Comentario comentario) {		
		comentario.setNomeDisciplina(disciplina);
		this.comentarioDao.save(comentario);				
		return this.perfilDisciplinaDao.findByNomeDisciplina(disciplina);
	}
	
	public PerfilDisciplina deletarComentario(String disciplina, long idComentario) {	
		Comentario comentario = this.comentarioDao.findById(idComentario);
		comentario.setNomeDisciplina(disciplina);	
		comentario.setApagado("sim");
		this.comentarioDao.save(comentario);				
		return this.perfilDisciplinaDao.findByNomeDisciplina(disciplina);		
	}
	
	public PerfilDisciplina retirarLike(PerfilDisciplina perfil, String email) {
		List<Likes> likes = this.likeDao.findAllByPerfil(perfil);
		for (int i = 0; i < likes.size(); i ++) {
			if (likes.get(i).getEmailUsuario().equals(email)) {
				this.likeDao.removeById(likes.get(i).getId());
			}
		}		
        return perfil;
	}
	
	public List<Comentario> adicionarComentarioEmComentario(long idComentarioPai, Comentario comentarioFilho) {
		comentarioFilho.setComentarioPai(idComentarioPai);
		this.comentarioDao.save(comentarioFilho);
		return this.comentarioDao.findAllByComentarioPai(idComentarioPai);
		
	}	
	
	public List<Comentario> buscarComentariosFilhos(long idComentarioPai) {
		return this.comentarioDao.findAllByComentarioPai(idComentarioPai);		
	}
	
	public List<Comentario> deletarComentarioFilho(long idComentario) {	
		Comentario comentario = this.comentarioDao.findById(idComentario);
		comentario.setApagado("sim");
		this.comentarioDao.save(comentario);				
		return this.comentarioDao.findAllByComentarioPai(idComentario);	
	}
	
	public List<PerfilDisciplina> getAll() {
		return this.perfilDisciplinaDao.findAll();
	}
	
	public List<String> ranking(){
		
        List<Disciplina> disciplinas = this.disciplinaService.getAll();
		
		for(int i = 0; i < disciplinas.size(); i ++) {
			findByNomeDisciplina(disciplinas.get(i).getNome());			
		}
				
		List<PerfilDisciplina> perfis = getAll();		
		
        List<List<Likes>> likes = new ArrayList<>(); 
        
        for(int i = 0; i < perfis.size(); i ++) {
        	likes.add(perfis.get(i).getLikes());
        }
        
        Collections.sort(likes, new Comparator<List<Likes>>(){
            public int compare(List<Likes> a1, List<Likes> a2) {
                return a2.size() - a1.size(); // ordena do maior para o menor
            }
        });        
        
        List<String> rank = new ArrayList<>();

        for(int i = 0; i < likes.size(); i ++) {
        	if (likes.get(i).size() > 0) {
            	rank.add(i + 1 + " - " + nomeFormatacao(likes.get(i).get(0).getPerfil().getNomeDisciplina()) + " - " + likes.get(i).size() + " like(s)");
        	} 
        } 
        
        for(int i = 0; i < perfis.size(); i ++) {
        	if (perfis.get(i).getLikes().size() == 0) {
        		rank.add(rank.size() + 1 + " - " + nomeFormatacao(perfis.get(i).getNomeDisciplina()) + " - 0 like(s)");
        	}
        }        
        
        return rank;         
	}
	
	public String nomeFormatacao(String nome) {
		String saida = "";
		String[] nomeSaida = nome.split(" ");
	    for(int i = 0; i < nomeSaida.length; i ++) {
	    	saida += nomeSaida[i].substring(0, 1) + nomeSaida[i].substring(1, nomeSaida[i].length()).toLowerCase() + " ";	    	
	    }
	    saida = saida.trim();
	    if (saida.substring(saida.length()-2, saida.length()).equals("ii")) {
	    	saida = saida.substring(0, saida.length()-2);
	    	saida += "II";
	    } else if (saida.substring(saida.length()-1, saida.length()).equals("i")) {
	    	saida = saida.substring(0, saida.length()-1);
	    	saida += "I";
	    }
	    return saida;	
	}
}
