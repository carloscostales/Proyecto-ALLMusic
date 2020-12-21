package com.carlos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.model.Playlist;
import com.carlos.model.Usuario;
import com.carlos.repository.PlaylistDAO;

@Service
public class PlaylistServiceImpl implements PlaylistService {

	@Autowired
	private PlaylistDAO playlistDAO;
	
	@Override
	public void add(Playlist playlist) {
		
		playlistDAO.save(playlist);
	}

	@Override
	public List<Playlist> buscarPorUsuario(Usuario usuario) {
		
		return playlistDAO.findByUsuario(usuario);
	}

	@Override
	public List<Playlist> buscarUltimasSeis(String usuario) {
		
		return playlistDAO.findLast6(usuario);
	}

	@Override
	public void borrarPlaylist(Integer id) {
		
		if(playlistDAO.existsById(id))
			playlistDAO.borrarPlaylist(id);
	}

}
