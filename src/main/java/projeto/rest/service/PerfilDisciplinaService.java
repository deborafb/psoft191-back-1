package projeto.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import projeto.rest.dao.ComentarioDAO;
import projeto.rest.dao.LikeDAO;
import projeto.rest.dao.NotaDAO;
import projeto.rest.dao.PerfilDisciplinaDAO;
import projeto.rest.model.Comentario;
import projeto.rest.model.Likes;
import projeto.rest.model.Nota;
import projeto.rest.model.PerfilDisciplina;

@Service
public class PerfilDisciplinaService {
	
	private PerfilDisciplinaDAO perfilDisciplinaDao;
	private LikeDAO likeDao;
	private NotaDAO notaDao;
	private ComentarioDAO comentarioDao;
	
	public PerfilDisciplinaService(PerfilDisciplinaDAO PerfildisciplinaDao, LikeDAO likeDao, NotaDAO notaDao, ComentarioDAO comentarioDao){
        this.perfilDisciplinaDao = PerfildisciplinaDao;
        this.likeDao = likeDao;
        this.notaDao = notaDao;
        this.comentarioDao = comentarioDao;
    }	
	
	public PerfilDisciplina findByNomeDisciplina(String nome) {
		return this.perfilDisciplinaDao.findByNomeDisciplina(nome);
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
	
	public List<Comentario> buscarComentarios(PerfilDisciplina perfil) {
		return this.comentarioDao.findAllByPerfil(perfil);
	}
	
	public PerfilDisciplina adicionarComentario(PerfilDisciplina perfil, Comentario comentario) {		
		comentario.setPerfil(perfil);
		this.comentarioDao.save(comentario);				
		return perfil;		
	}
	
	public PerfilDisciplina deletarComentario(PerfilDisciplina perfil, long idComentario) {	
		Comentario comentario = this.comentarioDao.findById(idComentario);
		comentario.setPerfil(perfil);	
		comentario.setApagado("sim");
		this.comentarioDao.save(comentario);				
		return perfil;		
	}
	
	public PerfilDisciplina retirarLike(PerfilDisciplina perfil, long id) {
		Likes like = this.likeDao.findById(id);
		like.setPerfil(perfil);
        this.likeDao.removeById(id);
        return perfil;
	}
	//FaltaResolver
	public PerfilDisciplina adicionarComentarioEmComentario(PerfilDisciplina perfil, long idComentarioPai, Comentario comentarioFilho) {
		comentarioFilho.setPerfil(perfil);
		Comentario comentario = this.comentarioDao.findById(idComentarioPai);		
		//comentario.setFilhos(comentario.);
		this.perfilDisciplinaDao.save(perfil);
		return perfil;
	}
	
}
