package com.carlos.service;

import java.util.List;

import com.carlos.model.Album;
import com.carlos.model.Cancion;

public interface CancionService {

	public void add(Cancion cancion);
	
	public void delete(Cancion cancion);
	
	public List<Cancion> buscarPorAlbum(Album album);
}
