package com.carlos.datos.albumes;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carlos.datos.artistas.Artista;

@Repository
public interface AlbumDAO extends CrudRepository<Album, Integer> {

	// Saca los albumes de un artista
    List<Album> findByArtista(Artista artista);
}
