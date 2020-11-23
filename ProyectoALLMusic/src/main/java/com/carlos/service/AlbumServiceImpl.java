package com.carlos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.model.Album;
import com.carlos.model.Artista;
import com.carlos.repository.AlbumDAO;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumDAO albumDAO;
	
	
	@Override
	public void add(Album album) {

		albumDAO.save(album);
	}

	@Override
	public void update(Album album) {

		if(albumDAO.existsById(album.getId()))
			albumDAO.save(album);
	}

	@Override
	public void delete(Album album) {
		
		if (albumDAO.existsById(album.getId()))
			albumDAO.delete(album);
	}
	
	@Override
	public List<Album> listaAlbumesCompleta() {
		return (List<Album>) albumDAO.findAll();
	}

	@Override
	public List<Album> buscarAlbumesDeArtista(Artista artista) {
		
		return albumDAO.findByArtista(artista);
	}

	@Override
	public List<Album> buscarUltimosSeis() {

		return albumDAO.findLast6();
	}

	@Override
	public List<Album> buscarPorTipoAlbum(Integer album_id, String tipo) {
		
		return albumDAO.findAllByTipo_album(album_id, tipo);
	}

	@Override
	public List<Album> buscarTodosMenosUnoArtista(Integer artista_id, Integer album_id) {
		
		return albumDAO.findTodosMenosUnoArtista(artista_id, album_id);
	}

	@Override
	public void borrarAlbumesDeArtista(Integer id) {
		
		if (albumDAO.existsById(id))
			albumDAO.borrarAlbumesDeArtista(id);
	}

	@Override
	public void borrarAlbum(Integer id) {
		
		if (albumDAO.existsById(id))
			albumDAO.borrarAlbum(id);
	}
	
}
