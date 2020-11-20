package com.carlos.service;

import java.util.List;

import com.carlos.model.Playlist;
import com.carlos.model.PlaylistCancion;

public interface PlaylistCancionService {

	public void add(PlaylistCancion pc);
	
	public void delete(PlaylistCancion pc);
	
	public List<PlaylistCancion> buscarCancionesDePlaylist(Playlist playlist);
	
}
