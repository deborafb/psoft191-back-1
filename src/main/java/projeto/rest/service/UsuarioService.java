package projeto.rest.service;

import org.springframework.stereotype.Service;

import projeto.rest.dao.UsuarioDAO;
import projeto.rest.model.Usuario;

@Service
public class UsuarioService {

    @SuppressWarnings("rawtypes")
	private final UsuarioDAO usuarioDao;

    @SuppressWarnings("rawtypes")
	UsuarioService(UsuarioDAO usuarioDao){
        this.usuarioDao = usuarioDao;
    }

    public Usuario create(Usuario usuario) {
        return usuarioDao.save(usuario);
    }   

    public Usuario findByEmail(String email) {
        return usuarioDao.findByEmail(email);
    }

}
