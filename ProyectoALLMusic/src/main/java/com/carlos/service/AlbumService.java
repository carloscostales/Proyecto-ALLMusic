package com.carlos.service;

import java.text.ParseException;
import java.util.List;

import com.carlos.model.Album;
import com.carlos.model.Artista;

public interface AlbumService {
	
	public void add(Album album);
	
	public void update(Album album);
	
	public void delete(Album album);
	
	public List<Album> listaAlbumesCompleta();
	
	public List<Album> buscarAlbumesDeArtista(Artista artista);
	
	public List<Album> buscarUltimosSeis();
	
	public List<Album> buscarPorTipoAlbum(Integer album_id, String tipo);
	
	public List<Album> buscarTodosMenosUnoArtista(Integer artista_id, Integer album_id);
	
	public void borrarAlbumesDeArtista(Integer id);
	
	public void borrarAlbum(Integer id);
	
	public List<Album> cambiarFecha(List<Album> lista_albumes) throws ParseException;
	
}
