package com.carlos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.model.Album;
import com.carlos.model.Cancion;
import com.carlos.repository.CancionDAO;

@Service
public class CancionServiceImpl implements CancionService {

	@Autowired
	private CancionDAO cancionDAO;
	
	
	@Override
	public void add(Cancion cancion) {
		
		cancionDAO.save(cancion);
	}
	
	@Override
	public void update(Cancion cancion) {
		
		if (cancionDAO.existsById(cancion.getId()))
			cancionDAO.save(cancion);
	}
	
	@Override
	public void delete(Cancion cancion) {
		
		if (cancionDAO.existsById(cancion.getId()))
			cancionDAO.delete(cancion);
	}
	
	@Override
	public List<Cancion> buscarPorAlbum(Album album) {
		
		return cancionDAO.findByAlbum(album);
	}
	
	@Override
	public void borrarCancionesDeArtista(Integer id) {
		
		if (cancionDAO.existsById(id))
			cancionDAO.borrarCancionesDeArtista(id);
	}
	
	@Override
	public void borrarCancionesDeAlbum(Integer id) {
		
		if (cancionDAO.existsById(id))
			cancionDAO.borrarCancionesDeAlbum(id);
	}
	
	@Override
	public void borrarCancionesDeArtistaEnPlaylist(Integer id) {
		
		if (cancionDAO.existsById(id))
			cancionDAO.borrarCancionesDeArtistaEnPlaylist(id);
	}
	
	@Override
	public void borrarCancionesDeAlbumEnPlaylist(Integer id) {
		
		if (cancionDAO.existsById(id))
			cancionDAO.borrarCancionesDeAlbumEnPlaylist(id);
	}

	@Override
	public void borrarCancionesDePlaylist(Integer id) {
		
		if (cancionDAO.existsById(id))
			cancionDAO.borrarCancionesDePlaylist(id);
	}
	
	@Override
	public void borrarCancion(Integer id) {
		
		if (cancionDAO.existsById(id))
			cancionDAO.borrarCancion(id);
	}

}
