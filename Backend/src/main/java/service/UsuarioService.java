package service;

import org.springframework.stereotype.Service;

import dao.UsuarioDao;
import model.Usuario;

@Service
public class UsuarioService {
	
	private final UsuarioDao usuarioDao;

	UsuarioService(UsuarioDao usuarioDao){		
		this.usuarioDao = usuarioDao;		
	}
	
	public Usuario create(Usuario usuario) {
	    return usuarioDao.save(usuario);
	}
   // E também criar a exceção UsuarioNotFoundException, no lab2 tem explicando como fazer
	public Usuario update(Usuario usuarioToUpdate) throws UsuarioNotFoundException {
	    Usuario usuario = usuarioDao.findById(usuarioToUpdate.getId());
	       if (usuario == null)
	           throw new UsuarioNotFoundException("Não é possível atualizar. Usuário inexistente.");

	       return usuarioDao.save(usuarioToUpdate);
	   }

	   public void delete(String email) {
	       usuarioDao.deleteById(email);
	   }

	   public Usuario findById(String email) {
	       return usuarioDao.findById(email);
	   }

}
