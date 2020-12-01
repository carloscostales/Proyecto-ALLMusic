package com.carlos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.model.Usuario;
import com.carlos.repository.UsuarioDAO;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;
	@Override
	public void add(Usuario usuario) {
		
		usuarioDAO.save(usuario);
	}

	@Override
	public void delete(Usuario usuario) {
		
		usuarioDAO.delete(usuario);
	}
	
	@Override
	public void deleteById(String usuario_id) {
		
		usuarioDAO.deleteById(usuario_id);
	}

	@Override
	public List<Usuario> listaUsuarios() {
		
		return (List<Usuario>) usuarioDAO.findAll();
	}

	@Override
	public String generoPreferidoUsuario(String usuario) {
		
		return usuarioDAO.mostAddedGenreUser(usuario);
	}

	@Override
	public Integer artistaPreferidoUsuario(String usuario) {
		
		return usuarioDAO.mostAddedArtistUser(usuario);
	}

}
