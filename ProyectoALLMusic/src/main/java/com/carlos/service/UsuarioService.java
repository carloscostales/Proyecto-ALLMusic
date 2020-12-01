package com.carlos.service;

import java.util.List;

import com.carlos.model.Usuario;

public interface UsuarioService {

	public void add(Usuario usuario);
	
	public void delete(Usuario usuario);
	
	public void deleteById(String usuario_id);
	
	public List<Usuario> listaUsuarios();
	
	public String generoPreferidoUsuario(String usuario);
	
}
