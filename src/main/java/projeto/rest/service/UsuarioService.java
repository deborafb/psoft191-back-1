package projeto.rest.service;

import org.springframework.stereotype.Service;

import projeto.exception.UsuarioAlreadyExistsException;
import projeto.exception.UsuarioInvalidoException;
import projeto.exception.UsuarioNotFoundException;
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
    	
    	if (usuarioDao.findByEmail(usuario.getEmail()) != null){
			throw new UsuarioAlreadyExistsException("Usuário já existe, tente outro e-mail");
		}

		if (usuario.getEmail() == null || usuario.getPrimeiroNome() == null 
			|| usuario.getUltimoNome() == null || usuario.getSenha() == null 
			|| usuario.getEmail() == "" || usuario.getPrimeiroNome() == "" 
			|| usuario.getUltimoNome() == "" || usuario.getSenha() == "") {
			throw new UsuarioInvalidoException("Usuário inválido, preencha todos os campos corretamente");
		}	
		
        return usuarioDao.save(usuario);
    }   

    public Usuario findByEmail(String email) {
    	
    	Usuario usuario = usuarioDao.findByEmail(email);
	    if (usuario == null) {
	        throw new UsuarioNotFoundException("Usuário não encontrado");
	    }	    	    
        return usuario;
    }

}
