package com.carlos.service;

import java.util.List;

import com.carlos.model.Playlist;
import com.carlos.model.Usuario;

public interface PlaylistService {

	public void add(Playlist playlist);
	
	public List<Playlist> buscarPorUsuario(Usuario usuario);
	
	public List<Playlist> buscarUltimasSeis(String usuario);
	
	public void borrarPlaylist(Integer id);
	
}
