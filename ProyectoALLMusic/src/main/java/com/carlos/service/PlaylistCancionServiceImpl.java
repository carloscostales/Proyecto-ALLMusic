package com.carlos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.model.Playlist;
import com.carlos.model.PlaylistCancion;
import com.carlos.repository.PlaylistCancionDAO;

@Service
public class PlaylistCancionServiceImpl implements PlaylistCancionService {

	@Autowired
	private PlaylistCancionDAO playlistCancionDAO;

	@Override
	public void add(PlaylistCancion pc) {
		
		playlistCancionDAO.save(pc);
	}

	@Override
	public void delete(PlaylistCancion pc) {
		
		playlistCancionDAO.delete(pc);
	}

	@Override
	public List<PlaylistCancion> buscarCancionesDePlaylist(Playlist playlist) {
		
		return playlistCancionDAO.findByPlaylist(playlist);
	}

}
