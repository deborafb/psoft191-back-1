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
		//Likes like = this.likeDao.findById(id);
		//like.setPerfil(perfil);
        //this.likeDao.removeById(id);
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
	
}
